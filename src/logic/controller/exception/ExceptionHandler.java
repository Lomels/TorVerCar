package logic.controller.exception;

import javax.servlet.http.HttpSession;

import logic.bean.MessageBean;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExceptionHandler {
	
	public static void handle(Exception e, HttpServletRequest request, HttpServletResponse response, String location) {
		MessageBean message = new MessageBean();
		message.setMessage(e.getMessage());
		message.setType("error");
		message.setTitle("Error!");
		request.setAttribute("message", message);
		try {
			RequestDispatcher rd= request.getRequestDispatcher(location);
			rd.forward(request, response);
		} catch (ServletException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
}
