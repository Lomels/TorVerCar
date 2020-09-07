package logic.view;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.controller.LiftController;
import logic.controller.LoginController;
import logic.controller.RatingController;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.model.Lift;
import logic.model.UserSingleton;
import logic.utilities.MyLogger;
import logic.view.booking.BookView;
import logic.view.offer.OfferView;

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
	@FXML
	private Button btLifts;

	private UserSingleton sg = UserSingleton.getInstance();
	private String userID;
	private List<String> notifications;
	private List<Lift> completedLifts;
	private LiftController liftContr = new LiftController();

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/Home_menu.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();

		if (!(notifications = sg.getNotifications()).isEmpty()) {
			showNotifications(notifications.size());
		}

		if (!(completedLifts = sg.getCompletedLift()).isEmpty()) {
			MyLogger.info("completed lifts", completedLifts);
			showCompletedLifts(completedLifts.size());
		}
	}

	// TODO: assolutamente mostrare a schermo queste eccezioni
	private void showCompletedLifts(int size) throws InvalidInputException, DatabaseException {
		int i = 0;
		
		String format = new String("Please tell us if you enjoyed the ride, click on Show Details for further infos about the Lift");
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Completed lift");
		alert.setHeaderText("Did you enjoyed the ride?");
		alert.setContentText(format);
		
		ButtonType btYes = new ButtonType("YES", ButtonData.YES);
		ButtonType btNo = new ButtonType("NO", ButtonData.NO);

		alert.getButtonTypes().setAll(btYes, btNo);

		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("fxml/TorVerCar.css").toExternalForm());
		dialogPane.getStyleClass().add("myDialog");
				
		format = new String("Lift arrived to %s.\nDeparted at %s.\nOffered by %s.");
		
		Text textArea = new Text();
		GridPane content = new GridPane();
	    content.setMaxWidth(Double.MAX_VALUE);
	    content.add(textArea, 0, 0);

	    alert.getDialogPane().setExpandableContent(content);
		
		do {
			Lift lift = completedLifts.get(i);
			MyLogger.info("Driver id", lift.getDriver().getUserID());
			
			String message = String.format(format, lift.getRoute().getDropoffPosition().getAddress(), lift.getStopDateTime().toString(),
					lift.getDriver().getName() + " " + lift.getDriver().getSurname());
			textArea.setText(message);
			MyLogger.info("message", message);

			Optional<ButtonType> result = alert.showAndWait(); 
			RatingController ratingController = new RatingController();
			if (result.get() == btYes) {
				ratingController.upvoteLift(sg.getUserID(), lift.getLiftID(), lift.getDriver());
				MyLogger.info("yes");
			} else if (result.get() == btNo) {
				ratingController.downvote(sg.getUserID(), lift.getLiftID(), lift.getDriver());
				MyLogger.info("no");
			} 
			i++;
		}while(i < size && i >= 0);
	}

	public void showNotifications(int index) {
		int i = 0;
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Notification");
		alert.setHeaderText("Look, an Information Dialog");
		alert.setContentText(notifications.get(i));
		ButtonType btNext = new ButtonType("NEXT", ButtonData.NEXT_FORWARD);
		ButtonType btBack = new ButtonType("BACK", ButtonData.BACK_PREVIOUS);
		ButtonType btClose = new ButtonType("CLOSE", ButtonData.CANCEL_CLOSE);

		alert.getButtonTypes().setAll(btNext, btBack, btClose);

		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("fxml/TorVerCar.css").toExternalForm());
		dialogPane.getStyleClass().add("myDialog");

		do {
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == btNext) {
				i++;
				alert.setContentText(notifications.get(i));
			} else if (result.get() == btBack) {
				i--;
				alert.setContentText(notifications.get(i));
			} else {
				break;
			}
		} while ((i < index && i >= 0));

		liftContr.flushNotification(sg.getUserID());

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String welcome = null;
		switch (sg.getRole()) {
		case DRIVER:
			welcome = sg.getStudentCar().getName() + "!";
			userID = sg.getUserID();
			break;
		case STUDENT:
			welcome = sg.getStudent().getName() + "!";
			userID = sg.getUserID();
			break;
		default:
			//TODO: visualizza errore poiché non esiste il Role richiesto
			break;
		}
		tvName.setText(welcome);
		sg.setNotifications(liftContr.loadNotifications(userID));

		sg.setCompletedLift(liftContr.checkCompletedLift(userID));
	}

	@FXML
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
	public void myCarButtonController() throws Exception {
		MyCarView car = new MyCarView();
		car.start((Stage) btMyCar.getScene().getWindow());
	}

	@FXML
	public void profileButtonController() throws Exception {
		ProfileView profile = new ProfileView();
		profile.start((Stage) btProfile.getScene().getWindow());
	}

	@FXML
	public void logoutButtonController() throws IOException {
		try {
			LoginController.logout();
		} catch (Exception e) {
			e.printStackTrace();
		}
		HomeView home = new HomeView();
		home.start((Stage) btLogout.getScene().getWindow());
	}

	@FXML
	public void offerButtonController() throws Exception {
		OfferView offer = new OfferView();
		offer.start((Stage) btOffer.getScene().getWindow());
	}

	@FXML
	public void bookButtonController() throws Exception {
		BookView book = new BookView();
		book.start((Stage) btBook.getScene().getWindow());
	}
}
