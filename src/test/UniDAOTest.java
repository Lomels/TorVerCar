package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import logic.bean.UserBean;
import logic.view.mysql.UniDAO;
import test.utilities.TestUtilities;

class UniDAOTest extends TestUtilities{
	
	private static final String USERID = "1234";
	private static final String NAME = "pippo";
	private static final String SURNAME = "franco";
	private static final String EMAIL = "abcdef";
	private static final String NOT_EXISTING_ID = "0";
	
	
//	@Test
	void loadExistingInfos() throws Exception {
		UniDAO uni = new UniDAO();
		UserBean iToCompare = new UserBean();
		iToCompare.setUserID(USERID);
		iToCompare.setName(NAME);
		iToCompare.setSurname(SURNAME);
		iToCompare.setEmail(EMAIL);
		//TODO: check two userBean
		assertTrue(false);
	}
	
//	@Test
	void loadNotExistingInfos() throws Exception{
		UniDAO uni = new UniDAO();
		try {
			uni.getByUserID(NOT_EXISTING_ID);
		}catch(Exception e){
			assertEquals(e.getMessage(), "Student Not Found");
		}
	}

}




