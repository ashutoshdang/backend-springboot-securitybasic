package com.asu.document;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
@Document
public class User implements UserDetails {

	
	@Id
	private String id;
	private String username;
	private String password;
	private String email;
	private boolean enabled;
	
	@DBRef
    private Set<Role> roles;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	public User(String username2, String password2, String email,boolean b, List<GrantedAuthority> authorities) {
		this.username=username2;
		this.password=password2;
		this.email=email;
		this.enabled=b;
	}
	
	public User(String string, String string2, boolean b, boolean c, boolean d, boolean e, List<GrantedAuthority> authorities) {
		super();
	}
	
}
