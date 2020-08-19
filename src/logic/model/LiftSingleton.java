package logic.model;

import java.util.ArrayList;
import java.util.List;

public class LiftSingleton {
	private List<Position> listPos;
	private Position startPoint;
	private Position endPoint;
	private String departureTime;
	private String maxDuration;
	private Integer address;
	private String notes;
	

public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String status = START;

	public static final String START = "START";
	public static final String STOP = "STOP";
	public static final String BOTH = "BOTH";

	private static LiftSingleton instance = null;

	private LiftSingleton() {
	}

	public static LiftSingleton getInstance() {
		if (instance == null)
			instance = new LiftSingleton();
		return instance;
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

}
