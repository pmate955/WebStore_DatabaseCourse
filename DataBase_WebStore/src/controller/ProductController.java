package controller;

import java.util.ArrayList;
import java.util.List;

import dao.Database_Dao;
import model.bean.Comment;
import model.bean.Product;
import model.bean.User;

public class ProductController {
	
	private Database_Dao dao;
	
	public ProductController() {
		this.dao = new Database_Dao();
	}

	public List<Product> getProductsByBuyers(Product p) {				//Azon termékek listája, amit az adott terméket megvásárolt felhasználók vásároltak
		List<Product> output = new ArrayList<Product>();
		for(int i = 0; i < 5; i++){
			Product px = new Product(i, "farok", 100+i, "faszok", null, 0,0);
			output.add(px);
		}
		return output;
	}

	public List<Comment> getComments(Product product) {					//Lekéri az adott termékhez tartozó kommenteket
		
		return dao.getComments(product);
	}

	public List<String> getCategories(){								//Lekéri a kategóriákat

		return dao.getCategories();
	}
	
	public List<Product> getProductsByCategory(String category){		//Lekéri az adott kategóriába tartozó termékeket
		
		return dao.getProductsByCategory(category);
	}
	
	public List<Product> getProductsByUser(User u){						//Lekéri egy adott felhasználó által vásárolt termékeket
		List<Product> out = new ArrayList<Product>();
		for(int i = 0; i < 10; i++){
			Product p = new Product(i, "farok", 60+i, "faszok", null, 0,0);
			out.add(p);
		}
		return out;
	}
	
	public boolean buyProduct(User u, Product p){
		return true;
	}

}
