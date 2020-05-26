package logic.view.FXML;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.model.UserSingleton;

public class MainMenuView extends Application implements Initializable{
	@FXML private Text tvWelcome;
	
	UserSingleton sg = UserSingleton.getInstance();
	
	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Home_menu.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String string = "Hi, %s!";
		String welcome = String.format(string, sg.getUser().getName().toString());
		tvWelcome.setText(welcome);
	}

}
