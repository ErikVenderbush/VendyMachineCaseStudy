package vendymachine.controller;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import vendymachine.config.APIGlobals;
import vendymachine.config.Mapping;
import vendymachine.database.dao.UserDAO;
import vendymachine.database.entity.User;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// TODO bot connection button here until separate settings page; can add editors here too maybe
// Landing page once a user logs in; displays some channel stats from Twitch
@Controller
public class DashboardController {
	
	@Autowired
	private APIGlobals globals;
	
	@Autowired
	private UserDAO userDAO;
	
	private User user;
	
	//
	@GetMapping(value = Mapping.DASHBOARD)
	public ModelAndView dashboard(@CurrentSecurityContext(expression = "authentication?.name") String username)
			throws Exception {
		ModelAndView response = checkLoginMVC(username);
		
		ArrayList<Object> twitchInfo = getTwitchInfo();
		
		response.addObject("title", twitchInfo.get(0));
		response.addObject("category", twitchInfo.get(1));
		response.addObject("viewers", twitchInfo.get(2));
		response.addObject("followers", twitchInfo.get(3));
		response.addObject("subscribers", twitchInfo.get(4));
		response.addObject("views", twitchInfo.get(5));
		response.setViewName("authenticated/dashboard");
		
		return response;
	}
	
	// Calls methods to fill the dashboard with relevant info
	private ArrayList<Object> getTwitchInfo() throws Exception {
		ArrayList<Object> twitchInfo = new ArrayList<>();
		
		// TODO Stream Info when Live https://dev.twitch.tv/docs/api/reference#get-streams
		// Channel Info https://dev.twitch.tv/docs/api/reference#search-channels
		ArrayList<String> streamInfo = apiRequests("/channels", "broadcaster_id", "title",
				"game_name");
		twitchInfo.add(streamInfo.get(0));
		twitchInfo.add(streamInfo.get(1));
		
		// "Current Viewers"
		twitchInfo.add("A bunch");
		
		// Follower Count https://dev.twitch.tv/docs/api/reference#get-users-follows
		twitchInfo.add(apiRequest("/users/follows", "to_id", "total"));
		
		// TODO Sub Count (requires user OAuth & another scope); when streamer role is enacted
		// "Subscriber Count"
		// twitchInfo.add(apiRequest("subscriptions", "broadcaster_id", "total"));
		twitchInfo.add("Lots");
		
		// "Current Viewers"
		twitchInfo.add("Lots");
		
		
		return twitchInfo;
	}
	
	// One piece of info per request
	private Object apiRequest(String uri, String parameter, String response) throws Exception {
		URL url = new URL(globals.getTwitchBaseEndpoint() + uri + "?" + parameter + "=" + user.getTwitchID());
		HttpsURLConnection https = (HttpsURLConnection) url.openConnection();
		https.setRequestMethod("GET");
		https.setDoOutput(true);
		https.setRequestProperty("Authorization", "Bearer " + globals.getTestToken());
		https.setRequestProperty("Client-Id", globals.getClientId());
		
		InputStream instr = https.getInputStream();
		String input = new BufferedReader(new InputStreamReader(instr)).lines().collect(Collectors.joining("\n"));
		
		return new JSONObject(input).get(response);
	}
	
	// Multiple pieces of info per request
	private ArrayList<String> apiRequests(String uri, String parameter, String response1, String response2) throws Exception {
		URL url = new URL(globals.getTwitchBaseEndpoint() + uri + "?" + parameter + "=" + user.getTwitchID());
		HttpsURLConnection https = (HttpsURLConnection) url.openConnection();
		https.setRequestMethod("GET");
		https.setDoOutput(true);
		https.setRequestProperty("Authorization", "Bearer " + globals.getTestToken());
		https.setRequestProperty("Client-Id", globals.getClientId());
		
		InputStream instr = https.getInputStream();
		String input = new BufferedReader(new InputStreamReader(instr)).lines().collect(Collectors.joining("\n"));
		JSONObject jsonobj = new JSONObject(input).getJSONArray("data").getJSONObject(0);
		
		ArrayList<String> streamInfo = new ArrayList<>();
		streamInfo.add(jsonobj.getString(response1));
		streamInfo.add(jsonobj.getString(response2));
		
		return streamInfo;
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