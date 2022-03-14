package vendymachine.database.dao;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import vendymachine.database.entity.Level;
import vendymachine.database.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LevelDAOTests {
	
	@Autowired
	public LevelDAO levelDAO;
	
	@Test
	@Order(1)
	@Rollback(value = false)
	public void testCreateLevel() {
		Level level = new Level();
		
		level.setChannel(1);
		level.setViewer(1);
		level.setLevel(7);
		level.setExperience(50);
		
		levelDAO.save(level);
		
		Assertions.assertEquals(7, level.getLevel());
	}
	
	@Test
	@Order(2)
	public void testReadLevel() {
		Level level = levelDAO.findByChannelAndViewer(1, 1);
		
		Assertions.assertEquals(50, level.getExperience());
	}
	
	@Test
	@Order(3)
	@Rollback(value = false)
	public void testUpdateLevel() {
		Level level = levelDAO.findByChannelAndViewer(1, 1);
		
		level.setLevel(13);
		
		Assertions.assertEquals(13, level.getLevel());
	}
	
	@Test
	@Order(4)
	@Rollback(value = false)
	public void testDeleteLevel() {
		Level level = levelDAO.findByChannelAndViewer(1, 1);
		
		levelDAO.delete(level);
		
		Assertions.assertNull(levelDAO.findByChannelAndViewer(1, 1));
	}
}
