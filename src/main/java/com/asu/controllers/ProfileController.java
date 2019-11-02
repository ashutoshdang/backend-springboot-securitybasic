package com.asu.controllers;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.asu.document.ProfilePhoto;
import com.asu.document.User;
import com.asu.service.ProfileService;

@RestController
public class ProfileController {
	
	@Autowired
	private ProfileService profileService;

	@PostMapping("/photos/add")
	public ResponseEntity<String> addPhoto(
			@RequestParam("file") MultipartFile image, Model model,HttpServletRequest request) throws IOException {
		
		System.out.println("we got a call to upload image");
		User user =   (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String fotoTitle="profile"+user.getUsername();
		System.out.println(">>>>>>>>>>>>>"+fotoTitle);
	    String id = profileService.addProfilePhoto(fotoTitle, image);
	    return new ResponseEntity<String>("Successfully uploaded profile photo",HttpStatus.OK);
	}
	
	@GetMapping("/photos/{id}")
	public String getPhoto(@PathVariable String id, Model model) {
		User user =  (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ProfilePhoto photo = profileService.getProfilePhoto(id+user.getUsername());
	    model.addAttribute("title", photo.getTitle());
	    model.addAttribute("image", 
	      Base64.getEncoder().encodeToString(photo.getImage().getData()));
	    return "photos";
	}
}
