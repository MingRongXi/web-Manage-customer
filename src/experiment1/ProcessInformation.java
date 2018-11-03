package experiment1;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ProcessInformation
 */
@WebServlet("/ProcessInformation")
public class ProcessInformation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// JDBC 椹卞姩鍚嶅強鏁版嵁搴� URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/customerInfo?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC&useSSL=false";
	// 鏁版嵁搴撶殑鐢ㄦ埛鍚嶄笌瀵嗙爜锛岄渶瑕佹牴鎹嚜宸辩殑璁剧疆
	static final String USER = "root";
	static final String PASSWORD = "123456";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProcessInformation() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Id is the deleted person's id, the name is the button's name.Because the
		// 'delete' button just pass id and its name, so I don't use the post method.
		String id = request.getParameter("id");
		String name = request.getParameter("name");

		try {
			if (name.equals("delete")) {
				delete(request, response, id);
				scan(request, response);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// When adding, modifying and querying, I post a form to the servlet, so when
		// response the request, I need to tell which button post a form.
		String method = request.getParameter("method");
		try {
			if (method.equals("enter")) {
				scan(request, response);
			}

			else if (method.equals("add")) {
				addCustomer(request);
				scan(request, response);
			} else if (method.equals("modify")) {
				String id = request.getParameter("modify_value");
				modify(request, response, id);
				scan(request, response);

			} else if (method.equals("query")) {
				String queried_id = request.getParameter("findByID");
				String queried_name = request.getParameter("findByName");
				System.out.println("queried_name = " + queried_name);
				// 涓嶈兘閫氳繃鏄笉鏄瓑浜巒ull鏉ュ垽鏂�
				if (queried_id.length() != 0) {
					System.out.println("鏌ヨid = " + queried_id);
					find(request, response, queried_id, "customerId");
				} else if (queried_name.length() != 0 && queried_id.length() == 0) {
					queried_name = new String(queried_name.getBytes("ISO8859-1"), "UTF-8");
					System.out.println("queried_name = " + queried_name);
					find(request, response, queried_name, "customerName");
				} else {
					scan(request, response);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void addCustomer(HttpServletRequest request) throws UnsupportedEncodingException {
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//		} catch (ClassNotFoundException e) {
//			System.out.println("鎵句笉鍒伴┍鍔ㄧ▼搴忕被");
//			e.printStackTrace();
//		}
//		String id = new String(request.getParameter("customerID").getBytes("ISO8859-1"), "UTF-8");
//		String name = new String(request.getParameter("customerName").getBytes("ISO8859-1"), "UTF-8");
//		String sex = new String(request.getParameter("sex").getBytes("ISO8859-1"), "UTF-8");
//		String occupation = new String(request.getParameter("occupation").getBytes("ISO8859-1"), "UTF-8");
//		String eduLevel = new String(request.getParameter("eduLevel").getBytes("ISO8859-1"), "UTF-8");
//		String address = new String(request.getParameter("address").getBytes("ISO8859-1"), "UTF-8");
//		Connection connection = null;
//		PreparedStatement ptmt = null;
//
//		try {
//			connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
//		} catch (SQLException e) {
//			System.out.println("Failed to get connection: " + e.getMessage());
//			e.printStackTrace();
//		}
//
//		String sql = "INSERT INTO customer(customerId, customerName, sex, occupation, eduLevel, address)" + "values("
//				+ "?,?,?,?,?,?)";
//		try {
//			ptmt = connection.prepareStatement(sql);
//			ptmt.setString(1, id);
//			ptmt.setString(2, name);
//			ptmt.setString(3, sex);
//			ptmt.setString(4, occupation);
//			ptmt.setString(5, eduLevel);
//			ptmt.setString(6, address);
//
//			ptmt.execute();
//		} catch (SQLException e1) {
//			e1.printStackTrace();
//		}
//		try {
//			ptmt.setString(1, id);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		Database database = new Database();
		String[] list = { new String(request.getParameter("customerID").getBytes("ISO8859-1"), "UTF-8"),
				new String(request.getParameter("customerName").getBytes("ISO8859-1"), "UTF-8"),
				new String(request.getParameter("sex").getBytes("ISO8859-1"), "UTF-8"),
				new String(request.getParameter("occupation").getBytes("ISO8859-1"), "UTF-8"),
				new String(request.getParameter("eduLevel").getBytes("ISO8859-1"), "UTF-8"),
				new String(request.getParameter("address").getBytes("ISO8859-1"), "UTF-8") };
		database.addData(list);
	}

	/**
	 * Pass the resultSet to the findperson.jsp to display the people list in the
	 * jsp.
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws SQLException
	 * @throws ServletException
	 */
	private static void scan(HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException, ServletException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("鎵句笉鍒伴┍鍔ㄧ▼搴忕被");
			e.printStackTrace();
		}
		Connection connection = null;
		Statement statement = null;
		PreparedStatement ptmt = null;

		try {
			connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
			statement = connection.createStatement();
		} catch (SQLException e) {
			System.out.println("Failed to get connection: " + e.getMessage());
			e.printStackTrace();
		}

		String sql = "SELECT customerId, customerName, sex, occupation, eduLevel, address From customer";
		ResultSet resultSet = statement.executeQuery(sql);

		/*
		 * The trouble thought is getting a resultSet, then travesing the resultSet and
		 * storing the people in a list in the servlet, then pass the list to the jsp,
		 * then traversing the list to display the person in the jsp, after improving
		 * the code, I pass the resultSet to the jsp derectly, and travesing the
		 * resultSet to display the people in the jsp.
		 */

//		while(resultSet.next()) {
//			CustomerInfo customerInfo = new CustomerInfo();
//			customerInfo.id = (resultSet.getString("customerId"));
//			customerInfo.name = (resultSet.getString("customerName"));
//			customerInfo.sex = (resultSet.getString("sex"));
//			customerInfo.occupation = (resultSet.getString("occupation"));
//			customerInfo.eduLevel = (resultSet.getString("eduLevel"));
//			customerInfo.address = (resultSet.getString("address"));
//			customerInfos.add(customerInfo);
//		}
//		 ptmt.execute();
//		 request.setAttribute("customerList", customerInfos);

		// pass the result to the findperson.jsp.
		request.setAttribute("resultSet", resultSet);

		// Redirect to the findperson.jsp.
		RequestDispatcher dispatcher = request.getRequestDispatcher("findperson.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * Find person by id exactly, or find person by the keyword in name roughly.
	 * 
	 * @param request
	 * @param response
	 * @param information id or keyword in name
	 * @param colName     Make sure find by id or name.
	 * @throws SQLException
	 * @throws ServletException
	 * @throws IOException
	 */
	public static void find(HttpServletRequest request, HttpServletResponse response, String information,
			String colName) throws SQLException, ServletException, IOException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("鎵句笉鍒伴┍鍔ㄧ▼搴忕被");
			e.printStackTrace();
		}

		Connection connection = null;
		PreparedStatement ptmt = null;

		try {
			connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
		} catch (SQLException e) {
			System.out.println("Failed to get connection: " + e.getMessage());
			e.printStackTrace();
		}

		String sql = "SELECT * FROM customer WHERE " + colName + " LIKE '%" + information + "%'";
		ptmt = connection.prepareStatement(sql);

		ResultSet resultSet = ptmt.executeQuery();
		request.setAttribute("resultSet", resultSet);
		RequestDispatcher dispatcher = request.getRequestDispatcher("findperson.jsp");
		dispatcher.forward(request, response);
	}

	public static void delete(HttpServletRequest request, HttpServletResponse response, String id)
			throws SQLException, ServletException, IOException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("鎵句笉鍒伴┍鍔ㄧ▼搴忕被");
			e.printStackTrace();
		}

		Connection connection = null;
		PreparedStatement ptmt = null;

		try {
			connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
		} catch (SQLException e) {
			System.out.println("Failed to get connection: " + e.getMessage());
			e.printStackTrace();
		}

		String sql = "Delete From customer where customerId=?";
		ptmt = connection.prepareStatement(sql);
		ptmt.setString(1, id);
		int i = ptmt.executeUpdate();
		System.out.println(i);
	}

	public static void modify(HttpServletRequest request, HttpServletResponse response, String id)
			throws SQLException, UnsupportedEncodingException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("鎵句笉鍒伴┍鍔ㄧ▼搴忕被");
			e.printStackTrace();
		}
		Connection connection = null;
		PreparedStatement ptmt = null;

		try {
			connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
		} catch (SQLException e) {
			System.out.println("Failed to get connection: " + e.getMessage());
			e.printStackTrace();
		}
		String sql = "Update customer  set customerId=?, customerName=?, sex=?, occupation=?,eduLevel=?,address=? where customerId="
				+ id;
		ptmt = connection.prepareStatement(sql);

		String customerId = new String(request.getParameter("customerID").getBytes("ISO8859-1"), "UTF-8");
		String name = new String(request.getParameter("customerName").getBytes("ISO8859-1"), "UTF-8");
		String sex = new String(request.getParameter("sex").getBytes("ISO8859-1"), "UTF-8");
		String occupation = new String(request.getParameter("occupation").getBytes("ISO8859-1"), "UTF-8");
		String eduLevel = new String(request.getParameter("eduLevel").getBytes("ISO8859-1"), "UTF-8");
		String address = new String(request.getParameter("address").getBytes("ISO8859-1"), "UTF-8");
		ptmt.setString(1, customerId);
		ptmt.setString(2, name);
		ptmt.setString(3, sex);
		ptmt.setString(4, occupation);
		ptmt.setString(5, eduLevel);
		ptmt.setString(6, address);
		int i = ptmt.executeUpdate();
		System.out.println(i);
	}
}
