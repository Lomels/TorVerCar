package logic.view;

import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.entity.Student;

public interface OurStudentDatabase {

	//return if student(with or without car) already exist in our database
	public boolean existByUserID(String userID);
	
	//return if student(with or without car) was previously banned
	public boolean wasBannedByUserID(String userID);
	
	//add a student to our database
	public void addStudent(Student student) throws DatabaseException;
	
	//load a student from our database
	public Student loadStudentByUserID(String userID) throws InvalidInputException;
	
	public String loadPasswordByUserID(String userID) throws InvalidInputException, DatabaseException;
}
