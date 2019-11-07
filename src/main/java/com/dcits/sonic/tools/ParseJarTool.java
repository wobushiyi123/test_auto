package com.dcits.sonic.tools;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * jar包解析工具类
 */
public class ParseJarTool {
    private final static String suffix_pom = "pom.xml";
    private final static String suffix_jar = ".jar";
    private final static String keyWords = "SNAPSHOT";
    private final static String errorMessage = "该jar中包含快照！";

    /**
     * 测试入口
     *
     * @param args
     */
    public static void main(String args[]) {
        String filePath = "";
        if (args.length == 1) {
            System.out.println("扫描路径为:" + args[0]);
            filePath = args[0];
        }
        if ("".equals(filePath) || null == filePath) {
            System.out.println("扫描的文件路径为空!");
            return;
        }
        try {
            getFileList(filePath);
        } catch (Exception e) {
            if (errorMessage.equals(e.getMessage())) {
                System.out.println("测试不通过!");
                return;
            }
        }
        System.out.println("测试通过!");
    }

    /**
     * 获取jar包并解析
     *
     * @param strPath
     * @throws IOException
     */
    public static void getFileList(String strPath) throws Exception {
        File fileDir = new File(strPath);
        if (null != fileDir && fileDir.isDirectory()) {
            File[] files = fileDir.listFiles();
            if (null != files) {
                for (int i = 0; i < files.length; i++) {
                    // 如果是文件夹,继续递归读取
                    if (files[i].isDirectory()) {
                        getFileList(files[i].getAbsolutePath());
                    } else {
                        String strFileName = files[i].getAbsolutePath();
                        if (null != strFileName && strFileName.endsWith(suffix_jar)) {
                            try {
                                parseJarFile(strFileName);
                            } catch (IOException e) {
                                System.out.println("解析pom文件异常!");
                                e.printStackTrace();
                            }
                        }
                    }
                }

            }
        }
    }

    /**
     * jar包解析
     *
     * @param jarFilePath
     * @throws IOException
     */
    private static void parseJarFile(String jarFilePath) throws Exception {
        //获取pom文件
        JarEntry jarEntry = getPomFile(jarFilePath);
        if (null != jarEntry) {
            String pomFilePath = "jar:file:" + jarFilePath + "!/" + jarEntry.getName();
            URL url = new URL(pomFilePath);
            InputStream inputStream = url.openStream();
            byte[] bytes = IOUtils.toByteArray(inputStream);
            String content = new String(bytes);
            if (content.contains(keyWords)) {
                System.out.println(errorMessage);
                System.out.println("jar包路径:" + pomFilePath);
                Throwable e = new Throwable();
                throw new Exception(errorMessage, e);
            }
        }
    }

    /**
     * 获取pom文件
     *
     * @param jarFilePath jar包文件路径
     * @return jarEntry
     */
    private static JarEntry getPomFile(String jarFilePath) {
        JarFile jarFile = null;
        JarEntry jarEntry = null;
        try {
            jarFile = new JarFile(jarFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            jarEntry = entries.nextElement();
            if (jarEntry.getName().endsWith(suffix_pom)) {
                break;//找到pom.xml后，跳出while循环
            } else {
                jarEntry = null;
            }
        }
        return jarEntry;
    }

}
