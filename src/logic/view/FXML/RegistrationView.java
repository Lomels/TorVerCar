package logic.view.FXML;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.Button;



public class RegistrationView extends Application {
	@FXML private Button homeButton;
	
	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("registration.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
	
	@FXML
	public void buttonController() {
		System.out.println("OK");
	}
	
	@FXML
	public void homeButtonController() throws IOException {
		HomeView home = new HomeView();
		home.start((Stage) homeButton.getScene().getWindow());
	}

	public static void main(String[] args) {
		launch(args);
	}
}
