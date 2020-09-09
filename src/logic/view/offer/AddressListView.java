package logic.view.offer;

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
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;
import logic.controller.LoginController;
import logic.controller.exception.InvalidStateException;
import logic.controller.maps.ViewMapHereApi;
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
import logic.view.ViewController;
import logic.view.booking.BookView;

public class AddressListView extends Application implements Initializable {

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
	private TextField tfStartPoint;
	@FXML
	private TextField tfArrivalPoint;
	@FXML
	private TextField tfStartTime;
	@FXML
	private TextField tfArrivalTime;
	@FXML
	private Button btCheckStart;
	@FXML
	private Button btCheckEnd;
	@FXML
	private Button btConfirm;
	@FXML
	private Button btLifts;
	@FXML
	private ListView<Position> addressList;

	private LiftSingleton lift = LiftSingleton.getInstance();
	private UserSingleton sg = UserSingleton.getInstance();
	private ViewMapHereApi map = ViewMapHereApi.getInstance();
	private ViewController viewController = new ViewController();


	@Override
	public void start(Stage stage) throws Exception {
		viewController.start("fxml/address_list.fxml", stage);
	}

	public static void main(String[] args) {
		launch(args);
	}

	@FXML
	public void homeButtonController() throws Exception {
		lift.clearState();
		MainMenuView home = new MainMenuView();
		home.start((Stage) btHome.getScene().getWindow());
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
	public void liftsButtonController() throws Exception {
		MyLiftView myLift = new MyLiftView();
		myLift.start((Stage) btLifts.getScene().getWindow());
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
		MyLogger.info("Position selected", lift.getStartPoint());
		switch (sg.getStatus()) {
		case OFFER:
			OfferView offer = new OfferView();
			offer.start((Stage) btConfirm.getScene().getWindow());
			break;
		case BOOK:
			BookView book = new BookView();
			book.start((Stage) btConfirm.getScene().getWindow());
			break;
		default:
			throw new InvalidStateException("Invalid status");
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		for (Position pos : lift.getListPos()) {
			addressList.getItems().add(pos);
		}

		addressList.setCellFactory(new Callback<ListView<Position>, ListCell<Position>>() {
			@Override
			public ListCell<Position> call(ListView<Position> param) {
				return new RowAddress();
			}
		});

		addressList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		addressList.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends Position> ov, Position oldVal, Position newVal) -> {
					Position selectedItem = addressList.getSelectionModel().getSelectedItem();
					int index = addressList.getSelectionModel().getSelectedIndex();

					addressList.getFocusModel().focus(index);
					if (lift.getAddress().equals(1)) {
						lift.setStartPoint(selectedItem);
						try {
							lift.setStatus(Status.STOP);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						lift.setEndPoint(selectedItem);
						try {
							lift.setStatus(Status.BOTH);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});

	}
}
