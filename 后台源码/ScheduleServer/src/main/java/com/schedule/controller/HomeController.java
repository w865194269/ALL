package com.schedule.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.schedule.beans.User;
import com.schedule.beans.json.JsonObject;
import com.schedule.service.UserService;


@Controller
public class HomeController {

	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@RequestMapping(value = "/")
	public String toIndex() {
		return "login";
	}
	@RequestMapping(value = "/login")
	public String login(String username,String password){
		  System.out.println(username+" "+password);
		  return "index";
	}
	@RequestMapping(value = "/test1")
	public String atoIndex() {
		return "test1";
	}
	@RequestMapping(value = "/home")
	public String btoIndex() {
		return "home";
	}
	@RequestMapping(value = "/test2")
	public String ctoIndex() {
		return "test2";
	}
	@RequestMapping(value = "/test3")
	public String dtoIndex() {
		return "test3";
	}
	@RequestMapping(value = "/test", 
			method = RequestMethod.POST, 
			consumes = "application/json", 
			produces = "application/json;charset=UTF-8"
	)
	@ResponseBody
	public JsonObject test(@RequestBody User user) throws Exception {
		user = userService.addUser(user);
		return new JsonObject(user);
	}
}
