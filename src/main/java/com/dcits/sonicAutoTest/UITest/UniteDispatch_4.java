package com.dcits.sonicAutoTest.UITest;

import com.dcits.sonicAutoTest.UITest.util.MenuBarUtil;
import com.dcits.sonicAutoTest.UITest.util.UniteDispatchUtil_4;
import org.openqa.selenium.WebDriver;

import java.util.Map;

/**
 * 【统一调度】
 */
public class UniteDispatch_4 {

    public static void uniteDispatch_4(WebDriver driver, Map<String, String> paramMap) throws InterruptedException {
        //变量初始化
        String jobName = paramMap.get("jobName");
        String jobType = paramMap.get("jobType");
        String jobStatus = paramMap.get("jobStatus");
        String stepStatus = paramMap.get("stepStatus");
        //进入【统一调度】
        MenuBarUtil.goToUnifiedDispatchView_40(driver);
        //【4.1——JOB定义】
        MenuBarUtil.goToJobDefineListView_41(driver);
        //UniteDispatchUtil_4.jobDefineTest_41(driver,jobName);
        //【4.2——JOB列表】
        MenuBarUtil.goToJobListView_42(driver);
        UniteDispatchUtil_4.jobListTest_42(driver, jobName, jobType);
        //【4.3——JOB运行信息】
        MenuBarUtil.goToJobRunListView_43(driver);
        UniteDispatchUtil_4.jobRunTest_43(driver, jobName, jobStatus, stepStatus);
        //【4.4——JOB运行历史】
        MenuBarUtil.goToJobHisListView_44(driver);
        UniteDispatchUtil_4.jobHisList_44(driver, jobName, stepStatus);
    }


}

