package logic.bean;

import java.security.InvalidParameterException;

public class CarInfoBean{
	
	private String plate;
	private Integer seats;
	private String model;
	private String colour;
	
	public String getPlate() {
		return plate;
	}
	
	public void setPlate(String plate) {
		if (plate.length() > 0 && plate.length() < 8) {
			this.plate = plate;
		} else {
			throw new InvalidParameterException("Invalid plate");
		}
	}
	
	public Integer getSeats() {
		return seats;
	}
	public void setSeats(Integer seats) {
		this.seats = seats;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getColour() {
		return colour;
	}
	public void setColour(String colour) {
		this.colour = colour;
	}
	
	

}
