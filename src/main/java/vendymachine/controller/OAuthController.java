package vendymachine.controller;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vendymachine.config.APIGlobals;
import vendymachine.config.Mapping;
import vendymachine.database.dao.UserDAO;
import vendymachine.database.dao.UserRoleDAO;
import vendymachine.database.entity.User;
import vendymachine.database.entity.UserRole;
import vendymachine.security.RoleList;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Collectors;


// TODO maybe turn into @RestController; then redirect
// Handles all of the user OAuth stuff from Twitch
@Controller
@RequestMapping(value = Mapping.OAUTH)
public class OAuthController {
	
	@Autowired
	private APIGlobals globals;
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private UserRoleDAO userRoleDAO;
	
	// The URL is really long so this looks cleaner on the front end; also helps when changing the redirect URI
	@GetMapping(value = "")
	private ModelAndView authorizationCode() throws Exception {
		return new ModelAndView("redirect:" + globals.getTwitchAuthorizationCodeRequest());
	}
	
	// TODO handle denial of authorization
	// Exchanges authorization code for access token, then access token for Twitch user data
	// Persists user if new or updates user if needed; sets session user and redirects to login/register
	@GetMapping(value = "/code")
	private ModelAndView authorize(@RequestParam String code, HttpSession session) throws Exception {
		ModelAndView response = new ModelAndView();
		
		// TODO will require storing user access tokens for certain endpoints (e.g. sub count)
		// TODO more scopes for certain endpoints
		String token = accessToken(code);;
		
		// Makes request to Twitch
		URL url = new URL(globals.getTwitchBaseEndpoint() + "/users?oauth_token=" + token);
		HttpsURLConnection https = (HttpsURLConnection) url.openConnection();
		https.setRequestMethod("GET");
		https.setDoOutput(true);
		https.setRequestProperty("Authorization", "Bearer " + token);
		https.setRequestProperty("Client-Id", globals.getClientId());
		
		// Response parsed a bunch then user data retrieved from Twitch
		InputStream instr = https.getInputStream();
		String input = new BufferedReader(new InputStreamReader(instr)).lines().collect(Collectors.joining("\n"));
		JSONObject jsonobj = new JSONObject(input).getJSONArray("data").getJSONObject(0);
		Long responseID = jsonobj.getLong("id");
		String responseLogin = jsonobj.getString("login");
		String responseDisplay = jsonobj.getString("display_name");
		String responsePFP = jsonobj.getString("profile_image_url");
		
		https.disconnect();
		
		User user;
		
		// Checks DB for user
		if (userDAO.findByTwitchID(responseID) == null) {
			user = new User();
			user.setTwitchID(responseID);
			user.setTwitchUsername(responseLogin);
			user.setTwitchDisplayName(responseDisplay);
			user.setTwitchPFP(responsePFP);
			
			userDAO.save(user);
			
			UserRole userRole = new UserRole();
			userRole.setUser(user.getUid());
			userRole.setRole(RoleList.USER.name());
			
			userRoleDAO.save(userRole);
			
			response.setViewName("redirect:" + Mapping.REGISTER);
		} else {
			user = userDAO.findByTwitchID(responseID);
			
			checkDB(user, responseLogin, responseDisplay, responsePFP);
			
			// Register if no password set; otherwise login
			if (user.getPassword() == null) {
				response.setViewName("redirect:" + Mapping.REGISTER);
			} else {
				response.setViewName("redirect:" + Mapping.LOGIN);
			}
		}
		
		session.setAttribute("user", responseID);
		
		return response;
	}
	
	// Exchanges authorization code for access token; access token is not persisted
	private String accessToken(String code) throws Exception {
		URL url = new URL(globals.getTwitchAccessTokenRequest() + code);
		HttpsURLConnection https = (HttpsURLConnection) url.openConnection();
		https.setRequestMethod("POST");
		https.setDoOutput(true);
		
		// Response parsed a bunch then access token instantiated
		InputStream instr = https.getInputStream();
		String input = new BufferedReader(new InputStreamReader(instr)).lines().collect(Collectors.joining("\n"));
		JSONObject jsonobj = new JSONObject(input);
		String responseToken = jsonobj.getString("access_token");
		
		https.disconnect();
		
		return responseToken;
	}
	
	// Ensures database is synced with Twitch on connection
	private void checkDB(User user, String responseLogin, String responseDisplay, String responsePFP) {
		boolean comparisonTest = false;
		
		if (!StringUtils.equals(user.getTwitchUsername(), responseLogin)) {
			user.setTwitchUsername(responseLogin);
			comparisonTest = true;
		}
		
		if (!StringUtils.equals(user.getTwitchDisplayName(), responseDisplay)) {
			user.setTwitchDisplayName(responseDisplay);
			comparisonTest = true;
		}
		
		if (!StringUtils.equals(user.getTwitchPFP(), responsePFP)) {
			user.setTwitchPFP(responsePFP);
			comparisonTest = true;
		}
		
		if (comparisonTest) {
			userDAO.save(user);
		}
		
		// Double checks user has USER role
		if (userRoleDAO.findUserRoleByUserAndRole(user.getUid(), RoleList.USER.name()) == null) {
			UserRole userRole = new UserRole();
			userRole.setUser(user.getUid());
			userRole.setRole(RoleList.USER.name());
			
			userRoleDAO.save(userRole);
		}
	}
}
