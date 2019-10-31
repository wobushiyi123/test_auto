package com.dcits.controller.ftp;

import com.dcits.entity.FTPConfig;
import com.dcits.ftp.FtpUpload;
import org.apache.commons.net.ftp.FTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

@RestController
@RequestMapping("/ftp1")
public class FTP1Controller {

    @Autowired
    FtpUpload ftp;

    @RequestMapping(value = "/singlefile/uploadNoCover")
    public String uploadNoCover(@RequestParam String fileName){
        String retMsg = null;
        FTPConfig config = new FTPConfig();
        config.setOperateFile(fileName);
        retMsg = ftp.uploadSingleFile(config,false);
        return retMsg;
    }

    @RequestMapping(value = "/singlefile/uploadCover")
    public String uploadCover(@RequestParam String fileName){
        String retMsg = null;
        FTPConfig config = new FTPConfig();
        config.setOperateFile(fileName);
        retMsg = ftp.uploadSingleFile(config,true);
        return retMsg;
    }

    @RequestMapping(value = "/singlefile/downloadNoCover")
    public String downLoadNoCover(@RequestParam String fileName ){
        String retMsg = null;
        FTPConfig config = new FTPConfig();
        config.setOperateFile(fileName);
        retMsg = ftp.downLoadSingleFile(config,false);
        return retMsg;
    }

    @RequestMapping(value = "/singlefile/downloadCover")
    public String downLoadCover(@RequestParam String fileName ){
        String retMsg = null;
        FTPConfig config = new FTPConfig();
        config.setOperateFile(fileName);
        retMsg = ftp.downLoadSingleFile(config,true);
        return retMsg;
    }

    @RequestMapping(value = "/singlefile/rename")
    public String renamesingleFile(@RequestParam String fileName ){
        FTPConfig config = new FTPConfig();
        config.setOperateFile(fileName);
        String retMsg = ftp.renameSingleName(config);
        return retMsg;
    }

    @RequestMapping(value = "/singlefile/remove")
    public String removesingleFile(@RequestParam String fileName ){
        FTPConfig config = new FTPConfig();
        config.setOperateFile(fileName);
        String retMsg = ftp.removeSingleFile(config);
        return retMsg;
    }


    @RequestMapping(value = "/dir/uploadNoCover")
    public String uploadNoCoverDir(@RequestParam String fileName){
        String retMsg = null;
        FTPConfig config = new FTPConfig();
        config.setOperateFile(fileName);
        retMsg = ftp.uploadSingleFile(config,false);
        return retMsg;
    }

    @RequestMapping(value = "/dir/uploadCover")
    public String uploadCoverDir(@RequestParam String fileName){
        String retMsg = null;
        FTPConfig config = new FTPConfig();
        config.setOperateFile(fileName);
        retMsg = ftp.uploadSingleFile(config,true);
        return retMsg;
    }

    @RequestMapping(value = "/dir/downloadNoCover")
    public String downLoadNoCoverDir(@RequestParam String fileName ){
        String retMsg = null;
        FTPConfig config = new FTPConfig();
        config.setOperateFile(fileName);
        retMsg = ftp.downLoadSingleFile(config,false);
        return retMsg;
    }

    @RequestMapping(value = "/dir/downloadCover")
    public String downLoadCoverDir(@RequestParam String fileName ){
        String retMsg = null;
        FTPConfig config = new FTPConfig();
        config.setOperateFile(fileName);
        retMsg = ftp.downLoadSingleFile(config,true);
        return retMsg;
    }

    @RequestMapping(value = "/dir/rename")
    public String renamesingleFileDir(@RequestParam String fileName ){
        FTPConfig config = new FTPConfig();
        config.setOperateFile(fileName);
        String retMsg = ftp.renameSingleName(config);
        return retMsg;
    }

    @RequestMapping(value = "/dir/remove")
    public String removesingleFileDir(@RequestParam String fileName ){
        FTPConfig config = new FTPConfig();
        config.setOperateFile(fileName);
        String retMsg = ftp.removeSingleFile(config);
        return retMsg;
    }



}
