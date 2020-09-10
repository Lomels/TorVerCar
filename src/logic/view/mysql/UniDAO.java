package logic.view.mysql;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import logic.bean.UserBean;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.utilities.InputChecker;
import logic.view.DatabaseBoundary;

public class UniDAO implements DatabaseBoundary {

	private static final String USER = "torvercar";
	private static final String PASS = "ispw2020";
	private static final String URL = "jdbc:mysql://localhost:3306/UniDB?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true";

	private Statement stmt;
	private Connection conn;

	public UniDAO() {
		this.stmt = null;
		this.conn = null;

	}

	@Override
	public UserBean getByUserID(String userID) throws InvalidInputException, DatabaseException {
		InputChecker.checkUserID(userID);
		UserBean info = null;
		try {
			this.connect();

			ResultSet rs = MyQueries.getInfoByUserID(this.stmt, userID);

			if (!rs.first())
				throw new DatabaseException("Student Not Found");

			rs.first();

			String name = rs.getString("name");
			String surname = rs.getString("surname");
			String email = rs.getString("email");
			info = new UserBean();
			info.setUserID(userID);
			info.setName(name);
			info.setSurname(surname);
			info.setEmail(email);

			rs.close();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		} finally {
			this.disconnect();
		}
		return info;
	}

	@Override
	public Boolean existByUserID(String userID) throws DatabaseException, InvalidInputException {
		Boolean result = false;
		try {
			this.connect();
			ResultSet rs = MyQueries.existByUserIdUniDB(this.stmt, userID);
			result = rs.first();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		} finally {
			this.disconnect();
		}
		return result;
	}

	private void connect() throws DatabaseException {
		try {
			this.conn = DriverManager.getConnection(URL, USER, PASS);
			this.stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			throw new DatabaseException(e);
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
				Logger.getLogger(this.getClass().getCanonicalName()).log(Level.SEVERE, "Exception thrown.", se2);
			}
		}
	}

}
