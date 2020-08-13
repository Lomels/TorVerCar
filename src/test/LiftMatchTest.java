package test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.Test;

import logic.controller.LiftController;
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
import logic.utilities.MyLogger;
import logic.view.mysql.MySqlDAO;

public class LiftMatchTest {

	MySqlDAO dao = new MySqlDAO();
	MapsApi maps = AdapterMapsApi.getInstance();

	private static final String MARCO_ID = "0241118";
	private static final String GIULIA_ID = "0245061";

	private static final Integer LIFT_TO_INSERT = 5;
	private static final Boolean RECOMPUTE = false;
	private static final Boolean INSERT = false;
	private static final boolean LIST = false;
	private static final boolean LOG_FINE = true;

	private static final MyLogger logger = new MyLogger(LOG_FINE);

	@Test
	public void liftMatch() throws DatabaseException, InvalidInputException, ApiNotReachableException {
		MySqlDAO dao = new MySqlDAO();

		Integer liftID = null;
		LocalDateTime startDateTime = LocalDateTime.parse("2020-08-13T19:00:00");
		Integer maxDuration;
		String note = "Test Lift for liftMatchTest #";
		StudentCar driver = dao.loadStudentCarByUserID(GIULIA_ID);
		List<Student> passengers = new ArrayList<Student>();
		Route route;

		Route routeShort = null, routeLong = null;

		// Route computing
		if (RECOMPUTE) {
			List<Position> stops = new ArrayList<Position>();
			String addrStart = "Via folcarotonda 19, Palestrina";
			Position posStart = maps.addrToPos(addrStart).get(0);
			logger.infoB("posStart", posStart);

			String addrStop = "Viale del Politecnico 1, Roma";
			Position posStop = maps.addrToPos(addrStop).get(0);
			logger.infoB("posStop", posStop);

			String addrMedStart = "Via prenestina nuova 51, Palestrina";
			Position posMedStart = maps.addrToPos(addrMedStart).get(0);
			logger.infoB("posMedStart", posMedStart);

			String addrMedLong = "Tivoli";
			Position posMedLong = maps.addrToPos(addrMedLong).get(0);
			logger.infoB("posStop", posMedLong);

			String addrMedShort = "Zagarolo";
			Position posMedShort = maps.addrToPos(addrMedShort).get(0);
			logger.infoB("posStop", posMedShort);

//			stops.add(posStart);
			stops.add(posMedStart);
			stops.add(posMedShort);
//			stops.add(posStop);

			route = maps.startToStop(stops);

			logger.infoB("recomputed route", route);
			logger.infoB("Route image:\n" + maps.viewFromRoute(route));
			logger.infoB("JSON:\n" + route.JSONencode().toString());

		} else {
			String jsonRoute = "{\"duration\":37,\"distance\":26762,\"stops\":[{\"score\":8.1408014297,\"address\":\"Via Folcarotonda, 19, 00036 Palestrina\",\"lon\":12.85975,\"lat\":41.83976},{\"score\":8.6688928604,\"address\":\"Via del Politecnico, 00133 Roma\",\"lon\":12.62165,\"lat\":41.85573}]}";
			route = Route.JSONdecode(new JSONObject(jsonRoute));

			String jsonShort = "{\"duration\":13,\"distance\":6566,\"stops\":[{\"score\":10.2996511459,\"address\":\"Via Prenestina Nuova, 51, 00036 Palestrina\",\"lon\":12.88611,\"lat\":41.83322},{\"score\":2.3917956352,\"address\":\"Zagarolo\",\"lon\":12.82922,\"lat\":41.83991}]}";
			routeShort = Route.JSONdecode(new JSONObject(jsonShort));

			String jsonLong = "{\"duration\":44,\"distance\":26111,\"stops\":[{\"score\":10.2996511459,\"address\":\"Via Prenestina Nuova, 51, 00036 Palestrina\",\"lon\":12.88611,\"lat\":41.83322},{\"score\":2.4017963409,\"address\":\"Tivoli\",\"lon\":12.79827,\"lat\":41.96358}]}";
			routeLong = Route.JSONdecode(new JSONObject(jsonLong));
		}
		maxDuration = (int) (route.getDuration() * 3);

		if (INSERT) {
			for (Integer i = 0; i < LIFT_TO_INSERT; i++) {
				Lift liftToSave = new Lift(liftID, startDateTime, maxDuration, note, driver, passengers, route);
				dao.saveLift(liftToSave);
			}
		}

		if (LIST)
			this.logLifts(dao.listLiftStartingAfterDateTime(startDateTime));

		if (!RECOMPUTE) {
			LiftController liftController = new LiftController();

			// Short route, no passengers on Lift
//			List<Lift> matchedLifts = 
			liftController.matchLiftStartingAfter(startDateTime, routeShort.getStops(), 0);

			// Long route, no passengers
			List<Lift> matchedLifts = liftController.matchLiftStartingAfter(startDateTime, routeLong.getStops(), 0);

			this.logLifts(matchedLifts);
		}

	}

	private void logLifts(List<Lift> lifts) {
		int i = 0;
		for (Lift lift : lifts) {
			MyLogger.info("Lift #" + i++, lift);
		}
	}

}
