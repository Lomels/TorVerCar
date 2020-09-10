package test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import logic.bean.CarInfoBean;
import logic.bean.UserBean;
import logic.controller.RegistrationController;
import logic.controller.SetCarInfoController;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.model.Role;
import logic.utilities.MyLogger;
import test.utilities.TestUtilities;

/* MARCO LO MELE */

public class SetCarInfoTest extends TestUtilities{
	private SetCarInfoController carController = new SetCarInfoController();
	private RegistrationController registration = new RegistrationController();
	private UserBean userBean = new UserBean();
	private CarInfoBean carInfo = new CarInfoBean();
	
	
	@Test
	public void editCarTest() {
		
	}
	
	@Test
	public void removeCarTest() throws DatabaseException, InvalidInputException {
		String userID = "0567891";
		userBean.setUserID(userID);
		dao.removeStudentByUserID(userBean.getUserID());

		userBean = registration.recapInfo(userBean);
		userBean.setPassword(PASSWORD);
		userBean.setPhone(PHONE);

		carInfo.setModel(MODEL);
		carInfo.setColour(COLOR);
		carInfo.setPlate("FA769KN");
		carInfo.setSeats(SEATS);
		
		registration.addStudentCar(carInfo, userBean);
		MyLogger.info("Old user role", dao.loadRoleByUserID(userID));

		carController.removeCar(userBean);
		
		MyLogger.info("New user role", dao.loadRoleByUserID(userID));
		assertEquals(Role.STUDENT, dao.loadRoleByUserID(userID));
	}
	
}
