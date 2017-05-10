package com.skd.schedule.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by Wuchuang on 2017/4/14.
 */

public class ServiceManger {
    private static Gson gson;

    /**
     * 获取验证码
     *
     * @return
     */
    public static synchronized Bitmap getVerificationCode(String path) {
        Bitmap bitmap = null;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(path);
            conn = (HttpURLConnection) url.openConnection();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            //保存cookie
//           ScheduleApplication.setCookies(conn.getHeaderField("Set-cookie").split(";")[0]);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            release(conn,null,null);
        }
        return bitmap;
    }

    public synchronized static String getInfoList(String path){
        StringBuffer sb=new StringBuffer();
        HttpURLConnection conn = null;

        BufferedReader reader=null;
        try {
            URL url = new URL(path);
            conn = (HttpURLConnection) url.openConnection();
            conn.connect();
            reader=new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
            String line=null;
            while ((line=reader.readLine())!=null){
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            release(conn,null,reader);
        }
        Log.e("Log",path);
        Log.e("Log",sb.toString());
        return sb.toString();
    }
    public static <T> String mapToJson(Map<String, T> map) {
        String jsonStr = gson.toJson(map);
        return jsonStr;
    }
    /**
     * 使用POST请求
     *
     * @param path
     * @param headers
     * @param params
     * @return
     */
    public static synchronized String  getScheduleInfo(String path, Map<String, String> headers, Map<String, String> params) {
        StringBuffer sb = new StringBuffer();
        StringBuffer result=new StringBuffer();
        gson = new Gson();
        HttpURLConnection conn = null;
        OutputStream os=null;
        BufferedReader reader=null;
        try {
            URL url = new URL(path);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/json");
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setDoInput(true);
            //设置请求头
//            for (String key : headers.keySet()) {
//                conn.setRequestProperty(key, headers.get(key));
//            }
            //参数
//            for (String key : params.keySet()) {
//                sb.append(key + "=" + params.get(key) + "&");
//            }


            String  paramsJson = mapToJson(params);
            Log.i("log",paramsJson+"json");

//            sb.deleteCharAt(sb.length() - 1);
            os = conn.getOutputStream();
            os.write(paramsJson.getBytes());
            os.flush();
            os.close();
//            sb.delete(0, sb.length());

                reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
                String line=null;
                while((line=reader.readLine())!=null){
                    result.append(line);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            release(conn,os,reader);
        }
        Log.e("Log",result.toString());
        return result.toString();
    }

    /**
     * 释放资源
     * @param conn
     * @param os
     * @param reader
     */
    private static void release(HttpURLConnection conn,OutputStream os,BufferedReader reader){
        try {
            if (conn!=null){
                conn.disconnect();
            }
            if (os!=null){
                os.close();
            }
            if (reader!=null){
                reader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
