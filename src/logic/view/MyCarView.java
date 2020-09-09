package logic.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.bean.CarInfoBean;
import logic.controller.SetCarInfoController;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.ExceptionHandler;
import logic.controller.exception.InvalidInputException;
import logic.model.Role;
import logic.model.UserSingleton;

public class MyCarView extends ViewController implements Initializable{
	@FXML private Button btBack;
	@FXML private Button btEdit;
	@FXML private Button btSave;
	
	@FXML private TextField tfModel;
	@FXML private TextField tfColour;
	@FXML private TextField tfPlate;
	@FXML private TextField tfSeats;

	UserSingleton sg = UserSingleton.getInstance();
	CarInfoBean cIBean = new CarInfoBean();
	SetCarInfoController controller = new SetCarInfoController();
	private ViewController view = new ViewController();
	
	@Override

	public void start(Stage stage){
		view.start("fxml/My_car_page.fxml", stage);
		
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

	
	public boolean checkCar() {
		return sg.getRole().equals(Role.DRIVER);
		
	}
}


