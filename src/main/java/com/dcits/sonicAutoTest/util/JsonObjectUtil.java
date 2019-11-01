package com.dcits.sonicAutoTest.util;

import net.sf.json.JSONObject;

public class JsonObjectUtil {
    /*传递json的字符串，转换为JSONObject*/
    public static JSONObject jsonStringToJsonObject(String json){
        JSONObject object = JSONObject.fromObject(json);
        return object;
    }
}
