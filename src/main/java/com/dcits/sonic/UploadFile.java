package com.dcits.sonic;


import com.dcits.util.DriverUtil;
import com.dcits.util.PropertyUtil;
import com.dcits.util.TimeUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class UploadFile {

    static WebDriver driver;
    @BeforeSuite
    public void login() throws InterruptedException {
        String driverPath = PropertyUtil.getProperty("webDriverPath");
        driver = DriverUtil.getChromeDriver(driverPath);
        driver.manage().window().maximize();
        driver.get(PropertyUtil.getProperty("sonic.managerURL"));
        WebElement userName = driver.findElement(By.name("username"));
        userName.sendKeys(PropertyUtil.getProperty("sonic.username"));
        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys(PropertyUtil.getProperty("sonic.password"));
        WebElement commit = driver.findElement(By.xpath("/html/body/div/div/div/div/div/button"));
        commit.click();
        Thread.sleep(1000);
    }
    @Test
    public void toUpFile() throws InterruptedException {
        WebElement platManage = driver.findElement(By.xpath("/html/body/div/div/div[1]/ul/li[2]/div"));
        platManage.click();
        Thread.sleep(1000);
        WebElement versionManage = driver.findElement(By.xpath("/html/body/div/div/div[1]/ul/li[2]/ul/li[4]"));
        versionManage.click();
        Thread.sleep(1000);
        WebElement selectApp = driver.findElement(By.xpath("//*[@id=\"pane-first\"]/div/div[1]/div/div/div"));
        selectApp.click();
        Thread.sleep(1000);
        WebElement selectApp$ = driver.findElement(By.xpath("/html/body/div[2]/div[1]/div[1]/ul/li[2]"));
        selectApp$.click();
        Thread.sleep(1000);
        WebElement upFileButton = driver.findElement(By.xpath("//*[@id=\"pane-first\"]/div/div[1]/div/button"));
        upFileButton.click();
        Thread.sleep(1000);
        WebElement fileDesc = driver.findElement(By.xpath("//*[@id=\"pane-first\"]/div/div[4]/div/div[2]/div[1]/input"));
        fileDesc.sendKeys("test File release"+ TimeUtil.gerCurrentDate());
        Thread.sleep(1000);
        WebElement fileUp = driver.findElement(By.xpath("//*[@id=\"pane-first\"]/div/div[4]/div/div[2]/div[2]/div/input"));
        fileUp.sendKeys("/Users/yangguangyu/Desktop/app1.zip");
        Thread.sleep(15000);
        WebElement releaseOperate = driver.findElement(By.xpath("//*[@id=\"pane-first\"]/div/div[2]/div[1]/div[3]/table/tbody/tr[1]/td[6]/div/div/button/span"));
        releaseOperate.click();
        Thread.sleep(1000);
        WebElement selectENV = driver.findElement(By.xpath("//*[@id=\"dropdown-menu-7417\"]/li[1]/button"));
        selectENV.click();
        Thread.sleep(1000);
        WebElement selectENV1 = driver.findElement(By.xpath("//*[@id=\"pane-first\"]/div/div[3]/div/div[2]/form/div/div/div/div/input"));
        selectENV1.click();
        Thread.sleep(1000);
        WebElement selectENV2 = driver.findElement(By.xpath("/html/body/div[3]/div[1]/div[1]/ul/li"));
        selectENV2.click();
        Thread.sleep(1000);
        WebElement confirmUpLoad = driver.findElement(By.xpath("//*[@id=\"pane-first\"]/div/div[3]/div/div[3]/span/button[1]"));
        confirmUpLoad.click();
        Assert.assertEquals("success",driver.switchTo().alert());
    }
    @AfterSuite
    public void closeDriver(){
        driver.quit();
    }


}
