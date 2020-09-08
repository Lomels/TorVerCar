package logic.view.booking;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import logic.model.LiftMatchResult;

public class RowLift extends ListCell<LiftMatchResult> {
	private LiftMatchResult lift;
	private String from;
	private String to;
	private String timeFrom;
	private String timeTo;

	private Node graphic;
	private RowLiftController controller;

	public RowLift(LiftMatchResult lift) {
		this.lift = lift;
	}

	public RowLift() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/lift_row.fxml"));
			graphic = loader.load();
			controller = loader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void updateItem(LiftMatchResult lift, boolean empty) {
		super.updateItem(lift, empty);
		if (empty) {
			setGraphic(null);
		} else {
			controller.setFrom(lift.getLift().getRoute().getPickupPosition().getAddress());
			controller.setTo(lift.getLift().getRoute().getDropoffPosition().getAddress());
			controller.setTimeFrom(lift.getRelativeStartDateTime().toString());
			controller.setTimeTo(lift.getLift().getStopDateTime().toString());

			setGraphic(graphic);
		}
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getTimeFrom() {
		return timeFrom;
	}

	public void setTimeFrom(String timeFrom) {
		this.timeFrom = timeFrom;
	}

	public String getTimeTo() {
		return timeTo;
	}

	public void setTimeTo(String timeTo) {
		this.timeTo = timeTo;
	}

	public LiftMatchResult getResult() {
		return this.lift;
	}
}
