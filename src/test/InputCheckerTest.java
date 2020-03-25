package test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


import org.junit.Test;
import logic.controller.exception.InvalidInputException;
import logic.utilities.InputChecker;

public class InputCheckerTest {

	//email checks
	@Test
	public void correctEmail() {
		String email = "g.marseglia.it@gmail.com";
		assertDoesNotThrow(() -> InputChecker.checkEmail(email));
	}

	@Test
	public void invalidEmail() {
		String email = "pippofranco";
		assertThrows(InvalidInputException.class, () -> InputChecker.checkEmail(email));
	}

	@Test
	public void emptyMail() {
		String email = "";
		assertThrows(InvalidInputException.class, () -> InputChecker.checkEmail(email));
	}

	@Test
	public void nullMail() {
		assertThrows(InvalidInputException.class, () -> InputChecker.checkEmail(null));
	}

	//phone checks
	@Test
	public void correctPhone() {
		String phone = "+39 3331231231";
		assertDoesNotThrow(() -> InputChecker.checkPhone(phone));
	}

	@Test
	public void correctPhone2() {
		String phone = "3311231236";
		assertDoesNotThrow(() -> InputChecker.checkPhone(phone));
	}

	@Test
	public void invalidPhone() {
		String phone = "4568";
		assertThrows(InvalidInputException.class, () -> InputChecker.checkPhone(phone));
	}

	@Test
	public void emptyPhone() {
		String phone = "";
		assertThrows(InvalidInputException.class, () -> InputChecker.checkPhone(phone));
	}

	@Test
	public void nullPhone() {
		assertThrows(InvalidInputException.class, () -> InputChecker.checkPhone(null));
	}
	
	//userID checks
	@Test
	public void correctUserID() {
		String userID = "15632";
		assertDoesNotThrow(() -> InputChecker.checkUserID(userID));
	}
	
	@Test
	public void invalidUserID() {
		String userID = "abcs";
		assertThrows(InvalidInputException.class, () -> InputChecker.checkUserID(userID));
	}
	
	@Test
	public void emptyUserID() {
		String userID = "";
		assertThrows(InvalidInputException.class, () -> InputChecker.checkUserID(userID));
	}
	
	@Test
	public void nullUserID() {
		assertThrows(InvalidInputException.class, () -> InputChecker.checkUserID(null));
	}
	
	//Password checks
	@Test
	public void correctPassword() {
		String password = "aaaaaa1@";
		assertDoesNotThrow(() -> InputChecker.checkPassword(password));
	}
	
	@Test
	public void invalidPassword() {
		String password = "Mariorossi";
		assertThrows(InvalidInputException.class, () -> InputChecker.checkPassword(password));
	}
	
	@Test
	public void emptyPassword() {
		String password = "";
		assertThrows(InvalidInputException.class, () -> InputChecker.checkPassword(password));
	}
	
	@Test
	public void nullPassword() {
		assertThrows(InvalidInputException.class, () -> InputChecker.checkPassword(null));

	}
	
	//Plate checks
	@Test
	public void correctPlate() {
		String plate = "Ab 123 cd";
		assertDoesNotThrow(() -> InputChecker.checkPlate(plate));
	}
	
	@Test
	public void invalidPlate() {
		String plate = "123da";
		assertThrows(InvalidInputException.class, () -> InputChecker.checkPlate(plate));
	}
	
	@Test
	public void emptyPlate() {
		String plate = "";
		assertThrows(InvalidInputException.class, () -> InputChecker.checkPlate(plate));
	}
	
	@Test
	public void nullPlate() {
		assertThrows(InvalidInputException.class, () -> InputChecker.checkPlate(null));
		
	}
	
	

}
