package logic.view.dummy;

import logic.bean.UserBean;
import logic.controller.StudentBuilder;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.model.Student;
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
	public Student loadStudentByUserID(String userID) throws InvalidInputException, DatabaseException {
		DummyDatabaseBoundary uni = new DummyDatabaseBoundary();
		UserBean res = uni.getByUserID("12345");
		Student s;
		s = StudentBuilder.newBuilder(userID).password("aaAAA123@").fullname(res.getName(), res.getSurname()).build();	
		return s;
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

}
