package com.dcits.sonicAutoTest.UITest.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * 【菜单公共类】
 */
public class MenuBarUtil {
    //【1.首页】
    public static void goToIndexView_10(WebDriver driver) throws InterruptedException {
        driver.findElement(By.xpath("//li[contains(.,' 首页')]")).click();
        Thread.sleep(1000);
    }

    //【2.平台管理】
    public static void goToPlatformManageView_20(WebDriver driver) throws InterruptedException {
        driver.findElement(By.xpath("//span[contains(.,'平台管理')]")).click();
        Thread.sleep(1000);
    }

    //【3.作业管理】
    public static void goToJobManageView_30(WebDriver driver) throws InterruptedException {
        driver.findElement(By.xpath("//span[contains(.,'作业管理')]")).click();
        Thread.sleep(1000);
    }

    //【4.统一调度】
    public static void goToUnifiedDispatchView_40(WebDriver driver) throws InterruptedException {
        driver.findElement(By.xpath("//span[contains(.,'统一调度')]")).click();
        Thread.sleep(1000);
    }

    //【2.1——环境配置管理】
    public static void goToEnvConfigView_21(WebDriver driver) throws InterruptedException {
        driver.findElement(By.xpath("//li[2]/ul/li[1]")).click();
        Thread.sleep(1000);
    }

    //【2.2——应用管理】
    public static void goToAppManageView_22(WebDriver driver) throws InterruptedException {
        driver.findElement(By.xpath("//li[2]/ul/li[2]")).click();
        Thread.sleep(1000);
    }

    //【2.3——用户权限管理】
    public static void goToUserAuthManageView_23(WebDriver driver) throws InterruptedException {
        driver.findElement(By.xpath("//li[2]/ul/li[3]")).click();
        Thread.sleep(1000);
    }

    //【2.4——版本管理】
    public static void goToVersionManageView_24(WebDriver driver) throws InterruptedException {
        driver.findElement(By.xpath("//li[2]/ul/li[4]")).click();
        Thread.sleep(1000);
    }

    //【3.1—JOB列表】
    public static void goToJobListView_31(WebDriver driver) throws InterruptedException {
        driver.findElement(By.xpath("//li[3]/ul/li[1]")).click();
        Thread.sleep(1000);
    }

    //【3.2-JOB运行信息】
    public static void goToJobRunnListView_32(WebDriver driver) throws InterruptedException {
        driver.findElement(By.xpath("//li[3]/ul/li[2]")).click();
        Thread.sleep(1000);
    }

    //【3.3-JOB运行历史】
    public static void goToJobHisListView_33(WebDriver driver) throws InterruptedException {
        driver.findElement(By.xpath("//li[3]/ul/li[3]")).click();
        Thread.sleep(1000);
    }

    //【4.1—JOB定义】
    public static void goToJobDefineListView_41(WebDriver driver) throws InterruptedException {
        driver.findElement(By.xpath("//li[4]/ul/li[1]")).click();
        Thread.sleep(1000);
    }

    //【4.2-JOB列表】
    public static void goToJobListView_42(WebDriver driver) throws InterruptedException {
        driver.findElement(By.xpath("//li[4]/ul/li[2]")).click();
        Thread.sleep(1000);
    }

    //【4.3-JOB运行信息】
    public static void goToJobRunListView_43(WebDriver driver) throws InterruptedException {
        driver.findElement(By.xpath("//li[4]/ul/li[3]")).click();
        Thread.sleep(1000);
    }

    //【4.4-JOB运行历史】
    public static void goToJobHisListView_44(WebDriver driver) throws InterruptedException {
        driver.findElement(By.xpath("//li[4]/ul/li[4]")).click();
        Thread.sleep(1000);
    }
}
