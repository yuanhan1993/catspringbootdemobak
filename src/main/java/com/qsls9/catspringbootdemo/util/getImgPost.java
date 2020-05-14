package com.qsls9.catspringbootdemo.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class getImgPost {

    public static final String URL ="http://192.168.0.103:8081/getrandomimg";
    public static final String APPKEY = "6d92c52a70ef1bffc40b9d72dd3ed86b7001b4cae655100ded764ed4478d9109";


    private static String getHTML(String url) {
        url = url+"?appkey="+APPKEY;
        StringBuffer buffer = new StringBuffer();
        BufferedReader bufferedReader = null;
        try {
            //创建URL对象
            java.net.URL u = new URL(url);
            //打开连接
            HttpURLConnection connection = (HttpURLConnection) u.openConnection();
            //从连接中拿到InputStream并由BufferedReader进行读取
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            //循环每次加入一行HTML内容 直到最后一行
            while ((line = bufferedReader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                //结束时候关闭释放资源
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return buffer.toString();
    }

    public static String dealJson(String JsonString,String key) {
        if("[".equals(JsonString.substring(0,1))){
            return JSONObject.parseObject(JsonString.substring(1,JsonString.length()-1)).getString(key);
        } else {
            return JSONObject.parseObject(JsonString).getString(key);
        }
    }

    public static String getimg(){
        return dealJson(dealJson(getHTML(URL),"data"),"Imgurl");
    }



/*    public static String getimg(String msg) throws IOException, JSONException {
        return dealJson(dealJson(postJson(setPostMessage(msg)),"data"),"Imgurl");
    }*/
/*    public static void main(String[] args) throws IOException {
        System.out.println(getimg());
    }*/
}
