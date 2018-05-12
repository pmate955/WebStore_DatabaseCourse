package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.AdminController;
import controller.LogInController;
import controller.ProductController;
import model.bean.Product;
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
		JMenuBar bar = new JMenuBar();
		this.setJMenuBar(bar);
		JButton logout = new JButton("Log out");
		bar.add(logout);
		logout.addActionListener(e -> {
			this.setVisible(false);
			new MainFrame(new LogInController(), prod);
			this.dispose();
		});
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		mainPanel.add(createAddPanel());
		mainPanel.add(createCategoryPanel());
		mainPanel.add(createDeletePanel());
		this.add(mainPanel);
		this.pack();
		this.setVisible(true);
	}

	private JPanel createDeletePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.add(new JLabel("Product name: "));
		JTextField name = new JTextField(10);
		panel.add(name);
		JButton addBtn = new JButton("Delete");
		panel.add(addBtn);
		addBtn.addActionListener(e -> {
			if(name.getText().isEmpty()){
				JOptionPane.showMessageDialog(this, "Empty field");
			} else {
				if(contr.deleteProduct(name.getText())){
					JOptionPane.showMessageDialog(this, "Delete succesful", "Done", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(this, "Database error :(");
				}
			}
		});
		panel.setBorder(BorderFactory.createTitledBorder("Delete product"));
		return panel;
	}

	private JPanel createCategoryPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.add(new JLabel("New category name: "));
		JTextField name = new JTextField(10);
		panel.add(name);
		JButton addBtn = new JButton("Add");
		panel.add(addBtn);
		addBtn.addActionListener(e -> {
			if(name.getText().isEmpty()){
				JOptionPane.showMessageDialog(this, "Empty field");
			} else {
				if(contr.addCategory(name.getText())){
					JOptionPane.showMessageDialog(this, "Adding succesful", "Done", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(this, "Database error :(");
				}
			}
		});
		panel.setBorder(BorderFactory.createTitledBorder("Add new category"));
		return panel;
	}

	public JPanel createAddPanel() {
		JPanel out = new JPanel();
		out.setLayout(new GridLayout(2,4));
		out.add(new JLabel("Name: "));
		JTextField nameField = new JTextField();
		out.add(nameField);
		//out.add(new JLabel("Price: ").setSize(50, 10));
		JTextField priceField = new JTextField();
		out.add(priceField);
		out.add(new JLabel("Category: "));
		JComboBox<String> categ = new JComboBox<String>();
		List<String> allcategory = prod.getCategories();
		for(String s : allcategory) categ.addItem(s);
		out.add(categ);
		JButton addBtn = new JButton("Add product");
		addBtn.addActionListener(e -> {
			if(nameField.getText().isEmpty()){
				JOptionPane.showMessageDialog(this, "Empty field");
			} else {
				try {
					int price = Integer.parseInt(priceField.getText());
					Date d = new Date(Calendar.getInstance().getTimeInMillis());
					Product p = new Product(0, nameField.getText(), price, (String)categ.getSelectedItem(), d);
					if(contr.addProduct(p)){
						JOptionPane.showMessageDialog(this, "Adding succesful", "Done", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(this, "Database error :(");
					}
				} catch (NumberFormatException ex){
					JOptionPane.showMessageDialog(this, "Not a number!");
				}
			}
		});
		out.add(new JLabel());
		out.add(addBtn);
		
		out.setBorder(BorderFactory.createTitledBorder("Add product"));
		return out;
	}
}
