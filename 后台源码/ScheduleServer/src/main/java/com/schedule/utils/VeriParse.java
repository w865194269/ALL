package com.schedule.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.schedule.controller.ImageController;

/**
 * 获取验证码
 *
 */
public class VeriParse {

	private  HttpURLConnection conn = null;
	
    /**
     * 获取验证码的输入流
     *
     * @return
     */
    public  InputStream getVerificationCode(String path) {
       
        InputStream is=null;
        try {
            URL url = new URL(path);
            conn = (HttpURLConnection) url.openConnection();
            is = conn.getInputStream();
            //保存cookie
            ImageController.cookie=conn.getHeaderField("Set-cookie").split(";")[0];
        } catch (IOException e) {
            e.printStackTrace();
        } 
        return is;
    }

    /**
     * 关闭连接
     */
   public void release(){
	   if (conn!=null) {
		conn.disconnect();
	}
   }

	
}
