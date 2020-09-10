package logic.view.booking;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;
import javafx.util.Callback;
import logic.controller.PassengerController;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.ExceptionHandler;
import logic.controller.exception.InvalidInputException;
import logic.controller.exception.InvalidStateException;
import logic.controller.exception.PassengerException;
import logic.model.LiftMatchResult;
import logic.model.LiftSingleton;
import logic.model.Role;
import logic.model.UserSingleton;
import logic.utilities.MyLogger;
import logic.view.MainMenuView;
import logic.view.ViewController;

public class LiftListView extends ViewController implements Initializable {

	
	@FXML
	private Button btConfirm;
	
	@FXML
	private ListView<LiftMatchResult> liftList;

	private LiftSingleton lift = LiftSingleton.getInstance();
	private UserSingleton sg = UserSingleton.getInstance();
	private PassengerController controller = new PassengerController();
	private ViewController viewController = new ViewController();


	@Override
	public void start(Stage stage) {
		viewController.start("fxml/lift_list.fxml", stage);
	}

	@FXML
	public void confirmButtonController() {
		MyLogger.info("lift", lift.getSelectedLift());
		try {
			if (sg.getRole().equals(Role.STUDENT))
				controller.addPassenger(lift.getSelectedLift(), sg.getStudent());
			else
				controller.addPassenger(lift.getSelectedLift(), sg.getStudentCar());
		} catch (InvalidStateException | DatabaseException | PassengerException | InvalidInputException e) {
			ExceptionHandler.handle(e);
		}

		lift.clearState();

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("All right!");
		alert.setHeaderText("Awesome!!");
		alert.setContentText("You have successfully booked your lift!");

		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("../fxml/TorVerCar.css").toExternalForm());
		dialogPane.getStyleClass().add("myDialog");

		alert.showAndWait();

		MainMenuView home = new MainMenuView();
		home.start((Stage) btConfirm.getScene().getWindow());
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		for (LiftMatchResult result : lift.getListLifts()) {
			MyLogger.info("result ID", result.getLift().getLiftID());
			liftList.getItems().add(result);
		}

		liftList.setCellFactory(new Callback<ListView<LiftMatchResult>, ListCell<LiftMatchResult>>() {
			@Override
			public ListCell<LiftMatchResult> call(ListView<LiftMatchResult> param) {
				return new RowLift();
			}
		});

		liftList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		liftList.getSelectionModel().selectedItemProperty().addListener(
				(ObservableValue<? extends LiftMatchResult> ov, LiftMatchResult oldVal, LiftMatchResult newVal) -> {
					LiftMatchResult selectedItem = liftList.getSelectionModel().getSelectedItem();
					int index = liftList.getSelectionModel().getSelectedIndex();

					liftList.getFocusModel().focus(index);
					lift.setSelectedLift(selectedItem.getLift());
				});

	}

}