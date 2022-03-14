package vendymachine.database.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import vendymachine.database.entity.Level;

import java.util.List;

public interface LevelDAO extends JpaRepository<Level, Long> {
	
	Level findByLid(@Param("lid") Long lid);
	
	Level findByChannelAndViewer(@Param("channel") Integer channel, @Param("viewer") Integer viewer);
	
	// Level leaderbaord for a channel
	List<Level> findByChannelOrderByLevelDescExperienceDesc(@Param("channel") Integer channel);
	
	// List of viewer's levels by channel
	List<Level> findByViewerOrderByChannel(@Param("viewer") Integer viewer);
}