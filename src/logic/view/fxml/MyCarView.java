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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.model.StudentCarSingleton;
import logic.model.UserSingleton;

public class MyCarView extends Application implements Initializable{
	@FXML private Button btBack;
	@FXML private Button btEdit;
	@FXML private Button btHome;
	@FXML private Button btProfile;
	@FXML private Button btLogout;
	@FXML private Button btMyCar;
	@FXML private Button btSave;
	
	@FXML private TextField tfModel;
	@FXML private TextField tfColour;
	@FXML private TextField tfPlate;
	@FXML private TextField tfSeats;

	StudentCarSingleton sg = StudentCarSingleton.getInstance();
	
	
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader(getClass().getResource("My_car_page.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		
		stage.setScene(scene);
		stage.show();
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tfModel.setText(sg.getUser().getCarInfo().getModel());
		tfColour.setText(sg.getUser().getCarInfo().getColour());
		tfPlate.setText(sg.getUser().getCarInfo().getPlate());
		tfSeats.setText(sg.getUser().getCarInfo().getSeats().toString());
		
		tfModel.setDisable(true);
		tfColour.setDisable(true);
		tfPlate.setDisable(true);
		tfSeats.setDisable(true);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
	@FXML
	public void editButtonController() throws Exception {
		MyCarView myCar = new MyCarView();
		myCar.start((Stage) btEdit.getScene().getWindow());
		
		tfModel.setDisable(false);
		tfColour.setDisable(false);
		tfPlate.setDisable(false);
		tfSeats.setDisable(false);
	}
	
	@FXML
	public void saveButtonController() throws Exception {
		
		sg.getUser().getCarInfo().setModel(tfModel.getText());
		sg.getUser().getCarInfo().setColour(tfColour.getText());
		sg.getUser().getCarInfo().setPlate(tfPlate.getText());
		sg.getUser().getCarInfo().setSeats(Integer.parseInt(tfSeats.getText()));
		
		
		
		MyCarView myCar = new MyCarView();
		myCar.start((Stage) btSave.getScene().getWindow());
		
		
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
	public void logoutButtonController() throws Exception {
		HomeView home = new HomeView();
		home.start((Stage) btLogout.getScene().getWindow());
	}
	
}


