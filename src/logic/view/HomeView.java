package logic.view;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import logic.view.registration.RegistrationView;

public class HomeView extends Application {
	@FXML
	private Button loginButton;
	@FXML
	private Button regButton;
	

	@Override
	public void start(Stage primaryStage)  {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("fxml/homepage.fxml"));
		
		Stage stage = primaryStage;
		Scene scene = new Scene(root);
		stage.setTitle("TorVerCar");
		stage.setScene(scene);
		stage.setResizable(false);

		stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void loginButtonController()  {
		LoginView login = new LoginView();
		login.start((Stage) loginButton.getScene().getWindow());
	}

	public void regButtonController() {
		RegistrationView register = new RegistrationView();
		register.start((Stage) regButton.getScene().getWindow());
	}

}
