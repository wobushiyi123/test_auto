package com.dcits.sonic.test.jobTestUtil;

import com.dcits.sonic.test.preDataUtil.JobAndStepInterface;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.util.Comparator;
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
     * @param paramMap
     * @return
     */
    public String getListAndAssert(JSONObject preData, Map<String, String> paramMap) {
        JSONObject pre_getList = preData.getJSONObject("getList");
        JSONObject act_getList = jobAndStepInterface.getList(paramMap);
        //校验
        Assert.assertEquals(act_getList.getString("message"), pre_getList.getString("message"), "接口返回的message信息不同！");
        Assert.assertEquals(act_getList.getString("code"), pre_getList.getString("code"), "接口返回的code信息不同！");
        Assert.assertEquals(act_getList.getString("totalCount"), pre_getList.getString("totalCount"), "接口返回的总条数不同！");
        JSONObject pre_data = getDataJSON(pre_getList);
        JSONObject act_data = getDataJSON(act_getList);
        Assert.assertEquals(act_data.getString("jobId"), pre_data.getString("jobId"), "jobId不同！");
        Assert.assertEquals(act_data.getString("jobName"), pre_data.getString("jobName"), "jobName不同！");
        Assert.assertEquals(act_data.getString("jobDesc"), pre_data.getString("jobDesc"), "jobDesc不同！");
        Assert.assertEquals(act_data.getString("jobStatus"), pre_data.getString("jobStatus"), "jobStatus不同！");
        Assert.assertEquals(act_data.getString("jobType"), pre_data.getString("jobType"), "jobType不同！");
        Assert.assertEquals(act_data.getString("profile"), pre_data.getString("profile"), "profile不同！");
        Assert.assertEquals(act_data.getString("tenantId"), pre_data.getString("tenantId"), "tenantId不同！");
        Assert.assertEquals(act_data.getString("cronExpression"), pre_data.getString("cronExpression"), "cronExpression不同！");
        logger.info("JOB列表校验通过！");
        String jobId = jobAndStepInterface.getJobInfo(act_getList, "jobId");
        return jobId;
    }

    /**
     * 【2.JOB详细信息】
     */
    public void getChainAndAssert(JSONObject preData, Map<String, String> paramMap) {
        JSONObject pre_getChain = preData.getJSONObject("getChain");
        JSONObject act_getChain = jobAndStepInterface.getChain(paramMap);
        //校验
        Assert.assertEquals(act_getChain.getString("message"), pre_getChain.getString("message"), "接口返回的message信息不同！");
        Assert.assertEquals(act_getChain.getString("code"), pre_getChain.getString("code"), "接口返回的code信息不同！");
        JSONObject pre_data = pre_getChain.getJSONObject("data");
        JSONObject act_data = act_getChain.getJSONObject("data");
        Assert.assertEquals(act_data.getString("jobId"), pre_data.getString("jobId"), "jobId不同！");
        Assert.assertEquals(act_data.getString("jobName"), pre_data.getString("jobName"), "jobName不同！");
        Assert.assertEquals(act_data.getString("jobDesc"), pre_data.getString("jobDesc"), "jobDesc不同！");
        //对data的step信息进行校验,新增
        JSONArray pre_jsonArray = act_getChain.getJSONObject("data").getJSONArray("steps");
        JSONArray acu_jsonArray = act_getChain.getJSONObject("data").getJSONArray("steps");
        //排序处理，针对预言值和实际值
        pre_jsonArray.sort(Comparator.comparing(obj -> ((JSONObject) obj).getString("stepName")));
        pre_jsonArray.sort(Comparator.comparing(obj -> ((JSONObject) obj).getString("nodeId")));
        acu_jsonArray.sort(Comparator.comparing(obj -> ((JSONObject) obj).getString("stepName")));
        acu_jsonArray.sort(Comparator.comparing(obj -> ((JSONObject) obj).getString("nodeId")));
        for (int i = 0; i < acu_jsonArray.size(); i++) {
            JSONObject acu_jsonObject = acu_jsonArray.getJSONObject(i);
            JSONObject pre_jsonObject = pre_jsonArray.getJSONObject(i);
            Assert.assertEquals(acu_jsonObject.getString("stepId"), pre_jsonObject.getString("stepId"), "stepId不同！");
            Assert.assertEquals(acu_jsonObject.getString("stepName"), pre_jsonObject.getString("stepName"), "stepName不同！");
            Assert.assertEquals(acu_jsonObject.getString("stepDesc"), pre_jsonObject.getString("stepDesc"), "stepDesc不同！");
            Assert.assertEquals(acu_jsonObject.getString("nodeId"), pre_jsonObject.getString("nodeId"), "nodeId不同！");
        }
        logger.info("JOB详细信息校验通过！");

    }

    /**
     * 【3.1——JOB指令】
     */
    public void jobCommandAndAssert(JSONObject preData, Map<String, String> paramMap) {
        JSONObject act_getList =  jobAndStepInterface.getCurrentRunJob(paramMap);
        //當前任務執行失敗，則應該為回復
        if (act_getList != null && "F".equals(act_getList.getJSONArray("data").getJSONObject(0).getString("jobStatus"))){
            paramMap.put("instruction","restart");
        }
        JSONObject pre_jobCommand = preData.getJSONObject("jobCommand");
        String jobRunId = jobAndStepInterface.getJobRunId(paramMap);
        paramMap.put("jobRunId", jobRunId);
        JSONObject act_jobCommand = jobAndStepInterface.getJobCommand(paramMap);
        //终止定时任务JOb
        long startJobTimeFlag = act_jobCommand.getLong("startJobTimeFlag");
        long endJobTimeFlag = System.currentTimeMillis();
        paramMap.put("job_startTime", String.valueOf(startJobTimeFlag));
        paramMap.put("job_endTime", String.valueOf(endJobTimeFlag));
        if ("1".equals(paramMap.get("hasCron"))) {
            paramMap.put("instruction", "stop");
            //获取预言值
            JSONObject pre_timeJobStopResult = preData.getJSONObject("timeJobStopResult");
            JSONObject act_timeJobStopResult = null;
            act_timeJobStopResult = stopTimeJob(paramMap);
            paramMap.put("job_endTime", act_timeJobStopResult.getString("endJobTimeFlag"));
            //停止操作也要进行断言判断
            Assert.assertEquals(act_timeJobStopResult.getString("message"), pre_timeJobStopResult.getString("message"), "接口返回的message信息不同！");
            Assert.assertEquals(act_timeJobStopResult.getString("code"), pre_timeJobStopResult.getString("code"), "接口返回的code信息不同！");
            //instruction、jobId、startType
            jobCommandAssert(pre_timeJobStopResult, act_timeJobStopResult);
        }
        Assert.assertEquals(act_jobCommand.getString("message"), pre_jobCommand.getString("message"), "接口返回的message信息不同！");
        Assert.assertEquals(act_jobCommand.getString("code"), pre_jobCommand.getString("code"), "接口返回的code信息不同！");
        //instruction、jobId、startType
        if (!"restart".equals(paramMap.get("instruction"))) {
            jobCommandAssert(pre_jobCommand, act_jobCommand);
        }
        logger.info("JOB指令校验通过！");
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
        String acu_startType = "";
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
            acu_startType = act_data.getString("startType");
        }
        Assert.assertEquals(acu_instruction, pre_instruction, "instruction不同！");
        Assert.assertEquals(acu_startType, pre_startType, "startType不同！");
    }

    /**
     * 【3.3——JOB指令】
     *
     * @return
     */
    public JSONObject stopTimeJob(Map<String, String> paramMap) {
        logger.info("定时任务运行中...");
        try {
            Thread.sleep(Long.parseLong(paramMap.get("timelength")));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JSONObject timeJobStopResult = JobAndStepInterface.getInstance().jobCommand(paramMap);
        timeJobStopResult.put("endJobTimeFlag", System.currentTimeMillis());
        logger.info("定时任务已停止");
        return timeJobStopResult;
    }

    /**
     * 【4.JOB运行列表】
     */
    public String getCurrentRunJobAndAssert(JSONObject preData, Map<String, String> paramMap) {
        JSONObject pre_getCurrentRunJob = preData.getJSONObject("getCurrentRunJob");
        JSONObject act_getCurrentRunJob = jobAndStepInterface.getCurrentRunJob(paramMap);
        Assert.assertEquals(act_getCurrentRunJob.getString("message"), pre_getCurrentRunJob.getString("message"), "接口返回的message信息不同！");
        Assert.assertEquals(act_getCurrentRunJob.getString("code"), pre_getCurrentRunJob.getString("code"), "接口返回的code信息不同！");
        Assert.assertEquals(act_getCurrentRunJob.getString("totalCount"), pre_getCurrentRunJob.getString("totalCount"), "接口返回的总条数不同！");
        JSONObject pre_data = getDataJSON(pre_getCurrentRunJob);
        JSONObject act_data = getDataJSON(act_getCurrentRunJob);
        Assert.assertEquals(act_data.getString("jobId"), pre_data.getString("jobId"), "jobId不同！");
        Assert.assertEquals(act_data.getString("jobName"), pre_data.getString("jobName"), "jobName不同！");
        Assert.assertEquals(act_data.getString("jobDesc"), pre_data.getString("jobDesc"), "jobDesc不同！");
        Assert.assertEquals(act_data.getString("failCnt"), pre_data.getString("failCnt"), "失败数不同！");
        Assert.assertEquals(act_data.getString("finishedCnt"), pre_data.getString("finishedCnt"), "成功数不同！");
        Assert.assertEquals(act_data.getString("totalCnt"), pre_data.getString("totalCnt"), "总数不同！");
        Assert.assertEquals(act_data.getString("jobStatus"), pre_data.getString("jobStatus"), "jobStatus不同！");
        Assert.assertEquals(act_data.getString("jobType"), pre_data.getString("jobType"), "jobType不同！");
        Assert.assertEquals(act_data.getString("profile"), pre_data.getString("profile"), "profile不同！");
        Assert.assertEquals(act_data.getString("tenantId"), pre_data.getString("tenantId"), "tenantId不同！");
        Assert.assertEquals(act_data.getString("cronExpression"), pre_data.getString("cronExpression"), "cronExpression不同！");
        logger.info("JOB运行列表校验通过！");
        String jobRunId = jobAndStepInterface.getJobInfo(act_getCurrentRunJob, "jobRunId");
        return jobRunId;
    }

    /**
     * 【5.JOB运行详情】
     */
    public void getRunChainAndAssert(JSONObject preData, Map<String, String> paramMap) {
        JSONObject pre_getRunChain = preData.getJSONObject("getRunChain");
        JSONObject act_getRunChain = jobAndStepInterface.getRunChain(paramMap);
        Assert.assertEquals(act_getRunChain.getString("message"), pre_getRunChain.getString("message"), "接口返回的message信息不同！");
        Assert.assertEquals(act_getRunChain.getString("code"), pre_getRunChain.getString("code"), "接口返回的code信息不同！");
        JSONObject pre_data = pre_getRunChain.getJSONObject("data");
        JSONObject act_data = act_getRunChain.getJSONObject("data");
        Assert.assertEquals(act_data.getString("jobId"), pre_data.getString("jobId"), "jobId不同！");
        Assert.assertEquals(act_data.getString("jobName"), pre_data.getString("jobName"), "jobName不同！");
        Assert.assertEquals(act_data.getString("jobDesc"), pre_data.getString("jobDesc"), "jobDesc不同！");
        logger.info("JOB运行明细校验通过！");
    }

    /**
     * 【6.1——STEP运行列表】
     */
    public void getCurrentRunStepAndAssert(JSONObject preData, Map<String, String> paramMap) {
        JSONObject pre_getCurrentRunStep = preData.getJSONObject("getCurrentRunStep");
        JSONObject act_getCurrentRunStep = jobAndStepInterface.getCurrentRunStep(paramMap);
        Assert.assertEquals(act_getCurrentRunStep.getString("message"), pre_getCurrentRunStep.getString("message"), "接口返回的message信息不同！");
        Assert.assertEquals(act_getCurrentRunStep.getString("code"), pre_getCurrentRunStep.getString("code"), "接口返回的code信息不同！");
        Assert.assertEquals(act_getCurrentRunStep.getString("totalCount"), pre_getCurrentRunStep.getString("totalCount"), "接口返回的总条数不同！");
        stepTotalCountAssert(pre_getCurrentRunStep, act_getCurrentRunStep);
        //递归获取STEP运行列表并校验
        getStepRunListAndAssert(preData, act_getCurrentRunStep, paramMap, "step");
        logger.info("第一层STEP运行列表校验通过！");
    }

    /**
     * 【6.2——递归获取STEP运行列表】
     *
     * @param preData
     * @param act_getCurrentRunStep
     * @param paramMap
     * @param parentStepName
     */
    private void getStepRunListAndAssert(JSONObject preData, JSONObject act_getCurrentRunStep, Map<String, String> paramMap, String parentStepName) {
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
            paramMap.put("stepRunId", stepRunId);
            stepName = stepRunInfo.getString("stepName");
            stepRunErrorInfo = jobAndStepInterface.getStepErrorInfo(paramMap);
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
                splitStepRunInfo = jobAndStepInterface.getCurrentSplitRunStep(paramMap);
                preData_info = preData.getJSONObject(keyName + "段_list");
                //增加对分段个数总数，失败，以及成功个数进行校验
                sepStepInfoAssert(splitStepRunInfo,preData_info);
                JSONArray dataArray = splitStepRunInfo.getJSONArray("data");
                if(dataArray.size()>0){
                    stepSegmentInfo = dataArray.getJSONObject(0);
                }
                splitStepRunInfo.put("data", stepSegmentInfo);
                //preData_info = preData.getJSONObject(keyName + "段_list");
                stepInfoAssert(preData_info, splitStepRunInfo, "SD");
            }
            //分组——RF
            else if ("RF".equals(stepType)) {
                preData_info = preData.getJSONObject(keyName + "组_info");
                preData_error = preData.getJSONObject(keyName + "组_error");
                stepInfoAssert(preData_info, stepRunInfo, "");
                stepErrorAssert(preData_error, stepRunErrorInfo);
                String jobRunId = stepRunInfo.getString("refJobId");
                paramMap.put("jobRunId", jobRunId);
                JSONObject stepGroupInfo = jobAndStepInterface.getCurrentRunStep(paramMap);
                preData_info = preData.getJSONObject(keyName + "组_list");
                //增加对step组的data数组进行校验
                stepInfoAssert(preData_info, stepGroupInfo, "RF");
                //判断step组中的step成功和失败个数，新增
                stepTotalCountAssert(preData_info, stepGroupInfo);
                getStepRunListAndAssert(preData, stepGroupInfo, paramMap, keyName + "组");
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
            Assert.assertEquals(stepRunInfo.getString("message"), preData_info.getString("message"), "接口返回的message信息不同！");
            Assert.assertEquals(stepRunInfo.getString("code"), preData_info.getString("code"), "接口返回的code信息不同！");
            Assert.assertEquals(stepRunInfo.getString("totalCount"), preData_info.getString("totalCount"), "接口返回的总条数不同！");
        } else {
            Assert.assertEquals(stepRunInfo.getString("appId"), preData_info.getString("appId"), "appId返回值有误");
            Assert.assertEquals(stepRunInfo.getString("tenantId"), preData_info.getString("tenantId"), "tenantId返回值有误");
            Assert.assertEquals(stepRunInfo.getString("stepType"), preData_info.getString("stepType"), "stepType返回值有误");
            Assert.assertEquals(stepRunInfo.getString("stepStatus"), preData_info.getString("stepStatus"), "stepStatus返回值有误");
            Assert.assertEquals(stepRunInfo.getString("stepName"), preData_info.getString("stepName"), "stepName返回值有误");
            Assert.assertEquals(stepRunInfo.getString("splitCount"), preData_info.getString("splitCount"), "splitCount返回值有误");
            Assert.assertEquals(stepRunInfo.getString("splitClazzName"), preData_info.getString("splitClazzName"), "splitClazzName返回值有误");
            Assert.assertEquals(stepRunInfo.getString("clazzName"), preData_info.getString("clazzName"), "clazzName返回值有误");
            //判断params参数
            asserStepParms(preData_info,stepRunInfo);
        }
    }


    /**
     * 【6.4——STEP错误信息】
     *
     * @param preData_error
     * @param stepRunErrorInfo
     */
    public void stepErrorAssert(JSONObject preData_error, JSONObject stepRunErrorInfo) {
        Assert.assertEquals(stepRunErrorInfo.getString("message"), preData_error.getString("message"), "接口返回的message信息不同！");
        Assert.assertEquals(stepRunErrorInfo.getString("code"), preData_error.getString("code"), "接口返回的code信息不同！");
        JSONArray acu_array= stepRunErrorInfo.getJSONArray("data");
        JSONArray per_array = preData_error.getJSONArray("data");
        if (acu_array.size() == per_array.size()){
            if(acu_array.size() > 0 && acu_array != null){
                Assert.assertEquals(acu_array.getJSONObject(0).getString("errorType"),per_array.getJSONObject(0).getString("errorType"), "接口返回的errorType信息不同！");
                Assert.assertEquals(acu_array.getJSONObject(0).getString("message"),per_array.getJSONObject(0).getString("message"), "接口返回的message信息不同！");
                Assert.assertEquals(acu_array.getJSONObject(0).getString("stack"),per_array.getJSONObject(0).getString("stack"), "接口返回的stack信息不同！");
            }
        }else{
            Assert.assertEquals(acu_array.size(), per_array.size() , "报错信息个数不同！");
        }
    }

    /**
     * 【7.JOB历史信息】
     */
    public void getHistRunJobAndAssert(JSONObject preData, Map<String, String> paramMap) {
        JSONObject pre_getHistRunJob = preData.getJSONObject("getHistRunJob");
        //获取实际值，定时和非定时有区分
        JSONObject act_getHistRunJob = JobAndStepInterface.getInstance().getHistRunJob(paramMap);
        //定时任务，判断执行次数
        if ("1".equals(paramMap.get("hasCron"))) {
            pre_getHistRunJob = preData.getJSONObject("getHistRunJobHaveLimitTime");
            Assert.assertEquals(act_getHistRunJob.getString("totalCount"), pre_getHistRunJob.getString("totalCount"), "定时任务执行次数不对等！");
        } else {
            Assert.assertEquals(act_getHistRunJob.getString("totalCount"), pre_getHistRunJob.getString("totalCount"), "接口返回的总条数不同！");

        }
        Assert.assertEquals(act_getHistRunJob.getString("message"), pre_getHistRunJob.getString("message"), "接口返回的message信息不同！");
        Assert.assertEquals(act_getHistRunJob.getString("code"), pre_getHistRunJob.getString("code"), "接口返回的code信息不同！");

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
        Assert.assertEquals(act_finishedCnt, pre_finishedCnt, "STEP完成数不同！");
        Assert.assertEquals(act_failedCnt, pre_failedCnt, "STEP失败数不同！");
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
        map.put("allStepsCnt", String.valueOf(jsonArray.size()));
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

    /**
     * 对分段的step总数以及失败和成功个数进行校验
     *
     * @param splitStepRunInfo
     * @param preData_info
     * @return
     */
    private void sepStepInfoAssert(JSONObject splitStepRunInfo, JSONObject preData_info) {
        //获取总数、成功数、失败数
        Map<String,String> acu_split = getStepCnt(splitStepRunInfo);
        Assert.assertEquals(acu_split.get("allStepsCnt"),preData_info.getString("allStepsCnt"),"分段step的个数不一致");
        Assert.assertEquals(acu_split.get("finishedCnt"),preData_info.getString("finishedCnt"), "分段成功step的个数不一致");
        Assert.assertEquals(acu_split.get("failedCnt"), preData_info.getString("failedCnt"), "分段失败step的个数不一致");
    }

    /**
     * 对step的params参数进行校验
     *
     * @param pre_object
     * @param acu_object
     * @return
     */
    public void asserStepParms(JSONObject pre_object, JSONObject acu_object){
        JSONArray pre_params = pre_object.getJSONArray("params");
        JSONArray acu_params = acu_object.getJSONArray("params");
        if (pre_params.size() == acu_params.size()){
            if (acu_params.size() >0 && acu_params != null){
                for (int i = 0; i < pre_params.size(); i++) {
                    JSONObject pre_paramObject = pre_params.getJSONObject(i);
                    for (int j = 0; j< acu_params.size(); j++){
                        JSONObject acu_paramObject = acu_params.getJSONObject(j);
                        String pre_K = pre_paramObject.get("key").toString();
                        String acu_K = acu_paramObject.get("key").toString();
                        String pre_v = pre_paramObject.get("value").toString();
                        String acu_v = acu_paramObject.get("value").toString();
                        if (pre_paramObject.get("key").equals(acu_paramObject.get("key"))){
                            Assert.assertEquals(acu_paramObject.get("value"),pre_paramObject.get("value"),"返回paramss参数有误！");
                        }
                    }
                }
            }
        }else{
            Assert.assertEquals(pre_params.size(),acu_params.size(),"返回的params个数有错误！");
        }

    }

}
