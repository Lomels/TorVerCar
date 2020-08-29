package logic.view.booking;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class RowLiftController {
	@FXML private Text tvFrom;
	@FXML private Text tvTo;
	@FXML private Text tvTimeFrom;
	@FXML private Text tvTimeTo;
	
	
	
	
	public void setFrom(String from) {
		tvFrom.setText(from);
	}
	
	public void setTo(String to) {
		tvTo.setText(to);
	}
	
	public void setTimeFrom(String timeFrom) {
		tvTimeFrom.setText(timeFrom);
	}
	
	public void setTimeTo(String timeTo) {
		tvTimeTo.setText(timeTo);
	}
}
