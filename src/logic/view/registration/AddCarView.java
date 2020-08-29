package logic.view.registration;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.bean.CarInfoBean;
import logic.bean.UserBeanSingleton;
import logic.controller.RegistrationController;
import logic.view.HomeView;
import logic.view.LoginView;

public class AddCarView extends Application{
	Stage stage;
	@FXML private Button btHome, btNext, btBack;
	@FXML private TextField tfModel, tfColour, tfSeats, tfPlate;
	UserBeanSingleton sg = UserBeanSingleton.getInstance();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("../fxml/Add_car_info.fxml"));
		this.stage = primaryStage;
		Scene scene = new Scene(root);
		stage.setTitle("Add Car Infos");
		stage.setScene(scene);
		stage.show();
	}
	

	public static void main(String[] args) {
		launch(args);
	}
	
	@FXML
	public void btNextController() throws Exception {		
		CarInfoBean carInfo = new CarInfoBean();
		carInfo.setModel(tfModel.getText());
		carInfo.setColour(tfColour.getText());
		carInfo.setPlate(tfPlate.getText());
		carInfo.setSeats(Integer.parseInt(tfSeats.getText()));
		
		RegistrationController controller = new RegistrationController();
		controller.addStudentCar(carInfo);
		
		LoginView login = new LoginView();
		login.start((Stage) btNext.getScene().getWindow());
		
	}
	
	@FXML
	public void btHomeController() throws IOException {
		HomeView home = new HomeView();
		home.start((Stage) btHome.getScene().getWindow());
	}
	
	@FXML
	public void btBackController() throws Exception{
		AddInfoView addInfo = new AddInfoView();
		addInfo.start((Stage) btBack.getScene().getWindow());
		
	}
}
