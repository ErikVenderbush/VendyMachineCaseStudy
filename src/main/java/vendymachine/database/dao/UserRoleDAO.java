package vendymachine.database.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import vendymachine.database.entity.UserRole;

public interface UserRoleDAO extends JpaRepository<UserRole, Long> {
	
	UserRole findUserRoleByUserAndRole(@Param("user") Integer user, @Param("role") String role);
}
