package logic.view.offer;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.bean.LiftBean;
import logic.controller.LiftController;
import logic.controller.exception.ApiNotReachableException;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.ExceptionHandler;
import logic.controller.exception.InvalidInputException;
import logic.controller.exception.InvalidStateException;
import logic.controller.maps.CompleteMapsApi;
import logic.controller.maps.MapsApi;
import logic.model.LiftSingleton;
import logic.model.UserSingleton;
import logic.utilities.Status;
import logic.view.MainMenuView;
import logic.view.MyCarView;
import logic.view.ViewController;
import logic.model.Role;

public class OfferView extends ViewController implements Initializable {
	
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
	

	private MapsApi mapsApi = CompleteMapsApi.getInstance();
	private LiftSingleton lp = LiftSingleton.getInstance();
	private UserSingleton userSg = UserSingleton.getInstance();
	private LiftController controller = new LiftController();
	private ViewController viewController = new ViewController();


	@Override
	public void start(Stage stage){
		viewController.start("fxml/Offer.fxml", stage);
	}

	public static void main(String[] args) {
		launch(args);
	}

	
	@FXML
	public void checkStartAddressController()  {
		lp.setAddress(1);
		try {
			lp.setListPos(mapsApi.addrToPos(tfStartPoint.getText()));
		} catch (ApiNotReachableException | InvalidInputException e) {
			ExceptionHandler.handle(e);
		}
		AddressListView list = new AddressListView();
		list.start((Stage) btCheckStart.getScene().getWindow());
	}

	@FXML
	public void checkEndAddressController() {
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
	public void addCarButtonController()  {
		lp.clearState();
		MyCarView car = new MyCarView();
		car.start((Stage) btAddCar.getScene().getWindow());

	}

	@FXML
	public void confirmButtonController() {
		LiftBean lift = new LiftBean();
		String time = dpDate.getValue().toString() + "T" + tfStartTime.getText();
		lift.setStartDateTime(LocalDateTime.parse(time));
		lift.setMaxDuration(Integer.parseInt(tfMaxDuration.getText()));
		lift.setNote(tfNotes.getText());
		lift.setDriver(userSg.getStudentCar());
		lift.setStartPos(lp.getStartPoint());
		lift.setStopPos(lp.getEndPoint());
		try {
			controller.createLift(lift);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (InvalidInputException | DatabaseException | InvalidStateException | ApiNotReachableException e) {
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
