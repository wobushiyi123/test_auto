package com.dcits.selenium.sonic.preDataUtil;

import com.dcits.selenium.sonic.createJsonFile.GetPreDataToJsonArray;
import com.dcits.selenium.sonic.interfaceTest.JobAndStepInterface;
import com.dcits.util.SonicPropertyUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.Comparator;

/**
 * 生成预言值公共类
 */
public class GetPreDataFromDb {
    //日志打印
    private static final Logger logger = LoggerFactory.getLogger(GetPreDataFromDb.class);
    //接口
    JobAndStepInterface jobAndStepInterface = JobAndStepInterface.getInstance();

    /**
     * 生成预言值公共方法
     *
     * @param param_fileName 参数文件名
     * @param pre_fileName   预言值文件名
     * @param isTimedJob     是否为定时任务
     */
    @Parameters({"param_fileName", "pre_fileName", "isTimedJob"})
    @Test
    public void getPreDataFromDb(String param_fileName, String pre_fileName, String isTimedJob) {
        //总的预言值
        JSONObject predictJSON = new JSONObject();
        String doJobStatus = "";
        //1.JOB列表
        JSONObject getList = jobAndStepInterface.getList(param_fileName);
        String jobId = jobAndStepInterface.getJobInfo(getList, "jobId");
        //2.JOB定义详情
        JSONObject getChain = jobAndStepInterface.getChain(param_fileName, jobId);
        //3.JOB指令
        String jobRunId = jobAndStepInterface.getJobRunId(param_fileName, jobId);
        JSONObject jobCommand = jobAndStepInterface.getJobCommand(param_fileName, jobId, jobRunId, doJobStatus);
        //轮询JOB，直到JOB运行完成
        JobAndStepInterface.getInstance().waitGetCurrentRunJob(param_fileName, jobId);
        //判断定时任务
        long start = jobCommand.getLong("startJobTimeFlag");
        long end = System.currentTimeMillis();
        if (Boolean.valueOf(isTimedJob)) {
            doJobStatus = "stop";
            JSONObject timeJobStopResult = doJobTime(param_fileName, jobId, jobRunId, doJobStatus);
            end = Long.valueOf(String.valueOf(timeJobStopResult.get("endJobTimeFlag")));
            predictJSON.put("timeJobStopResult", timeJobStopResult);
        }
        //4.JOB运行列表
        JSONObject getCurrentRunJob = jobAndStepInterface.getCurrentRunJob(param_fileName, jobId);
        //5.JOB运行详情
        jobRunId = jobAndStepInterface.getJobInfo(getCurrentRunJob, "jobRunId");
        JSONObject getRunChain = jobAndStepInterface.getRunChain(param_fileName, jobId, jobRunId);
        //6.递归获取STEP运行列表
        JSONObject getCurrentRunStep = jobAndStepInterface.getCurrentRunStep(param_fileName, jobRunId);
        getStepRunList(getCurrentRunStep, predictJSON, param_fileName, "step");
        //历史列表查询执行次数,依据定时任务执行次数
        if (Boolean.valueOf(isTimedJob)) {
            //定时任务执行历史列表返回值
            JSONObject getHistRunJob = JobAndStepInterface.getInstance().getHistRunJob(param_fileName, jobId,
                    String.valueOf(start), String.valueOf(end), isTimedJob);
            if (getHistRunJob != null) {
                predictJSON.put("getHistRunJobHaveLimitTime", getHistRunJob);
            }
        }
        predictJSON.put("getList", getList);
        predictJSON.put("getChain", getChain);
        predictJSON.put("jobCommand", jobCommand);
        predictJSON.put("getCurrentRunJob", getCurrentRunJob);
        predictJSON.put("getRunChain", getRunChain);
        predictJSON.put("getCurrentRunStep", getCurrentRunStep);
        //将预言值存储至xml文件中param_fileName
        SavePreDataToXml.savePreDataToXml(predictJSON, pre_fileName);
        //生成JSONArray格式的预言值
        GetPreDataToJsonArray.getInstance().getPreDataToJsonArray(predictJSON, pre_fileName);
        logger.info("==预言值存储完成==");
    }

    /**
     * 用于判断定时任务时间,以及获取结果值
     *
     * @param param_fileName
     * @param jobId
     * @param jobRunId
     * @param doJobStatus
     * @return
     */
    public JSONObject doJobTime(String param_fileName, String jobId,
                                String jobRunId, String doJobStatus) {
        try {
            logger.info("定时任务运行中...");
            Thread.sleep(Long.parseLong(SonicPropertyUtil.getProperty("sonic.job.timelength", param_fileName)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //停止定时任务
        JSONObject timeJobStopResult = JobAndStepInterface.getInstance().jobCommand(param_fileName, jobId, jobRunId, doJobStatus);
        timeJobStopResult.remove("startJobTimeFlag");
        timeJobStopResult.put("endJobTimeFlag", System.currentTimeMillis());
        logger.info("定时任务已停止");
        return timeJobStopResult;
    }

    /**
     * 递归获取STEP运行列表
     *
     * @param getCurrentRunStep step运行列表
     * @param predictJSON       预期值
     * @param param_fileName    参数文件
     */
    private void getStepRunList(JSONObject getCurrentRunStep, JSONObject predictJSON, String param_fileName, String parentStepName) {
        JSONArray stepJSONArray = getCurrentRunStep.getJSONArray("data");
        JSONObject stepRunInfo = null;
        JSONObject stepRunErrorInfo = null;
        JSONObject splitStepRunInfo = null;
        JSONObject stepSegmentInfo = null;
        String keyName = "";
        String stepType = "";//step类型
        String stepRunId = "";
        String stepName = "";//step名称
        //正序
        stepJSONArray.sort(Comparator.comparing(obj -> ((JSONObject) obj).getString("stepName")));
        stepJSONArray.sort(Comparator.comparing(obj -> ((JSONObject) obj).getString("params")));

        //遍历STEP运行列表
        for (int i = 0; i < stepJSONArray.size(); i++) {
            stepRunInfo = stepJSONArray.getJSONObject(i);
            stepType = stepRunInfo.getString("stepType");
            stepRunId = stepRunInfo.getString("stepRunId");
            stepName = stepRunInfo.getString("stepName");
            stepRunErrorInfo = jobAndStepInterface.getStepErrorInfo(param_fileName, stepRunId);
            keyName = parentStepName + "_" + (i + 1) + "_" + stepName + "_";
            //普通——NR
            if ("NR".equals(stepType)) {
                predictJSON.put(keyName + "info", stepRunInfo);
                predictJSON.put(keyName + "error", stepRunErrorInfo);
            }
            //分段——SD
            else if ("SD".equals(stepType)) {
                predictJSON.put(keyName + "段_info", stepRunInfo);
                predictJSON.put(keyName + "段_error", stepRunErrorInfo);
                //取出一条数据放入到结果中去，减少分段的信息返回
                splitStepRunInfo = jobAndStepInterface.getCurrentSplitRunStep(param_fileName, stepRunId);
                stepSegmentInfo = splitStepRunInfo.getJSONArray("data").getJSONObject(0);
                splitStepRunInfo.put("data", stepSegmentInfo);
                predictJSON.put(keyName + "段_list", splitStepRunInfo);
            }
            //分组——RF
            else if ("RF".equals(stepType)) {
                predictJSON.put(keyName + "组_info", stepRunInfo);
                predictJSON.put(keyName + "组_error", stepRunErrorInfo);
                String jobRunId = stepRunInfo.getString("refJobId");
                JSONObject stepGroupInfo = jobAndStepInterface.getCurrentRunStep(param_fileName, jobRunId);
                predictJSON.put(keyName + "组_list", stepGroupInfo);
                getStepRunList(stepGroupInfo, predictJSON, param_fileName, keyName + "组");
            }
        }
    }
}