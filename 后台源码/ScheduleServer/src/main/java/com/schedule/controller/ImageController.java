package com.schedule.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.schedule.exception.bean.BaseException;
import com.schedule.utils.VeriParse;

@Controller
public class ImageController {

	@Autowired
	@Qualifier("veriParse")
	private VeriParse veriParse;
	
	public static String cookie="";
	
	@RequestMapping(value = "/verification", method = RequestMethod.GET)
	public void getVerificationImage(HttpServletResponse respose) throws BaseException {
//		respose.setContentType("image/**");
		InputStream in = null;
		OutputStream os = null;
		try {
			os = respose.getOutputStream();
			in = veriParse.getVerificationCode("http://xg.zdsoft.com.cn/sys/ValidateCode.aspx");
			byte buffer[] = new byte[128];
			int len=0;
			while ((len=in.read(buffer))>0){
				os.write(buffer, 0, len);
			}
			respose.addHeader("Content-Disposition", "attachment;fileName=" + "verification.png");// 设置文件名
			os.flush();
		} catch (IOException e) {
			throw new BaseException("验证码获取失败");
		} finally {
			veriParse.release();
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
