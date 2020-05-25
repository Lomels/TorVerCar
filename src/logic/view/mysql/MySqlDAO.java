package logic.view.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import logic.controller.StudentBuilder;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.model.Student;
import logic.utilities.InputChecker;
import logic.view.OurStudentDatabase;

public class MySqlDAO implements OurStudentDatabase {

	private static final String USER = "torvercar";
	private static final String PASS = "ispw2020";
	private static final String URL = "jdbc:mysql://localhost:3306/TorVerCar";
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
			MyQueries.addStudent(this.stmt, student);
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			this.disconnect();
		}
	}

	@Override
	public Student loadStudentByUserID(String userID) throws InvalidInputException, DatabaseException {
		InputChecker.checkUserID(userID);
		Student s = null;
		try {
			this.connect();

			ResultSet rs = MyQueries.loadStudentByUserID(this.stmt, userID);

			if (!rs.first())
				throw new DatabaseException("Student not found");

			rs.first();
			String password = rs.getString("password");
			String name = rs.getString("name");
			String surname = rs.getString("surname");
			s = StudentBuilder.newBuilder(userID).password(password).fullname(name, surname).build();

			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException(e.getMessage());
		} finally {
			this.disconnect();
		}
		return s;
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
