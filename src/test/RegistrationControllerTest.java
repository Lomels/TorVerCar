package test;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

import logic.controller.RegistrationController;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;

public class RegistrationControllerTest {

	//TODO: Test, redo without dummy
//	@Test
//	public void validInput() {
//		try {
//			RegistrationController rc = new RegistrationController();
//			rc.createStudent("12345", "marioRossi11@");	
//		} catch (Exception e) {
//			Logger.getGlobal().log(Level.FINE, e.getMessage());
//		} 
//		assertEquals(1, DummyOurStudentDatabase.getCounter());
//	}

	//TODO: Test, redo without dummy
	@Test
	public void invalidInput() {
		RegistrationController rc = new RegistrationController();
		assertThrows(InvalidInputException.class, () -> rc.createStudent("aaa", "aaa"));
	}
	
	//TODO: Test, redo without dummy
	@Test
	public void alreadyExistingUser() {
		RegistrationController rc = new RegistrationController();
		assertThrows(DatabaseException.class, () -> rc.createStudent("00000", "aaA123@"));
	}
	
	//TODO: Test, redo without dummy
	@Test
	public void previouslyBannedUser() {
		RegistrationController rc = new RegistrationController();
		assertThrows(DatabaseException.class, () -> rc.createStudent("99999", "aaA123@"));
	}

}
