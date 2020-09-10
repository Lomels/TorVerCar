package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import logic.bean.LiftBean;
import logic.controller.LiftController;
import logic.controller.exception.ApiNotReachableException;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.controller.exception.InvalidStateException;
import logic.model.Lift;
import logic.model.Route;
import logic.model.StudentCar;
import test.utilities.TestUtilities;

/* GIULIA DESIDERI */

class OfferTest extends TestUtilities {

	private String beanToString(LiftBean lift) {
		return "Driver: " + lift.getDriver().toString() + ", From: " + lift.getStartPos() + ", To: " + lift.getStopPos()
				+ ", Depart at: " + lift.getStartDateTime().format(DateTimeFormatter.ISO_DATE_TIME) + ", MaxDuration: " + lift.getMaxDuration()
				+ ", Notes: " + lift.getNote();
	}

	@Test
	void createLiftTest()
			throws DatabaseException, InvalidInputException, InvalidStateException, ApiNotReachableException {
		LiftController controller = new LiftController();

		StudentCar driver = dao.loadStudentCarByUserID("0245061");
		LiftBean liftBean = new LiftBean();
		liftBean.setDriver(driver);
		LocalDateTime startDateTime = LocalDateTime.now().withNano(0);
		liftBean.setStartDateTime(startDateTime);		
		
		Route route = Route.jsonDecode(new JSONObject(R_MARCO_UNI));
		liftBean.setStartPos(route.getStop(0));
		liftBean.setStopPos(route.getStop(1));
		liftBean.setMaxDuration(100);
		liftBean.setNote("offerTest");

		controller.createLift(liftBean);
		Lift lastLift = dao.getLastInsertedLift();
		LiftBean lastBean = new LiftBean();
		lastBean.setDriver(lastLift.getDriver());
		lastBean.setStartDateTime(lastLift.getStartDateTime());
		lastBean.setStartPos(lastLift.getRoute().getStop(0));
		lastBean.setStopPos(lastLift.getRoute().getStop(1));
		lastBean.setMaxDuration(lastLift.getMaxDuration());
		lastBean.setNote(lastLift.getNote());

		assertEquals(beanToString(liftBean), beanToString(lastBean));
	}

}
