package com.dcits.sonicAutoTest.UITest;

import com.dcits.sonicAutoTest.UITest.util.LoginUtil;
import com.dcits.sonicAutoTest.util.SonicPropertyUtil;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * sonicUI自动化测试启动类
 */
public class StartTest {
    //main方法测试
    public static void main(String args[]) {
        doTest();
    }

    //testNG方式测试
    @Test
    public static void testNGWay() {
        doTest();
    }

    public static void doTest() {
        System.out.println("测试开始！");
        String paramFile = "param.properties";
        String driverPath = "sonic.platform.driverPath";
        String url = "sonic.platform.url";
        String username = "sonic.platform.username";
        String password = "sonic.platform.password";
        String appName = "sonic.platform.appName";
        String jobName = "sonic.platform.jobName";
        String jobType = "sonic.platform.jobType";
        String jobStatus = "sonic.platform.jobStatus";
        String stepStatus = "sonic.platform.stepStatus";

        driverPath = SonicPropertyUtil.getProperty(driverPath, paramFile);
        url = SonicPropertyUtil.getProperty(url, paramFile);
        username = SonicPropertyUtil.getProperty(username, paramFile);
        password = SonicPropertyUtil.getProperty(password, paramFile);
        appName = SonicPropertyUtil.getProperty(appName, paramFile);
        jobName = SonicPropertyUtil.getProperty(jobName, paramFile);
        jobType = SonicPropertyUtil.getProperty(jobType, paramFile);
        jobStatus = SonicPropertyUtil.getProperty(jobStatus, paramFile);
        stepStatus = SonicPropertyUtil.getProperty(stepStatus, paramFile);

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("driverPath", driverPath);
        paramMap.put("url", url);
        paramMap.put("username", username);
        paramMap.put("password", password);
        paramMap.put("appName", appName);
        paramMap.put("jobName", jobName);
        paramMap.put("jobType", jobType);
        paramMap.put("jobStatus", jobStatus);
        paramMap.put("stepStatus", stepStatus);
        try {
            WebDriver driver = LoginUtil.loadDriver(paramMap);
            LoginUtil.login(paramMap, driver);
            //PlatformManage_2.platformManage_2(driver);//平台管理
            JobManage_3.jobManage_3(driver, paramMap);//作业管理
            UniteDispatch_4.uniteDispatch_4(driver, paramMap);//统一调度
            LoginUtil.logout(driver, jobName, jobType);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("测试完成！");

    }
}
