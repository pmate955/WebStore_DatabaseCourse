package controller;

import java.util.List;

import dao.Database_Dao;
import model.bean.Order;
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
		return dao.updateQty(p, quantity);
	}
	
	public List<Order> getOrders(){
		return dao.getOrders();
	}
	
	public boolean updateOrder(Order o) {
		return dao.updateOrder(o);
	}
	
	public List<String> getMonthlyStat(){
		return dao.getMonthlyStat();
	}
}
