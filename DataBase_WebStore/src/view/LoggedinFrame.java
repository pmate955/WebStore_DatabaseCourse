package view;

import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import view.dialog.CompareDialog;
import view.dialog.ProfileFrame;
import view.panels.ProductPanel;

public class LoggedinFrame extends MainFrame implements Runnable {
	private static final long serialVersionUID = 1L;
	private User user;
	LogInController contr;
	ProductController prod;
	private JPanel mainPanel;
	private JMenu menu;
	private Thread update;
	private boolean isRun = true;

	public LoggedinFrame(LogInController controller, ProductController prod, User user) {
		super(controller, prod);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.user = controller.reloadUser(user);
		this.contr = controller;
		this.prod = prod;
		this.setJMenuBar(createLoggedInMenuBar());
		this.createLoggedInPanel();
		this.pack();
		this.addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent e) {
		       isRun = false;
		    }
		});
		update = new Thread(this);
		update.start();
	}
	
	
	protected JMenuBar createLoggedInMenuBar() {
		JMenuBar menubar = new JMenuBar();
		menu = new JMenu(user.getUserName() + " | " + user.getBalance() + " HUF");
		JMenu category = new JMenu("Category");
		JMenuItem profile = new JMenuItem("Profile");
		profile.addActionListener(e -> {
			new ProfileFrame(user,prod, contr);
		});
		JMenuItem basket = new JMenuItem("Compare");
		basket.addActionListener(e -> {
			new CompareDialog(prod);
		});
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
			mainPanel.add(new ProductPanel(prod, p, user, contr));
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
			int i = 0;
			for(Product p: prod.getProductsByCategory(categories.get(0))){
				products.add(p);
				if(i++>=8) break;
			}
		} 
		
		mainPanel.setLayout(new GridLayout(products.size()/5+1,products.size()%5+1));
		int num = 0;
		for(Product p:products){
			mainPanel.add(new ProductPanel(prod, p, user, contr));
			if(num > 10) break;
			num++;
		}
		mainPanel.setBorder(BorderFactory.createTitledBorder("Products by your orders"));
		this.setContentPane(mainPanel);
	}


	@Override
	public void run() {
		while(this.isVisible()){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			user = contr.reloadUser(user);
			menu.setText(user.getUserName() + " | " + user.getBalance() + " HUF");
		}
		
	}
}
