package logic.utilities;

import logic.model.Position;
import java.util.*;

public class PositionListCombiner extends ListCombiner {

	public List<List<Position>> combinePositions(List<Position> startStops, List<Position> addStops) {
		List<List<Position>> result = new ArrayList<>();
		
		for(List<?> list : this.combine(startStops, addStops)) {
			@SuppressWarnings("unchecked")
			List<Position> actualList = (List<Position>) list;
			result.add(actualList);
		}
		
		return result;
	}

}
