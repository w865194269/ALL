package com.schedule.service.imp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.schedule.beans.ClazzSchedule;
import com.schedule.beans.Image;
import com.schedule.beans.ListBean;
import com.schedule.dao.ClazzDao;
import com.schedule.dao.ImageDao;
import com.schedule.exception.bean.BaseException;
import com.schedule.service.ClazzService;
import com.schedule.utils.ClazzParse;

@Service("clazzService")
public class ClazzServiceImp implements ClazzService {

	@Autowired
	@Qualifier("clazzParse")
	private ClazzParse clazzParse;
	@Autowired
	@Qualifier("clazzDao")
	private ClazzDao clazzDao;
	@Autowired
	@Qualifier("imageDao")
	private ImageDao imageDao;

	@Override
	public List<ListBean> queryAll() throws Exception {
		List<ListBean> list = clazzDao.queryAll();
		if (list == null || list.size() == 0) {
			list = clazzParse.getCourseList();
			clazzDao.insertClazzList(list);// 向数据库中添加数据
		}
		return list;
	}

	/**
	 * 从网络上请求获取图片,保存在本地,同时将图片地址保存在数据库
	 */
	@Override
	public Image getClazzSchedule(Map<String, String> map, String path) throws Exception {
		StringBuffer id = new StringBuffer();
		id.append(map.get("Sel_XNXQ") + map.get("Sel_XZBJ"));
		Image image=imageDao.getImagePath(id.toString());
		if (image!=null) {
			return image;
		}
		String name=UUID.randomUUID().toString().replaceAll("-", "")+".png";
		String namePath=path+name;
		System.out.println(namePath);
		 image=new Image(id.toString(), name);
		if (clazzParse.getClazzSchedule(map, namePath)) {
			imageDao.InsertImageInfo(image);
		}
		return image;
	}

}
