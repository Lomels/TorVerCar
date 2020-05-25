package logic.view;

import logic.bean.UserInfo;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;

public interface DatabaseBoundary {

	public UserInfo getByUserID(String userID) throws DatabaseException, InvalidInputException;
	
	public Boolean existByUserID(String userID) throws DatabaseException, InvalidInputException;
	
}
