package logic.model;

public class UserSingleton {

	private Student user = null;
	private String code;
	
	private static UserSingleton instance = null;
	
	private UserSingleton() {}
	
	public static UserSingleton getInstance() {
		if(instance == null) 
			instance = new UserSingleton();
		return instance;
	}
	
	public void setUser(Student user) {
		this.user = user;
	}
	
	public Student getUser() {
		return this.user;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
	
}
