package data.FileIO;

import java.sql.*;

import javax.naming.*;
import javax.sql.DataSource;

public class DatabaseConn {
	public static synchronized Connection getConnection() throws Exception{
		try{
			
			Connection connect = DriverManager.getConnection(
			          "jdbc:mysql://localhost:3306/mysql","root","873877");
			
			return connect;
		}
		
		catch(SQLException e){
			throw e;
		}
	}

}
