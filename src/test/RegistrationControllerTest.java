package test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.security.InvalidParameterException;
import org.junit.Test;


import logic.controller.RegistrationController;
import logic.controller.exception.DatabaseException;
import logic.view.dummy.DummyOurStudentDatabase;

public class RegistrationControllerTest {

	//TODO: redo without dummy
	@Test
	public void validInput() {
		try {
			RegistrationController rc = new RegistrationController();
			rc.createStudent("12345", "marioRossi11@");	
		} catch (Exception e) {

		} 
		assertEquals(1, DummyOurStudentDatabase.counter);
	}

	//TODO: redo without dummy
	@Test
	public void invalidInput() {
		RegistrationController rc = new RegistrationController();
		assertThrows(InvalidParameterException.class, () -> rc.createStudent("aaa", "aaa"));
	}
	
	//TODO: redo without dummy
	@Test
	public void alreadyExistingUser() {
		RegistrationController rc = new RegistrationController();
		assertThrows(DatabaseException.class, () -> rc.createStudent("00000", "aaA123@"));
	}

}
