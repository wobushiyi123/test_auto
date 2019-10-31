package com.dcits.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 *  * Desc:properties文件获取工具类
 *  * Created by hafiz.zhang on 2016/9/15.
 *  
 */
public class SonicPropertyUtil {

    private static final Logger logger = LoggerFactory.getLogger(SonicPropertyUtil.class);

    public static Properties props;
    private static String fileName;

  /*  static {
        loadProps();
    }*/


    synchronized static private void loadProps() {
        System.out.println("fileName"+fileName);
        logger.info("开始加载properties文件内容.......");
        props = new Properties();
        InputStream in = null;
        try {
            in = SonicPropertyUtil.class.getClassLoader().getResourceAsStream(fileName);
            props.load(in);
        } catch (FileNotFoundException e) {
            logger.error(fileName+"文件未找到");
        } catch (IOException e) {
            logger.error("出现IOException");
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
            } catch (IOException e) {
                logger.error("properties文件流关闭出现异常");
            }
        }
        logger.info("加载properties文件内容完成...........");
        logger.info("properties文件内容：" + props);
    }


    public static String getProperty(String key,String propName) {
        if ("sonic.job.jobName".equals(key)){
            System.out.println("文件名称"+propName);
        }
        fileName = propName;
        String value="";
        loadProps();
        /*if (null == props) {
            loadProps();
        }*/
       try {
            if(StringUtils.isNotBlank(props.getProperty(key))){
               value =  new String(props.getProperty(key).getBytes("ISO8859-1"), "gbk");
           }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //日期时间格式转换
        if(("sonic.job.startTime_S".equals(key) || "sonic.job.startTime_E".equals(key)) && StringUtils.isNotBlank(props.getProperty(key))){
            SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = null;
            try {
                date = format.parse(props.getProperty(key));
                value=date.getTime()+"";
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return value;
        //return props.getProperty(key);
    }


    public static String getProperty(String key, String defaultValue,String propName) {
        fileName = propName;
        String value="";
        if (null == props) {
            loadProps();
        }
        try {
            value =  new String(props.getProperty(key, defaultValue).getBytes("ISO8859-1"), "gbk");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return value;
        //return props.getProperty(key, defaultValue);
    }

    public static void main(String[] args) {
        String vv = SonicPropertyUtil.getProperty("sonic.job.jobName","sonic.properties");
        System.out.println(vv);
    }
}
