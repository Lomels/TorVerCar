package logic.utilities;

import logic.controller.exception.InvalidInputException;

public class InputChecker {

	// userID must be numeric long between 1 and 10
	// password must be at least 6 char long and contain at least a symbol
	// plate like AA123BB
	// generic String must not be empty

	private static final String ERRORFORMAT_STRING = "Given %s: %s not match %s";

	private InputChecker() {
		// must remain empty
	}

	private static void checkPattern(String stringToCheck, CheckPatterns pattern) throws InvalidInputException {
		String name = pattern.name();
		InputChecker.checkNotNull(stringToCheck, name);
		if (!stringToCheck.matches(pattern.getPattern())) {
			String message = String.format(ERRORFORMAT_STRING, name, stringToCheck, pattern.getPattern());
			throw new InvalidInputException(message);
		}
	}

	public static void checkEmail(String email) throws InvalidInputException {
		InputChecker.checkPattern(email, CheckPatterns.EMAIL);
	}

	public static void checkPhone(String phone) throws InvalidInputException {
		InputChecker.checkPattern(phone, CheckPatterns.PHONE);
	}

	public static void checkUserID(String userID) throws InvalidInputException {
		InputChecker.checkPattern(userID, CheckPatterns.USERID);

	}

	public static void checkPassword(String password) throws InvalidInputException {
		InputChecker.checkPattern(password, CheckPatterns.PASSWORD);
	}

	public static void checkPlate(String plate) throws InvalidInputException {
		InputChecker.checkPattern(plate, CheckPatterns.PLATE);
	}

	public static void checkGenericString(String generic) throws InvalidInputException {
		InputChecker.checkPattern(generic, CheckPatterns.GENERIC_STRING);
	}

	public static void checkNotNull(Object object, String name) {
		if (object == null)
			throw new NullPointerException(String.format("%s must not be null.", name));
	}

	public static void checkIntegerMoreThan(String name, Integer value, Integer lowerBound)
			throws InvalidInputException {
		InputChecker.checkNotNull(value, name);
		InputChecker.checkNotNull(lowerBound, "Inserted lowerBound for " + name + ".");
		if (value <= lowerBound)
			throw new InvalidInputException(String.format("%s: %d must be more than %d.", name, value, lowerBound));
	}

}
