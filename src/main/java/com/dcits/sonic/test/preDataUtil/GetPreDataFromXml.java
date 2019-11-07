package com.dcits.sonic.test.preDataUtil;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.xml.XMLSerializer;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * 从XML文件中读取预言值
 */
public class GetPreDataFromXml {
    /**
     * 日志打印
     */
    private static final Logger logger = LoggerFactory.getLogger(GetPreDataFromXml.class);
    private volatile static GetPreDataFromXml getPreDataFromXml;

    private GetPreDataFromXml() {
    }

    public static GetPreDataFromXml getInstance() {
        if (null == getPreDataFromXml) {
            synchronized (GetPreDataFromXml.class) {
                if (null == getPreDataFromXml) {
                    getPreDataFromXml = new GetPreDataFromXml();
                }
            }
        }
        return getPreDataFromXml;
    }

    /**
     * 从XML文件中读取预言值
     *
     * @param filePath 文件路径
     * @return 返回JSON格式的预言值
     * @throws IOException
     */
    public JSONArray getPreDataFromXml(String filePath) {
        String xml = null;
        JSON json = null;
        JSONArray oj = null;
        try {
            String text = FileUtils.readFileToString(new File(filePath), "UTF-8");
            XMLSerializer xmlSerializer = new XMLSerializer();
            json = xmlSerializer.read(text);
            oj = JSONArray.fromObject(json);
        } catch (Exception e) {
            logger.error("read xml error", e);
        }
        return oj;
    }
}
