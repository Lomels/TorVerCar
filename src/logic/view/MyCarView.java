package logic.view;

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
import logic.bean.CarInfoBean;
import logic.controller.LoginController;
import logic.controller.SetCarInfoController;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.ExceptionHandler;
import logic.controller.exception.InvalidInputException;
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
	public void start(Stage stage)  {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/My_car_page.fxml"));
		Parent root;
		try {
			root = loader.load();
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);

		stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	public void editButtonController(){
		tfModel.setDisable(false);
		tfColour.setDisable(false);
		tfPlate.setDisable(false);
		tfSeats.setDisable(false);
	}
	
	@FXML
	public void saveButtonController(){		
		cIBean.setModel(tfModel.getText());
		cIBean.setColour(tfColour.getText());
		cIBean.setPlate(tfPlate.getText());
		cIBean.setSeats(Integer.parseInt(tfSeats.getText()));
		try {
			controller.editCar(cIBean);
		} catch (InvalidInputException | DatabaseException e) {
			ExceptionHandler.handle(e);
		}
		
		tfModel.setDisable(true);
		tfColour.setDisable(true);
		tfPlate.setDisable(true);
		tfSeats.setDisable(true);
		btEdit.setDisable(false);

	}

	@FXML
	public void homeButtonController() {
		MainMenuView home = new MainMenuView();
		home.start((Stage) btHome.getScene().getWindow());
	}
	
	@FXML
	public void profileButtonController()  {
		ProfileView profile = new ProfileView();
		profile.start((Stage) btProfile.getScene().getWindow());
	}
	
	@FXML
	public void myCarButtonController()  {
		MyCarView myCar = new MyCarView();
		myCar.start((Stage) btMyCar.getScene().getWindow());
	}
	
	@FXML
	public void liftsButtonController()  {
		MyLiftView myLift = new MyLiftView();
		myLift.start((Stage) btLifts.getScene().getWindow());
	}
	
	@FXML
	public void logoutButtonController() {
		try {
			LoginController.logout();
		} catch (Exception e) {
			e.printStackTrace();
		}
		HomeView home = new HomeView();
		home.start((Stage) btLogout.getScene().getWindow());
	}
	
	@FXML
	public void bookButtonController()  {
		BookView book = new BookView();
		book.start((Stage) btBook.getScene().getWindow());
	}
	
	@FXML
	public void offerButtonController()  {
		OfferView offer = new OfferView();
		offer.start((Stage) btOffer.getScene().getWindow());
	}
	
	public boolean checkCar() {
		return sg.getRole().equals(Role.DRIVER);
		
	}
}


