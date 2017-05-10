package com.schedule.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.schedule.beans.ClazzSchedule;
import com.schedule.beans.Image;
import com.schedule.beans.ListBean;

/**
 * Service 获取班级课表
 *
 */
public interface ClazzService {

	/**
	 * 获取所有的班级列表
	 * @return
	 * @throws Exception
	 */
	public List<ListBean> queryAll() throws Exception;
	
	
	/**
	 * 获取班级课程表的信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Image getClazzSchedule(Map<String, String> map,String path)throws Exception;
	
	
}
