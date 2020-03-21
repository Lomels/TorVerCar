package logic.entity;

import logic.controller.InputChecker;
import logic.controller.exception.InvalidInputException;

public class CarInfo {
	private String plate;
	private Integer seats;
	private String model;
	private String colour;

	public CarInfo(String plate, Integer seats, String model, String colour) throws InvalidInputException {
		this.setPlate(plate);
		this.setSeats(seats);
		this.setModel(model);
		this.setColour(colour);
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) throws InvalidInputException {
		InputChecker.checkPlate(plate);
		this.plate = plate;
	}

	public Integer getSeats() {
		return seats;
	}

	public void setSeats(Integer seats) throws InvalidInputException {
		if (seats <= 1) {
			throw new InvalidInputException("Number os seats must be greater than 1");
		} else {
			this.seats = seats;
		}
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) throws InvalidInputException {
		InputChecker.checkGeneric(model);
		this.model = model;

	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) throws InvalidInputException {
		InputChecker.checkGeneric(colour);
		this.colour = colour;
	}
}
