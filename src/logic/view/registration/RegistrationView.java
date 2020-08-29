package logic.view.registration;

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import logic.bean.UserBean;
import logic.bean.UserBeanSingleton;
import logic.controller.RegistrationController;
import logic.controller.exception.DatabaseException;
import logic.view.HomeView;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;


public class RegistrationView extends Application {
	@FXML private Button btHome;
	@FXML private Button btNext;
	@FXML private TextField userID;
	@FXML private Text txMessage;
	
	UserBeanSingleton usBean = UserBeanSingleton.getInstance();
	
	
	
	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/registration.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
	
	//TODO criptare la password
	
	@FXML
	public void nextButtonController() throws DatabaseException {
		try {

			RegistrationController reg = new RegistrationController();
			if(!reg.alreadyExist(userID.getText())) {
			UserBean user = reg.recapInfo(userID.getText().toString());
			usBean.setUserBean(user);
			RecapView recap = new RecapView();
			recap.start((Stage) btNext.getScene().getWindow());
			}else {
				txMessage.setText("Already registered");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	@FXML
	public void homeButtonController() throws IOException {
		HomeView home = new HomeView();
		home.start((Stage) btHome.getScene().getWindow());
	}

	public static void main(String[] args) {
		launch(args);
	}
}
