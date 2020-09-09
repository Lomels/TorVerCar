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
	private ViewController view = new ViewController();


	
	public void start(Stage primaryStage) throws IOException {
		view.start("fxml/homepage.fxml", primaryStage);
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
