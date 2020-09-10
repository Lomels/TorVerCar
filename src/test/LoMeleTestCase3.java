package test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

import logic.controller.PassengerController;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.controller.exception.InvalidStateException;
import logic.controller.exception.PassengerException;
import logic.model.Lift;
import logic.model.Student;
import logic.view.mysql.MySqlDAO;

/* MARCO LO MELE */

class LoMeleTestCase3 {
	private PassengerController passenger = new PassengerController();
	private MySqlDAO dao = new MySqlDAO();

	@Test
	void test() throws DatabaseException, InvalidInputException, InvalidStateException, PassengerException {
		Student student = dao.loadStudentByUserID("0245061");
		Lift lift = dao.loadLiftByID(1);
		
		// Make sure that the given student is not already a passenger.
		for(Student s : lift.getPassengers()) {
			if (student.getUserID().contentEquals(s.getUserID())) {
				lift.getPassengers().remove(s);
				break;
			}
		}
		
		assertDoesNotThrow(() -> passenger.addPassenger(lift, student));
	}

}
