package logic.view.booking;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
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
	public void profileButtonController() throws Exception {
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
		MainMenuView home = new MainMenuView();
		home.start((Stage) btConfirm.getScene().getWindow());
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		for (LiftMatchResult result : lift.getListLifts()) {
			MyLogger.info("result ID", result.getLift().getLiftID());
			liftList.getItems()
					.add(new RowLift(result.getLift()));
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
					controller.setFrom(row.getLift().getRoute().getPickupPosition().getAddress());
					controller.setTo(row.getLift().getRoute().getDropoffPosition().getAddress());
					controller.setTimeFrom(row.getLift().getStartDateTime().toString());
					controller.setTimeTo(row.getLift().getStopDateTime().toString());

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
					lift.setSelectedLift(selectedItem.getLift());
				});

	}

}