package logic.controller;

import logic.bean.UserBean;
import logic.view.mysql.*;
import logic.controller.exception.*;
import logic.model.Student;
import logic.model.StudentCar;
import logic.model.UserSingleton;

public class LoginController {
	private MySqlDAO ourDb = new MySqlDAO();
	UserSingleton sg = UserSingleton.getInstance();
	
	public void login(UserBean bean) throws InvalidInputException, DatabaseException, InvalidStateException{
		if(checkBean(bean)) {
			if(!ourDb.wasBannedByUserID(bean.getUserID())) {

				sg.setRole(ourDb.loadRoleByUserID(bean.getUserID()));
				
				//TODO: implementare controllo sessione attiva
				switch(sg.getRole()) {
					case STUDENT:
						Student s = ourDb.loadStudentByUserID(bean.getUserID());
						sg.setStudent(s);
						break;
					case DRIVER:
						StudentCar sCar = ourDb.loadStudentCarByUserID(bean.getUserID());
						sg.setStudentCar(sCar);
						break;
					case ADMIN:
						//TODO: implementare
						break;
				}
			}else {
				throw new DatabaseException("Banned User");
			}
		}else {
			throw new InvalidInputException("Wrong password");
		}
		
	}
	
	public Boolean checkBean(UserBean bean) throws InvalidInputException, DatabaseException{
			return bean.getPassword().equals(ourDb.loadPasswordByUserID(bean.getUserID()));
	}
	
}
