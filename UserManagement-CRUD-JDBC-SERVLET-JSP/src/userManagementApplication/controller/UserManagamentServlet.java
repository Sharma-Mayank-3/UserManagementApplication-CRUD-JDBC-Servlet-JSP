package userManagementApplication.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import userManagementApplication.dao.UserDao;
import userManagementApplication.model.User;

/**
 * Servlet implementation class UserManagamentServlet
 */
@WebServlet("/")
public class UserManagamentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	public UserDao userDao;
	
	public void init() {
		userDao = new UserDao();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		
		switch (action) {
		case "/new":
			createNew(request, response);
			break;
			
			case "/update":
				updateUser(request, response);
				break;
				
			case "/delete":
			try {
				deleteUser(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				break;
				
			case "/insert":
			try {
				insertUser(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				break;
				
			case "/edit":
			try {
				editUser(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				break;
				
		default:
			getUserList(request, response);
			break;
		}
		
	}
	
	public void getUserList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ArrayList<User> listUser = userDao.getAllUsers();
			
			request.setAttribute("listUser", listUser);
			
			request.getRequestDispatcher("view/user-list.jsp").forward(request, response);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createNew(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher("view/user-new.jsp").forward(request, response);
		} catch (ServletException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String idString = request.getParameter("id");
		int id = Integer.parseInt(idString);
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String country = request.getParameter("country");
		
		User user = new User(id, name, email, country);
		try {
			userDao.updateUserById(user);
			
			response.sendRedirect("list");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		String idString = request.getParameter("id");
		int id = Integer.parseInt(idString);
		userDao.deleteUser(id);
		response.sendRedirect("list");
	}
	
	public void insertUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String country = request.getParameter("country");
		User user = new User(name, email, country);
		userDao.createNewUser(user);
		response.sendRedirect("list");
	}
	
	public void editUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		String idString = request.getParameter("id");
		int id = Integer.parseInt(idString);
		User user = userDao.getUserById(id);
		request.setAttribute("user", user);
		request.getRequestDispatcher("view/user-new.jsp").forward(request, response);
	}
}
