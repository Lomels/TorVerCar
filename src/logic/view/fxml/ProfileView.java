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
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.controller.ProfileController;
import logic.model.UserSingleton;

public class ProfileView extends Application implements Initializable {
	@FXML private Text txName;
	@FXML private Text txSurname;
	@FXML private Text txMatNum;
	@FXML private TextField tfEmail;
	@FXML private TextField tfPass;
	@FXML private TextField tfPhone;
	
	@FXML private Button btHome;
	@FXML private Button btBack;
	@FXML private Button btProfile;
	@FXML private Button btMyCar;
	@FXML private Button btSave;
	@FXML private Button btEdit;
	@FXML private Button btLogout;
	@FXML private Button btOffer;
	@FXML private Button btBook;

	UserSingleton sg = UserSingleton.getInstance();
	ProfileController controller = new ProfileController();
	String userID;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		switch (sg.getRole()) {
		case STUDENT:
			txName.setText(sg.getStudent().getName().toString());
			txSurname.setText(sg.getStudent().getSurname().toString());
			txMatNum.setText(sg.getStudent().getUserID().toString());
			tfPhone.setText(sg.getStudent().getPhone().toString());
			tfEmail.setText(sg.getStudent().getEmail().toString());
			tfPass.setText(sg.getStudent().getPassword().toString());
			userID = sg.getStudent().getUserID();
			break;
			
		case DRIVER:
			txName.setText(sg.getStudentCar().getName().toString());
			txSurname.setText(sg.getStudentCar().getSurname().toString());
			txMatNum.setText(sg.getStudentCar().getUserID().toString());
			tfPhone.setText(sg.getStudentCar().getPhone().toString());
			tfEmail.setText(sg.getStudentCar().getEmail().toString());
			tfPass.setText(sg.getStudentCar().getPassword().toString());
			userID = sg.getStudentCar().getUserID();
			break;
		}

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
	public void homeButtonController() throws Exception {
		MainMenuView home = new MainMenuView();
		home.start((Stage) btHome.getScene().getWindow());
	}
	
	@FXML
	public void bookButtonController() throws Exception {
		BookView book = new BookView();
		book.start((Stage) btBook.getScene().getWindow());
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
	
	public void offerButtonController() throws Exception {
		OfferView offer = new OfferView();
		offer.start((Stage) btOffer.getScene().getWindow());
	}
	

	@FXML
	public void editButtonController() {
		tfPhone.setDisable(false);
		tfEmail.setDisable(false);
		tfPass.setDisable(false);
	}

	@FXML
	public void saveButtonController() throws Exception {
		switch (sg.getRole()) {
		case STUDENT:
			controller.edit(sg.getStudent().getUserID(), tfEmail.getText(), tfPhone.getText(), tfPass.getText());
			break;
		case DRIVER:
			controller.edit(sg.getStudentCar().getUserID(), tfEmail.getText(), tfPhone.getText(), tfPass.getText());
			break;
		default:
			tfPhone.setDisable(true);
			tfEmail.setDisable(true);
			tfPass.setDisable(true);
		}
	}
}
