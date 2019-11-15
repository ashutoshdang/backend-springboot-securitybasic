package com.asu.document;

import java.util.Calendar;
import java.util.Date;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;

@Document
@Data
@AllArgsConstructor
public class VerificationToken {

	private static final int EXPIRATION = 60 * 24;
	
	@Id
    private String id;
	
	private String token;
	
	@DBRef
    private User user;
	
	private Date expiryDate;
	
	private Date  calculateExpiryDate(int expiryTimeInMinutes)  {
		
		final Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
	}

	/**
	 * setting activation token expiration time as five minutes
	 * @param token2
	 * @param user2
	 */
	public VerificationToken(String token2, User user2) {
		this.token=token2;
		this.user=user2;
		this.expiryDate=calculateExpiryDate(5);
	}
}
