package logic.controller;

public abstract class InputChecker {

	private static String emailPattern = "^([a-zA-Z0-9\\.\\_\\-]+)@([a-zA-Z0-9\\.\\_\\-]+)\\.([a-zA-Z0-9\\.\\_\\-]{2,5})$";
	private static String phonePattern = "^([\\+]*)([0-9\\ ]{10,15})$";
	
	private InputChecker() {
		//must remain empty
	}
	
	public static boolean checkEmail(String email) {
		return email.matches(InputChecker.emailPattern);
	}
	
	public static boolean checkPhone(String phone) {
		return phone.matches(InputChecker.phonePattern);
	}
}
