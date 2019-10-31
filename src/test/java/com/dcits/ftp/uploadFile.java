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

public class uploadFile {

    FileOptUtil fileOptUtil = new FileOptUtil();
    static String optFileName;


    @BeforeTest
    public void createFile(){
        optFileName = fileOptUtil.createFile(TimeUtil.gerCurrentDate()+".txt");
    }

    /*
    * 不覆盖，不存在
    * */
    @Test(priority = 0)
    public void uploadNotExistNotCover(){
        ShellUtil shellUtil = new ShellUtil("10.7.19.115","sonic","sonic");
        String result = HTTPUtil.get("http://127.0.0.1:8080/ftp1/singlefile/uploadNoCover?fileName="+optFileName);
        shellUtil.execute("ls  /home/sonic/galaxy-ftp-1");
        Assert.assertEquals(optFileName,shellUtil.getStandardOutput().get(0));
        shellUtil.getStandardOutput().clear();
    }
    /*
    * 不覆盖，存在
    * */
    @Test(priority = 1)
    public void uploadExistNotCover(){
        ShellUtil shellUtil = new ShellUtil("10.7.19.115","sonic","sonic");
        String result = HTTPUtil.get("http://127.0.0.1:8080/ftp1/singlefile/uploadNoCover?fileName="+optFileName);
        String result1 = HTTPUtil.get("http://127.0.0.1:8080/ftp1/singlefile/uploadNoCover?fileName="+optFileName);
        Assert.assertEquals(result1,"failed");
        shellUtil.getStandardOutput().clear();
    }

    /*
    * 覆盖，不存在
    * */
    @Test(priority = 2)
    public void uploadNotExistCover(){
        ShellUtil shellUtil = new ShellUtil("10.7.19.115","sonic","sonic");
        String result = HTTPUtil.get("http://127.0.0.1:8080/ftp1/singlefile/uploadCover?fileName="+optFileName);
        shellUtil.execute("ls  /home/sonic/galaxy-ftp-1/");
        Assert.assertEquals(optFileName,shellUtil.getStandardOutput().get(0));
        Assert.assertEquals(result,"successful");
        shellUtil.getStandardOutput().clear();
    }

    /*
     * 覆盖，存在
     * */
    @Test(priority = 3)
    public void uploadExistCover(){
        ShellUtil shellUtil = new ShellUtil("10.7.19.115","sonic","sonic");
        String result = HTTPUtil.get("http://127.0.0.1:8080/ftp1/singlefile/uploadCover?fileName="+optFileName);
        shellUtil.execute("cat  /home/sonic/galaxy-ftp-1/"+optFileName);
        new FileOptUtil().changeFile(optFileName,"aaaaaaa");
        String result1 = HTTPUtil.get("http://127.0.0.1:8080/ftp1/singlefile/uploadCover?fileName="+optFileName);
        shellUtil.execute("cat  /home/sonic/galaxy-ftp-1/"+optFileName);
        Assert.assertEquals(result1,"successful");
        Assert.assertNotSame(shellUtil.getStandardOutput().get(1),shellUtil.getStandardOutput().get(0));
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
