package test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import logic.controller.exception.ApiNotReachableException;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.controller.maps.AdapterMapsApi;
import logic.controller.maps.MapsApi;
import logic.model.Lift;
import logic.model.Position;
import logic.model.Route;
import logic.model.Student;
import logic.model.StudentCar;
import logic.view.mysql.MySqlDAO;

public class LiftPersitenceTest {

	private void info(String name, Object object) {
		Logger.getGlobal().info(String.format("%s is: %s\n", name, object.toString()));
	}

	private void info(String message) {
		Logger.getGlobal().info(message + "\n");
	}

	public void saveLiftWithoutPassengers() throws InvalidInputException, DatabaseException {

		MySqlDAO dao = new MySqlDAO();

		String fromData = "{\"duration\":33,\"distance\":27083,\"stops\":[{\"score\":10.2996511459,\"address\":\"Via Prenestina Nuova, 51, 00036 Palestrina\",\"lon\":12.88611,\"lat\":41.83322},{\"score\":8.6688928604,\"address\":\"Via del Politecnico, 00133 Roma\",\"lon\":12.62165,\"lat\":41.85573}]}";

		Integer liftID = null;
		LocalDateTime startTime = LocalDateTime.now();
		Integer maxDuration = 90;
		String note = "Oh vita, oh vita";
		StudentCar driver = dao.loadStudentCarByUserID("0241118");
		List<Student> passengers = null;
		Route route = Route.JSONdecode(new JSONObject(fromData));

		Lift lift = new Lift(liftID, startTime, maxDuration, note, driver, passengers, route);

		info("Lift", lift);

		dao.saveLift(lift);
	}

	@Test
	public void loadLift() throws JSONException, DatabaseException, InvalidInputException {
		
		MySqlDAO dao = new MySqlDAO();
		
		Lift lift = dao.loadLiftbyLiftID(3);
		
		info("lift", lift);
		
	}
}
