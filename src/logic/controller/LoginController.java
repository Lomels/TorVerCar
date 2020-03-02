package logic.controller;

import logic.bean.LoginBean;
import logic.view.dummy.*;
import logic.controller.exception.*;

public class LoginController {
	private DummyOurStudentDatabase ourDb;
	
	public LoginController() {
		this.ourDb = new DummyOurStudentDatabase();
	}
	
	public void login(LoginBean bean) throws InvalidInputException, DatabaseException {
		//TODO implementare logica di controllo di login
	}
	
	public Boolean checkBean(LoginBean bean) throws InvalidInputException, DatabaseException{
			return bean.getPwd().equals(ourDb.loadPasswordByUserID(bean.getUserID()));
	}
}
