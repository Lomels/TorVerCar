package logic.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import logic.bean.LoginBean;
import logic.controller.InputChecker;
import logic.controller.LoginController;

public class LoginFXMLController {
    private LoginBean userBean;
    private LoginController control = new LoginController();

	@FXML private TextField matricola;
	@FXML private TextField pwd;
	@FXML private Button loginButton;
	@FXML private Text text;
	@FXML private URL location;
    @FXML private ResourceBundle resources;

    
	/* RICORDATI DI METTERE onAction="#nomeController" COME ATTRIBUTO NELL'ELEMENTO DA CONTROLLARE */
	
	@FXML
	public void buttonController() throws Exception{
		InputChecker.checkUserID(matricola.getText());
		InputChecker.checkPassword(pwd.getText());
		userBean = createBean(matricola.getText(), pwd.getText());
		control.login(userBean);
		//TODO chiamare controller grafico di homepage
		text.setText("Succesfully logged in.");
	}
	
	public LoginBean createBean(String userID, String password) {
		LoginBean bean = new LoginBean();
		bean.setUserID(userID);
		bean.setPwd(password);
		return bean;
	}
	
}
