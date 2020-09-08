package logic.view.booking;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import logic.controller.LiftController;
import logic.controller.maps.AdapterMapsApi;
import logic.controller.maps.MapsApi;
import logic.model.LiftMatchResult;
import logic.model.LiftSingleton;
import logic.model.Position;
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
import logic.controller.exception.ExceptionHandler;
import logic.controller.exception.NoLiftAvailable;

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
	private String time;
	private LiftMatchListener listener;

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyy-MM-ddHH:mm");

	private boolean firstLog = true;

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/Book.fxml"));

		Parent root = loader.load();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);

		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	@FXML
	public void homeButtonController() throws Exception {
		liftSg.clearState();
		MainMenuView home = new MainMenuView();
		home.start((Stage) btHome.getScene().getWindow());
	}

	@FXML
	public void liftsButtonController() throws Exception {
		liftSg.clearState();
		MyLiftView myLift = new MyLiftView();
		myLift.start((Stage) btLifts.getScene().getWindow());
	}

	@FXML
	public void bookButtonController() throws Exception {
		liftSg.clearState();
		BookView book = new BookView();
		book.start((Stage) btBook.getScene().getWindow());
	}

	@FXML
	public void myCarButtonController() throws Exception {
		liftSg.clearState();
		MyCarView car = new MyCarView();
		car.start((Stage) btMyCar.getScene().getWindow());

	}

	@FXML
	public void startButtonController() throws Exception {
		liftSg.setAddress(1);
		liftSg.setListPos(mapsApi.addrToPos(tfStartPoint.getText()));

		AddressListView list = new AddressListView();
		list.start((Stage) btCheckStart.getScene().getWindow());
	}

	@FXML
	public void destButtonController() throws Exception {
		liftSg.setAddress(2);
		liftSg.setListPos(mapsApi.addrToPos(tfArrivalPoint.getText()));
		AddressListView list = new AddressListView();
		list.start((Stage) btCheckEnd.getScene().getWindow());
	}

	@FXML
	public void profileButtonController() throws Exception {
		liftSg.clearState();
		ProfileView profile = new ProfileView();
		profile.start((Stage) btProfile.getScene().getWindow());
	}

	@FXML
	public void logoutButtonController() throws IOException {
		try {
			LoginController.logout();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HomeView home = new HomeView();
		home.start((Stage) btLogout.getScene().getWindow());
	}

	@FXML
	public void offerButtonController() throws Exception {
		liftSg.clearState();
		OfferView offer = new OfferView();
		offer.start((Stage) btOffer.getScene().getWindow());
	}

	@FXML
	public void findButtonController() {
		List<Position> stops = new ArrayList<>();
		stops.add(liftSg.getStartPoint());
		stops.add(liftSg.getEndPoint());
		try {
			if (rbGoing.isSelected()) {
				liftSg.setPurpose("cbGoing");
				time = dpDate.getValue().toString() + tfArrivalTime.getText();
				liftSg.setArrivalTime(time);

				liftController.matchLiftStoppingBefore(LocalDateTime.parse(liftSg.getArrivalTime(), FORMATTER), stops,
						0, this);

			} else if (rbReturn.isSelected()) {
				liftSg.setPurpose("cbReturn");
				time = dpDate.getValue().toString() + tfStartTime.getText();
				liftSg.setDepartureTime(time);

				liftController.matchLiftStartingAfter(LocalDateTime.parse(liftSg.getDepartureTime(), FORMATTER), stops,
						0, this);
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
			liftSg.setPurpose("cbGoing");
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

		if (liftSg.getPurpose().equals("cbGoing")) {
			rbGoing.setDisable(true);
			rbReturn.setSelected(false);
			rbReturn.setDisable(false);
			tfStartTime.setDisable(false);
			tfArrivalTime.setDisable(true);
		}
		if (liftSg.getPurpose().equals("cbReturn")) {
			rbReturn.setDisable(true);
			rbGoing.setSelected(false);
			rbGoing.setDisable(false);
			tfArrivalTime.setDisable(false);
			tfStartTime.setDisable(true);
		}

		try {
			userSg.setStatus(Status.BOOK);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (!liftSg.getStatus().equals(Status.START)) {
			tfStartPoint.setText(liftSg.getStartPoint().getAddress());
			tfStartPoint.setDisable(true);
		}

		if (!(liftSg.getStatus().equals(Status.STOP) | liftSg.getStatus().equals(Status.START))) {
			tfArrivalPoint.setText(liftSg.getEndPoint().getAddress());
			tfArrivalPoint.setDisable(true);
		}

	}

	@Override
	public void onThreadEnd(List<LiftMatchResult> results) {
		if (!results.isEmpty()) {
			for (LiftMatchResult result : results) {
				MyLogger.info("thread result", result.getLift().getLiftID());
				liftSg.addLiftMatchResult(result);
			}
			LiftListView list = new LiftListView();
			try {
				list.start((Stage) btFind.getScene().getWindow());
			} catch (Exception e) {
				// TODO Auto-generated catch block
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

			Optional<ButtonType> result = alert.showAndWait();

		}

	}

	@Override
	public void onThreadRunning() {
		if (firstLog) {
			firstLog = !firstLog;
			MyLogger.info("Thread started running.");
		}
		// TODO implementare schermata di wait
	}

}
