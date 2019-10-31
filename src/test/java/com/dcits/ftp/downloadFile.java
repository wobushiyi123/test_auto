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

public class downloadFile {

    FileOptUtil fileOptUtil = new FileOptUtil();
    static String optFileName;

/*
* 创建文件，并上传，以供下载
* */
    @BeforeTest
    public void createFile(){
        optFileName = fileOptUtil.createFile(TimeUtil.gerCurrentDate()+".txt");
        String result = HTTPUtil.get("http://127.0.0.1:8080/ftp1/singlefile/uploadCover?fileName="+optFileName);

    }

    /*
    * 不覆盖，不存在
    * */
    @Test(priority = 0)
    public void downloadNotExistNotCover(){
        ShellUtil shellUtil = new ShellUtil("10.7.19.115","sonic","sonic");
        //清空本地文件
        fileOptUtil.clearLocalFiles();
        //下载到本地文件
        String result1 = HTTPUtil.get("http://127.0.0.1:8080/ftp1/singlefile/downloadNoCover?fileName="+optFileName);
        boolean result2 = fileOptUtil.checkFileExist(System.getProperty("user.dir")+"/src/main/resources/ftp/",optFileName);
        Assert.assertEquals(result1,"successful");
        Assert.assertEquals(result2,true);
        shellUtil.getStandardOutput().clear();
    }
    /*
    * 不覆盖，存在
    * */
    @Test(priority = 1)
    public void downloadExistNotCover(){
        ShellUtil shellUtil = new ShellUtil("10.7.19.115","sonic","sonic");
        //下载到本地文件
        String result1 = HTTPUtil.get("http://127.0.0.1:8080/ftp1/singlefile/downloadNoCover?fileName="+optFileName);
        Assert.assertEquals(result1,"failed");
        shellUtil.getStandardOutput().clear();
    }

    /*
    * 覆盖，不存在
    * */
    @Test(priority = 2)
    public void downloadNotExistCover(){
        ShellUtil shellUtil = new ShellUtil("10.7.19.115","sonic","sonic");
        //清空本地文件
        fileOptUtil.clearLocalFiles();
        String result = HTTPUtil.get("http://127.0.0.1:8080/ftp1/singlefile/downloadCover?fileName="+optFileName);
        boolean result1 = fileOptUtil.checkFileExist(System.getProperty("user.dir")+"/src/main/resources/ftp/",optFileName);
        Assert.assertEquals(result,"successful");
        Assert.assertEquals(result1,true);
        shellUtil.getStandardOutput().clear();
    }

    /*
     * 覆盖，存在
     * */
    @Test(priority = 3)
    public void downloadExistCover(){
        ShellUtil shellUtil = new ShellUtil("10.7.19.115","sonic","sonic");
        //修改远程文件
        shellUtil.execute("echo aaaa>/home/sonic/galaxy-ftp-1/"+optFileName);
        String result = HTTPUtil.get("http://127.0.0.1:8080/ftp1/singlefile/downloadCover?fileName="+optFileName);
        boolean result1 = fileOptUtil.checkFileExist(System.getProperty("user.dir")+"/src/main/resources/ftp/",optFileName);
        String fileContent = fileOptUtil.getFileContent(System.getProperty("user.dir")+"/src/main/resources/ftp/",optFileName).get(0);
        Assert.assertEquals(result,"successful");
        Assert.assertEquals(result1,true);
        Assert.assertEquals(fileContent,"aaaa");
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
