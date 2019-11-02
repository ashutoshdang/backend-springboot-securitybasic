package com.asu.service;

import org.springframework.web.multipart.MultipartFile;

import com.asu.document.ProfilePhoto;

public interface ProfileService {

	public String addProfilePhoto(String title, MultipartFile file);
	public ProfilePhoto getProfilePhoto(String id);
}
