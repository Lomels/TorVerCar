package logic.view.fxml;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;

public class AddressListView extends Application implements Initializable {
	@FXML private Button btHome;
	@FXML private Button btBook;
	@FXML private Button btMyCar;
	@FXML private Button btProfile;
	@FXML private Button btLogout;
	@FXML private Button btOffer;
	@FXML private TextField tfStartPoint;
	@FXML private TextField tfArrivalPoint;
	@FXML private TextField tfStartTime;
	@FXML private TextField tfArrivalTime;
	@FXML private Button btCheckStart;
	@FXML private Button btCheckEnd;
	@FXML private Button btConfirm;
	@FXML private ListView<Row> addressList;
	
	
	
	@Override
	public void start(Stage stage) throws Exception {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("address_list.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();		
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@FXML
	public void homeButtonController() throws Exception {
		MainMenuView home = new MainMenuView();
		home.start((Stage) btHome.getScene().getWindow());
	}
	
	@FXML
	public void bookButtonController() throws Exception {
		BookView book = new BookView();
		book.start((Stage) btBook.getScene().getWindow());
	}
	
	@FXML
	public void myCarButtonController() throws Exception {
		MyCarView car = new MyCarView();
		car.start((Stage) btMyCar.getScene().getWindow());
		
	}
	
	@FXML
	public void profileButtonController() throws Exception {
		ProfileView profile = new  ProfileView();
		profile.start((Stage) btProfile.getScene().getWindow());
	}
	
	@FXML
	public void logoutButtonController() throws IOException {
		HomeView home = new HomeView();
		home.start((Stage) btLogout.getScene().getWindow());
	}
	
	@FXML
	public void offerButtonController() throws Exception {
		OfferView offer = new OfferView();
		offer.start((Stage) btOffer.getScene().getWindow());
	}


    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
    	addressList.setCellFactory(lv -> new ListCell<Row>() {
    	    private Node graphic ;
    	    private RowList controller ;

    	    {
    	        try {
    	            FXMLLoader loader = new FXMLLoader(getClass().getResource("list_cell.fxml"));
    	            graphic = loader.load();
    	            controller = loader.getController();
    	        } catch (IOException exc) {
    	            throw new RuntimeException(exc);
    	        }
    	    }

    	    @Override
    	    protected void updateItem(Row row, boolean empty) {
    	        super.updateItem(row, empty);
    	        if (empty) {
    	            setGraphic(null);
    	        } else {
    	        	controller.setAddress(row.getAddress());
    	        	controller.setMap(row.getURL());
    	        
    	           
    	            setGraphic(graphic);
    	        }
		
    	    }
    	});
    }
}
	
	
	//TODO implements confirm




