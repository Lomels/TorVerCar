package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.security.InvalidParameterException;

import org.junit.Test;
import logic.controller.*;

public class InputCheckerTest {

	//email checks
	@Test
	public void correctEmail() {
		String email = "g.marseglia.it@gmail.com";
		assertTrue(InputChecker.checkEmail(email));
	}

	@Test
	public void correctEmail2() {
		String email = "mario.rossi123@libero.it";
		assertTrue(InputChecker.checkEmail(email));
	}

	@Test
	public void incorrectEmail() {
		String email = "pippofranco";
		assertFalse(InputChecker.checkEmail(email));
	}

	@Test
	public void emptyMail() {
		String email = "";
		assertFalse(InputChecker.checkEmail(email));
	}

	@Test
	public void nullMail() {
		assertThrows(InvalidParameterException.class, () -> 
		InputChecker.checkEmail(null)
				);
	}

	//phone checks
	@Test
	public void correctPhone() {
		String phone = "+39 3331231231";
		assertTrue(InputChecker.checkPhone(phone));
	}

	@Test
	public void correctPhone2() {
		String phone = "3311231236";
		assertTrue(InputChecker.checkPhone(phone));
	}

	@Test
	public void incorrectPhone() {
		String phone = "4568";
		assertFalse(InputChecker.checkPhone(phone));
	}

	@Test
	public void emptyPhone() {
		String phone = "";
		assertFalse(InputChecker.checkPhone(phone));
	}

	@Test
	public void nullPhone() {
		assertThrows(InvalidParameterException.class, () -> 
		InputChecker.checkPhone(null)
				);
	}
	
	//userID checks
	@Test
	public void correctUserID() {
		String UserID = "15632";
		assertTrue(InputChecker.checkUserID(UserID));
	}
	
	@Test
	public void incorrectUserID() {
		String UserID = "abcs";
		assertFalse(InputChecker.checkUserID(UserID));
	}
	
	@Test
	public void emptyUserID() {
		String UserID = "";
		assertFalse(InputChecker.checkUserID(UserID));
	}
	
	@Test
	public void nullUserID() {
		assertThrows(InvalidParameterException.class, () -> 
		InputChecker.checkUserID(null)
				);
	}
	
	//Password checks
	@Test
	public void correctPassword() {
		String Password = "marioRossi1224@";
		assertTrue(InputChecker.checkPassword(Password));
	}
	
	@Test
	public void incorrectPassword() {
		String Password = "Mariorossi";
		assertFalse(InputChecker.checkPassword(Password));
	}
	
	@Test
	public void emptyPassword() {
		String Password = "";
		assertFalse(InputChecker.checkPassword(Password));
	}
	
	@Test
	public void nullPassword() {
		assertThrows(InvalidParameterException.class, () -> 
		InputChecker.checkPassword(null)
				);
	}
	
	

}
