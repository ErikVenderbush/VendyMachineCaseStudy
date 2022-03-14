package vendymachine.database.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vendymachine.database.entity.Command;
import vendymachine.database.entity.Level;
import vendymachine.database.entity.User;
import vendymachine.database.entity.UserRole;

import java.util.List;

public interface UserDAO extends JpaRepository<User, Long> {
	
	User findByUid(@Param("uid") Integer uid);
	
	User findByTwitchID(@Param("twitch_id") Long twitchID);
	
	User findByTwitchUsername(@Param("twitch_username") String twitchUsername);
	
	// Get list of user's roles
	@Query("select ur from UserRole ur where ur.user = :uid")
	List<UserRole> getUserRoles(Integer uid);
	
	// Get list of user's commands
	@Query(value = "SELECT * FROM commands c WHERE c.user = :uid ORDER BY c.command", nativeQuery = true)
	List<Command> getCommands(Integer uid);
	
	// Get level leaderboard for user's channel
	@Query("SELECT l FROM Level l WHERE l.channel = :uid ORDER BY l.level, l.experience, l.viewer")
	List<Level> getLevelsByChannel(Integer uid);
	
	@Query("SELECT u FROM User u JOIN Level l WHERE l.channel = :uid ORDER BY l.level, l.experience, l.viewer")
	List<Level> getViewersByChannel(Integer uid);
	
	// Get list of user's levels per channel
	@Query("SELECT l FROM Level l WHERE l.viewer = :uid")
	List<Level> getLevelsByViewer(Integer uid);
}