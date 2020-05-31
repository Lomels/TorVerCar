package logic.view;

import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.model.Student;

public interface OurStudentDatabase {

	//return if student(with or without car) already exist in our database
	public boolean existByUserID(String userID) throws DatabaseException, InvalidInputException;
	
	//return if student(with or without car) was previously banned
	public boolean wasBannedByUserID(String userID) throws DatabaseException, InvalidInputException;
	
	//add a student to our database
	public void addStudent(Student student) throws DatabaseException, InvalidInputException;
	
	//load a student from our database
	public Student loadStudentByUserID(String userID) throws InvalidInputException, DatabaseException;
	
	//load the password for the login
	public String loadPasswordByUserID(String userID) throws InvalidInputException, DatabaseException;

	//update student info
	public void editInfoByUserID(String userID, String password, String email, String phone) throws DatabaseException;
}
