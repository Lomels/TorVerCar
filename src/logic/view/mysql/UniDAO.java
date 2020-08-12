package logic.view.mysql;

import java.sql.*;

import logic.bean.UserBean;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.utilities.InputChecker;
import logic.view.DatabaseBoundary;

public class UniDAO implements DatabaseBoundary{

	private static final String USER = "torvercar";
	private static final String PASS = "ispw2020";
	private static final String URL = "jdbc:mysql://localhost:3306/UniDB?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true";
	private static final String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
	
	private Statement stmt;
	private Connection conn;
	
	public UniDAO() {
		this.stmt = null;
		this.conn = null;
		try {
			Class.forName(DRIVER_CLASS_NAME);
		}catch(ClassNotFoundException e) {
			//TODO: gestione
			e.printStackTrace();
		}
	}
	
	@Override
	public UserBean getByUserID(String userID) throws InvalidInputException, DatabaseException {
		InputChecker.checkUserID(userID);
		UserBean info = null;
		try {		
			this.connect();
			
			ResultSet rs = MyQueries.getInfoByUserID(this.stmt, userID);
			
			if(!rs.first())
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
		}catch(Exception e) {
			e.printStackTrace();
			throw new DatabaseException(e.getMessage());
		}finally {
			this.disconnect();
		}
		return info;
	}
		
	@Override
	public Boolean existByUserID(String userID) throws DatabaseException, InvalidInputException{
		Boolean result = false;		
		try {
			this.connect();
			ResultSet rs = MyQueries.existByUserId_UniDB(this.stmt, userID);
			result = rs.first();
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		} 
		finally {
			this.disconnect();
		}
		return result;
	}
	
	private void connect() {
		try {
			this.conn = DriverManager.getConnection(URL, USER, PASS);
			this.stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		}catch(Exception e) {
			//TODO: gestione
			e.printStackTrace();
		}
	}
	
	private void disconnect() {
		try {
			if(this.stmt != null) 
				this.stmt.close();
		}catch(SQLException se) {
			try {
				if(this.conn != null) 
					this.conn.close();	
			}catch(SQLException se2) {
				//TODO handle
				se2.printStackTrace();
			}
		}
	}


}
