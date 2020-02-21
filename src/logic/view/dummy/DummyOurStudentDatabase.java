package logic.view.dummy;

import logic.bean.UserInfo;
import logic.controller.StudentBuilder;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.entity.Student;
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
	public boolean existByUserID(String userID) {
		return userID.equals("00000");
	}

	@Override
	public void addStudent(Student student) throws DatabaseException {
		DummyOurStudentDatabase.incrementCounter();
	}

	@Override
	public Student loadStudentByUserID(String userID) throws InvalidInputException {
		DummyDatabaseBoundary uni = new DummyDatabaseBoundary();
		UserInfo res = uni.getByUserID("12345");
		Student s;
		s = StudentBuilder.newBuilder(userID).password("aaAAA123@").fullname(res.getName(), res.getSurname()).build();	
		return s;
	}

	@Override
	public boolean wasBannedByUserID(String userID) {
		return userID.equals("99999");
	}

}
