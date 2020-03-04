package logic.view.FXML;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;
import logic.view.*;

public class FXMLLauncher extends Application {
	private HomeBoundary homeController;
	private LoginView loginController;
	private Stage stage;
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		this.stage = primaryStage;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("homepage.fxml"));
		Parent root = (Parent)loader.load();
		homeController = loader.getController();
		
		Scene scene = new Scene(root);
		stage.setTitle("TorVerCar");
		stage.setScene(scene);
		stage.show();
	}

	public void sceneSwitch(String fxmlFile) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
		Parent root;
		try {
			root = (Parent)loader.load();
			if(fxmlFile.equals("login.fxml")) {
				loginController = (LoginView)loader.getController();
			}
			Scene scene = new Scene(root);
			(this.stage).setScene(scene);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
