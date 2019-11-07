package com.dcits.sonic.util;

import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.builder.HCB;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.HttpHeader;
import com.arronlong.httpclientutil.exception.HttpProcessException;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;

public class HTTPUtil {
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
