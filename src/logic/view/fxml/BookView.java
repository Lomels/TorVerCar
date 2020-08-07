package logic.view.fxml;

import java.io.IOException;


import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class BookView extends Application  {
	@FXML private Button btHome;
	@FXML private Button btBook;
	@FXML private Button btMyCar;
	@FXML private Button btProfile;
	@FXML private Button btLogout;
	@FXML private TextField tfStartPoint;
	@FXML private TextField tfArrivalPoint;
	@FXML private TextField tfStartTime;
	@FXML private TextField tfArrivalTime;
	
	
	
	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Book.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();		
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void homeButtonController() throws Exception {
		MainMenuView home = new MainMenuView();
		home.start((Stage) btHome.getScene().getWindow());
	}
	
	public void bookButtonController() throws Exception {
		BookView book = new BookView();
		book.start((Stage) btBook.getScene().getWindow());
	}
	
	public void myCarButtonController() throws Exception {
		MyCarView car = new MyCarView();
		car.start((Stage) btMyCar.getScene().getWindow());
		
	}
	
	public void profileButtonController() throws Exception {
		ProfileView profile = new  ProfileView();
		profile.start((Stage) btProfile.getScene().getWindow());
	}
	
	public void logoutButtonController() throws IOException {
		HomeView home = new HomeView();
		home.start((Stage) btLogout.getScene().getWindow());
	}
	
	//TODO: implementare Find e Offer

}
