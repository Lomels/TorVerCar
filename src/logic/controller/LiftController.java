package logic.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import logic.controller.email.SendEmail;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.controller.maps.AdapterMapsApi;
import logic.controller.maps.MapsApi;
import logic.controller.maps.RoutingApi;
import logic.controller.maps.RoutingHereAPI;
import logic.model.Lift;
import logic.model.Position;
import logic.model.Route;
import logic.model.Student;
import logic.model.StudentCar;
import logic.view.mysql.MySqlDAO;

public class LiftController {

	private final static Integer MINUTES_OF_MARGIN = 10;
	private final static Integer MAX_LIFTS_LISTED = 100;
	private MySqlDAO ourDb = new MySqlDAO();
	private Integer autoID = 0;

	RoutingApi routingApi = RoutingHereAPI.getInstance();
	
	public Lift createLift(Integer liftID, String startDateTimeString, Integer maxDuration, String note, StudentCar driver,
			List<Student> passengers, Position pickUp, Position dropOff) throws InvalidInputException, DatabaseException {
		LocalDateTime startDateTime = LocalDateTime.parse(startDateTimeString);
		Route route = routingApi.startToStop(pickUp, dropOff);
		Lift lift = new Lift(null, startDateTime, maxDuration, note, driver, null, route);
		ourDb.saveLift(lift);

		return lift;
	}

	private class UnorderedLift implements Comparable<UnorderedLift> {

		protected Lift lift;
		protected Integer deltaDuration;
		protected Integer comparator;

		public UnorderedLift(Lift lift, Integer deltaDuration) {
			this.lift = lift;
			this.deltaDuration = deltaDuration;
			this.comparator = this.deltaDuration * MAX_LIFTS_LISTED + autoID++;
		}

		@Override
		public int compareTo(UnorderedLift o) {
			return this.comparator - o.comparator;
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
		List<Lift> possibleLifts = dao
				.listAvailableLiftStartingAfterDateTime(startDateTime.minusMinutes(MINUTES_OF_MARGIN));

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
