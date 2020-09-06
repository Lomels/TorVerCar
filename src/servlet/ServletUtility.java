package servlet;

import java.util.List;

import javax.servlet.http.HttpSession;

import logic.bean.UserBean;
import logic.controller.LiftController;
import logic.controller.exception.ApiNotReachableException;
import logic.controller.exception.InvalidInputException;
import logic.controller.maps.AdapterMapsApi;
import logic.controller.maps.MapsApi;
import logic.model.Position;
import logic.model.Role;
import logic.model.Student;
import logic.model.StudentCar;
import logic.model.UserSingleton;

public class ServletUtility {

	public static void createUser(UserBean usr, HttpSession session) throws InvalidInputException {
		LiftController liftController = new LiftController();
		UserSingleton sg = UserSingleton.getInstance();
		usr.setRole(sg.getRole());
		
		if (usr.getRole().equals(Role.STUDENT)) {
			Student student = sg.getStudent();
			student.setBookedLift(liftController.loadBookedLift(student.getUserID()));
			session.setAttribute("user", student);
			session.setAttribute("role", "student");
			return;
		}else if (usr.getRole().equals(Role.DRIVER)) {
			StudentCar studentCar = sg.getStudentCar();
			studentCar.setBookedLift(liftController.loadBookedLift(studentCar.getUserID()));
			studentCar.setOfferedLift(liftController.loadOfferedLift(studentCar.getUserID()));
			session.setAttribute("user", studentCar);
			session.setAttribute("role", "driver");
			return;
		}
	}

	public static List<Position> pupulateListPosition(String address)
			throws ApiNotReachableException, InvalidInputException {
		MapsApi mapsApi = AdapterMapsApi.getInstance();
		return mapsApi.addrToPos(address);

	}
	
	public static void liftRefresh(HttpSession session) {
		LiftController liftController = new LiftController();
		String role = (String) session.getAttribute("role");
		if ("student".equals(role)) {
			Student student = (Student) session.getAttribute("user");
			student.setBookedLift(liftController.loadBookedLift(student.getUserID()));
			session.setAttribute("user", student);
			return;
		}else if ("driver".equals(role)) {
			StudentCar studentCar = (StudentCar) session.getAttribute("user");
			studentCar.setBookedLift(liftController.loadBookedLift(studentCar.getUserID()));
			studentCar.setOfferedLift(liftController.loadOfferedLift(studentCar.getUserID()));
			session.setAttribute("user", studentCar);
			return;
		}
	}
}
