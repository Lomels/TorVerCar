package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import logic.bean.UserInfo;
import logic.view.mysql.UniDAO;

class UniDAOTest {
	
	private static final String USERID = "1234";
	private static final String NAME = "pippo";
	private static final String SURNAME = "franco";
	private static final String EMAIL = "abcdef";
	
	@Test
	void loadExistingInfos() throws Exception {
		UniDAO uni = new UniDAO();
		UserInfo iByDao = uni.getByUserID(USERID);
		System.out.println("UserID = "+iByDao.getUserID()+", Name = "+iByDao.getName()+", Surname = "+iByDao.getSurname()+", Email = "+iByDao.getEmail());
		UserInfo iToCompare = new UserInfo();
		iToCompare.setUserID(USERID);
		iToCompare.setName(NAME);
		iToCompare.setSurname(SURNAME);
		iToCompare.setEmail(EMAIL);
		
		//assertTrue(iToCompare.equals(iByDao));
		assertEquals(iToCompare.toString(), iByDao.toString());
	}


}
