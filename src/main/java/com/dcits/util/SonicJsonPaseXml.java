package com.dcits.util;


import org.apache.commons.io.FileUtils;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import net.sf.json.JSONObject;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.*;


/**
 * @program: galaxy-test
 * @description: json file parse to xml file
 * @author: liu yan
 * @create: 2019-09-30 09:41
 */
public class SonicJsonPaseXml {
    //格式化
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

    //转化为String
        public static String jsonToXML(JSONObject jsonObject) {
        net.sf.json.xml.XMLSerializer xmlSerializer = new net.sf.json.xml.XMLSerializer();
        // 根节点名称
        xmlSerializer.setRootName("resource");
        // 不对类型进行设置
        xmlSerializer.setTypeHintsEnabled(false);
        String xmlStr = "";
        xmlStr = xmlSerializer.write(jsonObject);
        return xmlStr;}
/*    public static String jsonToXML(String json) {
        net.sf.json.xml.XMLSerializer xmlSerializer = new net.sf.json.xml.XMLSerializer();
        // 根节点名称
        xmlSerializer.setRootName("resource");
        // 不对类型进行设置
        xmlSerializer.setTypeHintsEnabled(false);
        String xmlStr = "";
        JSONObject jobj = JSONObject.fromObject(json);
        JSONObject jobjRep = new JSONObject();
        jobjRep.put("jobList",jobj);
        jobjRep.put("job",jobjRep);
        jobjRep.remove("jobList");
        xmlStr = xmlSerializer.write(jobjRep);
        return xmlStr;}*/
   /* public static void main(String[] args) throws Exception{
        String aa = "{\"index\":\"0\",\"title\":null,\"order\":\"0\",\"componentKey\":\"ColumnPanel\",\"layoutDetail\":[{\"aa\":\"12\"}]}";
        // String original_filename= "/Users/xidehan/Downloads/aa.txt";
        //String file = FileUtils.readFileToString(new File(original_filename));
        String s = jsonToXML(aa);

        System.out.println(new SonicJsonPaseXml().format(s));
        PrintWriter writer = new PrintWriter("E:\\resource.xml", "UTF-8");
        writer.println(new SonicJsonPaseXml().format(s));
        writer.close();
    }*/

    }
