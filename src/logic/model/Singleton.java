package logic.model;

public class Singleton {

	private User user = null;
	
	private static Singleton instance = null;
	
	private Singleton() {}
	
	public static Singleton getInstance() {
		if(instance == null) 
			instance = new Singleton();
		return instance;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public User getUser() {
		return this.user;
	}
	
}
