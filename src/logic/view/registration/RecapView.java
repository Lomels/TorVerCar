package logic.view.registration;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import logic.bean.UserBean;
import logic.bean.UserBeanSingleton;
import logic.controller.RegistrationController;
import logic.view.HomeView;
import logic.view.ViewController;

public class RecapView extends Application implements Initializable{
	@FXML private Button btHome;
	@FXML private Button btNext;
	@FXML private TextField tfName;  
	@FXML private TextField tfSurname;
	@FXML private TextField tfId;
	@FXML private TextField tfEmail;
	UserBeanSingleton sg = UserBeanSingleton.getInstance();
	
	private ViewController view = new ViewController();
	
	@Override
	public void start(Stage primaryStage){
		view.start("fxml/Recap_database_info.fxml", primaryStage);

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		UserBean user = sg.getUserBean();
		tfId.setText(user.getUserID());
		tfName.setText(user.getName());
		tfSurname.setText(user.getSurname());
		tfEmail.setText(user.getEmail());
	}

	@FXML
	public void btNextController()  {
		RegistrationController controller = new RegistrationController();
		controller.sendCode();
		CheckIdentityView next = new CheckIdentityView();
		next.start((Stage) btNext.getScene().getWindow());
	}

	
	
	@FXML
	public void btHomeController()  {
		HomeView home = new HomeView();
		home.start((Stage) btHome.getScene().getWindow());
	}

}

