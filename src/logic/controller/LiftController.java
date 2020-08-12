package logic.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import logic.controller.email.SendEmail;
import logic.model.Lift;
import logic.model.Route;
import logic.model.Student;
import logic.model.UserSingleton;
import logic.view.mysql.MySqlDAO;

public class LiftController {

	private final static Integer MAX_LIFTS_LISTED = 4;
	private MySqlDAO ourDb = new MySqlDAO();
	private UserSingleton sg = UserSingleton.getInstance();

	public List<Lift> matchLiftStartingAfter(Route route, LocalDateTime startDateTime, Integer initIndex) {
		List<Lift> result = new ArrayList<Lift>();
		MySqlDAO dao = new MySqlDAO();

		List<Lift> possibleLifts = dao.listAvailableLiftStartingAfterDateTime(startDateTime);

		if (possibleLifts.size() == 0)
			return result;

		return result;
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
