package com.dcits.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverUtil {

    public static WebDriver getChromeDriver(String driverUrl){
        System.setProperty("webdriver.chrome.driver",driverUrl);
        return new ChromeDriver();
    }
    public static WebDriver getFiedFoxDriver(String driverUrl){
        System.setProperty("webdriver.gecko.driver",driverUrl);
        return new FirefoxDriver();
    }



}
