package logic.controller.maps;

import java.util.ArrayList;
import java.util.List;

import logic.model.Position;
import logic.utilities.ListCombiner;

public class PositionListCombiner extends ListCombiner {

	public List<List<Position>> combinePositions(List<Position> startStops, List<Position> addStops) {
		List<List<Position>> result = new ArrayList<>();

		for (List<?> list : this.combine(startStops, addStops)) {
			@SuppressWarnings("unchecked")
			List<Position> actualList = (List<Position>) list;
			result.add(actualList);
		}

		return result;
	}

}
