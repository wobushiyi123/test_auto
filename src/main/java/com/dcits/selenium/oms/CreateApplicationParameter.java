package com.dcits.selenium.oms;

import com.dcits.util.DriverUtil;
import com.dcits.util.TimeUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import java.util.*;

public class CreateApplicationParameter {

    public static void setApplicationParameter(String appType) throws InterruptedException {
//        WebDriver driver = DriverUtil.("/Users/yangguangyu/app/Chromedriver/chromedriver");
        WebDriver driver;
        Map<String, Object> vars;
        JavascriptExecutor js;
        driver = DriverUtil.getFiedFoxDriver("/Users/yangguangyu/app/firefoxdriver/geckodriver");
        driver.get("http://10.7.19.111:8000/");
        driver.manage().window().maximize();

        driver.findElement(By.name("username")).click();
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("123456");
        driver.findElement(By.cssSelector("span")).click();
        {
            WebElement element = driver.findElement(By.cssSelector("span"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
            Thread.sleep(2000);
        }
        {
            WebElement element = driver.findElement(By.tagName("body"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element, 0, 0).perform();
            Thread.sleep(2000);
        }
        {
            WebElement element = driver.findElement(By.linkText("主机列表"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
            Thread.sleep(2000);
        }
        {
            WebElement element = driver.findElement(By.tagName("body"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element, 0, 0).perform();
            Thread.sleep(2000);
        }
        driver.findElement(By.cssSelector(".el-menu-vertical-demo > .el-submenu:nth-child(2) > .el-submenu__title")).click();
        Thread.sleep(2000);
        driver.findElement(By.cssSelector(".el-menu-item:nth-child(5)")).click();
        Thread.sleep(2000);
        driver.findElement(By.cssSelector(".el-button--warning > span")).click();
        Thread.sleep(2000);
        {
            WebElement element = driver.findElement(By.cssSelector(".el-button--warning > span"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
            Thread.sleep(2000);
        }
        {
            WebElement element = driver.findElement(By.tagName("body"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element, 0, 0).perform();
            Thread.sleep(2000);
        }
        driver.findElement(By.cssSelector(".el-form-item:nth-child(1) > .el-form-item__content > .el-input > .el-input__inner")).click();
        Thread.sleep(2000);
        driver.findElement(By.cssSelector(".el-form-item:nth-child(1) > .el-form-item__content > .el-input > .el-input__inner")).sendKeys("测试应用类型"+ TimeUtil.gerCurrentDate());
        Thread.sleep(2000);
        driver.findElement(By.cssSelector(".el-form-item:nth-child(2) .el-input__inner")).click();
        Thread.sleep(2000);
        driver.findElement(By.cssSelector(".el-form-item:nth-child(2) .el-input__inner")).sendKeys(appType);
        Thread.sleep(2000);
        driver.findElement(By.cssSelector(".el-form-item:nth-child(3) .el-input__inner")).click();
        Thread.sleep(2000);
        driver.findElement(By.cssSelector(".el-form-item:nth-child(3) .el-input__inner")).sendKeys("AppType");
        Thread.sleep(2000);
        driver.findElement(By.cssSelector(".el-form-item:nth-child(4) .el-input__inner")).click();
        Thread.sleep(2000);
        driver.findElement(By.cssSelector(".el-form-item:nth-child(4) .el-input__inner")).sendKeys("测试账户服务"+ TimeUtil.gerCurrentDate());
        Thread.sleep(2000);
        driver.findElement(By.cssSelector(".dialog-footer > .el-button--small:nth-child(1)")).click();
        {
            /*
            * 完成添加，点击确定
            * */
            WebElement element = driver.findElement(By.cssSelector(".dialog-footer > .el-button--small:nth-child(1)"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
            Thread.sleep(2000);
        }

        driver.findElement(By.cssSelector(".el-button--warning")).click();
        Thread.sleep(2000);
        driver.findElement(By.cssSelector(".el-form-item:nth-child(1) > .el-form-item__content > .el-input > .el-input__inner")).click();
        Thread.sleep(2000);
        driver.findElement(By.cssSelector(".el-form-item:nth-child(1) > .el-form-item__content > .el-input > .el-input__inner")).sendKeys(appType+".START");
        Thread.sleep(2000);
        driver.findElement(By.cssSelector(".el-form-item:nth-child(2) .el-input__inner")).click();
        Thread.sleep(2000);
        driver.findElement(By.cssSelector(".el-form-item:nth-child(2) .el-input__inner")).sendKeys("cd $APP_HOME_DIR/bin && ./start.sh");
        Thread.sleep(2000);
        driver.findElement(By.cssSelector(".el-form-item:nth-child(3) .el-input__inner")).click();
        Thread.sleep(2000);
        driver.findElement(By.cssSelector(".el-form-item:nth-child(3) .el-input__inner")).sendKeys("OperCommand");
        Thread.sleep(2000);
        driver.findElement(By.cssSelector(".el-form-item:nth-child(4) .el-input__inner")).click();
        Thread.sleep(2000);
        driver.findElement(By.cssSelector(".el-form-item:nth-child(4) .el-input__inner")).sendKeys("应用启动脚本");
        Thread.sleep(2000);
        driver.findElement(By.cssSelector(".el-button--small:nth-child(1) > span:nth-child(1)")).click();
        Thread.sleep(2000);
        driver.findElement(By.cssSelector(".el-button--warning > span")).click();
        Thread.sleep(2000);
        driver.findElement(By.cssSelector(".el-form-item:nth-child(1) > .el-form-item__content > .el-input > .el-input__inner")).click();
        Thread.sleep(2000);
        driver.findElement(By.cssSelector(".el-form-item:nth-child(1) > .el-form-item__content > .el-input > .el-input__inner")).sendKeys(appType+".STOP");
        Thread.sleep(2000);
        driver.findElement(By.cssSelector(".el-form-item:nth-child(2) .el-input__inner")).click();
        Thread.sleep(2000);
        driver.findElement(By.cssSelector(".el-form-item:nth-child(2) .el-input__inner")).sendKeys("cd $APP_HOME_DIR/bin&& ./stop.sh");
        Thread.sleep(2000);
        driver.findElement(By.cssSelector(".el-form-item:nth-child(3) .el-input__inner")).click();
        Thread.sleep(2000);
        driver.findElement(By.cssSelector(".el-form-item:nth-child(3) .el-input__inner")).sendKeys("OperCommand");
        Thread.sleep(2000);
        driver.findElement(By.cssSelector(".el-form-item:nth-child(4) .el-input__inner")).click();
        Thread.sleep(2000);
        driver.findElement(By.cssSelector(".el-form-item:nth-child(4) .el-input__inner")).sendKeys("账户服务停止脚本");
        Thread.sleep(2000);
        driver.findElement(By.cssSelector(".dialog-footer > .el-button--small:nth-child(1)")).click();
        {
            WebElement element = driver.findElement(By.cssSelector(".dialog-footer > .el-button--small:nth-child(1)"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
            Thread.sleep(2000);
        }
        {
            WebElement element = driver.findElement(By.tagName("body"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element, 0, 0).perform();
            Thread.sleep(2000);
        }
        driver.findElement(By.cssSelector(".el-button--warning > span")).click();
        Thread.sleep(2000);
        {
            WebElement element = driver.findElement(By.cssSelector(".el-button--warning > span"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
            Thread.sleep(2000);
        }
        {
            WebElement element = driver.findElement(By.tagName("body"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element, 0, 0).perform();
            Thread.sleep(2000);
        }
        driver.findElement(By.cssSelector(".el-form-item:nth-child(1) > .el-form-item__content > .el-input > .el-input__inner")).click();
        Thread.sleep(2000);
        driver.findElement(By.cssSelector(".el-form-item:nth-child(1) > .el-form-item__content > .el-input > .el-input__inner")).sendKeys(appType+".STATUS");
        Thread.sleep(2000);
        driver.findElement(By.cssSelector(".el-form-item:nth-child(2) .el-input__inner")).click();
        Thread.sleep(2000);
        driver.findElement(By.cssSelector(".el-form-item:nth-child(2) .el-input__inner")).sendKeys("ps -ef|grep $APP_HOME_DIR|grep -v grep");
        Thread.sleep(2000);
        driver.findElement(By.cssSelector(".el-form-item:nth-child(3) .el-input__inner")).click();
        Thread.sleep(2000);
        driver.findElement(By.cssSelector(".el-form-item:nth-child(3) .el-input__inner")).sendKeys("OperCommand");
        Thread.sleep(2000);
        driver.findElement(By.cssSelector(".el-form-item:nth-child(4) .el-input__inner")).click();
        Thread.sleep(2000);
        driver.findElement(By.cssSelector(".el-form-item:nth-child(4) .el-input__inner")).sendKeys("账户服务状态脚本");
        Thread.sleep(2000);
        driver.findElement(By.cssSelector(".el-button--small:nth-child(1) > span:nth-child(1)")).click();
        Thread.sleep(2000);
        {
            WebElement element = driver.findElement(By.tagName("body"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element, 0, 0).perform();
            Thread.sleep(2000);
        }
        driver.quit();
    }
}
