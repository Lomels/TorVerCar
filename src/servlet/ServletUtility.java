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
import logic.controller.maps.AdapterMapsApi;
import logic.controller.maps.MapsApi;
import logic.model.Position;
import logic.model.Role;
import logic.model.Student;
import logic.model.StudentCar;
import logic.model.UserSingleton;

public class ServletUtility {

	public static void login(String userID, String password, HttpServletRequest request, HttpServletResponse response){
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
		List<String> notifications = liftController.loadNotifications(usr.getUserID());
		if (usr.getRole().equals(Role.STUDENT)) {
			Student student = sg.getStudent();
			student.setBookedLift(liftController.loadBookedLift(student.getUserID()));
			student.setNotifications(notifications);
			session.setAttribute("user", student);
			session.setAttribute("role", "student");
			
		}else if (usr.getRole().equals(Role.DRIVER)) {
			StudentCar studentCar = sg.getStudentCar();
			studentCar.setBookedLift(liftController.loadBookedLift(studentCar.getUserID()));
			studentCar.setOfferedLift(liftController.loadOfferedLift(studentCar.getUserID()));
			studentCar.setNotifications(notifications);
			session.setAttribute("user", studentCar);
			session.setAttribute("role", "driver");
		}
		
		try {
			request.getRequestDispatcher("homepage.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
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
		}else if ("driver".equals(role)) {
			StudentCar studentCar = (StudentCar) session.getAttribute("user");
			studentCar.setBookedLift(liftController.loadBookedLift(studentCar.getUserID()));
			studentCar.setOfferedLift(liftController.loadOfferedLift(studentCar.getUserID()));
			session.setAttribute("user", studentCar);
		}
	}
}
