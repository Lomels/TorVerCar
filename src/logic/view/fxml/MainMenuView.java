package logic.view.fxml;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.controller.LiftController;
import logic.model.Lift;
import logic.model.UserSingleton;
import logic.utilities.MyLogger;

public class MainMenuView extends Application implements Initializable {
	@FXML
	private Text tvWelcome;
	@FXML
	private Text tvName;
	@FXML
	private Button btHome;
	@FXML
	private Button btMyCar;
	@FXML
	private Button btProfile;
	@FXML
	private Button btLogout;
	@FXML
	private Button btOffer;
	@FXML
	private Button btBook;

	private UserSingleton sg = UserSingleton.getInstance();
	private String userID;
	private List<String> notifications;
	private List<Lift> completedLifts;
	private LiftController liftContr = new LiftController();


	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Home_menu.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

//		if (!(notifications = sg.getNotifications()).isEmpty()) {
			for(String s : notifications) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Notification");
				alert.setHeaderText("Look, an Information Dialog");
				alert.setContentText(s);
				alert.showAndWait();
			}
//		}

		if (!(completedLifts.isEmpty())) {
			// TODO: check lift completati
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String welcome = null;
		switch (sg.getRole()) {
		case DRIVER:
			welcome = sg.getStudentCar().getName().toString() + "!";
			userID = sg.getStudentCar().getUserID();
			break;
		case STUDENT:
			welcome = sg.getStudent().getName().toString() + "!";
			userID = sg.getStudent().getUserID();
			break;
		case ADMIN:
			// TODO: implementare
			break;
		}
		tvName.setText(welcome);
		sg.setNotifications(liftContr.loadNotifications(userID));

		completedLifts = liftContr.checkCompletedLift(userID);
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void homeButtonController() throws Exception {
		MainMenuView home = new MainMenuView();
		home.start((Stage) btHome.getScene().getWindow());
	}

	public void myCarButtonController() throws Exception {
		MyCarView car = new MyCarView();
		car.start((Stage) btMyCar.getScene().getWindow());
	}

	public void profileButtonController() throws Exception {
		ProfileView profile = new ProfileView();
		profile.start((Stage) btProfile.getScene().getWindow());
	}

	public void logoutButtonController() throws IOException {
		HomeView home = new HomeView();
		home.start((Stage) btLogout.getScene().getWindow());
	}

	public void offerButtonController() throws Exception {
		OfferView offer = new OfferView();
		offer.start((Stage) btOffer.getScene().getWindow());
	}

	public void bookButtonController() throws Exception {
		BookView book = new BookView();
		book.start((Stage) btBook.getScene().getWindow());
	}
}
