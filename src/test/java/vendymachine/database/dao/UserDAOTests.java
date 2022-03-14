package vendymachine.database.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import vendymachine.database.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
public class UserDAOTests {
	
	@Autowired
	public UserDAO userDAO;
	
	@Test
	@Order(1)
	@Rollback(value = false)
	public void testCreateUser() {
		User user = new User();
		
		user.setTwitchID(1L);
		user.setTwitchUsername("test");
		user.setTwitchDisplayName("Test");
		user.setTwitchPFP("https://static-cdn.jtvnw.net/user-default-pictures-uv/cdd517fe-def4-11e9-948e-784f43822e80-profile_image-70x70.png");
		
		userDAO.save(user);
		
		Assertions.assertEquals("test", user.getTwitchUsername());
	}
	
	@Test
	@Order(2)
	public void testReadUser() {
		User user = userDAO.findByTwitchID(1L);
		
		Assertions.assertEquals("test", user.getTwitchUsername());
	}
	
	@Test
	@Order(3)
	@Rollback(value = false)
	public void testUpdateUser() {
		User user = userDAO.findByTwitchID(1L);
		
		user.setTwitchUsername("newtest");
		
		Assertions.assertEquals("newtest", user.getTwitchUsername());
	}
	
	@Test
	@Order(4)
	@Rollback(value = false)
	public void testDeleteUser() {
		User user = userDAO.findByTwitchID(1L);
		
		userDAO.delete(user);
		
		Assertions.assertNull(userDAO.findByTwitchID(1L));
	}
}
