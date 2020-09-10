package logic.view;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.bean.LiftBean;
import logic.bean.UserBean;
import logic.controller.LiftController;
import logic.controller.RatingController;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.ExceptionHandler;
import logic.controller.exception.InvalidInputException;
import logic.controller.exception.InvalidStateException;
import logic.model.Lift;
import logic.model.UserSingleton;
import logic.utilities.MyLogger;

public class MainMenuView extends ViewController implements Initializable {
	@FXML
	private Text tvWelcome;
	@FXML
	private Text tvName;

	private UserSingleton sg = UserSingleton.getInstance();
	private UserBean userBean = new UserBean();
	private List<String> notifications;
	private List<Lift> completedLifts;
	private LiftController liftContr = new LiftController();
		
	@Override
	public void start(Stage stage){
		super.start("fxml/Home_menu.fxml", stage);		
		notifications = sg.getNotifications();
		if (!notifications.isEmpty()) {
			try {
				showNotifications(notifications.size());
			} catch (DatabaseException e) {
				ExceptionHandler.handle(e);
			}
		}

		completedLifts = sg.getCompletedLift();
		if (!completedLifts.isEmpty()) {
			MyLogger.info("completed lifts", completedLifts);
			showCompletedLifts(completedLifts.size());
		}
	}

	private void showCompletedLifts(int size) {
		int i = 0;

		String format = "Please tell us if you enjoyed the ride, click on Show Details for further infos about the Lift";

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

		format = "Lift arrived to %s.\nDeparted at %s.\nOffered by %s.";

		Text textArea = new Text();
		GridPane content = new GridPane();
		content.setMaxWidth(Double.MAX_VALUE);
		content.add(textArea, 0, 0);

		alert.getDialogPane().setExpandableContent(content);

		do {
			Lift lift = completedLifts.get(i);
			MyLogger.info("Driver id", lift.getDriver().getUserID());

			String message = String.format(format, lift.getRoute().getDropoffPosition().getAddress(),
					lift.getStopDateTime().toString(),
					lift.getDriver().getName() + " " + lift.getDriver().getSurname());
			textArea.setText(message);
			MyLogger.info("message", message);

			Optional<ButtonType> result = alert.showAndWait();
			RatingController ratingController = new RatingController();
			
			
			LiftBean liftBean = new LiftBean();
			liftBean.setDriver(lift.getDriver());
			liftBean.setLiftID(lift.getLiftID());
			try {
				if (result.isPresent()) {
					if (result.get() == btYes) {
						ratingController.upvoteLift(userBean, liftBean);
					} else if (result.get() == btNo) {
						ratingController.downvote(userBean, liftBean);
					}
				}
			} catch (InvalidInputException | DatabaseException e) {
				ExceptionHandler.handle(e);
			}
			i++;
		} while (i < size && i >= 0);
	}

	public void showNotifications(int index) throws DatabaseException {
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
			if (result.isPresent()) {
				if (result.get() == btNext) {
					i++;
					alert.setContentText(notifications.get(i));
				} else if (result.get() == btBack) {
					i--;
					alert.setContentText(notifications.get(i));
				} else {
					break;
				}
			}
		} while ((i < index && i >= 0));

		liftContr.flushNotification(userBean);

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String welcome = null;
		switch (sg.getRole()) {
		case DRIVER:
			welcome = sg.getStudentCar().getName() + "!";
			break;
		case STUDENT:
			welcome = sg.getStudent().getName() + "!";
			break;
		default:
			ExceptionHandler.handle(
					new InvalidStateException("Something went wrong (invalid ROLE), please restart the application."));
			break;
		}
		tvName.setText(welcome);
		userBean.setUserID(sg.getUserID());
		
		try {
			sg.setNotifications(liftContr.loadNotifications(userBean));
			sg.setCompletedLift(liftContr.checkCompletedLift(userBean));
		} catch (DatabaseException | InvalidInputException e) {
			ExceptionHandler.handle(e);
		}
	}


}
