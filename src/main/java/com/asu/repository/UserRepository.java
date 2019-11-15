package com.asu.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.asu.document.User;

public interface UserRepository extends MongoRepository<User,String> {

	//@Query(value = "{ $and: [{'username' :?0 },{'password':?1}]}")
	User findByUsernameAndPassword(String username, String password);

	User findByEmail(String email);

	User findByUsername(String username);

	User findByUsernameAndEnabledTrue(String username);

	
}
