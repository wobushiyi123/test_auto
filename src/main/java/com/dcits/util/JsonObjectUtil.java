package com.dcits.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonObjectUtil {
    /*传递json的字符串，转换为JSONObject*/
    public static JSONObject jsonStringToJsonObject(String json){
        JSONObject object = JSONObject.fromObject(json);
        return object;
    }
    /*传递JSON字符串，以及json中数组名称*/
    public static JSONArray getJsonArray(String json,String arrayName){
        JSONObject object = JSONObject.fromObject(json);
        String ss = object.getString("arrayName");
        JSONArray jsonArray = JSONArray.fromObject(ss);
        return jsonArray;
    }

    /**
     * 获取接口返回值的【data】JSON对象
     * @param jsonObject 接口返回的JSON对象
     * @return 转换后JSON对象
     */
    public static JSONObject getDataJson(JSONObject jsonObject){
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        JSONObject dataJson = jsonArray.getJSONObject(0);
        return dataJson;
    }
}
