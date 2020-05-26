package logic.view.FXML.registration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import logic.bean.UserInfoBean;
import logic.controller.RegistrationController;
import logic.model.Singleton;
import logic.model.Student;
import logic.view.FXML.HomeView;
import logic.view.FXML.MainMenuView;

public class RecapView extends Application{
	@FXML private Button btHome;
	@FXML private Button btNext;
	@FXML private Button btEdit;
	@FXML private Text tfName;  
	@FXML private Text tfSurname;
	@FXML private Text tfId;
	@FXML private Text tfEmail;
	@FXML private Text tfPhone;
	Singleton sg = Singleton.getInstance();
	
	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Recap_database_info.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		stage.setTitle("Recap");
		stage.setScene(scene);
		setInfo();
		stage.show();
	}

	
	public static void main(String[] args) {
		launch(args);
	}

	public void setInfo() {
		Student student = sg.getUser();
		tfName.setText(student.getName());
		tfSurname.setText(student.getSurname());
		tfEmail.setText(student.getEmail());
		tfPhone.setText(student.getPhone());
		tfId.setText(student.getUserID());
	}
	
	@FXML
	public void btNextController() throws Exception {
		MainMenuView next = new MainMenuView();
		next.start((Stage) btNext.getScene().getWindow());
	}

	@FXML
	public void btHomeController() throws IOException {
		HomeView home = new HomeView();
		home.start((Stage) btHome.getScene().getWindow());
	}

	@FXML
	public void btEditController() throws Exception {
		EditInfoView edit = new EditInfoView(/*this.user*/);
		edit.start((Stage) btNext.getScene().getWindow());
	}


	
}

