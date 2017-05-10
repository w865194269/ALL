package com.schedule.service;

import java.util.List;
import java.util.Map;

import com.schedule.beans.CSchedule;
import com.schedule.beans.ListBean;

/** 
* @version 创建时间：2017年4月24日 下午12:08:35 
*
*/
public interface CourseService {

	/**
	 * 获取所有的课程信息
	 * @return
	 * @throws Exception
	 */
	public List<ListBean> queryAll() throws Exception;
	
	
	/**
	 * 获取课程表的信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<CSchedule> getCourseSchedule(Map<String, String> map)throws Exception;
}
