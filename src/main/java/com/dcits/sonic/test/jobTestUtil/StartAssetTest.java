package com.dcits.sonic.test.jobTestUtil;

import org.testng.TestNG;
import org.testng.collections.Lists;

import java.io.File;
import java.util.List;

/**
 * @program: sonicAtuoTest
 * @description: 接口自动化测试main方法启动
 * @author: liu yan
 * @create: 2019-11-04 08:56
 */
public class StartAssetTest {
    public static void main(String[] args) {
        TestNG testng = new TestNG();
        List suites = Lists.newArrayList();
        //testng启动类文件
        String jobTestPath =  System.getProperty("user.dir")
                + File.separator
                + "src"
                + File.separator
                + "main"
                + File.separator
                + "resources"
                + File.separator
                + "testNG"
                + File.separator+"jobTest.xml";
        suites.add(jobTestPath);//path to xml..
        testng.setTestSuites(suites);
        testng.run();
    }

}
