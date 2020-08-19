package test;

import org.junit.Test;

import logic.bean.CarInfoBean;
import logic.controller.SetCarInfoController;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.model.StudentCar;
import logic.utilities.MyLogger;
import logic.view.mysql.MySqlDAO;

public class StudentPersistenceTest extends TestUtilities{
	private SetCarInfoController carController = new SetCarInfoController();
	private MySqlDAO dao = new MySqlDAO();

	
//	@Test
	public void removeCar() throws DatabaseException, InvalidInputException {
		String userID = USER_ID+"2";
		MyLogger.info("Old user role", dao.loadRoleByUserID(userID));

		StudentCar studentCar = dao.loadStudentCarByUserID(userID);
		carController.removeCar(studentCar);
		
		MyLogger.info("New user role", dao.loadRoleByUserID(userID));
	}
	
	@Test
	public void addCar() throws InvalidInputException, DatabaseException {
		String userID = USER_ID+"0";
		String plate = PLATE_FORMAT+"000";
		
		CarInfoBean carInfo = new CarInfoBean();
		carInfo.setPlate(plate);
		carInfo.setColour(COLOR);
		carInfo.setModel(MODEL);
		carInfo.setSeats(SEATS);
		
		carController.addCar(carInfo);
		
		MyLogger.info("Expected Car", carInfo);
		MyLogger.info("Inserted Car", dao.loadStudentCarByUserID(userID).getCarInfo());
		
	}
}
