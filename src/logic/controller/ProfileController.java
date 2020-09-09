package logic.controller;

import logic.bean.UserBean;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.controller.exception.InvalidStateException;
import logic.model.UserSingleton;
import logic.view.mysql.MySqlDAO;

public class ProfileController {
	private MySqlDAO ourDb = new MySqlDAO();
	UserSingleton sg = UserSingleton.getInstance();

	public void edit(UserBean usr)
			throws InvalidInputException, DatabaseException, InvalidStateException {
		switch (sg.getRole()) {
			case STUDENT:
				sg.getStudent().setEmail(usr.getEmail());
				sg.getStudent().setPassword(usr.getPassword());
				sg.getStudent().setPhone(usr.getPhone());
				break;
			case DRIVER:
				sg.getStudentCar().setEmail(usr.getEmail());
				sg.getStudentCar().setPassword(usr.getPassword());
				sg.getStudentCar().setPhone(usr.getPhone());
				break;
			default:
				throw new InvalidStateException("Role not defined.");
		}
		ourDb.editInfoByUserID(usr.getUserID(), usr.getPassword(), usr.getEmail(), usr.getPhone());

	}
	
	public void deleteProfile(UserBean usr) throws DatabaseException {
		ourDb.removeStudentByUserID(usr.getUserID());
	}
}