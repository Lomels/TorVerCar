package logic.controller;

import logic.bean.CarInfoBean;
import logic.bean.UserBean;
import logic.bean.UserBeanSingleton;
import logic.controller.email.SendEmail;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.model.Student;
import logic.model.StudentCar;
import logic.model.UserSingleton;
import logic.utilities.CodeGenerator;
import logic.utilities.MyLogger;
import logic.view.DatabaseBoundary;
import logic.view.mysql.MySqlDAO;
import logic.view.mysql.UniDAO;

public class RegistrationController {
	private DatabaseBoundary uniDb;
	private MySqlDAO ourDb = new MySqlDAO();
	UserSingleton sg = UserSingleton.getInstance();
	UserBeanSingleton beanSg = UserBeanSingleton.getInstance();

	public RegistrationController() {
		this.uniDb = new UniDAO();
		this.ourDb = new MySqlDAO();
	}

	public boolean alreadyExist(UserBean user) throws DatabaseException {
		return ourDb.existByUserID(user.getUserID());
	}

	public void addStudent(UserBean user) throws InvalidInputException, DatabaseException {
		Student student = new Student(user.getUserID(), user.getPassword(), user.getEmail(), user.getName(), user.getSurname(), user.getPhone());
		ourDb.addStudent(student);
	}

	public void addStudentCar(CarInfoBean carInfo, UserBean user) throws InvalidInputException, DatabaseException {
		SetCarInfoController controller = new SetCarInfoController();
		StudentCar studentCar = controller.addCar(carInfo, user);
		MyLogger.info("sCar", studentCar.toString());
		ourDb.addStudentCar(studentCar);
	}

	public UserBean recapInfo(UserBean user) throws DatabaseException, InvalidInputException  {
		return uniDb.getByUserID(user.getUserID());
	}

	public void sendCode() {
		UserBean user = beanSg.getUserBean();
		String code = CodeGenerator.randomCode();
		String subject = "Confirm your identity";
		String body = "Hi! Your activation code is: %s";
		String message = String.format(body, code);
		beanSg.setCode(code);

		String[] recipients = new String[] { user.getEmail() };
		new SendEmail().send(recipients, recipients, subject, message);
	}

}
