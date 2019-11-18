package com.asu.listener;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.asu.document.User;
import com.asu.model.OnRegistrationCompleteEvent;
import com.asu.service.UserService;
import com.asu.util.ThymeleafUtil;

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
		 try {
			this.confirmRegistration(event);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
    
	private void confirmRegistration(final OnRegistrationCompleteEvent event) throws MessagingException {
        final User user = event.getUser();
        final String token = UUID.randomUUID().toString();
        uService.createVerificationTokenForUser(user, token);
         constructEmailMessage(event, user, token);
        
    }

	
	private final void constructEmailMessage(final OnRegistrationCompleteEvent event, final User user, final String token) throws MessagingException {
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
        
       
        
        Map<String, Object> model = new HashMap<>();
		model.put("url", confirmationUrl);
		String html = ThymeleafUtil.getProcessedHtml(model, "ActivateAccountTemplate");
		
		/**
		 * we meed to create mime type supported email content/msg else it wont be 
		 * rendered as expected in the inbox instead all the html source will be send
		 *  and displayed as content in the email
		 */
		 MimeMessage msg = mailSender.createMimeMessage();
		 
	     // true = multipart message
	     MimeMessageHelper helper = new MimeMessageHelper(msg, true);
		
        final SimpleMailMessage email = new SimpleMailMessage();
        helper.setTo(recipientAddress);
        helper.setSubject(subject);
       
        helper.setText(html,true);
        helper.setFrom(env.getProperty("support.email"));
        mailSender.send(msg);
    }

}
