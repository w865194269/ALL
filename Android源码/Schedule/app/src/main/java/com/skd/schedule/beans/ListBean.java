package com.skd.schedule.beans;

/**
 * Created by Wuchuang on 2017/4/17.
 * Spinner 下拉数据
 */

public class ListBean {
    private String cId;
    private String name;

    public ListBean() {
    }

    public ListBean(String id, String name) {
        this.cId = id;
        this.name = name;
    }

    public String getId() {
        return cId;
    }

    public void setId(String id) {
        this.cId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
