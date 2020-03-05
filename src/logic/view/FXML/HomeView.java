package logic.view.FXML;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HomeView extends Application {
	@FXML private Button loginButton;
	@FXML private Button regButton;
	private Stage stage;
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("homepage.fxml"));
		this.stage = primaryStage;
		Scene scene = new Scene(root);
		stage.setTitle("TorVerCar");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	public void loginButtonController() throws Exception {
		System.out.println("Login\n");
		LoginView login = new LoginView();
		login.start((Stage) loginButton.getScene().getWindow());
	}
	
	public void regButtonController() throws IOException {
		System.out.println("register\n");

		RegistrationView register = new RegistrationView();
		register.start((Stage) regButton.getScene().getWindow());
	}
	
}
