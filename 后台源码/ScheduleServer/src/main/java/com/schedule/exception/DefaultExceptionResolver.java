package com.schedule.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.schedule.beans.json.JsonObject;
import com.schedule.exception.bean.BaseException;

@ControllerAdvice
public class DefaultExceptionResolver {

	
	@ExceptionHandler(BaseException.class)
	@ResponseBody
	public ResponseEntity<JsonObject> expection(BaseException e) {
		ResponseEntity<JsonObject> entity = new ResponseEntity<JsonObject>(e.productJsonObject(),
				HttpStatus.OK);
		return entity;
	}
	
	@ExceptionHandler(Throwable.class)
	@ResponseBody
	public ResponseEntity<JsonObject> baseExpection(Throwable t) {
		BaseException e=new BaseException(t.getMessage());
		ResponseEntity<JsonObject> entity = new ResponseEntity<JsonObject>(e.productJsonObject(),
				HttpStatus.OK);
		return entity;
	}
	
}
