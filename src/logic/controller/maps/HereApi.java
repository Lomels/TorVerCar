package logic.controller.maps;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import logic.entity.Position;

public class HereApi implements ViewMapsApi {
	
	private static HereApi instance = null;
	
	// use HERE REST Api
	//TODO: implement class for better build the url
	private static final String KEY = "JB1x92IV3wywdCVXejt2bDvpnRsK14Y9vBoLUONVBLE";
	private static final String FORMAT = "https://image.maps.ls.hereapi.com/mia/1.6/mapview" + "?apiKey=%s" + "&c=%s,%s"
			+ "&z=%s" + "&t=0" + "&vt=0" + "&ml=ita" + "&h=512" + "&w=512" + "&pip=13";
	private static final String FILE = "src/logic/controller/maps/image.jpg";
	private File actualFile;
	private static int defaultZoom = 16;

	private HereApi() {
		this.actualFile = new File(FILE);
	}
	
	public static HereApi getInstance() {
		if(HereApi.instance == null)
			HereApi.instance = new HereApi();
		return HereApi.instance;
	}

	@Override
	public String viewFromPos(Position p) {
		return this.viewFromPos(p, HereApi.defaultZoom);
	}

	@Override
	public String viewFromPos(Position p, int zoom) {
		String result = String.format(FORMAT, KEY, p.getLat(), p.getLon(), zoom);
		return result;
	}

	@Override
	public void saveImage(Position p) {
		try {
			URL url = new URL(this.viewFromPos(p));
			BufferedImage image = ImageIO.read(url);
			ImageIO.write(image, "jpg", actualFile);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

}
