package com.schedule.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.schedule.beans.CSchedule;
import com.schedule.beans.ListBean;
import com.schedule.beans.json.JsonList;
import com.schedule.service.CourseService;

/**
 * @version 创建时间：2017年4月24日 上午11:43:01
 *
 */
@Controller
@RequestMapping(value = "/course/",
	produces = "application/json")
@ResponseBody
public class CourseController {
	
	@Autowired
	@Qualifier(value="courseService")
	private CourseService courseService;

	@RequestMapping(value="list",method=RequestMethod.GET)
	public JsonList<ListBean> getCourseList() throws Exception {
		return new JsonList<ListBean>(courseService.queryAll());
	}
    
	@RequestMapping(value="schedule",method=RequestMethod.POST,consumes = "application/json", 
			produces = "application/json;charset=UTF-8")
	public JsonList<CSchedule> getCourseSchedule(@RequestBody Map<String,String> map) throws Exception{
		map.put("type", "2");
		List<CSchedule> list=courseService.getCourseSchedule(map);
		return new JsonList<CSchedule>(list);
	}
	
}
