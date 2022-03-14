package vendymachine.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

// Pulls properties from application.properties for use in code
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "vm")
public class APIGlobals {
	
	// Twitch API Properties
	private String clientId;
	private String twitchAuthorizationCodeRequest;
	private String twitchAccessTokenRequest;
	private String twitchClientCredentialsRequest;
	private String twitchBaseEndpoint;
	private String testToken;
	private String oauthPassword;
	
	// Gmail Properties
	private String gmailAccount;
	private String gmailPassword;
}
