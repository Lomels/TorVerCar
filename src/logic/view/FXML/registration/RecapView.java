package logic.view.FXML.registration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import logic.bean.UserInfo;
import logic.controller.RegistrationController;
import logic.view.FXML.HomeView;

public class RecapView extends Application{
	
	
	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Recap_database_info.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		Controller controller = loader.getController();
		stage.setTitle("Recap");
		stage.setScene(scene);
		//setInfo();
		stage.show();
	}

	
	public static void main(String[] args) {
		launch(args);
	}

	public static class Controller implements Initializable {
		@FXML private Button btHome;
		@FXML private Button btNext;
		@FXML private Button btEdit;
		@FXML private Text tfName;  
		@FXML private Text tfSurname;
		@FXML private Text tfId;
		@FXML private Text tfEmail;
		private UserInfo user;
		private RegistrationController control = new RegistrationController();
		
		public Controller(String userID) throws Exception { 
			user = control.recapInfo(userID);
			setInfo(user);
		}
		  
		public Controller(UserInfo newUser) { 
			this.user = newUser; 
			setInfo(user); 
		}
		
		@FXML
		public void btNextController() throws Exception {
			//setInfo();
		}
		
		public void setInfo(UserInfo user) {
			tfName.setText(user.getName());
			tfSurname.setText(user.getSurname());
			tfId.setText(user.getUserID());
			tfEmail.setText(user.getEmail());
			/*tfName.setText("");
			tfSurname.setText("ciao");
			tfId.setText("ciao");
			tfEmail.setText("ciao");*/
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

		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			// TODO Auto-generated method stub
			
		}
	}
	
}

