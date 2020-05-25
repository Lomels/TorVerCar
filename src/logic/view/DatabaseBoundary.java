package logic.view;

import logic.bean.UserInfoBean;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;

public interface DatabaseBoundary {

	public UserInfoBean getByUserID(String userID) throws DatabaseException, InvalidInputException;
	
	public Boolean existByUserID(String userID) throws DatabaseException, InvalidInputException;
	
}
