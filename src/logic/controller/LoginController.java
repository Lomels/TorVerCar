package logic.controller;

import logic.bean.LoginBean;
import logic.view.mysql.*;
import logic.controller.exception.*;
import logic.entity.Student;

public class LoginController {
	private MySqlDAO ourDb;
	private Student student;
	
	public LoginController() {
		this.ourDb = new MySqlDAO();
	}
	
	public void login(LoginBean bean) throws InvalidInputException, DatabaseException, InvalidStateException{
		//TODO implementare logica di controllo di login
		if(checkBean(bean)) {
			if(!ourDb.wasBannedByUserID(bean.getUserID())) {
				student = ourDb.loadStudentByUserID(bean.getUserID());
				student.setState(true);
			}else {
				throw new DatabaseException("Banned User");
			}
		}else {
			throw new InvalidInputException("Wrong password");
		}
		
	}
	
	public Boolean checkBean(LoginBean bean) throws InvalidInputException, DatabaseException{
			return bean.getPwd().equals(ourDb.loadPasswordByUserID(bean.getUserID()));
	}
	
}
