package com.asu.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.asu.document.Role;

public interface RoleRepository extends MongoRepository<Role, String> {
	
	Role findByRole(String role);

}
