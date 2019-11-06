package com.asu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.asu.document.User;
import com.asu.repository.UserRepository;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;
	@Override
	public boolean findUser(String username, String password) {
		User user=userRepository.findByUsername(username);
		
		boolean isUserPresent=bcryptEncoder.matches(password,user.getPassword());
		return isUserPresent;
	}

}
