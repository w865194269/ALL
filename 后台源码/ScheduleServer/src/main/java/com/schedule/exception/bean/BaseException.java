package com.schedule.exception.bean;

import com.schedule.beans.json.JsonObject;

public class BaseException extends Exception {

	private int code = -1;

	public BaseException(String message) {
		super(message, null);
	}

	public BaseException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public int getErrorCode() {
		return code;
	}

	public void setErrorCode(int errorCode) {
		this.code = errorCode;
	}

	public JsonObject productJsonObject() {
		return new JsonObject(code, getMessage());
	}

}
