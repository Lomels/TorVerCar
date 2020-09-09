package logic.view;

import java.time.LocalDateTime;
import java.util.List;
import logic.bean.CarInfoBean;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.model.Lift;
import logic.model.Role;
import logic.model.Student;
import logic.model.StudentCar;

public interface OurStudentDatabase {

	// return if student(with or without car) already exist in our database
	public boolean existByUserID(String userID) throws DatabaseException;

	// return if student(with or without car) was previously banned
	public boolean wasBannedByUserID(String userID) throws DatabaseException;

	// add a student to our database
	public void addStudent(Student student) throws DatabaseException;

	// load a student from our database
	public Student loadStudentByUserID(String userID) throws InvalidInputException, DatabaseException;

	// load the password for the login
	public String loadPasswordByUserID(String userID) throws InvalidInputException, DatabaseException;

	// update student info
	public void editInfoByUserID(String userID, String password, String email, String phone) throws DatabaseException;

	// add a student with car to our database
	public void addStudentCar(StudentCar studentCar) throws DatabaseException, InvalidInputException;

	// load a student with car from our database
	public StudentCar loadStudentCarByUserID(String userID) throws DatabaseException, InvalidInputException;

	public void editCarInfoByUserID(String userID, CarInfoBean carInfo) throws DatabaseException;

	public void addCar(StudentCar studentCar) throws DatabaseException;

	public Role loadRoleByUserID(String userID) throws DatabaseException;

	// Lift queries

	public void saveLift(Lift lift) throws DatabaseException;

	public Lift loadLiftByID(Integer liftID) throws DatabaseException, InvalidInputException;

	public void deleteLiftByID(Integer liftID) throws DatabaseException;

	public List<Lift> listLiftsByDriverID(String driverID) throws DatabaseException, InvalidInputException;

	public List<Lift> listLiftsByPassengerID(String passengerID) throws DatabaseException, InvalidInputException;

	// Passenger queries

	public void addPassengerByLiftIDAndUserID(Integer liftID, String passengerID) throws DatabaseException;

	public List<Student> listPassengersByLiftID(Integer liftID) throws DatabaseException, InvalidInputException;

	public void removePassengerByLiftIDAndUserID(Integer liftID, String passengerID) throws DatabaseException;

	public void addNotificationByUserID(String userID, String message) throws DatabaseException;

	public void removeCarByUserID(String userID) throws DatabaseException;

	public List<String> loadNotificationsByUserID(String userID) throws DatabaseException;

	public void removeNotificationsByUserID(String userID) throws DatabaseException;

	public void upvoteRating(String userID, Integer liftID, String driverID) throws DatabaseException;

	public void downvoteRating(String userID, Integer liftID, String driverID) throws DatabaseException;

	public List<Lift> listUnratedLiftsByPassengerID(String passengerID) throws DatabaseException, InvalidInputException;

	public List<Lift> listAvailableLiftStartingWithinIntervalDateTime(LocalDateTime intervalStartDateTime,
			LocalDateTime intervalStopDateTime) throws DatabaseException, InvalidInputException;

	public Lift getLastInsertedLift() throws DatabaseException, InvalidInputException;

	public Integer getLastInsertedLiftID() throws DatabaseException, InvalidInputException;

	void removeStudentByUserID(String userID) throws DatabaseException;
  
	boolean isRatedFromAllPassengers(Lift lift) throws DatabaseException;

	Lift getEarliestPassengerLift(Student passenger) throws DatabaseException, InvalidInputException;

	Lift getEarliestDriverLift(StudentCar driver) throws DatabaseException, InvalidInputException;

}
