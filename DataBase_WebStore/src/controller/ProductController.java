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

	public List<Product> getProductsByBuyers(Product p) {				//Azon term�kek list�ja, amit az adott term�ket megv�s�rolt felhaszn�l�k v�s�roltak
		List<Product> output = new ArrayList<Product>();
		for(int i = 0; i < 5; i++){
			Product px = new Product(i, "farok", 100+i, "faszok", null, 0,0);
			output.add(px);
		}
		return output;
	}

	public List<Comment> getComments(Product product) {					//Lek�ri az adott term�khez tartoz� kommenteket
		
		return dao.getComments(product);
	}

	public List<String> getCategories(){								//Lek�ri a kateg�ri�kat

		return dao.getCategories();
	}
	
	public List<Product> getProductsByCategory(String category){		//Lek�ri az adott kateg�ri�ba tartoz� term�keket
		
		return dao.getProductsByCategory(category);
	}
	
	public List<Product> getProductsByUser(User u){						//Lek�ri egy adott felhaszn�l� �ltal v�s�rolt term�keket
		return dao.getProductsByUser(u);
	}
	
	public boolean buyProduct(User u, Product p){
		return true;
	}

}
