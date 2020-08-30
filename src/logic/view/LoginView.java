package logic.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.stage.Stage;
import logic.bean.UserBean;
import logic.controller.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LoginView extends Application implements Initializable{
    @FXML private Button btHome;
	@FXML private TextField matricola;
	@FXML private PasswordField hiddenPwd;
	@FXML private TextField tfPwd;
	@FXML private CheckBox cbShow;
	@FXML private Button btLogin;
	@FXML private Text text;
	@FXML private Text error;
	
	
	@Override
	public void start(Stage stage) throws Exception{
		
		Parent root = FXMLLoader.load(getClass().getResource("fxml/login.fxml"));
		Scene scene = new Scene(root);
		stage.setTitle("Benvenuto in TorVerCar.");
		stage.setScene(scene);

		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	@FXML
	public void homeButtonController() throws IOException {
		HomeView home = new HomeView();
		home.start((Stage) btHome.getScene().getWindow());
	}
	
	@FXML
	public void loginButtonController() throws Exception{
		try {
			UserBean bean = createBean(matricola.getText(), tfPwd.getText());
			LoginController controller = new LoginController();
			controller.login(bean);
			MainMenuView main = new MainMenuView();
			main.start((Stage) btLogin.getScene().getWindow());
		} catch (Exception e) {
			// TODO: handle exception
			error.setText(e.getMessage());
		}
	}
	
	
	public UserBean createBean(String userID, String password) {
		UserBean user = new UserBean();
		user.setUserID(userID);
		user.setPassword(password);
		return user;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		tfPwd.setManaged(false);
		tfPwd.setVisible(false);
		
		tfPwd.managedProperty().bind(cbShow.selectedProperty());
		tfPwd.visibleProperty().bind(cbShow.selectedProperty());
		
		hiddenPwd.managedProperty().bind(cbShow.selectedProperty().not());
		hiddenPwd.visibleProperty().bind(cbShow.selectedProperty().not());

		tfPwd.textProperty().bindBidirectional(hiddenPwd.textProperty());
	}
}