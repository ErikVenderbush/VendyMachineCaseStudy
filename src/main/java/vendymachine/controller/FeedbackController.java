package vendymachine.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import vendymachine.config.APIGlobals;
import vendymachine.database.dao.UserDAO;
import vendymachine.database.entity.User;
import vendymachine.form.FeedbackFormBean;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

// Handles the feedback sending funcitonality
@Controller
public class FeedbackController {
	
	@Autowired
	private APIGlobals globals;
	
	@Autowired
	private UserDAO userDAO;
	
	private User user;
	
	// Handles basic feedback page
	@GetMapping(value = "/feedback")
	public ModelAndView feedback(@CurrentSecurityContext(expression="authentication?.name") String username)
			throws Exception {
		ModelAndView response = checkLoginMVC(username);
		
		// response.addObject("twitchDisplayName", user.getTwitchDisplayName());
		response.setViewName("authenticated/email");
		
		return response;
	}
	
	// Handles action when feedback form is submitted
	@PostMapping(value = "/sendFeedback")
	public ModelAndView sendFeedback(FeedbackFormBean form,
             @CurrentSecurityContext(expression="authentication?.name") String username) throws Exception {
		ModelAndView response = checkLoginMVC(username);
		
		sendEmail(form);
		
		// response.addObject("twitchDisplayName", user.getTwitchDisplayName());
		response.addObject("feedbackSuccess", "Feedback sent successfully");
		response.setViewName("authenticated/email");
		
		return response;
	}
	
	// Creates and sends the feedback from form to the bot email account
	private void sendEmail(FeedbackFormBean form) throws Exception {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(globals.getGmailAccount(), globals.getGmailPassword());
			}
		});
		
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(globals.getGmailAccount(), false));
		
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(globals.getGmailAccount()));
		msg.setSubject(form.getCategory() + " - " + form.getSubject());
		msg.setContent(form.getCategory() + " - " + form.getSubject(), "text/html");
		msg.setSentDate(new Date());
		
		MimeBodyPart msgBody = new MimeBodyPart();
		msgBody.setContent(form.getCategory() + " from " + form.getUsername() + "<br><br>" + form.getBody(), "text" +
				"/html");
		
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(msgBody);
		
		msg.setContent(multipart);
		Transport.send(msg);
	}
	
	// Adds some user stuff when the user is logged in
	private ModelAndView checkLoginMVC(String username) throws Exception {
		ModelAndView response = new ModelAndView();
		
		if (!StringUtils.equals(username, "anonymousUser")) {
			user = userDAO.findByTwitchID(Long.valueOf(username));
			
			response.addObject("username", user.getTwitchDisplayName());
			response.addObject("twitchPFP", user.getTwitchPFP());
		} else {
			response.addObject("username", "anonymous");
		}
		
		return response;
	}
}