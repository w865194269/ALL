package com.schedule.dao;

import com.schedule.beans.User;

public interface UserDao {

	/**
	 * 用户信息添加
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public void addUser(User user) throws Exception;
	
	/**
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public User getUser(User user) throws Exception;
}
