package logic.view.fxml;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.controller.LiftController;
import logic.controller.maps.AdapterMapsApi;
import logic.controller.maps.MapsApi;
import logic.model.LiftSingleton;

public class BookView extends Application {
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
	private Button btStart;
	@FXML
	private Button btDestination;
	@FXML
	private TextField tfStartPoint;
	@FXML
	private TextField tfArrivalPoint;
	@FXML
	private TextField tfStartTime;
	@FXML
	private TextField tfArrivalTime;

	private LiftSingleton liftSg = LiftSingleton.getInstance();
	private MapsApi mapsApi = AdapterMapsApi.getInstance();
	private LiftController liftController = new LiftController();
	
	
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
		list.start((Stage) btStart.getScene().getWindow());
	}

	@FXML
	public void destButtonController() throws Exception {
		liftSg.setAddress(2);
		liftSg.setListPos(mapsApi.addrToPos(tfArrivalPoint.getText()));
		AddressListView list = new AddressListView();
		list.start((Stage) btDestination.getScene().getWindow());
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
	public void findButtonController() {
		
	}

	// TODO: implementare Find

}
