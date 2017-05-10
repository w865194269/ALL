package com.skd.schedule.beans.json;

/**
 * Created by 43827_000 on 2017/4/30.
 */

public class JsonObject<T> {
    private int code;
    private String msg;
    private T result = null;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
