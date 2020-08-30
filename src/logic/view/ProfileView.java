package logic.view;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.controller.LoginController;
import logic.controller.ProfileController;
import logic.controller.RatingController;
import logic.controller.exception.DatabaseException;
import logic.model.UserSingleton;
import logic.utilities.MyLogger;
import logic.view.booking.BookView;
import logic.view.offer.OfferView;

public class ProfileView extends Application implements Initializable {
	@FXML private Text txName;
	@FXML private Text txSurname;
	@FXML private Text txMatNum;
	@FXML private TextField tfEmail;
	@FXML private TextField tfPass;
	@FXML private TextField tfPhone;
	@FXML private PasswordField pfHidden;
	
	@FXML private CheckBox cbShow;
	
	@FXML private Button btHome;
	@FXML private Button btBack;
	@FXML private Button btProfile;
	@FXML private Button btMyCar;
	@FXML private Button btSave;
	@FXML private Button btEdit;
	@FXML private Button btLogout;
	@FXML private Button btOffer;
	@FXML private Button btBook;
	@FXML private Button btLifts;
	@FXML private Button btDelete;

	UserSingleton sg = UserSingleton.getInstance();
	ProfileController controller = new ProfileController();
	String userID;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		tfPass.setManaged(false);
		tfPass.setVisible(false);
		
		
		tfPass.managedProperty().bind(cbShow.selectedProperty());
		tfPass.visibleProperty().bind(cbShow.selectedProperty());
		
		pfHidden.managedProperty().bind(cbShow.selectedProperty().not());
		pfHidden.visibleProperty().bind(cbShow.selectedProperty().not());
		pfHidden.setDisable(true);
		tfPass.textProperty().bindBidirectional(pfHidden.textProperty());
		
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
		FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/Profile_page.fxml"));
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
	public void liftsButtonController() throws Exception {
		MyLiftView myLift = new MyLiftView();
		myLift.start((Stage) btLifts.getScene().getWindow());
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
		try {
			LoginController.logout();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LoginView login = new LoginView();
		login.start((Stage) btLogout.getScene().getWindow());
	}
	
	public void offerButtonController() throws Exception {
		OfferView offer = new OfferView();
		offer.start((Stage) btOffer.getScene().getWindow());
	}
	
	@FXML
	public void deleteButtonController() throws DatabaseException, IOException {
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete profile");
		alert.setHeaderText("Warning!");
		alert.setContentText("Are you sure you want to delete your profile?");
		
		ButtonType btYes = new ButtonType("YES", ButtonData.YES);
		ButtonType btNo = new ButtonType("NO", ButtonData.NO);

		alert.getButtonTypes().setAll(btYes, btNo);

		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("fxml/TorVerCar.css").toExternalForm());
		dialogPane.getStyleClass().add("myDialog");
		
		Optional<ButtonType> result = alert.showAndWait(); 

		if (result.get() == btYes) {
			controller.deleteProfile(sg.getUserID());
			HomeView newHome = new HomeView();
			newHome.start((Stage) btDelete.getScene().getWindow());
			
		} else if (result.get() == btNo) {
			alert.close();
			
		} 
		
	}
	

	@FXML
	public void editButtonController() {
		pfHidden.setDisable(false);
		tfPhone.setDisable(false);
		tfEmail.setDisable(false);
		tfPass.setDisable(false);
		
		tfPass.setManaged(false);
		tfPass.setVisible(false);
		
		tfPass.managedProperty().bind(cbShow.selectedProperty());
		tfPass.visibleProperty().bind(cbShow.selectedProperty());
		
		pfHidden.managedProperty().bind(cbShow.selectedProperty().not());
		pfHidden.visibleProperty().bind(cbShow.selectedProperty().not());

		tfPass.textProperty().bindBidirectional(pfHidden.textProperty());
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
