package logic.entity;

import java.util.List;

public class Student extends User {

	protected String name;
	protected String surname;
	protected Profile profile;
	//Collection of favorite routes for the week
	protected WeeklyPreferencies weeklyPreferencies;
	//Collection of active lifts
	protected List<Lift> lifts;

	//Generated
	public Student(String userID, String password, String name, String surname, Profile profile,
			WeeklyPreferencies weeklyPreferencies, List<Lift> lifts) {
		super(userID, password);
		this.name = name;
		this.surname = surname;
		this.profile = profile;
		this.weeklyPreferencies = weeklyPreferencies;
		this.lifts = lifts;
	}

	//Costruttore che parte dello studentCar
	public Student(StudentCar studentCar) {
		this(studentCar.userID, studentCar.password, studentCar.name, studentCar.surname, studentCar.profile, studentCar.weeklyPreferencies, studentCar.lifts);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if(name.length()<1) {
			return;
		}
		this.name = name;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		if(surname.length()<1) {
			return;
		}
		this.surname = surname;
	}

	public Profile getProfile() {
		return this.profile;
	}

	public void setProfile(Profile profile) {
		if(profile == null) {
			return;
		}
		this.profile = profile;
	}

	public WeeklyPreferencies getWeeklyPreferencies() {
		return weeklyPreferencies;
	}

	public void setWeeklyPreferencies(WeeklyPreferencies weeklyPreferencies) {
		if(weeklyPreferencies == null) {
			return;
		}
		this.weeklyPreferencies = weeklyPreferencies;
	}

	public List<Lift> getLifts() {
		return this.lifts;
	}

	public void setLifts(List<Lift> lifts) {
		if(lifts == null) {
			return;
		}
		this.lifts = lifts;
	}

	//Generato automaticamente
	@Override
	public String toString() {
		return "Student [name=" + name + ", surname=" + surname + ", profile=" + profile + ", weeklyPreferencies="
				+ weeklyPreferencies + ", lifts=" + lifts + "]";
	}
	
	

}
