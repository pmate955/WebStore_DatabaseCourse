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
			Product px = new Product(i, "farok", 100+i, "faszok", null);
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
		return dao.getProductsByUser(u);
	}
	
	public List<Product> getProductsByTheUser(User u){					//Azon termékek listája, amit user megvett
		return dao.getProductsByTheUser(u);
	}
	
	public boolean buyProduct(User u, Product p){						//Megveszi a terméket (ár újraszámolva), felhasználó egyenlegét is frissiteni kell, raktár értéke növelés
		return true;
	}
	
	public boolean addComment(Comment c, Product p){					//Hozzáadja a kommentet
		return true;
	}
	
	

}
