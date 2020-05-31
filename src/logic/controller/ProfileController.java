package logic.controller;

public class ProfileController {
	private MySqlDAO ourDb = new MySqlDAO();
	UserSingleton sg = UserSingleton.getInstance();
	
	
	
	public void edit(UderID userID, String email, String phone, String password) throws InvalidInputException, DatabaseException, InvalidStateException{
		
			 
			 ourDb.editInfoByUserID(userID, email, phone, password);
			 

	}
