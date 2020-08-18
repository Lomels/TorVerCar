package test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import logic.controller.exception.ApiNotReachableException;
import logic.controller.exception.InvalidInputException;
import logic.controller.maps.AdapterMapsApi;
import logic.controller.maps.MapsApi;
import logic.model.Position;
import logic.model.Route;
import logic.utilities.MyLogger;

public class relativeDateTimeTest {
	
	MapsApi maps = AdapterMapsApi.getInstance();
	
	MyLogger logger = new MyLogger(true);
	
	@Test
	public void test() throws ApiNotReachableException, InvalidInputException {
		List<Position> stops = new ArrayList<>();
		
		String addrStart = "Via folcarotonda 19, Palestrina";
		Position posStart = maps.addrToPos(addrStart).get(0);
		logger.infoB("posStart", posStart);

		String addrStop = "Viale del Politecnico 1, Roma";
		Position posStop = maps.addrToPos(addrStop).get(0);
		logger.infoB("posStop", posStop);

		String addrMedStart = "Via prenestina nuova 51, Palestrina";
		Position posMedStart = maps.addrToPos(addrMedStart).get(0);
		logger.infoB("posMedStart", posMedStart);

		String addrMedLong = "Tivoli";
		Position posMedLong = maps.addrToPos(addrMedLong).get(0);
		logger.infoB("posStop", posMedLong);

		String addrMedShort = "Zagarolo";
		Position posMedShort = maps.addrToPos(addrMedShort).get(0);
		logger.infoB("posStop", posMedShort); 
		
		stops.add(posStart);
		stops.add(posMedStart);
		stops.add(posMedShort);
		stops.add(posStop);
		
		Route route = maps.startToStop(stops);
		logger.infoB("Route", route);
	}

}
