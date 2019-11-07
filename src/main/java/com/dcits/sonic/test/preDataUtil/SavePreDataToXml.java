package com.dcits.sonic.test.preDataUtil;

import com.dcits.sonic.test.createJsonFile.SavePreDataToJsonFile;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * 将预言值存储在JSON文件中
 */
public class SavePreDataToXml {
    //日志打印
    private static final Logger logger = LoggerFactory.getLogger(SavePreDataToJsonFile.class);
    //预言值文件所在路径
    public static final String preDataXmlFilesPath =
            System.getProperty("user.dir")
                    + File.separator
                    + "src"
                    + File.separator
                    + "main"
                    + File.separator
                    + "resources"
                    + File.separator
                    + "preDataFiles"
                    + File.separator
                    + "xml"
                    + File.separator;

    /**
     * 创建存储预言值的XML文件
     *
     * @param preData
     * @param fileName
     * @return
     */
    public static boolean savePreDataToXml(JSONObject preData, String fileName) {
        //判断文件夹是否存在，不存在就创建
        File preFile = new File(preDataXmlFilesPath);
        if (!preFile.exists()) {
            preFile.mkdirs();
            logger.info("==预言值文件夹创建成功==");
        } else {
            logger.info("==预言值文件夹已存在==");
        }
        String preDataXml = JsonToXml.getInstance().jsonToXML(preData);
        PrintWriter writer = null;
        boolean createFlag = true;
        try {
            writer = new PrintWriter(preDataXmlFilesPath + fileName + ".xml", "UTF-8");
        } catch (FileNotFoundException e) {
            logger.error("create error", e);
            createFlag = false;
        } catch (UnsupportedEncodingException e) {
            logger.error("create error", e);
            createFlag = false;
        }
        writer.println(JsonToXml.getInstance().format(preDataXml));
        writer.close();
        return createFlag;
    }
}
