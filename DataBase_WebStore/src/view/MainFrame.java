package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;

import controller.LogInController;
import controller.ProductController;
import model.bean.Product;
import model.bean.User;
import view.dialog.LoginDialog;
import view.dialog.RegistDialog;
import view.panels.ProductPanel;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private LogInController controller;
	private ProductController prod;
	private JPanel mainPanel;
	private LoginDialog l;

	public MainFrame(LogInController controller, ProductController prod) {
		this.controller = controller;
		this.prod = prod;
		this.setTitle("Webshop");
		this.setSize(new Dimension(1200, 600));
		this.setJMenuBar(createMenuBar());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.createMainPanel();
		
		this.setVisible(true);
	}

	protected JMenuBar createMenuBar() {
		JMenuBar menubar = new JMenuBar();
		JButton login = new JButton("Log In");
		JButton regist = new JButton("Registration");
		
		login.addActionListener(event -> {
			l = new LoginDialog(controller);
			User u = l.user1;
			if(u != null) {
				new LoggedinFrame(controller, prod, u);
				this.dispose();
			}
			
			l.dispose();
		});
		
		regist.addActionListener(event -> {
			new RegistDialog(controller);
		});
	
	
		menubar.add(login);
		menubar.add(regist);
		
		return menubar;
	}
	
	private void createMainPanel(){
		mainPanel = new JPanel();
		JScrollPane scroll = new JScrollPane(mainPanel);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		List<String> categories = prod.getCategories();
		for(int i = 0; i < 5; i++){
			String category = categories.get(i);
			if(category != null) mainPanel.add(this.createItemPanel(category));
		}
		
		this.add(scroll);
	}
	
	private JPanel createItemPanel(String category){
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		JPanel catPanel = new JPanel();
		catPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel categoryLabel = new JLabel(category+ ":");
		categoryLabel.setFont(new Font("TimesNewRoman", Font.BOLD, 16));
		categoryLabel.setAlignmentX(CENTER_ALIGNMENT);
		categoryLabel.setAlignmentY(CENTER_ALIGNMENT);
		catPanel.add(categoryLabel);
		panel.add(catPanel);
		List<Product> input = prod.getProductsByCategory(category);
		for(int i = 0; i < 5; i++){
			Product p = input.get(i);
			if(p != null){
				ProductPanel pr = new ProductPanel(prod, p, null);
				panel.add(pr);
			}
		}
		panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.WHITE, Color.darkGray));
		return panel;
	}
}
