package com.schedule.service.imp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.schedule.beans.CSchedule;
import com.schedule.beans.ListBean;
import com.schedule.dao.CScheduleDao;
import com.schedule.dao.CourseDao;
import com.schedule.service.CourseService;
import com.schedule.utils.CourseParse;

/**
 * @version 创建时间：2017年4月24日 下午12:07:58
 *
 */
@Service(value = "courseService")
public class CourseServiceImp implements CourseService {

	@Autowired
	@Qualifier(value = "courseDao")
	private CourseDao courseDao;

	@Autowired
	@Qualifier("courseParse")
	private CourseParse courseParse;
	@Autowired
	private CScheduleDao cScheduleDao;

	public List<ListBean> queryAll() throws Exception {
		List<ListBean> list = courseDao.queryAll();
		if (list == null || list.size() == 0) {
			list = courseParse.getCourseList();
			courseDao.insertCourseList(list);// 向数据库中添加数据
		}
		return list;
	}

	public List<CSchedule> getCourseSchedule(Map<String, String> map) throws Exception {
		String id=map.get("Sel_KC");
		List<CSchedule> list=cScheduleDao.querySchedule(id);	
		
		if (list==null||list.size()==0) {
			list=courseParse.getScheduleList(map);
			if (list!=null&&list.size()>0) {
				cScheduleDao.insertScheduleList(list);
			}
		}
		
		return list;
	}

}
