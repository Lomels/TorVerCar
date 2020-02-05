package Entity;
	
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
			return;
		}
		this.model = model;
	}
	public String getColour() {
		return colour;
	}
	public void setColour(String colour) {
		if(colour.length()<3) {	//colore più corto = blu - Marco
			return;
		}
		this.colour = colour;
	}
}
