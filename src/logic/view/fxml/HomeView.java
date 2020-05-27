package logic.view.fxml;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import logic.view.fxml.registration.RegistrationView;

public class HomeView extends Application {
	@FXML
	private Button loginButton;
	@FXML
	private Button regButton;
	

	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("homepage.fxml"));
		Stage stage = primaryStage;
		Scene scene = new Scene(root);
		stage.setTitle("TorVerCar");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void loginButtonController() throws Exception {
		LoginView login = new LoginView();
		login.start((Stage) loginButton.getScene().getWindow());
	}

	public void regButtonController() throws IOException {
		RegistrationView register = new RegistrationView();
		register.start((Stage) regButton.getScene().getWindow());
	}

}
