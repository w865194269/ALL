package com.schedule.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.schedule.beans.Image;
import com.schedule.dao.ImageDao;
import com.schedule.exception.bean.BaseException;
import com.schedule.service.ImageService;

@Service("imageService")
public class ImageServiceImp implements ImageService {

	@Autowired
	@Qualifier("imageDao")
	private ImageDao imageDao;

	@Override
	public Image getImageById(String id) throws Exception {
		Image img = imageDao.getImagePath(id);
		if(img==null){
			throw new BaseException("请输入验证码");
		}
		return img;
	}

	@Override
	public Image insertImage(Image image) throws Exception {
		return null;
	}

}
