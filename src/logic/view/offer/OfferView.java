package logic.view.offer;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.controller.LiftController;
import logic.controller.LoginController;
import logic.controller.exception.ApiNotReachableException;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.ExceptionHandler;
import logic.controller.exception.InvalidInputException;
import logic.controller.exception.InvalidStateException;
import logic.controller.maps.AdapterMapsApi;
import logic.controller.maps.MapsApi;
import logic.model.LiftSingleton;
import logic.model.UserSingleton;
import logic.utilities.Status;
import logic.view.HomeView;
import logic.view.MainMenuView;
import logic.view.MyCarView;
import logic.view.MyLiftView;
import logic.view.ProfileView;
import logic.view.booking.BookView;
import logic.model.Role;

public class OfferView extends Application implements Initializable {
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
	private TextField tfMaxDuration;
	@FXML
	private TextField tfNotes;
	@FXML
	private DatePicker dpDate;
	@FXML
	private Button btCheckStart;
	@FXML
	private Button btCheckEnd;
	@FXML
	private Button btConfirm;
	@FXML
	private Button btAddCar;
	@FXML
	private Button btLifts;

	private MapsApi mapsApi = AdapterMapsApi.getInstance();
	private LiftSingleton lp = LiftSingleton.getInstance();
	private UserSingleton userSg = UserSingleton.getInstance();
	private LiftController controller = new LiftController();

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/Offer.fxml"));
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
		lp.clearState();
		MainMenuView home = new MainMenuView();
		home.start((Stage) btHome.getScene().getWindow());
	}

	@FXML
	public void bookButtonController() throws Exception {
		lp.clearState();
		BookView book = new BookView();
		book.start((Stage) btBook.getScene().getWindow());
	}

	@FXML
	public void myCarButtonController() throws Exception {
		lp.clearState();
		MyCarView car = new MyCarView();
		car.start((Stage) btMyCar.getScene().getWindow());

	}

	@FXML
	public void profileButtonController() throws Exception {
		lp.clearState();
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
		OfferView offer = new OfferView();
		offer.start((Stage) btOffer.getScene().getWindow());
	}

	@FXML
	public void checkStartAddressController() throws Exception {
		lp.setAddress(1);
		lp.setListPos(mapsApi.addrToPos(tfStartPoint.getText()));
		AddressListView list = new AddressListView();
		list.start((Stage) btCheckStart.getScene().getWindow());
	}

	@FXML
	public void checkEndAddressController() throws Exception{
		lp.setAddress(2);
		try {
			lp.setListPos(mapsApi.addrToPos(tfArrivalPoint.getText()));
		}catch(ApiNotReachableException e) {
			ExceptionHandler.handle(e);
		}catch (InvalidInputException e) {
			e.printStackTrace();
		}
		AddressListView list = new AddressListView();
		list.start((Stage) btCheckEnd.getScene().getWindow());
	}

	@FXML
	public void addCarButtonController() throws Exception {
		lp.clearState();
		MyCarView car = new MyCarView();
		car.start((Stage) btAddCar.getScene().getWindow());

	}

	@FXML
	public void confirmButtonController() throws Exception{
		String time = dpDate.getValue().toString() + "T" + tfStartTime.getText();
		lp.setDepartureTime(time);
		lp.setMaxDuration(tfMaxDuration.getText());
		lp.setNotes(tfNotes.getText());
		try {
			controller.createLift(lp.getDepartureTime(), Integer.parseInt(lp.getMaxDuration()), lp.getNotes(),
					userSg.getStudentCar(), lp.getStartPoint(), lp.getEndPoint());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (InvalidInputException | DatabaseException | InvalidStateException e) {
			ExceptionHandler.handle(e);
		}

		lp.clearState();
		MainMenuView home = new MainMenuView();
		home.start((Stage) btConfirm.getScene().getWindow());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		lp.setPurpose("offer");
		try {
			userSg.setStatus(Status.OFFER);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (userSg.getRole().equals(Role.STUDENT)) {
			tfStartPoint.setDisable(true);
			tfArrivalPoint.setDisable(true);
			tfStartTime.setDisable(true);
			tfMaxDuration.setDisable(true);
			tfNotes.setDisable(true);
			dpDate.setDisable(true);
			btCheckStart.setDisable(true);
			btCheckEnd.setDisable(true);
			btConfirm.setDisable(true);
			btAddCar.setVisible(true);
		} else {
			btAddCar.setVisible(false);
		}

		if (lp.getStatus().equals(Status.START)) {
			tfStartPoint.setText(" ");
		} else {
			tfStartPoint.setText(lp.getStartPoint().getAddress());
			tfStartPoint.setDisable(true);
		}

		if (lp.getStatus().equals(Status.STOP) || lp.getStatus().equals(Status.START)) {
			tfArrivalPoint.setText(" ");
		} else {
			tfArrivalPoint.setText(lp.getEndPoint().getAddress());
			tfArrivalPoint.setDisable(true);
		}

	}

}
