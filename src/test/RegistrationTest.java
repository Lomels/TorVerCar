package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import logic.bean.CarInfoBean;
import logic.bean.UserBean;
import logic.controller.RegistrationController;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.model.CarInfo;
import logic.model.Student;
import logic.model.StudentCar;
import test.utilities.TestUtilities;

/* MARCO LO MELE */

class RegistrationTest extends TestUtilities {
	private RegistrationController registration = new RegistrationController();
	private UserBean userBean = new UserBean();

	private String beanToString(UserBean user) {
		return "UserID: " + user.getUserID() + ", Name: " + user.getName() + ", Surname: " + user.getSurname()
				+ ", Email: " + user.getEmail();
	}

	@Test
	void alreadyExistTest() throws DatabaseException, InvalidInputException {
		userBean.setUserID(ID_MARCO);

		assertTrue(registration.alreadyExist(userBean));
	}

	@Test
	void notExistingStudentTest() throws DatabaseException, InvalidInputException {
		userBean.setUserID("9999999");

		assertFalse(registration.alreadyExist(userBean));
	}

	@Test
	public void loadInfo() throws DatabaseException, InvalidInputException {
		userBean.setUserID(ID_MARCO);
		userBean.setEmail("marco.lomele@gmail.com");
		userBean.setName("Marco");
		userBean.setSurname("Lo Mele");

		assertEquals(beanToString(userBean), beanToString(registration.recapInfo(userBean)));
	}

	@Test
	public void addStudent() throws DatabaseException, InvalidInputException {
		dao.removeStudentByUserID(ID_MARCO);

		userBean.setUserID(ID_MARCO);
		userBean.setPassword(PASSWORD);
		userBean.setPhone(PHONE);
		userBean.setEmail("marco.lomele@gmail.com");
		userBean.setName("Marco");
		userBean.setSurname("Lo Mele");
		registration.addStudent(userBean);

		Student student = new Student(userBean.getUserID(), userBean.getPassword(), userBean.getEmail(),
				userBean.getName(), userBean.getSurname(), userBean.getPhone());

		Student daoStudent = dao.loadStudentByUserID(ID_MARCO);
		assertTrue(student.compare(daoStudent));
	}

	@Test
	public void addStudentCar() throws InvalidInputException, DatabaseException {
		dao.removeStudentByUserID(ID_MARCO);

		CarInfoBean carInfoBean = new CarInfoBean();
		carInfoBean.setModel(MODEL);
		carInfoBean.setColour(COLOR);
		carInfoBean.setPlate("TV777CR");
		carInfoBean.setSeats(SEATS);

		userBean.setUserID(ID_MARCO);
		userBean.setPassword(PASSWORD);
		userBean.setPhone(PHONE);
		userBean.setEmail("marco.lomele@gmail.com");
		userBean.setName("Marco");
		userBean.setSurname("Lo Mele");

		CarInfo carInfo = new CarInfo(carInfoBean.getPlate(), carInfoBean.getSeats(), carInfoBean.getModel(),
				carInfoBean.getColour());

		Student student = new Student(userBean.getUserID(), userBean.getPassword(), userBean.getEmail(),
				userBean.getName(), userBean.getSurname(), userBean.getPhone());
		StudentCar studentCar = new StudentCar(student, 0, carInfo);
		dao.addStudentCar(studentCar);
//		registration.addStudentCar(carInfoBean, userBean);
		StudentCar daoStudCar = dao.loadStudentCarByUserID(ID_MARCO);
		
		assertTrue(studentCar.compare(daoStudCar));
	}

}
