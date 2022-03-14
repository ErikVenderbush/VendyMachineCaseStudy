package vendymachine.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import vendymachine.security.RoleList;
import vendymachine.security.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf()
				.disable()
			.authorizeRequests()
				.antMatchers("/","/pub/**", Mapping.INDEX, Mapping.GUIDE, Mapping.ABOUT, Mapping.REGISTER,
						"/registerSubmit", Mapping.OAUTH + "/**", Mapping.LOGIN, "/loginSubmit", "/feedback",
						"/sendFeedback")
					.permitAll()
				// TODO change commands & chat to streamer auth?
				.antMatchers(Mapping.CHAT, Mapping.COMMANDS, Mapping.DASHBOARD)
					.hasAuthority(RoleList.USER.name())
				.antMatchers(Mapping.LEVELS)
					.hasAuthority(RoleList.STREAMER.name())
				.anyRequest()
					.authenticated()
				.and()
			.formLogin()
				.loginPage(Mapping.INDEX)
				.loginProcessingUrl("/loginSubmit")
				.defaultSuccessUrl(Mapping.DASHBOARD,true)
				.failureUrl(Mapping.LOGIN)
				.and()
			.logout()
				// TODO maybe make it revoke Twitch authorization
				.invalidateHttpSession(true)
				.logoutUrl(Mapping.LOGOUT)
				.logoutSuccessUrl("/")
				.and()
			.rememberMe()
				.key("SR_KEY")
				.tokenValiditySeconds(60 * 60 * 24 * 3)
				.rememberMeParameter("remember-me")
				.and()
			.exceptionHandling()
				.accessDeniedPage("/error/404");
	}
	
	@Bean
	public DaoAuthenticationProvider getAuthenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(getPasswordEncoder());
		return authProvider;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
		auth.authenticationProvider(getAuthenticationProvider());
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean(name = "passwordEncoder")
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}