package logic.view.FXML;

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import logic.bean.LoginBean;
import logic.controller.InputChecker;
import logic.controller.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LoginView extends Application {
    private LoginBean userBean;
    private LoginController control = new LoginController();
    
    @FXML private Button homeButton;

	@FXML private TextField matricola;
	@FXML private TextField pwd;
	@FXML private Button loginButton;
	@FXML private Text text;
	@FXML private Text error;
	
	
	@Override
	public void start(Stage stage) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
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
		home.start((Stage) homeButton.getScene().getWindow());
	}
	
	@FXML
	public void buttonController() throws Exception{
		try {
			InputChecker.checkUserID(matricola.getText());
			InputChecker.checkPassword(pwd.getText());
			userBean = createBean(matricola.getText(), pwd.getText());
			control.login(userBean);
			//TODO chiamare controller grafico di homepage
			text.setText("Succesfully logged in.");
		} catch (Exception e) {
			// TODO: handle exception
			error.setText(e.getMessage());
		}
	}
	
	public LoginBean createBean(String userID, String password) {
		LoginBean bean = new LoginBean();
		bean.setUserID(userID);
		bean.setPwd(password);
		return bean;
	}
}
