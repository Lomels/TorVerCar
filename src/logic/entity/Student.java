package entity;

import java.util.List;

public class Student extends User {

	protected String name;
	protected String surname;
	protected Profile profile;
	
	//Collection of favorite routes for the week
	protected WeeklyPreferencies weeklyPreferencies;
	
	//Collection of active lifts
	protected List<Lift> lifts;
	
	//Used in the RegistrationController, da mettere in una factory
	public Student(String userID, String password, String name, String surname, WeeklyPreferencies weeklyPreferecies, List<Lift> lifts) {
		super(userID, password);
		this.setName(name);
		this.setSurname(surname);
		this.setWeeklyPreferencies(weeklyPreferencies);
		this.setLifts(lifts);
	}
	
	//Generated
	protected Student(String userID, String password, String name, String surname, Profile profile) {
		super(userID, password);
		this.name = name;
		this.surname = surname;
		this.profile = profile;
	}
	
	protected Student(String userID, String password, String name, String surname) {
		super(userID, password);
		this.name = name;
		this.surname = surname;
	}
	
	protected Student(String userID, String password, String name, String surname, Profile profile,
			WeeklyPreferencies weeklyPreferencies, List<Lift> lifts) {
		super(userID, password);
		this.name = name;
		this.surname = surname;
		this.profile = profile;
		this.weeklyPreferencies = weeklyPreferencies;
		this.lifts = lifts;
	}

	//TODO Used in removeCar in SetCarInfoController, da mettere in una factory
	public Student(StudentCar studentCar) {
		this(studentCar.userID, studentCar.password, studentCar.name, studentCar.surname, studentCar.profile, studentCar.weeklyPreferencies, studentCar.lifts);
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if(name.length()<1) {
			// TODO Implementare meglio
			return;
		}
		this.name = name;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		if(surname.length()<1) {
			// TODO Implementare meglio
			return;
		}
		this.surname = surname;
	}

	public Profile getProfile() {
		return this.profile;
	}

	public void setProfile(Profile profile) {
		if(profile == null) {
			// TODO Implementare meglio
			return;
		}
		this.profile = profile;
	}

	public WeeklyPreferencies getWeeklyPreferencies() {
		return weeklyPreferencies;
	}

	public void setWeeklyPreferencies(WeeklyPreferencies weeklyPreferencies) {
		if(weeklyPreferencies == null) {
			// TODO Implementare meglio
			return;
		}
		this.weeklyPreferencies = weeklyPreferencies;
	}

	public List<Lift> getLifts() {
		return this.lifts;
	}

	public void setLifts(List<Lift> lifts) {
		if(lifts == null) {
			// TODO Implementare meglio
			return;
		}
		this.lifts = lifts;
	}
		
}
