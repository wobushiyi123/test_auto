package com.dcits.selenium.sonic.createJsonFile;

import com.dcits.selenium.sonic.preDataUtil.SavePreDataToXml;
import net.sf.json.JSONArray;

import java.io.*;

/**
 * 将预言值存储在JSON文件中
 */
public class SavePreDataToJsonFile {

    /**
     * 将预言值存储在JSON文件中
     *
     * @param preData_jsonArray
     * @param fileName
     * @return 文件是否生成成功
     */
    public static boolean savePreDataToJsonFile(JSONArray preData_jsonArray, String fileName) {
        // 标记文件生成是否成功
        boolean flag = true;
        // 生成的JSON文件所在路径
        String fullPath = SavePreDataToXml.preDataFilesPath + File.separator + fileName + ".json";

        try {
            //创建文件
            File file = new File(fullPath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();

            String jsonString = preData_jsonArray.toString();
            if (jsonString.indexOf("'") != -1) {
                //将单引号转义一下，因为JSON串中的字符串类型可以单引号引起来的
                jsonString = jsonString.replaceAll("'", "\\'");
            }
            if (jsonString.indexOf("\"") != -1) {
                //将双引号转义一下，因为JSON串中的字符串类型可以单引号引起来的
                jsonString = jsonString.replaceAll("\"", "\\\"");
            }

            if (jsonString.indexOf("\r\n") != -1) {
                //将回车换行转换一下，因为JSON串中字符串不能出现显式的回车换行
                jsonString = jsonString.replaceAll("\r\n", "\\u000d\\u000a");
            }
            if (jsonString.indexOf("\n") != -1) {
                //将换行转换一下，因为JSON串中字符串不能出现显式的换行
                jsonString = jsonString.replaceAll("\n", "\\u000a");
            }

            // 格式化json字符串
            jsonString = JsonFormatTool.formatJson(jsonString);
            // 将格式化后的字符串写入文件
            Writer write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            write.write(jsonString);
            write.flush();
            write.close();
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }
        // 返回是否成功的标记
        return flag;
    }
}
