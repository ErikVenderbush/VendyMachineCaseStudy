package vendymachine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import vendymachine.config.APIGlobals;
import vendymachine.config.Mapping;
import vendymachine.database.dao.UserDAO;
import vendymachine.database.entity.User;
import vendymachine.form.RegistrationFormBean;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

// Handles anything to do with login & registration
@Controller
public class LoginController {
	
	@Autowired
	private APIGlobals globals;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	// Maps to Registration page
	@GetMapping(value = Mapping.REGISTER)
	public ModelAndView register(HttpSession session) throws Exception {
		ModelAndView response = new ModelAndView();
		
		if (session.getAttribute("user") != null) {
			Long sessionUser = Long.valueOf(session.getAttribute("user").toString());
			User user = userDAO.findByTwitchID(sessionUser);
			
			response.addObject("twitchID", user.getTwitchID());
			response.addObject("username", user.getTwitchDisplayName());
			response.addObject("twitchPFP", user.getTwitchPFP());
			
			response.setViewName("login/register");
		} else {
			response.setViewName("redirect:" + Mapping.INDEX);
		}
		
		return response;
	}
	
	// Finds user in DB & sets the password if the form passes validation
	@PostMapping(value = "/registerSubmit")
	public ModelAndView registerSubmit(@Valid RegistrationFormBean form, BindingResult errors)
			throws Exception {
		ModelAndView response = new ModelAndView();
		
		if (errors.hasErrors()) {
			for (FieldError error : errors.getFieldErrors()) {
				form.getErrorMessages().add(error.getDefaultMessage());
				System.out.println("error field = " + error.getField() + "; message = " + error.getDefaultMessage());
			}
			
			User user = userDAO.findByTwitchID(form.getUsername());
			
			response.addObject("twitchID", user.getTwitchID());
			response.addObject("username", user.getTwitchDisplayName());
			response.addObject("twitchPFP", user.getTwitchPFP());
			
			response.addObject("formBeanKey", form);
			response.setViewName("login/register");
		} else {
			User user = userDAO.findByTwitchID(form.getUsername());
			
			String encryptedPassword = passwordEncoder.encode(form.getPassword());
			user.setPassword(encryptedPassword);
			
			userDAO.save(user);
			
			response.setViewName("redirect:" + Mapping.LOGIN);
		}
		
		return response;
	}
	
	// Maps to Login page
	@GetMapping(value = Mapping.LOGIN)
	public ModelAndView login(HttpSession session) throws Exception {
		ModelAndView response = new ModelAndView();
		
		if (session.getAttribute("user") != null) {
			Long sessionUser = Long.valueOf(session.getAttribute("user").toString());
			User user = userDAO.findByTwitchID(sessionUser);
			
			response.addObject("twitchID", user.getTwitchID());
			response.addObject("username", user.getTwitchDisplayName());
			response.addObject("twitchPFP", user.getTwitchPFP());
			
			response.setViewName("login/login");
		} else {
			response.setViewName("redirect:" + Mapping.INDEX);
		}
		
		return response;
	}
}
