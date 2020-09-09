package logic.view;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import logic.controller.LoginController;
import logic.controller.exception.ExceptionHandler;
import logic.controller.exception.InvalidStateException;
import logic.view.booking.BookView;
import logic.view.offer.OfferView;

public class ViewController extends Application {
	@FXML
	protected Button btHome;
	@FXML
	protected Button btMyCar;
	@FXML
	protected Button btProfile;
	@FXML
	protected Button btLogout;
	@FXML
	protected Button btOffer;
	@FXML
	protected Button btBook;
	@FXML
	protected Button btLifts;
	
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

	@Override
	public void start(Stage primaryStage) throws Exception {
		
	}
	
	@FXML
	public void homeButtonController()  {
		MainMenuView home = new MainMenuView();
		home.start((Stage) btHome.getScene().getWindow());
	}

	@FXML
	public void liftsButtonController()  {
		MyLiftView myLift = new MyLiftView();
		myLift.start((Stage) btLifts.getScene().getWindow());
	}

	@FXML
	public void myCarButtonController()  {
		MyCarView car = new MyCarView();
		car.start((Stage) btMyCar.getScene().getWindow());
	}

	@FXML
	public void profileButtonController(){
		ProfileView profile = new ProfileView();
		profile.start((Stage) btProfile.getScene().getWindow());
	}

	@FXML
	public void logoutButtonController()  {
		
			try {
				LoginController.logout();
			} catch (InvalidStateException e) {
				ExceptionHandler.handle(e);;
			}
		
		HomeView home = new HomeView();
		home.start((Stage) btLogout.getScene().getWindow());
	}

	@FXML
	public void offerButtonController()  {
		OfferView offer = new OfferView();
		offer.start((Stage) btOffer.getScene().getWindow());
	}

	@FXML
	public void bookButtonController()  {
		BookView book = new BookView();
		book.start((Stage) btBook.getScene().getWindow());
	}

}
