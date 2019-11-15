package com.asu.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.asu.document.User;
import com.asu.document.VerificationToken;

public interface VerificationTokenRepository extends MongoRepository<VerificationToken, String> {
	
	 VerificationToken findByToken(String token);
	 
	 VerificationToken findByUser(User user);

}
