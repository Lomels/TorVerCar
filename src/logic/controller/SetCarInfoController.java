package logic.controller;

import logic.bean.CarInfoBean;
import logic.controller.exception.InvalidInputException;
import logic.entity.CarInfo;
import logic.entity.Student;
import logic.entity.StudentCar;


public class SetCarInfoController {

	private StudentCarBuilder builder;
	
	
	public StudentCar addCar(CarInfoBean carInfo, Student student) throws InvalidInputException {
		CarInfo car = new CarInfo(carInfo.getPlate(), carInfo.getSeats(),
				carInfo.getModel(), carInfo.getColour());
		
		builder = new StudentCarBuilder(student);
		builder.carInfo(car);
		
		StudentCar studentCar = builder.build();
		
		return studentCar;
	}
	
	public void editCar(CarInfoBean newCarInfo, StudentCar studentCar) throws InvalidInputException {
		CarInfo carInfo = new CarInfo(newCarInfo.getPlate(), newCarInfo.getSeats(),
				newCarInfo.getModel(), newCarInfo.getColour());
		studentCar.setCarInfo(carInfo);
	}
	
	public void removeCar(StudentCar studentCar) {
		//TODO Implementare removeCar
	}
}
