package logic.utilities;

public enum CheckPatterns {

	EMAIL("^([a-zA-Z0-9\\.\\_\\-]+)@([a-zA-Z0-9\\.\\_\\-]+)\\.([a-zA-Z0-9\\.\\_\\-]{2,5})$"),
	PHONE("^([\\+]*)([0-9\\ ]{10,15})$"), USERID("^([0-9]{1,10})$"),
	PASSWORD("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$"),
	PLATE("^([a-zA-Z]{2})(\\s*)(\\d{3})(\\s*)([a-zA-Z]{2})$"), GENERIC_STRING("^(?!\\s*$).+$");

	private String pattern;

	private CheckPatterns(String pattern) {
		this.pattern = pattern;
	}

	public String getPattern() {
		return pattern;
	}

}
