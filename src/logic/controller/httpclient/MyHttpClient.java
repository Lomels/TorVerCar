package logic.controller.httpclient;

import java.io.IOException;
import java.net.URI;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;

public class MyHttpClient {
	
	private MyHttpClient() {
		
	}

	public static String getXmlFromUrl(URI requestUrl) {
		String xml = null;
		
		// opening the client
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet request = new HttpGet(requestUrl);
		request.addHeader("accept", "application/xml");

		// generazione response
		CloseableHttpResponse response;
		try {
			response = client.execute(request);
			HttpEntity entity = response.getEntity();
			xml = EntityUtils.toString(entity);
			response.close();
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xml;
	}
}
