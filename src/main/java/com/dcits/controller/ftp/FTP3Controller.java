package com.dcits.controller.ftp;

import com.dcits.entity.FTPConfig;
import com.dcits.ftp.FtpUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ftp3")
public class FTP3Controller {

    @Autowired
    FtpUpload ftp;

    @RequestMapping(value = "/singlefile/uploadNoCover")
    public String uploadNoCover(@RequestParam String fileName,@RequestParam String remoteDir,@RequestParam String localDir){
        String retMsg = null;
        FTPConfig config = new FTPConfig();
        config.setOperateFile(fileName);
        config.setRemoteDir(remoteDir);
        config.setLocalDir(localDir);
        retMsg = ftp.uploadSingleFile(config,false);
        return retMsg;
    }

    @RequestMapping(value = "/singlefile/uploadCover")
    public String uploadCover(@RequestParam String fileName,@RequestParam String remoteDir,@RequestParam String localDir){
        String retMsg = null;
        FTPConfig config = new FTPConfig();
        config.setOperateFile(fileName);
        config.setRemoteDir(remoteDir);
        config.setLocalDir(localDir);
        retMsg = ftp.uploadSingleFile(config,true);
        return retMsg;
    }

    @RequestMapping(value = "/singlefile/downloadNoCover")
    public String downLoadNoCover(@RequestParam String fileName,@RequestParam String remoteDir,@RequestParam String localDir){
        String retMsg = null;
        FTPConfig config = new FTPConfig();
        config.setOperateFile(fileName);
        config.setRemoteDir(remoteDir);
        config.setLocalDir(localDir);
        retMsg = ftp.downLoadSingleFile(config,false);
        return retMsg;
    }

    @RequestMapping(value = "/singlefile/downloadCover")
    public String downLoadCover(@RequestParam String fileName,@RequestParam String remoteDir,@RequestParam String localDir){
        String retMsg = null;
        FTPConfig config = new FTPConfig();
        config.setOperateFile(fileName);
        config.setRemoteDir(remoteDir);
        config.setLocalDir(localDir);
        retMsg = ftp.downLoadSingleFile(config,true);
        return retMsg;
    }

    @RequestMapping(value = "/singlefile/rename")
    public String renamesingleFile(@RequestParam String fileName,@RequestParam String remoteDir,@RequestParam String localDir){
        FTPConfig config = new FTPConfig();
        config.setOperateFile(fileName);
        config.setRemoteDir(remoteDir);
        config.setLocalDir(localDir);
        String retMsg = ftp.renameSingleName(config);
        return retMsg;
    }

    @RequestMapping(value = "/singlefile/remove")
    public String removesingleFile(@RequestParam String fileName,@RequestParam String remoteDir,@RequestParam String localDir){
        FTPConfig config = new FTPConfig();
        config.setOperateFile(fileName);
        config.setRemoteDir(remoteDir);
        config.setLocalDir(localDir);
        String retMsg = ftp.removeSingleFile(config);
        return retMsg;
    }


    @RequestMapping(value = "/dir/uploadNoCover")
    public String uploadNoCoverDir(@RequestParam String fileName,@RequestParam String remoteDir,@RequestParam String localDir){
        String retMsg = null;
        FTPConfig config = new FTPConfig();
        config.setOperateFile(fileName);
        config.setRemoteDir(remoteDir);
        config.setLocalDir(localDir);
        retMsg = ftp.uploadSingleFile(config,false);
        return retMsg;
    }

    @RequestMapping(value = "/dir/uploadCover")
    public String uploadCoverDir(@RequestParam String fileName,@RequestParam String remoteDir,@RequestParam String localDir){
        String retMsg = null;
        FTPConfig config = new FTPConfig();
        config.setOperateFile(fileName);
        config.setRemoteDir(remoteDir);
        config.setLocalDir(localDir);
        retMsg = ftp.uploadSingleFile(config,true);
        return retMsg;
    }

    @RequestMapping(value = "/dir/downloadNoCover")
    public String downLoadNoCoverDir(@RequestParam String fileName,@RequestParam String remoteDir,@RequestParam String localDir){
        String retMsg = null;
        FTPConfig config = new FTPConfig();
        config.setOperateFile(fileName);
        config.setRemoteDir(remoteDir);
        config.setLocalDir(localDir);
        retMsg = ftp.downLoadSingleFile(config,false);
        return retMsg;
    }

    @RequestMapping(value = "/dir/downloadCover")
    public String downLoadCoverDir(@RequestParam String fileName,@RequestParam String remoteDir,@RequestParam String localDir){
        String retMsg = null;
        FTPConfig config = new FTPConfig();
        config.setOperateFile(fileName);
        config.setRemoteDir(remoteDir);
        config.setLocalDir(localDir);
        retMsg = ftp.downLoadSingleFile(config,true);
        return retMsg;
    }

    @RequestMapping(value = "/dir/rename")
    public String renamesingleFileDir(@RequestParam String fileName,@RequestParam String remoteDir,@RequestParam String localDir){
        FTPConfig config = new FTPConfig();
        config.setOperateFile(fileName);
        config.setRemoteDir(remoteDir);
        config.setLocalDir(localDir);
        String retMsg = ftp.renameSingleName(config);
        return retMsg;
    }

    @RequestMapping(value = "/dir/remove")
    public String removesingleFileDir(@RequestParam String fileName,@RequestParam String remoteDir,@RequestParam String localDir){
        FTPConfig config = new FTPConfig();
        config.setOperateFile(fileName);
        config.setRemoteDir(remoteDir);
        config.setLocalDir(localDir);
        String retMsg = ftp.removeSingleFile(config);
        return retMsg;
    }



}
