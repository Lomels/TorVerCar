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

	public static String getStringFromUrl(URI requestUrl) {
		return MyHttpClient.getStringFromUrl(requestUrl, null);
	}

	public static String getStringFromUrl(URI requestUrl, String accept) {
		String body = null;

		// Opening the client
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet request = new HttpGet(requestUrl);
		if (accept != null)
			request.addHeader("accept", accept);

		// Generate response
		CloseableHttpResponse response;
		try {
			response = client.execute(request);
			HttpEntity entity = response.getEntity();
			body = EntityUtils.toString(entity);
			response.close();
			client.close();
		} catch (ParseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return body;

	}
}
