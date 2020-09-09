package logic.view.registration;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import logic.bean.UserBeanSingleton;
import logic.view.HomeView;

public class CheckIdentityView extends Application{

	UserBeanSingleton sg = UserBeanSingleton.getInstance();
	@FXML private Button btNext;
	@FXML private Button btHome;
	@FXML private TextField etCode;
	
	@Override
	public void start(Stage stage)  {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/Check_Registration.fxml"));
		Parent root;
		try {
			root = loader.load();
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);

		stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

		
	@FXML
	public void homeButtonController()  {
		HomeView back = new HomeView();
		back.start((Stage) btHome.getScene().getWindow());
	}
	
	@FXML
	public void nextButtonController(){
		String systemCode = sg.getCode();
		String userCode = etCode.getText();
		if(userCode.equals(systemCode)) {
			AddInfoView next = new AddInfoView();
			try {
				next.start((Stage) btNext.getScene().getWindow());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Oops!");
			alert.setContentText("Inserted code does not match with security code.");
			alert.showAndWait(); 
		}
	}
}
