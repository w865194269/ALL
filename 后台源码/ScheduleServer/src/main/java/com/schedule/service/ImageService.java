package com.schedule.service;

import com.schedule.beans.Image;

public interface ImageService {

	/**
	 * 根据图片的id信息寻找
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Image getImageById(String id) throws Exception;
	
	/**
	 * 添加一条记录
	 * @param image
	 * @return
	 * @throws Exception
	 */
	public Image insertImage(Image image)throws Exception;
	
}
