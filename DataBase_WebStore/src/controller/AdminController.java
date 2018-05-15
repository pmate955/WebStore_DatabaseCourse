package controller;

import dao.Database_Dao;
import model.bean.Product;

public class AdminController {
	
	private Database_Dao dao;
	
	public AdminController() {
		this.dao = new Database_Dao();
	}
	
	public boolean addProduct(Product p, int quantity){
		return true;
	}

	public boolean addCategory(String category){
		return dao.addCategory(category);
	}
	
	public boolean deleteProduct(String productName){
		return dao.deleteProduct(productName);
	}
}
