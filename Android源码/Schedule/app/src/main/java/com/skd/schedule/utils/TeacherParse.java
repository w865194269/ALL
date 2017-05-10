package com.skd.schedule.utils;

import com.skd.schedule.beans.ListBean;
import com.skd.schedule.beans.Teacher;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TeacherParse {
	//教师课表调用用例
	//TeacherInfo("http://xg.zdsoft.com.cn/ZNPK/Private/List_JS.aspx?xnxq=20160");
	public static List<ListBean> TeacherInfo(String htmlUrl){
		//记录教师信息
		List<ListBean> lists=new ArrayList<ListBean>();
//		Map<String, String> map = new HashMap<String, String>();
		try {
			//获取网站，并返回一个Document对象
			Document doc = Jsoup.parse(htmlUrl);
			Elements content = doc.getElementsByTag("script");
			String regx = "\\<option value=([A-Za-z0-9]*)[^>]*\\>([^<>]*)\\<\\/option\\>";
			Pattern pat = Pattern.compile(regx);
			Matcher mat = pat.matcher(content.html());
			while(mat.find()){
//				map.put(mat.group(1).trim(), mat.group(2).trim());
				lists.add(new ListBean(mat.group(1),mat.group(2)));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lists;
	}
	//教师课表
	public static List<Teacher> getData(String html,String id){
		List<Teacher> list = new ArrayList<>();
		StringBuffer sb = new StringBuffer();
		Document doc = Jsoup.parse(html);
		Elements table_content = doc.select("td");
		for (Element link : table_content) {
			sb.append(((Element) link).getAllElements().text().trim()+"\n");
		}
		String[] result = sb.toString().split("\n");
		for(int i = 14; i < result.length-11; i = i+10){
			Teacher t = new Teacher();
//			t.setId(result[i]);
//			t.settId(id);
			t.setId(id);
			t.setCourse(result[i+1]);
			t.setCredit(result[i+2]);
			t.setTeachingType(result[i+3]);
			t.setCourseType(result[i+4]);
			t.setClassNum(result[i+5]);
			t.setClassRoom(result[i+6]);
			t.setNumber(result[i+7]);
			t.setTime(result[i+8]);
			t.setAddress(result[i+9]);
			list.add(t);
		}
		return list;
	}
}
