package logic.controller;

import java.security.InvalidParameterException;

public abstract class InputChecker {

	private static String emailPattern = "^([a-zA-Z0-9\\.\\_\\-]+)@([a-zA-Z0-9\\.\\_\\-]+)\\.([a-zA-Z0-9\\.\\_\\-]{2,5})$";
	private static String phonePattern = "^([\\+]*)([0-9\\ ]{10,15})$";
	private static String userIDPattern = "^([0-9]{1,10})$";
	
	private InputChecker() {
		//must remain empty
	}
	
	private static void catchNull(String input) {
		if(input == null) {
			throw new InvalidParameterException("Input must not be null");
		}
	}
	
	public static boolean checkEmail(String email) {
		InputChecker.catchNull(email);
		return email.matches(InputChecker.emailPattern);
	}
	
	public static boolean checkPhone(String phone) {
		InputChecker.catchNull(phone);
		return phone.matches(InputChecker.phonePattern);
	}
	
	public static boolean checkUserID(String userID) {
		InputChecker.catchNull(userID);
		return userID.matches(InputChecker.userIDPattern);
	}
}
