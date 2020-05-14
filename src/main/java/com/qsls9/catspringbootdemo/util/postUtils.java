package com.qsls9.catspringbootdemo.util;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class postUtils {
    public static final String URL ="http://openapi.tuling123.com/openapi/api/v2";
    public static final MediaType head = MediaType.parse("application/json; charset=utf-8");
    public static final String API_KEY = "307a0723c8594ac09ecd39cc0938f4b3";
    public static final String USER_ID = "147431828c4cbf45";

    public static String setPostMessage(String msg){
        Map<String,Object>  map = new HashMap<>();
        Map<String,Object>  perception = new HashMap<>();
        Map<String,String> userinfo = new HashMap<>();
        Map<String,String> inputText = new HashMap<>();
        perception.put("inputText",inputText);
        inputText.put("text",msg);
        userinfo.put("apiKey",API_KEY);
        userinfo.put("userId",USER_ID);
        map.put("userInfo",userinfo);
        map.put("reqType",0);
        map.put("perception",perception);
        return JSON.toJSONString(map);
    }

    public static String postJson(String RequestJsonbean) throws IOException {
        /**
         * 返回的仍然是json格式
         */
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(head, RequestJsonbean);
        Request request = new Request.Builder().url(URL).post(body).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    public static String getmessage(String JsonString) throws JSONException {
        JSONObject jo = JSONObject.parseObject(JsonString);
        String results = jo.getString("results");
        JSONObject values = null;
        if("[".equals(results.substring(0,1))){
            values = JSONObject.parseObject(results.substring(1,results.length()-1));
        } else {
            values = JSONObject.parseObject(results);
        }
        String text = values.getString("values");
        return  JSONObject.parseObject(text).getString("text");

    }

    public static String TLRobot(String msg) throws IOException, JSONException {
        return getmessage(postJson(setPostMessage(msg)));
    }
}
