package logic.model;

import java.util.ArrayList;
import java.util.List;

import logic.utilities.Status;

public class LiftSingleton {
	private List<Position> listPos;
	private Position startPoint;
	private Position endPoint;
	private String departureTime;
	private String maxDuration;
	private Integer address;
	private String notes;
	private String arrivalTime;

	private Status status = Status.START;

	private static LiftSingleton instance = null;

	private LiftSingleton() {
	}

	public static LiftSingleton getInstance() {
		if (instance == null)
			instance = new LiftSingleton();
		return instance;
	}
	
	//TODO: usare setter
	public void clearState() throws Exception {
		this.listPos.clear();
		this.startPoint = null;
		this.endPoint = null;
		this.arrivalTime = null;
		this.departureTime = null;
		this.maxDuration = null;
		this.notes = null;
		this.arrivalTime = null;
		this.setStatus(Status.START);
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

	public void setStatus(Status status) throws Exception {
		if(status.equals(Status.OFFER) | status.equals(Status.BOOK))			
			throw new Exception("Wrong status");
		else
			this.status = status;
	}
}
