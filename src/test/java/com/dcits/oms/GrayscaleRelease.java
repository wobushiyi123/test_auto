package com.dcits.oms;

import com.dcits.selenium.oms.AddApplication;
import com.dcits.selenium.oms.ApplicationOpt;
import com.dcits.selenium.oms.CreateApplicationParameter;
import com.dcits.selenium.oms.UploadVersion;
import com.dcits.util.TimeUtil;
import org.testng.annotations.Test;

public class GrayscaleRelease {

    @Test
    public void release() throws InterruptedException {
        for (int i = 1;i <= 2;i++){
            CreateApplicationParameter.setApplicationParameter("acctService"+ TimeUtil.gerCurrentDate());
            UploadVersion.uploadOMSApplicationVersion("version"+i,"1.0."+(i-1),"/Users/yangguangyu/Desktop/acct-1.0-SNAPSHOT.tar.gz");
            AddApplication.addOMSApplication("testNode1","10.7.19.111","/home/sonic/acctService","acctService"+i);
            AddApplication.addOMSApplication("testNode2","10.7.19.112","/home/sonic/acctService","acctService"+i);
            AddApplication.addOMSApplication("testNode3","10.7.19.113","/home/sonic/acctService","acctService"+i);
            ApplicationOpt.deployOMSApplication();
        }
        ApplicationOpt.deployOMSApplication();
        ApplicationOpt.startApplicatio();
    }
}
