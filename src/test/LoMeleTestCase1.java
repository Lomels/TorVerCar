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
import logic.view.mysql.MySqlDAO;

/* MARCO LO MELE */

class LoMeleTestCase1{
	private RegistrationController registration = new RegistrationController();
	private UserBean userBean = new UserBean();
	private MySqlDAO dao = new MySqlDAO();

	private static final String USERID1 = "0567891";
	private static final String USERID2 = "0241118";
	public static final String PASSWORD = "aaaAAA123@";
	public static final String PHONE = "3334445556";

	public static final String PLATE = "FA769KN";
	public static final Integer SEATS = 4;
	public static final String MODEL = "Fiat Macchina";
	public static final String COLOR = "Arcobaleno";
	
	
	private String beanToString(UserBean user) {
		return "UserID: " + user.getUserID() + ", Name: " + user.getName() + ", Surname: " + user.getSurname()
				+ ", Email: " + user.getEmail();
	}

	@Test
	void alreadyExistTest() throws DatabaseException, InvalidInputException {
		userBean.setUserID(USERID1);

		assertTrue(registration.alreadyExist(userBean));
	}

	@Test
	void notExistingStudentTest() throws DatabaseException, InvalidInputException {
		userBean.setUserID("9999999");

		assertFalse(registration.alreadyExist(userBean));
	}

	@Test
	public void loadInfo() throws DatabaseException, InvalidInputException {
		userBean.setUserID(USERID2);
		userBean.setEmail("marco.lomele@gmail.com");
		userBean.setName("Marco");
		userBean.setSurname("Lo Mele");

		assertEquals(beanToString(userBean), beanToString(registration.recapInfo(userBean)));
	}

	@Test
	public void addStudent() throws DatabaseException, InvalidInputException {
		userBean.setUserID(USERID1);
		dao.removeStudentByUserID(userBean.getUserID());

		userBean = registration.recapInfo(userBean);
		userBean.setPassword(PASSWORD);
		userBean.setPhone(PHONE);

		registration.addStudent(userBean);
		
		Student student = new Student(userBean.getUserID(), userBean.getPassword(), userBean.getEmail(),
				userBean.getName(), userBean.getSurname(), userBean.getPhone());

		Student daoStudent = dao.loadStudentByUserID(userBean.getUserID());
		assertTrue(student.compare(daoStudent));
	}

	@Test
	public void addStudentCar() throws InvalidInputException, DatabaseException {
		dao.removeStudentByUserID(USERID2);

		CarInfoBean carInfo = new CarInfoBean();
		carInfo.setModel(MODEL);
		carInfo.setColour(COLOR);
		carInfo.setPlate("TV777CR");
		carInfo.setSeats(SEATS);

		userBean.setUserID(USERID2);
		userBean.setPassword(PASSWORD);
		userBean.setPhone(PHONE);
		userBean.setEmail("marco.lomele@gmail.com");
		userBean.setName("Marco");
		userBean.setSurname("Lo Mele");

		CarInfo car = new CarInfo(carInfo.getPlate(), carInfo.getSeats(), carInfo.getModel(), carInfo.getColour());

		Student student = new Student(userBean.getUserID(), userBean.getPassword(), userBean.getEmail(),
				userBean.getName(), userBean.getSurname(), userBean.getPhone());

		StudentCar studentCar = new StudentCar(student, 0, car);
		
		registration.addStudentCar(carInfo, userBean);
		
		StudentCar daoStudent = dao.loadStudentCarByUserID(USERID2);
		
		assertTrue(studentCar.compare(daoStudent));
	}

}
