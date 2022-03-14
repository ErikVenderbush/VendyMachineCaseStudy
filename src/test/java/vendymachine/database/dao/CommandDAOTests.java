package vendymachine.database.dao;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import vendymachine.database.entity.Command;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommandDAOTests {
	
	@Autowired
	public CommandDAO commandDAO;
	
	@Test
	@Order(1)
	@Rollback(value = false)
	public void testCreateCommand() {
		Command command = new Command();
		
		command.setCommand("test command");
		command.setResponse("test response");
		command.setEnabled(true);
		command.setPermission("test");
		command.setCooldown(0);
		command.setUser(1);
		
		commandDAO.save(command);
		
		Assertions.assertEquals("test command", command.getCommand());
	}
	
	@Test
	@Order(2)
	public void testReadCommand() {
		Command command = commandDAO.findByCommand("test command");
		
		Assertions.assertEquals("test command", command.getCommand());
	}
	
	@Test
	@Order(3)
	@Rollback(value = false)
	public void testUpdateCommand() {
		Command command = commandDAO.findByCommand("test command");
		
		command.setCommand("another test command");
		
		Assertions.assertEquals("another test command", command.getCommand());
	}
	
	@Test
	@Order(4)
	@Rollback(value = false)
	public void testDeleteCommand() {
		Command command = commandDAO.findByCommand("another test command");
		
		commandDAO.delete(command);
		
		Assertions.assertNull(commandDAO.findByCommand("another test command"));
	}
}
