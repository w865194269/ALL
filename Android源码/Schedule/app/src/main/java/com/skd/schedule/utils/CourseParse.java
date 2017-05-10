package com.skd.schedule.utils;

import com.skd.schedule.beans.Course;
import com.skd.schedule.beans.ListBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sunny on 2017/4/15.
 */

public class CourseParse {
    public static List<ListBean> CourseInfo(String htmlUrl){
        //记录课程信息
        List<ListBean> lists=new ArrayList<ListBean>();
        Map<String, String> map = new HashMap<String, String>();
        try {
            //获取网站，并返回一个Document对象
            Document doc = Jsoup.parse(htmlUrl);
            Elements content = doc.getElementsByTag("script");
            String regx = "\\<option value=([A-Za-z0-9]*)[^>]*\\>([^<>]*)\\<\\/option\\>";
            Pattern pat = Pattern.compile(regx);
            Matcher mat = pat.matcher(content.html());
            while(mat.find()){
                map.put(mat.group(1), mat.group(2));
                lists.add(new ListBean(mat.group(1),mat.group(2)));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return lists;
    }

    //课程课表
    //courseInfo("http://xg.zdsoft.com.cn/ZNPK/Private/List_XNXQKC.aspx?xnxq=20160");
    private static Map<String, String> courseInfo(String htmlUrl) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            //获取网站，并返回一个Document对象
            Document doc = Jsoup.connect(htmlUrl).get();
            //获取在doc里的标签为option的元素
            Elements content = doc.getElementsByTag("script");
            String regx = "\\<option value=([A-Za-z0-9]*)[^>]*\\>([^<>]*)\\<\\/option\\>";
            Pattern pat = Pattern.compile(regx);
            Matcher mat = pat.matcher(content.html());
            while(mat.find()){
                map.put(mat.group(1), mat.group(2));
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return map;
    }

    //课程课表 格式一
    public static List<Course> getData(String html, String id) throws Exception{
        List<Course> list = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        Document doc = Jsoup.parse(html);
        Elements table_content = doc.select("td");
        for (Element link : table_content) {
            sb.append(((Element) link).getAllElements().text().trim()+"\n");
        }
        String[] result = sb.toString().split("\n");
        for(int i = 13; i < result.length-8; i = i+9){
            Course c = new Course();
            c.setCourseid(id);
            c.setName(result[i]);
            c.setClassNum(result[i+1]);
            c.setNumber(result[i+2]);
            c.setCourseType(result[i+3]);
            c.setCredit(result[i+4]);
            c.setClassRoom(result[i+5]);
            c.setWeeks(result[i+6]);
            c.setSection(result[i+7]);
            c.setAddress(result[i+8]);
            list.add(c);
        }
        return list;
    }
    //标题
    public static String getTitle(String html) throws Exception{
        StringBuffer sb = new StringBuffer();
        Document doc = Jsoup.parse(html);
        Elements table_content = doc.select("td");
        for (Element link : table_content) {
            sb.append(((Element) link).getAllElements().text().trim()+"\n");
        }
        String[] result = sb.toString().split("\n");
        String title = result[2];
        return title;
    }
}
