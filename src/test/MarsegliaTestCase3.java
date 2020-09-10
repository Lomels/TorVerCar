package test;

import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;

import logic.bean.LiftBean;
import logic.controller.LiftController;
import logic.controller.LiftMatchListener;
import logic.controller.exception.ApiNotReachableException;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.controller.exception.NoLiftAvailable;
import logic.controller.maps.CompleteMapsApi;
import logic.controller.maps.MapsApi;
import logic.model.CarInfo;
import logic.model.Lift;
import logic.model.LiftMatchResult;
import logic.model.Position;
import logic.model.Route;
import logic.model.Student;
import logic.model.StudentCar;
import logic.view.mysql.MySqlDAO;

public class MarsegliaTestCase3 implements LiftMatchListener {

	private Lift lift;

	@Test
	public void liftMatchTest()
			throws DatabaseException, InvalidInputException, ApiNotReachableException, NoLiftAvailable {
		MapsApi mapsApi = CompleteMapsApi.getInstance();
		MySqlDAO ourDB = new MySqlDAO();
		ourDB.emptyDB();

		String driverID = "1100000";
		Student student = new Student(driverID, "aaaAA123@", "driver@torvercar.it", "The", "Stig", "1234567890");
		CarInfo carInfo = new CarInfo("TV000CR", 4, "Good car", "Good color");
		StudentCar studentCar = new StudentCar(student, 0, carInfo);
		ourDB.addStudentCar(studentCar);

		LocalDateTime startDateTime = LocalDateTime.now().plusHours(2);

		Position startPosition = mapsApi.addrToPos("Via Prenestina Nuova 51, Palestina").get(0);
		Position stopPositionPosition = mapsApi.addrToPos("Viale del Politecnico 1, Roma").get(0);
		Route route = mapsApi.startToStop(startPosition, stopPositionPosition);

		Integer maxDuration = route.getTotalDuration() * 10;

		String note = "This is a test drive.";

		StudentCar driver = ourDB.loadStudentCarByUserID(driverID);

		Lift liftToSave = new Lift(null, startDateTime, maxDuration, note, driver, null, route);

		ourDB.saveLift(liftToSave);

		this.lift = ourDB.getLastInsertedLift();

		LiftBean liftBean = new LiftBean();
		LocalDateTime matchDateTime = LocalDateTime.now();
		Position matchStartPosition = mapsApi.addrToPos("Zagarolo").get(0);
		Position matchStopPosition = mapsApi.addrToPos("Tivoli").get(0);
		liftBean.setStartDateTime(matchDateTime);
		liftBean.setStartPos(matchStartPosition);
		liftBean.setStopPos(matchStopPosition);

		LiftController liftController = new LiftController();
		liftController.matchLiftStartingAfter(liftBean, 0, this);
	}

	@Override
	public void onThreadEnd(List<LiftMatchResult> results) {
		LiftMatchResult firstResult = results.get(0);
		assertTrue(this.lift.getLiftID() == firstResult.getLift().getLiftID());
	}

	@Override
	public void onThreadRunning() {
		// Do nothing
	}
}
