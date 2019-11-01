package com.dcits.sonicAutoTest.UITest.util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * 【作业管理公共类】
 */
public class JobManageUtil_3 {

    /**
     * JOB运行信息筛选
     *
     * @param driver
     * @param jobName
     * @param jobStatus
     * @throws InterruptedException
     */
    public static void getJobRunListByCondition(WebDriver driver, String appName, String jobName, String jobStatus) throws InterruptedException {
        //应用名
        driver.findElement(By.xpath("//input[@type='text']")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//span[contains(.,"+"'"+appName+"'"+")]")).click();
        Thread.sleep(1000);
        //job名称
        driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div[2]/div/div/div/form/div[2]/div/div/div[1]")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div[2]/div/div/div/form/div[2]/div/div/div[1]/input")).sendKeys(jobName);
        Thread.sleep(1000);
        driver.findElement(By.xpath("//span[contains(.,"+"'"+jobName+"'"+")]")).click();
        Thread.sleep(1000);
        //搜索
        driver.findElement(By.xpath("//div[5]/div/button/span")).click();
        Thread.sleep(1000);
    }


    /**
     * 获取分段信息
     *
     * @param driver
     * @param stepStatus
     * @throws InterruptedException
     */
    public static void getStepSegmentRunningList(WebDriver driver, String stepStatus) throws InterruptedException {
        //展开【操作】下拉三角
        driver.findElement(By.xpath("//td[10]/div/div/button/span/i")).click();
        Thread.sleep(1000);
        //点击【查看分段运行信息】
        driver.findElement(By.xpath("//li[2]/button")).click();
        Thread.sleep(1000);
    }

    /**
     * JOB列表筛选
     *
     * @param driver
     * @param jobName
     * @param jobType
     * @throws InterruptedException
     */
    public static void getJobListByCondition(WebDriver driver, String appName, String jobName, String jobType) throws InterruptedException {
        //应用名
        driver.findElement(By.xpath("//input[@type='text']")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//span[contains(.,"+"'"+appName+"'"+")]")).click();
        Thread.sleep(500);
        //job名称
        driver.findElement(By.xpath("//div[2]/div/div/div/span/span/i")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div[2]/div/div/div/form/div[2]/div/div/div/input")).sendKeys(jobName);
        Thread.sleep(500);
        driver.findElement(By.xpath("//span[contains(.,"+"'"+jobName+"'"+")]")).click();
        Thread.sleep(500);
        //搜索
        driver.findElement(By.cssSelector(".el-icon-search")).click();
        Thread.sleep(1000);
    }

    /**
     * 查看JOB定义,启动JOB
     *
     * @param driver
     * @throws InterruptedException
     */
    public static void getJobDefinedDetail(WebDriver driver) throws InterruptedException {
        //下拉三角
        driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div[2]/div/div/div/div[2]/div[3]/table/tbody/tr/td[9]/div/div")).click();
        Thread.sleep(1500);
        //查看
        driver.findElement(By.xpath("//body/ul/li")).click();
        Thread.sleep(1500);
    }

    /**
     * 定时任务启停
     *
     * @param driver
     * @throws InterruptedException
     */
    public static void startAndStopTimedJob(WebDriver driver) throws InterruptedException {
        // 点击【操作】下拉三角
        driver.findElement(By.xpath("//td[8]/div/div/button/span")).click();
        Thread.sleep(1000);
        //点击启动
        driver.findElement(By.xpath("//body/ul/li[3]/button/span")).click();
        Thread.sleep(1000);
        //点击确认
        driver.findElement(By.xpath("//div[3]/span/button/span")).click();
        Thread.sleep(3000);
    }

    /**
     * 启动运行JOB
     *
     * @param driver
     * @param jobName
     * @param jobType
     * @throws InterruptedException
     */
    public static void startJob(WebDriver driver,String appName, String jobName, String jobType) throws InterruptedException {
        //进入JOB定义界面
        getJobDefinedDetail(driver);
        //普通任务启动方式
        if (LoginUtil.JOB_TYPE.普通任务.toString().equals(jobType)) {
            driver.findElement(By.cssSelector(".el-button--warning:nth-child(1) > span")).click();
            Thread.sleep(1000);
            driver.findElement(By.cssSelector(".el-dialog__footer:nth-child(3) .el-button--primary > span")).click();
            Thread.sleep(3000);
        }
        //定时任务启动方式
        else if (LoginUtil.JOB_TYPE.定时任务.toString().equals(jobType)) {
            MenuBarUtil.goToJobListView_42(driver);
            getJobListByCondition(driver,appName, jobName, jobType);
            startAndStopTimedJob(driver);
        }

    }

    /**
     * STEP详细信息
     *
     * @param driver
     * @throws InterruptedException
     */
    public static void getStepRunDetail(WebDriver driver) throws InterruptedException {
        //下拉三角
        driver.findElement(By.xpath("//div[2]/table/tbody/tr/td[8]/div/div/button/span/i")).click();
        Thread.sleep(500);
        //查看STEP运行信息
        driver.findElement(By.xpath("//body/ul/li[1]/button/span")).click();
        Thread.sleep(1000);
    }

    /**
     * STEP运行信息筛选
     *
     * @param driver
     * @param stepStatus
     * @throws InterruptedException
     */
    public static void getStepListByCondition(WebDriver driver, String stepStatus) throws InterruptedException {
        //运行状态
        driver.findElement(By.xpath("//span/span/i")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//li[contains(.,'" + stepStatus + "')]")).click();
        Thread.sleep(500);
        //展开下拉三角，查看STEP运行的详细信息
        driver.findElement(By.xpath("//div/div/i")).click();
        Thread.sleep(3000);
        //收缩下拉三角
        driver.findElement(By.xpath("//div/div/i")).click();
        Thread.sleep(500);
    }

    /**
     * 分段错误信息
     *
     * @param driver
     * @throws InterruptedException
     */
    public static void getSegmentErrorInfo(WebDriver driver) throws InterruptedException {
        //点击【错误信息】
        driver.findElement(By.xpath("//td[9]/div/button/i")).click();
        Thread.sleep(2000);
        //关闭【错误信息】界面
        driver.findElement(By.cssSelector(".el-dialog__wrapper:nth-child(11) .el-dialog__close")).click();
        Thread.sleep(1000);
    }

    /**
     * STEP错误信息
     *
     * @param driver
     * @throws InterruptedException
     */
    public static void getStepErrorInfo(WebDriver driver) throws InterruptedException {
        //下拉三角
        driver.findElement(By.xpath("//td[10]/div/div/button/span/i")).click();
        Thread.sleep(1000);
        //查看错误运行信息
        driver.findElement(By.xpath("//li/button")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//div[6]/div/div/button/i")).click();
        Thread.sleep(1000);
    }

    /**
     * 查看STEP组信息
     *
     * @param driver
     * @throws InterruptedException
     */
    public static void getStepGroupInfo(WebDriver driver) throws InterruptedException {
        //展开【操作】下拉三角
        driver.findElement(By.xpath("//td[10]/div/div/button/span/i")).click();
        Thread.sleep(1000);
        //点击【查看STEP组信息】
        driver.findElement(By.xpath("//li[2]/button/span")).click();
        Thread.sleep(1000);
    }


    /**
     * JOB运行历史筛选
     *
     * @param driver
     * @param jobName
     * @throws InterruptedException
     */
    public static void getJobHisListByCondition(WebDriver driver, String appName, String jobName) throws InterruptedException {
        //应用名称
        driver.findElement(By.xpath("//input[@type='text']")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("//span[contains(.,"+"'"+appName+"'"+")]")).click();
        Thread.sleep(1000);
        //job名称
        driver.findElement(By.xpath("//div[2]/div/div/div/span/span/i")).click();
        Thread.sleep(1000);
        driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div[2]/div/div/div/form/div[2]/div/div/div/input")).sendKeys(jobName);
        Thread.sleep(1000);
        driver.findElement(By.xpath("//span[contains(.,"+"'"+jobName+"'"+")]")).click();
        Thread.sleep(1000);
        //搜索
        driver.findElement(By.cssSelector(".el-form-item__content > .el-button")).click();
        Thread.sleep(1000);
    }

    /**
     * JOB运行历史测试
     *
     * @param driver
     * @param jobName
     * @param stepStatus
     * @throws InterruptedException
     */
    public static void jobHisList_33(WebDriver driver, String appName, String jobName, String stepStatus) throws InterruptedException {
        getJobHisListByCondition(driver,appName, jobName);
    }

    /**
     * JOB列表测试
     *
     * @param driver
     * @param jobName
     * @param jobType
     * @throws InterruptedException
     */
    public static void jobList_31(WebDriver driver, String appName, String jobName, String jobType) throws InterruptedException {
        //JOB列表查看
        getJobListByCondition(driver, appName,jobName, jobType);
        //启动JOB
        startJob(driver, appName,jobName, jobType);

    }

    /**
     * JOB运行信息测试
     *
     * @param driver
     * @param jobName
     * @param jobStatus
     * @param stepStatus
     * @throws InterruptedException
     */
    public static void jobRunList_32(WebDriver driver, String appName, String jobName, String jobStatus, String stepStatus) throws InterruptedException {
        getJobRunListByCondition(driver,appName, jobName, jobStatus);
        getStepRunDetail(driver);
        getStepListByCondition(driver, stepStatus);
        //1.查看step错误运行信息和step组信息
        getStepErrorInfo(driver);
    }

}