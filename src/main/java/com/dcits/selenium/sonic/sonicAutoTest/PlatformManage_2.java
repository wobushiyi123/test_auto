package com.dcits.selenium.sonic.sonicAutoTest;

import com.dcits.selenium.sonic.sonicAutoTest.util.MenuBarUtil;
import com.dcits.selenium.sonic.sonicAutoTest.util.PlatformManageUtil_2;
import org.openqa.selenium.WebDriver;

/**
 * 【平台管理】
 */
public class PlatformManage_2 {

    public static void platformManage_2(WebDriver driver) throws InterruptedException {
        //进入【平台管理】
        MenuBarUtil.goToPlatformManageView_20(driver);
        //进入【2.1环境配置】
        MenuBarUtil.goToEnvConfigView_21(driver);
        PlatformManageUtil_2.envConfigManage(driver);
        //进入【2.2应用管理】
        MenuBarUtil.goToAppManageView_22(driver);
        PlatformManageUtil_2.appManage(driver);
        //进入【2.3用户权限管理】
        MenuBarUtil.goToUserAuthManageView_23(driver);
        PlatformManageUtil_2.updateUserAuth(driver);
        //进入【2.4版本管理】
        MenuBarUtil.goToVersionManageView_24(driver);
        PlatformManageUtil_2.versionManage(driver);
    }
}
