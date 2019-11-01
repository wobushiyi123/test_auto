package com.dcits.sonicAutoTest.interfaceTest.preDataUtil;

import com.dcits.sonicAutoTest.util.HTTPUtil;
import com.dcits.sonicAutoTest.util.JsonObjectUtil;
import com.dcits.sonicAutoTest.util.SonicPropertyUtil;
import com.dcits.sonicAutoTest.util.TimeUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    String jobName = "sonic.job.jobName";
    String jobType = "sonic.job.jobType";
    String page = "sonic.platform.page";
    String per_page = "sonic.platform.per_page";
    String jobId = "sonic.job.jobId";
    String operator = "sonic.job.operator";
    String instruction = "sonic.job.instruction";
    String secret = "sonic.job.secret";
    String startType = "sonic.job.startType";
    String jobStatus = "sonic.job.jobStatus";
    String startTime_S = "sonic.job.startTime_S";
    String startTime_E = "sonic.job.startTime_E";
    String jobRunId = "sonic.step.jobRunId";
    String stepStatus = "sonic.step.stepStatus";
    String stepRunId = "sonic.step.stepRunId";
    String userId = "sonic.platform.userId";
    String hasCron = "sonic.job.hasCron";

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
     *
     * @param fileName
     * @return
     */
    public JSONObject getList(String fileName) {
        JSONObject jsonResult = new JSONObject();
        String url = SonicPropertyUtil.getProperty(baseURL, fileName) +
                "/sonic/job/getList" +
                "?tenantId=" + SonicPropertyUtil.getProperty(tenantId, fileName) +
                "&appId=" + SonicPropertyUtil.getProperty(appId, fileName) +
                "&profile=" + SonicPropertyUtil.getProperty(profile, fileName) +
                "&page=" + SonicPropertyUtil.getProperty(page, fileName) +
                "&per_page=" + SonicPropertyUtil.getProperty(per_page, fileName) +
                "&jobName=" + SonicPropertyUtil.getProperty(jobName, fileName) +
                "&jobType=" + SonicPropertyUtil.getProperty(jobType, fileName) +
                "&hasCron=" + SonicPropertyUtil.getProperty(hasCron, fileName) +
                "&userId=" + SonicPropertyUtil.getProperty(userId, fileName);
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
     *
     * @param fileName
     * @param jobIdValue
     * @return
     */
    public JSONObject jobCommand(String fileName, String jobIdValue, String jobRunId, String doJobStatus) {
        String url = SonicPropertyUtil.getProperty(baseURL, fileName) +
                "/sonic/job/command";
        logger.info("==url拼接结果==\n" + url);
        JSONObject postJson = new JSONObject();
        //【必输1】应用名，每个应用下面可以定义多个Job
        postJson.put("appId", SonicPropertyUtil.getProperty(appId, fileName));
        //【必输2】start;resume;pause;stop;restart;invokeOnce
        postJson.put("instruction", SonicPropertyUtil.getProperty(instruction, fileName));
        if ("stop".equals(doJobStatus)) {
            postJson.put("instruction", doJobStatus);
        }
        //【必输3】jobID,可以在配置文件配置，如果未配置，从接口返回值中取
        postJson.put("jobId", getSpecifiedValue(jobIdValue, fileName, jobId));
        //【必输4】操作员
        postJson.put("operator", SonicPropertyUtil.getProperty(operator, fileName));
        //【必输5】环境配置项
        postJson.put("profile", SonicPropertyUtil.getProperty(profile, fileName));
        //【必输6】授权安全码
        postJson.put("secret", SonicPropertyUtil.getProperty(secret, fileName));
        //【必输7】只有command为start的时候有效，启动类型 O:联机应用触发 T:定时触发 M:手工触发
        postJson.put("startType", SonicPropertyUtil.getProperty(startType, fileName));
        //【必输8】租户ID
        postJson.put("tenantId", SonicPropertyUtil.getProperty(tenantId, fileName));
        //【必输9】jobRunId 续跑时需要传入jobRunId
        postJson.put("jobRunId", jobRunId);
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
     *
     * @param fileName
     * @param jobIdValue
     * @return
     */
    public JSONObject getCurrentRunJob(String fileName, String jobIdValue) {
        String url = SonicPropertyUtil.getProperty(baseURL, fileName) +
                "/sonic/job/getCurrentRunJob" +
                "?profile=" + SonicPropertyUtil.getProperty(profile, fileName) +
                "&appId=" + SonicPropertyUtil.getProperty(appId, fileName) +
                "&jobId=" + getSpecifiedValue(jobIdValue, fileName, jobId) +
                "&startTime_S=" + SonicPropertyUtil.getProperty(startTime_S, fileName) +
                "&startTime_E=" + SonicPropertyUtil.getProperty(startTime_E, fileName) +
                "&tenantId=" + SonicPropertyUtil.getProperty(tenantId, fileName) +
                "&jobStatus=" + SonicPropertyUtil.getProperty(jobStatus, fileName) +
                "&page=" + SonicPropertyUtil.getProperty(page, fileName) +
                "&per_page=" + SonicPropertyUtil.getProperty(per_page, fileName);
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
     *
     * @param fileName
     * @param jobIdValue
     * @return
     */
    public JSONObject getHistRunJob(String fileName, String jobIdValue, String job_startTime, String job_endTime, String isTimedJob) {
        String startTime_S_value = SonicPropertyUtil.getProperty(startTime_S, fileName);
        String startTime_E_value = SonicPropertyUtil.getProperty(startTime_E, fileName);
        if (Boolean.valueOf(isTimedJob)) {
            startTime_S_value = job_startTime;
            startTime_E_value = job_endTime;
        }
        String url = SonicPropertyUtil.getProperty(baseURL, fileName) +
                "/sonic/job/getHistRunJob" +
                "?profile=" + SonicPropertyUtil.getProperty(profile, fileName) +
                "&appId=" + SonicPropertyUtil.getProperty(appId, fileName) +
                "&jobId=" + getSpecifiedValue(jobIdValue, fileName, this.jobId) +
                "&startTime_S=" + startTime_S_value +
                "&startTime_E=" + startTime_E_value +
                "&tenantId=" + SonicPropertyUtil.getProperty(tenantId, fileName) +
                "&jobStatus=" + SonicPropertyUtil.getProperty(jobStatus, fileName) +
                "&page=" + SonicPropertyUtil.getProperty(page, fileName) +
                "&per_page=" + SonicPropertyUtil.getProperty(per_page, fileName);
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
     *
     * @param fileName
     * @param jobRunIdValue
     * @return
     */
    public JSONObject getCurrentRunStep(String fileName, String jobRunIdValue) {
        String url = SonicPropertyUtil.getProperty(baseURL, fileName) +
                "/sonic/step/getCurrentRunStep" +
                "?tenantId=" + SonicPropertyUtil.getProperty(tenantId, fileName) +
                "&appId=" + SonicPropertyUtil.getProperty(appId, fileName) +
                "&jobRunId=" + getSpecifiedValue(jobRunIdValue, fileName, jobRunId) +
                "&stepStatus=" + SonicPropertyUtil.getProperty(stepStatus, fileName) +
                "&page=" + SonicPropertyUtil.getProperty(page, fileName) +
                "&per_page=" + SonicPropertyUtil.getProperty(per_page, fileName);
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
     *
     * @param fileName
     * @param stepRunIdValue
     * @return
     */
    public JSONObject getCurrentSplitRunStep(String fileName, String stepRunIdValue) {
        String url = SonicPropertyUtil.getProperty(baseURL, fileName) +
                "/sonic/step/getCurrentSplitRunStep" +
                "?stepRunId=" + getSpecifiedValue(stepRunIdValue, fileName, stepRunId) +
                "&stepStatus=" + SonicPropertyUtil.getProperty(stepStatus, fileName) +
                "&page=" + SonicPropertyUtil.getProperty(page, fileName) +
                "&per_page=" + SonicPropertyUtil.getProperty(per_page, fileName);
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
     *
     * @param fileName
     * @param stepRunIdValue
     * @return
     */
    public JSONObject getStepErrorInfo(String fileName, String stepRunIdValue) {
        String url = SonicPropertyUtil.getProperty(baseURL, fileName) +
                "/sonic/job/getStepErrorInfo" +
                "?tenantId=" + SonicPropertyUtil.getProperty(tenantId, fileName) +
                "&stepRunId=" + getSpecifiedValue(stepRunIdValue, fileName, stepRunId);
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
     *
     * @param fileName
     * @param jobIdValue
     * @return
     */
    public JSONObject getChain(String fileName, String jobIdValue) {
        JSONObject jsonResult = new JSONObject();
        String url = SonicPropertyUtil.getProperty(baseURL, fileName) +
                "/sonic/job/getChain" +
                "?tenantId=" + SonicPropertyUtil.getProperty(tenantId, fileName) +
                "&appId=" + SonicPropertyUtil.getProperty(appId, fileName) +
                "&jobId=" + getSpecifiedValue(jobIdValue, fileName, jobId) +
                "&profile=" + SonicPropertyUtil.getProperty(profile, fileName);
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
     *
     * @param fileName
     * @param jobIdValue
     * @param jobRunIdValue
     * @return
     */
    public JSONObject getRunChain(String fileName, String jobIdValue, String jobRunIdValue) {
        JSONObject jsonResult = new JSONObject();
        String url = SonicPropertyUtil.getProperty(baseURL, fileName) +
                "/sonic/job/getRunChain" +
                "?tenantId=" + SonicPropertyUtil.getProperty(tenantId, fileName) +
                "&appId=" + SonicPropertyUtil.getProperty(appId, fileName) +
                "&jobId=" + getSpecifiedValue(jobIdValue, fileName, jobId) +
                "&profile=" + SonicPropertyUtil.getProperty(profile, fileName) +
                "&jobRunId=" + getSpecifiedValue(jobRunIdValue, fileName, jobRunId);
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
     * 判断property配置文件中是否存在配置，优先取配置文件中数据
     *
     * @param param    其他接口传入的参数
     * @param fileName
     * @param key
     * @return
     */
    public String getSpecifiedValue(String param, String fileName, String key) {
        String value = "";
        if (StringUtils.isBlank(param)) {
            value = SonicPropertyUtil.getProperty(key, fileName);
        } else {
            value = param;
        }
        return value;
    }

    /**
     * 获取JOB列表接口返回数据中获取JobId,jobRunId
     *
     * @param jsonObject
     * @param key
     * @return JobId, jobRunId
     */
    public String getJobInfo(JSONObject jsonObject, String key) {
        String jobInfo = "";
        JSONObject data = jsonObject.getJSONArray("data").getJSONObject(0);
        jobInfo = data.getString(key);
        return jobInfo;
    }

    /**
     * 轮询直到JOB运行状态变成【已完成】
     *
     * @param getCurrentRunJob
     * @param fileName         参数文件名
     * @param jobId
     */
    public void runSchedeukedTask(JSONObject getCurrentRunJob, String fileName, String jobId) {
        if ((getCurrentRunJob == null || getCurrentRunJob.size() == 0) || "R".equals(getJobInfo(getCurrentRunJob, "jobStatus"))) {
            JSONObject currentRunJob = JobAndStepInterface.getInstance().getCurrentRunJob(fileName, jobId);
            runSchedeukedTask(currentRunJob, fileName, jobId);
        }
    }

    /**
     * JOB指令执行
     *
     * @param param_fileName
     * @param jobId
     * @return 指令执行结果
     */
    public JSONObject getJobCommand(String param_fileName, String jobId, String jobRunId, String doJobStatus) {
        JSONObject jobCommand = jobCommand(param_fileName, jobId, jobRunId, doJobStatus);
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
     *
     * @param param_fileName
     * @param jobId
     * @return jobRunId
     */
    public String getJobRunId(String param_fileName, String jobId) {
        String jobRunId = "";
        if ("restart".equals(SonicPropertyUtil.getProperty("sonic.job.instruction", param_fileName))) {
            JSONObject getCurrentRunJob = jobAndStepInterface.getCurrentRunJob(param_fileName, jobId);
            jobRunId = jobAndStepInterface.getJobInfo(getCurrentRunJob, "jobRunId");
        }
        return jobRunId;
    }

    /**
     * JOB轮询
     *
     * @param param_fileName
     * @param jobId
     */
    public void waitGetCurrentRunJob(String param_fileName, String jobId) {
        //获取JOB运行信息
        JSONObject getCurrentRunJob = getCurrentRunJob(param_fileName, jobId);
        String startTime = TimeUtil.gerCurrentTime();
        //开始轮询
        runSchedeukedTask(getCurrentRunJob, param_fileName, jobId);
        String endTime = TimeUtil.gerCurrentTime();
        String diffTime = TimeUtil.getDiffTime(startTime, endTime);
        logger.info("轮询耗时：" + diffTime);
    }
}
