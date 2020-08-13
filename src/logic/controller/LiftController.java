package logic.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import logic.controller.email.SendEmail;
import logic.controller.exception.InvalidInputException;
import logic.controller.maps.AdapterMapsApi;
import logic.controller.maps.MapsApi;
import logic.model.Lift;
import logic.model.Position;
import logic.model.Route;
import logic.model.Student;
import logic.model.UserSingleton;
import logic.view.mysql.MySqlDAO;

public class LiftController {

	private final static Integer MAX_LIFTS_LISTED = 10;

	private class UnorderedLift implements Comparable<UnorderedLift> {

		protected Lift lift;
		protected Integer deltaDuration;

		public UnorderedLift(Lift lift, Integer deltaDuration) {
			this.lift = lift;
			this.deltaDuration = deltaDuration;
		}

		@Override
		public int compareTo(UnorderedLift o) {
			return this.deltaDuration - o.deltaDuration;
		}

		public Lift getLift() {
			return this.lift;
		}

	}

	public List<Lift> matchLiftStartingAfter(LocalDateTime startDateTime, List<Position> stops, Integer initIndex) {
		List<Lift> matchedLifts = new ArrayList<Lift>();

		Set<UnorderedLift> unorderedLifts = new TreeSet<>();
		MySqlDAO dao = new MySqlDAO();
		MapsApi maps = AdapterMapsApi.getInstance();

		// Get all the lifts starting after startDateTime
		List<Lift> possibleLifts = dao.listAvailableLiftStartingAfterDateTime(startDateTime);

		// For cycle that stops once it reaches the end of possibileLifts or after
		// MAX_LIFTS_LISTED iterations
		for (Integer index = initIndex; (index < possibleLifts.size()) && (index < MAX_LIFTS_LISTED); index++) {
			Lift possibileLift = possibleLifts.get(index);
			Integer currentMaxDuration = possibileLift.getMaxDuration();
			Route currentRoute = possibileLift.getRoute();
			Integer currentDuration = currentRoute.getDuration();

			// Add all the possibile routes and order them
			try {
				// Compute the route passing for the positions given in stops
				Route newRoute = maps.addInternalRoute(currentRoute, stops);
				// If the newRoute has a duration less than the maxDuration, it is considered a
				// match
				if (newRoute.getDuration() <= currentMaxDuration) {
					possibileLift.setRoute(newRoute);
					Integer deltaDuration = newRoute.getDuration() - currentDuration;
					unorderedLifts.add(new UnorderedLift(possibileLift, deltaDuration));
				}
			} catch (InvalidInputException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Ordered list of routes by smaller variation of duration
		for (UnorderedLift ur : unorderedLifts) {
			matchedLifts.add(ur.getLift());
		}

		return matchedLifts;
		
		// TODO: test
	}

public void deleteLift(Lift lift) {
		if (!lift.getPassengers().isEmpty())
			notifyPassengers(lift);
		ourDb.deleteLiftByID(lift.getLiftID());
	}

	public void notifyPassengers(Lift lift) {
		String subject = "You lift has been deleted!";
		String format = "The lift you booked for: %s, departing at: %s, has been deleted by the driver.";
		String message = String.format(format, lift.getRoute().getDropoffPosition().getAddress(),
				lift.getStartDateTime());

		for (Student student : lift.getPassengers()) {
			ourDb.addNotificationByUserID(student.getUserID(), message);

			String[] recipients = new String[] { student.getEmail() };
			new SendEmail().send(recipients, recipients, subject, message);
		}
	}

	public List<String> loadNotifications(String userID) {
		return ourDb.loadNotificationsByUserID(userID);
	}
	
	

}
