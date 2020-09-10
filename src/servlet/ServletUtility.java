package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.bean.UserBean;
import logic.controller.LiftController;
import logic.controller.LoginController;
import logic.controller.exception.ApiNotReachableException;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.ExceptionHandler;
import logic.controller.exception.InvalidInputException;
import logic.controller.maps.CompleteMapsApi;
import logic.controller.maps.MapsApi;
import logic.model.Position;
import logic.model.Role;
import logic.model.Student;
import logic.model.StudentCar;
import logic.model.UserSingleton;

public class ServletUtility {

	private ServletUtility() {
	}

	public static void login(String userID, String password, HttpServletRequest request, HttpServletResponse response) throws DatabaseException, InvalidInputException {
		LiftController liftController = new LiftController();
		LoginController lgController = new LoginController();
		HttpSession session = request.getSession();

		UserBean usr = new UserBean();
		usr.setUserID(userID);
		usr.setPassword(password);
		try {
			lgController.login(usr);
		} catch (InvalidInputException | DatabaseException e1) {
			ExceptionHandler.handle(e1, request, response, "index.jsp");
		}

		UserSingleton sg = UserSingleton.getInstance();
		usr.setRole(sg.getRole());
		try {
			List<String> notifications = liftController.loadNotifications(usr);
			if (usr.getRole().equals(Role.STUDENT)) {
				Student student = sg.getStudent();
				student.setBookedLift(liftController.loadBookedLift(usr));
				student.setNotifications(notifications);
				session.setAttribute("user", student);
				session.setAttribute("role", "student");

			} else if (usr.getRole().equals(Role.DRIVER)) {
				StudentCar studentCar = sg.getStudentCar();
				studentCar.setBookedLift(liftController.loadBookedLift(usr));
				studentCar.setOfferedLift(liftController.loadOfferedLift(usr));
				studentCar.setNotifications(notifications);
				session.setAttribute("user", studentCar);
				session.setAttribute("role", "driver");
			}

			request.getRequestDispatcher("homepage.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

	public static List<Position> pupulateListPosition(String address)
			throws ApiNotReachableException, InvalidInputException {
		MapsApi mapsApi = CompleteMapsApi.getInstance();
		return mapsApi.addrToPos(address);

	}

	public static void liftRefresh(HttpSession session) throws DatabaseException, InvalidInputException {
		LiftController liftController = new LiftController();
		String role = (String) session.getAttribute("role");
		UserBean user = new UserBean();
		if ("student".equals(role)) {
			Student student = (Student) session.getAttribute("user");
			user.setUserID(student.getUserID());
			student.setBookedLift(liftController.loadBookedLift(user));
			session.setAttribute("user", student);
		} else if ("driver".equals(role)) {
			StudentCar studentCar = (StudentCar) session.getAttribute("user");
			user.setUserID(studentCar.getUserID());
			studentCar.setBookedLift(liftController.loadBookedLift(user));
			studentCar.setOfferedLift(liftController.loadOfferedLift(user));
			session.setAttribute("user", studentCar);
		}
	}
}
