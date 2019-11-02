package com.asu.controllers;

import java.security.Principal;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.asu.document.User;
import com.asu.service.LoginService;
import com.asu.service.UserService;

@RestController
public class UserController {

	@Autowired
	private LoginService loginService;
	@Autowired 
	private UserService userService;
	
	@GetMapping("/login")
    public boolean login(HttpServletRequest request) {
		String authToken= request.getHeader("Authorization").substring(("Basic").length()).trim();
		if(authToken!=null) {
		String username=new String(Base64.getDecoder().decode(authToken)).split(":")[0];
		String password=new String(Base64.getDecoder().decode(authToken)).split(":")[1];
		
		if(loginService.findUser(username,password)) {
         return true;
		}else {
		return false;
		} 
		}
		return false;
    }
	
	@GetMapping("/user")
	public Principal user(HttpServletRequest request) {
		String authToken= request.getHeader("Authorization").substring(("Basic").length()).trim();
		
		return ()-> new String(Base64.getDecoder().decode(authToken)).split(":")[0];
		
	}
	@PostMapping("/createUser")
	public ResponseEntity<String> create(@RequestBody User user){
		userService.createUser(user);
		return new ResponseEntity<String>("New User "+user.getUsername()+"Created",HttpStatus.OK);
	}

}
