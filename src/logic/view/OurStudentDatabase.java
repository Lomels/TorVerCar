package logic.view;

import org.json.JSONException;

import logic.bean.CarInfoBean;
import logic.bean.UserBean;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.model.Lift;
import logic.model.Role;
import logic.model.Student;
import logic.model.StudentCar;

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

	//add a student with car to our database
	public void addStudentCar(StudentCar studentCar) throws DatabaseException, InvalidInputException;

	//load a student with car from our database
	public StudentCar loadStudentCarByUserID(String userID) throws DatabaseException;

	public void editCarInfoByUserID(String userID, CarInfoBean carInfo) throws DatabaseException;

	public void addCar(StudentCar studentCar) throws DatabaseException;

	public Role loadRoleByUserID(String userID) throws DatabaseException;
	
	public void saveLift(Lift lift) throws DatabaseException;
	
	public Lift loadLiftbyLiftID(Integer liftID) throws DatabaseException, JSONException, InvalidInputException;
}
