package logic.view.fxml;

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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.controller.LiftController;
import logic.controller.maps.AdapterMapsApi;
import logic.controller.maps.MapsApi;
import logic.model.LiftSingleton;
import logic.model.UserSingleton;
import logic.model.Role;

public class OfferView extends Application implements Initializable{
	@FXML private Button btHome;
	@FXML private Button btBook;
	@FXML private Button btMyCar;
	@FXML private Button btProfile;
	@FXML private Button btLogout;
	@FXML private Button btOffer;
	@FXML private TextField tfStartPoint;
	@FXML private TextField tfArrivalPoint;
	@FXML private TextField tfStartTime;
	@FXML private TextField tfMaxDuration;
	@FXML private TextField tfNotes;
	@FXML private TextField tfDay;
	@FXML private Button btCheckStart;
	@FXML private Button btCheckEnd;
	@FXML private Button btConfirm;
	@FXML private Button btAddCar;
	
	private MapsApi mapsApi = AdapterMapsApi.getInstance();
	private LiftSingleton lp = LiftSingleton.getInstance();
	private UserSingleton userSg = UserSingleton.getInstance();
	private LiftController controller = new LiftController();
	
	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Offer.fxml"));
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
	public void profileButtonController() throws Exception {
		ProfileView profile = new  ProfileView();
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
	public void checkStartAddressController() throws Exception {
		lp.setAddress(1);
		lp.setListPos(mapsApi.addrToPos(tfStartPoint.getText()));
		AddressListView list = new AddressListView();
		list.start((Stage) btCheckStart.getScene().getWindow());
	}
	
	@FXML
	public void checkEndAddressController() throws Exception {
		lp.setAddress(2);
		lp.setListPos(mapsApi.addrToPos(tfArrivalPoint.getText()));
		AddressListView list = new AddressListView();
		list.start((Stage) btCheckEnd.getScene().getWindow());
	}
	
	@FXML
	public void addCarButtonController() throws Exception {
		MyCarView car = new MyCarView();
		car.start((Stage) btAddCar.getScene().getWindow());
		
	}

	@FXML
	public void confirmButtonController() throws Exception {
		String time = tfDay.getText() + "T" + tfStartTime.getText();
		lp.setDepartureTime(time);
		lp.setMaxDuration(tfMaxDuration.getText());
		lp.setNotes(tfNotes.getText());
		controller.createLift(lp.getDepartureTime(),
				Integer.parseInt(lp.getMaxDuration()),
				lp.getNotes(),
				userSg.getStudentCar(),
				lp.getStartPoint(), lp.getEndPoint());
		
		MainMenuView home = new MainMenuView();
		home.start((Stage) btConfirm.getScene().getWindow());
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if(userSg.getRole().equals(Role.STUDENT)) {
			tfStartPoint.setDisable(true);
			tfArrivalPoint.setDisable(true);
			tfStartTime.setDisable(true);
			tfMaxDuration.setDisable(true);
			tfNotes.setDisable(true);
			tfDay.setDisable(true);
			btCheckStart.setDisable(true);
			btCheckEnd.setDisable(true);
			btConfirm.setDisable(true);
			
			btAddCar.setVisible(true);
			
			}else {
				btAddCar.setVisible(false);
			}
		
		if(lp.status.equals(LiftSingleton.START)) {
			tfStartPoint.setText(" ");
		}else {
			tfStartPoint.setText(lp.getStartPoint().getAddress());
			tfStartPoint.setDisable(true);
		}
		
		if(lp.status.equals(LiftSingleton.STOP) | lp.status.equals(LiftSingleton.START)) {
			tfArrivalPoint.setText(" ");
		}else {
			tfArrivalPoint.setText(lp.getEndPoint().getAddress());
			tfArrivalPoint.setDisable(true);
		}
		
		
		
		
	}
	
	
	
		
}
	
