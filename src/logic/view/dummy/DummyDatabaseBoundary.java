package logic.view.dummy;

import logic.bean.UserBean;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.view.*;

public class DummyDatabaseBoundary implements DatabaseBoundary {

	@Override
	public UserBean getByUserID(String userID) {
		UserBean response = new UserBean();
		response.setUserID(userID);
		response.setName("Mario");
		response.setSurname("Rossi");
		return response;
	}

	@Override
	public Boolean existByUserID(String userID) throws DatabaseException, InvalidInputException {
		// TODO Auto-generated method stub
		return null;
	}

}
