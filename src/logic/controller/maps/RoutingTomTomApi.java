package logic.controller.maps;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import logic.entity.Position;
import logic.entity.RangeTime;
import logic.entity.Route;

public class RoutingTomTomApi extends TomTomApi implements RoutingMapsApi {

	private static RoutingTomTomApi instance = null;
	// Logger
	private final static Logger LOGGER = Logger.getLogger(RoutingTomTomApi.class.getCanonicalName());

	// using Routing
	private static final String VERSION_NUMBER = "1";
	private static final String FORMAT = SERVER + "routing/%s/calculateRoute/%s/%s?key=%s";
	private static final String FILE = "src/logic/controller/maps/" + RoutingTomTomApi.class.getCanonicalName() + ".xml";

	private String routingFormat;
	private Position lastPickup = null;
	private Position lastDropoff = null;
	private String file;
	
	// constructor
	private RoutingTomTomApi() {
		this.routingFormat = String.format(FORMAT, VERSION_NUMBER, "%s", EXT, KEY);
		try {
			this.file = new File(FILE).getCanonicalPath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// singleton
	public RoutingTomTomApi GetInstance() {
		if(instance == null)
			return new RoutingTomTomApi();
		return instance;
	}

	@Override
	public Route startToStop(Position pickup, Position dropoff, RangeTime startInterval) {
		return this.startToStop(pickup, dropoff, null, startInterval);
	}

	@Override
	public Route startToStop(Position pickup, Position dropoff, List<Position> stops, RangeTime startIntervall) {
		//TODO
		return null;
	}
	
	private Integer evaluateDuration(Position pickup, Position dropoff) {
		if(this.lastPickup.equals(pickup) && this.lastDropoff.equals(lastDropoff))
			return this.evaluateDuration(pickup, dropoff, CACHE);
		else {
			//TODO REFRESH
		}
		return null;
	}

	private Integer evaluateDuration(Position pickup, Position dropoff, int cache) {
		// TODO Auto-generated method stub
		return null;
	}
	
	


}
