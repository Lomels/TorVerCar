package logic.view.mylift;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import logic.model.Lift;

public class RowMyLift extends ListCell<Lift>{
	private String from;
	private String to;
	private String when;
	private String start;
	private String stop;
	private Node graphic;
	private RowMyLiftController controller;
	
	public RowMyLift(){
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/mylift_row.fxml"));
			graphic = loader.load();
			controller = loader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void updateItem(Lift row, boolean empty) {
		super.updateItem(row, empty);
		if (empty) {
			setGraphic(null);
		} else {
			controller.setFrom(row.getRoute().getPickupPosition().getAddress());
			controller.setTo(row.getRoute().getDropoffPosition().getAddress());
			controller.setStart(row.getStartDateTime().format(DateTimeFormatter.ISO_LOCAL_TIME));
			controller.setStop(row.getStopDateTime().format(DateTimeFormatter.ISO_LOCAL_TIME));
			controller.setWhen(row.getStartDateTime().format(DateTimeFormatter.ISO_DATE));
			setGraphic(graphic);
		}
	}
	
	public String getStop() {
		return stop;
	}

	public void setStop(String stop) {
		this.stop = stop;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getWhen() {
		return when;
	}

	public void setWhen(String when) {
		this.when = when;
	}
}
