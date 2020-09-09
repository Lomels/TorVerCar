package logic.view;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;
import javafx.util.Callback;
import logic.bean.UserBean;
import logic.controller.LiftController;
import logic.model.Lift;
import logic.model.UserSingleton;
import logic.view.mylift.RowMyLift;

public class MyLiftView extends ViewController implements Initializable {
	@FXML
	private ListView<Lift> lvLift;
	@FXML
	private RadioButton rbOffered;
	@FXML
	private RadioButton rbBooked;

	private LiftController liftController = new LiftController();
	private UserSingleton sg = UserSingleton.getInstance();

	private List<Lift> offered = new ArrayList<>();
	private List<Lift> booked = new ArrayList<>();
	private UserBean userBean = new UserBean();
	private ViewController view = new ViewController();
	
	@Override

	public void start(Stage stage){
		view.start("fxml/mylift_list.fxml", stage);
		
	}
	public static void main(String[] args) {
		launch(args);
	}

	public void rbBookedController() {
		if (rbBooked.isSelected()) {
			rbBooked.setDisable(true);
			rbOffered.setSelected(false);
			rbOffered.setDisable(false);
			lvLift.getItems().clear();

			for (Lift result : booked) {
				lvLift.getItems().add(result);
			}
		}
	}

	public void rbOfferedController() {
		if (rbOffered.isSelected()) {
			rbOffered.setDisable(true);
			rbBooked.setSelected(false);
			rbBooked.setDisable(false);
			lvLift.getItems().clear();
			for (Lift result : offered) {
				lvLift.getItems().add(result);
			}
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		userBean.setUserID(sg.getUserID());
		offered = liftController.loadOfferedLift(userBean);
		booked = liftController.loadBookedLift(userBean);
		
		if (rbOffered.isSelected()) {
			for (Lift result : offered) {
				lvLift.getItems().add(result);
			}
		} else {
			for (Lift result : booked) {
				lvLift.getItems().add(result);
			}
		}
		
		lvLift.setCellFactory(new Callback<ListView<Lift>, ListCell<Lift>>() {
			@Override
			public ListCell<Lift> call(ListView<Lift> param) {
				return new RowMyLift();
			}
		});
	}
}
