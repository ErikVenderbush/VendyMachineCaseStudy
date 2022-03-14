package vendymachine.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import vendymachine.config.APIGlobals;
import vendymachine.config.Mapping;
import vendymachine.database.dao.UserDAO;
import vendymachine.database.entity.Level;
import vendymachine.database.entity.User;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LevelsController {
	
	@Autowired
	private APIGlobals globals;
	
	@Autowired
	private UserDAO userDAO;
	
	private User user;
	
	@GetMapping(value = Mapping.LEVELS + "/{channel}")
	public ModelAndView levels(@CurrentSecurityContext(expression="authentication?.name") String username,
	                           @PathVariable String channel) throws Exception {
		ModelAndView response = checkLoginMVC(username);
		
		User channelUser = userDAO.findByTwitchUsername(channel.toLowerCase());
		
		List<Level> levels = userDAO.getLevelsByChannel(channelUser.getUid());
		response.addObject("levelListKey", levels);
		
		// TODO better way to do this?; How to link via the ManyToMany and print via JSTL?
		List<User> viewers = new ArrayList<>();

		for (Level level : levels) {
			User viewer = userDAO.findByUid(level.getViewer());
			viewers.add(viewer);
		}

		response.addObject("viewerListKey", viewers);
		
		response.setViewName("authenticated/levels");
		
		return response;
	}
	
	// Adds some user stuff when the user is logged in
	private ModelAndView checkLoginMVC(String username) throws Exception {
		ModelAndView response = new ModelAndView();
		
		if (!StringUtils.equals(username, "anonymousUser")) {
			user = userDAO.findByTwitchID(Long.valueOf(username));
			
			response.addObject("username", user.getTwitchDisplayName());
			response.addObject("twitchPFP", user.getTwitchPFP());
		}
		
		return response;
	}
}
