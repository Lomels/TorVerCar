package Entity;

public class Profile {
	public String email;
	public String phoneNumber;
	public String profilePic;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		if(email.length() < 5) {
			return;
		}
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		if(phoneNumber.length() != 10) {
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
