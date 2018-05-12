package view.dialog;

import java.awt.Dimension;

import javax.swing.JDialog;

import controller.ProductController;
import model.bean.User;

public class ProfileFrame extends JDialog {
	private User user;
	private ProductController prod;
	
	public ProfileFrame(User user, ProductController prod){
		this.setTitle(user.getUserName());
		this.setSize(new Dimension(500,500));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
}
