package logic.controller.maps;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import logic.model.Position;

public class ViewMapHereApi extends HereApi implements ViewMapApi {

	private static ViewMapHereApi instance = null;

	// TODO: implement class for better build the url
	private static final  String path = "/mia/1.6/mapview";
	private static final int zoom = 15;
	private final String pathname = "src/logic/controller/maps/" + ViewMapHereApi.class.getCanonicalName() + ".jpg";

	private ViewMapHereApi() {
	}

	public static ViewMapHereApi getInstance() {
		if (ViewMapHereApi.instance == null)
			ViewMapHereApi.instance = new ViewMapHereApi();
		return ViewMapHereApi.instance;
	}

	@Override
	public String viewFromPos(Position p) {
		return this.viewFromPos(p, zoom);
	}

	@Override
	public String viewFromPos(Position p, int zoom) {
		StringBuilder builder = new StringBuilder();
		builder.append(SCHEME);
		builder.append(IMAGE_HOST);
		builder.append(path);
		builder.append("?apiKey=" + KEY);
		// coordinates of the point to visualize
		builder.append("&lat=" + p.getLat());
		builder.append("&lon=" + p.getLon());
		builder.append("&z=" + zoom);
		for (ViewMapHereParameter parameter : ViewMapHereParameter.values()) {
			builder.append(parameter.getParameter());
		}
		return builder.toString();
	}

	// TODO: better the file position
	@Override
	public void saveImage(Position p) {
		try {
			File file = new File(pathname);
			URL url = new URL(this.viewFromPos(p));
			BufferedImage image = ImageIO.read(url);
			ImageIO.write(image, "jpg", file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}

}
