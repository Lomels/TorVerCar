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
import java.util.logging.Level;
import java.util.logging.Logger;

import logic.bean.LiftBean;
import logic.bean.UserBean;
import logic.controller.email.SendEmail;
import logic.controller.exception.ApiNotReachableException;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.controller.exception.InvalidStateException;
import logic.controller.exception.NoLiftAvailable;
import logic.controller.maps.AdapterMapsApi;
import logic.controller.maps.MapsApi;
import logic.controller.maps.RoutingApi;
import logic.controller.maps.RoutingHereAPI;
import logic.model.Lift;
import logic.model.LiftMatchResult;
import logic.model.Position;
import logic.model.Route;
import logic.model.Student;
import logic.model.UnorderedLift;
import logic.view.mysql.MySqlDAO;

public class LiftController {

	private static final Logger LOGGER = Logger.getLogger(LiftController.class.getCanonicalName());

	private static final Integer MINUTES_OF_MARGIN = 10;
	private static final Integer HOURS_OF_MARGIN = 6;
	private static final Integer MAX_LIFTS_LISTED = 100;

	private MySqlDAO ourDb = new MySqlDAO();

	RoutingApi routingApi = RoutingHereAPI.getInstance();

	// String startDateTimeString, Integer maxDuration, String note, StudentCar
	// driver,
	// Position pickUp, Position dropOff

	public void createLift(LiftBean liftBean)
			throws InvalidInputException, DatabaseException, InvalidStateException, ApiNotReachableException {
		LocalDateTime startDateTime = liftBean.getStartDateTime();
		Route route = routingApi.startToStop(liftBean.getStartPos(), liftBean.getStopPos());
		Integer maxDuration = liftBean.getMaxDuration();

		if (maxDuration < route.getTotalDuration()) {
			// If maxDuration is less than route Duration
			String message = String.format("maxDuration: %d is less than route total duration: %d.", maxDuration,
					route.getTotalDuration());
			throw new InvalidStateException(message);
		}

		List<Lift> driverLifts = ourDb.listLiftsByDriverID(liftBean.getDriver().getUserID());

		// If driver already has offered a Lift in the same time
		for (Lift lift : driverLifts) {
			if (liftIntersect(lift, route, startDateTime)) {
				String message = String.format("Lift: %d collision with new one starting: %s and duration: %d.",
						lift.getLiftID(), startDateTime, route.getTotalDuration());
				throw new InvalidStateException(message);
			}
		}
		Lift lift = new Lift(null, startDateTime, maxDuration, liftBean.getNote(), liftBean.getDriver(), null, route);
		ourDb.saveLift(lift);
	}

	// A lift is concluded if is rated from all the passengers and its stopDateTime
	// is before now
	public boolean isConcluded(Lift lift) throws DatabaseException {
		boolean allRated = ourDb.isRatedFromAllPassengers(lift);
		return allRated && lift.getStopDateTime().isBefore(LocalDateTime.now());
	}

	public void deleteLift(Lift lift) throws DatabaseException {
		if (!lift.getPassengers().isEmpty())
			notifyPassengers(lift);
		ourDb.deleteLiftByID(lift.getLiftID());
	}

	public void deleteLiftIfConcluded(Lift lift) throws DatabaseException {
		if (this.isConcluded(lift)) {
			ourDb.deleteLiftByID(lift.getLiftID());
		}
	}

	public void notifyPassengers(Lift lift) throws DatabaseException {
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

	public List<Lift> checkCompletedLift(UserBean user) throws DatabaseException, InvalidInputException {
		List<Lift> fullList = ourDb.listUnratedLiftsByPassengerID(user.getUserID());
		List<Lift> completed = new ArrayList<>();
		for (Lift l : fullList) {
			if (l.getStopDateTime().isBefore(LocalDateTime.now()))
				completed.add(l);
		}

		// TODO: aggiornare lift nel DB per non farli ricomparire nella lista una volta
		// dato il rating

		return completed;
	}

	public List<String> loadNotifications(UserBean user) throws DatabaseException {
		return ourDb.loadNotificationsByUserID(user.getUserID());
	}

	public void matchLiftStartingAfter(LiftBean liftBean, Integer initIndex, LiftMatchListener listener)
			throws NoLiftAvailable, DatabaseException, InvalidInputException {
		// Get all the lifts starting in
		// [startDateTime - MINUTES_OF_MARGIN, startDateTime + HOURS_OF_MARGIN]
		LocalDateTime intervalStartDateTime = liftBean.getStartDateTime().minusMinutes(MINUTES_OF_MARGIN);
		LocalDateTime intervalStopDateTime = liftBean.getStartDateTime().plusHours(HOURS_OF_MARGIN);
		List<Lift> possibleLifts = this.ourDb.listAvailableLiftStartingWithinIntervalDateTime(intervalStartDateTime,
				intervalStopDateTime);

		if (possibleLifts.isEmpty())
			throw new NoLiftAvailable(
					String.format("No lift available starting after: %s.", liftBean.getStartDateTime()));
		
		List<Position> stops = new ArrayList<>();
		stops.add(liftBean.getStartPos());
		stops.add(liftBean.getStopPos());

		
		// Launch thread for computing
		LiftThread thread = new LiftThread(possibleLifts, stops, initIndex);
		this.launchThread(thread, listener);
	}

	public void matchLiftStoppingBefore(LiftBean liftBean, Integer initIndex, LiftMatchListener listener)
			throws NoLiftAvailable, DatabaseException, InvalidInputException {
		// Get all the lifts starting in
		// [stopDateTime - HOURS_OF_MARGIN, stopDateTime + MINUTES_OF_MARGIN]
		LocalDateTime intervalStopDateTime = liftBean.getStartDateTime().plusMinutes(MINUTES_OF_MARGIN);
		LocalDateTime intervalStartDateTime = liftBean.getStartDateTime().minusHours(HOURS_OF_MARGIN);
		List<Lift> possibleLifts = this.ourDb.listAvailableLiftStartingWithinIntervalDateTime(intervalStartDateTime,
				intervalStopDateTime);

		if (possibleLifts.isEmpty())
			throw new NoLiftAvailable(
					String.format("No lift available stopping before: %s.", liftBean.getStartDateTime()));

		List<Position> stops = new ArrayList<>();
		stops.add(liftBean.getStartPos());
		stops.add(liftBean.getStopPos());

		// Launch thread for computing
		LiftThread thread = new LiftThread(possibleLifts, stops, initIndex);
		thread.setStopDateTime(intervalStopDateTime);
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
		} catch (InterruptedException e) {
			LOGGER.severe(e.toString());
			Thread.currentThread().interrupt();
		} catch (ExecutionException e) {
			LOGGER.log(Level.SEVERE, "Exception found", e);
			listener.onThreadEnd(new ArrayList<>());
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
		public List<LiftMatchResult> call() throws NoLiftAvailable {
			return this.matchLifts();
		}

		public List<LiftMatchResult> matchLifts() throws NoLiftAvailable {
			Set<UnorderedLift> unorderedLifts = new TreeSet<>();

			// For cycle that stops once it reaches the end of possibileLifts or after
			// MAX_LIFTS_LISTED iterations
			for (Integer index = initIndex; (index < possibleLifts.size()) && (index < MAX_LIFTS_LISTED); index++) {

				Lift possibleLift = possibleLifts.get(index);

				Integer currentMaxDuration = possibleLift.getMaxDuration();
				Route currentRoute = possibleLift.getRoute();
				Integer currentDuration = currentRoute.getTotalDuration();

				// Add all the possible routes and order them
				try {
					// Compute the route passing for the positions given in stops
					Route newRoute = this.maps.addInternalRoute(currentRoute, stops);
					// If the newRoute has a duration less than the maxDuration, it is considered a
					// match
					if (newRoute.getTotalDuration() <= currentMaxDuration) {
						possibleLift.setRoute(newRoute);
						if (this.isBeforeStopDateTime(possibleLift)) {
							Integer deltaDuration = newRoute.getTotalDuration() - currentDuration;
							// id is sequential number in order of receiving by the DB
							Integer id = index - initIndex;
							unorderedLifts.add(new UnorderedLift(possibleLift, deltaDuration, id, MAX_LIFTS_LISTED));
						}
					}
				} catch (InvalidInputException | ApiNotReachableException e) {
					LOGGER.log(Level.SEVERE, "Exception", e);
				}
			}

			// Ordered list of routes by smaller variation of duration
			for (UnorderedLift ur : unorderedLifts) {
				Lift lift = ur.getLift();
				this.addLiftToLiftMatch(lift);
			}

			if (results.isEmpty())
				throw new NoLiftAvailable("No compatible lifts found.");

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
			LiftMatchResult result = null;
			try {
				result = new LiftMatchResult(lift, relativeStartDateTime, relativeStopDateTime);
			} catch (InvalidInputException e) {
				LOGGER.fine(e.toString());
			}
			this.results.add(result);
		}
	}

	public void flushNotification(UserBean user) throws DatabaseException {
		ourDb.removeNotificationsByUserID(user.getUserID());
	}

	private boolean liftIntersect(Lift alreadyLift, Route newRoute, LocalDateTime newStartDateTime) {
		boolean stopsBefore = alreadyLift.getStopDateTime().isBefore(newStartDateTime);
		boolean startAfter = alreadyLift.getStartDateTime()
				.isAfter(newStartDateTime.plusMinutes(newRoute.getTotalDuration()));
		return !(stopsBefore || startAfter);
	}

	public List<Lift> loadOfferedLift(UserBean user) throws DatabaseException, InvalidInputException {
		return ourDb.listLiftsByDriverID(user.getUserID());
	}

	public List<Lift> loadBookedLift(UserBean user) throws DatabaseException, InvalidInputException {
		return ourDb.listLiftsByPassengerID(user.getUserID());
	}
}
