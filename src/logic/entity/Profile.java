package logic.entity;

import logic.controller.InputChecker;

public class Profile {
	private String email;
	private String phoneNumber;
	private String profilePic;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		try {
			InputChecker.checkEmail(email);
			this.email = email;	
		} catch (Exception e) {
			throw e;
		}

	}
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		try {
			InputChecker.checkPhone(phoneNumber);
			this.phoneNumber = phoneNumber;		
		} catch (Exception e) {
			throw e;
		}
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

}
