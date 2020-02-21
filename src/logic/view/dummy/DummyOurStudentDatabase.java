package logic.view.dummy;

import logic.bean.UserInfo;
import logic.controller.StudentBuilder;
import logic.controller.exception.DatabaseException;
import logic.entity.Student;
import logic.view.*;


public class DummyOurStudentDatabase implements OurStudentDatabase {
	
	public static int counter = 0;

	@Override
	public boolean existByUserID(String userID) {
		return userID.equals("00000");
	}

	@Override
	public void addStudent(Student student) throws DatabaseException {
		DummyOurStudentDatabase.counter++;
	}

	@Override
	public Student loadStudentByUserID(String userID) {
		DummyDatabaseBoundary uni = new DummyDatabaseBoundary();
		UserInfo res = uni.getByUserID("12345");
		Student s = StudentBuilder.newBuilder(userID).password("aaAAA123@").fullname(res.getName(), res.getSurname()).build();
		return s;
	}

}
