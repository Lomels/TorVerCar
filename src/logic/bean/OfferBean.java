package logic.bean;

import java.util.List;

import logic.model.Position;

public class OfferBean {
	private List<Position> startingPosition;
	private List<Position> endPosition;
	
	public List<Position> getStartingPosition() {
		return startingPosition;
	}
	public void setStartingPosition(List<Position> startingPosition) {
		this.startingPosition = startingPosition;
	}
	public List<Position> getEndPosition() {
		return endPosition;
	}
	public void setEndPosition(List<Position> endPosition) {
		this.endPosition = endPosition;
	}
}
