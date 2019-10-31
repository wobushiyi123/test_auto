package com.dcits.entity;

public class FTPConfig {
    private String nodeName;
    private String remoteDir;
    private String localDir;
    private String operateFile;

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getRemoteDir() {
        return remoteDir;
    }

    public void setRemoteDir(String remoteDir) {
        this.remoteDir = remoteDir;
    }

    public String getLocalDir() {
        return localDir;
    }

    public void setLocalDir(String localDir) {
        this.localDir = localDir;
    }

    public String getOperateFile() {
        return operateFile;
    }

    public void setOperateFile(String operateFile) {
        this.operateFile = operateFile;
    }
}
