package logic.view.FXML.registration;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.controller.exception.InvalidInputException;
import logic.model.Singleton;
import logic.model.Student;
import logic.view.FXML.HomeView;
import logic.view.FXML.MainMenuView;

public class AddInfoView extends Application{
	@FXML private AnchorPane paneAdd;
	@FXML private AnchorPane paneSummary;
	@FXML private Button btHome;
	@FXML private Button btFinish;
	@FXML private Button btCar;
	@FXML private TextField etPassword;
	@FXML private TextField etRepeat;
	@FXML private TextField etPhone;
	@FXML private Text tfError;
	@FXML private Text txId;
	@FXML private Text txFirst;
	@FXML private Text txLast;
	@FXML private Text txEmail;
	@FXML private Text txPhone;
	
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
			/*RecapView finish = new RecapView();
			finish.start((Stage) btFinish.getScene().getWindow());*/
			MainMenuView main = new MainMenuView();
			main.start((Stage) btFinish.getScene().getWindow());
		}else {
			tfError.setVisible(true);
		}
	}
	
	@FXML void carButtonController() throws Exception {
		AddCarView car = new AddCarView();
		car.start((Stage)btCar.getScene().getWindow());
	}
	
	@FXML
	public void homeButtonController() throws IOException {
		HomeView home = new HomeView();
		home.start((Stage) btHome.getScene().getWindow());
	}
	
	@FXML
	public void back1ButtonController() {
		
	}
	
	@FXML
	public void back2ButtonController() {
		paneSummary.setVisible(false);
		paneAdd.setVisible(true);
	}
	
	@FXML
	public void nextButtonController() {
		Student user = sg.getUser();
		paneAdd.setVisible(false);
		paneSummary.setVisible(true);
		txId.setText(user.getUserID());
		txFirst.setText(user.getName());
		txLast.setText(user.getSurname());
		txEmail.setText(user.getEmail());
		txPhone.setText(user.getPhone());
	}

}
