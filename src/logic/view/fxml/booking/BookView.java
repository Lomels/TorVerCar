package logic.view.fxml.booking;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
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
import logic.view.fxml.HomeView;
import logic.view.fxml.MainMenuView;
import logic.view.fxml.MyCarView;
import logic.view.fxml.ProfileView;
import logic.view.fxml.offer.AddressListView;
import logic.view.fxml.offer.OfferView;
import javafx.event.EventHandler;
import logic.controller.LiftMatchListener;

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
	private CheckBox cbGoing;
	@FXML
	private CheckBox cbReturn;

	private LiftSingleton liftSg = LiftSingleton.getInstance();
	private UserSingleton userSg = UserSingleton.getInstance();
	private MapsApi mapsApi = AdapterMapsApi.getInstance();
	private LiftController liftController = new LiftController();
	private String time;
	private LiftMatchListener listener;
	

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

	private boolean firstLog = true;
	
	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Book.fxml"));
		
		
		
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
	public void myCarButtonController() throws Exception {
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
		ProfileView profile = new ProfileView();
		profile.start((Stage) btProfile.getScene().getWindow());
	}

	@FXML
	public void logoutButtonController() throws IOException {
		HomeView home = new HomeView();
		home.start((Stage) btLogout.getScene().getWindow());
	}

	@FXML
	public void offerButtonController() throws Exception {
		OfferView offer = new OfferView();
		offer.start((Stage) btOffer.getScene().getWindow());
	}
	
		
		

	@FXML
	public void findButtonController() throws Exception {
		List<Position> stops = new ArrayList<>();
		stops.add(liftSg.getStartPoint());
		stops.add(liftSg.getEndPoint());

		if (cbGoing.isSelected()) {
			liftSg.setPurpose("cbGoing");
			time = tfDay.getText() + tfArrivalTime.getText().toString();
			liftSg.setArrivalTime(time);
			MyLogger.info("LocalDateTime", LocalDateTime.parse(liftSg.getArrivalTime(), FORMATTER));
			MyLogger.info("Stops", stops);
			// TODO spostare in un controller

			liftController.matchLiftStoppingBefore(LocalDateTime.parse(liftSg.getArrivalTime(), FORMATTER), stops, 0,
					this);
		} else if (cbReturn.isSelected()){
			liftSg.setPurpose("cbReturn");
			time = tfDay.getText().toString() + tfStartTime.getText().toString();
			liftSg.setDepartureTime(time);
			MyLogger.info("LocalDateTime", LocalDateTime.parse(liftSg.getDepartureTime(), FORMATTER));
			MyLogger.info("Stops", stops);

			liftController.matchLiftStartingAfter(LocalDateTime.parse(liftSg.getDepartureTime(), FORMATTER), stops, 0,
					this);
		} else {
			MyLogger.info("You must choose a lift option");
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//TODO sistemare spunta
		if(liftSg.getPurpose().equals("cbGoing")) {
			cbGoing.setSelected(true);
		}
		if(liftSg.getPurpose().equals("cbReturn")) {
			cbReturn.setSelected(false);
		}
		
		try {
			userSg.setStatus(Status.BOOK);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		EventHandler<ActionEvent> eventGoing = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				if (cbGoing.isPressed()) {
					cbGoing.setSelected(false);
					tfStartTime.setDisable(false);
					tfArrivalTime.setDisable(true);
					cbReturn.setSelected(true);

				} else {
					cbReturn.setSelected(false);
					tfArrivalTime.setDisable(false);
					tfStartTime.setDisable(true);
					cbGoing.setSelected(true);
				}
			}
		};

		EventHandler<ActionEvent> eventReturn = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				if (cbReturn.isPressed()) {
					cbReturn.setSelected(false);
					tfStartTime.setDisable(true);
					tfArrivalTime.setDisable(false);
					cbGoing.setSelected(true);

				} else {
					cbGoing.setSelected(false);
					tfArrivalTime.setDisable(true);
					tfStartTime.setDisable(false);
					cbReturn.setSelected(true);
				}
			}
		};

		cbGoing.setOnAction(eventGoing);
		cbReturn.setOnAction(eventReturn);

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
		// TODO Auto-generated method stub
		if(!results.isEmpty()) {
			for (LiftMatchResult result : results) {
				MyLogger.info("Result", result);
				liftSg.addLiftMatchResult(result);

				LiftListView list = new LiftListView();
				try {
					list.start((Stage) btFind.getScene().getWindow());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else {
			MyLogger.info("No lifts found");
			// TODO: toast di avviso
		}

		
		// TODO creare lista di passaggi matched e far partire nuova lista di roba
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
