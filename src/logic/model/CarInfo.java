package logic.model;

import logic.controller.exception.InvalidInputException;
import logic.utilities.InputChecker;

public class CarInfo {
	private String plate;
	private Integer seats;
	private String model;
	private String color;

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
		InputChecker.checkIntegerMoreThan("Seats", seats, 0);
		this.seats = seats;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		InputChecker.checkNotNull(model, "Model");
		this.model = model;

	}

	public String getColour() {
		return color;
	}

	public void setColour(String color) {
		InputChecker.checkNotNull(color, "Color");
		this.color = color;
	}

	@Override
	public String toString() {
		return "CarInfo [plate=" + plate + ", seats=" + seats + ", model=" + model + ", colour=" + color + "]";
	}

}
