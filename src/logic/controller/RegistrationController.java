package logic.controller;

import logic.controller.email.SendEmail;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.model.UserSingleton;
import logic.model.Student;
import logic.model.StudentCar;
import logic.utilities.CodeGenerator;
import logic.utilities.InputChecker;
import logic.view.DatabaseBoundary;
import logic.view.OurStudentDatabase;

import java.io.IOException;

import logic.bean.CarInfoBean;
import logic.bean.UserBean;
import logic.bean.UserBeanSingleton;
import logic.view.mysql.*;

public class RegistrationController {
	private DatabaseBoundary uniDb;
	private OurStudentDatabase ourDb;
	UserSingleton sg = UserSingleton.getInstance();
	UserBeanSingleton beanSg = UserBeanSingleton.getInstance();

	public RegistrationController() {
		this.uniDb = new UniDAO();
		this.ourDb = new MySqlDAO();
		
	}
	
	public boolean alreadyExist(String userID) throws DatabaseException, InvalidInputException {
		if(ourDb.existByUserID(userID)) {
			return true;
			
		} 
		return false;
	}

	public void createStudent(String userID, String password) throws Exception{
		//check is userID and password are valid
		InputChecker.checkUserID(userID);
		InputChecker.checkPassword(password);
		//if to check if student already exist in our database
		if(ourDb.existByUserID(userID)) {
			throw new DatabaseException("Already registered student");
			
		} else {
			if(ourDb.wasBannedByUserID(userID)) {
				throw new DatabaseException("User was previously banned");
			}
			//get the UserInfo bean from uniDb
			UserBean response = uniDb.getByUserID(userID);
			//TODO: implement password encryption
			//build and add student to our database
			Student student = StudentBuilder.newBuilder(userID).password(password).fullname(response.getName(), response.getSurname()).build();
			ourDb.addStudent(student);			
		}
	}
	
	public void addStudent(UserBean user) throws InvalidInputException, DatabaseException{
		Student student = StudentBuilder.newBuilder(user.getUserID())
				.password(user.getPassword())
				.fullname(user.getName(), user.getSurname())
				.email(user.getEmail())
				.phone(user.getPhone())
				.build();
		ourDb.addStudent(student);
	}
	
	public void addStudentCar(CarInfoBean carInfo) throws InvalidInputException, DatabaseException {
		SetCarInfoController controller = new SetCarInfoController();
		StudentCar studentCar = controller.addCar(carInfo);
				
		ourDb.addStudentCar(studentCar);
	}
	
	
	
	public UserBean recapInfo(String userID) throws Exception{
		return uniDb.getByUserID(userID);
	}
	
	
	public void sendCode() throws IOException {
		UserBean user = beanSg.getUserBean();
		String code = CodeGenerator.randomCode();
		beanSg.setCode(code);
		String[] recipients = new String[] {user.getEmail()};
		new SendEmail().send(recipients,recipients, code);
	}
	
}
