package com.dcits.selenium.oms;

import com.dcits.util.DriverUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

import java.util.Map;

public class AddApplication {
    public static void addOMSApplication(String nodeName,String address,String path,String serviceName) throws InterruptedException {
        WebDriver driver;
        Map<String, Object> vars;
        JavascriptExecutor js;
        driver = DriverUtil.getFiedFoxDriver("/Users/yangguangyu/app/firefoxdriver/geckodriver");
        driver.get("http://10.7.19.111:8000/");
        driver.manage().window().maximize();
        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("123456");
        driver.findElement(By.cssSelector(".el-button")).click();
        Thread.sleep(1500);

        {
            WebElement element = driver.findElement(By.cssSelector("span"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
            Thread.sleep(1500);
        }
        driver.findElement(By.cssSelector(".el-submenu:nth-child(5) > .el-submenu__title")).click();
        Thread.sleep(1500);
        driver.findElement(By.cssSelector(".is-opened > .el-menu .el-submenu__title")).click();
        Thread.sleep(1500);
        driver.findElement(By.cssSelector(".is-opened .el-menu-item:nth-child(3)")).click();
        Thread.sleep(1500);
        driver.findElement(By.cssSelector(".button-group")).click();
        Thread.sleep(1500);
        driver.findElement(By.cssSelector(".el-button--warning")).click();
        Thread.sleep(1500);
        {
            WebElement element = driver.findElement(By.cssSelector(".el-button--warning"));
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
        driver.findElement(By.cssSelector(".el-form-item:nth-child(2) .el-input__inner")).click();
        Thread.sleep(1500);
        driver.findElement(By.cssSelector(".el-form-item:nth-child(2) .el-input__inner")).sendKeys(nodeName);
        Thread.sleep(1500);
        driver.findElement(By.cssSelector(".el-form-item:nth-child(3) .el-input__inner")).click();
        Thread.sleep(1500);
        driver.findElement(By.xpath("/html/body/div[3]/div[1]/div[1]/ul/li[1]")).click();
        Thread.sleep(1500);
        driver.findElement(By.cssSelector(".el-form-item:nth-child(4) .el-input__inner")).click();
        Thread.sleep(1500);
        driver.findElement(By.cssSelector(".el-form-item:nth-child(4) .el-input__inner")).sendKeys(serviceName);//安装名设置为服务名
        Thread.sleep(1500);
        driver.findElement(By.cssSelector(".el-form-item:nth-child(5) .el-input__inner")).click();
        Thread.sleep(1500);
        driver.findElement(By.cssSelector(".el-form-item:nth-child(5) .el-input__inner")).sendKeys(path);
        Thread.sleep(1500);
        driver.findElement(By.cssSelector(".el-col > .el-autocomplete .el-input__inner")).click();
        Thread.sleep(1500);
        driver.findElement(By.cssSelector(".el-col > .el-autocomplete .el-input__inner")).sendKeys(address);
        Thread.sleep(1500);
//        driver.findElement(By.id("el-autocomplete-6637-item-0")).click();
//        Thread.sleep(1500);
        driver.findElement(By.cssSelector("div.el-form-item:nth-child(7) > label:nth-child(1)")).click();
        Thread.sleep(1500);
        driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div[2]/div/div/div/div[2]/div[2]/div/div[2]/form/div[7]/div/div/div/div/div/input")).click();
        Thread.sleep(1500);
        driver.findElement(By.xpath("/html/body/div[5]/div[1]/div[1]/ul/li")).click();
        Thread.sleep(1500);
        driver.findElement(By.cssSelector(".is-required > .el-form-item__content > .el-input > .el-input__inner")).click();
        Thread.sleep(1500);
        driver.findElement(By.cssSelector(".is-required > .el-form-item__content > .el-input > .el-input__inner")).sendKeys(serviceName);
        Thread.sleep(1500);
        driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div[2]/div/div/div/div[2]/div[2]/div/div[3]/span/button[1]")).click();
        Thread.sleep(1500);


        driver.close();
    }
}
