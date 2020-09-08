package logic.view;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import javafx.util.Callback;
import logic.controller.LiftController;
import logic.controller.LoginController;
import logic.model.Lift;
import logic.model.UserSingleton;
import logic.view.booking.BookView;
import logic.view.mylift.RowMyLift;
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
	private ListView<Lift> lvLift;
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
		stage.setResizable(false);

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
				lvLift.getItems().add(result);
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
				lvLift.getItems().add(result);
			}
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (rbOffered.isSelected()) {
			for (Lift result : offered) {
				lvLift.getItems().add(result);
			}
		} else {
			for (Lift result : booked) {
				lvLift.getItems().add(result);
			}
		}
		
		lvLift.setCellFactory(new Callback<ListView<Lift>, ListCell<Lift>>() {
			@Override
			public ListCell<Lift> call(ListView<Lift> param) {
				return new RowMyLift();
			}
		});
	}
}
