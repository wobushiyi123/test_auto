package com.dcits.selenium.sonic.sonicAutoTest.util;

import com.dcits.util.DriverUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.Map;

/**
 * 【登录登出管理公共类】
 */
public class LoginUtil {

    //任务类型
    public enum JOB_TYPE {
        所有任务, 定时任务, 普通任务
    }

    //job运行状态
    public enum JOB_STATUS {
        已暂停, 已完成, 运行中, 失败
    }

    //step运行状态
    public enum STEP_STATUS {
        新建, 失败, 执行完成, 运行中, 已跳过, 派发中
    }

    /**
     * 获取浏览器驱动
     *
     * @param paramMap
     * @return
     */
    public static WebDriver loadDriver(Map<String, String> paramMap) {
        WebDriver driver = null;
        String driverPath = paramMap.get("driverPath");
        //谷歌浏览器
        //driver = DriverUtil.getChromeDriver(driverPath);
        //火狐浏览器
        driver = DriverUtil.getFiedFoxDriver(driverPath);
        return driver;


    }

    /**
     * 登录测试
     *
     * @param driver
     * @throws InterruptedException
     */
    public static void login(Map<String,String> paramMap,WebDriver driver) throws InterruptedException {
        Thread.sleep(1000);
        //获取访问地址—调度端地址：10.7.19.116:31561
        driver.get(paramMap.get("url"));
        //driver.get("http://10.7.20.206:8099");
        //浏览器最大化
        driver.manage().window().maximize();
        Thread.sleep(1000);
        //输入用户名：mayym
        driver.findElement(By.name("username")).sendKeys(paramMap.get("username"));
        Thread.sleep(1000);
        //输入密码：123456
        driver.findElement(By.name("password")).sendKeys(paramMap.get("password"));
        Thread.sleep(1000);
        //点击登录
        driver.findElement(By.cssSelector(".el-button")).click();
        Thread.sleep(2000);

    }

    /**
     * 测试完成，退出登录
     *
     * @param driver
     * @throws InterruptedException
     */
    public static void logout(WebDriver driver, String jobName, String jobType) throws InterruptedException {
        //停止定时任务
        if (LoginUtil.JOB_TYPE.定时任务.toString().equals(jobType)) {
            MenuBarUtil.goToJobListView_42(driver);
            UniteDispatchUtil_4.getJobDefinedListByCondition(driver, jobName, jobType);
            UniteDispatchUtil_4.startAndStopTimedJob(driver);
        }
        //鼠标移至【标签选项】
        {
            WebElement element = driver.findElement(By.cssSelector(".el-button--mini > span"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
        }
        Thread.sleep(2000);
        //点击【关闭所有】页签
        driver.findElement(By.xpath("//li[contains(.,'关闭所有')]")).click();
        Thread.sleep(2000);
        //点击【用户名】
        driver.findElement(By.xpath("//span/i")).click();
        Thread.sleep(2000);
        //退出登录
        driver.findElement(By.xpath("//li[contains(.,'退出')]")).click();
        Thread.sleep(3000);
        //关闭浏览器
        driver.close();
    }
}
