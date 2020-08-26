package test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.junit.Test;

import logic.controller.LiftController;
import logic.controller.LiftMatchListener;
import logic.controller.exception.ApiNotReachableException;
import logic.controller.exception.InvalidInputException;
import logic.model.LiftMatchResult;
import logic.model.Position;
import logic.model.Route;
import logic.utilities.MyLogger;

public class NewLiftMatchTest extends TestUtilities implements LiftMatchListener {

	private Route route;
	private boolean firstLog = true;

	private void setup() throws InvalidInputException, ApiNotReachableException {
		populateDB();

		Route routeForStops = Route.jsonDecode(new JSONObject(R_MARCO_GIU_TIVOLI_UNI));
		List<Position> routeStops = routeForStops.getStops();

		List<Position> stops = new ArrayList<>();
//		stops.add(routeStops.get(1));
//		stops.add(routeStops.get(2));

		stops.add(maps.addrToPos("Via Folcarotonda 19, 00036 Palestrina").get(0));
		stops.add(maps.addrToPos("Via Cambridge, 00133 Roma").get(0));
		
		this.route = maps.startToStop(stops);
		MyLogger.info("Route of the passenger: " + this.route.toStringLong());
	}

//	@Test
	public void matchLiftStartAfter() throws InvalidInputException {
		try {
			this.setup();
		} catch (ApiNotReachableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LiftController liftController = new LiftController();
		LocalDateTime startDateTime = LocalDateTime.parse("2020-08-15T19:45");

		liftController.matchLiftStartingAfter(startDateTime, this.route.getStops(), 0, this);
	}

	@Test
	public void matchLiftStopBefore() throws InvalidInputException {
		try {
			this.setup();
		} catch (ApiNotReachableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LiftController liftController = new LiftController();
		LocalDateTime stopDateTime = LocalDateTime.parse("2020-08-15T19:45");

		liftController.matchLiftStoppingBefore(stopDateTime, this.route.getStops(), 0, this);
	}

	@Override
	public void onThreadEnd(List<LiftMatchResult> results) {
		if(results.isEmpty()) {
			MyLogger.info("No available lifts");
			return;
		}
		for (LiftMatchResult result : results) {
			MyLogger.info("Result", result);
		}

	}

	@Override
	public void onThreadRunning() {
		if (firstLog) {
			firstLog = !firstLog;
			MyLogger.info("Thread started running.");
		}
		// No operations
	}

}
