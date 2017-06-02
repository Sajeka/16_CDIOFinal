package rest;

import java.util.List;

public interface IDAO {
	boolean save(User user);
	List<User> getAll();
	boolean delete(String id);
	boolean editUser(User user);

}
