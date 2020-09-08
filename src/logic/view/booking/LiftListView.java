package logic.view.booking;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;
import javafx.util.Callback;
import logic.controller.LoginController;
import logic.controller.PassengerController;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.ExceptionHandler;
import logic.controller.exception.InvalidStateException;
import logic.model.LiftMatchResult;
import logic.model.LiftSingleton;
import logic.model.Role;
import logic.model.UserSingleton;
import logic.utilities.MyLogger;
import logic.view.HomeView;
import logic.view.MainMenuView;
import logic.view.MyCarView;
import logic.view.MyLiftView;
import logic.view.ProfileView;
import logic.view.offer.OfferView;

public class LiftListView extends Application implements Initializable {

	@FXML
	private Button btHome;
	@FXML
	private Button btBook;
	@FXML
	private Button btMyCar;
	@FXML
	private Button btProfile;
	@FXML
	private Button btLogout;
	@FXML
	private Button btOffer;
	@FXML
	private Button btConfirm;
	@FXML
	private Button btLifts;
	@FXML
	private ListView<LiftMatchResult> liftList;

	private LiftSingleton lift = LiftSingleton.getInstance();
	private UserSingleton sg = UserSingleton.getInstance();
	private PassengerController controller = new PassengerController();

	@Override
	public void start(Stage stage) throws Exception {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/lift_list.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);

		stage.show();
	}

	@FXML
	public void homeButtonController() throws Exception {
		lift.clearState();
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
		lift.clearState();
		BookView book = new BookView();
		book.start((Stage) btBook.getScene().getWindow());
	}

	@FXML
	public void myCarButtonController() throws Exception {
		lift.clearState();
		MyCarView car = new MyCarView();
		car.start((Stage) btMyCar.getScene().getWindow());

	}

	@FXML
	public void profileButtonController() throws Exception {
		lift.clearState();
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
		lift.clearState();
		OfferView offer = new OfferView();
		offer.start((Stage) btOffer.getScene().getWindow());
	}

	@FXML
	public void confirmButtonController() throws Exception {
		MyLogger.info("lift", lift.getSelectedLift());
		try {
			if (sg.getRole().equals(Role.STUDENT))
				controller.addPassenger(lift.getSelectedLift(), sg.getStudent());
			else
				controller.addPassenger(lift.getSelectedLift(), sg.getStudentCar());

		} catch (InvalidStateException | DatabaseException e) {
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