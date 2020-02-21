package logic.controller;

import java.security.InvalidParameterException;

import logic.controller.exception.DatabaseException;
import logic.entity.Student;
import logic.view.DatabaseBoundary;
import logic.view.OurStudentDatabase;
import logic.view.dummy.DummyDatabaseBoundary;
import logic.view.dummy.DummyOurStudentDatabase;
import logic.bean.UserInfo;

public class RegistrationController {

	private DatabaseBoundary uniDb;
	private OurStudentDatabase ourDb;

	public RegistrationController() {
		//TODO: implement uniDb and ourDb
		this.uniDb = new DummyDatabaseBoundary();
		this.ourDb = new DummyOurStudentDatabase();
	}

	public void createStudent(String userID, String password) throws DatabaseException, InvalidParameterException{
		try {
			//if to check is userID and password are valid
			if(InputChecker.checkUserID(userID) && InputChecker.checkPassword(password)) {
				//if to check if student already exist in our database
				if(ourDb.existByUserID(userID)) {
					throw new DatabaseException("Already registered student");
				} else {
					//get the UserInfo bean from uniDb
					UserInfo response = uniDb.getByUserID(userID);
					//TODO: implement password encryption
					//build and add student to our database
					Student student = StudentBuilder.newBuilder(userID).password(password).fullname(response.getName(), response.getSurname()).build();
					ourDb.addStudent(student);
				}
			} else {
				throw new InvalidParameterException("Invalid userID or password");
			}
			//chain-throw exception to make the graphic controller handle it
		} catch (Exception e) {
			throw e;
		}
//		catch (DatabaseException e) {
//			throw e;
//		}	catch (InvalidParameterException e) {
//			throw e;
//		}
	}


}
