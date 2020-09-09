package logic.view;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ViewController {
	
	
	public void start(String path, Stage stage) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(path));

		Parent root;
		try {
			root = loader.load();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setResizable(false);

			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
