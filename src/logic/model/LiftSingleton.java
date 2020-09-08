package logic.model;

import java.util.ArrayList;
import java.util.List;

import logic.controller.exception.WrongStatus;
import logic.utilities.Status;

public class LiftSingleton {
	private List<Position> listPos = new ArrayList<>();
	private List<LiftMatchResult> listLifts = new ArrayList<>();
	private Lift selectedLift;
	private Position startPoint;
	private Position endPoint;
	private String departureTime;
	private String maxDuration;
	private Integer address;
	private String notes;
	private String arrivalTime;
	private String purpose = "cbGoing";

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	private Status status = Status.START;

	private static LiftSingleton instance = null;

	private LiftSingleton() {
	}

	public static LiftSingleton getInstance() {
		if (instance == null)
			instance = new LiftSingleton();
		return instance;
	}

	public void clearState() {
		this.listPos.clear();
		this.listLifts.clear();
		this.setStartPoint(null);
		this.setEndPoint(null);
		this.setArrivalTime(null);
		this.setDepartureTime(null);
		this.setMaxDuration(null);
		this.setNotes(null);
		this.setStatus(Status.START);
		this.setSelectedLift(null);
		this.setPurpose("cbGoing");
	}

	public List<Position> getListPos() {
		return listPos;
	}

	public void setListPos(List<Position> listPos) {
		this.listPos = listPos;
	}

	public Position getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(Position startPoint) {
		this.startPoint = startPoint;
	}

	public Position getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(Position endPoint) {
		this.endPoint = endPoint;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public String getMaxDuration() {
		return maxDuration;
	}

	public void setMaxDuration(String arrivalTime) {
		this.maxDuration = arrivalTime;
	}

	public Integer getAddress() {
		return address;
	}

	public void setAddress(Integer address) {
		this.address = address;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		if (status.equals(Status.OFFER) || status.equals(Status.BOOK))
			throw new WrongStatus();
		else
			this.status = status;
	}

	public List<LiftMatchResult> getListLifts() {
		return listLifts;
	}

	public void addLiftMatchResult(LiftMatchResult lift) {
		this.listLifts.add(lift);
	}

	public Lift getSelectedLift() {
		return selectedLift;
	}

	public void setSelectedLift(Lift selectedLift) {
		this.selectedLift = selectedLift;
	}
}
