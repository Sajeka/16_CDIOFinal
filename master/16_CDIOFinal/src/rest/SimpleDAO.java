package rest;

import java.util.ArrayList;
import java.util.List;

public class SimpleDAO implements IDAO {
	private static ArrayList<User> allUsers = new ArrayList<User>(); 
	static {
		allUsers.add(new User("1234567890", "Sarina", "SA", "password", 1));
		allUsers.add(new User("2345678901", "Iman", "IM", "password", 2));
		allUsers.add(new User("3456789012", "Hemen", "HE", "password", 3));
		allUsers.add(new User("4567890123", "Emil", "EG", "password", 4));
	}

	@Override
	public boolean save(User user) {
		System.out.println(user);
		User existingUser = findUser(user.getOprCpr());
		if (existingUser != null) 
			return false;
		allUsers.add(user);
		return true;	
	}

	@Override
	public List<User> getAll() {
		return allUsers;
	}
	
	@Override
	public boolean editUser(User user) {
		User existingUser = findUser(user.getOprCpr());
		if (existingUser == null) return false;
		existingUser.setOprNavn(user.getOprNavn());
		existingUser.setPassword(user.getPassword());
		existingUser.setRolleId(user.getRolleId());
		return true;
	}

	@Override
	public boolean delete(String id) {
		User existingUser = findUser(id);
		if (existingUser == null) return false;
		allUsers.remove(existingUser);
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


}
