package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 数据库连接工具
 * @author 
 */
public class DBUtils {
	
	public static final String URL= "jdbc:mysql://localhost:3306/test?characterEncoding=utf8&useSSL=true";
	public static final String USER="root";
	public static final String PASSWORD="csy";
	
	private static Connection conn;
	
	static
        {
		try {
			//加载mysql驱动
			Class.forName("com.mysql.jdbc.Driver");
			//获得connection
			conn =	DriverManager.getConnection(URL,USER,PASSWORD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static Connection getConnection()
        {
		return conn;
	}
	
	public static void main(String[] args) throws Exception 
        {
	
	}
}
