package entity;
	
public class CarInfo {
	private String plate;
	private Integer seats;
	private String model;
	private String colour;
	
	public String getPlate() {
		return plate;
	}
	//AGGIUNGERE EXCEPTION
	public void setPlate(String plate) {
		if(plate.length() != 7) {
			// TODO Implementare meglio
			return;		
	}else {
			this.plate = plate;
		}
		
	}
	public Integer getSeats() {
		return seats;
	}
	//AGGIUNGERE EXCEPTION
	public void setSeats(Integer seats){
		if(seats<1) {
			// TODO Implementare meglio
			return;
		}else {
			this.seats = seats;
		}
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		if(model.length()<1) {	
			// TODO Implementare meglio
			return;
		}
		this.model = model;
	}
	public String getColour() {
		return colour;
	}
	public void setColour(String colour) {
		if(colour.length()<3) {
			// TODO Implementare meglio
			return;
		}
		this.colour = colour;
	}
}
