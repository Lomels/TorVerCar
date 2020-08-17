package logic.utilities;

import logic.controller.exception.InvalidInputException;

public class InputChecker {

	private static String emailPattern = "^([a-zA-Z0-9\\.\\_\\-]+)@([a-zA-Z0-9\\.\\_\\-]+)\\.([a-zA-Z0-9\\.\\_\\-]{2,5})$";
	private static String phonePattern = "^([\\+]*)([0-9\\ ]{10,15})$";
	//userID must be numeric long between 1 and 10
	private static String userIDPattern = "^([0-9]{1,10})$";
	//password must be at least 6 char long and contain at least a symbol
	private static String passwordPattern = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$";
	//plate like AA123BB
	private static String platePattern = "^([a-zA-Z]{2})(\\s*)(\\d{3})(\\s*)([a-zA-Z]{2})$";
	//generic String must not be empty
	private static String genericPattern = "^(?!\\s*$).+$";
	
	private InputChecker() {
		//must remain empty
	}

	public static void checkEmail(String email) throws InvalidInputException{
		try {
			if(!email.matches(InputChecker.emailPattern)) {
				throw new InvalidInputException("Given email does not match " + InputChecker.emailPattern);
			}
		} catch (NullPointerException e) {
			throw new InvalidInputException("Given email must not be null"); 
		}
	}
	
	public static void checkPhone(String phone) throws InvalidInputException{
		try {
			if(!phone.matches(InputChecker.phonePattern)) {
				throw new InvalidInputException("Given phone does not match " + InputChecker.phonePattern);
			}
		} catch (NullPointerException e) {
			throw new InvalidInputException("Given phone must not be null"); 
		}
	}
	
	public static void checkUserID(String userID) throws InvalidInputException{
		try {
			if(!userID.matches(InputChecker.userIDPattern)) {
				throw new InvalidInputException("Given userID does not match " + InputChecker.userIDPattern);
			}
		} catch (NullPointerException e) {
			throw new InvalidInputException("Given userID must not be null"); 
		}
	}
	
	public static void checkPassword(String password) throws InvalidInputException{
		try {
			if(!password.matches(InputChecker.passwordPattern)) {
				throw new InvalidInputException("Given password does not match " + InputChecker.passwordPattern);
			}
		} catch (NullPointerException e) {
			throw new InvalidInputException("Given password must not be null"); 
		}
	}
	
	public static void checkPlate(String plate) throws InvalidInputException{
		try {
			if(!plate.matches(InputChecker.platePattern)) {
				throw new InvalidInputException("Given plate does not match " + InputChecker.platePattern);
			}
		} catch (NullPointerException e) {
			throw new InvalidInputException("Given plate must not be null"); 
		}
	}
	
	public static void checkGeneric(String generic) throws InvalidInputException{
		try {
			if(!generic.matches(InputChecker.genericPattern)) {
				throw new InvalidInputException("Given generic string is empty");
			}
		} catch (NullPointerException e) {
			throw new InvalidInputException("Given generic must not be null"); 
		}
	}
	
	public static void checkNotNull(Object object, String name) throws InvalidInputException{
		if(object == null)
			throw new InvalidInputException(String.format("{%s} must not be null.", name));
	}
	
}
