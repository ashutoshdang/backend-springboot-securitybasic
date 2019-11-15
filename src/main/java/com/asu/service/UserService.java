package com.asu.service;

import com.asu.document.User;
import com.asu.document.VerificationToken;

public interface UserService {

	User createUser(User user);
	
	User getUser(String verificationToken);
	
	void createVerificationTokenForUser(User user, String token);
	String validateVerificationToken(String token);
	VerificationToken getVerificationToken(String VerificationToken);
}
