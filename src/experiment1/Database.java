package experiment1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
	// JDBC驱动名和数据库的URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/customerInfo?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC&useSSL=false";
	// 数据库的用户名和密码
	static final String  USER = "root";
	static final String PASSWORD = "123456";
	// 数据库的连接
	static Connection connection;
	
	public Database() {
		unit();
	}
	
	private static void unit(){
		try {
			Class.forName(JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println("找不到驱动程序类");
			e.printStackTrace();
		} 
		
		try {
			connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Add a person to the database.
	 * @param list  The information of a person.
	 */
	public  void addData(String[] list) {
		String sql = "INSERT INTO customer(customerId, customerName, sex, occupation, eduLevel, address)" + "values("
				+ "?,?,?,?,?,?)";
		try {
			PreparedStatement ptmt = connection.prepareStatement(sql);
			for(int i = 0; i < list.length; i++) {
				ptmt.setString(i+1, list[i]);
			}
			
			ptmt.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Update a person's information.
	 * @param id	The updated person's id.
	 * @param informationList	The new information.
	 */
	public void update(String id, String[] informationList) {
		String sql = "Update customer  set customerId=?, customerName=?, sex=?, occupation=?,eduLevel=?,address=? where customerId="+ id;
		try {
			PreparedStatement ptmt = connection.prepareStatement(sql);
			for(int i = 0; i < informationList.length; i++) {
				ptmt.setString(i+1, informationList[i]);
			}
			ptmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Find person by id or by keyword in name.
	 * @param colName    Make sure find by id or name.
	 * @param information	Id or name.
	 * @return	The result found.
	 */
	public ResultSet get(String colName, String information) {
		String sql = "SELECT * FROM customer WHERE "+ colName + " LIKE '%"+ information+"%'";
		try {
			PreparedStatement ptmt = connection.prepareStatement(sql);
			ResultSet resultSet = ptmt.executeQuery();
			return resultSet;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		throw new NullPointerException("无此项");
	}
	
	/**
	 * Delete a person by id.
	 * @param id  Deleted person's id.
	 */
	public void delete(String id) {
		String sql = "Delete From customer where customerId=?";
		try {
			PreparedStatement ptmt = connection.prepareStatement(sql);
			ptmt.setString(1, id);
			ptmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
