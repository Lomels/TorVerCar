package test;

import logic.bean.UserBean;
import logic.controller.SetCarInfoController;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.utilities.MyLogger;
import logic.view.mysql.MySqlDAO;
import test.utilities.TestUtilities;

public class StudentPersistenceTest extends TestUtilities{
	private SetCarInfoController carController = new SetCarInfoController();
	private MySqlDAO dao = new MySqlDAO();

	
//	@Test
	public void removeCar() throws DatabaseException, InvalidInputException {
		String userID = USER_ID+"2";
		MyLogger.info("Old user role", dao.loadRoleByUserID(userID));

		UserBean userBean = new UserBean();
		userBean.setUserID(userID);
		carController.removeCar(userBean);
		
		MyLogger.info("New user role", dao.loadRoleByUserID(userID));
	}
	
}
