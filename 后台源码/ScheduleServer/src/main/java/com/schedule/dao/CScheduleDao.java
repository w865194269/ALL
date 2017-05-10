package com.schedule.dao;

import java.util.List;

import com.schedule.beans.CSchedule;

/**
 * @version 创建时间：2017年4月23日 下午7:00:41
 *
 */
public interface CScheduleDao {
	/**
	 * 查看课程信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<CSchedule> querySchedule(String id) throws Exception;
	
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public void insertScheduleList(List<CSchedule> list) throws Exception;
}
