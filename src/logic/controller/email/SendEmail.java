package logic.controller.email;

import java.io.IOException;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

public class SendEmail {
	private static String API_KEY = 
			"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";
	
	public static void main(String[] args) throws Exception{
		Email from = new Email("torvercar2020@gmail.com"); 
		Email to = new Email("marco.lomele@gmail.com");
		Email to2 = new Email("g.marseglia.it@gmail.com");
		String subject = "ispw";
		Content content = new Content("text/plain", "Se sboccia.");
		Mail mail = new Mail(from, subject, to, content);
		
		SendGrid sg = new SendGrid(API_KEY);
		Request request = new Request();
		
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = sg.api(request);
			System.out.println(response.getStatusCode());
			System.out.println(response.getBody());
			System.out.println(response.getHeaders());
		} catch(IOException ex) {
			throw(ex);
		}
	}

}
