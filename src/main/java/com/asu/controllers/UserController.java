package com.asu.controllers;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.asu.document.User;
import com.asu.model.OnRegistrationCompleteEvent;
import com.asu.service.LoginService;
import com.asu.service.UserService;

@RestController
public class UserController {

	@Autowired
	private LoginService loginService;
	@Autowired 
	private UserService userService;
	
	@Autowired
    private MessageSource messages;
	
	/**
	 * Using a Spring Event to Create the Token and Send the Verification Email
	 * The controller will publish a Spring ApplicationEvent to trigger the execution
	 * of these tasks. This is as simple as injecting the ApplicationEventPublisher 
	 * and then using it to publish the registration completion.
	 */
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
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
	public User user(HttpServletRequest request) {
		String authToken= request.getHeader("Authorization").substring(("Basic").length()).trim();
		if(authToken!=null) {
		//String authToken= request.getHeader("Authorization").substring(("Basic").length()).trim();
		System.out.println((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		return null;
		
	}
	@PostMapping("/createUser")
	public ResponseEntity<String> create(@RequestBody User user,WebRequest request){
		User registered=userService.createUser(user);
		String appUrl = request.getContextPath();
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), appUrl));
		return new ResponseEntity<String>("New User "+user.getUsername()+"Created",HttpStatus.OK);
	}
	
	@GetMapping(value = "/registrationConfirm")
    public ResponseEntity<String> confirmRegistration(final HttpServletRequest request, final Model model, @RequestParam("token") final String token) throws UnsupportedEncodingException {
   
		 Locale locale = request.getLocale();
		 
		 final String result = userService.validateVerificationToken(token);
		 if (result.equals("valid")) {
	            final User user = userService.getUser(token);
	            // if (user.isUsing2FA()) {
	            // model.addAttribute("qr", userService.generateQRUrl(user));
	            // return "redirect:/qrcode.html?lang=" + locale.getLanguage();
	            // }
	            
	            //check this out by commenting and uncommenting 
	           // authWithoutPassword(user);
	            model.addAttribute("message", messages.getMessage("message.accountVerified", null, locale));
	            //return "redirect:/console.html?lang=" + locale.getLanguage();
	            return new ResponseEntity<String> ("User Registration Confirmed",HttpStatus.OK);
	        }

	        model.addAttribute("message", messages.getMessage("auth.message." + result, null, locale));
	        model.addAttribute("expired", "expired".equals(result));
	        model.addAttribute("token", token);
	        return new ResponseEntity<String> ("User Registration Failed",HttpStatus.OK);
    }

}
