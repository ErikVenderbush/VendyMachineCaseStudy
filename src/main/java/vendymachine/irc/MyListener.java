package vendymachine.irc;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.cap.EnableCapHandler;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.types.GenericMessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import vendymachine.config.APIGlobals;
import vendymachine.database.dao.CommandDAO;
import vendymachine.database.dao.UserDAO;
import vendymachine.database.entity.Command;
import vendymachine.database.entity.User;

import javax.annotation.PostConstruct;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
// @Component
public class MyListener extends ListenerAdapter {
	
	@Autowired
	private APIGlobals globals;
	
	@Override
	public void onGenericMessage(GenericMessageEvent event) {
		if (event.getMessage().startsWith("?helloworld"))
			event.respond("Hello world!");
	}
	
	// @EventListener
	public void initializeBot(ApplicationReadyEvent event) throws Exception {
		String ircPassword = "oauth:";
		log.info("Authenticating VendyMachine...");
		try {
			ircPassword += clientCredentials();
			log.info("VendyMachine authenticated successfully");
		} catch (Exception e) {
			log.error("Unable to authenticate");
			e.printStackTrace();
		}
		
		Configuration configuration = new Configuration.Builder()
				.setAutoNickChange(false) //Twitch doesn't support multiple users
				.setOnJoinWhoEnabled(false) //Twitch doesn't support WHO command
				.setCapEnabled(true)
				.addCapHandler(new EnableCapHandler("twitch.tv/membership")) //Twitch by default doesn't send JOIN, PART, and NAMES unless you request it, see https://dev.twitch.tv/docs/irc/guide/#twitch-irc-capabilities
				
				.addServer("irc.twitch.tv", 6697)
				.setName("vendymachine") //Your twitch.tv username
				.setServerPassword(ircPassword) //Your oauth password from http://twitchapps.com/tmi
				.addAutoJoinChannel("#vendy13") //Some twitch channel
				
				.addListener(new MyListener()) //Add our listener that will be called on Events
				.buildConfiguration();
		
		//Create our bot with the configuration
		PircBotX bot = new PircBotX(configuration);
		//Connect to the server
		bot.startBot();
	}
	
	private String clientCredentials() throws Exception {
		URL url = new URL(globals.getTwitchClientCredentialsRequest());
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
	
	// TODO get user by channel, command list by user, get user by sender, logic check method,
	// public void onMessage(String channel, String login, String hostname, String message) {
	// 	User user = userDAO.findByTwitchUsername(channel);
	//
	// 	List<Command> commandList = commandDAO.findByUserOrderByCommand(user.getUid());
	//
	// 	for (Command c : commandList) {
	// 		if (message.equalsIgnoreCase(c.getCommand())){
	// 			sendMessage(channel, c.getResponse());
	// 		}
	// 	}
	//
	// }
	
	// public boolean checkPermissions() {
	// 	if () {
	// 		if () {
	// 			if () {
	//
	// 			}
	// 		}
	// 	}
	//
	// 	return true;
	// }
}