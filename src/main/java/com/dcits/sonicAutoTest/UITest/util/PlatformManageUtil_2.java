package com.dcits.sonicAutoTest.UITest.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * 【平台管理公共类】
 */
public class PlatformManageUtil_2 {
    /**
     * 添加环境配置
     *
     * @param driver
     * @param envName
     * @param envDesc
     * @throws InterruptedException
     */
    public static void addEnvConfig(WebDriver driver, String envName, String envDesc) throws InterruptedException {
        //添加
        driver.findElement(By.xpath("//span[contains(.,'添加')]")).click();
        Thread.sleep(1000);
        //点击环境配置输入框
        driver.findElement(By.xpath("(//input[@type='text'])[2]")).click();
        Thread.sleep(1000);
        //输入
        driver.findElement(By.xpath("//div/div/div/input")).sendKeys(envName);
        Thread.sleep(1000);
        //点击环境配置描述输入框
        driver.findElement(By.xpath("//textarea")).click();
        Thread.sleep(1000);
        //输入
        driver.findElement(By.xpath("//textarea")).sendKeys(envDesc);
        Thread.sleep(1000);
        //点击确认
        driver.findElement(By.xpath("//div[3]/span/button/span")).click();
        Thread.sleep(1000);
    }

    /**
     * 编辑环境配置
     *
     * @param driver
     * @throws InterruptedException
     */
    public static void updateEnvConfig(WebDriver driver) throws InterruptedException {
        //点击编辑
        driver.findElement(By.xpath("//tr[2]/td[3]/div/button/span")).click();
        Thread.sleep(1000);
        //点击确认
        driver.findElement(By.xpath("//div[3]/span/button/span")).click();
        Thread.sleep(1000);
    }

    /**
     * 删除环境配置
     *
     * @param driver
     * @throws InterruptedException
     */
    public static void deleteEnvConfig(WebDriver driver) throws InterruptedException {
        //点击删除
        driver.findElement(By.xpath("//tr[2]/td[3]/div/button[2]/span")).click();
        Thread.sleep(1000);
        //点击确认
        driver.findElement(By.xpath("//div[4]/div/div[3]/span/button/span")).click();
        Thread.sleep(1000);
    }

    /**
     * 添加应用
     *
     * @param driver
     * @param envName
     * @param envDesc
     * @throws InterruptedException
     */
    public static void addApp(WebDriver driver, String envName, String envDesc) throws InterruptedException {
        //添加
        driver.findElement(By.xpath("//span[contains(.,'添加')]")).click();
        Thread.sleep(1000);
        //点击环境配置输入框
        driver.findElement(By.xpath("(//input[@type='text'])[2]")).click();
        Thread.sleep(1000);
        //输入
        driver.findElement(By.xpath("//div/div/div/input")).sendKeys(envName);
        Thread.sleep(1000);
        //点击环境配置描述输入框
        driver.findElement(By.xpath("//textarea")).click();
        Thread.sleep(1000);
        //输入
        driver.findElement(By.xpath("//textarea")).sendKeys(envDesc);
        Thread.sleep(1000);
        //点击确认
        driver.findElement(By.xpath("//div[3]/span/button/span")).click();
        Thread.sleep(1000);
    }

    /**
     * 编辑应用
     *
     * @param driver
     * @throws InterruptedException
     */
    public static void updateApp(WebDriver driver) throws InterruptedException {
        //点击编辑
        driver.findElement(By.xpath("//tr[2]/td[4]/div/button/span")).click();
        Thread.sleep(1000);
        //点击确认
        driver.findElement(By.xpath("//div[3]/span/button/span")).click();
        Thread.sleep(1000);
    }

    /**
     * 删除应用
     *
     * @param driver
     * @throws InterruptedException
     */
    public static void deleteApp(WebDriver driver) throws InterruptedException {
        //点击删除
        driver.findElement(By.xpath("//tr[2]/td[4]/div/button[2]/span")).click();
        Thread.sleep(1000);
        //点击确认
        driver.findElement(By.xpath("//div[4]/div/div[3]/span/button/span")).click();
        Thread.sleep(1000);
    }

    /**
     * 编辑用户权限
     *
     * @param driver
     * @throws InterruptedException
     */
    public static void updateUserAuth(WebDriver driver) throws InterruptedException {
        //添加
        driver.findElement(By.xpath("//span[contains(.,'添加')]")).click();
        Thread.sleep(1000);
        //选择1
        driver.findElement(By.xpath("//div/label/span/span")).click();
        Thread.sleep(1000);
        //选择2
        driver.findElement(By.xpath("//label[2]/span/span")).click();
        Thread.sleep(1000);
        //选择3
        driver.findElement(By.xpath("//label[3]/span/span")).click();
        Thread.sleep(1000);
        //移动到右侧
        driver.findElement(By.xpath("//button[2]/span/i")).click();
        Thread.sleep(1000);
        //确认
        driver.findElement(By.xpath("//div[3]/span/button/span")).click();
        Thread.sleep(1000);
        //点击权限管理
        driver.findElement(By.xpath("//td[4]/div/button/span")).click();
        Thread.sleep(1000);
        //勾选环境
        driver.findElement(By.xpath("//label/span/span")).click();
        Thread.sleep(1000);
        //点击保存
        driver.findElement(By.xpath("//div[3]/span/button/span")).click();
        Thread.sleep(1000);
        //点击权限管理
        driver.findElement(By.xpath("//td[4]/div/button/span")).click();
        Thread.sleep(1000);
        //去勾选环境
        driver.findElement(By.xpath("//label/span/span")).click();
        Thread.sleep(1000);
        //点击保存
        driver.findElement(By.xpath("//div[3]/span/button/span")).click();
        Thread.sleep(1000);
        //添加
        driver.findElement(By.xpath("//span[contains(.,'添加')]")).click();
        Thread.sleep(1000);
        //全选
        driver.findElement(By.xpath("//div[3]/p/label/span/span")).click();
        Thread.sleep(1000);
        //移动到左侧
        driver.findElement(By.xpath("//div[2]/button/span/i")).click();
        Thread.sleep(1000);
        //确认
        driver.findElement(By.xpath("//div[6]/div/div[3]/span/button/span")).click();
        Thread.sleep(1000);
    }

    /**
     * 版本管理
     *
     * @param driver
     * @throws InterruptedException
     */
    public static void versionManage(WebDriver driver) throws InterruptedException {
        //点击操作下拉三角
        driver.findElement(By.xpath("//td[6]/div/div/button/span/i")).click();
        Thread.sleep(1000);
        //点击发布到指定环境
        driver.findElement(By.xpath("//body/ul/li/button")).click();
        Thread.sleep(1000);
        //点击下拉框
        driver.findElement(By.xpath("//form/div/div/div/div/input")).click();
        Thread.sleep(1000);
        //选择下拉框值
        driver.findElement(By.xpath("//div[3]/div/div/ul/li")).click();
        Thread.sleep(1000);
        //确定
        driver.findElement(By.xpath("//div[3]/span/button/span")).click();
        Thread.sleep(1000);
        //点击操作下拉三角
        driver.findElement(By.xpath("//td[6]/div/div/button/span/i")).click();
        Thread.sleep(1000);
        //点击【同步到统一调度】
        driver.findElement(By.xpath("//body/ul/li[2]/button/span")).click();
        Thread.sleep(1000);
        //点击删除
        driver.findElement(By.xpath("//span[contains(.,'删除')]")).click();
        Thread.sleep(1000);
        //取消删除
        driver.findElement(By.xpath("//div[5]/div/div[3]/span/button[2]/span")).click();
        Thread.sleep(1000);
        //点击【2.版本发布列表】
        driver.findElement(By.id("tab-second")).click();
        Thread.sleep(1000);
        //点击【3.统一调度列表】
        driver.findElement(By.id("tab-third")).click();
        Thread.sleep(1000);
        //点击【1.版本上传列表】
        driver.findElement(By.id("tab-first")).click();
        Thread.sleep(1000);
    }

    /**
     * 添加用户权限
     *
     * @param driver
     * @throws InterruptedException
     */
    public static void appManage(WebDriver driver) throws InterruptedException {
        //添加
        addApp(driver, "111", "222");
        //编辑
        updateApp(driver);
        //删除
        deleteApp(driver);
    }

    /**
     * 用户权限管理
     *
     * @param driver
     * @throws InterruptedException
     */
    public static void envConfigManage(WebDriver driver) throws InterruptedException {
        //添加
        PlatformManageUtil_2.addEnvConfig(driver, "111", "222");
        //编辑
        PlatformManageUtil_2.updateEnvConfig(driver);
        //删除
        PlatformManageUtil_2.deleteEnvConfig(driver);
    }
}
