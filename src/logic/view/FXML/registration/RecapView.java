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
import logic.view.FXML.HomeView;

public class RecapView extends Application{
	Controller controller = new Controller();
	protected static UserInfoBean user = new UserInfoBean();
	
	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Recap_database_info.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		controller = loader.getController();
		stage.setTitle("Recap");
		stage.setScene(scene);
		controller.setInfo();
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
		
		private RegistrationController control = new RegistrationController();
		
		@FXML
		public void btNextController() throws Exception {
			
		}
		
		public void setUser(String userID) throws Exception {
			user = control.recapInfo(userID);
			System.out.println(user.getName()+" "+user.getSurname());
		}
		
		public void setInfo() {
			tfName.setText(user.getName());
			tfSurname.setText(user.getSurname());
			tfId.setText(user.getUserID());
			tfEmail.setText(user.getEmail());
			System.out.println(user.getEmail()+" "+user.getUserID());

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

