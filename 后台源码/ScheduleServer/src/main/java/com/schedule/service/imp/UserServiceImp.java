package com.schedule.service.imp;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.schedule.beans.User;
import com.schedule.dao.UserDao;
import com.schedule.exception.bean.BaseException;
import com.schedule.service.UserService;

@Service("userService")
public class UserServiceImp implements UserService {

	@Autowired
	@Qualifier("userDao")
	private UserDao userDao;

	public User addUser(User user) throws BaseException {
		try {
			userDao.addUser(user);
			user = userDao.getUser(user);
		} catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				throw new BaseException("用户已存在");
			} else {
				throw new BaseException(e.getMessage());
			}
		}
		return user;
	}

	public User getUser(User user) throws Exception {
		return null;
	}

}
