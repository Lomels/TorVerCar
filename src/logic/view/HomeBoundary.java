package logic.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import logic.view.FXML.*;

public class HomeBoundary {
	@FXML private Button loginButton;
	@FXML private Button regButton;
	FXMLLauncher launcher = new FXMLLauncher();
	
	public void loginButtonController() {
		System.out.println("login\n");
		launcher.sceneSwitch("login.fxml");
	}
	
	public void regButtonController() {
		System.out.println("register\n");

		//launcher.sceneSwitch("register.fxml");
	}
}
