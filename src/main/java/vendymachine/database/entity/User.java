package vendymachine.database.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "uid")
	private Integer uid;
	
	@Column(name = "twitch_id")
	private Long twitchID;
	
	@Column(name = "twitch_username")
	private String twitchUsername;
	
	@Column(name = "twitch_display_name")
	private String twitchDisplayName;
	
	@Column(name = "twitch_pfp")
	private String twitchPFP;
	
	@Column(name = "password")
	private String password;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "levels",
			joinColumns = {@JoinColumn(name = "channel")},
			inverseJoinColumns = {@JoinColumn(name = "viewer")})
	private Set<User> channels = new HashSet<User>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "levels",
			joinColumns = {@JoinColumn(name = "viewer")},
			inverseJoinColumns = {@JoinColumn(name = "channel")})
	private Set<User> viewers = new HashSet<User>();
}