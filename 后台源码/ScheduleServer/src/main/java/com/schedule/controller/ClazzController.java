package com.schedule.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.schedule.beans.Image;
import com.schedule.beans.ListBean;
import com.schedule.beans.json.JsonList;
import com.schedule.beans.json.JsonObject;
import com.schedule.exception.bean.BaseException;
import com.schedule.service.ClazzService;
import com.schedule.service.ImageService;

/**
 * 
 * 班级课程查询Controller
 */
@Controller
@RequestMapping("/clazz/")
public class ClazzController {

	@Autowired
	@Qualifier("clazzService")
	private ClazzService clazzService;

	@Autowired
	@Qualifier("imageService")
	private ImageService imageService;

	@RequestMapping(value = "list", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	@ResponseBody
	public JsonList<ListBean> getClazzList() throws Exception {
		return new JsonList<ListBean>(clazzService.queryAll());
	}

	/**
	 * 获取图片的地址,图片已经存在本地
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "local/path", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public JsonObject getClazzScheduleLocaPath(@RequestBody Map<String, String> map) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append(map.get("Sel_XNXQ") + map.get("Sel_XZBJ"));
		Image img = imageService.getImageById(sb.toString());
		return new JsonObject(img);
	}

	/**
	 * 根据携带验证码去获取图片,将图片存到本地,返回图片的地址
	 * 
	 * @param map
	 * @throws Exception
	 */
	@RequestMapping(value = "net/path", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public JsonObject getClazzScheduleNetPath(@RequestBody Map<String, String> map, HttpServletRequest request)
			throws Exception {
		String path = (String) request.getServletContext().getInitParameter("storePath");
		System.out.println(path);
		map.put("type", "2");
		map.put("txtxzbj", "");
		Image image = clazzService.getClazzSchedule(map, path);
		return new JsonObject(image);
	}

	@RequestMapping(value = "schedule/{name:.+}", method = RequestMethod.GET)
	public void getClazzSchudele(@PathVariable("name") String name, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String path = (String) request.getServletContext().getInitParameter("storePath")+name ;
		FileInputStream fis = new FileInputStream(new File(path));
		OutputStream os = response.getOutputStream();
		byte[] buffer = new byte[256];
		int len = 0;
		while ((len = fis.read(buffer)) > 0) {
			os.write(buffer, 0, len);
		}
		response.addHeader("Content-Disposition", "attachment;fileName=" + name);
		os.flush();
		os.close();
		fis.close();
	}

}
