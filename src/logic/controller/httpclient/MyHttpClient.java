package logic.controller.httpclient;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Logger;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import logic.controller.exception.ApiNotReachableException;

public class MyHttpClient {

	private static final Logger LOGGER = Logger.getLogger(MyHttpClient.class.getCanonicalName());

	private static final String MESSAGE_FORMAT = "Could not recieve response for URL: %s.";

	private MyHttpClient() {

	}

	public static String getStringFromUrl(URI requestUrl) throws ApiNotReachableException {
		String body = null;

		// Opening the client
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet request = new HttpGet(requestUrl);

		// Generate response
		CloseableHttpResponse response;
		try {
			response = client.execute(request);
			HttpEntity entity = response.getEntity();
			body = EntityUtils.toString(entity);
			response.close();
		} catch (IOException | ParseException e) {
			throw new ApiNotReachableException(String.format(MESSAGE_FORMAT, requestUrl.toString()));
		} finally {
			try {
				client.close();
			} catch (IOException e) {
				LOGGER.fine(e.toString());
			}
		}

		return body;

	}
}
