package logic.view.fxml.registration;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.bean.UserBeanSingleton;
import logic.view.fxml.HomeView;

public class CheckIdentityView extends Application{

	UserBeanSingleton sg = UserBeanSingleton.getInstance();
	@FXML private Button btNext;
	@FXML private Button btHome;
	@FXML private TextField etCode;
	
	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Check_Registration.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

		
	@FXML
	public void homeButtonController() throws IOException {
		HomeView back = new HomeView();
		back.start((Stage) btHome.getScene().getWindow());
	}
	
	@FXML
	public void nextButtonController() throws Exception {
		String systemCode = sg.getCode();
		String userCode = etCode.getText();
		if(userCode.equals(systemCode)) {
			AddInfoView next = new AddInfoView();
			next.start((Stage) btNext.getScene().getWindow());
		}else {
			throw new Exception("Inserted code does not match with security code.");
		}
	}
}
