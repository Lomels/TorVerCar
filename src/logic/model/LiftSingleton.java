package logic.model;

import java.util.List;

public class LiftSingleton {
	private List<Position> listPos;
	private String startPoint = new String();
	private String endPoint = new String();
	private String departureTime;
	private String arrivalTime;
	private Integer address;
	
	

private static LiftSingleton instance = null;
	
	private LiftSingleton() {}
	
	public static LiftSingleton getInstance() {
		if(instance == null) 
			instance = new LiftSingleton();
		return instance;
	}

	public List<Position> getListPos() {
		return listPos;
	}

	public void setListPos(List<Position> listPos) {
		this.listPos = listPos;
	}
	
	
	public String getStartPoint() {
		return startPoint;
	}

	public void setStartPoint(String startPoint) {
		this.startPoint = startPoint;
	}

	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Integer getAddress() {
		return address;
	}

	public void setAddress(Integer address) {
		this.address = address;
	}


}
