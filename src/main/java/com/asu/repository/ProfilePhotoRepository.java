package com.asu.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.asu.document.ProfilePhoto;

public interface ProfilePhotoRepository extends MongoRepository<ProfilePhoto, String> {

}
