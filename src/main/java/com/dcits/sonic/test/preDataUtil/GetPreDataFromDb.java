package com.dcits.sonic.test.preDataUtil;

import com.dcits.sonic.test.createJsonFile.GetPreDataToJsonArray;
import com.dcits.sonic.test.jobTestUtil.GetActDataAndAssert;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

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
     */
    @Parameters({"pre_fileName", "jobName", "instruction", "hasCron", "timelength"})
    @Test
    public void getPreDataFromDb(String pre_fileName, String jobName, String instruction, String hasCron, String timelength) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("jobName", jobName);
        paramMap.put("instruction", instruction);
        paramMap.put("hasCron", hasCron);
        paramMap.put("timelength", timelength);

        //总的预言值
        JSONObject predictJSON = new JSONObject();
        //1.JOB列表
        JSONObject getList = jobAndStepInterface.getList(paramMap);
        paramMap.put("jobId", jobAndStepInterface.getJobInfo(getList, "jobId"));
        //2.JOB定义详情
        JSONObject getChain = jobAndStepInterface.getChain(paramMap);
        //先判斷當前任務狀態
        JSONObject act_getList =  jobAndStepInterface.getCurrentRunJob(paramMap);
        //當前任務執行失敗，則應該為回復
        if (act_getList != null && "F".equals(act_getList.getJSONArray("data").getJSONObject(0).getString("jobStatus"))){
            paramMap.put("instruction","restart");
        }
        //3.JOB指令
        paramMap.put("jobRunId", jobAndStepInterface.getJobRunId(paramMap));
        paramMap.put("timedJobStatus", "");
        JSONObject jobCommand = jobAndStepInterface.getJobCommand(paramMap);
        //判断定时任务
        long start = jobCommand.getLong("startJobTimeFlag");
        long end = System.currentTimeMillis();
        if ("1".equals(paramMap.get("hasCron"))) {
            paramMap.put("instruction", "stop");
            JSONObject timeJobStopResult = doJobTime(paramMap);
            end = Long.valueOf(String.valueOf(timeJobStopResult.get("endJobTimeFlag")));
            predictJSON.put("timeJobStopResult", timeJobStopResult);
        }
        //轮询JOB，直到JOB运行完成
        JobAndStepInterface.getInstance().waitGetCurrentRunJob(paramMap);
        //4.JOB运行列表
        JSONObject getCurrentRunJob = jobAndStepInterface.getCurrentRunJob(paramMap);
        paramMap.put("jobRunId", jobAndStepInterface.getJobInfo(getCurrentRunJob, "jobRunId"));
        //5.JOB运行详情
        JSONObject getRunChain = jobAndStepInterface.getRunChain(paramMap);
        //6.递归获取STEP运行列表
        JSONObject getCurrentRunStep = jobAndStepInterface.getCurrentRunStep(paramMap);
        getStepRunList(getCurrentRunStep, predictJSON, paramMap, "step");
        //历史列表查询执行次数,依据定时任务执行次数
        if ("1".equals(paramMap.get("hasCron"))) {
            paramMap.put("job_startTime", String.valueOf(start));
            paramMap.put("job_endTime", String.valueOf(end));
            //定时任务执行历史列表返回值
            JSONObject getHistRunJob = JobAndStepInterface.getInstance().getHistRunJob(paramMap);
            if (null != getHistRunJob) {
                predictJSON.put("getHistRunJobHaveLimitTime", getHistRunJob);
            }
        }
        predictJSON.put("getList", getList);
        predictJSON.put("getChain", getChain);
        predictJSON.put("jobCommand", jobCommand);
        predictJSON.put("getCurrentRunJob", getCurrentRunJob);
        predictJSON.put("getRunChain", getRunChain);
        predictJSON.put("getCurrentRunStep", getCurrentRunStep);
        //将预言值存储至xml文件中paramMap
        SavePreDataToXml.savePreDataToXml(predictJSON, pre_fileName);
        //生成JSONArray格式的预言值
        GetPreDataToJsonArray.getInstance().getPreDataToJsonArray(predictJSON, pre_fileName);
        logger.info("==预言值存储完成==");
    }

    /**
     * 用于判断定时任务时间,以及获取结果值
     */
    public JSONObject doJobTime(Map<String, String> paramMap) {
        try {
            logger.info("定时任务运行中...");
            Thread.sleep(Long.parseLong(paramMap.get("timelength")));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //停止定时任务
        JSONObject timeJobStopResult = JobAndStepInterface.getInstance().jobCommand(paramMap);
        timeJobStopResult.remove("startJobTimeFlag");
        timeJobStopResult.put("endJobTimeFlag", System.currentTimeMillis());
        logger.info("定时任务已停止");
        return timeJobStopResult;
    }

    /**
     * 递归获取STEP运行列表
     */
    private void getStepRunList(JSONObject getCurrentRunStep, JSONObject predictJSON, Map<String, String> paramMap, String parentStepName) {
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
            paramMap.put("stepRunId", stepRunId);
            stepName = stepRunInfo.getString("stepName");
            stepRunErrorInfo = jobAndStepInterface.getStepErrorInfo(paramMap);
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
                splitStepRunInfo = jobAndStepInterface.getCurrentSplitRunStep(paramMap);
                //统计成功个数，失败个数，以及总个数
                Map<String,String> splitMap = GetActDataAndAssert.getInstance().getStepCnt(splitStepRunInfo);
                splitStepRunInfo.put("allStepsCnt",splitMap.get("allStepsCnt"));
                splitStepRunInfo.put("finishedCnt",splitMap.get("finishedCnt"));
                splitStepRunInfo.put("failedCnt",splitMap.get("failedCnt"));
                JSONArray dataArray = splitStepRunInfo.getJSONArray("data");
                if(dataArray.size()>0){
                    stepSegmentInfo = dataArray.getJSONObject(0);
                }
                splitStepRunInfo.put("data", stepSegmentInfo);
                predictJSON.put(keyName + "段_list", splitStepRunInfo);
            }
            //分组——RF
            else if ("RF".equals(stepType)) {
                predictJSON.put(keyName + "组_info", stepRunInfo);
                predictJSON.put(keyName + "组_error", stepRunErrorInfo);
                String jobRunId = stepRunInfo.getString("refJobId");
                paramMap.put("jobRunId", jobRunId);
                JSONObject stepGroupInfo = jobAndStepInterface.getCurrentRunStep(paramMap);
                predictJSON.put(keyName + "组_list", stepGroupInfo);
                getStepRunList(stepGroupInfo, predictJSON, paramMap, keyName + "组");
            }
        }
    }
}