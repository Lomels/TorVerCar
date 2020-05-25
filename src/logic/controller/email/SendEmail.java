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
	private static String APIKEY = 
			"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";
	
	private SendEmail() {
		throw new IllegalStateException("Utility Class");
	}
	
	public static void send(String userEmail, String code) throws IOException {
		Email from = new Email("torvercar2020@gmail.com"); 
		Email to = new Email(userEmail);
		String subject = "Confirm your identity";
		String body = "Hi! Your activation code is: %s";
		String mailText = String.format(body, code);
		Content content = new Content("text/plain", mailText);
		Mail mail = new Mail(from, subject, to, content);
		
		SendGrid sg = new SendGrid(APIKEY);
		Request request = new Request();
		
		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = sg.api(request);
		} catch(IOException ex) {
			throw(ex);
		}
	}

}
