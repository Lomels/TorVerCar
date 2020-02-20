package logic.entity;

import java.security.InvalidParameterException;

import logic.controller.InputChecker;

public class Profile {
	private String email;
	private String phoneNumber;
	private String profilePic;
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		if(InputChecker.checkEmail(email)) {
			this.email = email;
		} else {
			throw new InvalidParameterException("Email does not respect the regular expression");
		}
		
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		if(InputChecker.checkPhone(phoneNumber)) {
			this.phoneNumber = phoneNumber;;
		} else {
			throw new InvalidParameterException("Phone number does not respect the regular expression");
		}
		
	}
	
	public String getProfilePic() {
		return profilePic;
	}
	
	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}
	
}
