package test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.controller.maps.AdapterMapsApi;
import logic.controller.maps.MapsApi;
import logic.model.Lift;
import logic.model.Route;
import logic.model.Student;
import logic.model.StudentCar;
import logic.view.mysql.MySqlDAO;

public class LiftMatchTest {

	MySqlDAO dao = new MySqlDAO();
	MapsApi maps = AdapterMapsApi.getInstance();

	private static final String MARCO_ID = "0241118";

	@Test
	public void liftMatch() throws DatabaseException, InvalidInputException {
		
		Integer liftID = null;
		LocalDateTime startDateTime = LocalDateTime.parse("2020-08-12T19:00:00");
		Integer maxDuration = 120;
		String note = "Fino a due ore";
		StudentCar driver = dao.loadStudentCarByUserID(MARCO_ID);
		List<Student> passengers = new ArrayList<Student>();
		
		// TODO
		Route route = null;
		
		Lift liftToSave = new Lift(liftID, startDateTime, maxDuration, note, driver, passengers, route);
	}

}
