package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.bean.MessageBean;
import logic.bean.UserBean;
import logic.controller.RegistrationController;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.ExceptionHandler;
import logic.controller.exception.InvalidInputException;

@WebServlet("/LoginControllerServlet")
public class LoginControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CURRENT_USER = "currentUser";

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String index = "index.jsp";

		try {
			if ("login".equals(action)) {
				String userID = request.getParameter("userID");
				String pwd = request.getParameter("pwd");
				ServletUtility.login(userID, pwd, request, response);

			}

			if ("check".equals(action)) {
				RegistrationController rgController = new RegistrationController();
				String userID = request.getParameter("userID");
				UserBean userBean = new UserBean();
				userBean.setUserID(userID);
				
				if (rgController.alreadyExist(userBean)) {
					MessageBean message = new MessageBean();
					message.setMessage("A user with this Student ID is already registered.");
					message.setType("warning");
					request.setAttribute("message", message);
					request.getRequestDispatcher(index).forward(request, response);
					return;
				} else {
					userBean = rgController.recapInfo(userBean);
					userBean.setUserID(userID);
					session.setAttribute(CURRENT_USER, userBean);
					session.setAttribute("check", true);
					request.getRequestDispatcher(index).forward(request, response);
					return;

				}
			}

			if ("register".equals(action)) {
				RegistrationController regController = new RegistrationController();
				UserBean userBean = (UserBean) session.getAttribute(CURRENT_USER);

				String password = request.getParameter("password");
				String phone = request.getParameter("phone");

				userBean.setPassword(password);
				userBean.setPhone(phone);
				regController.addStudent(userBean);

				session.setAttribute(CURRENT_USER, userBean);
				ServletUtility.login(userBean.getUserID(), password, request, response);

				request.getRequestDispatcher("homepage.jsp").forward(request, response);

				return;
			}

			if ("logout".equals(action)) {
				session.invalidate();
				response.sendRedirect(".");
			}

		} catch (InvalidInputException | DatabaseException e) {
			ExceptionHandler.handle(e, request, response, index);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
}
