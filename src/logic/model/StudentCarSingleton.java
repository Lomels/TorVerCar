package logic.model;

public class StudentCarSingleton {
	
	private StudentCar user = null;
	private String code;
	
	private static StudentCarSingleton instance = null;
	
	private StudentCarSingleton() {}
	
	public static StudentCarSingleton getInstance() {
		if(instance == null) 
			instance = new StudentCarSingleton();
		return instance;
	}
	
	public void setUser(StudentCar user) {
		this.user = user;
	}
	
	public StudentCar getUser() {
		return this.user;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}
	
}



