package logic.view.offer;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import logic.controller.maps.ViewMapHereApi;
import logic.model.Position;

public class RowAddress extends ListCell<Position>{
	ViewMapHereApi map = ViewMapHereApi.getInstance();
	private String address;
	private String url;
	private Position position;
	
	private Node graphic;
	private RowAddressController controller;
	
	public RowAddress(){
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/address_row.fxml"));
			graphic = loader.load();
			controller = loader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void updateItem(Position pos, boolean empty) {
		super.updateItem(pos, empty);
		if (empty) {
			setGraphic(null);
		} else {
			controller.setAddress(pos.getAddress());
			controller.setMap(map.viewFromPos(pos));

			setGraphic(graphic);
		}

	}

	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getURL() {
		return url;
	}
	public void setURL(String uRL) {
		url = uRL;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

}
