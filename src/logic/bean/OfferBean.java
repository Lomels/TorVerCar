package logic.bean;

import java.util.ArrayList;
import java.util.List;

import logic.model.Position;

public class OfferBean {
	private List<Position> startingPosition;
	private List<Position> endPosition;
	private List<Position> result;
	private String status;
	private List<Position> stops = new ArrayList<>();
	
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<Position> getStops() {
		return stops;
	}
	public void setStops(List<Position> stops) {
		this.stops = stops;
	}
	
	public void addStop(Position stop) {
		this.stops.add(stop);
	}
	public List<Position> getResult() {
		return result;
	}
	public void setResult(List<Position> result) {
		this.result = result;
	}
}
