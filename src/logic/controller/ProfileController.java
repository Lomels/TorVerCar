package logic.controller;

import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.controller.exception.InvalidStateException;
import logic.model.UserSingleton;
import logic.view.mysql.MySqlDAO;

public class ProfileController {
	private MySqlDAO ourDb = new MySqlDAO();
	UserSingleton sg = UserSingleton.getInstance();

	public void edit(String userID, String email, String phone, String password)
			throws InvalidInputException, DatabaseException, InvalidStateException {
		switch (sg.getRole()) {
			case STUDENT:
				sg.getStudent().setEmail(email);
				sg.getStudent().setPassword(password);
				sg.getStudent().setPhone(phone);
				break;
			case DRIVER:
				sg.getStudentCar().setEmail(email);
				sg.getStudentCar().setPassword(password);
				sg.getStudentCar().setPhone(phone);
				break;
			default:
				throw new InvalidStateException("Role not defined.");
		}
		ourDb.editInfoByUserID(userID, password, email, phone);

	}
}