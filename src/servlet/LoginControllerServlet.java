package servlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import logic.bean.UserBean;
import logic.controller.LoginController;
import logic.controller.RegistrationController;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.ExceptionHandler;
import logic.controller.exception.InvalidInputException;
import logic.controller.exception.InvalidStateException;
import logic.utilities.MyLogger;

@WebServlet("/LoginControllerServlet")
public class LoginControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String index = "index.jsp";
		
		
		if("login".equals(action)) {
			String userID = request.getParameter("userID");
			String pwd = request.getParameter("pwd");
			MyLogger.info("userID", userID);
			MyLogger.info("password", pwd);
			UserBean userBean = new UserBean();
			userBean.setUserID(request.getParameter("userID"));
			userBean.setPassword(request.getParameter("pwd"));
			
			LoginController lg = new LoginController();
			try {
				lg.login(userBean);
				ServletUtility.createUser(userBean, session);
				session.setAttribute("userBean", userBean);
				request.getRequestDispatcher("homepage.jsp").forward(request, response);
			} catch (InvalidInputException | DatabaseException | InvalidStateException e) {
				ExceptionHandler.handle(e, request, response, index);
			} catch (ServletException e) {
				e.printStackTrace();
			}

		}
		
		if("check".equals(action)) {
			RegistrationController rgController = new RegistrationController();
			String userID = request.getParameter("userID");
			String password = request.getParameter("password");
			UserBean userBean;
			try {
				if(rgController.alreadyExist(userID)) {
					String message = "A user with this Student ID is already registered.";
					session.setAttribute("message", message);
					MyLogger.info("message", message);
					request.getRequestDispatcher("index.jsp").forward(request,response);
					return;
				}else {
					userBean = rgController.recapInfo(userID);
					session.setAttribute("currentUser", userBean);
					session.setAttribute("userID", userID);
					session.setAttribute("check", true);
					MyLogger.info("currentUser", userBean.getName()+" "+userBean.getSurname());
					MyLogger.info("check", session.getAttribute("check").toString());
					request.getRequestDispatcher("index.jsp").forward(request,response);
					return;
				}
			} catch (DatabaseException | InvalidInputException e) {
				ExceptionHandler.handle(e, request, response, index);
			} catch (ServletException e) {
				e.printStackTrace();
			}
		}
		
		if("register".equals(action)) {
			RegistrationController regController = new RegistrationController();
			UserBean userBean = (UserBean) session.getAttribute("currentUser");
			String password = request.getParameter("password");
			String phone = request.getParameter("phone");
			MyLogger.info("currentUser", userBean.getName()+" "+userBean.getSurname());
			userBean.setPassword(password);
			userBean.setPhone(phone);
			
			try {
				regController.addStudent(userBean);
				session.setAttribute("currentUser", userBean);
				request.getRequestDispatcher("homepage.jsp").forward(request,response);
			} catch (InvalidInputException | DatabaseException e) {
				ExceptionHandler.handle(e, request, response, index);
			} catch (ServletException e) {
				e.printStackTrace();
			}
			
			return;
		}
		
		if("logout".equals(action)) {
			session.invalidate(); 
			response.sendRedirect(".");
		}
	}

}
