package servlet;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.bean.LiftBean;
import logic.bean.MessageBean;
import logic.bean.OfferBean;
import logic.controller.LiftController;
import logic.controller.exception.ApiNotReachableException;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.ExceptionHandler;
import logic.controller.exception.InvalidInputException;
import logic.controller.exception.InvalidStateException;
import logic.model.Position;
import logic.model.StudentCar;

@WebServlet("/OfferControllerServlet")
public class OfferControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String offer = "offer.jsp";

		try {
			if ("startPos".equals(action)) {

				String address = request.getParameter("start");
				OfferBean offerBean = new OfferBean();

				List<Position> positions = ServletUtility.pupulateListPosition(address);
				offerBean.setResult(positions);
				offerBean.setStatus("startPos");
				session.setAttribute("offerBean", offerBean);
				request.getRequestDispatcher(offer).forward(request, response);

			}

			if ("desPos".equals(action)) {
				String address = request.getParameter("dest");
				OfferBean offerBean = (OfferBean) session.getAttribute("offerBean");

				List<Position> positions = ServletUtility.pupulateListPosition(address);
				offerBean.setResult(positions);
				offerBean.setStatus("startPos");
				session.setAttribute("offerBean", offerBean);
				request.getRequestDispatcher("offer.jsp").forward(request, response);

			}

			if ("stop".equals(action)) {
				String index = request.getParameter("index");
				OfferBean offerBean = (OfferBean) session.getAttribute("offerBean");
				offerBean.addStop(offerBean.getResult().get(Integer.parseInt(index)));
				offerBean.setStatus("");

				session.setAttribute("offerBean", offerBean);

				request.getRequestDispatcher("offer.jsp").forward(request, response);

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

				LiftBean lift = new LiftBean();
				lift.setStartDateTime(LocalDateTime.parse(startDateTimeString));
				lift.setMaxDuration(Integer.parseInt(maxDuration));
				lift.setNote(note);
				lift.setDriver(driver);
				lift.setStartPos(offerBean.getStops().get(0));
				lift.setStopPos(offerBean.getStops().get(1));

				liftController.createLift(lift);
				OfferBean newBean = new OfferBean();
				session.setAttribute("offerBean", newBean);
				ServletUtility.liftRefresh(session);
				MessageBean msg = new MessageBean();
				msg.setMessage("You have succesfully offered a lift!");
				msg.setType("success");
				msg.setTitle("Yay!");
				request.setAttribute("message", msg);
				request.getRequestDispatcher(offer).forward(request, response);

			}
		} catch (NumberFormatException | InvalidInputException | DatabaseException | InvalidStateException
				| ApiNotReachableException e) {
			ExceptionHandler.handle(e, request, response, offer);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}

	}

}
