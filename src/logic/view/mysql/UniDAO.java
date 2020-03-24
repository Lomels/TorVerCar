package logic.view.mysql;

import java.sql.*;

import logic.bean.UserInfo;
import logic.controller.InputChecker;
import logic.controller.exception.DatabaseException;
import logic.view.DatabaseBoundary;

public class UniDAO implements DatabaseBoundary{

	private static final String USER = "torvercar";
	private static final String PASS = "ispw2020";
	private static final String URL = "jdbc:mysql://localhost:3306/UniDB?autoReconnect=true&useSSL=false";
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
	public UserInfo getByUserID(String userID) throws Exception {
		InputChecker.checkUserID(userID);
		UserInfo info = null;
		try {		
			this.connect();
			
			ResultSet rs = MyQueries.getInfoByUserID(this.stmt, userID);
			
			if(!rs.first())
				throw new DatabaseException("Student Not Found");
			
			rs.first();
			
			String name = rs.getString("name");
			String surname = rs.getString("surname");
			String email = rs.getString("email");
			info = new UserInfo();
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
