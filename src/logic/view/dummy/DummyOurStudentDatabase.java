package logic.view.dummy;

import logic.bean.CarInfoBean;
import logic.bean.UserBean;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.model.Role;
import logic.model.Student;
import logic.model.StudentCar;
import logic.view.*;


public class DummyOurStudentDatabase implements OurStudentDatabase {

	private static int counter = 0;

	public static void incrementCounter() {
		DummyOurStudentDatabase.counter++;
	}

	public static int getCounter() {
		return DummyOurStudentDatabase.counter;
	}

	@Override
	public boolean existByUserID(String userID) throws DatabaseException, InvalidInputException {
		return userID.equals("00000");
	}

	@Override
	public void addStudent(Student student) throws DatabaseException, InvalidInputException {
		DummyOurStudentDatabase.incrementCounter();
	}

	

	@Override
	public boolean wasBannedByUserID(String userID) throws DatabaseException, InvalidInputException {
		return userID.equals("99999");
	}

	@Override
	public String loadPasswordByUserID(String user) throws InvalidInputException, DatabaseException {
		return "aaaA123@";
	}

	@Override
	public void editInfoByUserID(String userID, String password, String email, String phone) throws DatabaseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addStudentCar(StudentCar studentCar) throws DatabaseException, InvalidInputException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Student loadStudentByUserID(String userID) throws InvalidInputException, DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public void editCarInfoByUserID(String userID, CarInfoBean carInfo) throws DatabaseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addCar(StudentCar studentCar) throws DatabaseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public StudentCar loadStudentCarByUserID(String userID) throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Role loadRoleByUserID(String userID) throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

}
