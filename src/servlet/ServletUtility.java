package servlet;

import java.util.List;

import javax.servlet.http.HttpSession;

import logic.bean.UserBean;
import logic.controller.exception.ApiNotReachableException;
import logic.controller.exception.InvalidInputException;
import logic.controller.maps.AdapterMapsApi;
import logic.controller.maps.MapsApi;
import logic.model.Position;
import logic.model.Role;
import logic.model.Student;
import logic.model.StudentCar;
import logic.model.UserSingleton;

public class ServletUtility {

	public static void createUser(UserBean usr, HttpSession session) throws InvalidInputException {
		UserSingleton sg = UserSingleton.getInstance();
		usr.setRole(sg.getRole());

		if (usr.getRole().equals(Role.STUDENT)) {
			Student student = sg.getStudent();
			session.setAttribute("user", student);
			return;
		}else if (usr.getRole().equals(Role.DRIVER)) {
			StudentCar studentCar = sg.getStudentCar();
			session.setAttribute("user", studentCar);
			return;
		}
	}

	public static List<Position> pupulateListPosition(String address)
			throws ApiNotReachableException, InvalidInputException {
		MapsApi mapsApi = AdapterMapsApi.getInstance();
		return mapsApi.addrToPos(address);

	}
}
