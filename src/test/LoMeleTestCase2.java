package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import logic.bean.CarInfoBean;
import logic.bean.UserBean;
import logic.controller.RegistrationController;
import logic.controller.SetCarInfoController;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.model.CarInfo;
import logic.model.Role;
import logic.model.UserSingleton;
import logic.view.mysql.MySqlDAO;

/* MARCO LO MELE */

public class LoMeleTestCase2 {
	private SetCarInfoController carController = new SetCarInfoController();
	private RegistrationController registration = new RegistrationController();
	private UserBean userBean = new UserBean();
	private CarInfoBean carInfo = new CarInfoBean();
	private MySqlDAO dao = new MySqlDAO();
	private UserSingleton sg = UserSingleton.getInstance();

	private static final String USERID1 = "0567891";
	public static final String PASSWORD = "aaaAAA123@";
	public static final String PHONE = "3334445556";

	public static final String PLATE = "FA769KN";
	public static final Integer SEATS = 4;
	public static final String MODEL = "Fiat Macchina";
	public static final String COLOR = "Arcobaleno";

	private void setupNoCar(String userID) throws DatabaseException, InvalidInputException {
		userBean.setUserID(userID);
		dao.removeStudentByUserID(userBean.getUserID());

		userBean = registration.recapInfo(userBean);
		userBean.setPassword(PASSWORD);
		userBean.setPhone(PHONE);

		registration.addStudent(userBean);
		
		sg.setStudent(dao.loadStudentByUserID(userID));
		sg.setRole(Role.STUDENT);
	}

	private void setupWithCar(String userID) throws DatabaseException, InvalidInputException {
		userBean.setUserID(userID);
		dao.removeStudentByUserID(userBean.getUserID());

		userBean = registration.recapInfo(userBean);
		userBean.setPassword(PASSWORD);
		userBean.setPhone(PHONE);

		carInfo.setModel(MODEL);
		carInfo.setColour(COLOR);
		carInfo.setPlate(PLATE);
		carInfo.setSeats(SEATS);

		registration.addStudentCar(carInfo, userBean);
		
		sg.setStudentCar(dao.loadStudentCarByUserID(userID));
		sg.setRole(Role.DRIVER);
	}

	@Test
	public void editDriverCarTest() throws DatabaseException, InvalidInputException {
		this.setupWithCar(USERID1);

		CarInfoBean newCarInfo = new CarInfoBean();
		newCarInfo.setColour("Red");
		newCarInfo.setModel("Fiat Panda");
		newCarInfo.setSeats(3);
		newCarInfo.setPlate("CC333CC");

		carController.editCar(newCarInfo);

		CarInfo insertedCar = new CarInfo(newCarInfo.getPlate(), newCarInfo.getSeats(), newCarInfo.getModel(),
				newCarInfo.getColour());
		CarInfo daoCar = dao.loadStudentCarByUserID(USERID1).getCarInfo();
		
		assertEquals(insertedCar.toString(), daoCar.toString());
	}
	
	@Test
	public void editCarStudentTest() throws DatabaseException, InvalidInputException {
		this.setupNoCar(USERID1);

		carInfo.setColour(COLOR);
		carInfo.setModel(MODEL);
		carInfo.setSeats(SEATS);
		carInfo.setPlate(PLATE);

		carController.editCar(carInfo);

		CarInfo insertedCar = new CarInfo(carInfo.getPlate(), carInfo.getSeats(), carInfo.getModel(),
				carInfo.getColour());
		CarInfo daoCar = dao.loadStudentCarByUserID(USERID1).getCarInfo();
		Role daoRole = dao.loadRoleByUserID(USERID1);
		
		assertEquals(insertedCar.toString().equals(daoCar.toString()), daoRole.equals(Role.DRIVER));
	}

	

}
