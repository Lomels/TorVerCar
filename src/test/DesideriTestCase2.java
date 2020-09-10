package test;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import logic.bean.CarInfoBean;
import logic.bean.UserBean;
import logic.controller.RegistrationController;
import logic.controller.SetCarInfoController;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.model.Role;
import logic.model.UserSingleton;
import logic.view.mysql.MySqlDAO;

/* GIULIA DESIDERI */

class DesideriTestCase2 {
	private SetCarInfoController carController = new SetCarInfoController();
	private RegistrationController registration = new RegistrationController();
	private UserBean userBean = new UserBean();
	private CarInfoBean carInfo = new CarInfoBean();
	private MySqlDAO dao = new MySqlDAO();
	private UserSingleton sg = UserSingleton.getInstance();

	
	@Test
	public void removeCarTest() throws DatabaseException, InvalidInputException {
		String userID = "0245061";
		userBean.setUserID(userID);
		dao.removeStudentByUserID(userBean.getUserID());

		userBean = registration.recapInfo(userBean);
		userBean.setPassword("aaaAAA123@");
		userBean.setPhone("3334445556");

		carInfo.setModel("Alfa Romeo Giulia");
		carInfo.setColour("Rossa");
		carInfo.setPlate("FL398CD");
		carInfo.setSeats(4);

		registration.addStudentCar(carInfo, userBean);
		
		sg.setStudentCar(dao.loadStudentCarByUserID(userID));
		sg.setRole(Role.DRIVER);
		carController.removeCar(userBean);

		assertEquals(Role.STUDENT, dao.loadRoleByUserID(userID));
	}

}
