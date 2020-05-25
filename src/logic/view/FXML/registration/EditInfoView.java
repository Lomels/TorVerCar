package logic.view.FXML.registration;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import logic.view.FXML.HomeView;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.Button;

public class EditInfoView extends Application{
	@FXML Button btHome;
	@FXML Button btNext;
	@FXML Button btBack;
	
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		Parent root = FXMLLoader.load(getClass().getResource("Edit_DBInfo.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	@FXML
	public void btHomeController() throws IOException{
		HomeView home = new HomeView();
		home.start((Stage) btHome.getScene().getWindow());
	}
	
	public void btNextController() throws Exception{
		AddCarView addCar = new AddCarView();
		addCar.start((Stage) btNext.getScene().getWindow());
	}
	
	public void btBackController() throws Exception{
		RecapView recap = new RecapView(/*user*/);
		recap.start((Stage) btBack.getScene().getWindow());
	}
}
