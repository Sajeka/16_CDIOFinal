package rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

// crud
// svarer til 
// post,get,put,delete

@Path("users")
public class UserService {
	private IDAO idao = new sqlDAO();
	
	// Svarer til read
	// wget -method GET "http://localhost:8080/UserService/rest2/users"
	// postman GET http://localhost:8080/UserService/rest2/users
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsers() {
		return idao.getAll();
	}
	
	//Svarer til create
	//wget -method POST "http://localhost:8080/UserService/rest2/users" -Body '{"id":"40","name":"Ulrik"}' -ContentType "Application/Json"
	//Postman POST http://localhost:8080/UserService/rest2/users Body > {"id":"40","name":"Ulrik"} Headers(1) Key: ContentType > Value: Application/Json
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean addUser(User u) {
			return idao.save(u);
	}

	//Svarer til opdater
    //wget -method PUT "http://localhost:8080/UserService/rest2/users" -Body '{"id":"40","name":"Ulrik h"}' -ContentType "Application/Json"
	//Postman PUT http://localhost:8080/UserService/rest2/users Body row > {"id":"40","name":"Ulrik"} Headers(1) Key: ContentType > Value: Application/Json
	@PUT
	public boolean editUser(User u) {
		return idao.editUser(u);	
	}

	// ok1 svarer til Slet
	//wget -method DELETE "http://localhost:8080/UserService/rest2/users" -Body '{"id":"20"}' -ContentType "Application/Json"
	//wget -method DELETE "http://localhost:8080/UserService/rest2/users" -Body '{"id":"22"}' -ContentType "Application/Json"
	//Postman DELETE http://localhost:8080/UserService/rest2/users Body row > {"id":"40"} Headers(1) Key: ContentType > Value: Application/Json
	@DELETE
	public boolean delete(User request) {
		return idao.delete(request.getOprCpr());
	}
	
}