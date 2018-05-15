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
		
		return dao.getProductsByBuyers(p);
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
	
	public List<Product> getProductsByTheUser(User u){					//Azon term�kek list�ja, amit user megvett
		return dao.getProductsByTheUser(u);
	}
	
	public boolean buyProduct(User u, Product p){						//Megveszi a term�ket (�r �jrasz�molva), felhaszn�l� egyenleg�t is frissiteni kell, rakt�r �rt�ke n�vel�s
		return dao.buyProduct(u, p);
	}
	
	public boolean addComment(Comment c, Product p, User u){					//Hozz�adja a kommentet
		return dao.addComment(c, p, u);
	}

	public int getQuantity(Product p){
		return dao.getQuantity(p);
	}
	
	public List<Product> getAllProducts(){
		return dao.getAllProduct();
	}
	
}
