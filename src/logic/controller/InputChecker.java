package logic.controller;

import java.security.InvalidParameterException;

public abstract class InputChecker {

	private static String emailPattern = "^([a-zA-Z0-9\\.\\_\\-]+)@([a-zA-Z0-9\\.\\_\\-]+)\\.([a-zA-Z0-9\\.\\_\\-]{2,5})$";
	private static String phonePattern = "^([\\+]*)([0-9\\ ]{10,15})$";
	//userID must be numeric long between 1 and 10
	private static String userIDPattern = "^([0-9]{1,10})$";
	//password must be at least 6 char long and contain at least a symbol
	private static String passwordPattern = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$";

	private InputChecker() {
		//must remain empty
	}

	public static boolean checkEmail(String email) throws InvalidParameterException{
		try {
			return email.matches(InputChecker.emailPattern);
		} catch (NullPointerException e) {
			throw new InvalidParameterException("Input must not be null"); 
		}
	}
	
	public static boolean checkPhone(String phone) throws InvalidParameterException{
		try {
			return phone.matches(InputChecker.phonePattern);
		} catch (NullPointerException e) {
			throw new InvalidParameterException("Input must not be null"); 
		}
	}
	
	public static boolean checkUserID(String userID) throws InvalidParameterException{
		try {
			return userID.matches(InputChecker.userIDPattern);
		} catch (NullPointerException e) {
			throw new InvalidParameterException("Input must not be null"); 
		}
	}
	
	public static boolean checkPassword(String password) throws InvalidParameterException{
		try {
			return password.matches(InputChecker.passwordPattern);
		} catch (NullPointerException e) {
			throw new InvalidParameterException("Input must not be null"); 
		}
	}
}
