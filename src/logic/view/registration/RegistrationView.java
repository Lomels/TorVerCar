package logic.view.registration;

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import logic.bean.UserBean;
import logic.bean.UserBeanSingleton;
import logic.controller.RegistrationController;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.ExceptionHandler;
import logic.controller.exception.InvalidInputException;
import logic.view.HomeView;
import logic.view.ViewController;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;

public class RegistrationView extends Application {
	@FXML
	private Button btHome;
	@FXML
	private Button btNext;
	@FXML
	private TextField userID;
	@FXML
	private Text txMessage;

	UserBeanSingleton usBean = UserBeanSingleton.getInstance();
	private ViewController view = new ViewController();
	
	@Override
	public void start(Stage stage) throws IOException {
		view.start("fxml/registration.fxml", stage);
	}


	@FXML
	public void nextButtonController() {
		RegistrationController reg = new RegistrationController();
		UserBean startUser = new UserBean();
		startUser.setUserID(userID.getText());
			try {
				if (!reg.alreadyExist(startUser)) {
					UserBean user = reg.recapInfo(startUser);
					usBean.setUserBean(user);
					RecapView recap = new RecapView();
					recap.start((Stage) btNext.getScene().getWindow());
				} else {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setHeaderText("Oops!");
					alert.setContentText("A user with this Student ID is already registered.");
					alert.showAndWait(); 
				}
			} catch (DatabaseException | InvalidInputException e) {
				ExceptionHandler.handle(e);
			} catch (Exception e) {
				e.printStackTrace();
			}

		
	}

	@FXML
	public void homeButtonController()  {
		HomeView home = new HomeView();
		home.start((Stage) btHome.getScene().getWindow());
	}

	public static void main(String[] args) {
		launch(args);
	}
}
