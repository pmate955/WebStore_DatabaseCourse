package controller;

import dao.Database_Dao;
import model.bean.Product;

public class AdminController {
	
	private Database_Dao dao;
	
	public AdminController() {
		this.dao = new Database_Dao();
	}
	
	public boolean addProduct(Product p, int quantity){
		return dao.addProduct(p, quantity);
	}

	public boolean addCategory(String category){
		return dao.addCategory(category);
	}
	
	public boolean deleteProduct(Product p){
		return dao.deleteProduct(p);
	}
	
	public boolean updateQty(Product p, int quantity){
		return true;
	}
}
