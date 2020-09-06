package servlet;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.bean.OfferBean;
import logic.controller.LiftController;
import logic.controller.exception.ApiNotReachableException;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.controller.exception.InvalidStateException;
import logic.model.Position;
import logic.model.StudentCar;
import logic.utilities.MyLogger;
import logic.view.mysql.MySqlDAO;

@WebServlet("/OfferControllerServlet")
public class OfferControllerServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String offer = "offer.jsp";

		if ("startPos".equals(action)) {
			String address = request.getParameter("start");
			OfferBean offerBean = new OfferBean();
			try {
				List<Position> positions = ServletUtility.pupulateListPosition(address);
				offerBean.setResult(positions);
				offerBean.setStatus("startPos");
				session.setAttribute("offerBean", offerBean);
				request.getRequestDispatcher("offer.jsp").forward(request, response);
			} catch (ApiNotReachableException | InvalidInputException | ServletException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if ("desPos".equals(action)) {
			String address = request.getParameter("dest");
			OfferBean offerBean = (OfferBean) session.getAttribute("offerBean");
			try {
				List<Position> positions = ServletUtility.pupulateListPosition(address);
				offerBean.setResult(positions);
				offerBean.setStatus("startPos");
				session.setAttribute("offerBean", offerBean);
				request.getRequestDispatcher("offer.jsp").forward(request, response);
			} catch (ApiNotReachableException | InvalidInputException | ServletException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if ("stop".equals(action)) {
			String index = request.getParameter("index");
			OfferBean offerBean = (OfferBean) session.getAttribute("offerBean");
			offerBean.addStop(offerBean.getResult().get(Integer.parseInt(index)));
			offerBean.setStatus("");

			session.setAttribute("offerBean", offerBean);

			MyLogger.info("selected position", offerBean.getResult().get(Integer.parseInt(index)));
			try {
				request.getRequestDispatcher("offer.jsp").forward(request, response);
			} catch (ServletException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if ("offer".equals(action)) {
			LiftController liftController = new LiftController();
			OfferBean offerBean = (OfferBean) session.getAttribute("offerBean");
			StudentCar driver = (StudentCar) session.getAttribute("user");

			String date = request.getParameter("day");
			String time = request.getParameter("time");
			String note = request.getParameter("notes");
			String maxDuration = request.getParameter("maxTime");

			String startDateTimeString = date + "T" + time;
			try {
				liftController.createLift(startDateTimeString, Integer.parseInt(maxDuration), note, driver,
						offerBean.getStops().get(0), offerBean.getStops().get(1));
				OfferBean newBean = new OfferBean();
				session.setAttribute("offerBean", newBean);
				ServletUtility.liftRefresh(session);
				request.getRequestDispatcher("homepage.jsp").forward(request, response);

			} catch (NumberFormatException | InvalidInputException | DatabaseException | InvalidStateException
					| ServletException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
