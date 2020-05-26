package logic.view.FXML.registration;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.controller.exception.InvalidInputException;
import logic.model.Singleton;
import logic.model.Student;
import logic.view.FXML.HomeView;

public class AddInfoView extends Application{
	@FXML private Button btHome;
	@FXML private Button btFinish;
	@FXML private Button btCar;
	@FXML private TextField etPassword;
	@FXML private TextField etRepeat;
	@FXML private TextField etPhone;
	@FXML private TextField tfError;
	
	Singleton sg = Singleton.getInstance();
	
	
	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Add_info_registration.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();	
	}
	
	@FXML
	public void finishButtonController() throws Exception, InvalidInputException {
		Student student = sg.getUser();
		if(etPassword.getText().equals(etRepeat.getText())) {
			student.setPassword(etPassword.getText());
			student.setPhone(etPhone.getText());
			RecapView finish = new RecapView();
			finish.start((Stage) btFinish.getScene().getWindow());
		}else {
			tfError.setVisible(true);
		}
	}
	
	@FXML void carButtonController() {
		
	}
	
	@FXML
	public void homeButtonController() throws IOException {
		HomeView home = new HomeView();
		home.start((Stage) btHome.getScene().getWindow());
	}
	

}
