package logic.view.FXML.registration;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import logic.bean.UserInfo;
import logic.controller.RegistrationController;
import logic.view.FXML.HomeView;

public class EditInfoView extends Application{
	@FXML Button btHome, btNext, btBack;
	
	private UserInfo user;
	
	/*
	 * public EditInfoView(UserInfo newUser) { this.user = newUser; }
	 */
	
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		Parent root = FXMLLoader.load(getClass().getResource("Edit_DBInfo.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	@FXML
	public void btHomeController() throws IOException{
		HomeView home = new HomeView();
		home.start((Stage) btHome.getScene().getWindow());
	}
	
	public void btNextController() throws Exception{
		AddCarView addCar = new AddCarView();
		addCar.start((Stage) btNext.getScene().getWindow());
	}
	
	public void btBackController() throws Exception{
		RecapView recap = new RecapView(/*user*/);
		recap.start((Stage) btBack.getScene().getWindow());
	}
}
