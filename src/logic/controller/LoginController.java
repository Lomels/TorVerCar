package logic.controller;

import logic.bean.UserBean;
import logic.view.mysql.*;
import logic.controller.exception.*;
import logic.model.LiftSingleton;
import logic.model.Student;
import logic.model.StudentCar;
import logic.model.UserSingleton;

public class LoginController {
	private MySqlDAO ourDb = new MySqlDAO();
	private static UserSingleton sg = UserSingleton.getInstance();
	private static LiftSingleton liftSg = LiftSingleton.getInstance();

	public void login(UserBean bean) throws InvalidInputException, DatabaseException {
		if (checkBean(bean)) {
			if (!ourDb.wasBannedByUserID(bean.getUserID())) {
				sg.setRole(ourDb.loadRoleByUserID(bean.getUserID()));
				switch (sg.getRole()) {
				case STUDENT:
					Student s = ourDb.loadStudentByUserID(bean.getUserID());
					sg.setStudent(s);
					break;
				case DRIVER:
					StudentCar sCar = ourDb.loadStudentCarByUserID(bean.getUserID());
					sg.setStudentCar(sCar);
					break;
				default:
					throw new NoRoleFound();
				}
			} else {
				throw new DatabaseException("Banned User");
			}
		} else {
			throw new InvalidInputException("Wrong password");
		}

	}

	public boolean checkBean(UserBean bean) throws InvalidInputException, DatabaseException {
		return bean.getPassword().equals(ourDb.loadPasswordByUserID(bean.getUserID()));
	}

	public static void logout() throws InvalidStateException {
		liftSg.clearState();
		sg.clearState();
	}

}
