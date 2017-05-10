package com.skd.schedule.beans;

/**
 * Created by Wuchuang on 2017/4/14.
 */

public class Course {
    private String cId;//课程id
    private String name;//任课教师
    private String classNum;//上课班号
    private String number;//上课人数
    private String courseType;//课程类别
    private String credit;//考核方式
    private String classRoom;//上课班级
    private String weeks;//周次
    private String section;//节次
    private String address;//地点

    public String getCourseid() {
        return cId;
    }
    public void setCourseid(String courseid) {
        this.cId = courseid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getClassNum() {
        return classNum;
    }
    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public String getCourseType() {
        return courseType;
    }
    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }
    public String getCredit() {
        return credit;
    }
    public void setCredit(String credit) {
        this.credit = credit;
    }
    public String getClassRoom() {
        return classRoom;
    }
    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }
    public String getWeeks() {
        return weeks;
    }
    public void setWeeks(String weeks) {
        this.weeks = weeks;
    }
    public String getSection() {
        return section;
    }
    public void setSection(String section) {
        this.section = section;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    @Override
    public String toString() {
        return "Course [name=" + name + ", classNum=" + classNum + ", number=" + number + ", courseType=" + courseType
                + ", credit=" + credit + ", classRoom=" + classRoom + ", weeks=" + weeks + ", section=" + section
                + ", address=" + address + "]";
    }
}
