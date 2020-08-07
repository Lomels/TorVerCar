package logic.model;

public class UserSingleton {

	private Student student = null;
	private StudentCar studentCar = null;
	private Role role;

	private String code;
	
	private static UserSingleton instance = null;
	
	private UserSingleton() {}
	
	public static UserSingleton getInstance() {
		if(instance == null) 
			instance = new UserSingleton();
		return instance;
	}
	
	public void setStudent(Student user) {
		this.student = user;
	}
	
	public Student getStudent() {
		return this.student;
	}
	
	public void setStudentCar(StudentCar user) {
		this.studentCar = user;
	}
	
	public StudentCar getStudentCar() {
		return this.studentCar;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return this.code;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
}
