package com.dcits.entity;

import java.util.Arrays;
import java.util.List;

/**
 * @program: galaxy-test
 * @description: 接口需要判断的参数
 * @author: liu yan
 * @create: 2019-10-10 16:13
 */
public class SonicModel {
    //job列表
    public static List<String> jobReturnList = Arrays.asList("jobName","profile","tenantId","jobId","jobType","creator"
           , "createTime");
    //jobCommand
    public static List<String> jobCommandReturnList = Arrays.asList("profile","tenantId","jobId",
            "operator", "instruction");
    //job运行列表
    public static List<String> runJobReturnList = Arrays.asList("jobName","profile","tenantId","jobId","jobType",
            "creator", "createTime","jobStatus","startType", "finishedCnt",
            "failCnt", "totalCnt","machineId","operator");
    //step运行信息,获取分段后子step运行信息
    public static List<String> currentRunStepList = Arrays.asList("tenantId","appId","profile","stepId","stepName",
            "clazzName", "splitClazzName","stepType","refJobId",
         "splitCount","currentCount","elapsed","progress");
    //Job的Step依赖关系
    public static List<String> chainListOuterData = Arrays.asList("appId","jobId","jobName","creator","tenantId");
   // Job的Step出错信息
   public static List<String> StepErrorReturnList = Arrays.asList("errorType","stack","errorType");
    public static void main(String[] args) {

    }
}
