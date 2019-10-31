package com.dcits.ftp;

import com.dcits.entity.FTPConfig;
import com.dcits.ftp.base.TransferFile;
import org.apache.log4j.Logger;

public class TransferFileOpt {
    private static Logger logger = Logger.getLogger(TransferFile.class);


    public TransferFile getTranFile(FTPConfig config) throws Exception {
        if (config.getNodeName() == null||config.getNodeName() == ""
                &&config.getLocalDir() == null||config.getLocalDir() == ""
                &&config.getRemoteDir() == null||config.getRemoteDir() == ""
                &&!(config.getOperateFile() == null||config.getOperateFile() == "")){
            return new TransferFile(config.getOperateFile());

        }else if (!(config.getNodeName() == null||config.getNodeName() == "")
                &&!(config.getLocalDir() == null||config.getLocalDir() == "")
                &&!(config.getRemoteDir() == null||config.getRemoteDir() == "")
                &&!(config.getOperateFile() == null||config.getOperateFile() == "")){
return new TransferFile(config.getNodeName(),config.getRemoteDir(),config.getLocalDir(),config.getOperateFile());
        }else if ((config.getNodeName() == null||config.getNodeName() == "")
                &&!(config.getLocalDir() == null||config.getLocalDir() == "")
                &&!(config.getRemoteDir() == null||config.getRemoteDir() == "")
                &&!(config.getOperateFile() == null||config.getOperateFile() == "")){
return new TransferFile(config.getRemoteDir(),config.getLocalDir(),config.getOperateFile());
        }else {
            logger.error("ftp config error!");
            return null;
        }
    }
}
