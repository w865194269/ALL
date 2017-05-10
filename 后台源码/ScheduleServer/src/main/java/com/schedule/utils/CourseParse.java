package com.schedule.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.schedule.beans.CSchedule;
import com.schedule.beans.ListBean;
import com.schedule.controller.ImageController;
import com.schedule.exception.bean.BaseException;

/**
 * 课程解析
 *
 */
public class CourseParse {

	private final String path = "http://xg.zdsoft.com.cn/ZNPK/Private/List_XNXQKC.aspx";
	private final String path_sch = "http://xg.zdsoft.com.cn/ZNPK/KBFB_LessonSel_rpt.aspx";
	private final String REFERER = "http://xg.zdsoft.com.cn/ZNPK/KBFB_LessonSel.aspx";

	/**
	 * 获取课程列表
	 * 
	 * @return
	 */
	public List<ListBean> getCourseList() {
		int t = new Random().nextInt(400) + 100;
		String url = path + "?xnxq=20160&t=" + t;
		List<ListBean> list = parseCourseHtml(getInfoList(url));
		return list;
	}

	public List<CSchedule> getScheduleList(Map<String, String> params) throws Exception {
		List<CSchedule> list = null;
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("User-Agent",
				"Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.110 Safari/537.36");
		 headers.put("Cookie", ImageController.cookie);
		headers.put("Referer", REFERER);
		String html=getScheduleInfo(path_sch, headers, params);
		if (html.contains("验证码")) {
			throw new BaseException("验证码出错");
		}
		System.out.println(html);
		list=parseScheduleHtml(html,params.get("Sel_KC"));
		return list;
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

	public List<ListBean> parseCourseHtml(String html) {
		// 记录课程信息
		List<ListBean> lists = new ArrayList<ListBean>();
		try {
			Document doc = Jsoup.parse(html);
			Elements content = doc.getElementsByTag("script");
			String regx = "\\<option value=([A-Za-z0-9]*)[^>]*\\>([^<>]*)\\<\\/option\\>";
			Pattern pat = Pattern.compile(regx);
			Matcher mat = pat.matcher(content.html());
			while (mat.find()) {
				lists.add(new ListBean(mat.group(1), mat.group(2)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lists;
	}

	// 课程课表 格式一
	public static List<CSchedule> parseScheduleHtml(String html,String cId) {
		List<CSchedule> list = new ArrayList<CSchedule>();
		StringBuffer sb = new StringBuffer();
		Document doc = Jsoup.parse(html);
		Elements table_content = doc.select("td");
		for (Element link : table_content) {
			sb.append(((Element) link).getAllElements().text().trim() + "\n");
		}
		String[] result = sb.toString().split("\n");
		int index = 0;
		for (int i = 0; i < result.length; i++) {
			if (result[i].equals("地点")) {
				index = i + 1;
			}
		}
		for (int i = index; i < result.length - 8; i = i + 9) {
			CSchedule c = new CSchedule();
			c.setcId(cId);
			c.setName(result[i]);
			c.setClassNum(result[i + 1]);
			c.setNumber(result[i + 2]);
			c.setCourseType(result[i + 3]);
			c.setCredit(result[i + 4]);
			c.setClassRoom(result[i + 5]);
			c.setWeeks(result[i + 6]);
			c.setSection(result[i + 7]);
			c.setAddress(result[i + 8]);
			list.add(c);
		}
		for (int i = 0; i < list.size() - 1; i++) {
			if (list.get(i + 1).getName().equals("") && list.get(i + 1).getClassNum().equals("")
					&& list.get(i + 1).getNumber().equals("") && list.get(i + 1).getCourseType().equals("")
					&& list.get(i + 1).getCredit().equals("") && list.get(i + 1).getClassRoom().equals("")) {
				list.get(i + 1).setName(list.get(i).getName());
				list.get(i + 1).setClassNum(list.get(i).getClassNum());
				list.get(i + 1).setNumber(list.get(i).getNumber());
				list.get(i + 1).setCourseType(list.get(i).getCourseType());
				list.get(i + 1).setCredit(list.get(i).getCredit());
				list.get(i + 1).setClassRoom(list.get(i).getClassRoom());
			}
		}
		return list;
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
