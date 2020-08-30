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
import logic.controller.LoginController;
import logic.controller.maps.ViewMapHereApi;
import logic.model.LiftSingleton;
import logic.model.Position;
import logic.model.UserSingleton;
import logic.utilities.MyLogger;
import logic.utilities.Status;
import logic.view.HomeView;
import logic.view.MainMenuView;
import logic.view.MyCarView;
import logic.view.ProfileView;
import logic.view.booking.BookView;

import javafx.scene.Node;

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
	private ListView<RowAddress> addressList;

	LiftSingleton lift = LiftSingleton.getInstance();
	UserSingleton sg = UserSingleton.getInstance();
	ViewMapHereApi map = ViewMapHereApi.getInstance();

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/address_list.fxml"));
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
		lift.clearState();
		OfferView offer = new OfferView();
		offer.start((Stage) btOffer.getScene().getWindow());
	}

	@FXML
	public void confirmButtonController() throws Exception {
		MyLogger.info("Position selected", lift.getStartPoint());
		switch (sg.getStatus()) {
		default:
			throw new Exception("Invalid status");
		case OFFER:
			OfferView offer = new OfferView();
			offer.start((Stage) btConfirm.getScene().getWindow());
			break;
		case BOOK:
			BookView book = new BookView();
			book.start((Stage) btConfirm.getScene().getWindow());
			break;
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		for (Position pos : lift.getListPos()) {
			addressList.getItems().add(new RowAddress(pos.getAddress(), map.viewFromPos(pos), pos));
		}

		addressList.setCellFactory(lv -> new ListCell<RowAddress>() {
			private Node graphic;
			private RowAddressController controller;
			{
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/list_cell.fxml"));
					graphic = loader.load();
					controller = loader.getController();
				} catch (IOException exc) {
					throw new RuntimeException(exc);
				}
			}

			@Override
			protected void updateItem(RowAddress row, boolean empty) {
				super.updateItem(row, empty);
				if (empty) {
					setGraphic(null);
				} else {
					controller.setAddress(row.getAddress());
					controller.setMap(row.getURL());

					setGraphic(graphic);
				}

			}

		});

		addressList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		addressList.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends RowAddress> ov, RowAddress old_val, RowAddress new_val) -> {
					RowAddress selectedItem = addressList.getSelectionModel().getSelectedItem();
					int index = addressList.getSelectionModel().getSelectedIndex();

					addressList.getFocusModel().focus(index);
					if (lift.getAddress().equals(1)) {
						lift.setStartPoint(selectedItem.getPosition());
						try {
							lift.setStatus(Status.STOP);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						lift.setEndPoint(selectedItem.getPosition());
						try {
							lift.setStatus(Status.BOTH);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});

	}
}
