package com.dcits.selenium.oms;

import com.dcits.util.DriverUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.Map;

public class UploadVersion {

    public static void uploadOMSApplicationVersion(String versionName,String versionNo,String filePath) throws InterruptedException {
        WebDriver driver;
        Map<String, Object> vars;
        JavascriptExecutor js;
        driver = DriverUtil.getFiedFoxDriver("/Users/yangguangyu/app/firefoxdriver/geckodriver");
        driver.get("http://10.7.19.111:8000/");
        driver.manage().window().maximize();
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("123456");
        driver.findElement(By.name("password")).sendKeys(Keys.ENTER);
        Thread.sleep(1500);
        driver.findElement(By.cssSelector(".el-submenu:nth-child(5) > .el-submenu__title > span")).click();
        Thread.sleep(1500);
        driver.findElement(By.cssSelector(".is-opened > .el-menu .el-submenu__title > span")).click();
        Thread.sleep(1500);
        driver.findElement(By.cssSelector(".is-opened .el-menu-item:nth-child(2) > span")).click();
        Thread.sleep(1500);
        driver.findElement(By.cssSelector(".el-button--warning")).click();
        Thread.sleep(1500);
        driver.findElement(By.cssSelector(".input-file")).sendKeys(filePath);
        Thread.sleep(2000);
        driver.findElement(By.cssSelector(".el-form-item--feedback .el-select .el-input__inner")).click();
        Thread.sleep(1500);
        driver.findElement(By.xpath("/html/body/div[3]/div[1]/div[1]/ul/li[1]")).click();
        Thread.sleep(1500);
        driver.findElement(By.cssSelector(".el-row:nth-child(6) .el-input__inner")).click();
        Thread.sleep(1500);
        driver.findElement(By.cssSelector(".el-row:nth-child(6) .el-input__inner")).sendKeys(versionName);
        Thread.sleep(1500);
        driver.findElement(By.cssSelector(".el-row:nth-child(7) .el-input__inner")).click();
        Thread.sleep(1500);
        driver.findElement(By.cssSelector(".el-row:nth-child(7) .el-input__inner")).sendKeys(versionNo);
        Thread.sleep(1500);
        driver.findElement(By.cssSelector(".el-textarea__inner")).click();
        Thread.sleep(1500);
        driver.findElement(By.cssSelector(".el-dialog__footer:nth-child(3) .el-button--primary > span")).click();
        Thread.sleep(1500);
        {
            WebElement element = driver.findElement(By.cssSelector(".el-dialog__footer:nth-child(3) .el-button--primary > span"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
            Thread.sleep(1500);
        }
        {
            WebElement element = driver.findElement(By.tagName("body"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element, 0, 0).perform();
            Thread.sleep(1500);
        }
        int percent = 0;
        while (percent != 100){
            percent = Integer.parseInt(driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div[2]/div/div/div/div[4]/div/div[2]/form/div[9]/div/div/div/div/div/div/div/div")).getText().replace("%",""));
            Thread.sleep(1000);
            System.out.println(percent);
        }

        Thread.sleep(1500);
        driver.close();
    }
}
