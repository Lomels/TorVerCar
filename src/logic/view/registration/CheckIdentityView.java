package logic.view.registration;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import logic.bean.UserBeanSingleton;
import logic.view.HomeView;
import logic.view.ViewController;

public class CheckIdentityView extends Application{

	UserBeanSingleton sg = UserBeanSingleton.getInstance();
	@FXML private Button btNext;
	@FXML private Button btHome;
	@FXML private TextField etCode;
	
	private ViewController view = new ViewController();
	
	@Override
	public void start(Stage primaryStage){
		view.start("fxml/Check_Registration.fxml", primaryStage);
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
