package logic.view;

import logic.bean.UserBean;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;

public interface DatabaseBoundary {

	public UserBean getByUserID(String userID) throws DatabaseException, InvalidInputException;
	
	public Boolean existByUserID(String userID) throws DatabaseException, InvalidInputException;
	
}
