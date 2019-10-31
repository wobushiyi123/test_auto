package com.dcits.selenium.sonic.sonicAutoTest;

import com.dcits.selenium.sonic.sonicAutoTest.util.JobManageUtil_3;
import com.dcits.selenium.sonic.sonicAutoTest.util.MenuBarUtil;
import org.openqa.selenium.WebDriver;

import java.util.Map;

/**
 * 【作业管理】
 */
public class JobManage_3 {

    public static void jobManage_3(WebDriver driver, Map<String, String> paramMap) throws InterruptedException {
        //变量初始化
        String appName = paramMap.get("appName");
        String jobName = paramMap.get("jobName");
        String jobType = paramMap.get("jobType");
        String jobStatus = paramMap.get("jobStatus");
        String stepStatus = paramMap.get("stepStatus");
        //进入【作业管理】
        MenuBarUtil.goToJobManageView_30(driver);
        //【3.1——JOB列表】
        MenuBarUtil.goToJobListView_31(driver);
        JobManageUtil_3.jobList_31(driver, appName, jobName, jobType);
        //【3.2——JOB运行信息】
        MenuBarUtil.goToJobRunnListView_32(driver);
        JobManageUtil_3.jobRunList_32(driver, appName, jobName, jobStatus, stepStatus);
        //【3.3——JOB运行历史】
        MenuBarUtil.goToJobHisListView_33(driver);
        JobManageUtil_3.jobHisList_33(driver, appName, jobName, stepStatus);
    }

}
