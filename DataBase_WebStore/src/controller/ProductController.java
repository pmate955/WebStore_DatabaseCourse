package controller;

import java.util.ArrayList;
import java.util.List;

import model.bean.Comment;
import model.bean.Product;
import model.bean.User;

public class ProductController {

	public List<Product> getProductsByBuyers(Product p) {				//Azon term�kek list�ja, amit az adott term�ket megv�s�rolt felhaszn�l�k v�s�roltak
		List<Product> output = new ArrayList<Product>();
		for(int i = 0; i < 5; i++){
			Product px = new Product(i, "farok", 100+i, "faszok", null, 0,0);
			output.add(px);
		}
		return output;
	}

	public List<Comment> getComments(Product product) {					//Lek�ri az adott term�khez tartoz� kommenteket
		List<Comment> output = new ArrayList<Comment>();
		for(int i = 0; i < 5; i++){
			Comment px = new Comment("Comment text --- - -- ", i,null,"user" + i);
			output.add(px);
		}
		return output;
	}

	public List<String> getCategories(){								//Lek�ri a kateg�ri�kat
		List<String> out = new ArrayList<String>();
		for(int i = 0; i < 8; i++){
			out.add("Category " + i);
		}
		return out;
	}
	
	public List<Product> getProductsByCategory(String category){		//Lek�ri az adott kateg�ri�ba tartoz� term�keket
		List<Product> out = new ArrayList<Product>();
		for(int i = 0; i < 10; i++){
			Product p = new Product(i, "farok", 50+i, "faszok", null, 0,0);
			out.add(p);
		}
		return out;
	}
	
	public List<Product> getProductsByUser(User u){						//Lek�ri egy adott felhaszn�l� �ltal v�s�rolt term�keket
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
