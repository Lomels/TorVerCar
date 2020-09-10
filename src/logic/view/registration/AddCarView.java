package logic.view.registration;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.bean.CarInfoBean;
import logic.bean.UserBeanSingleton;
import logic.bean.UserBeanSingleton;
import logic.controller.RegistrationController;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.ExceptionHandler;
import logic.controller.exception.InvalidInputException;
import logic.view.HomeView;
import logic.view.LoginView;
import logic.view.MyLiftView;
import logic.view.ViewController;

public class AddCarView extends Application {
	Stage stage;
	@FXML
	private Button btHome;
	@FXML private Button btNext;
	@FXML private Button btBack;

	@FXML
	private Button btLifts;
	@FXML private TextField tfModel;
	@FXML private TextField tfColour;
	@FXML private TextField tfSeats;
	@FXML private TextField tfPlate;
	private ViewController view = new ViewController();
	private UserBeanSingleton sg = UserBeanSingleton.getInstance();
	
	@Override
	public void start(Stage primaryStage){
		view.start("fxml/Add_car_info.fxml", primaryStage);
	}

	public static void main(String[] args) {
		launch(args);
	}

	@FXML
	public void btNextController()  {
		CarInfoBean carInfo = new CarInfoBean();
		carInfo.setModel(tfModel.getText());
		carInfo.setColour(tfColour.getText());
		carInfo.setPlate(tfPlate.getText());
		carInfo.setSeats(Integer.parseInt(tfSeats.getText()));

		RegistrationController controller = new RegistrationController();
		try {
			controller.addStudentCar(carInfo, sg.getUserBean());
		} catch (InvalidInputException | DatabaseException e) {
			ExceptionHandler.handle(e);
		}

		LoginView login = new LoginView();
		login.start((Stage) btNext.getScene().getWindow());

	}

	@FXML
	public void liftsButtonController() {
		MyLiftView myLift = new MyLiftView();
		myLift.start((Stage) btLifts.getScene().getWindow());
	}

	@FXML
	public void btHomeController()  {
		HomeView home = new HomeView();
		home.start((Stage) btHome.getScene().getWindow());
	}

	@FXML
	public void btBackController()  {
		AddInfoView addInfo = new AddInfoView();
		addInfo.start((Stage) btBack.getScene().getWindow());

	}
}
