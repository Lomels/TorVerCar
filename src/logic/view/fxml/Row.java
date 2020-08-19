package logic.view.fxml;

import logic.model.Position;

public class Row {
	
	private String address;
	private String URL;
	private Position position;
	
	public Row(String address, String uRL, Position position) {
		super();
		this.address = address;
		this.URL = uRL;
		this.setPosition(position);
	}

	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

}
