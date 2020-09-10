package test;

import java.time.LocalDateTime;

import org.junit.Test;

import logic.controller.exception.ApiNotReachableException;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.model.CarInfo;
import logic.model.Lift;
import logic.model.Position;
import logic.model.Route;
import logic.model.Student;
import logic.model.StudentCar;
import test.utilities.TestUtilities;

public class DemoDatabase extends TestUtilities {

	@Test
	public void demoDatabase()
			throws DatabaseException, InvalidInputException, ApiNotReachableException {
		
		String[] drivers = { "Emmett_Brown_DeLorean_Silver", "David_Hasselhoff_KIT_Black",
				"Lewis_Hamilton_Mercedes_Grey", "Sebastian_Vettel_Ferrari_Red", "Fernando_Alonso_Renault_Blue",
				"Dominic_Toretto_Dodge Charger_Black", "James_Bond_Aston Martin_Silver" };

		String[] students = { "Micheal_Jordan", "Muhammad_Ali", "Cristiano_Ronaldo", "Serena_Williams", "Usain_Bolt",
				"Francesco_Totti" };

		String[] lifts = { "0_from_Palestrina", "1_from_Zagarolo", "2_from_Frascati", "3_from_Frosinone",
				"4_to_Palestrina", "5_to_San Cesareo", "6_to_Tivoli" };

		emptyDB();
		Integer lastInsertedID = 0;

		// Insert studentCar
		String code = "aaaAAA123@";
		String phone = "3331112223";

		for (int i = 0; i < drivers.length; i++) {
			String userID = generateUserID(lastInsertedID++);
			String name = drivers[i].split("_")[0];
			String surname = drivers[i].split("_")[1];
			String email = name + "." + surname + "@torvercar.it";
			Student student = new Student(userID, code, email, name, surname, phone);
			String model = drivers[i].split("_")[2];
			String color = drivers[i].split("_")[3];
			CarInfo carInfo = new CarInfo(generatePlate(lastInsertedID - 1), (i / 2) + 1, model, color);
			Integer rating = (-drivers.length / 2 + i);
			StudentCar studentCar = new StudentCar(student, rating, carInfo);
			dao.addStudentCar(studentCar);
		}

		// Insert Student
		for (int i = 0; i < students.length; i++) {
			String userID = generateUserID(lastInsertedID++);
			String name = students[i].split("_")[0];
			String surname = students[i].split("_")[1];
			String email = name + "." + surname + "@torvercar.it";
			Student student = new Student(userID, code, email, name, surname, phone);
			dao.addStudent(student);
		}

		// Insert lifts
		LocalDateTime startDateTimeGeneral = LocalDateTime.parse("2020-09-17T07:00");
		Integer hoursForReturn = 4;
		Double totalDurationMultiplier = 2.0;
		Position uniPosition = maps.addrToPos("Via del Politecnico 1, Roma").get(0);

		for (int i = 0; i < lifts.length; i++) {
			String ctrl = lifts[i].split("_")[1];
			LocalDateTime startDateTime = null;
			String startAddress = lifts[i].split("_")[2];
			Position driverPosition = maps.addrToPos(startAddress).get(0);
			Route route = null;
			switch (ctrl) {
			case "from":
				startDateTime = startDateTimeGeneral.plusMinutes(i * 10);
				route = maps.startToStop(driverPosition, uniPosition);
				break;
			case "to":
				startDateTime = startDateTimeGeneral.plusMinutes(i * 10).plusHours(hoursForReturn);
				route = maps.startToStop(uniPosition, driverPosition);
				break;
			default:
				break;
			}
			assert (route != null);
			int maxDuration = (int) (route.getTotalDuration() * totalDurationMultiplier);
			String userID = generateUserID(Integer.valueOf(lifts[i].split("_")[0]));
			StudentCar driver = dao.loadStudentCarByUserID(userID);
			String note = driver.getFullName() + " has gently offered this lift for you, please be kind.";
			Lift lift = new Lift(null, startDateTime, maxDuration, note, driver, null, route);
			dao.saveLift(lift);
		}

	}

//	@Test
	public void empty() throws DatabaseException {
		emptyDB();
	}

}
