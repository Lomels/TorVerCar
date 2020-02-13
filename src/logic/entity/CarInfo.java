package logic.entity;

import java.security.InvalidParameterException;

public class CarInfo {
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

	public void setSeats(Integer seats){
		if(seats <= 0) {
			throw new InvalidParameterException("Number os seats must be greater than 0");
		}else {
			this.seats = seats;
		}
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		if(model.length() <= 0) {	
			throw new InvalidParameterException("Invalid model name");
		}
		this.model = model;
	}
	public String getColour() {
		return colour;
	}
	public void setColour(String colour) {
		if(colour.length() <= 0) {	
			throw new InvalidParameterException("Invalid colour name");
		}
		this.colour = colour;
	}
}
