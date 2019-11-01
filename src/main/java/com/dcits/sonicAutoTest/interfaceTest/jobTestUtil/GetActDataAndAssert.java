package com.dcits.sonicAutoTest.interfaceTest.jobTestUtil;

import com.dcits.sonicAutoTest.interfaceTest.preDataUtil.JobAndStepInterface;
import com.dcits.sonicAutoTest.util.SonicPropertyUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * 结果断言公共类
 */
public class GetActDataAndAssert {
    //日志打印
    private static final Logger logger = LoggerFactory.getLogger(GetActDataAndAssert.class);

    JobAndStepInterface jobAndStepInterface = JobAndStepInterface.getInstance();

    private volatile static GetActDataAndAssert getActDataAndAssert;

    private GetActDataAndAssert() {
    }

    public static GetActDataAndAssert getInstance() {
        if (null == getActDataAndAssert) {
            synchronized (GetActDataAndAssert.class) {
                if (null == getActDataAndAssert) {
                    getActDataAndAssert = new GetActDataAndAssert();
                }
            }
        }
        return getActDataAndAssert;
    }


    /**
     * 【1.JOB列表】
     *
     * @param preData
     * @param param_fileName
     * @return
     */
    public String getListAndAssert(JSONObject preData, String param_fileName) {
        JSONObject pre_getList = preData.getJSONObject("getList");
        JSONObject act_getList = jobAndStepInterface.getList(param_fileName);
        //校验
        Assert.assertEquals(pre_getList.getString("message"), act_getList.getString("message"), "接口返回的message信息不同！");
        Assert.assertEquals(pre_getList.getString("code"), act_getList.getString("code"), "接口返回的code信息不同！");
        Assert.assertEquals(pre_getList.getString("totalCount"), act_getList.getString("totalCount"), "接口返回的总条数不同！");
        JSONObject pre_data = getDataJSON(pre_getList);
        JSONObject act_data = getDataJSON(act_getList);
        Assert.assertEquals(pre_data.getString("jobId"), act_data.getString("jobId"), "jobId不同！");
        Assert.assertEquals(pre_data.getString("jobName"), act_data.getString("jobName"), "jobName不同！");
        Assert.assertEquals(pre_data.getString("jobDesc"), act_data.getString("jobDesc"), "jobDesc不同！");
        Assert.assertEquals(pre_data.getString("jobStatus"), act_data.getString("jobStatus"), "jobStatus不同！");
        Assert.assertEquals(pre_data.getString("jobType"), act_data.getString("jobType"), "jobType不同！");
        Assert.assertEquals(pre_data.getString("profile"), act_data.getString("profile"), "profile不同！");
        Assert.assertEquals(pre_data.getString("tenantId"), act_data.getString("tenantId"), "tenantId不同！");
        Assert.assertEquals(pre_data.getString("cronExpression"), act_data.getString("cronExpression"), "cronExpression不同！");
        logger.info("JOB列表校验通过！");
        String jobId = jobAndStepInterface.getJobInfo(act_getList, "jobId");
        return jobId;
    }

    /**
     * 【2.JOB详细信息】
     *
     * @param preData
     * @param param_fileName
     * @param jobId
     */
    public void getChainAndAssert(JSONObject preData, String param_fileName, String jobId) {
        JSONObject pre_getChain = preData.getJSONObject("getChain");
        JSONObject act_getChain = jobAndStepInterface.getChain(param_fileName, jobId);
        //校验
        Assert.assertEquals(pre_getChain.getString("message"), act_getChain.getString("message"), "接口返回的message信息不同！");
        Assert.assertEquals(pre_getChain.getString("code"), act_getChain.getString("code"), "接口返回的code信息不同！");
        JSONObject pre_data = pre_getChain.getJSONObject("data");
        JSONObject act_data = act_getChain.getJSONObject("data");
        Assert.assertEquals(pre_data.getString("jobId"), act_data.getString("jobId"), "jobId不同！");
        Assert.assertEquals(pre_data.getString("jobName"), act_data.getString("jobName"), "jobName不同！");
        Assert.assertEquals(pre_data.getString("jobDesc"), act_data.getString("jobDesc"), "jobDesc不同！");
        logger.info("JOB详细信息校验通过！");

    }

    /**
     * 【3.1——JOB指令】
     *
     * @param preData
     * @param param_fileName
     * @param jobId
     * @param isTimedJob
     * @return
     */
    public Map<String, Object> jobCommandAndAssert(JSONObject preData, String param_fileName, String jobId, String isTimedJob) {
        String doJobStatus = "";
        JSONObject pre_jobCommand = preData.getJSONObject("jobCommand");
        String jobRunId = jobAndStepInterface.getJobRunId(param_fileName, jobId);
        JSONObject act_jobCommand = jobAndStepInterface.getJobCommand(param_fileName, jobId, jobRunId, isTimedJob);
        //终止定时任务JOb
        long startJobTimeFlag = act_jobCommand.getLong("startJobTimeFlag");
        long endJobTimeFlag = System.currentTimeMillis();
        Map<String, Object> timeMap = new HashMap<>();
        timeMap.put("startJobTimeFlag", startJobTimeFlag);
        timeMap.put("endJobTimeFlag", endJobTimeFlag);
        timeMap.put("isTimedJob", isTimedJob);
        if (Boolean.valueOf(isTimedJob)) {
            doJobStatus = "stop";
            //获取预言值
            JSONObject pre_timeJobStopResult = preData.getJSONObject("timeJobStopResult");
            JSONObject act_timeJobStopResult = null;
            act_timeJobStopResult = stopTimeJob(param_fileName, jobId, jobRunId, doJobStatus);
            timeMap.put("endJobTimeFlag", act_timeJobStopResult.getString("endJobTimeFlag"));
            //停止操作也要进行断言判断
            Assert.assertEquals(pre_timeJobStopResult.getString("message"), act_timeJobStopResult.getString("message"), "接口返回的message信息不同！");
            Assert.assertEquals(pre_timeJobStopResult.getString("code"), act_timeJobStopResult.getString("code"), "接口返回的code信息不同！");
            //instruction、jobId、startType
            jobCommandAssert(pre_timeJobStopResult, act_timeJobStopResult);
        }
        Assert.assertEquals(pre_jobCommand.getString("message"), act_jobCommand.getString("message"), "接口返回的message信息不同！");
        Assert.assertEquals(pre_jobCommand.getString("code"), act_jobCommand.getString("code"), "接口返回的code信息不同！");
        //instruction、jobId、startType
        if (!"restart".equals(SonicPropertyUtil.getProperty("sonic.job.instruction", param_fileName))) {
            jobCommandAssert(pre_jobCommand, act_jobCommand);
        }
        logger.info("JOB指令校验通过！");
        return timeMap;
    }

    /**
     * 【3.2——JOB指令】
     *
     * @param pre_value
     * @param act_value
     */
    public void jobCommandAssert(JSONObject pre_value, JSONObject act_value) {
        JSONObject pre_data = pre_value.getJSONObject("data");
        JSONObject act_data = act_value.getJSONObject("data");
        String pre_instruction = "";
        String pre_startType = "";
        String acu_instruction = "";
        String acu_pre_startType = "";
        if (pre_data.size() > 0 && act_data.size() > 0) {
            if ("[]".equals(pre_data.getString("startType")) && StringUtils.isBlank(act_data.getString("startType"))) {
                //涉及到从xm;读取的数据和实际接口返回来的数据内容其实是一样，格式有区别，在此做区分，后期别处有问题，同样处理
                pre_data.put("startType", "");
                act_data.put("startType", "");
            } else {
                pre_startType = pre_data.getString("startType");
            }
            pre_instruction = pre_data.getString("instruction");
            acu_instruction = act_data.getString("instruction");
            acu_pre_startType = act_data.getString("startType");
        }
        Assert.assertEquals(pre_instruction, acu_instruction, "instruction不同！");
        Assert.assertEquals(pre_startType, acu_pre_startType, "startType不同！");
    }

    /**
     * 【3.3——JOB指令】
     *
     * @param param_fileName
     * @param jobId
     * @param jobRunId
     * @param doJobStatus
     * @return
     */
    public JSONObject stopTimeJob(String param_fileName, String jobId, String jobRunId, String doJobStatus) {
        logger.info("定时任务运行中...");
        try {
            Thread.sleep(Long.parseLong(SonicPropertyUtil.getProperty("sonic.job.timelength", param_fileName)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONObject timeJobStopResult = JobAndStepInterface.getInstance().jobCommand(param_fileName, jobId, jobRunId, doJobStatus);
        timeJobStopResult.put("endJobTimeFlag", System.currentTimeMillis());
        logger.info("定时任务已停止");
        return timeJobStopResult;
    }

    /**
     * 【4.JOB运行列表】
     *
     * @param preData
     * @param param_fileName
     * @param jobId
     * @return
     */
    public String getCurrentRunJobAndAssert(JSONObject preData, String param_fileName, String jobId) {
        JSONObject pre_getCurrentRunJob = preData.getJSONObject("getCurrentRunJob");
        JSONObject act_getCurrentRunJob = jobAndStepInterface.getCurrentRunJob(param_fileName, jobId);
        Assert.assertEquals(pre_getCurrentRunJob.getString("message"), act_getCurrentRunJob.getString("message"), "接口返回的message信息不同！");
        Assert.assertEquals(pre_getCurrentRunJob.getString("code"), act_getCurrentRunJob.getString("code"), "接口返回的code信息不同！");
        Assert.assertEquals(pre_getCurrentRunJob.getString("totalCount"), act_getCurrentRunJob.getString("totalCount"), "接口返回的总条数不同！");
        JSONObject pre_data = getDataJSON(pre_getCurrentRunJob);
        JSONObject act_data = getDataJSON(act_getCurrentRunJob);
        Assert.assertEquals(pre_data.getString("jobId"), act_data.getString("jobId"), "jobId不同！");
        Assert.assertEquals(pre_data.getString("jobName"), act_data.getString("jobName"), "jobName不同！");
        Assert.assertEquals(pre_data.getString("jobDesc"), act_data.getString("jobDesc"), "jobDesc不同！");
        Assert.assertEquals(pre_data.getString("failCnt"), act_data.getString("failCnt"), "失败数不同！");
        Assert.assertEquals(pre_data.getString("finishedCnt"), act_data.getString("finishedCnt"), "成功数不同！");
        Assert.assertEquals(pre_data.getString("totalCnt"), act_data.getString("totalCnt"), "总数不同！");
        Assert.assertEquals(pre_data.getString("jobStatus"), act_data.getString("jobStatus"), "jobStatus不同！");
        Assert.assertEquals(pre_data.getString("jobType"), act_data.getString("jobType"), "jobType不同！");
        Assert.assertEquals(pre_data.getString("profile"), act_data.getString("profile"), "profile不同！");
        Assert.assertEquals(pre_data.getString("tenantId"), act_data.getString("tenantId"), "tenantId不同！");
        Assert.assertEquals(pre_data.getString("cronExpression"), act_data.getString("cronExpression"), "cronExpression不同！");
        logger.info("JOB运行列表校验通过！");
        String jobRunId = jobAndStepInterface.getJobInfo(act_getCurrentRunJob, "jobRunId");
        return jobRunId;
    }

    /**
     * 【5.JOB运行详情】
     *
     * @param preData
     * @param param_fileName
     * @param jobId
     * @param jobRunId
     */
    public void getRunChainAndAssert(JSONObject preData, String param_fileName, String jobId, String jobRunId) {
        JSONObject pre_getRunChain = preData.getJSONObject("getRunChain");
        JSONObject act_getRunChain = jobAndStepInterface.getRunChain(param_fileName, jobId, jobRunId);
        Assert.assertEquals(pre_getRunChain.getString("message"), act_getRunChain.getString("message"), "接口返回的message信息不同！");
        Assert.assertEquals(pre_getRunChain.getString("code"), act_getRunChain.getString("code"), "接口返回的code信息不同！");
        JSONObject pre_data = pre_getRunChain.getJSONObject("data");
        JSONObject act_data = act_getRunChain.getJSONObject("data");
        Assert.assertEquals(pre_data.getString("jobId"), act_data.getString("jobId"), "jobId不同！");
        Assert.assertEquals(pre_data.getString("jobName"), act_data.getString("jobName"), "jobName不同！");
        Assert.assertEquals(pre_data.getString("jobDesc"), act_data.getString("jobDesc"), "jobDesc不同！");
        logger.info("JOB运行明细校验通过！");
    }

    /**
     * 【6.1——STEP运行列表】
     *
     * @param preData
     * @param param_fileName
     * @param jobRunId
     */
    public void getCurrentRunStepAndAssert(JSONObject preData, String param_fileName, String jobRunId) {
        JSONObject pre_getCurrentRunStep = preData.getJSONObject("getCurrentRunStep");
        JSONObject act_getCurrentRunStep = jobAndStepInterface.getCurrentRunStep(param_fileName, jobRunId);
        Assert.assertEquals(pre_getCurrentRunStep.getString("message"), act_getCurrentRunStep.getString("message"), "接口返回的message信息不同！");
        Assert.assertEquals(pre_getCurrentRunStep.getString("code"), act_getCurrentRunStep.getString("code"), "接口返回的code信息不同！");
        Assert.assertEquals(pre_getCurrentRunStep.getString("totalCount"), act_getCurrentRunStep.getString("totalCount"), "接口返回的总条数不同！");
        stepTotalCountAssert(pre_getCurrentRunStep, act_getCurrentRunStep);
        //递归获取STEP运行列表并校验
        getStepRunListAndAssert(preData, act_getCurrentRunStep, param_fileName, "step");
        logger.info("第一层STEP运行列表校验通过！");
    }

    /**
     * 【6.2——递归获取STEP运行列表】
     *
     * @param preData
     * @param act_getCurrentRunStep
     * @param param_fileName
     * @param parentStepName
     */
    private void getStepRunListAndAssert(JSONObject preData, JSONObject act_getCurrentRunStep, String param_fileName, String parentStepName) {
        JSONArray stepJSONArray = act_getCurrentRunStep.getJSONArray("data");
        JSONObject stepRunInfo = null;
        JSONObject stepRunErrorInfo = null;
        JSONObject splitStepRunInfo = null;
        JSONObject stepSegmentInfo = null;
        JSONObject preData_info;
        JSONObject preData_error;
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
                preData_info = preData.getJSONObject(keyName + "info");
                preData_error = preData.getJSONObject(keyName + "error");
                stepInfoAssert(preData_info, stepRunInfo, "");
                stepErrorAssert(preData_error, stepRunErrorInfo);
            }
            //分段——SD
            else if ("SD".equals(stepType)) {
                preData_info = preData.getJSONObject(keyName + "段_info");
                preData_error = preData.getJSONObject(keyName + "段_error");
                stepInfoAssert(preData_info, stepRunInfo, "");
                stepErrorAssert(preData_error, stepRunErrorInfo);
                //取出一条数据放入到结果中去，减少分段的信息返回
                splitStepRunInfo = jobAndStepInterface.getCurrentSplitRunStep(param_fileName, stepRunId);
                stepSegmentInfo = splitStepRunInfo.getJSONArray("data").getJSONObject(0);
                splitStepRunInfo.put("data", stepSegmentInfo);
                preData_info = preData.getJSONObject(keyName + "段_list");
                stepInfoAssert(preData_info, splitStepRunInfo, "SD");
            }
            //分组——RF
            else if ("RF".equals(stepType)) {
                preData_info = preData.getJSONObject(keyName + "组_info");
                preData_error = preData.getJSONObject(keyName + "组_error");
                stepInfoAssert(preData_info, stepRunInfo, "");
                stepErrorAssert(preData_error, stepRunErrorInfo);
                String jobRunId = stepRunInfo.getString("refJobId");
                JSONObject stepGroupInfo = jobAndStepInterface.getCurrentRunStep(param_fileName, jobRunId);
                preData_info = preData.getJSONObject(keyName + "组_list");
                stepInfoAssert(preData_info, stepGroupInfo, "RF");
                getStepRunListAndAssert(preData, stepGroupInfo, param_fileName, keyName + "组");
            }
        }
    }

    /**
     * 【6.3——STEP信息】
     *
     * @param preData_info
     * @param stepRunInfo
     * @param stepType
     */
    public void stepInfoAssert(JSONObject preData_info, JSONObject stepRunInfo, String stepType) {
        if (!"".equals(stepType) && null != stepType && ("RF".equals(stepType) || "SD".equals(stepType))) {
            Assert.assertEquals(preData_info.getString("message"), stepRunInfo.getString("message"), "接口返回的message信息不同！");
            Assert.assertEquals(preData_info.getString("code"), stepRunInfo.getString("code"), "接口返回的code信息不同！");
            Assert.assertEquals(preData_info.getString("totalCount"), stepRunInfo.getString("totalCount"), "接口返回的总条数不同！");
        } else {
            Assert.assertEquals(preData_info.getString("appId"), stepRunInfo.getString("appId"), "appId返回值有误");
            Assert.assertEquals(preData_info.getString("tenantId"), stepRunInfo.getString("tenantId"), "tenantId返回值有误");
            Assert.assertEquals(preData_info.getString("stepType"), stepRunInfo.getString("stepType"), "stepType返回值有误");
            Assert.assertEquals(preData_info.getString("stepStatus"), stepRunInfo.getString("stepStatus"), "stepStatus返回值有误");
            Assert.assertEquals(preData_info.getString("stepName"), stepRunInfo.getString("stepName"), "stepName返回值有误");
            Assert.assertEquals(preData_info.getString("splitCount"), stepRunInfo.getString("splitCount"), "splitCount返回值有误");
            Assert.assertEquals(preData_info.getString("splitClazzName"), stepRunInfo.getString("splitClazzName"), "splitClazzName返回值有误");
            Assert.assertEquals(preData_info.getString("clazzName"), stepRunInfo.getString("clazzName"), "clazzName返回值有误");
        }
    }

    /**
     * 【6.4——STEP错误信息】
     *
     * @param preData_error
     * @param stepRunErrorInfo
     */
    public void stepErrorAssert(JSONObject preData_error, JSONObject stepRunErrorInfo) {
        Assert.assertEquals(preData_error.getString("message"), stepRunErrorInfo.getString("message"), "接口返回的message信息不同！");
        Assert.assertEquals(preData_error.getString("code"), stepRunErrorInfo.getString("code"), "接口返回的code信息不同！");
    }

    /**
     * 【7.JOB历史信息】
     *
     * @param preData
     * @param param_fileName
     * @param jobId
     * @param commanMap
     */
    public void getHistRunJobAndAssert(JSONObject preData, String param_fileName, String jobId, Map<String, Object> commanMap) {
        JSONObject pre_getHistRunJob = preData.getJSONObject("getHistRunJob");
        String startJobTimeFlag = String.valueOf(commanMap.get("startJobTimeFlag"));
        String endJobTimeFlag = String.valueOf(commanMap.get("endJobTimeFlag"));
        //获取实际值，定时和非定时有区分
        JSONObject act_getHistRunJob = JobAndStepInterface.getInstance().getHistRunJob(param_fileName, jobId, startJobTimeFlag, endJobTimeFlag, String.valueOf(commanMap.get("isTimedJob")));
        //定时任务，判断执行次数
        if (Boolean.valueOf(String.valueOf(commanMap.get("isTimedJob")))) {
            pre_getHistRunJob = preData.getJSONObject("getHistRunJobHaveLimitTime");
            Assert.assertEquals(pre_getHistRunJob.getString("totalCount"), act_getHistRunJob.getString("totalCount"), "定时任务执行次数不对等！");
        } else {
            Assert.assertEquals(pre_getHistRunJob.getString("totalCount"), act_getHistRunJob.getString("totalCount"), "接口返回的总条数不同！");

        }
        Assert.assertEquals(pre_getHistRunJob.getString("message"), act_getHistRunJob.getString("message"), "接口返回的message信息不同！");
        Assert.assertEquals(pre_getHistRunJob.getString("code"), act_getHistRunJob.getString("code"), "接口返回的code信息不同！");

    }


    /**
     * STEP数量校验
     *
     * @param pre_value
     * @param act_value
     * @return
     */
    public void stepTotalCountAssert(JSONObject pre_value, JSONObject act_value) {
        Map<String, String> pre_map = new HashedMap(10);
        pre_map = getStepCnt(pre_value);
        String pre_finishedCnt = pre_map.get("finishedCnt");
        String pre_failedCnt = pre_map.get("failedCnt");
        Map<String, String> act_map = new HashedMap(10);
        act_map = getStepCnt(act_value);
        String act_finishedCnt = act_map.get("finishedCnt");
        String act_failedCnt = act_map.get("failedCnt");
        Assert.assertEquals(pre_finishedCnt, act_finishedCnt, "STEP完成数不同！");
        Assert.assertEquals(pre_failedCnt, act_failedCnt, "STEP失败数不同！");
    }

    /**
     * 统计STEP数
     *
     * @param resultJSON
     * @return
     */
    public Map<String, String> getStepCnt(JSONObject resultJSON) {
        JSONArray jsonArray = resultJSON.getJSONArray("data");
        Map<String, String> map = new HashedMap();
        Integer finishedCnt = 0;
        Integer failedCnt = 0;
        String stepStatus = "";
        JSONObject jsonObject = null;
        for (int i = 0; i < jsonArray.size(); i++) {
            jsonObject = jsonArray.getJSONObject(i);
            stepStatus = jsonObject.getString("stepStatus");

            if ("C".equals(stepStatus)) {
                finishedCnt++;//完成数统计
            } else if ("F".equals(stepStatus)) {
                failedCnt++;//失败数统计
            }
        }
        map.put("finishedCnt", finishedCnt.toString());
        map.put("failedCnt", failedCnt.toString());
        return map;
    }

    /**
     * 获取data的JSON对象
     *
     * @param resultJSON
     * @return
     */
    private JSONObject getDataJSON(JSONObject resultJSON) {
        JSONArray jsonArray = resultJSON.getJSONArray("data");
        JSONObject dataJSON = jsonArray.getJSONObject(0);
        return dataJSON;
    }
}
