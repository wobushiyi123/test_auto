package com.dcits.util;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileOptUtil {
    Logger logger = Logger.getLogger(FileOptUtil.class);
    public String createFile(String fileName) {
        String userDir = System.getProperty("user.dir");

        File f = new File(userDir+"/src/main/resources/ftp/"+fileName);
        if (f.exists()){
            f.delete();
        }
        OutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            fos.write((TimeUtil.gerCurrentDate()+fileName).getBytes());
        } catch (FileNotFoundException e) {
            logger.error("file not found");
            return null;
        }catch (IOException e){
            logger.error("write file error!");
            return null;
        }
        return fileName;
    }

    public boolean changeFile(String fileName,String appendContent){
        String userDir = System.getProperty("user.dir");

        File f = new File(userDir+"/src/main/resources/ftp/"+fileName);
        if (!f.exists()){
            logger.error("change file not exist");
            return false;
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f,true);
            fos.write(appendContent.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean cleanFiles() {
        String userDir = System.getProperty("user.dir");
        boolean result = false;
        File f = new File(userDir+"/src/main/resources/ftp/");
        if (f.isFile()){
            logger.error("file cannot be clean");
            return result;
        }
        if (f.isDirectory()){
            for (File file : f.listFiles()){
                result = file.delete();
            }
        }
        ShellUtil shellUtil = new ShellUtil("10.7.19.115","sonic","sonic");
        shellUtil.execute("cd /home/sonic/galaxy-ftp-1/ && rm -f *");
        return result;
    }

    public boolean clearLocalFiles(){
        String userDir = System.getProperty("user.dir");
        boolean result = false;
        File f = new File(userDir+"/src/main/resources/ftp/");
        if (f.isFile()){
            logger.error("file cannot be clean");
            return result;
        }
        if (f.isDirectory()){
            for (File file : f.listFiles()){
                result = file.delete();
            }
        }
        return result;
    }

    public List<String> getFileContent(String filePath, String fileName){
        File f = new File(filePath+fileName);
        List<String> res = new ArrayList<>();
        try {
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String line = null;
            while ((line = br.readLine()) != null){
                res.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return res;
    }

    public void clearRemoteFiles(String ip,String userName,String password,String path){
        ShellUtil shellUtil = new ShellUtil("10.7.19.115","sonic","sonic");
        shellUtil.execute("cd /home/sonic/galaxy-ftp-1/ && rm -f *");
    }

    public boolean checkFileExist(String path,String fileName){
        File f = new File(path+fileName);
        if (f.exists()){
            return true;
        }else {
            return false;
        }

    }


}
