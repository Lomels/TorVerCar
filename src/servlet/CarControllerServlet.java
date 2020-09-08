package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.bean.CarInfoBean;
import logic.controller.SetCarInfoController;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.ExceptionHandler;
import logic.controller.exception.InvalidInputException;
import logic.controller.exception.InvalidStateException;
import logic.model.UserSingleton;
import logic.model.CarInfo;
import logic.model.Role;
import logic.model.Student;
import logic.model.StudentCar;
import logic.view.mysql.MySqlDAO;

@WebServlet("/CarControllerServlet")
public class CarControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String myCar = "myCar.jsp";

		if ("save".equals(action)) {
			SetCarInfoController setCar = new SetCarInfoController();
			CarInfoBean carInfo = new CarInfoBean();
			UserSingleton sg = UserSingleton.getInstance();

			carInfo.setModel(request.getParameter("model"));
			carInfo.setPlate(request.getParameter("plate"));
			carInfo.setColour(request.getParameter("color"));
			carInfo.setSeats(Integer.parseInt(request.getParameter("seats")));

			String role = (String) session.getAttribute("role");

			StudentCar studentCar = null;

			if ("student".equals(role)) {
				Student student = (Student) session.getAttribute("user");
				sg.setStudent(student);
				sg.setRole(Role.STUDENT);
				try {
					setCar.editCar(carInfo);
					studentCar = new StudentCar(student, 0, new CarInfo(carInfo.getPlate(), carInfo.getSeats(),
							carInfo.getModel(), carInfo.getColour()));
				} catch (InvalidInputException | DatabaseException e) {
					ExceptionHandler.handle(e, request, response, myCar);
				}
			} else {
				studentCar = (StudentCar) session.getAttribute("user");
				sg.setStudentCar(studentCar);
				sg.setRole(Role.DRIVER);
				try {
					studentCar.setCarInfo(new CarInfo(carInfo.getPlate(), carInfo.getSeats(), carInfo.getModel(),
							carInfo.getColour()));
					setCar.editCar(carInfo);
				} catch (InvalidInputException | DatabaseException e) {
					ExceptionHandler.handle(e, request, response, myCar);
				}
			}

			try {
				session.setAttribute("role", "driver");
				session.setAttribute("user", studentCar);
				request.getRequestDispatcher(myCar).forward(request, response);
			} catch (ServletException | IOException e) {
				e.printStackTrace();
			}
		}

	}

}
