package servlet;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.bean.MessageBean;
import logic.bean.OfferBean;
import logic.controller.LiftController;
import logic.controller.PassengerController;
import logic.controller.exception.ApiNotReachableException;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.ExceptionHandler;
import logic.controller.exception.InvalidInputException;
import logic.controller.exception.InvalidStateException;
import logic.controller.exception.NoLiftAvailable;
import logic.controller.exception.PassengerException;
import logic.model.Lift;
import logic.model.Position;
import logic.model.Student;
import logic.utilities.MyLogger;

@WebServlet("/BookControllerServlet")
public class BookControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		session = request.getSession();
		String action = request.getParameter("action");
		String book = "book.jsp";

		if ("startPos".equals(action)) {
			String address = request.getParameter("start");
			OfferBean offerBean = new OfferBean();
			try {
				List<Position> positions = ServletUtility.pupulateListPosition(address);
				offerBean.setResult(positions);
				offerBean.setStatus("startPos");
				session.setAttribute("offerBean", offerBean);
				request.getRequestDispatcher(book).forward(request, response);
			} catch (ApiNotReachableException| ServletException | IOException e) {
				e.printStackTrace();
			} catch (InvalidInputException e) {
				ExceptionHandler.handle(e, request, response, book);				
			}
		}

		if ("destPos".equals(action)) {
			String address = request.getParameter("dest");
			OfferBean offerBean = (OfferBean) session.getAttribute("offerBean");
			try {
				List<Position> positions = ServletUtility.pupulateListPosition(address);
				offerBean.setResult(positions);
				offerBean.setStatus("startPos");
				session.setAttribute("offerBean", offerBean);
				request.getRequestDispatcher(book).forward(request, response);
			} catch (ApiNotReachableException| ServletException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidInputException e) {
				ExceptionHandler.handle(e, request, response, book);				
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
				request.getRequestDispatcher(book).forward(request, response);
			} catch (ServletException | IOException e) {
				e.printStackTrace();
			}
		}

		if ("search".equals(action)) {
			LiftController liftController = new LiftController();
			OfferBean offerBean = (OfferBean) session.getAttribute("offerBean");
			ServletListener listener = new ServletListener(session, request, response, offerBean);
			String date = request.getParameter("day");
			String time = request.getParameter("time");
			String slide = offerBean.getBookStatus();

			String dateTimeString = date + "T" + time;

			LocalDateTime parsedTime = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ISO_DATE_TIME);
			try {
			if ("going".equals(slide)) {
				liftController.matchLiftStoppingBefore(parsedTime, offerBean.getStops(), 0, listener);
			} else {
				liftController.matchLiftStartingAfter(parsedTime, offerBean.getStops(), 0, listener);
			}
			}catch(NoLiftAvailable e) {
				ExceptionHandler.handle(e, request, response, book);
			}
			
		}
		
		if ("book".equals(action)) {
			PassengerController passController = new PassengerController();
			Integer index = Integer.parseInt(request.getParameter("index"));
			OfferBean offerBean = (OfferBean) session.getAttribute("offerBean");
			Student student = (Student) session.getAttribute("user");
			Lift lift = offerBean.getLiftResult().get(index).getLift();

			try {
				passController.addPassenger(lift, student);
				ServletUtility.liftRefresh(session);
				
				MessageBean msg = new MessageBean();
				msg.setMessage("You have succesfully booked a lift!");
				msg.setType("success");
				msg.setTitle("Yay!");
				request.setAttribute("message", msg);
				request.getRequestDispatcher("book.jsp").forward(request, response);
				
			} catch (ServletException | IOException e) {
				e.printStackTrace();
			} catch (InvalidStateException | DatabaseException | PassengerException e) {
				ExceptionHandler.handle(e, request, response, book);
			}
		}
	}
}
