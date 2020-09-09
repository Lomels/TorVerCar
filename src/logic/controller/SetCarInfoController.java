package logic.controller;

import logic.bean.CarInfoBean;
import logic.bean.UserBean;
import logic.bean.UserBeanSingleton;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.controller.exception.NoRoleFound;
import logic.model.CarInfo;
import logic.model.Role;
import logic.model.Student;
import logic.model.StudentCar;
import logic.model.UserSingleton;
import logic.view.mysql.MySqlDAO;

public class SetCarInfoController {
	UserSingleton sg = UserSingleton.getInstance();
	UserBeanSingleton sgBean = UserBeanSingleton.getInstance();
	MySqlDAO ourDb = new MySqlDAO();

	public StudentCar addCar(CarInfoBean carInfo) throws InvalidInputException {
		CarInfo car = new CarInfo(carInfo.getPlate(), carInfo.getSeats(), carInfo.getModel(), carInfo.getColour());

		UserBean userBean = sgBean.getUserBean();
		Student student = new Student(userBean.getUserID(), userBean.getPassword(), userBean.getEmail(),
				userBean.getName(), userBean.getSurname(), userBean.getPhone());

		return new StudentCar(student, 0, car);

	}

	public void editCar(CarInfoBean newCarInfo) throws InvalidInputException, DatabaseException {
		CarInfo carInfo = new CarInfo(newCarInfo.getPlate(), newCarInfo.getSeats(), newCarInfo.getModel(),
				newCarInfo.getColour());
		switch (sg.getRole()) {
		case DRIVER:
			sg.getStudentCar().setCarInfo(carInfo);
			ourDb.editCarInfoByUserID(sg.getStudentCar().getUserID(), newCarInfo);
			break;
		case STUDENT:
			StudentCar sCar = new StudentCar(sg.getStudent(), 0, carInfo);
			ourDb.addCar(sCar);
			sg.setRole(Role.DRIVER);
			sg.setStudent(null);
			sg.setStudentCar(sCar);
			break;
		default:
			throw new NoRoleFound();
		}

	}

	public void removeCar(UserBean user) throws DatabaseException, InvalidInputException {
		ourDb.removeCarByUserID(user.getUserID());
		
		sg.setStudent(new Student(ourDb.loadStudentCarByUserID(user.getUserID())));
		sg.setRole(Role.STUDENT);
		sg.setStudentCar(null);
	}
}
