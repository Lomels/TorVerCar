package logic.model;

public class Singleton {

	private Student user = null;
	private String code;
	
	private static Singleton instance = null;
	
	private Singleton() {}
	
	public static Singleton getInstance() {
		if(instance == null) 
			instance = new Singleton();
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
