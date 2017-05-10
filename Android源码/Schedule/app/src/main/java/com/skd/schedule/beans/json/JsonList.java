package com.skd.schedule.beans.json;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 43827_000 on 2017/4/24.
 */

public class JsonList<T> {
    private int code;
    private String msg;
    private List<T> result = null;

    public String getMsg() {
        return msg;
    }


    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<T> getResult() {


        if(result == null)
        {
            result = new ArrayList<T>();
        }

        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
