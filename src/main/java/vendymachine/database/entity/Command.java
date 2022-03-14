package vendymachine.database.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "commands")
public class Command {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cid")
	private Long cid;
	
	@Column(name = "command", length = 255, nullable = false)
	private String command;
	
	@Column(name = "response", length = 500, nullable = false)
	private String response;
	
	@Column(name = "enabled", nullable = false)
	private Boolean enabled;
	
	@Column(name = "permission", nullable = false)
	private String permission;
	
	@Column(name = "cooldown", nullable = false)
	private Integer cooldown;
	
	@Column(name = "user", nullable = false)
	private Integer user;
}