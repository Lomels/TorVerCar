package logic.view.fxml;

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import logic.bean.UserBean;
import logic.controller.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LoginView extends Application {
    @FXML private Button btHome;
	@FXML private TextField matricola;
	@FXML private TextField pwd;
	@FXML private Button btLogin;
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
		home.start((Stage) btHome.getScene().getWindow());
	}
	
	@FXML
	public void loginButtonController() throws Exception{
		try {
			UserBean bean = createBean(matricola.getText(), pwd.getText());
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
}
