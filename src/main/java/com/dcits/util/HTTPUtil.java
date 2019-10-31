package com.dcits.util;

import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.builder.HCB;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.HttpHeader;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;

public class HTTPUtil {
    public static void main(String[] args) throws JSONException {
      /*  JSONObject object = new JSONObject();
        object.put("tenantId","fcadb98e728f4a19a598e3ba86336f15");
        object.put("appId","9305b0f96ea0840b8a29bc2a8842d828");
        object.put("profile","lyag");
        object.put("jobId","9c9352e6cae24bf684b95b07c1bbf8f2");
        object.put("jobStatus","");
        object.put("startTime_S","");
        object.put("startTime_E","");
        object.put("page","1");
        object.put("per_page","10");
        String url = "http://10.7.20.206:8099/sonic/job/getCurrentRunJob";
        String result = HTTPUtil.get(url,object.toString());*/
        String url ="http://10.7.20.206:8099/sonic/job/getCurrentRunJob" +
                "?tenantId=fcadb98e728f4a19a598e3ba86336f15" +
                "&appId=9305b0f96ea0840b8a29bc2a8842d828" +
                "&profile=lyag" +
                "&page=1" +
                "&per_page=10" +
                "&jobStatus=" +
                "&startTime_S=" +
                "&startTime_E=";
        String result ="";
        result = HTTPUtil.get(url);
        System.out.println(result);
    }

    public static String get(String url,String json){
        Header[] headers = HttpHeader.custom().build();
        HCB hcb = HCB.custom().retry(5);
        HttpClient client = hcb.build();
        HttpConfig config = HttpConfig.custom().headers(headers)
                .url(url)
                .encoding("utf-8")
                .client(client).json(json);
        String result = null;
        try {
            result = HttpClientUtil.get(config);
        } catch (HttpProcessException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static String get(String url){
        Header[] headers = HttpHeader.custom().build();
        HCB hcb = HCB.custom().retry(5);
        HttpClient client = hcb.build();
        HttpConfig config = HttpConfig.custom().headers(headers)
                .url(url)
                .encoding("utf-8")
                .client(client);
        String result = null;
        try {
            result = HttpClientUtil.get(config);
        } catch (HttpProcessException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String post(String url,String postJson){
        Header[] headers = HttpHeader.custom().build();
        HCB hcb = HCB.custom().retry(5);
        HttpClient client = hcb.build();
        HttpConfig config = HttpConfig.custom().headers(headers)
                .url(url)
                .encoding("utf-8")
                .json(postJson)
                .client(client);
        String result = null;
        try {
            result = HttpClientUtil.post(config);
        } catch (HttpProcessException e) {
            e.printStackTrace();
        }
        return result;
    }
}
