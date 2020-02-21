package logic.view;

import logic.bean.UserInfo;

public interface DatabaseBoundary {

	public UserInfo getByUserID(String userID);
	
}
