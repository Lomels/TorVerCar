package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.controller.ProfileController;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.ExceptionHandler;
import logic.controller.exception.InvalidInputException;
import logic.controller.exception.InvalidStateException;
import logic.model.Role;
import logic.model.Student;
import logic.model.StudentCar;
import logic.model.UserSingleton;
import logic.utilities.MyLogger;
import logic.view.mysql.MySqlDAO;

@WebServlet("/ProfileControllerServlet")
public class ProfileControllerServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		UserSingleton sg = UserSingleton.getInstance();
		MySqlDAO ourDb = new MySqlDAO();
		String action = request.getParameter("action");
		String profile = "profile.jsp";
		
		
		if("save".equals(action)) {
			String role = (String) session.getAttribute("role");
			String password = request.getParameter("password");
			String email = request.getParameter("email");
			String phone = request.getParameter("phone");
			String userID;
			
			MyLogger.info("role", role);
			MyLogger.info("email", email);
			MyLogger.info("password", password);
			MyLogger.info("phone", phone);

			
			if("student".equals(role)) {
				Student student = (Student) session.getAttribute("user");
				userID = student.getUserID();
				try {
					student.setEmail(email);
					student.setPassword(password);
					student.setPhone(phone);
				} catch (InvalidInputException e) {
					ExceptionHandler.handle(e, request, response, profile);		
					return;			
				}
				session.setAttribute("user", student);
			}else{
				StudentCar studentCar = (StudentCar) session.getAttribute("user");
				userID = studentCar.getUserID();
				try {
					studentCar.setEmail(email);
					studentCar.setPhone(phone);
					studentCar.setPassword(password);
				} catch (InvalidInputException e) {
					ExceptionHandler.handle(e, request, response, profile);	
					return;
				}
				session.setAttribute("user", studentCar);
			}
			try {
				ourDb.editInfoByUserID(userID, password, email, phone);
				request.getRequestDispatcher(profile).forward(request, response);
	
			} catch (ServletException | IOException e) {
				e.printStackTrace();
			} catch (DatabaseException e) {
				ExceptionHandler.handle(e, request, response, profile);	
				return;				
			}
			
		}
	}
}
