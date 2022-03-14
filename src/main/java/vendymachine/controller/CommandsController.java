package vendymachine.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vendymachine.config.APIGlobals;
import vendymachine.config.Mapping;
import vendymachine.database.dao.CommandDAO;
import vendymachine.database.dao.UserDAO;
import vendymachine.database.entity.Command;
import vendymachine.database.entity.User;
import vendymachine.form.CommandFormBean;
import vendymachine.security.RoleList;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

// The primary functionality of the site; CRUD operations for call & response for the IRC bot
@Slf4j
@Controller
public class CommandsController {
	
	@Autowired
	private APIGlobals globals;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private CommandDAO commandDAO;
	
	private User user;
	
	@GetMapping(value = vendymachine.config.Mapping.COMMANDS)
	public ModelAndView commands(@CurrentSecurityContext(expression="authentication?.name") String username)
			throws Exception {
		ModelAndView response = checkLoginMVC(username);
			
		List<Command> commands = commandDAO.findByUserOrderByCommand(user.getUid());
		response.addObject("commandListKey", commands);
		response.setViewName("authenticated/commands");
		
		return response;
	}
	
	@PostMapping(value = "/commandAdd")
	public ModelAndView commandAdd(@Valid CommandFormBean form, BindingResult errors,
            @CurrentSecurityContext(expression="authentication?.name") String username) throws Exception {
		ModelAndView response = checkLoginMVC(username);
		
		if (errors.hasErrors()) {
			errors.getFieldErrors().forEach(error -> {
				form.getErrorMessages().add(error.getDefaultMessage());
				System.out.println("error field = " + error.getField() + "; message = " + error.getDefaultMessage());
			});
			
			response.addObject("formBeanKey", form);
			
			List<Command> commands = commandDAO.findByUserOrderByCommand(user.getUid());
			response.addObject("commandListKey", commands);
			response.setViewName("authenticated/commands");
		} else {
			Command command = new Command();
			
			command.setCommand(form.getCommand());
			command.setResponse(form.getResponse());
			command.setPermission(form.getPermission());
			command.setCooldown(form.getCooldown());
			command.setEnabled(form.getEnabled());
			command.setUser(user.getUid());
			
			commandDAO.save(command);
			
			response.setViewName("redirect:" + Mapping.COMMANDS);
			
			log.info("Command added successfully!");
		}
		
		return response;
	}
	
	@PostMapping(value = "/commandEdit")
	public ModelAndView commandEdit(@Valid CommandFormBean form, BindingResult errors,
            @CurrentSecurityContext(expression="authentication?.name") String username) throws Exception {
		ModelAndView response = checkLoginMVC(username);
		
		if (errors.hasErrors()) {
			for (FieldError error : errors.getFieldErrors()) {
				form.getErrorMessages().add(error.getDefaultMessage());
				System.out.println("error field = " + error.getField() + "; message = " + error.getDefaultMessage());
			}
			
			response.addObject("formBeanKey", form);
			
			List<Command> commands = commandDAO.findByUserOrderByCommand(user.getUid());
			response.addObject("commandListKey", commands);
			response.setViewName("authenticated/commands");
		} else {
			Command command = commandDAO.findByCid(form.getCid());
			
			command.setCommand(form.getCommand());
			command.setResponse(form.getResponse());
			command.setPermission(form.getPermission());
			command.setCooldown(form.getCooldown());
			command.setEnabled(form.getEnabled());
			
			commandDAO.save(command);
			
			response.setViewName("redirect:" + Mapping.COMMANDS);
		}
		
		return response;
	}
	
	@GetMapping(value = "/commandDelete")
	public ModelAndView commandDelete(@RequestParam Long cid) throws Exception {
		ModelAndView response = new ModelAndView();
		commandDAO.deleteById(cid);
		
		response.setViewName("redirect:" + Mapping.COMMANDS);
		
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