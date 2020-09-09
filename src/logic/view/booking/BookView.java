package logic.view.booking;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import logic.bean.LiftBean;
import logic.controller.LiftController;
import logic.controller.maps.AdapterMapsApi;
import logic.controller.maps.MapsApi;
import logic.model.LiftMatchResult;
import logic.model.LiftSingleton;
import logic.model.UserSingleton;
import logic.utilities.MyLogger;
import logic.utilities.Status;
import logic.view.HomeView;
import logic.view.MainMenuView;
import logic.view.MyCarView;
import logic.view.MyLiftView;
import logic.view.ProfileView;
import logic.view.offer.AddressListView;
import logic.view.offer.OfferView;
import logic.controller.LiftMatchListener;
import logic.controller.LoginController;
import logic.controller.exception.ApiNotReachableException;
import logic.controller.exception.ExceptionHandler;
import logic.controller.exception.InvalidInputException;
import logic.controller.exception.NoLiftAvailable;
import logic.controller.exception.PassengerException;

public class BookView extends Application implements Initializable, LiftMatchListener {
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
	private Button btFind;
	@FXML
	private Button btOffer;
	@FXML
	private Button btCheckStart;
	@FXML
	private Button btCheckEnd;
	@FXML
	private Button btLifts;
	@FXML
	private TextField tfStartPoint;
	@FXML
	private TextField tfArrivalPoint;
	@FXML
	private TextField tfStartTime;
	@FXML
	private TextField tfArrivalTime;
	@FXML
	private TextField tfDay;
	@FXML
	private RadioButton rbGoing;
	@FXML
	private RadioButton rbReturn;
	@FXML
	private DatePicker dpDate;

	private LiftSingleton liftSg = LiftSingleton.getInstance();
	private UserSingleton userSg = UserSingleton.getInstance();
	private MapsApi mapsApi = AdapterMapsApi.getInstance();
	private LiftController liftController = new LiftController();
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyy-MM-ddHH:mm");

	private boolean firstLog = true;
	private static final String CB_GOING = "cbGoing";
	private static final String CB_RETURN = "cbArrive";

	@Override
	public void start(Stage stage) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/Book.fxml"));

		Parent root;
		try {
			root = loader.load();

			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setResizable(false);

			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	@FXML
	public void homeButtonController() {
		liftSg.clearState();
		MainMenuView home = new MainMenuView();
		try {
			home.start((Stage) btHome.getScene().getWindow());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void liftsButtonController()  {
		liftSg.clearState();
		MyLiftView myLift = new MyLiftView();
		myLift.start((Stage) btLifts.getScene().getWindow());
	}

	@FXML
	public void bookButtonController() {
		liftSg.clearState();
		BookView book = new BookView();
		book.start((Stage) btBook.getScene().getWindow());
	}

	@FXML
	public void myCarButtonController() {
		liftSg.clearState();
		MyCarView car = new MyCarView();
		car.start((Stage) btMyCar.getScene().getWindow());

	}

	@FXML
	public void startButtonController()  {
		liftSg.setAddress(1);
		try {
			liftSg.setListPos(mapsApi.addrToPos(tfStartPoint.getText()));
		} catch (ApiNotReachableException | InvalidInputException e) {
			ExceptionHandler.handle(e);
		}

		AddressListView list = new AddressListView();
		list.start((Stage) btCheckStart.getScene().getWindow());
	}

	@FXML
	public void destButtonController()  {
		liftSg.setAddress(2);
		try {
			liftSg.setListPos(mapsApi.addrToPos(tfArrivalPoint.getText()));
		} catch (ApiNotReachableException | InvalidInputException e) {
			ExceptionHandler.handle(e);
		}
		AddressListView list = new AddressListView();
		list.start((Stage) btCheckEnd.getScene().getWindow());
	}

	@FXML
	public void profileButtonController()  {
		liftSg.clearState();
		ProfileView profile = new ProfileView();
		profile.start((Stage) btProfile.getScene().getWindow());
	}

	@FXML
	public void logoutButtonController()  {
		try {
			LoginController.logout();
		} catch (Exception e) {
			e.printStackTrace();
		}
		HomeView home = new HomeView();
		home.start((Stage) btLogout.getScene().getWindow());
	}

	@FXML
	public void offerButtonController()  {
		liftSg.clearState();
		OfferView offer = new OfferView();
		offer.start((Stage) btOffer.getScene().getWindow());
	}

	@FXML
	public void findButtonController() {
		String time;
		LiftBean lift = new LiftBean();

		lift.setStartPos(liftSg.getStartPoint());
		lift.setStopPos(liftSg.getEndPoint());
		lift.setDriver(userSg.getStudentCar());
		lift.setStartPos(liftSg.getStartPoint());
		lift.setStopPos(liftSg.getEndPoint());

		try {
			if (rbGoing.isSelected()) {
				liftSg.setPurpose(CB_GOING);
				time = dpDate.getValue().toString() + tfStartTime.getText();
				lift.setStartDateTime(LocalDateTime.parse(time, FORMATTER));
				liftController.matchLiftStoppingBefore(lift, 0, this);

			} else if (rbReturn.isSelected()) {
				liftSg.setPurpose(CB_RETURN);
				time = dpDate.getValue().toString() + tfArrivalTime.getText();
				lift.setStartDateTime(LocalDateTime.parse(time, FORMATTER));
				liftController.matchLiftStartingAfter(lift, 0, this);

			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning");
				alert.setHeaderText("Oops!");
				alert.setContentText("You must choose a lift option");
				alert.showAndWait();
			}
		} catch (NoLiftAvailable e) {
			ExceptionHandler.handle(e);
		}
	}

	@FXML
	public void rbGoingButtonController() {
		if (rbGoing.isSelected()) {
			rbGoing.setDisable(true);
			rbReturn.setSelected(false);
			rbReturn.setDisable(false);
			tfStartTime.setDisable(false);
			tfArrivalTime.setDisable(true);
			liftSg.setPurpose(CB_GOING);
		}
	}

	@FXML
	public void rbReturnButtonController() {
		if (rbReturn.isSelected()) {
			rbReturn.setDisable(true);
			rbGoing.setSelected(false);
			rbGoing.setDisable(false);
			tfArrivalTime.setDisable(false);
			tfStartTime.setDisable(true);
			liftSg.setPurpose("cbReturn");
		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (liftSg.getPurpose().equals(null)) {
			rbGoing.setSelected(false);
			rbReturn.setSelected(false);
		}

		if (liftSg.getPurpose().equals(CB_GOING)) {
			rbGoing.setDisable(true);
			rbReturn.setSelected(false);
			rbReturn.setDisable(false);
			tfStartTime.setDisable(false);
			tfArrivalTime.setDisable(true);
		}
		if (liftSg.getPurpose().equals(CB_RETURN)) {
			rbReturn.setDisable(true);
			rbGoing.setSelected(false);
			rbGoing.setDisable(false);
			tfArrivalTime.setDisable(false);
			tfStartTime.setDisable(true);
		}

		try {
			userSg.setStatus(Status.BOOK);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		if (!liftSg.getStatus().equals(Status.START)) {
			tfStartPoint.setText(liftSg.getStartPoint().getAddress());
			tfStartPoint.setDisable(true);
		}

		if (!(liftSg.getStatus().equals(Status.STOP) || liftSg.getStatus().equals(Status.START))) {
			tfArrivalPoint.setText(liftSg.getEndPoint().getAddress());
			tfArrivalPoint.setDisable(true);
		}

	}

	@Override
	public void onThreadEnd(List<LiftMatchResult> results) {
		MyLogger.info("results", results);
		if (!results.isEmpty()) {
			for (LiftMatchResult result : results) {
				MyLogger.info("thread result", result.getLift().getLiftID());
				liftSg.addLiftMatchResult(result);
			}
			LiftListView list = new LiftListView();
			try {
				list.start((Stage) btFind.getScene().getWindow());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			MyLogger.info("No lifts found");
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Oh no!");
			alert.setHeaderText(":(");
			alert.setContentText("No lift found!");

			DialogPane dialogPane = alert.getDialogPane();

			dialogPane.getStylesheets().add(getClass().getResource("../fxml/TorVerCar.css").toExternalForm());
			dialogPane.getStyleClass().add("myDialog");

			alert.showAndWait();

		}

	}

	@Override
	public void onThreadRunning() {
		if (firstLog) {
			firstLog = !firstLog;
			MyLogger.info("Thread started running.");
		}
	}

}
