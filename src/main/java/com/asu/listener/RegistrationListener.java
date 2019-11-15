package com.asu.listener;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.asu.document.User;
import com.asu.model.OnRegistrationCompleteEvent;
import com.asu.service.UserService;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
	
	@Autowired
    private UserService uService;

    @Autowired
    private MessageSource messages;

    @Autowired(required=true)
    private JavaMailSender mailSender;

    @Autowired
    private Environment env;
    
    @Autowired
    private ApplicationContext applicationContext;


	@Override
	public void onApplicationEvent(OnRegistrationCompleteEvent event) {
		 this.confirmRegistration(event);
	}
    
	private void confirmRegistration(final OnRegistrationCompleteEvent event) {
        final User user = event.getUser();
        final String token = UUID.randomUUID().toString();
        uService.createVerificationTokenForUser(user, token);
        final SimpleMailMessage email = constructEmailMessage(event, user, token);
        mailSender.send(email);
    }

	
	private final SimpleMailMessage constructEmailMessage(final OnRegistrationCompleteEvent event, final User user, final String token) {
        final String recipientAddress = user.getEmail();
        final String subject = "Registration Confirmation";
        /**
         * additional code to get the host name and port
         */
        String finalUrl="";
        try {
        	//String domainName=request.getRequestURL().toString();
			String ip = InetAddress.getLocalHost().getHostName();
			String contextPath=env.getProperty("server.context-path");
			int port =Integer.parseInt(env.getProperty("server.port"));
			finalUrl="http://"+ip+":"+port+contextPath;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
        
        
        final String confirmationUrl = finalUrl + "/registrationConfirm?token=" + token;
        final String message = messages.getMessage("message.regSucc", null, event.getLocale());
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + " \r\n" + confirmationUrl);
        email.setFrom(env.getProperty("support.email"));
        return email;
    }

}
