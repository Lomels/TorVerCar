package logic.controller;

import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.controller.exception.InvalidStateException;
import logic.model.UserSingleton;
import logic.view.mysql.MySqlDAO;

public class ProfileController {
	private MySqlDAO ourDb = new MySqlDAO();
	UserSingleton sg = UserSingleton.getInstance();
	
	
	
	public void edit(String userID, String email, String phone, String password) throws InvalidInputException, DatabaseException, InvalidStateException{
		
			 
			 ourDb.editInfoByUserID(userID, email, phone, password);
			 

	}
}