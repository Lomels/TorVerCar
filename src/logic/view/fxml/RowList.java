package logic.view.fxml;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class RowList {
	@FXML private Text tvSet;
	@FXML private ImageView ivMap;
	
	public void setAddress(String address) {
		tvSet.setText(address);
	}
	
	public void setMap(String URL) {
		ivMap.setImage(null);
		
	}
	
	
}
