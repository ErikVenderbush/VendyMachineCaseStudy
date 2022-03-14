package vendymachine.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import vendymachine.config.Mapping;
import vendymachine.database.dao.UserDAO;
import vendymachine.database.entity.User;

import javax.servlet.http.HttpSession;

// TODO live chat message mirror (not the embed)
// Mainly just displays an embedded chat; will soon retrieve chat messages via the IRC bot
@Controller
public class ChatController {
	
	@Autowired
	private UserDAO userDAO;
	
	@GetMapping(value = Mapping.CHAT)
	public ModelAndView chat(@CurrentSecurityContext(expression="authentication?.name") String username)
			throws Exception {
		ModelAndView response = checkLoginMVC(username);
		
		response.setViewName("authenticated/chat");
		
		return response;
	}
	
	// Adds some user stuff when the user is logged in
	private ModelAndView checkLoginMVC(String username) throws Exception {
		ModelAndView response = new ModelAndView();
		
		if (!StringUtils.equals(username, "anonymousUser")) {
			User user = userDAO.findByTwitchID(Long.valueOf(username));
			
			response.addObject("username", user.getTwitchDisplayName());
			response.addObject("twitchPFP", user.getTwitchPFP());
		}
		
		return response;
	}
}