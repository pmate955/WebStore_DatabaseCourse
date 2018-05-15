package view.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.LogInController;
import controller.ProductController;
import model.bean.Product;
import model.bean.User;
import view.panels.ProductPanel;

public class ProfileFrame extends JDialog {
	private User user;
	private ProductController prod;
	private LogInController con;
	
	public ProfileFrame(User user, ProductController prod, LogInController con){
		this.user = con.reloadUser(user);
		this.prod = prod;
		this.con = con;		
		this.setTitle(user.getUserName());
		this.setSize(new Dimension(500,500));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.add(createDataPanel(), BorderLayout.NORTH);
		this.add(createProdPanel(), BorderLayout.CENTER);
		this.setVisible(true);
		this.pack();
	}

	private JPanel createDataPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		JLabel name = new JLabel("Name: " + user.getFirstName() + " " + user.getLastName());
		name.setFont(new Font("TimesNewRoman", Font.BOLD, 16));
		JLabel address = new JLabel(user.getAddress().toString());
		address.setFont(new Font("TimesNewRoman", Font.BOLD, 16));
		JLabel balance = new JLabel("Balance: " + user.getBalance() + " HUF");
		balance.setFont(new Font("TimesNewRoman", Font.BOLD, 16));
		JButton addMoney = new JButton("Get some money");
		addMoney.addActionListener(e -> {
			String money = JOptionPane.showInputDialog(this, "How much money you need?");
			try{
				if(!con.addMoney(user, Integer.parseInt(money))){
					JOptionPane.showMessageDialog(this, "Something bad happened");
				} else {
					this.setVisible(false);
					new ProfileFrame(user,prod,con);
					this.dispose();
				};
			} catch (NumberFormatException ex){
				JOptionPane.showMessageDialog(this, "Number pls");
			}
			
		});
		
		panel.add(name);
		panel.add(address);
		panel.add(balance);
		panel.add(addMoney);
		return panel;
	}
	
	private JPanel createProdPanel(){
		JPanel	panel = new JPanel();
		List<Product> products = prod.getProductsByTheUser(user);
		panel.setLayout(new GridLayout(products.size()/5+1,products.size()%5+1));
		for(Product p:products){
			panel.add(new ProductPanel(prod, p, user, con));
		}
		panel.setBorder(BorderFactory.createTitledBorder("Buyed products"));
		return panel;
	}
	
}
