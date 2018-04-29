package controller;

import java.util.ArrayList;
import java.util.List;

import model.bean.Comment;
import model.bean.Product;

public class ProductController {

	public List<Product> getProductsByBuyers() {				//ToDo: Implement by DAO
		List<Product> output = new ArrayList<Product>();
		for(int i = 0; i < 5; i++){
			Product px = new Product(i, "farok", 100+i, "faszok", null, 0,0);
			output.add(px);
		}
		return output;
	}

	public List<Comment> getComments(Product product) {
		List<Comment> output = new ArrayList<Comment>();
		for(int i = 0; i < 5; i++){
			Comment px = new Comment("Comment text --- - -- ", i,null,"user" + i);
			output.add(px);
		}
		return output;
	}

}
