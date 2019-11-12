package com.asu.service;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.asu.document.ProfilePhoto;
import com.asu.repository.ProfilePhotoRepository;

@Service
public class ProfileServiceImpl implements ProfileService	{

	@Autowired
    private ProfilePhotoRepository profilePhotoRepository;
 
    public String addProfilePhoto(String title, MultipartFile file) { 
    	
        ProfilePhoto ProfilePhoto = new ProfilePhoto(title); 
        try {
        ProfilePhoto.setImage(
          new Binary(BsonBinarySubType.BINARY, file.getBytes())); 
        ProfilePhoto = profilePhotoRepository.insert(ProfilePhoto); 
    	}catch(Exception e) {
    		System.out.println("error occured while uploading profile photo");
    	}
        return ProfilePhoto.getId(); 
    }
 
    public ProfilePhoto getProfilePhoto(String title) { 
        return profilePhotoRepository.findTop1ByTitle(title); 
    }
}
