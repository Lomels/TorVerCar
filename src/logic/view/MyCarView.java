package logic.view;

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
import logic.bean.CarInfoBean;
import logic.controller.LoginController;
import logic.controller.SetCarInfoController;
import logic.model.Role;
import logic.model.UserSingleton;
import logic.view.booking.BookView;
import logic.view.offer.OfferView;

public class MyCarView extends Application implements Initializable{
	@FXML private Button btBack;
	@FXML private Button btEdit;
	@FXML private Button btHome;
	@FXML private Button btProfile;
	@FXML private Button btLogout;
	@FXML private Button btMyCar;
	@FXML private Button btSave;
	@FXML private Button btBook;
	@FXML private Button btOffer;
	@FXML private Button btLifts;
	
	@FXML private TextField tfModel;
	@FXML private TextField tfColour;
	@FXML private TextField tfPlate;
	@FXML private TextField tfSeats;

	UserSingleton sg = UserSingleton.getInstance();
	CarInfoBean cIBean = new CarInfoBean();
	SetCarInfoController controller = new SetCarInfoController();
	
	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/My_car_page.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btEdit.setDisable(true);
		if(checkCar()) {
			tfModel.setText(sg.getStudentCar().getCarInfo().getModel());
			tfColour.setText(sg.getStudentCar().getCarInfo().getColour());
			tfPlate.setText(sg.getStudentCar().getCarInfo().getPlate());
			tfSeats.setText(sg.getStudentCar().getCarInfo().getSeats().toString());
		
			tfModel.setDisable(true);
			tfColour.setDisable(true);
			tfPlate.setDisable(true);
			tfSeats.setDisable(true);
			
			btEdit.setDisable(false);
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
	@FXML
	public void editButtonController() throws Exception {
		tfModel.setDisable(false);
		tfColour.setDisable(false);
		tfPlate.setDisable(false);
		tfSeats.setDisable(false);
	}
	
	@FXML
	public void saveButtonController() throws Exception{		
		cIBean.setModel(tfModel.getText());
		cIBean.setColour(tfColour.getText());
		cIBean.setPlate(tfPlate.getText());
		cIBean.setSeats(Integer.parseInt(tfSeats.getText()));
		controller.editCar(cIBean);
		
		tfModel.setDisable(true);
		tfColour.setDisable(true);
		tfPlate.setDisable(true);
		tfSeats.setDisable(true);
		btEdit.setDisable(false);

	}

	@FXML
	public void homeButtonController() throws Exception {
		MainMenuView home = new MainMenuView();
		home.start((Stage) btHome.getScene().getWindow());
	}
	
	@FXML
	public void profileButtonController() throws Exception {
		ProfileView profile = new ProfileView();
		profile.start((Stage) btProfile.getScene().getWindow());
	}
	
	@FXML
	public void myCarButtonController() throws Exception {
		MyCarView myCar = new MyCarView();
		myCar.start((Stage) btMyCar.getScene().getWindow());
	}
	
	@FXML
	public void liftsButtonController() throws Exception {
		MyLiftView myLift = new MyLiftView();
		myLift.start((Stage) btLifts.getScene().getWindow());
	}
	
	@FXML
	public void logoutButtonController() throws Exception {
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
	public void bookButtonController() throws Exception {
		BookView book = new BookView();
		book.start((Stage) btBook.getScene().getWindow());
	}
	
	@FXML
	public void offerButtonController() throws Exception {

		OfferView offer = new OfferView();
		offer.start((Stage) btOffer.getScene().getWindow());

		//TODO: Implementare

	}
	
	public boolean checkCar() {
		if(sg.getRole().equals(Role.DRIVER)) return true;
		
		return false;
	}
}


