package test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;

import logic.controller.RatingController;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.controller.exception.InvalidStateException;
import logic.model.Lift;
import logic.model.StudentCar;
import test.utilities.TestUtilities;

public class RatingControllerTest extends TestUtilities {

	@Test
	public void upvoteLiftNotDeleteTest() throws InvalidInputException, DatabaseException, InvalidStateException {
		populateDB();

		RatingController ratingController = new RatingController();
		Lift lift = getDummyLift();
		dao.saveLift(lift);
		dbModified();

		Lift liftFromDB = dao.getLastInsertedLift();
		Integer liftIDFromDB = liftFromDB.getLiftID();
		StudentCar driver = lift.getDriver();
		Integer oldRating = driver.getRating();

		String passenger1IDString = generateUserID(DRIVER_NUMBER + 1);
		String passenger2IDString = generateUserID(DRIVER_NUMBER + 2);

		dao.addPassengerByLiftIDAndUserID(liftIDFromDB, passenger1IDString);
		dao.addPassengerByLiftIDAndUserID(liftIDFromDB, passenger2IDString);

		ratingController.upvoteLift(passenger1IDString, liftIDFromDB, driver);

		Lift fromDBUpdatedLift = dao.loadLiftByID(liftIDFromDB);
		Integer newRating = fromDBUpdatedLift.getDriver().getRating();

		boolean isAppUpdated = (driver.getRating() - oldRating == 1);
		boolean isDBUpdated = (newRating - oldRating == 1);
		assertTrue(isAppUpdated && isDBUpdated);
	}

	@Test
	public void downvoteLiftNotDeleteTest() throws InvalidInputException, DatabaseException, InvalidStateException {
		populateDB();

		RatingController ratingController = new RatingController();
		Lift lift = getDummyLift();
		dao.saveLift(lift);
		dbModified();

		Lift liftFromDB = dao.getLastInsertedLift();
		Integer liftIDFromDB = liftFromDB.getLiftID();
		StudentCar driver = lift.getDriver();
		Integer oldRating = driver.getRating();

		String passenger1IDString = generateUserID(DRIVER_NUMBER + 1);
		String passenger2IDString = generateUserID(DRIVER_NUMBER + 2);

		dao.addPassengerByLiftIDAndUserID(liftIDFromDB, passenger1IDString);
		dao.addPassengerByLiftIDAndUserID(liftIDFromDB, passenger2IDString);

		ratingController.downvoteLift(passenger1IDString, liftIDFromDB, driver);

		Lift fromDBUpdatedLift = dao.loadLiftByID(liftIDFromDB);
		Integer newRating = fromDBUpdatedLift.getDriver().getRating();

		boolean isAppUpdated = (driver.getRating() - oldRating == -1);
		boolean isDBUpdated = (newRating - oldRating == -1);
		assertTrue(isAppUpdated && isDBUpdated);
	}

	@Test
	public void voteLiftNotPassengerTest() throws InvalidInputException, DatabaseException, InvalidStateException {
		populateDB();

		RatingController ratingController = new RatingController();
		Lift lift = getDummyLift();
		dao.saveLift(lift);
		dbModified();

		Lift liftFromDB = dao.getLastInsertedLift();
		Integer liftIDFromDB = liftFromDB.getLiftID();
		StudentCar driver = lift.getDriver();
		String passenger1IDString = generateUserID(DRIVER_NUMBER + 1);
		String passenger2IDString = generateUserID(DRIVER_NUMBER + 2);

		dao.addPassengerByLiftIDAndUserID(liftIDFromDB, passenger1IDString);

		assertThrows(InvalidStateException.class,
				() -> ratingController.downvoteLift(passenger2IDString, liftIDFromDB, driver));
	}

	@Test
	public void upvoteLiftDeleteTest() throws InvalidInputException, DatabaseException, InvalidStateException {
		populateDB();

		RatingController ratingController = new RatingController();
		Lift lift = getDummyLift();
		dao.saveLift(lift);
		dbModified();

		Lift liftFromDB = dao.getLastInsertedLift();
		Integer liftIDFromDB = liftFromDB.getLiftID();
		StudentCar driver = lift.getDriver();
		String driverID = driver.getUserID();
		Integer oldRating = driver.getRating();

		String passenger1IDString = generateUserID(DRIVER_NUMBER + 1);
		String passenger2IDString = generateUserID(DRIVER_NUMBER + 2);

		dao.addPassengerByLiftIDAndUserID(liftIDFromDB, passenger1IDString);
		dao.addPassengerByLiftIDAndUserID(liftIDFromDB, passenger2IDString);

		ratingController.upvoteLift(passenger1IDString, liftIDFromDB, driver);
		ratingController.upvoteLift(passenger2IDString, liftIDFromDB, driver);

		Integer newRating = dao.loadStudentCarByUserID(driverID).getRating();

		boolean isAppUpdated = (driver.getRating() - oldRating == 2);
		boolean isDBUpdated = (newRating - oldRating == 2);
		assertTrue(isAppUpdated && isDBUpdated);
		assertThrows(DatabaseException.class, () -> dao.loadLiftByID(liftIDFromDB));
	}

}
