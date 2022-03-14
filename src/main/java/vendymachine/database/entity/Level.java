package vendymachine.database.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "levels")
public class Level {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "lid")
	private Long lid;
	
	@Column(name = "channel")
	// @ManyToMany(mappedBy = "channels")
	private Integer channel;

	@Column(name = "viewer")
	private Integer viewer;
	
	@Column(name = "level")
	private Integer level;
	
	@Column(name = "experience")
	private Integer experience;
}