package com.dcits.ftp;

import com.dcits.util.FileOptUtil;
import com.dcits.util.HTTPUtil;
import com.dcits.util.ShellUtil;
import com.dcits.util.TimeUtil;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class removeFile {

    FileOptUtil fileOptUtil = new FileOptUtil();
    static String optFileName;


    @BeforeTest
    public void createFile(){
        optFileName = fileOptUtil.createFile(TimeUtil.gerCurrentDate()+".txt");
    }

    /*
    * 重命名不存在文件
    * */
    @Test(priority = 0)
    public void removeFileNotExist(){
        String result1 = HTTPUtil.get("http://127.0.0.1:8080/ftp1/singlefile/remove?fileName=aaa.txt");
        Assert.assertEquals(result1,"failed");
    }
    /*
    * 重命名存在文件
    * */
    @Test(priority = 1)
    public void uploadExistNotCover(){
        ShellUtil shellUtil = new ShellUtil("10.7.19.115","sonic","sonic");
        String result = HTTPUtil.get("http://127.0.0.1:8080/ftp1/singlefile/uploadCover?fileName="+optFileName);
        String result1 = HTTPUtil.get("http://127.0.0.1:8080/ftp1/singlefile/remove?fileName="+optFileName);
        shellUtil.execute("ls  /home/sonic/galaxy-ftp-1/");
        Assert.assertNotSame(optFileName,shellUtil.getStandardOutput());
        Assert.assertEquals(result1,"successful");
        shellUtil.getStandardOutput().clear();
    }


    @AfterTest
    public void cleanFile(){
        fileOptUtil.cleanFiles();

    }
    @AfterSuite
    public void close(){
    }
}
