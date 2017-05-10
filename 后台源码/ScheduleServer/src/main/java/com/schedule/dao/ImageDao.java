package com.schedule.dao;

import com.schedule.beans.Image;

public interface ImageDao {

	/**
	 * 获取图片信息,地址
	 * @return
	 * @throws Exception
	 */
	public Image getImagePath(String id)throws Exception;
	
	/**
	 * 添加一条图片的记录
	 * @param image
	 * @throws Exception
	 */
	public void InsertImageInfo(Image image)throws Exception;
	
}
