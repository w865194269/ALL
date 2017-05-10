package com.skd.schedule.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.skd.schedule.beans.Course;
import com.skd.schedule.beans.ListBean;
import com.skd.schedule.beans.Teacher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangxungong on 2017/4/14.
 */

public class DBManager {
    private DbHelper helper;
    private SQLiteDatabase db;
    public DBManager(Context context) {
        helper = new DbHelper(context);
    }
    /*
    添加教师课程信息
     */
    public void addTeacher(List<Teacher> teachers) {
        db = helper.getWritableDatabase();
        db.beginTransaction();  //开始事务
        try {
            for (Teacher person : teachers) {
                db.execSQL("INSERT INTO teacher VALUES(null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new Object[]{person.getId(), person.getCourse(), person.getCredit(), person.getTeachingType(), person.getCourseType(), person.getClassNum(), person.getClassRoom(), person.getNumber(), person.getTime(), person.getAddress()});
            }
            db.setTransactionSuccessful();  //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务
        }

    }
    /*
    添加教师列表
     */
    public void addTeacherList(List<ListBean> teachers) {
        db = helper.getWritableDatabase();
        db.beginTransaction();  //开始事务
        try {
            for (ListBean person : teachers) {
                db.execSQL("INSERT INTO teacherlist VALUES(null, ?, ?)", new Object[]{person.getId(), person.getName()});
            }
            db.setTransactionSuccessful();  //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务
        }

    }

    /*
    查询老师课表
     */
    public Cursor queryTheCursor(String id) {
        db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM teacher where id = ?", new String[]{id});
        return c;

    }

    public List<Teacher> queryTeacher(String id) {
        ArrayList<Teacher> teachers = new ArrayList<Teacher>();
        Cursor c = queryTheCursor(id);
        while (c.moveToNext()) {
            Teacher teacher = new Teacher();
            teacher.setId(c.getString(c.getColumnIndex("id")));
            teacher.setCourse(c.getString(c.getColumnIndex("course")));
            teacher.setCredit(c.getString(c.getColumnIndex("credit")));
            teacher.setTeachingType(c.getString(c.getColumnIndex("teachingType")));
            teacher.setCourseType(c.getString(c.getColumnIndex("courseType")));
            teacher.setClassNum(c.getString(c.getColumnIndex("classNum")));
            teacher.setClassRoom(c.getString(c.getColumnIndex("classroom")));
            teacher.setNumber(c.getString(c.getColumnIndex("number")));
            teacher.setTime(c.getString(c.getColumnIndex("time")));
            teacher.setAddress(c.getString(c.getColumnIndex("address")));
            teachers.add(teacher);
        }
        c.close();
        return teachers;
    }

    /*
  查询老师列表
   */
    public Cursor queryTheTeacherList() {
        db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM teacherlist", null);
        return c;
    }

    public List<ListBean> queryTeacherList() {
        ArrayList<ListBean> teachers = new ArrayList<ListBean>();
        Cursor c = queryTheTeacherList();
        while (c.moveToNext()) {
            ListBean teacherlist = new ListBean();
            teacherlist.setId(c.getString(c.getColumnIndex("jobnumber")));
            teacherlist.setName(c.getString(c.getColumnIndex("name")));
            teachers.add(teacherlist);
        }
        c.close();
        return teachers;
    }




    /*
   添加根据课程的信息
    */
    public void addCourse(List<Course> courses) {
        db = helper.getWritableDatabase();
        db.beginTransaction();  //开始事务
        try {
            for (Course course : courses) {
                db.execSQL("INSERT INTO course VALUES(null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", new Object[]{course.getCourseid(), course.getName(), course.getClassNum(), course.getNumber(), course.getCourseType(), course.getCredit(), course.getClassRoom(), course.getWeeks(), course.getSection(), course.getAddress()});
            }
            db.setTransactionSuccessful();  //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务
        }

    }

    /*
   查询课程课表
    */
    public Cursor queryCourseCursor(String id) {
        db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM course where courseid = ?", new String[]{id});
        return c;

    }

    public List<Course> queryCourse(String id) {
        ArrayList<Course> courses = new ArrayList<Course>();
        Cursor c = queryCourseCursor(id);
        while (c.moveToNext()) {
            Course course = new Course();
            course.setCourseid(c.getString(c.getColumnIndex("courseid")));
            course.setName(c.getString(c.getColumnIndex("name")));
            course.setClassNum(c.getString(c.getColumnIndex("classNum")));
            course.setNumber(c.getString(c.getColumnIndex("number")));
            course.setCourseType(c.getString(c.getColumnIndex("courseType")));
            course.setCredit(c.getString(c.getColumnIndex("credit")));
            course.setClassRoom(c.getString(c.getColumnIndex("classroom")));
            course.setWeeks(c.getString(c.getColumnIndex("weeks")));
            course.setSection(c.getString(c.getColumnIndex("section")));
            course.setAddress(c.getString(c.getColumnIndex("address")));
            courses.add(course);
        }
        c.close();
        return courses;
    }

    /*
    添加课程列表
     */
    public void addCourseList(List<ListBean> courses) {
        db = helper.getWritableDatabase();
        db.beginTransaction();  //开始事务
        try {
            for (ListBean course : courses) {
                db.execSQL("INSERT INTO courselist VALUES(null, ?, ?)", new Object[]{course.getId(), course.getName()});
            }
            db.setTransactionSuccessful();  //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务
        }

    }


    /*
  查询课程列表
   */
    public Cursor queryTheCourseList() {
        db = helper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM courselist", null);
        return c;

    }
    public List<ListBean> queryCourseList() {
        ArrayList<ListBean> courses = new ArrayList<ListBean>();
        Cursor c = queryTheCourseList();
        while (c.moveToNext()) {
            ListBean courselist = new ListBean();
            courselist.setId(c.getString(c.getColumnIndex("id")));
            courselist.setName(c.getString(c.getColumnIndex("name")));
            courses.add(courselist);
        }
        c.close();
    return courses;
}




    /*
   添加班级列表
    */
    public void addClassList(List<ListBean> classes) {
        db = helper.getWritableDatabase();
        db.beginTransaction();  //开始事务
        try {
            for (ListBean clas : classes) {
                db.execSQL("INSERT INTO classlist VALUES(null, ?, ?)", new Object[]{clas.getId(), clas.getName()});
            }
            db.setTransactionSuccessful();  //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务
        }

    }


    /*
  查询班级列表
   */
    public Cursor queryClassList() {
        db = helper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM classlist", null);
        return c;

    }
    public List<ListBean> queryAllClassList() {
        ArrayList<ListBean> classes = new ArrayList<ListBean>();
        Cursor c = queryClassList();
        while (c.moveToNext()) {
            ListBean classlist = new ListBean();
            classlist.setId(c.getString(c.getColumnIndex("id")));
            classlist.setName(c.getString(c.getColumnIndex("name")));
            classes.add(classlist);
        }
        c.close();
        return classes;
    }



    public void closeDB() {
        db.close();
    }
}
