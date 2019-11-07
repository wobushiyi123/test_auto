package com.dcits.sonic.test.jobTestUtil;

import com.dcits.sonic.test.preDataUtil.JobAndStepInterface;
import com.dcits.sonic.test.preDataUtil.GetPreDataFromXml;
import com.dcits.sonic.test.preDataUtil.SavePreDataToXml;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * JOB测试
 */
public class DoJobTest {

    GetActDataAndAssert getActDataAndAssert = GetActDataAndAssert.getInstance();

    /**
     * JOB测试公共方法
     */
    @Parameters({"pre_fileName", "jobName", "instruction", "hasCron", "timelength","platform_url"})
    @Test
    public void doJobTest(String pre_fileName, String jobName, String instruction, String hasCron,
                          String timelength,String  platform_url) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("jobName", jobName);
        paramMap.put("instruction", instruction);
        paramMap.put("hasCron", hasCron);
        paramMap.put("timelength", timelength);
        paramMap.put("platform_url", platform_url);
        String pre_filePath = SavePreDataToXml.preDataXmlFilesPath + pre_fileName + ".xml";
        JSONArray preJSONArray = GetPreDataFromXml.getInstance().getPreDataFromXml(pre_filePath);
        JSONObject preData = preJSONArray.getJSONObject(0);
        //获取实际值并验证
        //1.JOB列表
        String jobId = getActDataAndAssert.getListAndAssert(preData, paramMap);
        paramMap.put("jobId", jobId);
        //2.JOB详细信息
        getActDataAndAssert.getChainAndAssert(preData, paramMap);
        //3.JOB指令执行,添加参数commanMap
        getActDataAndAssert.jobCommandAndAssert(preData, paramMap);
        //轮询JOB，直到JOB运行完成
        JobAndStepInterface.getInstance().waitGetCurrentRunJob(paramMap);
        //4.JOB运行列表
        String jobRunId = getActDataAndAssert.getCurrentRunJobAndAssert(preData, paramMap);
        paramMap.put("jobRunId", jobRunId);
        //5.JOB运行详情
        getActDataAndAssert.getRunChainAndAssert(preData, paramMap);
        //6.递归获取STEP运行列表
        getActDataAndAssert.getCurrentRunStepAndAssert(preData, paramMap);
        //7.历史运行列表，判断定时任务执行次数
        if ("1".equals(paramMap.get("hasCron"))) {
            //判断执行次数
            GetActDataAndAssert.getInstance().getHistRunJobAndAssert(preData, paramMap);
        }
    }
}
