package com.dcits.sonic.test.preDataUtil;

import com.dcits.sonic.util.HTTPUtil;
import com.dcits.sonic.util.JsonObjectUtil;
import com.dcits.sonic.util.SonicPropertyUtil;
import com.dcits.sonic.util.TimeUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 接口公共类
 */
public class JobAndStepInterface {
    //日志打印
    private static final Logger logger = LoggerFactory.getLogger(JobAndStepInterface.class);
    //JOB指令执行后等待1秒
    private static final long SLEEP_TIME = 1000;
    String baseURL = "sonic.platform.url";
    String tenantId = "sonic.platform.tenantId";
    String appId = "sonic.platform.appId";
    String profile = "sonic.platform.profile";
    String jobType = "sonic.job.jobType";
    String page = "sonic.platform.page";
    String per_page = "sonic.platform.per_page";
    String operator = "sonic.job.operator";
    String secret = "sonic.job.secret";
    String startType = "sonic.job.startType";
    String jobStatus = "sonic.job.jobStatus";
    String startTime_S = "sonic.job.startTime_S";
    String startTime_E = "sonic.job.startTime_E";
    String stepStatus = "sonic.step.stepStatus";
    String userId = "sonic.platform.userId";

    private volatile static JobAndStepInterface jobAndStepInterface;

    private JobAndStepInterface() {
    }

    public static JobAndStepInterface getInstance() {
        if (null == jobAndStepInterface) {
            synchronized (JobAndStepInterface.class) {
                if (null == jobAndStepInterface) {
                    jobAndStepInterface = new JobAndStepInterface();
                }
            }
        }
        return jobAndStepInterface;
    }

    /**
     * 获取JOB列表接口
     */
    public JSONObject getList(Map<String, String> paramMap) {
        JSONObject jsonResult = new JSONObject();
        String url = paramMap.get("platform_url") +
                "/sonic/job/getList" +
                "?tenantId=" + SonicPropertyUtil.getProperty(tenantId) +
                "&appId=" + SonicPropertyUtil.getProperty(appId) +
                "&profile=" + SonicPropertyUtil.getProperty(profile) +
                "&page=" + SonicPropertyUtil.getProperty(page) +
                "&per_page=" + SonicPropertyUtil.getProperty(per_page) +
                "&jobName=" + paramMap.get("jobName") +
                "&jobType=" + SonicPropertyUtil.getProperty(jobType) +
                "&hasCron=" + paramMap.get("hasCron") +
                "&userId=" + SonicPropertyUtil.getProperty(userId);
        logger.info("==url拼接结果==\n" + url);
        try {
            String result = HTTPUtil.get(url);
            jsonResult = JsonObjectUtil.jsonStringToJsonObject(result);
        } catch (Exception e) {
            logger.error("getList", e);
        }
        String totalCount = jsonResult.getString("totalCount");
        if (!"1".equals(totalCount)) {
            logger.error("==获取JOB列表结果不等于1==");
            return null;
        }
        logger.info("获取JOB列表完成");
        return jsonResult;
    }


    /**
     * JOB命令接口
     */
    public JSONObject jobCommand(Map<String, String> paramMap) {
        String url = paramMap.get("platform_url") + "/sonic/job/command";
        logger.info("==url拼接结果==\n" + url);
        JSONObject postJson = new JSONObject();
        //【必输1】应用名，每个应用下面可以定义多个Job
        postJson.put("appId", SonicPropertyUtil.getProperty(appId));
        //【必输2】start;resume;pause;stop;restart;invokeOnce
        postJson.put("instruction", paramMap.get("instruction"));
        //【必输3】jobID,可以在配置文件配置，如果未配置，从接口返回值中取
        postJson.put("jobId", paramMap.get("jobId"));
        //【必输4】操作员
        postJson.put("operator", SonicPropertyUtil.getProperty(operator));
        //【必输5】环境配置项
        postJson.put("profile", SonicPropertyUtil.getProperty(profile));
        //【必输6】授权安全码
        postJson.put("secret", SonicPropertyUtil.getProperty(secret));
        //【必输7】只有command为start的时候有效，启动类型 O:联机应用触发 T:定时触发 M:手工触发
        postJson.put("startType", SonicPropertyUtil.getProperty(startType));
        //【必输8】租户ID
        postJson.put("tenantId", SonicPropertyUtil.getProperty(tenantId));
        //【必输9】jobRunId 续跑时需要传入jobRunId
        postJson.put("jobRunId", paramMap.get("jobRunId"));
        JSONObject jsonResult = new JSONObject();

        //定时任务系统开始时间的毫秒值，为了后续查询
        long start = System.currentTimeMillis();
        try {
            String result = HTTPUtil.post(url, postJson.toString());
            jsonResult = JsonObjectUtil.jsonStringToJsonObject(result);
        } catch (Exception e) {
            logger.error("jobCommand erroe", e);
        }
        jsonResult.put("startJobTimeFlag", start);
        logger.info("执行JOB指令完成");
        return jsonResult;
    }


    /**
     * 获取JOB运行列表接口
     */
    public JSONObject getCurrentRunJob(Map<String, String> paramMap) {
        String url = paramMap.get("platform_url") +
                "/sonic/job/getCurrentRunJob" +
                "?profile=" + SonicPropertyUtil.getProperty(profile) +
                "&appId=" + SonicPropertyUtil.getProperty(appId) +
                "&jobId=" + paramMap.get("jobId") +
                "&startTime_S=" + SonicPropertyUtil.getProperty(startTime_S) +
                "&startTime_E=" + SonicPropertyUtil.getProperty(startTime_E) +
                "&tenantId=" + SonicPropertyUtil.getProperty(tenantId) +
                "&jobStatus=" + SonicPropertyUtil.getProperty(jobStatus) +
                "&page=" + SonicPropertyUtil.getProperty(page) +
                "&per_page=" + SonicPropertyUtil.getProperty(per_page);
        logger.info("==url拼接结果==\n" + url);
        JSONObject jsonResult = new JSONObject();
        try {
            String result = HTTPUtil.get(url);
            jsonResult = JsonObjectUtil.jsonStringToJsonObject(result);
        } catch (Exception e) {
            logger.error("getCurrentRunJob", e);
        }
        String totalCount = jsonResult.getString("totalCount");
        if (!"1".equals(totalCount)) {
            logger.error("==获取JOB运行列表结果不等于1==");
            return null;
        }
        logger.info("获取JOB运行列表完成");
        return jsonResult;
    }

    /**
     * 获取JOB运行历史列表接口
     */
    public JSONObject getHistRunJob(Map<String, String> paramMap) {
        String startTime_S_value = SonicPropertyUtil.getProperty(startTime_S);
        String startTime_E_value = SonicPropertyUtil.getProperty(startTime_E);
        if ("1".equals(paramMap.get("hasCron"))) {
            startTime_S_value = paramMap.get("job_startTime");
            startTime_E_value = paramMap.get("job_endTime");
        }
        String url = paramMap.get("platform_url") +
                "/sonic/job/getHistRunJob" +
                "?profile=" + SonicPropertyUtil.getProperty(profile) +
                "&appId=" + SonicPropertyUtil.getProperty(appId) +
                "&jobId=" + paramMap.get("jobId") +
                "&startTime_S=" + startTime_S_value +
                "&startTime_E=" + startTime_E_value +
                "&tenantId=" + SonicPropertyUtil.getProperty(tenantId) +
                "&jobStatus=" + SonicPropertyUtil.getProperty(jobStatus) +
                "&page=" + SonicPropertyUtil.getProperty(page) +
                "&per_page=" + SonicPropertyUtil.getProperty(per_page);
        logger.info("==url拼接结果==\n" + url);
        JSONObject jsonResult = new JSONObject();
        try {
            String result = HTTPUtil.get(url);
            jsonResult = JsonObjectUtil.jsonStringToJsonObject(result);
        } catch (Exception e) {
            logger.error("getHistRunJob", e);
        }
        logger.info("获取JOB运行历史列表完成");
        return jsonResult;
    }

    /**
     * 获取STEP运行信息接口
     */
    public JSONObject getCurrentRunStep(Map<String, String> paramMap) {
        String url = paramMap.get("platform_url") +
                "/sonic/step/getCurrentRunStep" +
                "?tenantId=" + SonicPropertyUtil.getProperty(tenantId) +
                "&appId=" + SonicPropertyUtil.getProperty(appId) +
                "&jobRunId=" + paramMap.get("jobRunId") +
                "&stepStatus=" + SonicPropertyUtil.getProperty(stepStatus) +
                "&page=" + SonicPropertyUtil.getProperty(page) +
                "&per_page=" + SonicPropertyUtil.getProperty(per_page);
        logger.info("==url拼接结果==\n" + url);
        JSONObject jsonResult = new JSONObject();
        try {
            String result = HTTPUtil.get(url);
            jsonResult = JsonObjectUtil.jsonStringToJsonObject(result);
        } catch (Exception e) {
            logger.error("getCurrentRunStep", e);
        }
        logger.info("获取STEP运行信息列表完成");
        return jsonResult;
    }

    /**
     * 获取分段运行信息接口
     */
    public JSONObject getCurrentSplitRunStep(Map<String, String> paramMap) {
        String url = paramMap.get("platform_url") +
                "/sonic/step/getCurrentSplitRunStep" +
                "?stepRunId=" + paramMap.get("stepRunId") +
                "&stepStatus=" + SonicPropertyUtil.getProperty(stepStatus) +
                "&page=" + SonicPropertyUtil.getProperty(page) +
                "&per_page=" + SonicPropertyUtil.getProperty(per_page);
        logger.info("==url拼接结果==\n" + url);
        JSONObject jsonResult = new JSONObject();
        try {
            String result = HTTPUtil.get(url);
            jsonResult = JsonObjectUtil.jsonStringToJsonObject(result);
        } catch (Exception e) {
            logger.error("getCurrentSplitRunStep", e);
        }
        logger.info("获取分段运行信息列表完成");
        return jsonResult;
    }

    /**
     * 获取STEP错误运行信息接口
     */
    public JSONObject getStepErrorInfo(Map<String, String> paramMap) {
        String url = paramMap.get("platform_url") +
                "/sonic/job/getStepErrorInfo" +
                "?tenantId=" + SonicPropertyUtil.getProperty(tenantId) +
                "&stepRunId=" + paramMap.get("stepRunId");
        logger.info("==url拼接结果==\n" + url);
        JSONObject jsonResult = new JSONObject();
        try {
            String result = HTTPUtil.get(url);
            jsonResult = JsonObjectUtil.jsonStringToJsonObject(result);
        } catch (Exception e) {
            logger.error("getStepErrorInfo", e);
        }
        logger.info("获取STEP错误运行信息完成");
        return jsonResult;
    }

    /**
     * 获取JOB定义信息
     */
    public JSONObject getChain(Map<String, String> paramMap) {
        JSONObject jsonResult = new JSONObject();
        String url = paramMap.get("platform_url") +
                "/sonic/job/getChain" +
                "?tenantId=" + SonicPropertyUtil.getProperty(tenantId) +
                "&appId=" + SonicPropertyUtil.getProperty(appId) +
                "&jobId=" + paramMap.get("jobId") +
                "&profile=" + SonicPropertyUtil.getProperty(profile);
        logger.info("==url拼接结果==\n" + url);
        try {
            String result = HTTPUtil.get(url);
            jsonResult = JsonObjectUtil.jsonStringToJsonObject(result);
        } catch (Exception e) {
            logger.error("getChain", e);
        }
        logger.info("获取JOB定义信息完成");
        return jsonResult;
    }


    /**
     * 获取JOB运行信息
     */
    public JSONObject getRunChain(Map<String, String> paramMap) {
        JSONObject jsonResult = new JSONObject();
        String url = paramMap.get("platform_url") +
                "/sonic/job/getRunChain" +
                "?tenantId=" + SonicPropertyUtil.getProperty(tenantId) +
                "&appId=" + SonicPropertyUtil.getProperty(appId) +
                "&jobId=" + paramMap.get("jobId") +
                "&profile=" + SonicPropertyUtil.getProperty(profile) +
                "&jobRunId=" + paramMap.get("jobRunId");
        logger.info("==url拼接结果==\n" + url);
        try {
            String result = HTTPUtil.get(url);
            jsonResult = JsonObjectUtil.jsonStringToJsonObject(result);
        } catch (Exception e) {
            logger.error("getRunChain", e);
        }
        logger.info("获取JOB运行信息完成");
        return jsonResult;
    }


    /**
     * 获取JOB列表接口返回数据中获取JobId,jobRunId
     */
    public String getJobInfo(JSONObject jsonObject, String key) {
        String jobInfo = "";
        JSONObject data = jsonObject.getJSONArray("data").getJSONObject(0);
        jobInfo = data.getString(key);
        return jobInfo;
    }

    /**
     * JOB指令执行
     */
    public JSONObject getJobCommand(Map<String, String> paramMap) {
        JSONObject jobCommand = jobCommand(paramMap);
        try {
            Thread.sleep(SLEEP_TIME);
            logger.info("正在执行JOB指令...");
        } catch (InterruptedException e) {
            logger.info("JOB指令执行错误！");
            e.printStackTrace();
        }
        return jobCommand;
    }


    /**
     * 异常JOB续跑时先获取jobRunId
     */
    public String getJobRunId(Map<String, String> paramMap) {
        String jobRunId = "";
        if ("restart".equals(paramMap.get("instruction"))) {
            JSONObject getCurrentRunJob = jobAndStepInterface.getCurrentRunJob(paramMap);
            jobRunId = jobAndStepInterface.getJobInfo(getCurrentRunJob, "jobRunId");
        }
        return jobRunId;
    }

    /**
     * JOB轮询
     */
    public void waitGetCurrentRunJob(Map<String, String> paramMap) {
        //获取JOB运行信息
        JSONObject getCurrentRunJob = getCurrentRunJob(paramMap);
        String startTime = TimeUtil.gerCurrentTime();
        //开始轮询
        runSchedeukedTask(getCurrentRunJob, paramMap);
        String endTime = TimeUtil.gerCurrentTime();
        String diffTime = TimeUtil.getDiffTime(startTime, endTime);
        logger.info("轮询耗时：" + diffTime);
    }


    /**
     * 轮询直到JOB运行状态变成【已完成】
     */
    public void runSchedeukedTask(JSONObject getCurrentRunJob, Map<String, String> paramMap) {
        if ((getCurrentRunJob == null || getCurrentRunJob.size() == 0) || "R".equals(getJobInfo(getCurrentRunJob, "jobStatus"))) {
            JSONObject currentRunJob = JobAndStepInterface.getInstance().getCurrentRunJob(paramMap);
            runSchedeukedTask(currentRunJob, paramMap);
        }
    }
}
