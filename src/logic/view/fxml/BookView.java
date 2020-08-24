package logic.view.fxml;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.controller.LiftController;
import logic.controller.maps.AdapterMapsApi;
import logic.controller.maps.MapsApi;
import logic.model.LiftSingleton;
import javafx.event.EventHandler;
import logic.controller.LiftMatchListener;

public class BookView extends Application implements Initializable{
	@FXML private Button btHome;
	@FXML private Button btBook;
	@FXML private Button btMyCar;
	@FXML private Button btProfile;
	@FXML private Button btLogout;
	@FXML private Button btFind;
	@FXML private Button btOffer;
	@FXML private Button btCheckStart;
	@FXML private Button btCheckEnd;
	@FXML private TextField tfStartPoint;
	@FXML private TextField tfArrivalPoint;
	@FXML private TextField tfStartTime;
	@FXML private TextField tfArrivalTime;
	@FXML private TextField tfDay;
	@FXML private CheckBox cbGoing;
	@FXML private CheckBox cbReturn;

	private LiftSingleton liftSg = LiftSingleton.getInstance();
	private MapsApi mapsApi = AdapterMapsApi.getInstance();
	private LiftController liftController = new LiftController();
	private String time;
	private LiftMatchListener listener;
	
	
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
		if(cbGoing.isPressed()) {
			time = tfDay.getText() + "T" + tfArrivalTime.getText();
			liftSg.setArrivalTime(time);
			liftController.matchLiftStoppingBefore(liftSg.getArrivalTime(), null, 0, listener);
		}else {
			time = tfDay.getText() + "T" + tfStartTime.getText();
			liftSg.setDepartureTime(time);
			liftController.matchLiftStartingAfter(liftSg.getDepartureTime(), null, 0, listener);
		}
		
		
		AddressListView list = new AddressListView();
		list.start((Stage) btFind.getScene().getWindow());
		
		//TO DO create new controller list
	
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		EventHandler<ActionEvent> eventGoing = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				if(cbGoing.isPressed()) {
					cbGoing.setSelected(false);
					tfStartTime.setDisable(false);
					tfArrivalTime.setDisable(true);
					cbReturn.setSelected(true);
					
				}else {
					cbReturn.setSelected(false);
					tfArrivalTime.setDisable(false);
					tfStartTime.setDisable(true);
					cbGoing.setSelected(true);
				}
			}
		};
		
		EventHandler<ActionEvent> eventReturn = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				if(cbReturn.isPressed()) {
					cbReturn.setSelected(false);
					tfStartTime.setDisable(true);
					tfArrivalTime.setDisable(false);
					cbGoing.setSelected(true);
					
				}else {
					cbGoing.setSelected(false);
					tfArrivalTime.setDisable(true);
					tfStartTime.setDisable(false);
					cbReturn.setSelected(true);
				}
			}
		};
		
		cbGoing.setOnAction(eventGoing);
		cbReturn.setOnAction(eventReturn);
		
			
		
		
		
	}



}
