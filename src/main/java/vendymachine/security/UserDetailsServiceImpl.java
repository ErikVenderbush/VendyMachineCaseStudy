package vendymachine.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import vendymachine.database.dao.UserDAO;
import vendymachine.database.entity.User;
import vendymachine.database.entity.UserRole;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// Spring Security implementation; mainly used for granted authorities functionality
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
	
	public static final Logger LOG = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
	
	@Autowired
	private UserDAO userDAO;
	
	// Passed through by Spring Security; Twitch ID was used as username
	@Override
	public UserDetails loadUserByUsername(String twitchID) throws UsernameNotFoundException {
		User user = userDAO.findByTwitchID(Long.valueOf(twitchID));
		
		if (user == null) {
			throw new UsernameNotFoundException("User '" + twitchID + "' not found in database");
		}
		
		List<UserRole> userRoles = userDAO.getUserRoles(user.getUid());
		
		Collection<? extends GrantedAuthority> springRoles = buildGrantAuthorities(userRoles);
		
		return new org.springframework.security.core.userdetails.User(user.getTwitchID().toString(), user.getPassword(),
				springRoles);
	}
	
	private Collection<? extends GrantedAuthority> buildGrantAuthorities(List<UserRole> userRoles) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		for (UserRole role : userRoles) {
			authorities.add(new SimpleGrantedAuthority(role.getRole()));
		}
		
		return authorities;
	}
}