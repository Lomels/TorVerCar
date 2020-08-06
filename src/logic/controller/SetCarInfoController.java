package logic.controller;

import logic.bean.CarInfoBean;
import logic.bean.UserBeanSingleton;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.model.CarInfo;
import logic.model.Student;
import logic.model.StudentCar;
import logic.model.StudentCarSingleton;
import logic.model.UserSingleton;
import logic.view.OurStudentDatabase;


public class SetCarInfoController {
	 UserSingleton sg = UserSingleton.getInstance();
	 UserBeanSingleton sgBean = UserBeanSingleton.getInstance();
	 StudentCarSingleton sCarSg = StudentCarSingleton.getInstance();
	 private OurStudentDatabase ourDb;
	
	
	public StudentCar addCar(CarInfoBean carInfo) throws InvalidInputException, DatabaseException {
		CarInfo car = new CarInfo(carInfo.getPlate(), carInfo.getSeats(),
				carInfo.getModel(), carInfo.getColour());
		
		Student student = StudentBuilder.newBuilder(sgBean.getUserBean().getUserID())
				.fullname(sgBean.getUserBean().getName(), sgBean.getUserBean().getSurname())
				.email(sgBean.getUserBean().getEmail())
				.password(sgBean.getUserBean().getPassword())
				.phone(sgBean.getUserBean().getPhone())
				.build();
		
		StudentCarBuilder builder = new StudentCarBuilder(student);
		builder.carInfo(car);
		
		return builder.build();
	}
	
	public void editCar(CarInfoBean newCarInfo, StudentCar studentCar) throws InvalidInputException {
		CarInfo carInfo = new CarInfo(newCarInfo.getPlate(), newCarInfo.getSeats(),
				newCarInfo.getModel(), newCarInfo.getColour());
		studentCar.setCarInfo(carInfo);
		sCarSg.setUser(studentCar);
	}
	
	public void removeCar(StudentCar studentCar) {
		//TODO Implementare removeCar 	
	}
}
