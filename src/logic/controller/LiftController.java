package logic.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import logic.model.Lift;
import logic.model.Route;
import logic.view.mysql.MySqlDAO;

public class LiftController {
	
	private final static Integer MAX_LIFTS_LISTED = 4;

	public List<Lift> matchLiftStartingAfter(Route route, LocalDateTime startDateTime, Integer initIndex) {
		List<Lift> result = new ArrayList<Lift>();
		MySqlDAO dao = new MySqlDAO();

		List<Lift> possibleLifts = dao.listAvailableLiftStartingAfterDateTime(startDateTime);

		if (possibleLifts.size() == 0)
			return result;
		
		
		
		return result;
	}
	
	

}
