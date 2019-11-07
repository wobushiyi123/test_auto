package com.dcits.sonic.test.preDataUtil;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import net.sf.json.JSONObject;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


import java.io.*;

/**
 * JSON格式转为XML格式
 */
public class JsonToXml {
    /**
     * 日志打印
     */
    private static final Logger logger = LoggerFactory.getLogger(JobAndStepInterface.class);

    private volatile static JsonToXml jsonToXml;

    private JsonToXml() {
    }

    public static JsonToXml getInstance() {
        if (null == jsonToXml) {
            synchronized (JsonToXml.class) {
                if (null == jsonToXml) {
                    jsonToXml = new JsonToXml();
                }
            }
        }
        return jsonToXml;
    }

    /**
     * 创建xml
     *
     * @param jsonObject
     * @return
     */
    public String jsonToXML(JSONObject jsonObject) {
        net.sf.json.xml.XMLSerializer xmlSerializer = new net.sf.json.xml.XMLSerializer();
        // 根节点名称
        xmlSerializer.setRootName("resource");
        // 不对类型进行设置
        xmlSerializer.setTypeHintsEnabled(false);
        String xmlStr = "";
        xmlStr = xmlSerializer.write(jsonObject);
        return xmlStr;
    }

    /**
     * 格式化XML
     *
     * @param unformattedXml
     * @return
     */
    public String format(String unformattedXml) {
        try {
            final Document document = parseXmlFile(unformattedXml);
            OutputFormat format = new OutputFormat(document);
            format.setLineWidth(65);
            format.setIndenting(true);
            format.setIndent(2);
            Writer out = new StringWriter();
            XMLSerializer serializer = new XMLSerializer(out, format);
            serializer.serialize(document);
            return out.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 解析
     *
     * @param in
     * @return
     */
    private Document parseXmlFile(String in) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(in));
            return db.parse(is);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
