package com.skd.schedule.beans;

public class Teacher {
    private String id;//id
    private String course;//课程
    private String credit;//学分
    private String teachingType;//授课方式
    private String courseType;//课程类别
    private String classNum;//上课班号
    private String classroom;//上课班级
    private String number;//上课人数
    private String time;//时间
    private String address;//地点

    private String tId;//教师的id

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String gettId() {
        return tId;
    }

    public void settId(String tId) {
        this.tId = tId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getTeachingType() {
        return teachingType;
    }

    public void setTeachingType(String teachingType) {
        this.teachingType = teachingType;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getClassRoom() {
        return classroom;
    }

    public void setClassRoom(String classroom) {
        this.classroom = classroom;
    }

    public boolean isTrue(){
        if (course==null|course.length()<1){
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Teacher [id=" + id + ", course=" + course + ", credit=" + credit + ", teachingType=" + teachingType
                + ", courseType=" + courseType + ", classNum=" + classNum + ", number=" + number + ", time=" + time
                + ", address=" + address + "]";
    }
}
