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

public class RecapView extends Application{
	@FXML private Button btHome;
	@FXML private Button btNext;
	@FXML private Button btEdit;
	
	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("Recap_database_info.fxml"));
		Scene scene = new Scene(root);
		stage.setTitle("Recap");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	@FXML
	public void btNextController() throws Exception {
		
	}
	
	@FXML
	public void btHomeController() throws IOException {
		HomeView home = new HomeView();
		home.start((Stage) btHome.getScene().getWindow());
	}
	
	@FXML
	public void btEditController() throws Exception {
		EditInfoView edit = new EditInfoView();
		edit.start((Stage) btNext.getScene().getWindow());
	}
}
