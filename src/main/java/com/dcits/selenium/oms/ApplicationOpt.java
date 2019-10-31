package com.dcits.selenium.oms;

import com.dcits.util.DriverUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.Map;

public class ApplicationOpt {
    public static void deployOMSApplication() throws InterruptedException {
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
        driver.findElement(By.cssSelector(".el-submenu:nth-child(5) > .el-submenu__title")).click();
        Thread.sleep(1500);
        driver.findElement(By.cssSelector(".is-opened > .el-menu .el-submenu__title")).click();
        Thread.sleep(1500);
        driver.findElement(By.cssSelector(".is-opened .el-menu-item:nth-child(3)")).click();
        Thread.sleep(1500);
        driver.findElement(By.cssSelector(".el-table__body-wrapper .el-table__row:nth-child(1) .el-checkbox__inner")).click();
        Thread.sleep(1500);
        driver.findElement(By.cssSelector(".el-table__body-wrapper .el-table__row:nth-child(2) .el-checkbox__inner")).click();
        Thread.sleep(1500);
        driver.findElement(By.cssSelector(".el-table__body-wrapper .el-table__row:nth-child(3) .el-checkbox__inner")).click();
        Thread.sleep(1500);
        driver.findElement(By.cssSelector(".inline-block > .el-button:nth-child(1)")).click();
        Thread.sleep(1500);
        driver.findElement(By.cssSelector(".el-button--primary:nth-child(2) > span")).click();
        Thread.sleep(1500);
        String deployResult1 = "";
        String deployResult2 = "";
        String deployResult3 = "";
        while (deployResult1.equals(deployResult2) && deployResult2.equals(deployResult3) && !deployResult1.equals("已停止")) {
            deployResult1 = driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div[2]/div/div/div/div[2]/div[1]/div[3]/table/tbody/tr[1]/td[4]/div/span")).getText();
            deployResult2 = driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div[2]/div/div/div/div[2]/div[1]/div[3]/table/tbody/tr[2]/td[4]/div/span")).getText();
            deployResult3 = driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div[2]/div/div/div/div[2]/div[1]/div[3]/table/tbody/tr[3]/td[4]/div/span")).getText();
        }
        driver.close();
    }
    public static void startApplicatio() throws InterruptedException {
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
        driver.findElement(By.cssSelector(".el-submenu:nth-child(5) > .el-submenu__title")).click();
        Thread.sleep(1500);
        driver.findElement(By.cssSelector(".is-opened > .el-menu .el-submenu__title")).click();
        Thread.sleep(1500);
        driver.findElement(By.cssSelector(".is-opened .el-menu-item:nth-child(3)")).click();
        Thread.sleep(1500);
        driver.findElement(By.cssSelector(".el-table__body-wrapper .el-table__row:nth-child(1) .el-checkbox__inner")).click();
        Thread.sleep(1500);
        driver.findElement(By.cssSelector(".el-table__body-wrapper .el-table__row:nth-child(2) .el-checkbox__inner")).click();
        Thread.sleep(1500);
        driver.findElement(By.cssSelector(".el-table__body-wrapper .el-table__row:nth-child(3) .el-checkbox__inner")).click();
        Thread.sleep(1500);
        driver.findElement(By.cssSelector(".el-button--primary:nth-child(3) > span")).click();
        Thread.sleep(1500);
        driver.findElement(By.cssSelector(".el-button--primary:nth-child(2) > span")).click();
        Thread.sleep(1500);
//        {
//            WebElement element = driver.findElement(By.cssSelector(".el-button--primary:nth-child(2) > span"));
//            Actions builder = new Actions(driver);
//            builder.moveToElement(element).perform();
//        }
//        {
//            WebElement element = driver.findElement(By.tagName("body"));
//            Actions builder = new Actions(driver);
//            builder.moveToElement(element, 0, 0).perform();
//        }
        String startResult1 = "";
        String startResult2 = "";
        String startResult3 = "";
        while (!(startResult1.equals("运行中")&&startResult2.equals("运行中")&&startResult3.equals("运行中"))){
            startResult1 = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[2]/div/div/div/div[2]/div[1]/div[3]/table/tbody/tr[1]/td[4]/div/span/text()")).getText();
            startResult2 = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[2]/div/div/div/div[2]/div[1]/div[3]/table/tbody/tr[2]/td[4]/div/span/text()")).getText();
            startResult3 = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[2]/div/div/div/div[2]/div[1]/div[3]/table/tbody/tr[3]/td[4]/div/span/text()")).getText();
        }
        driver.quit();
    }
}
