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
		
		return dao.getProductsByBuyers(p);
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
		return dao.getProductsByUser(u);
	}
	
	public List<Product> getProductsByTheUser(User u){					//Azon termékek listája, amit user megvett
		return dao.getProductsByTheUser(u);
	}
	
	public boolean buyProduct(User u, Product p){						//Megveszi a terméket (ár újraszámolva), felhasználó egyenlegét is frissiteni kell, raktár értéke növelés
		return dao.buyProduct(u, p);
	}
	
	public boolean addComment(Comment c, Product p, User u){					//Hozzáadja a kommentet
		return dao.addComment(c, p, u);
	}

	public int getQuantity(Product p){
		return dao.getQuantity(p);
	}
	
	public List<Product> getAllProducts(){
		return dao.getAllProduct();
	}
	
}
