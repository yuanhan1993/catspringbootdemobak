package com.qsls9.catspringbootdemo.util;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Objects;

public class HttpUtils {

    public String sendGet(String url) {
        if (!url.contains("http")) {
            url = "http://" + url;
        }
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder request = new Request.Builder().url(url);
        request.get();
        request.addHeader("Accept", "text/html,application/xhtml+xml," +
                "application/xml;q=0.9,image/webp,*/*;q=0.8");
        request.addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36" +
                " (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
        Call call = okHttpClient.newCall(request.build());
        Response response = null;
        try {
            response = call.execute();

        } catch (IOException e) {
            System.out.println("HttpUtils异常");
        }
        try {
            assert response != null;
            String res = Objects.requireNonNull(response.body()).string();
            if (response.code() != 200) {
                res = "{\"code\":" + response.code() + "}";
            }

            return res;
        } catch (IOException e) {
            System.out.println("HttpUtil异常");
        }
        return "-1";
    }

    public String sendGet(String url, ArrayList<String> header) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder request = new Request.Builder().url(url);
        for (Object arr : header) {
            String[] arrs = arr.toString().split(":");
            if (arrs.length >= 1) {
                request.addHeader(arrs[0], arrs[1]);
            }
        }
        request.get();
        Call call = okHttpClient.newCall(request.build());
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            assert response != null;
            return Objects.requireNonNull(response.body()).string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "-1";
    }

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static String postJson(String api, String RequestJsonbean) throws IOException {
        /**
         * 返回的仍然是json格式
         */
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, RequestJsonbean);
        Request request = new Request.Builder().url(api).post(body).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }


    public void sendPost(String url, ArrayList<String> param, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        for (Object arr : param) {
            String[] arrs = arr.toString().split("=");
            if (arrs.length >= 1) {
                formBodyBuilder.add(arrs[0], arrs[1]);
            }
        }
        Request.Builder builder = new Request.Builder()
                .url(url)
                .post(formBodyBuilder.build());
        builder.addHeader("Accept", "text/html,application/xhtml+xml," +
                "application/xml;q=0.9,image/webp,*/*;q=0.8");
        builder.addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36" +
                " (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
        Request request = builder.build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public void sendPost(String url, ArrayList<String> param, ArrayList<String> header, Callback callback) {
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder formBodyBuilder = new FormBody.Builder();
        for (Object arr : param) {
            String[] arrs = arr.toString().split("=");
            if (arrs.length >= 1) {
                formBodyBuilder.add(arrs[0], arrs[1]);
            }
        }
        Request.Builder builder = new Request.Builder()
                .url(url)
                .post(formBodyBuilder.build());
        for (Object arr : header) {
            String[] arrs = arr.toString().split(":");
            if (arrs.length >= 1) {
                builder.addHeader(arrs[0], arrs[1]);
            }
        }
        Request request = builder.build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public static String getLongUrl(String shorturl) {
        Request request = new Request.Builder().url(shorturl).build();
        try {
            HttpUrl a = new OkHttpClient().newCall(request).execute().request().url();
            return a.toString();
        } catch (IOException e) {
            return "getLongUrlIO异常";
        }
    }

    public static String setShortUrl(String longurl) {
        // https://www.98api.cn/ 提供的缩短api
        String response = new HttpUtils().sendGet("https://www.98api.cn/api/sinaDwz.php?url=" + longurl);
        JSONObject jsonObject = JSONObject.parseObject(response);
        String shortUrl = jsonObject.getString("short_url");
        if (StringUtils.isEmpty(shortUrl)) {
            try {
                return URLDecoder.decode(jsonObject.toString(), "ASCII");
            } catch (UnsupportedEncodingException e) {
                System.out.println("转换异常");
            }
        }
        return shortUrl;
    }
}
