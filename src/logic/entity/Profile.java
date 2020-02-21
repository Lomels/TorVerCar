package logic.entity;

import logic.controller.InputChecker;
import logic.controller.exception.InvalidInputException;

public class Profile {
	private String email;
	private String phoneNumber;
	private String profilePic;

	public String getEmail() {
		return email;
	}

	//TODO: Redundancy?
	public void setEmail(String email) throws InvalidInputException{
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

	//TODO: Redundancy?
	public void setPhoneNumber(String phoneNumber) throws InvalidInputException{
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
