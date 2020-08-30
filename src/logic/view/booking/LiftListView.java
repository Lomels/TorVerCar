package logic.view.booking;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import logic.controller.LoginController;
import logic.controller.PassengerController;
import logic.controller.maps.ViewMapHereApi;
import logic.model.Lift;
import logic.model.LiftMatchResult;
import logic.model.LiftSingleton;
import logic.model.Role;
import logic.model.UserSingleton;
import logic.utilities.MyLogger;
import logic.view.HomeView;
import logic.view.MainMenuView;
import logic.view.MyCarView;
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
	private ListView<RowLift> liftList;

	private LiftSingleton lift = LiftSingleton.getInstance();
	private UserSingleton sg = UserSingleton.getInstance();
	private ViewMapHereApi map = ViewMapHereApi.getInstance();
	private PassengerController controller = new PassengerController();
	private Integer index;
	
	@Override
	public void start(Stage stage) throws Exception {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/lift_list.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
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
		MyLogger.info("lift", lift.getSelectedLift());
		if(sg.getRole().equals(Role.STUDENT))
			controller.addPassenger(lift.getSelectedLift(), sg.getStudent());
		else
			controller.addPassenger(lift.getSelectedLift(), sg.getStudentCar());
		
		lift.clearState();
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("All right!");
		alert.setHeaderText("");
		alert.setContentText("You have successfully booked your lift!");
		
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.getStylesheets().add(getClass().getResource("../fxml/TorVerCar.css").toExternalForm());
		dialogPane.getStyleClass().add("myDialog");
		
		Optional<ButtonType> result = alert.showAndWait(); 
		
		MainMenuView home = new MainMenuView();
		home.start((Stage) btConfirm.getScene().getWindow());
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		for (LiftMatchResult result : lift.getListLifts()) {
			MyLogger.info("result ID", result.getLift().getLiftID());
			liftList.getItems()
					.add(new RowLift(result));
		}
		liftList.setCellFactory(lv -> new ListCell<RowLift>() {
			private Node graphic;
			private RowLiftController controller;
			{
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/lift_row.fxml"));
					graphic = loader.load();
					controller = loader.getController();
				} catch (IOException exc) {
					throw new RuntimeException(exc);
				}
			}

			@Override
			protected void updateItem(RowLift row, boolean empty) {
				super.updateItem(row, empty);
				if (empty) {
					setGraphic(null);
				} else {
					controller.setFrom(row.getResult().getLift().getRoute().getPickupPosition().getAddress());
					controller.setTo(row.getResult().getLift().getRoute().getDropoffPosition().getAddress());
					controller.setTimeFrom(row.getResult().getRelativeStartDateTime().toString());
					controller.setTimeTo(row.getResult().getLift().getStopDateTime().toString());

					setGraphic(graphic);
				}

			}

		});

		liftList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		liftList.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends RowLift> ov, RowLift old_val, RowLift new_val) -> {
					RowLift selectedItem = liftList.getSelectionModel().getSelectedItem();
					int index = liftList.getSelectionModel().getSelectedIndex();

					liftList.getFocusModel().focus(index);
					lift.setSelectedLift(selectedItem.getResult().getLift());
				});

	}

}