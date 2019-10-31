package com.dcits.util;

import java.util.List;

public class RemoteNodeUtil {

//    第一个节点为默认节点
    static String ip = PropertyUtil.getProperty("remoteNodes.ip");
    static String username = PropertyUtil.getProperty("remoteNodes.username");
    static String password = PropertyUtil.getProperty("remoteNodes.password");

    String[] ips;
    String[] userNames;
    String[] passwords;

    public RemoteNodeUtil(){
        ips = ip.split(",");
        userNames = username.split(",");
        passwords = password.split(",");
    }

    public List<String> getRemoteNodeIps(){
        List<String> result = null;
        for (String a:ips) {
            result.add(a);
        }
        return result;
    }

    public List<String> getRemoteNodeUsernames(){
        List<String> result = null;
        for (String a:userNames) {
            result.add(a);
        }
        return result;
    }

    public List<String> getRemoteNodePasswords(){
        List<String> result = null;
        for (String a:passwords) {
            result.add(a);
        }
        return result;
    }




}
