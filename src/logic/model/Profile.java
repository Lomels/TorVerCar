package logic.model;

import logic.controller.exception.InvalidInputException;
import logic.utilities.InputChecker;

public class Profile {
	private String email;
	private String phoneNumber;
	private String profilePic;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) throws InvalidInputException{
		InputChecker.checkEmail(email);
		this.email = email;	
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) throws InvalidInputException{
		InputChecker.checkPhone(phoneNumber);
		this.phoneNumber = phoneNumber;		

	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

}
