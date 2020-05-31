package logic.view.fxml.registration;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.bean.UserBean;
import logic.bean.UserBeanSingleton;
import logic.controller.LoginController;
import logic.controller.RegistrationController;
import logic.controller.exception.InvalidInputException;
import logic.view.fxml.HomeView;
import logic.view.fxml.MainMenuView;

public class AddInfoView extends Application{
	@FXML private Button btHome;
	@FXML private Button btFinish;
	@FXML private Button btCar;
	@FXML private TextField etPassword;
	@FXML private TextField etRepeat;
	@FXML private TextField etPhone;
	@FXML private Text tfError;
	
	UserBeanSingleton sg = UserBeanSingleton.getInstance();
	
	
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
		UserBean user = sg.getUserBean();
		if(etPassword.getText().equals(etRepeat.getText())) {
			user.setPassword(etPassword.getText());
			user.setPhone(etPhone.getText());
			RegistrationController controller = new RegistrationController();
			controller.addStudent(user);
			LoginController login = new LoginController();
			login.login(user);
			MainMenuView finish = new MainMenuView();
			finish.start((Stage) btFinish.getScene().getWindow());
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

}