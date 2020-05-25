package logic.view;

import logic.bean.UserInfo;

public interface DatabaseBoundary {

	public UserInfo getByUserID(String userID) throws Exception;
	
	public Boolean existByUserID(String userID) throws Exception;
	
}
