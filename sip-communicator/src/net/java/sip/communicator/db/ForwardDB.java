package net.java.sip.communicator.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.HashSet;
import java.util.NoSuchElementException;


public class ForwardDB {
	
	String connectionURL = "jdbc:mysql://localhost:3306/softeng";
	Connection connection = null;
	PreparedStatement statement = null;
	String dbuser = "root";
	String dbpass = "1234";
	
	//Constructor
	public ForwardDB() {
		connect();
	}
	
	public void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(connectionURL, dbuser, dbpass);
		} catch (SQLException | ClassNotFoundException e) {
			//Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getFinalForward (String caller) {
		String forward_to = null;
		
		try {
			
			statement = connection.prepareStatement("SELECT ForwardTo FROM Forwarding where ForwardFrom = ?");
			String tempFrom = caller; 
			while (true) {
				statement.setString(1, tempFrom);
				ResultSet rs = statement.executeQuery();
				if (rs.next()) {
					forward_to = rs.getString("ForwardTo");
					tempFrom = forward_to;	
				} else
					break;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return forward_to;
	}
	
	
	public String getForward(String username) {
		String forward_to = "";
		
		try {
		
			statement = connection
					.prepareStatement("SELECT ForwardTo FROM Forwarding where ForwardFrom = ?");

			statement.setString(1, username);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				forward_to = rs.getString("ForwardTo");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return forward_to;
	}
	
	public void setForward(String fromUser, String toUser)
			throws NoSuchElementException, RuntimeException {

		// first check if there is such user
		try {
			statement = connection
					.prepareStatement("SELECT * FROM Users where username = ?");
			statement.setString(1, toUser);
			ResultSet rs = statement.executeQuery();
			if ((rs == null) || (!rs.next()))
				throw new NoSuchElementException();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// second check if circle
		HashSet<String> userSet = new HashSet<String>();
		userSet.add(fromUser);
		userSet.add(toUser);

		try {
			statement = connection
					.prepareStatement("SELECT ForwardTo FROM Forwarding where ForwardFrom = ?");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String tempFrom = toUser;
		String forward_to;
		while (true) {
			try {
				statement.setString(1, tempFrom);
				ResultSet rs = statement.executeQuery();
				if (rs.next()) {
					forward_to = rs.getString("ForwardTo");
					if (!userSet.add(forward_to))
						throw new RuntimeException();
					tempFrom = forward_to;
				} else
					break;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		/*
		 * ready to set the new forward first delete the old if exists and then
		 * set the new
		 */

		try {
			statement = connection
					.prepareStatement("delete from Forwarding where ForwardFrom=?");
			statement.setString(1, fromUser);
			statement.executeUpdate();
			statement = connection
					.prepareStatement("insert into Forwarding set ForwardFrom=?, ForwardTo=?");
			statement.setString(1, fromUser);
			statement.setString(2, toUser);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public void resetForward(String user) {
		try {
			statement = connection
					.prepareStatement("delete from Forwarding where ForwardFrom=?");
			statement.setString(1, user);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
}
