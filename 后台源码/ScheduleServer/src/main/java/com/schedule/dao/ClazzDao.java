package com.schedule.dao;

import java.util.List;

import com.schedule.beans.ListBean;

/**
 * 
 */
public interface ClazzDao {

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
	public int insertClazzList(List<ListBean> list) throws Exception;
}
