package com.dcits.selenium.sonic.sonicAutoTest.util;

import com.dcits.selenium.sonic.sonicAutoTest.util.LoginUtil;
import com.dcits.selenium.sonic.sonicAutoTest.util.MenuBarUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * 【统一调度公共类】
 */
public class UniteDispatchUtil_4 {

    /**
     * JOB运行信息筛选
     *
     * @param driver
     * @param jobName
     * @param jobStatus
     * @throws InterruptedException
     */
    public static void getJobRunListByCondition(WebDriver driver, String jobName, String jobStatus) throws InterruptedException {
        //JOB名称
        driver.findElement(By.xpath("//span/span/i")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//input")).sendKeys(jobName);
        Thread.sleep(500);
        driver.findElement(By.xpath("//span[contains(.,'" + jobName + "')]")).click();
        Thread.sleep(500);
        //运行状态
        driver.findElement(By.xpath("(//input[@type='text'])[2]")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//div[3]/div/div/ul/li[2]/span")).click();
        Thread.sleep(500);
        //搜索
        driver.findElement(By.cssSelector(".el-button > span:nth-child(2)")).click();
        Thread.sleep(1000);
    }


    /**
     * 获取分段信息
     *
     * @param driver
     * @throws InterruptedException
     */
    public static void getStepSegmentList(WebDriver driver) throws InterruptedException {
        //下拉三角
        driver.findElement(By.xpath("//td[10]/div/div/button/span/i")).click();
        Thread.sleep(1000);
        //查看分段运行信息
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
    public static void getJobDefinedListByCondition(WebDriver driver, String jobName, String jobType) throws InterruptedException {
        //JOB名称
        driver.findElement(By.xpath("//input")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//input")).sendKeys(jobName);
        Thread.sleep(500);
        driver.findElement(By.xpath("//span[contains(.,'" + jobName + "')]")).click();
        Thread.sleep(500);
        //任务类型
        driver.findElement(By.xpath("(//input[@type='text'])[2]")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//span[contains(.,'" + jobType + "')]")).click();
        Thread.sleep(500);
        //搜索
        driver.findElement(By.cssSelector(".el-button > span:nth-child(2)")).click();
        Thread.sleep(1000);
    }

    /**
     * 查看JOB定义
     *
     * @param driver
     * @throws InterruptedException
     */
    public static void getJobDefinedDetail(WebDriver driver) throws InterruptedException {
        // 点击【操作】下拉三角
        driver.findElement(By.xpath("//td[8]/div/div/button/span")).click();
        Thread.sleep(1000);
        //点击【查看】，进入【JOB定义信息图】界面
        driver.findElement(By.xpath("//body/ul/li/button/span")).click();
        Thread.sleep(1000);
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
    public static void startJob(WebDriver driver, String jobName, String jobType) throws InterruptedException {
        //进入JOB定义界面
        getJobDefinedDetail(driver);
        //普通JOB启动方式
        if (LoginUtil.JOB_TYPE.普通任务.toString().equals(jobType)) {
            driver.findElement(By.cssSelector(".el-button--warning:nth-child(1) > span")).click();
            Thread.sleep(1000);
            driver.findElement(By.cssSelector(".el-dialog__footer:nth-child(3) .el-button--primary > span")).click();
            Thread.sleep(3000);
        }
        //定时JOB启动方式
        else if (LoginUtil.JOB_TYPE.定时任务.toString().equals(jobType)) {
            MenuBarUtil.goToJobListView_42(driver);
            getJobDefinedListByCondition(driver, jobName, jobType);
            startAndStopTimedJob(driver);
        }

    }

    /**
     * JOB运行信息
     *
     * @param driver
     * @param jobName
     * @param jobStatus
     * @throws InterruptedException
     */
    public static void getJobRunningDetail(WebDriver driver, String jobName, String jobStatus) throws InterruptedException {
        //展开【操作】下拉三角
        driver.findElement(By.xpath("//div[2]/table/tbody/tr/td[8]/div/div/button/span/i")).click();
        Thread.sleep(2000);
        //点击【查看JOB运行信息】，进入【JOB运行信息图】界面
        driver.findElement(By.xpath("//body/ul/li[2]/button/span")).click();
        Thread.sleep(1000);
    }

    /**
     * JOB运行历史
     *
     * @param driver
     * @throws InterruptedException
     */
    public static void getJobRunningHistoryDetail(WebDriver driver) throws InterruptedException {
        //展开【操作】下拉三角
        driver.findElement(By.xpath("//div[2]/table/tbody/tr/td[8]/div/div/button/span/i")).click();
        Thread.sleep(2000);
        //点击【查看JOB运行信息】，进入【JOB运行信息图】界面
        driver.findElement(By.xpath("//body/ul/li[2]/button/span")).click();
        Thread.sleep(1000);
    }

    /**
     * STEP详细信息
     *
     * @param driver
     * @throws InterruptedException
     */
    public static void getStepRunningHistoryDetail(WebDriver driver) throws InterruptedException {
        //展开【操作】下拉三角
        driver.findElement(By.xpath("//div[2]/table/tbody/tr/td[8]/div/div/button/span/i")).click();
        Thread.sleep(2000);
        //点击【查看JOB运行信息】，进入【JOB运行信息图】界面
        driver.findElement(By.xpath("//body/ul/li[1]/button/span")).click();
        Thread.sleep(1000);
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
    }

    /**
     * 分段错误信息
     *
     * @param driver
     * @throws InterruptedException
     */
    public static void getSegmentErrorInfo(WebDriver driver) throws InterruptedException {
        //错误信息
        driver.findElement(By.xpath("//td[9]/div/button/i")).click();
        Thread.sleep(2000);
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
        //下拉三角
        driver.findElement(By.xpath("//td[10]/div/div/button/span/i")).click();
        Thread.sleep(1000);
        //查看STEP组信息
        driver.findElement(By.xpath("//li[2]/button/span")).click();
        Thread.sleep(1000);
    }

    /**
     * 更新JOB定义
     *
     * @param driver
     * @param jobName
     * @throws InterruptedException
     */
    public static void uptateJobDefine(WebDriver driver, String jobName) throws InterruptedException {
        //点击编辑
        driver.findElement(By.xpath("//span[contains(.,'编辑')]")).click();
        Thread.sleep(1000);
        //点击确认
        driver.findElement(By.xpath("//span/span/button")).click();
        Thread.sleep(1000);
        //【是否要跳转到流程绘制界面】确认
        driver.findElement(By.xpath("//span[contains(.,'确定')]")).click();
        Thread.sleep(1000);
        //绘制流程图
        driver.findElement(By.xpath("//span[contains(.,'绘制流程图')]")).click();
        Thread.sleep(1000);
        //展开用户
        driver.findElement(By.xpath("//div[@id='flow-tree']/div/div[2]/div/div/div/span")).click();
        Thread.sleep(1000);
        //展开app
        driver.findElement(By.xpath("//div[@id='flow-tree']/div/div[2]/div/div/div[2]/div/div/span")).click();
        Thread.sleep(1000);
        //展开JOB
        driver.findElement(By.xpath("//div[@id='flow-tree']/div/div[2]/div/div/div[2]/div/div[2]/div/div/div")).click();
        Thread.sleep(1000);
        //展开STEP
        driver.findElement(By.xpath("//div[@id='flow-tree']/div/div[2]/div/div/div[2]/div/div[2]/div[3]/div/span")).click();
        Thread.sleep(1000);
        //点击搜索框
        driver.findElement(By.xpath("//div[@id='flow-tree']/div/div/input")).click();
        Thread.sleep(1000);
        //搜索框内输入JOB名称
        driver.findElement(By.cssSelector(".el-col .el-input__inner")).sendKeys(jobName);
        Thread.sleep(1000);
        //绘制流程操作
        //保存流程绘制界面
        driver.findElement(By.xpath("//div[@id='content']/div/i[6]")).click();
        Thread.sleep(1000);
        //关掉流程绘制的界面
        driver.findElement(By.xpath("//div/div[5]/div/div/button/i")).click();
        Thread.sleep(1000);

    }

    /**
     * 添加JOB定义
     *
     * @param driver
     * @param jobName
     * @throws InterruptedException
     */
    public static void addJobDefine(WebDriver driver, String jobName) throws InterruptedException {
        String jobDesc = "JOB描述信息";
        //添加JOB
        driver.findElement(By.xpath("//span[contains(.,'添加')]")).click();
        Thread.sleep(1000);
        //点击【JOB名称】
        driver.findElement(By.xpath("(//input[@type='text'])[3]")).click();
        Thread.sleep(1000);
        //填写JOB名称
        driver.findElement(By.cssSelector(".el-form-item:nth-child(1) > .el-form-item__content > .el-input > .el-input__inner")).sendKeys(jobName);
        Thread.sleep(1000);
        //点击【间隔时间】
        driver.findElement(By.xpath("(//input[@type='text'])[4]")).click();
        Thread.sleep(1000);
        //点击【定时器表达式】
        driver.findElement(By.xpath("(//input[@type='text'])[5]")).click();
        Thread.sleep(1000);
        //点击【JOB描述】
        driver.findElement(By.xpath("//textarea")).click();
        Thread.sleep(1000);
        //填写JOB描述信息
        driver.findElement(By.cssSelector(".el-textarea__inner")).sendKeys(jobDesc);
        Thread.sleep(1000);
        //填写完成，点击确认
        driver.findElement(By.xpath("//span/span/button/span")).click();
        Thread.sleep(1000);
        //【是否要跳转到流程绘制界面】取消
        driver.findElement(By.xpath("//span[contains(.,'取消')]")).click();
        Thread.sleep(1000);
    }

    /**
     * 删除JOB定义
     *
     * @param driver
     * @param jobName
     * @throws InterruptedException
     */
    public static void deleteJobDefine(WebDriver driver, String jobName) throws InterruptedException {
        //删除JOB定义
        driver.findElement(By.xpath("//span[contains(.,'删除')]")).click();
        Thread.sleep(1000);
        //确认
        driver.findElement(By.xpath("//div[4]/div/div[3]/span/button/span")).click();
        Thread.sleep(1000);

    }

    /**
     * JOB定义筛选
     *
     * @param driver
     * @param jobName
     * @throws InterruptedException
     */
    public static void getJobDefinedListByJobName(WebDriver driver, String jobName) throws InterruptedException {
        //JOB名称
        driver.findElement(By.xpath("//span/span/i")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//input")).sendKeys(jobName);
        Thread.sleep(500);
        driver.findElement(By.xpath("//body/div[2]/div/div/ul/li")).click();
        Thread.sleep(500);
        //搜索
        driver.findElement(By.xpath("//span[contains(.,'搜索')]")).click();
        Thread.sleep(1000);
    }

    /**
     * JOB定义导出
     *
     * @param driver
     * @throws InterruptedException
     */
    public static void exportJobDefineList(WebDriver driver) throws InterruptedException {
        driver.findElement(By.xpath("//span[contains(.,'导出')]")).click();
        Thread.sleep(1000);
    }


    /**
     * JOB运行历史筛选
     *
     * @param driver
     * @param jobName
     * @throws InterruptedException
     */
    public static void getJobHisListByCondition(WebDriver driver, String jobName) throws InterruptedException {
        //JOB名称
        driver.findElement(By.xpath("//span/span/i")).click();
        Thread.sleep(500);
        driver.findElement(By.xpath("//input")).sendKeys(jobName);
        Thread.sleep(500);
        driver.findElement(By.xpath("//span[contains(.,'" + jobName + "')]")).click();
        Thread.sleep(500);
        //搜索
        driver.findElement(By.cssSelector(".el-button > span:nth-child(2)")).click();
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
    public static void jobHisList_44(WebDriver driver, String jobName, String stepStatus) throws InterruptedException {
        getJobHisListByCondition(driver, jobName);
    }

    /**
     * JOB定义测试
     *
     * @param driver
     * @param jobName
     * @throws InterruptedException
     */
    public static void jobDefineTest_41(WebDriver driver, String jobName) throws InterruptedException {
        //添加JOB定义
        addJobDefine(driver, jobName);
        //修改JOB定义
        uptateJobDefine(driver, jobName);
        //导出JOB定义
        exportJobDefineList(driver);
        //删除JOB定义
        deleteJobDefine(driver, jobName);
    }

    /**
     * JOB列表测试
     *
     * @param driver
     * @param jobName
     * @param jobType
     * @throws InterruptedException
     */
    public static void jobListTest_42(WebDriver driver, String jobName, String jobType) throws InterruptedException {
        //JOB列表查看
        getJobDefinedListByCondition(driver, jobName, jobType);
        //启动JOB
        startJob(driver, jobName, jobType);

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
    public static void jobRunTest_43(WebDriver driver, String jobName, String jobStatus, String stepStatus) throws InterruptedException {
        //筛选JOB
        getJobRunListByCondition(driver, jobName, jobStatus);
        //查看step运行信息
        getStepRunDetail(driver);
        getStepListByCondition(driver, stepStatus);
        //1.查看step错误运行信息和step组信息
        getStepErrorInfo(driver);
        getStepGroupInfo(driver);
        //2.查看step错误运行信息和分段信息
        getStepListByCondition(driver, stepStatus);
        getStepErrorInfo(driver);
        if (jobName.contains("分段")) {
            //查看分段运行信息【分段场景】
            getStepSegmentList(driver);
            getStepListByCondition(driver, stepStatus);
            //查看错误信息
            getSegmentErrorInfo(driver);
        }


    }

}
