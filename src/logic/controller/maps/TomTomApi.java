package logic.controller.maps;

import java.io.*;
import java.net.URI;

import logic.controller.exception.ApiNotReachableException;
import logic.controller.httpClient.MyHttpClient;


public class TomTomApi {

	private static TomTomApi instance = null;
	
	// using TomTomApi
	protected static final String KEY = "8xAoG57mmkjCxim04niX0NYPXAZMuAyV";
	protected static final String EXT = "xml";
	protected static final String SERVER = "https://api.tomtom.com/";
	
	protected static final int CACHE = 0;
	protected static final int REFRESH = 1;
	
	// prevent constructor
	protected TomTomApi() {
		
	}
	
	// method to use the REST API
	protected String useRestApi(URI requestUrl, String file) throws ApiNotReachableException {
		String xml = null;		
		try {
			// create client e request
			xml = MyHttpClient.getXmlFromUrl(requestUrl);
			// scrittura su file per cache
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(xml);
			writer.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xml;
	}

}
