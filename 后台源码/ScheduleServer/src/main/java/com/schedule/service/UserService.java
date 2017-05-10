package com.schedule.service;

import com.schedule.beans.User;

public interface UserService {

	/**
	 * 用户信息添加
	 * @param u
	 * @return
	 * @throws Exception
	 */
	public User addUser(User user) throws Exception;
	
	/**
	 * 查询用户信息
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public User getUser(User user) throws Exception;
	
}
