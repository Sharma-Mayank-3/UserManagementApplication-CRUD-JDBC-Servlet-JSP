package userManagementApplication.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import userManagementApplication.model.User;

public class UserDao {
	private final String CreateNewUser = "insert into users(name, email, country) values(?, ?, ?)";
	private final String GetUserList = "select * from users";
	private final String GetUserById = "select * from users where id = ?";
	private final String UpdateUserById = "update users set name = ?, email = ?, country = ? where id = ?";
	private final String DeleteUser = "delete from users where id = ?";
	
	private Connection connection;
	
	
	public boolean createNewUser(User user) throws SQLException {
		ConnectionUtil con = new ConnectionUtil();
		boolean b = false;
		try {
			connection = con.getConnection();
			
			PreparedStatement prepareStatement = connection.prepareStatement(CreateNewUser);
			prepareStatement.setString(1, user.getName());
			prepareStatement.setString(2, user.getEmail());
			prepareStatement.setString(3, user.getCountry());
			
			b = prepareStatement.executeUpdate() > 0;
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return b;
	}
	
	
	
	public ArrayList<User> getAllUsers() throws SQLException {
		ConnectionUtil con = new ConnectionUtil();
		ArrayList<User> userList = new ArrayList<User>();
		try {
			connection = con.getConnection();
			
			PreparedStatement prepareStatement = connection.prepareStatement(GetUserList);
			
			ResultSet result = prepareStatement.executeQuery();
			
			while(result.next()) {
				int id = result.getInt("id");
				String name = result.getString("name");
				String email = result.getString("email");
				String country = result.getString("country");
				User user = new User(id, name, email, country);
				userList.add(user);
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return userList;
		
	}
	
	public User getUserById(int id) throws SQLException {
		ConnectionUtil con = new ConnectionUtil();
		User user = null;
		try {
			connection = con.getConnection();
			
			PreparedStatement prepareStatement = connection.prepareStatement(GetUserById);
			prepareStatement.setInt(1, id);
			
			ResultSet result = prepareStatement.executeQuery();
			
			while(result.next()) {
				String name = result.getString("name");
				String email = result.getString("email");
				String country = result.getString("country");
				user = new User(id, name, email, country);
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return user;
	}
	
	public boolean updateUserById(User user) throws SQLException {
		ConnectionUtil con = new ConnectionUtil();
		boolean b = false;
		try {
			connection = con.getConnection();
			
			PreparedStatement prepareStatement = connection.prepareStatement(UpdateUserById);
			
			prepareStatement.setString(1, user.getName());
			prepareStatement.setString(2, user.getEmail());
			prepareStatement.setString(3, user.getCountry());
			prepareStatement.setInt(4, user.getId());
			
			b = prepareStatement.executeUpdate() > 0;
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return b;
	}
	
	
	public boolean deleteUser(int id) throws SQLException {
		ConnectionUtil con = new ConnectionUtil();
		boolean b = false;
		try {
			connection = con.getConnection();
			
			PreparedStatement prepareStatement = connection.prepareStatement(DeleteUser);
			
			prepareStatement.setInt(1, id);
			
			b = prepareStatement.executeUpdate() > 0;
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}
}
