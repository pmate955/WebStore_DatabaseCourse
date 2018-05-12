package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import controller.LogInController;
import controller.ProductController;
import model.bean.Product;
import model.bean.User;
import view.dialog.ProfileFrame;
import view.panels.ProductPanel;

public class LoggedinFrame extends MainFrame {
	private static final long serialVersionUID = 1L;
	private User user;
	LogInController contr;
	ProductController prod;
	private JPanel mainPanel;

	public LoggedinFrame(LogInController controller, ProductController prod, User user) {
		super(controller, prod);
		this.user = user;
		this.contr = controller;
		this.prod = prod;
		this.setJMenuBar(createLoggedInMenuBar());
		this.createLoggedInPanel();
		this.pack();
	}
	
	
	protected JMenuBar createLoggedInMenuBar() {
		JMenuBar menubar = new JMenuBar();
		JMenu menu = new JMenu(user.getUserName() + " | " + user.getBalance() + " HUF");
		JMenu category = new JMenu("Category");
		JMenuItem profile = new JMenuItem("Profile");
		profile.addActionListener(e -> {
			new ProfileFrame(user,prod);
		});
		JMenuItem basket = new JMenuItem("Basket");
		JMenuItem logout = new JMenuItem("Log Out");
		logout.addActionListener(evt -> {
			new MainFrame(contr,prod);
			this.dispose();
		});
		
		for(String cat:prod.getCategories()){
			JMenuItem item = new JMenuItem(cat);
			item.addActionListener(evt -> {
				this.setCategory(cat);
			});
			category.add(item);
		}
		
		menu.add(profile);
		menu.add(basket);
		menu.add(logout);
		
		menubar.add(menu);
		menubar.add(category);
		
		return menubar;
	}
	
	private void setCategory(String category){
		mainPanel = new JPanel();
		List<Product> products = prod.getProductsByCategory(category);
		mainPanel.setLayout(new GridLayout(products.size()/5+1,products.size()%5+1));
		for(Product p : products){
			mainPanel.add(new ProductPanel(prod, p, user));
		}
		mainPanel.setBorder(BorderFactory.createTitledBorder("Products by " + category));
		this.setContentPane(mainPanel);
		this.revalidate();
		this.repaint();
		this.pack();
	}

	private void createLoggedInPanel(){
		mainPanel = new JPanel();
		List<Product> products = prod.getProductsByUser(user);
		if(products.size() == 0){
			List<String> categories = prod.getCategories();
			for(String s : categories){
				products.addAll(prod.getProductsByCategory(s));		
				if(products.size()>=12) break;
			}
		} 
		
		mainPanel.setLayout(new GridLayout(products.size()/5+1,products.size()%5+1));
		for(Product p:products){
			mainPanel.add(new ProductPanel(prod, p, user));
		}
		mainPanel.setBorder(BorderFactory.createTitledBorder("Products by your cock"));
		this.setContentPane(mainPanel);
	}
}
