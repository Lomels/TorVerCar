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

public class ProfileView extends Application implements Initializable{
	private Text txName;
	private Text txSurname;
	private Text txMatNum;
	private Text tfEmail;
	private Text tfPass;
	private Text tfPhone;
	private Button btHome;
	private Button btBack;
	private Button btProfile;
	private Button btMyCar;
	private Button btSave;
	private Button btEdit;
	private Button btLogout;
	
	
	UserSingleton sg = UserSingleton.getInstance();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		txName.setText(sg.getStudent().getName().toString());
		txSurname.setText(sg.getStudent().getSurname().toString());
		txMatNum.setText(sg.getStudent().getUserID().toString());
		tfPhone.setText(sg.getStudent().getPhone().toString());
		tfEmail.setText(sg.getStudent().getEmail().toString());
		tfPass.setText(sg.getStudent().getPassword().toString());
		
		tfPhone.setDisable(true);
		tfEmail.setDisable(true);
		tfPass.setDisable(true);
		
		
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Profile_page.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@FXML
	public void homeButtonController() throws IOException {
		HomeView home = new HomeView();
		home.start((Stage) btHome.getScene().getWindow());
	}
	
	@FXML
	public void backButtonController() throws Exception {
		ProfileView profile = new ProfileView();
		profile.start((Stage) btBack.getScene().getWindow());
	}

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
	
	@FXML
	public void editButtonController() throws Exception {
		ProfileView profile = new ProfileView();
		profile.start((Stage) btEdit.getScene().getWindow());
		
		tfPhone.setDisable(false);
		tfEmail.setDisable(false);
		tfPass.setDisable(false);
		
		
	}
	
	public void saveButtonController() throws Exception {
		sg.getStudent().setPhone(tfPhone.getText());
		sg.getStudent().setEmail(tfEmail.getText());
		sg.getStudent().setPassword(tfPass.getText());
		
		ProfileView profile = new ProfileView();
		profile.start((Stage) btSave.getScene().getWindow());
		
	}
}
