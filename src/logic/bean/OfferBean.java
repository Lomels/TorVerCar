package logic.bean;

import java.util.ArrayList;
import java.util.List;

import logic.model.Lift;
import logic.model.LiftMatchResult;
import logic.model.Position;

public class OfferBean {
	private List<Position> startingPosition;
	private List<Position> endPosition;
	private List<Position> result;
	private String status;
	private String bookStatus = "going";
	private List<Position> stops = new ArrayList<>();
	
	private List<LiftMatchResult> liftResult;
	private Lift chosenLift;
	
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
	public List<LiftMatchResult> getLiftResult() {
		return liftResult;
	}
	public void setLiftResult(List<LiftMatchResult> liftResult) {
		this.liftResult = liftResult;
	}
	public Lift getChosenLift() {
		return chosenLift;
	}
	public void setChosenLift(Lift chosenLift) {
		this.chosenLift = chosenLift;
	}
	public String getBookStatus() {
		return bookStatus;
	}
	public void setBookStatus(String bookStatus) {
		this.bookStatus = bookStatus;
	}
}
