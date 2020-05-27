package logic.view.fxml.registration;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import logic.view.fxml.HomeView;

public class AddCarView extends Application{
	Stage stage;
	@FXML Button btHome, btNext, btBack;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Parent root = FXMLLoader.load(getClass().getResource("Add_car_info.fxml"));
		this.stage = primaryStage;
		Scene scene = new Scene(root);
		stage.setTitle("Add Car Infos");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	@FXML
	public void btNextController() {
		
	}
	
	@FXML
	public void btHomeController() throws IOException {
		HomeView home = new HomeView();
		home.start((Stage) btHome.getScene().getWindow());
	}
	
	@FXML
	public void btBackController() {
		
	}
}
