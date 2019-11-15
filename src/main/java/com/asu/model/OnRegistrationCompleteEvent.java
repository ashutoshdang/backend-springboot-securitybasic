package com.asu.model;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;
import org.springframework.data.mongodb.core.mapping.DBRef;

import com.asu.document.User;

public class OnRegistrationCompleteEvent extends ApplicationEvent{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String appUrl;
    private Locale locale;
    
    @DBRef
    private User user;
    
    public OnRegistrationCompleteEvent(
    	      User user, Locale locale, String appUrl) {
    	        
    	       super(user);
    	        this.user = user;
    	        this.locale = locale;
    	        this.appUrl = appUrl;
    	    }
    
    public String getAppUrl() {
        return appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public User getUser() {
        return user;
    }
 
}
