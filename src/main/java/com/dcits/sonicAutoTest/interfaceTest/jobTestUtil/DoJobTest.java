package com.dcits.sonicAutoTest.interfaceTest.jobTestUtil;

import com.dcits.sonicAutoTest.interfaceTest.preDataUtil.JobAndStepInterface;
import com.dcits.sonicAutoTest.interfaceTest.preDataUtil.GetPreDataFromXml;
import com.dcits.sonicAutoTest.interfaceTest.preDataUtil.SavePreDataToXml;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.Map;

/**
 * JOB测试
 */
public class DoJobTest {

    GetActDataAndAssert getActDataAndAssert = GetActDataAndAssert.getInstance();

    /**
     * JOB测试公共方法
     *
     * @param param_fileName 参数文件名
     * @param pre_fileName   预言值文件所在路径
     * @param isTimedJob     是否为定时任务
     */
    @Parameters({"param_fileName", "pre_fileName", "isTimedJob"})
    @Test
    public void doJobTest(String param_fileName, String pre_fileName, String isTimedJob) {
        String pre_filePath = SavePreDataToXml.preDataFilesPath + pre_fileName + ".xml";
        JSONArray preJSONArray = GetPreDataFromXml.getInstance().getPreDataFromXml(pre_filePath);
        JSONObject preData = preJSONArray.getJSONObject(0);
        //获取实际值并验证
        //1.JOB列表
        String jobId = getActDataAndAssert.getListAndAssert(preData, param_fileName);
        //2.JOB详细信息
        getActDataAndAssert.getChainAndAssert(preData, param_fileName, jobId);
        //3.JOB指令执行,添加参数commanMap
        Map<String, Object> commanMap = getActDataAndAssert.jobCommandAndAssert(preData, param_fileName, jobId, isTimedJob);
        //轮询JOB，直到JOB运行完成
        JobAndStepInterface.getInstance().waitGetCurrentRunJob(param_fileName, jobId);
        //4.JOB运行列表
        String jobRunId = getActDataAndAssert.getCurrentRunJobAndAssert(preData, param_fileName, jobId);
        //5.JOB运行详情
        getActDataAndAssert.getRunChainAndAssert(preData, param_fileName, jobId, jobRunId);
        //6.递归获取STEP运行列表
        getActDataAndAssert.getCurrentRunStepAndAssert(preData, param_fileName, jobRunId);
        //7.历史运行列表，判断定时任务执行次数
        if (Boolean.valueOf(isTimedJob)) {
            //判断执行次数
            GetActDataAndAssert.getInstance().getHistRunJobAndAssert(preData, param_fileName, jobId, commanMap);
        }
    }
}
