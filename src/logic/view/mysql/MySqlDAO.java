package logic.view.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import logic.bean.CarInfoBean;
import logic.bean.UserBean;
import logic.controller.StudentBuilder;
import logic.controller.StudentCarBuilder;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.model.CarInfo;
import logic.model.Role;
import logic.model.Student;
import logic.model.StudentCar;
import logic.utilities.InputChecker;
import logic.view.OurStudentDatabase;

public class MySqlDAO implements OurStudentDatabase {

	private static final String USER = "torvercar";
	private static final String PASS = "ispw2020";
	private static final String URL = "jdbc:mysql://localhost:3306/TorVerCar?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true";
	private static final String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";

	private Statement stmt;
	private Connection conn;

	public MySqlDAO() {
		this.stmt = null;
		this.conn = null;
		try {
			Class.forName(DRIVER_CLASS_NAME);
		} catch (ClassNotFoundException e) {
			// TODO: gestione errore
			e.printStackTrace();
		}
	}

	@Override
	public boolean existByUserID(String userID) throws DatabaseException, InvalidInputException {
		InputChecker.checkUserID(userID);
		boolean result = false;

		try {
			this.connect();

			ResultSet rs = null;
			rs = MyQueries.existByUserID(this.stmt, userID);
			result = rs.first(); // return true if is not empty
			rs.close();

		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} 
		finally {
			this.disconnect();
		}
		return result;
	}

	@Override
	public boolean wasBannedByUserID(String userID) throws InvalidInputException, DatabaseException {
		InputChecker.checkUserID(userID);
		boolean result = false;

		try {
			this.connect();
			ResultSet rs = MyQueries.wasBannedByUserID(this.stmt, userID);
			result = rs.first();
			rs.close();

		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			this.disconnect();
		}
		return result;
	}

	@Override
	public void addStudent(Student student) throws DatabaseException, InvalidInputException {
		try {
			this.connect();
			MyQueries.addStudent(this.stmt, student, Role.STUDENT.name());
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			this.disconnect();
		}
	}
	
	@Override
	public void addStudentCar(StudentCar studentCar) throws DatabaseException, InvalidInputException{
		try {
			this.connect();
			MyQueries.addStudent(stmt, studentCar, Role.DRIVER.name());
			MyQueries.addStudentCar(this.stmt, studentCar);
			
		} catch(SQLException e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			this.disconnect();
		}
	}
		
	

	@Override
	public UserBean loadStudentByUserID(String userID) throws InvalidInputException, DatabaseException {
		InputChecker.checkUserID(userID);
		//Student s = null;
		UserBean usr = new UserBean();
		try {
			this.connect();
			ResultSet rs = MyQueries.loadStudentByUserID(this.stmt, userID);

			if (!rs.first())
				throw new DatabaseException("Student not found");


			rs.first();
			usr.setPassword(rs.getString("password"));
			usr.setUserID(userID);
			usr.setEmail(rs.getString("name"));
			usr.setName(rs.getString("name"));
			usr.setSurname(rs.getString("surname"));
			usr.setPhone(rs.getString("phone"));
			usr.setRole(Role.valueOf(rs.getString("role")));
			
			/*
			 * String password = rs.getString("password"); String name =
			 * rs.getString("name"); String surname = rs.getString("surname"); String email
			 * = rs.getString("email"); String phone = rs.getString("phone"); Role role =
			 * Role.valueOf(rs.getString("role"));
			 * 
			 * s = StudentBuilder.newBuilder(userID) .password(password)
			 * .fullname(name,surname) .email(email) .phone(phone) .build();
			 */

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException(e.getMessage());
		} finally {
			this.disconnect();
		}

		return usr;
	}

	// TODO: migliorare e ridurre le query
	@Override
	public StudentCar loadStudentCarByUserID(Student s) throws DatabaseException {
		StudentCar sCar = null;
		CarInfo carInfo;
		try {
			this.connect();
			ResultSet rs = MyQueries.loadStudentCarByUserID(this.stmt, s.getUserID());

			if (!rs.first())
				throw new DatabaseException("Student not found");

			rs.first();
			
			String rating = rs.getString("rating"); 
			String carID = rs.getString("carID");
			
			rs = MyQueries.loadCarInfoByCarID(this.stmt, carID);
			rs.first();
			carInfo = new CarInfo(rs.getString("plate"),
					rs.getInt("seats"),
					rs.getString("model"), 
					rs.getString("color"));
			  
			sCar = new StudentCarBuilder(s)
					.rating(Integer.parseInt(rating))
					.carInfo(carInfo)
					.build();

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException(e.getMessage());
		} finally {
			this.disconnect();
		}

		return sCar;
	}
	
	@Override
	public String loadPasswordByUserID(String userID) throws InvalidInputException, DatabaseException {
		InputChecker.checkUserID(userID);
		String password = null;
		try {
			this.connect();

			ResultSet rs = MyQueries.loadPasswordByUserID(this.stmt, userID);

			if (!rs.first())
				throw new DatabaseException("User not found");

			rs.first();
			password = rs.getString("password");

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException(e.getMessage());
		} finally {
			this.disconnect();
		}
		return password;
	}
	
	@Override
	public void editInfoByUserID(String userID, String password, String email, String phone) throws DatabaseException {
		try {
			this.connect();
			MyQueries.updateStudent(this.stmt, userID, password, email, phone);
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new DatabaseException(e.getMessage());
		} finally {
			this.disconnect();
		}
	}
	
	@Override
	public void editCarInfoByUserID(String userID, CarInfoBean carInfo) throws DatabaseException {
		try {
			this.connect();
			MyQueries.updateCar(this.stmt, userID, carInfo.getPlate(), carInfo.getModel(), carInfo.getSeats(), carInfo.getColour());

			
		}catch(Exception e) {
			e.printStackTrace();
			throw new DatabaseException(e.getMessage());
		} finally {
			this.disconnect();
		}
	}

	private void connect() {
		try {
			this.conn = DriverManager.getConnection(URL, USER, PASS);
			// TODO: informati su parametri
			this.stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	private void disconnect() {

		try {
			if (this.stmt != null)
				this.stmt.close();
		} catch (SQLException se) {
			try {
				if (this.conn != null)
					this.conn.close();

			} catch (SQLException se2) {
				// TODO handle exception
				se2.printStackTrace();
			}
		}

	}
}
