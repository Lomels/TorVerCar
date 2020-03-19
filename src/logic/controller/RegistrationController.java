package logic.controller;

import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.entity.Student;
import logic.view.DatabaseBoundary;
import logic.view.OurStudentDatabase;
import logic.view.dummy.DummyDatabaseBoundary;
import logic.view.dummy.DummyOurStudentDatabase;
import logic.bean.UserInfo;

public class RegistrationController {
	
	//TODO: amplia, guarda class diagram, cambiare input del createStudent con il bean

	private DatabaseBoundary uniDb;
	private OurStudentDatabase ourDb;

	public RegistrationController() {
		//TODO: implement uniDb and ourDb
		this.uniDb = new DummyDatabaseBoundary();
		this.ourDb = new DummyOurStudentDatabase();
	}

	public void createStudent(String userID, String password) throws DatabaseException, InvalidInputException{
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
			UserInfo response = uniDb.getByUserID(userID);
			//TODO: implement password encryption
			//build and add student to our database
			Student student = StudentBuilder.newBuilder(userID).password(password).fullname(response.getName(), response.getSurname()).build();
			ourDb.addStudent(student);
		}
	}


}
