package src.main.java.login;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import entity.User;






public class GuardarRule {
	
	public void guardar(String nombre, String apellido, String fecha, String email){
	
	UserDao dao = new UserDao();

	// Add new user
	User user = new User();
	user.setFirstName(nombre);
	user.setLastName(apellido);
	try {
		Date dob = new SimpleDateFormat("dd/MM/yyyy").parse(fecha);
		user.setDob(dob);
	} catch (ParseException e) {
		e.printStackTrace();
	}
	user.setEmail(email);
	dao.addUser(user);
	
	// Update user
	//user.setEmail("daniel@updated.com");
	//dao.updateUser(user);

	// Delete user
	// dao.deleteUser(1);

	// Get all users
	for (User iter : dao.getAllUsers()) {
		System.out.println(iter);
	}

	// Get user by id
	System.out.println(dao.getUserById(2));
}



}
