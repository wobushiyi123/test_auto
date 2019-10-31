package com.dcits.ftp;

import com.dcits.GalaxyTestApplication;
import com.dcits.entity.FTPConfig;
import com.dcits.ftp.base.TransferFile;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;


@Component
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GalaxyTestApplication.class)
public class FtpUpload {




    public String uploadSingleFile(FTPConfig config,boolean cover){
        String result = null;
        try{
            TransferFile transferFile = new TransferFileOpt().getTranFile(config);
            if (transferFile.upload(cover)){
                result = "successful";
            }else {
                result = "failed";
            }
            transferFile.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public String downLoadSingleFile(FTPConfig config,boolean cover){
        String result = null;
        try{
            TransferFile transferFile = new TransferFileOpt().getTranFile(config);
            if (transferFile.download(cover)){
                result = "successful";
            }else {
                result = "failed";
            }
            transferFile.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return  result;
    }
    public String removeSingleFile(FTPConfig config){
        String result = null;
        try{
            TransferFile transferFile = new TransferFileOpt().getTranFile(config);
            if (transferFile.delete()){
                result = "successful";
            }else {
                result = "failed";
            }
            transferFile.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return  result;
    }
    public String renameSingleName(FTPConfig config){
        String result = null;
        try{
            TransferFile transferFile = new TransferFileOpt().getTranFile(config);
            if (transferFile.rename("renameNewFile.txt")){
                result = "successful";
            }else {
                result = "failed";
            }
            transferFile.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return  result;
    }

    public String uploadDir(FTPConfig config,boolean cover){
        String result = null;
        try{
            TransferFile transferFile = new TransferFileOpt().getTranFile(config);
            if (transferFile.upload(cover)){
                result = "successful";
            }else {
                result = "failed";
            }
            transferFile.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public String downLoadDir(FTPConfig config,boolean cover){
        String result = null;
        try{
            TransferFile transferFile = new TransferFileOpt().getTranFile(config);
            if (transferFile.download(cover)){
                result = "successful";
            }else {
                result = "failed";
            }
            transferFile.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return  result;
    }
    public String removeDir(FTPConfig config){
        String result = null;
        try{
            TransferFile transferFile = new TransferFileOpt().getTranFile(config);
            if (transferFile.delete()){
                result = "successful";
            }else {
                result = "failed";
            }
            transferFile.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return  result;
    }
    public String renameDir(FTPConfig config){
        String result = null;
        try{
            TransferFile transferFile = new TransferFileOpt().getTranFile(config);
            if (transferFile.rename("renameNewFile.txt")){
                result = "successful";
            }else {
                result = "failed";
            }
            transferFile.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return  result;
    }

    @Test
    public void uploadSingleFileTest(){
        //1 新建文件传输对象
        try {
            TransferFile transferFile = new TransferFile("115-1.txt");//只指定文件名称，此情况会使用默认节点最为文件服务器，使用配置文件中的本地和远端工作路径
            //TransferFile transferFile = new TransferFile("/home/sonic/","D:/FTP/","logback.xml");//指定远程和本地工作路径以及文件名称，此情况会使用默认节点最为文件服务器
            //TransferFile transferFile = new TransferFile("node1","/home/sonic/","D:/FTP/","logback.xml");//指定文件服务器节点，远程和本地工作路径以及文件名称

//2操作文件
//            transferFile.upload();//上传，常规模式，如果文件存在，则返回失败
            transferFile.upload(true);//上传，覆盖模式
            //transferFile.download();//下载，常规模式，如果文件存在，则返回失败
            //transferFile.download(true);//下载，覆盖模式
            //transferFile.delete();//删除
            //transferFile.rename("logback.xml-1");//重命名

//3关闭文件操作对象，释放资源
            transferFile.close();//关闭
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
