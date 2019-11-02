package com.asu.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asu.document.User;
import com.asu.repository.UserRepository;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public boolean findUser(String username, String password) {
		User user=userRepository.findByUsernameAndPassword(username,password);
		return user!=null;
	}

}
