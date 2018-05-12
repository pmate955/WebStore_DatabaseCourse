package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.AdminController;
import controller.ProductController;
import model.bean.User;

public class AdminFrame extends JFrame {
	private User u;
	private AdminController contr;
	private ProductController prod;
	
	public AdminFrame(User u, ProductController prod){
		this.u = u;
		this.prod = prod;
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		contr = new AdminController();
		this.setTitle("ADMIN");		
		this.setLayout(new BorderLayout());
		this.add(createAddPanel(), BorderLayout.NORTH);
		this.pack();
		this.setVisible(true);
	}

	public JPanel createAddPanel() {
		JPanel out = new JPanel();
		out.setLayout(new GridLayout(2,4));
		out.add(new JLabel("Name: "));
		JTextField nameField = new JTextField();
		out.add(nameField);
		out.add(new JLabel("Price: "));
		JTextField priceField = new JTextField();
		out.add(priceField);
		out.add(new JLabel("Category: "));
		JComboBox<String> categ = new JComboBox<String>();
		List<String> allcategory = prod.getCategories();
		for(String s : allcategory) categ.addItem(s);
		out.add(categ);
		JButton addBtn = new JButton("Add product");
		out.add(addBtn);
		
		out.setBorder(BorderFactory.createTitledBorder("Add product"));
		return out;
	}
}
