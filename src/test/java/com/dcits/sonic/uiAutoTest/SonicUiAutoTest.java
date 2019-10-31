package com.dcits.sonic.uiAutoTest;

import com.dcits.selenium.sonic.sonicAutoTest.SonicUiWorkManageTest;
import org.testng.annotations.Test;

//启动UI自动化测试
public class SonicUiAutoTest {
    @Test
    public  void release() throws InterruptedException {
         new SonicUiWorkManageTest().sonicWorkManage();
    }
}
