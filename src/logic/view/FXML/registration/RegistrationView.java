package logic.view.FXML.registration;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import logic.controller.RegistrationController;
import logic.view.FXML.HomeView;


public class RegistrationView extends Application {
	@FXML private Button homeButton;
	@FXML private TextField userID;
	@FXML private TextField password;
	@FXML private TextField repeat;
	@FXML private Text error;
	
	private RegistrationController control = new RegistrationController();
	
	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("registration.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
	
	//TODO criptare la password
	
	@FXML
	public void nextButtonController() throws Exception {
		try {
			if(password.getText().contentEquals(repeat.getCharacters())) {
				control.createStudent(userID.getText(), password.getText());
		}else {
				throw new Exception("Le password non combaciano, riprova.");
			}
		}catch(Exception e){
			error.setText(e.getMessage());			
		}
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