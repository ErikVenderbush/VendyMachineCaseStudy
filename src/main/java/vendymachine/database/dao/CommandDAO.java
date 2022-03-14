package vendymachine.database.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import vendymachine.database.entity.Command;

import java.util.List;

public interface CommandDAO extends JpaRepository<Command, Long> {
	
	Command findByCid(@Param("cid") Long cid);
	
	Command findByCommand(@Param("command") String command);
	
	List<Command> findByUserOrderByCommand(@Param("user") Integer user);
}