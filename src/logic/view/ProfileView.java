package logic.view;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.bean.UserBean;
import logic.controller.ProfileController;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.ExceptionHandler;
import logic.controller.exception.InvalidInputException;
import logic.controller.exception.InvalidStateException;
import logic.controller.exception.NoRoleFound;
import logic.model.UserSingleton;

public class ProfileView extends ViewController implements Initializable {
	@FXML
	private TextField txName;
	@FXML
	private TextField txSurname;
	@FXML
	private TextField txMatNum;
	@FXML
	private TextField tfEmail;
	@FXML
	private TextField tfPass;
	@FXML
	private TextField tfPhone;
	@FXML
	private PasswordField pfHidden;

	@FXML
	private CheckBox cbShow;

	@FXML
	private Button btBack;
	
	@FXML
	private Button btSave;
	@FXML
	private Button btEdit;
	@FXML
	private Button btDelete;

	UserSingleton sg = UserSingleton.getInstance();
	ProfileController controller = new ProfileController();
	String userID;

	private ViewController view = new ViewController();
	

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
			txName.setText(sg.getStudent().getName());
			txSurname.setText(sg.getStudent().getSurname());
			txMatNum.setText(sg.getStudent().getUserID());
			tfPhone.setText(sg.getStudent().getPhone());
			tfEmail.setText(sg.getStudent().getEmail());
			tfPass.setText(sg.getStudent().getPassword());
			userID = sg.getStudent().getUserID();
			break;

		case DRIVER:
			txName.setText(sg.getStudentCar().getName());
			txSurname.setText(sg.getStudentCar().getSurname());
			txMatNum.setText(sg.getStudentCar().getUserID());
			tfPhone.setText(sg.getStudentCar().getPhone());
			tfEmail.setText(sg.getStudentCar().getEmail());
			tfPass.setText(sg.getStudentCar().getPassword());
			userID = sg.getStudentCar().getUserID();
			break;

		default:
			throw new NoRoleFound();
		}

		tfPhone.setDisable(true);
		tfEmail.setDisable(true);
		tfPass.setDisable(true);

	}

	@Override

	public void start(Stage stage){
		view.start("fxml/Profile_page.fxml", stage);
		
	}

	public static void main(String[] args) {
		launch(args);
	}

	@FXML
	public void backButtonController()  {
		ProfileView profile = new ProfileView();
		profile.start((Stage) btBack.getScene().getWindow());
	}

	@FXML
	public void deleteButtonController() throws DatabaseException {
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

		UserBean usr = new UserBean();
		usr.setUserID(sg.getUserID());
		if (result.isPresent()) {
			if (result.get() == btYes) {
				controller.deleteProfile(usr);
				HomeView newHome = new HomeView();
				newHome.start((Stage) btDelete.getScene().getWindow());

			} else if (result.get() == btNo) {
				alert.close();
			}
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
	public void saveButtonController() {
		UserBean usr = new UserBean();
		usr.setEmail(tfEmail.getText());
		usr.setPhone(tfPhone.getText());
		usr.setPassword(tfPass.getText());
		usr.setUserID(sg.getUserID());
		try {
			switch (sg.getRole()) {
			case STUDENT:
				controller.edit(usr);
				break;
			case DRIVER:
				controller.edit(usr);
				break;
			default:
				tfPhone.setDisable(true);
				tfEmail.setDisable(true);
				tfPass.setDisable(true);
			}
		} catch (InvalidInputException | DatabaseException | InvalidStateException e) {
			ExceptionHandler.handle(e);
		}
	}
}
