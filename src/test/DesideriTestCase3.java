package test;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import logic.bean.UserBean;
import logic.controller.ProfileController;
import logic.controller.RegistrationController;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.controller.exception.InvalidStateException;
import logic.model.Role;
import logic.model.Student;
import logic.model.UserSingleton;
import logic.view.mysql.MySqlDAO;

/* GIULIA DESIDERI */

class DesideriTestCase3 {
	private MySqlDAO dao = new MySqlDAO();
	private UserSingleton sg = UserSingleton.getInstance();
	private RegistrationController registration = new RegistrationController();
	private ProfileController profile = new ProfileController();
	private UserBean userBean = new UserBean();
	
	private static final String USERID = "0245061";
	public static final String PASSWORD = "aaaAAA123@";
	public static final String PHONE = "3334445556";

	public static final String PLATE = "FA769KN";
	public static final Integer SEATS = 4;
	public static final String MODEL = "Fiat Macchina";
	public static final String COLOR = "Arcobaleno";
	
	private void setup() throws DatabaseException, InvalidInputException {
		userBean.setUserID(USERID);
		dao.removeStudentByUserID(userBean.getUserID());

		userBean = registration.recapInfo(userBean);
		userBean.setPassword(PASSWORD);
		userBean.setPhone(PHONE);

		registration.addStudent(userBean);
		
		sg.setStudent(dao.loadStudentByUserID(USERID));
		sg.setRole(Role.STUDENT);
	}
	@Test
	public void editInfoTest() throws DatabaseException, InvalidInputException, InvalidStateException {
		this.setup();
		userBean.setPhone("1113336669");
		userBean.setEmail("giuls.desideri.30@gmail.com");
		userBean.setPassword("@321AAAaaa");
		
		Student student = dao.loadStudentByUserID(USERID);
		student.setPhone("1113336669");
		student.setEmail("giuls.desideri.30@gmail.com");
		student.setPassword("@321AAAaaa");
		
		profile.edit(userBean);
		Student daoStudent = dao.loadStudentByUserID(USERID);
		
		assertTrue(student.compare(daoStudent));
	}

}
