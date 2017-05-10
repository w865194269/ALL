package com.schedule.dao;

import java.util.List;

import com.schedule.beans.ListBean;

/** 
* @version 创建时间：2017年4月23日 下午7:00:41 
*
*/
public interface CourseDao {

	/**
	 * 从数据库中查询所有的课程信息
	 * @return
	 * @throws Exception
	 */
	public List<ListBean> queryAll() throws Exception;
	
	/**
	 *向数据库中添加课程 
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public int insertCourseList(List<ListBean> list) throws Exception;
	
	
	
}
