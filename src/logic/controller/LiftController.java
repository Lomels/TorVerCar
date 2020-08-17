package logic.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import logic.controller.email.SendEmail;
import logic.controller.exception.InvalidInputException;
import logic.controller.maps.AdapterMapsApi;
import logic.controller.maps.MapsApi;
import logic.model.Lift;
import logic.model.Position;
import logic.model.Route;
import logic.model.Student;
import logic.view.mysql.MySqlDAO;

public class LiftController {

	private static final Integer MINUTES_OF_MARGIN = 10;
	private static final Integer MAX_LIFTS_LISTED = 100;

	private MySqlDAO ourDb = new MySqlDAO();

	private class UnorderedLift implements Comparable<UnorderedLift> {

		protected Lift lift;
		protected Integer comparator;

		public UnorderedLift(Lift lift, Integer deltaDuration, Integer ID) {
			this.lift = lift;
			this.comparator = deltaDuration * MAX_LIFTS_LISTED + ID;
		}

		@Override
		public int compareTo(UnorderedLift o) {
			return this.comparator - o.comparator;
		}

		public Lift getLift() {
			return this.lift;
		}

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

	public void matchLiftStartingAfter(LocalDateTime startDateTime, List<Position> stops, Integer initIndex,
			LiftMatchListener listener) {
		// Get all the lifts starting after startDateTime with a margin
		LocalDateTime marginStartDateTime = startDateTime.minusMinutes(MINUTES_OF_MARGIN);
		List<Lift> possibleLifts = this.ourDb.listAvailableLiftStartingAfterDateTime(marginStartDateTime);

		// Launch thread for computing
		LiftThread thread = new LiftThread(possibleLifts, stops, initIndex);
		this.launchThread(thread, listener);
	}

	public void matchLiftStoppingBefore(LocalDateTime stopDateTime, List<Position> stops, Integer initIndex,
			LiftMatchListener listener) {
		// Get all the lifts starting after startDateTime with a margin
		LocalDateTime marginStopDateTime = stopDateTime.plusMinutes(MINUTES_OF_MARGIN);
		List<Lift> possibleLifts = this.ourDb.listAvailableLiftStoppingBeforeDateTime(marginStopDateTime);
		
		// Launch thread for computing
		LiftThread thread = new LiftThread(possibleLifts, stops, initIndex);
		thread.setStopDateTime(marginStopDateTime);
		this.launchThread(thread, listener);
	}

	private void launchThread(LiftThread thread, LiftMatchListener listener) {
		List<Lift> matchedLifts = null;

		ExecutorService executorService = Executors.newCachedThreadPool();

		Future<List<Lift>> futureCall = executorService.submit(thread);

		while (!futureCall.isDone()) {
			listener.onThreadRunning(matchedLifts);
		}

		try {
			matchedLifts = futureCall.get();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		listener.onThreadEnd(matchedLifts);
	}

	protected class LiftThread implements Callable<List<Lift>> {

		private List<Lift> possibleLifts;
		private List<Position> stops;
		private Integer initIndex;
		private LocalDateTime stopDateTime;

		public LiftThread(List<Lift> possibleLifts, List<Position> stops, Integer initIndex) {
			this.possibleLifts = possibleLifts;
			this.stops = stops;
			this.initIndex = initIndex;
		}

		public void setStopDateTime(LocalDateTime stopDateTime) {
			this.stopDateTime = stopDateTime;
		}

		@Override
		public List<Lift> call() {
			List<Lift> matchedLifts = matchLifts();
			return matchedLifts;
		}

		public List<Lift> matchLifts() {
			List<Lift> matchedLifts = new ArrayList<Lift>();

			Set<UnorderedLift> unorderedLifts = new TreeSet<>();
			MapsApi maps = AdapterMapsApi.getInstance();

			// For cycle that stops once it reaches the end of possibileLifts or after
			// MAX_LIFTS_LISTED iterations
			for (Integer index = initIndex; (index < possibleLifts.size()) && (index < MAX_LIFTS_LISTED); index++) {
				Lift possibileLift = possibleLifts.get(index);
				Integer currentMaxDuration = possibileLift.getMaxDuration();
				Route currentRoute = possibileLift.getRoute();
				Integer currentDuration = currentRoute.getDuration();

				// Add all the possible routes and order them
				try {
					// Compute the route passing for the positions given in stops
					Route newRoute = maps.addInternalRoute(currentRoute, stops);
					// If the newRoute has a duration less than the maxDuration, it is considered a
					// match
					if (newRoute.getDuration() <= currentMaxDuration) {
						possibileLift.setRoute(newRoute);
						if (this.stopDateTime == null || possibileLift.getStopDateTime().isBefore(this.stopDateTime)) {
							Integer deltaDuration = newRoute.getDuration() - currentDuration;
							Integer ID = index - initIndex;
							unorderedLifts.add(new UnorderedLift(possibileLift, deltaDuration, ID));
						}
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
		}
	}
}
