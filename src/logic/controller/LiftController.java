package logic.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.controller.maps.AdapterMapsApi;
import logic.controller.maps.MapsApi;
import logic.controller.maps.RoutingApi;
import logic.controller.maps.RoutingHereAPI;
import logic.model.Lift;
import logic.model.LiftMatchResult;
import logic.model.Position;
import logic.model.Route;
import logic.model.Student;
import logic.model.StudentCar;
import logic.utilities.MyLogger;
import logic.view.mysql.MySqlDAO;

public class LiftController {

	private static final Integer MINUTES_OF_MARGIN = 10;
	private static final Integer MAX_LIFTS_LISTED = 100;

	private static final boolean LOG_OLD = false;
	private static final boolean DELETE_OLD = false;

	private MySqlDAO ourDb = new MySqlDAO();

	RoutingApi routingApi = RoutingHereAPI.getInstance();

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

		@Override
		public boolean equals(Object obj) {
			return super.equals(obj);
		}

		@Override
		public int hashCode() {
			return super.hashCode();
		}

	}

	public Lift createLift(String startDateTimeString, Integer maxDuration, String note, StudentCar driver,
			Position pickUp, Position dropOff) throws InvalidInputException, DatabaseException {
		LocalDateTime startDateTime = LocalDateTime.parse(startDateTimeString, DateTimeFormatter.ISO_DATE_TIME);
		Route route = routingApi.startToStop(pickUp, dropOff);
		Lift lift = new Lift(null, startDateTime, maxDuration, note, driver, null, route);
		ourDb.saveLift(lift);

		return lift;
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

	public List<Lift> checkCompletedLift(String userID) {
		List<Lift> fullList = ourDb.listLiftsByPassengerID(userID);
		List<Lift> completed = new ArrayList<>();
		for (Lift l : fullList) {
			if (l.getStopDateTime().isBefore(LocalDateTime.now()))
				completed.add(l);
		}

		// TODO: aggiornare lift nel DB per non farli ricomparire nella lista una volta
		// dato il rating
		
		return completed;
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
		List<Lift> possibleLifts = this.ourDb.listAvailableLiftStartingBeforeDateTime(marginStopDateTime);

		// Launch thread for computing
		LiftThread thread = new LiftThread(possibleLifts, stops, initIndex);
		thread.setStopDateTime(marginStopDateTime);
		this.launchThread(thread, listener);
	}

	private void launchThread(LiftThread thread, LiftMatchListener listener) {
		List<LiftMatchResult> matchedLifts = null;

		ExecutorService executorService = Executors.newCachedThreadPool();

		Future<List<LiftMatchResult>> futureCall = executorService.submit(thread);

		while (!futureCall.isDone()) {
			listener.onThreadRunning();
		}

		try {
			matchedLifts = futureCall.get();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		listener.onThreadEnd(matchedLifts);
	}

	protected class LiftThread implements Callable<List<LiftMatchResult>> {

		private List<Lift> possibleLifts;
		private List<Position> stops;
		private Integer initIndex;
		private LocalDateTime stopDateTime;
		private List<LiftMatchResult> results = new ArrayList<>();

		MapsApi maps = AdapterMapsApi.getInstance();

		public LiftThread(List<Lift> possibleLifts, List<Position> stops, Integer initIndex) {
			this.possibleLifts = possibleLifts;
			this.stops = stops;
			this.initIndex = initIndex;
		}

		public void setStopDateTime(LocalDateTime stopDateTime) {
			this.stopDateTime = stopDateTime;
		}

		@Override
		public List<LiftMatchResult> call() {
			List<LiftMatchResult> matchedLifts = this.matchLifts();
			return matchedLifts;
		}

		public List<LiftMatchResult> matchLifts() {
			Set<UnorderedLift> unorderedLifts = new TreeSet<>();

			// For cycle that stops once it reaches the end of possibileLifts or after
			// MAX_LIFTS_LISTED iterations
			for (Integer index = initIndex; (index < possibleLifts.size()) && (index < MAX_LIFTS_LISTED); index++) {

				Lift possibleLift = possibleLifts.get(index);

				// Check if the lift starts before now, which means that is old
				if (possibleLift.getStartDateTime().isBefore(LocalDateTime.now())) {
					if (LOG_OLD) {
						MyLogger.info("Lift #" + possibleLift.getLiftID() + " is old.");
						if (DELETE_OLD)
							ourDb.deleteLiftByID(possibleLift.getLiftID());
						continue;
					}
				}

				Integer currentMaxDuration = possibleLift.getMaxDuration();
				Route currentRoute = possibleLift.getRoute();
				Integer currentDuration = currentRoute.getDuration();

				// Add all the possible routes and order them
				try {
					// Compute the route passing for the positions given in stops
					Route newRoute = this.maps.addInternalRoute(currentRoute, stops);
					// If the newRoute has a duration less than the maxDuration, it is considered a
					// match
					if (newRoute.getDuration() <= currentMaxDuration) {
						possibleLift.setRoute(newRoute);
						if (this.isBeforeStopDateTime(possibleLift)) {
							Integer deltaDuration = newRoute.getDuration() - currentDuration;
							// id is sequential number in order of receiving by the DB
							Integer id = index - initIndex;
							unorderedLifts.add(new UnorderedLift(possibleLift, deltaDuration, id));
						}
					}
				} catch (InvalidInputException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// Ordered list of routes by smaller variation of duration
			for (UnorderedLift ur : unorderedLifts) {
				Lift lift = ur.getLift();
				this.addLiftToLiftMatch(lift);
			}

			return results;
		}

		private boolean isBeforeStopDateTime(Lift possibleLift) {
			// If stopDateTime is not set, then always return true
			if (this.stopDateTime == null)
				return true;

			// If stopDateTime is set, then
			// Get the last stop of the passenger
			Position lastStop = this.stops.get(this.stops.size() - 1);

			// Get the duration until the last stop, then add it to stopDateTime
			Route route = possibleLift.getRoute();
			Integer deltaDeltaStopDateTime = route.getDurationUntilPosition(lastStop);
			LocalDateTime stopDateTimeOnLastStop = possibleLift.getStartDateTime().plusMinutes(deltaDeltaStopDateTime);
			return stopDateTimeOnLastStop.isBefore(this.stopDateTime);
		}

		private void addLiftToLiftMatch(Lift lift) {
			Integer deltaStartDateTime = lift.getRoute().getDurationUntilPosition(stops.get(0));
			Integer deltaStopDateTime = lift.getRoute().getDurationUntilPosition(stops.get(stops.size() - 1));
			LocalDateTime relativeStartDateTime = lift.getStartDateTime().plusMinutes(deltaStartDateTime);
			LocalDateTime relativeStopDateTime = lift.getStartDateTime().plusMinutes(deltaStopDateTime);
			LiftMatchResult result = new LiftMatchResult(lift, relativeStartDateTime, relativeStopDateTime);
			this.results.add(result);
		}
	}
}
