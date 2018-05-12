package controller;

import javax.swing.JLabel;
import dao.*;

import model.bean.User;

public class LogInController {
	
	private Database_Dao dao;
	
	public LogInController() {
		this.dao = new Database_Dao();
	}

	public boolean isAvailableUser(String username, String email) {
		
		return dao.isAvailableUser(username, email);
	}

	public boolean addUser(User user, String pwd1) {
		
		return dao.addUser(user, pwd1);
	}

	public User getUser(String username, String password) {
		
		return dao.getUser(username, password);
	}
	
	public User reloadUser(User u){
		return dao.reloadUser(u);
	}

}
