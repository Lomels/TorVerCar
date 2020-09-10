package test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import logic.bean.LiftBean;
import logic.controller.LiftController;
import logic.controller.LiftMatchListener;
import logic.controller.exception.ApiNotReachableException;
import logic.controller.exception.InvalidInputException;
import logic.controller.exception.NoLiftAvailable;
import logic.model.LiftMatchResult;
import logic.model.Position;
import logic.model.Route;
import logic.utilities.MyLogger;
import test.utilities.TestRuntimeException;
import test.utilities.TestUtilities;

public class LiftMatchTest extends TestUtilities implements LiftMatchListener {

	private Route route;
	private boolean firstLog = true;

	public LiftMatchTest() throws ApiNotReachableException, InvalidInputException {
		List<Position> stops = new ArrayList<>();

		stops.add(maps.addrToPos(ADDR_MARCO).get(0));
		stops.add(maps.addrToPos(ADDR_UNI).get(0));

		this.route = maps.startToStop(stops);
	}

	private void setup() {
		populateDB();
		this.firstLog = true;
	}

	@Test
	public void matchLiftStartAfter() {
		this.setup();

		LiftController liftController = new LiftController();
		LocalDateTime startDateTime = LocalDateTime.parse("2020-08-15T19:45");
		LiftBean liftBean = new LiftBean();
		liftBean.setStartDateTime(startDateTime);
		liftBean.setStartPos(this.route.getStops().get(0));
		liftBean.setStartPos(this.route.getStops().get(1));
		assertDoesNotThrow(() -> liftController.matchLiftStartingAfter(liftBean, 0, this));
	}

	@Test
	public void matchLiftStartAfterNotExisting() {
		this.setup();
		LiftController liftController = new LiftController();
		LocalDateTime startDateTime = LocalDateTime.parse("2021-08-15T19:45");
		LiftBean liftBean = new LiftBean();
		liftBean.setStartDateTime(startDateTime);
		liftBean.setStartPos(this.route.getStops().get(0));
		liftBean.setStartPos(this.route.getStops().get(1));
		assertThrows(NoLiftAvailable.class, () -> liftController.matchLiftStartingAfter(liftBean, 0, this));
	}

	@Test
	public void matchLiftStopBeforeNotExisting() {
		this.setup();

		LiftController liftController = new LiftController();
		LocalDateTime stopDateTime = LocalDateTime.parse("2021-08-15T19:45");
		LiftBean liftBean = new LiftBean();
		liftBean.setStartDateTime(stopDateTime);
		liftBean.setStartPos(this.route.getStops().get(0));
		liftBean.setStartPos(this.route.getStops().get(1));
		assertThrows(NoLiftAvailable.class, () -> liftController.matchLiftStoppingBefore(liftBean, 0, this));
	}

	@Override
	public void onThreadEnd(List<LiftMatchResult> results) {
		if (results == null || results.isEmpty())
			throw new TestRuntimeException();
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
