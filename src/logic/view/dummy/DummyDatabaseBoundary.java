package logic.view.dummy;

import logic.bean.UserInfo;
import logic.view.*;

public class DummyDatabaseBoundary implements DatabaseBoundary {

	@Override
	public UserInfo getByUserID(String userID) {
		UserInfo response = new UserInfo();
		response.setUserID(userID);
		response.setName("Mario");
		response.setSurname("Rossi");
		return response;
	}

}
