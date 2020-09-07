package logic.view.mylift;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class RowMyLiftController {
	@FXML
	private Text tvFrom;
	@FXML
	private Text tvTo;
	@FXML
	private Text tvStart;
	@FXML
	private Text tvWhen;
	@FXML
	private Text tvStop;

	public void setFrom(String from) {
		tvFrom.setText(from);
	}
	
	public void setWhen(String when) {
		tvWhen.setText(when);
	}
	
	public void setTo(String to) {
		tvTo.setText(to);
	}
	
	public void setStart(String timeFrom) {
		tvStart.setText(timeFrom);
	}
	
	public void setStop(String timeTo) {
		tvStop.setText(timeTo);
	}
}
