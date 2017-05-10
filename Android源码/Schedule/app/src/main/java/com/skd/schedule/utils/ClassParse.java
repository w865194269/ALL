package com.skd.schedule.utils;

import com.skd.schedule.beans.ListBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class ClassParse {

    //班级课表调用用例
    //ClassInfo("http://xg.zdsoft.com.cn/ZNPK/KBFB_ClassSel.aspx");
//	public static Map<String, String> ClassInfo(String htmlUrl){
    public static List<ListBean> ClassInfo(String htmlUrl) {
        //记录班级信息
        List<ListBean> list = new ArrayList<ListBean>();
//        Map<String, String> map = new HashMap<String, String>();

        //获取网站，并返回一个Document对象
        Document doc = Jsoup.parse(htmlUrl);
        ;
        //获取在doc里的标签为option的元素
        Elements links = doc.getElementsByTag("option");
        for (Element link : links) {
            //获取option标签中属性为value的值和文本信息
//            map.put(link.val(), linknk.text());
            list.add(new ListBean(link.val().trim(), link.text().trim()));
        }
        return list;
    }

    //标题
    public static String getTitle(String html) throws Exception {
        StringBuffer sb = new StringBuffer();
        Document doc = Jsoup.parse(html);
        Elements table_content = doc.select("td");
        for (Element link : table_content) {
            sb.append(link.text().trim() + "\n");
        }
        String[] result = sb.toString().split("\n");
        return result[3];
    }

    //图片地址
    public static String getImgUrl(String html) {
        StringBuffer sb = new StringBuffer();
        Document doc = Jsoup.parse(html);
        Elements table_content = doc.select("img");
        for (Element link : table_content) {
            sb.append(link.attr("src").trim() + "\n");
        }
        return sb.toString();
    }

}
