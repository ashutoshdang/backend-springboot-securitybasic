package com.asu.service;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.asu.document.Role;
import com.asu.document.User;
import com.asu.document.VerificationToken;
import com.asu.repository.RoleRepository;
import com.asu.repository.UserRepository;
import com.asu.repository.VerificationTokenRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private VerificationTokenRepository vTokenRepository;
	
	public static final String TOKEN_INVALID = "invalidToken";
    public static final String TOKEN_EXPIRED = "expired";
    public static final String TOKEN_VALID = "valid";
	
	@Override
	public User createUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setEnabled(false);
		Role userRole = roleRepository.findByRole("ADMIN");
		user.setRoles(new HashSet<>(Arrays.asList(userRole)));
		User user2=userRepository.save(user);
		return user2;
	}
	
	@Override
    public String validateVerificationToken(String token) {
        final VerificationToken verificationToken = vTokenRepository.findByToken(token);
        if (verificationToken == null) {
            return TOKEN_INVALID;
        }

        final User user = verificationToken.getUser();
        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
        	vTokenRepository.delete(verificationToken);
            return TOKEN_EXPIRED;
        }

        user.setEnabled(true);
        // tokenRepository.delete(verificationToken);
        userRepository.save(user);
        return TOKEN_VALID;
    }
	
	@Override
    public void createVerificationTokenForUser(final User user, final String token) {
        final VerificationToken myToken = new VerificationToken(token, user);
        vTokenRepository.save(myToken);
    }
	@Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return vTokenRepository.findByToken(VerificationToken);
    }

	 @Override
	    public User getUser(final String verificationToken) {
	        final VerificationToken token = vTokenRepository.findByToken(verificationToken);
	        if (token != null) {
	            return token.getUser();
	        }
	        return null;
	    }

}
