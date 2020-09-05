package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.bean.OfferBean;
import logic.controller.LiftMatchListener;
import logic.model.LiftMatchResult;
import logic.utilities.MyLogger;

public class ServletListener implements LiftMatchListener{
	private HttpSession session;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private OfferBean offerBean;
	
	public ServletListener(HttpSession session, HttpServletRequest request, HttpServletResponse response, OfferBean bean) {
		this.session=session;
		this.request=request;
		this.response=response;
		this.offerBean=bean;
	}

	@Override
	public void onThreadEnd(List<LiftMatchResult> results) {
		offerBean.setLiftResult(results);
		offerBean.setStatus("liftResult");
		
		MyLogger.info("results ", results);
		
		session.setAttribute("offerBean", offerBean);
		
		try {
			request.getRequestDispatcher("book.jsp").forward(request, response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void onThreadRunning() {
	}

}
