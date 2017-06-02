package rest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class sqlDAO implements IDAO {
	private static ArrayList<User> allUsers = new ArrayList<User>();
	

	private Connection con;
	private Statement st;
	private ResultSet rs;
	private PreparedStatement saveStmt;

	
	public sqlDAO() {
		try{
			Class.forName("com.mysql.jdbc.Driver");
			
			con = DriverManager.getConnection("jdbc:mysql://185.121.172.103/nizzledk_cdio?useSSL=false","nizzledk_cdio","P9f7gIA5TqZ2");
		    saveStmt = con.prepareStatement("INSERT INTO operatoer(opr_cpr, opr_navn, ini, password, rolle_id) VALUES (?, ?, ?, ?, ?)");
			
			st = con.createStatement();
			
		}catch(Exception ex){
			System.out.println("Error: "+ex);
		}
	}

	@Override
	public boolean save(User user) {
		System.out.println(user);
		User existingUser = findUser(user.getOprCpr());
		if (existingUser != null) 
			return false;
		allUsers.add(user);
		saveUsers();
		return true;	
	}

	@Override
	public List<User> getAll() {
		return loadUsers();
	}
	
	@Override
	public boolean editUser(User user) {
		User existingUser = findUser(user.getOprCpr());
		if (existingUser == null) return false;
		existingUser.setOprNavn(user.getOprNavn());
		existingUser.setIni(user.getIni());
		existingUser.setPassword(user.getPassword());
		existingUser.setRolleId(user.getRolleId());
		saveUsers();
		return true;
	}

	@Override
	public boolean delete(String id) {
		User existingUser = findUser(id);
		if (existingUser == null) return false;
		allUsers.remove(existingUser);
		saveUsers();
		return true;

	}

	private User findUser(String OprCpr) {
		for (User existingUser : allUsers) {
			if (existingUser.getOprCpr().equals(OprCpr)) {
				return existingUser;
			}
		}
		return null;
	}


	private List<User> loadUsers() {
		try{
			String query = "SELECT * FROM operatoer";
			rs = st.executeQuery(query);
			System.out.println("Records from database:");
			allUsers = new ArrayList<User>();
			while(rs.next()){
				User user = new User(rs.getString("opr_cpr"), rs.getString("opr_navn"), rs.getString("ini"), rs.getString("password"), rs.getInt("rolle_id"));
				allUsers.add(user);
			}
		}catch(Exception ex){
			System.out.println(ex);
		}
		return allUsers;
	}

	private void saveUsers() {
		try{
			String query = "TRUNCATE TABLE operatoer";
			st.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (User user : allUsers) {
		    try {
			    saveStmt.setString(1, user.getOprCpr());
			    saveStmt.setString(2, user.getOprNavn());
			    saveStmt.setString(3, user.getIni());
			    saveStmt.setString(4, user.getPassword());
			    saveStmt.setInt(5, user.getRolleId());
		    	saveStmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}

}
