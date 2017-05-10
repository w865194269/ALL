package com.schedule.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.schedule.beans.ListBean;
import com.schedule.controller.ImageController;
import com.schedule.exception.bean.BaseException;

/**
 * 班级课表解析
 *
 */
public class ClazzParse {

	private static final String reffere = "http://xg.zdsoft.com.cn/ZNPK/KBFB_ClassSel.aspx";
	private static final String list_path = "http://xg.zdsoft.com.cn/ZNPK/KBFB_ClassSel.aspx";
	private static final String img_path_preffix = "http://xg.zdsoft.com.cn/ZNPK/";
	private static final String img_path_from = "http://xg.zdsoft.com.cn/ZNPK/KBFB_ClassSel_rpt.aspx";

	/**
	 * 获取班级列表
	 * 
	 * @return
	 */
	public List<ListBean> getCourseList() {
		return parseClassInfo(getInfoList(list_path));
	}

	/**
	 * 获取课程的表格
	 * 
	 * @param path
	 * @param params
	 * @return
	 * @throws BaseException
	 */
	public boolean getClazzSchedule(Map<String, String> params,String fileName) throws BaseException {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("User-Agent",
				"Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.110 Safari/537.36");
		headers.put("Cookie", ImageController.cookie);
		headers.put("Referer", reffere);
		String html = getScheduleInfo(img_path_from, headers, params);
		if (html.contains("验证码")) {
			throw new BaseException("验证码错误");
		}
		System.out.println(html);
		String img_path = img_path_preffix + getImgUrl(html);
		System.out.println(img_path);
		InputStream is = null;
		FileOutputStream fos=null;
		HttpURLConnection conn = null;
		try {
			URL url = new URL(img_path);
			conn = (HttpURLConnection) url.openConnection();
			// 设置请求头
			for (String key : headers.keySet()) {
				conn.setRequestProperty(key, headers.get(key));
			}
			is = conn.getInputStream();
			fos=new FileOutputStream(new File(fileName));
			byte []buffer=new byte[256];
			int len=0;
			while((len=is.read(buffer))>0){
				fos.write(buffer, 0, len);
			}
			fos.flush();
			fos.close();
			// 保存cookie
			// ImageController.cookie=conn.getHeaderField("Set-cookie").split(";")[0];
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return true;
	}

	// 图片地址
	public String getImgUrl(String html) {
		StringBuffer sb = new StringBuffer();
		Document doc = Jsoup.parse(html);
		Elements table_content = doc.select("img");
		for (Element link : table_content) {
			sb.append(link.attr("src").trim() + "\n");
		}
		return sb.toString();
	}

	/**
	 * 使用POST请求
	 *
	 * @param path
	 * @param headers
	 * @param params
	 * @return
	 */
	public String getScheduleInfo(String path, Map<String, String> headers, Map<String, String> params) {
		StringBuffer sb = new StringBuffer();
		StringBuffer result = new StringBuffer();
		HttpURLConnection conn = null;
		OutputStream os = null;
		BufferedReader reader = null;
		try {
			URL url = new URL(path);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setDoInput(true);
			// 设置请求头
			for (String key : headers.keySet()) {
				conn.setRequestProperty(key, headers.get(key));
			}
			// 参数
			for (String key : params.keySet()) {
				sb.append(key + "=" + params.get(key) + "&");
			}

			sb.deleteCharAt(sb.length() - 1);
			os = conn.getOutputStream();
			os.write(sb.toString().getBytes());
			os.flush();
			// os.close();
			sb.delete(0, sb.length());
			if (conn.getResponseCode() == 200) {
				reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "GBK"));
				String line = null;
				while ((line = reader.readLine()) != null) {
					result.append(line);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			release(conn, os, reader);
		}
		return result.toString();
	}

	public String getInfoList(String path) {
		StringBuffer sb = new StringBuffer();
		HttpURLConnection conn = null;

		BufferedReader reader = null;
		try {
			URL url = new URL(path);
			conn = (HttpURLConnection) url.openConnection();
			conn.connect();
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "gbk"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			release(conn, null, reader);
		}
		return sb.toString();
	}

	public static List<ListBean> parseClassInfo(String htmlUrl) {
		// 记录班级信息
		List<ListBean> list = new ArrayList<ListBean>();
		// 获取网站，并返回一个Document对象
		Document doc = Jsoup.parse(htmlUrl);
		// 获取在doc里的标签为option的元素
		Elements links = doc.getElementsByTag("option");
		for (Element link : links) {
			list.add(new ListBean(link.val().trim(), link.text().trim()));
		}
		return list;
	}

	/**
	 * 释放资源
	 * 
	 * @param conn
	 * @param os
	 * @param reader
	 */
	private void release(HttpURLConnection conn, OutputStream os, BufferedReader reader) {
		try {
			if (conn != null) {
				conn.disconnect();
			}
			if (os != null) {
				os.close();
			}
			if (reader != null) {
				reader.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
