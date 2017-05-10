package com.schedule.dao;

import java.util.List;

import com.schedule.beans.ClazzSchedule;

/**
 * @version 创建时间：2017年4月23日 下午7:00:41
 *
 */
public interface ClazzScheduleDao {
	/**
	 * 查看课程信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<ClazzSchedule> querySchedule(String id) throws Exception;
	
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public void insertScheduleList(List<ClazzSchedule> list) throws Exception;
}
