package experiment1;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ProcessInformation
 */
@WebServlet("/ProcessInformation1")
public class ProcessInformation1 extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProcessInformation1() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 /*Id is the deleted person's id, the name is the button's name.Because the
		 'delete' button just pass id and its name, so I don't use the post method.*/
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
			switch (method) {
			case ("enter"):
				scan(request, response);
				break;
			
			case ("add"):
				addCustomer(request);
				scan(request, response);
				break;
			
			case ("modify"):
				String id = request.getParameter("modify_value");
				modify(request, response, id);
				scan(request, response);
				break;
			
			case ("query"):
				String queried_id = request.getParameter("findByID");
				String queried_name = request.getParameter("findByName");
				// 不能通过是不是等于null来判断
				
				if (queried_id.length() != 0) {
					find(request, response, queried_id, "customerId");
				} 
				else if (queried_name.length() != 0 && queried_id.length() == 0) {
					queried_name = new String(queried_name.getBytes("ISO8859-1"), "UTF-8");
					find(request, response, queried_name, "customerName");
				} 
				else {
					scan(request, response);
				}
			default:
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Add person to database.
	 * @param request
	 * @throws UnsupportedEncodingException
	 */
	public static void addCustomer(HttpServletRequest request) throws UnsupportedEncodingException {
		//Get the information of person.
		String[] informationList = getInfoList(request);
		Database database = new Database();
		database.addData(informationList);
	}

	
	/**
	 * Pass the resultSet to the findperson.jsp to display the people list in the findperson.jsp.
	 * 
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws SQLException
	 * @throws ServletException
	 */
	private static void scan(HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException, ServletException {
		Database database = new Database();

		ResultSet resultSet = database.get("customerName", "");
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
	 * @param information Id or keyword in name
	 * @param colName     Make sure find by id or name.
	 * @throws SQLException
	 * @throws ServletException
	 * @throws IOException
	 */
	public static void find(HttpServletRequest request, HttpServletResponse response, String information,
			String colName) throws SQLException, ServletException, IOException {
		Database database = new Database();
		ResultSet resultSet = database.get(colName, information);
		request.setAttribute("resultSet", resultSet);
		RequestDispatcher dispatcher = request.getRequestDispatcher("findperson.jsp");
		dispatcher.forward(request, response);
	}

	
	/**
	 * Delete a person by id.
	 * @param request
	 * @param response
	 * @param id
	 * @throws SQLException
	 * @throws ServletException
	 * @throws IOException
	 */
	public static void delete(HttpServletRequest request, HttpServletResponse response, String id)
			throws SQLException, ServletException, IOException {

		Database database = new Database();
		database.delete(id);
	}

	
	/**
	 * Update the information of a person by his id.
	 * @param request
	 * @param response
	 * @param id
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	public static void modify(HttpServletRequest request, HttpServletResponse response, String id)
			throws SQLException, UnsupportedEncodingException {

		Database database = new Database();
		database.update(id, getInfoList(request));

	}

	/**
	 * Put the information of a person in a array, easily using in other place.
	 * @param request
	 * @return a array contains of the information of a person.
	 * @throws UnsupportedEncodingException
	 */
	public static String[] getInfoList(HttpServletRequest request) throws UnsupportedEncodingException {
		String[] informationList = new String[6];
		informationList[0] = new String(request.getParameter("customerID").getBytes("ISO8859-1"), "UTF-8");
		informationList[1] = new String(request.getParameter("customerName").getBytes("ISO8859-1"), "UTF-8");
		informationList[2] = new String(request.getParameter("sex").getBytes("ISO8859-1"), "UTF-8");
		informationList[3] = new String(request.getParameter("occupation").getBytes("ISO8859-1"), "UTF-8");
		informationList[4] = new String(request.getParameter("eduLevel").getBytes("ISO8859-1"), "UTF-8");
		informationList[5] = new String(request.getParameter("address").getBytes("ISO8859-1"), "UTF-8");

		return informationList;
	}
}