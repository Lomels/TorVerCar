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

		InputChecker.checkEmail(email);
		this.email = email;	


	}
	public String getPhoneNumber() {
		return phoneNumber;
	}

	//TODO: Redundancy?
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
