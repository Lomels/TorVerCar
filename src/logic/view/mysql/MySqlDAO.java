package logic.view.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import logic.controller.InputChecker;
import logic.controller.exception.DatabaseException;
import logic.controller.exception.InvalidInputException;
import logic.entity.Student;
import logic.view.OurStudentDatabase;

public class MySqlDAO implements OurStudentDatabase{
	
	//TODO: implementare come singleton
	
	private static String USER = "torvercar";
	private static String PASS = "ispw2020";
	private static String DB_URL = "jdbc:mysql://localhost:3306/TorVerCar";
	private static String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
	 

	@Override
	public boolean existByUserID(String userID) throws DatabaseException, InvalidInputException {
		InputChecker.checkUserID(userID);
		//STEP 1: Dichiarazioni
		Statement stmt = null;
		Connection conn = null;
		boolean result = false;
		ResultSet rs = null;
		
		try {
			//STEP 2: Controllo driver
			Class.forName(DRIVER_CLASS_NAME);
			
			//STEP 3: Apertura connessione
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			
			//STEP 4: Esecuzione query
			//TODO: informati su parametri
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
			rs = MyQueries.existByUserID(stmt, userID);
			result = rs.first();	//return true if is not empty
			
			//STEP 5: Pulizia
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException(e.getMessage());
		}
		finally {
			
			try {
				if(stmt != null)
					stmt.close();
			} catch (SQLException se) {
				try {
					if(conn != null)
						conn.close();
					
				} catch (SQLException se2) {
					se2.printStackTrace();
				}
			}
		}
		return result;
	}

	@Override
	public boolean wasBannedByUserID(String userID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addStudent(Student student) throws DatabaseException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Student loadStudentByUserID(String userID) throws InvalidInputException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String loadPasswordByUserID(String userID) throws InvalidInputException, DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

}
