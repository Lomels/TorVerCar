package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.bean.OfferBean;
import logic.controller.exception.ApiNotReachableException;
import logic.controller.exception.InvalidInputException;
import logic.model.Position;
import logic.utilities.MyLogger;

@WebServlet("/OfferControllerServlet")
public class OfferControllerServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		String offer = "offer.jsp";
		
		if("startPos".equals(action)) {
			String address = request.getParameter("start");
			OfferBean offerBean = new OfferBean();
			try {
				List<Position> positions = ServletUtility.pupulateListPosition(address);
				offerBean.setStartingPosition(positions);
				session.setAttribute("offerBean", offerBean);
				session.setAttribute("status", "startPos");
				
				MyLogger.info("status", session.getAttribute("status"));
				request.getRequestDispatcher("offer.jsp").forward(request, response);
			} catch (ApiNotReachableException | InvalidInputException | ServletException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if("desPos".equals(action)) {
			String address = request.getParameter("dest");
			try {
				List<Position> positions = ServletUtility.pupulateListPosition(address);
				session.setAttribute("destPositions", positions);
				session.setAttribute("status", "desPos");
				request.getRequestDispatcher("offer.jsp").forward(request, response);
			} catch (ApiNotReachableException | InvalidInputException | ServletException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
