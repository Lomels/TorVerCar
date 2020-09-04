package servlet;

import java.util.List;

import logic.bean.UserBean;
import logic.controller.exception.ApiNotReachableException;
import logic.controller.exception.InvalidInputException;
import logic.controller.maps.AdapterMapsApi;
import logic.controller.maps.MapsApi;
import logic.model.Position;
import logic.model.Role;
import logic.model.UserSingleton;

public class ServletUtility {

	
	public static UserBean populateUserBean(UserBean usr) {
		UserSingleton sg = UserSingleton.getInstance();
		
		usr.setRole(sg.getRole());
		if(usr.getRole().equals(Role.STUDENT)) {
			usr.setEmail(sg.getStudent().getEmail());
			usr.setName(sg.getStudent().getName());
			usr.setSurname(sg.getStudent().getSurname());
			usr.setPhone(sg.getStudent().getPhone());
		}else {
			usr.setEmail(sg.getStudentCar().getEmail());
			usr.setName(sg.getStudentCar().getName());
			usr.setSurname(sg.getStudentCar().getSurname());
			usr.setPhone(sg.getStudentCar().getPhone());
			usr.setCarInfo(sg.getStudentCar().getCarInfo());
			usr.setRating(sg.getStudentCar().getRating());
		}
		return usr;
	}
	
	public static List<Position> pupulateListPosition(String address) throws ApiNotReachableException, InvalidInputException{
		MapsApi mapsApi = AdapterMapsApi.getInstance();
		return mapsApi.addrToPos(address);
		
	}
}
