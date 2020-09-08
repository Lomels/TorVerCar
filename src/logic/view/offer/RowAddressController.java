package logic.view.offer;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class RowAddressController {
	@FXML private Text tvAddress;
	@FXML private ImageView ivMap;
	
	public void setAddress(String address) {
		tvAddress.setText(address);
	}
	
	public void setMap(String url) {
		Image image = new Image(url);
		ivMap.setImage(image);
		
	}
	
	
}
