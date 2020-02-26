package logic.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
//import javafx.scene.control.TextArea;
//import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.text.Text;

public class loginFXMLController {

	@FXML
	private TextField matricola;
	
	@FXML
	private TextField pwd;
	
	@FXML
	private Button loginButton;
	
	@FXML 
	private Text text;
	
	@FXML
    private URL location;
     
    @FXML
    private ResourceBundle resources;
	
	@FXML
    private void initialize() 
    {
    }

	/* RICORDATI DI METTERE onAction="#nomeController" COME ATTRIBUTO NELL'ELEMENTO DA CONTROLLARE */
	
	@FXML
	private void buttonController() {
		text.setText("culo");
	}
	
}
