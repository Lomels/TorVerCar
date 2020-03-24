package logic.bean;

public class UserInfo {

	private String userID;
	private String name;
	private String surname;
	private String email;

	/*
	public UserInfo(String userID, String name, String surname, String email) {
		setUserID(userID);
		setName(name);
		setSurname(surname);
		setEmail(email);
	}
	*/
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

}
