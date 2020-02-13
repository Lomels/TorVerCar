package logic.entity;

public class Profile {
	private String email;
	private String phoneNumber;
	private String profilePic;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		if(email.length() < 5) {
			return;
			// TODO Implementare meglio
		}
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		if(phoneNumber.length() != 10) {
			// TODO Implementare meglio
			return;
		}
		this.phoneNumber = phoneNumber;
	}
	public String getProfilePic() {
		return profilePic;
	}
	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}
	
}
