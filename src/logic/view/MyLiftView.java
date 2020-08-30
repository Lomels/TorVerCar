package logic.view;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;
import logic.controller.LiftController;
import logic.controller.LoginController;
import logic.model.Lift;
import logic.model.LiftMatchResult;
import logic.model.UserSingleton;
import logic.utilities.MyLogger;
import logic.view.booking.BookView;
import logic.view.booking.RowLift;
import logic.view.booking.RowLiftController;
import logic.view.mylift.RowMyLift;
import logic.view.mylift.RowMyLiftController;
import logic.view.offer.OfferView;

public class MyLiftView extends Application implements Initializable {
	@FXML
	private Button btHome;
	@FXML
	private Button btMyCar;
	@FXML
	private Button btProfile;
	@FXML
	private Button btLogout;
	@FXML
	private Button btOffer;
	@FXML
	private Button btBook;
	@FXML
	private ListView<RowMyLift> lvLift;
	@FXML
	private RadioButton rbOffered;
	@FXML
	private RadioButton rbBooked;

	private LiftController liftController = new LiftController();
	private UserSingleton sg = UserSingleton.getInstance();

	List<Lift> offered = liftController.loadOfferedLift(sg.getUserID());
	List<Lift> booked = liftController.loadBookedLift(sg.getUserID());

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/mylift_list.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void homeButtonController() throws Exception {
		MainMenuView home = new MainMenuView();
		home.start((Stage) btHome.getScene().getWindow());
	}

	public void myCarButtonController() throws Exception {
		MyCarView car = new MyCarView();
		car.start((Stage) btMyCar.getScene().getWindow());
	}

	public void profileButtonController() throws Exception {
		ProfileView profile = new ProfileView();
		profile.start((Stage) btProfile.getScene().getWindow());
	}

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

	public void offerButtonController() throws Exception {
		OfferView offer = new OfferView();
		offer.start((Stage) btOffer.getScene().getWindow());
	}

	public void bookButtonController() throws Exception {
		BookView book = new BookView();
		book.start((Stage) btBook.getScene().getWindow());
	}

	public void rbBookedController() {
		if (rbBooked.isSelected()) {
			rbBooked.setDisable(true);
			rbOffered.setSelected(false);
			rbOffered.setDisable(false);
			lvLift.getItems().clear();

			for (Lift result : booked) {
				lvLift.getItems()
						.add(new RowMyLift(result.getRoute().getPickupPosition().getAddress(),
								result.getRoute().getDropoffPosition().getAddress(),
								result.getStartDateTime().format(DateTimeFormatter.ISO_DATE),
								result.getStartDateTime().format(DateTimeFormatter.ISO_LOCAL_TIME),
								result.getStopDateTime().format(DateTimeFormatter.ISO_LOCAL_TIME)));
			}
		}
	}

	public void rbOfferedController() {
		if (rbOffered.isSelected()) {
			rbOffered.setDisable(true);
			rbBooked.setSelected(false);
			rbBooked.setDisable(false);
			lvLift.getItems().clear();
			for (Lift result : offered) {
				lvLift.getItems()
						.add(new RowMyLift(result.getRoute().getPickupPosition().getAddress(),
								result.getRoute().getDropoffPosition().getAddress(),
								result.getStartDateTime().format(DateTimeFormatter.ISO_DATE),
								result.getStartDateTime().format(DateTimeFormatter.ISO_LOCAL_TIME),
								result.getStopDateTime().format(DateTimeFormatter.ISO_LOCAL_TIME)));
			}
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		if (rbOffered.isSelected()) {
			for (Lift result : offered) {
				lvLift.getItems()
						.add(new RowMyLift(result.getRoute().getPickupPosition().getAddress(),
								result.getRoute().getDropoffPosition().getAddress(),
								result.getStartDateTime().format(DateTimeFormatter.ISO_DATE),
								result.getStartDateTime().format(DateTimeFormatter.ISO_LOCAL_TIME),
								result.getStopDateTime().format(DateTimeFormatter.ISO_LOCAL_TIME)));
			}
		} else {
			for (Lift result : booked) {
				lvLift.getItems()
						.add(new RowMyLift(result.getRoute().getPickupPosition().getAddress(),
								result.getRoute().getDropoffPosition().getAddress(),
								result.getStartDateTime().format(DateTimeFormatter.ISO_DATE),
								result.getStartDateTime().format(DateTimeFormatter.ISO_LOCAL_TIME),
								result.getStopDateTime().format(DateTimeFormatter.ISO_LOCAL_TIME)));
			}
		}
		lvLift.setCellFactory(lv -> new ListCell<RowMyLift>() {
			private Node graphic;
			private RowMyLiftController controller;
			{
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/mylift_row.fxml"));
					graphic = loader.load();
					controller = loader.getController();
				} catch (IOException exc) {
					throw new RuntimeException(exc);
				}
			}

			@Override
			protected void updateItem(RowMyLift row, boolean empty) {
				super.updateItem(row, empty);
				if (empty) {
					setGraphic(null);
				} else {
					controller.setFrom(row.getFrom());
					controller.setTo(row.getTo());
					controller.setStart(row.getStart());
					controller.setStop(row.getStop());
					controller.setWhen(row.getWhen());
					setGraphic(graphic);
				}

			}

		});
//
//		lvLift.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
//
//		lvLift.getSelectionModel().selectedItemProperty()
//				.addListener((ObservableValue<? extends RowMyLift> ov, RowMyLift old_val, RowMyLift new_val) -> {
//					RowMyLift selectedItem = lvLift.getSelectionModel().getSelectedItem();
//					int index = lvLift.getSelectionModel().getSelectedIndex();
//
//					lvLift.getFocusModel().focus(index);
//				});
//
//	
	}
}
