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
import java.security.Principal;

// Handles the simple requests requiring minimal logic and no authentication
@Controller
public class IndexController {
	
	@Autowired
	private UserDAO userDAO;
	
	// Maps to Home/Index page
	@GetMapping(value = {"/", Mapping.INDEX})
	public ModelAndView index(@CurrentSecurityContext(expression="authentication?.name") String username)
			throws Exception {
		ModelAndView response = checkLoginMVC(username);
		response.setViewName("global/index");
		
		return response;
	}
	
	// Maps to Guide page
	@GetMapping(value = Mapping.GUIDE)
	public ModelAndView guide(@CurrentSecurityContext(expression="authentication?.name") String username)
			throws Exception {
		ModelAndView response = checkLoginMVC(username);
		response.setViewName("global/guide");
		
		return response;
	}
	
	// Maps to About page
	@GetMapping(value = Mapping.ABOUT)
	public ModelAndView about(@CurrentSecurityContext(expression="authentication?.name") String username)
			throws Exception {
		ModelAndView response = checkLoginMVC(username);
		response.setViewName("global/about");
		
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