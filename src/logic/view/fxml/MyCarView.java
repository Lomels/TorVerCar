package logic.view.fxml;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.model.UserSingleton;

public class MyCarView extends Application implements Initializable{
	@FXML private Button btBack;
	@FXML private Button btEdit;
	@FXML private Button btHome;
	@FXML private Button btProfile;
	@FXML private Button btLogout;
	@FXML private Button btMyCar;
	
	@FXML private Text txModel;
	@FXML private Text txColor;
	@FXML private Text txLicense;
	@FXML private Text txPlaces;

	UserSingleton sg = UserSingleton.getInstance();
	
	
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader(getClass().getResource("My_car_page.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		
		stage.setScene(scene);
		stage.show();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@FXML
	public void homeButtonController() throws IOException {
		HomeView home = new HomeView();
		home.start((Stage) btHome.getScene().getWindow());
	}
	
	/*@FXML
	public void backButtonController() throws Exception {
		ProfileView profile = new ProfileView();
		profile.start((Stage) btBack.getScene().getWindow());
	}*/

	@FXML
	public void profileButtonController() throws Exception {
		ProfileView profile = new ProfileView();
		profile.start((Stage) btProfile.getScene().getWindow());
	}
	
	@FXML
	public void myCarButtonController() throws Exception {
		MyCarView myCar = new MyCarView();
		myCar.start((Stage) btMyCar.getScene().getWindow());
	}
	
	@FXML
	public void logoutButtonController() throws Exception {
		LoginView login = new LoginView();
		login.start((Stage) btLogout.getScene().getWindow());
	}
	
}


