package logic.view.registration;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.bean.UserBean;
import logic.bean.UserBeanSingleton;
import logic.controller.LoginController;
import logic.controller.RegistrationController;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.ExceptionHandler;
import logic.controller.exception.InvalidInputException;
import logic.model.Role;
import logic.view.HomeView;
import logic.view.MainMenuView;
import logic.view.ViewController;

public class AddInfoView extends Application{
	@FXML private Button btHome;
	@FXML private Button btFinish;
	@FXML private Button btCar;
	@FXML private TextField etPassword;
	@FXML private TextField etRepeat;
	@FXML private TextField etPhone;
	@FXML private Text tfError;
	
	UserBeanSingleton sg = UserBeanSingleton.getInstance();
	private ViewController view = new ViewController();
	
	@Override
	public void start(Stage primaryStage){
		view.start("fxml/Add_info_registration.fxml", primaryStage);
	}
	
	@FXML
	public void finishButtonController()   {
		UserBean user = sg.getUserBean();
		if(etPassword.getText().equals(etRepeat.getText())) {
			user.setPassword(etPassword.getText());
			user.setPhone(etPhone.getText());
			user.setRole(Role.STUDENT);
			RegistrationController controller = new RegistrationController();
			LoginController login = new LoginController();
			try {
				controller.addStudent(user);
				login.login(user);
			} catch (DatabaseException | InvalidInputException e) {
				ExceptionHandler.handle(e);
			}
			MainMenuView finish = new MainMenuView();
			finish.start((Stage) btFinish.getScene().getWindow());
		}else {
			tfError.setVisible(true);
		}
	}
	
	@FXML void carButtonController()  {
		if(etPassword.getText().equals(etRepeat.getText())) {
			sg.getUserBean().setPassword(etPassword.getText());
			sg.getUserBean().setPhone(etPhone.getText());
			sg.getUserBean().setRole(Role.DRIVER);
			
			AddCarView car = new AddCarView();
			car.start((Stage)btCar.getScene().getWindow());
		}else {
			tfError.setVisible(true);
		}
	}
	
	@FXML
	public void homeButtonController()  {
		HomeView home = new HomeView();
		home.start((Stage) btHome.getScene().getWindow());
	}

}
