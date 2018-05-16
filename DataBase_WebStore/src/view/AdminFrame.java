package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;

import controller.AdminController;
import controller.LogInController;
import controller.ProductController;
import model.bean.Order;
import model.bean.Product;
import model.bean.User;

public class AdminFrame extends JFrame {
	private User u;
	private AdminController contr;
	private ProductController prod;
	private List<Product> prods;
	
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
		mainPanel.add(createOrderPanel());
		this.add(mainPanel);
		this.pack();
		this.setVisible(true);
	}

	private Component createOrderPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		JButton shipping = new JButton("Shipping order");
		panel.add(shipping, BorderLayout.CENTER);
		String[] columnNames = {"Date","Username",
                "Product name",
                "Status",
                "Price"};
		List<Order> input = contr.getOrders();
		String[][] datas = new String[input.size()][5];
		for(int i = 0; i < input.size(); i++){
			Order o = input.get(i);
			datas[i][0] = o.getOrderDate().toString();
			datas[i][1] = o.getUser().getUserName();
			datas[i][2] = o.getProduct().getName();
			datas[i][3] = o.getStatus()+ " ";
			datas[i][4] = o.getProduct().getPrice() + " ";
		}
		JTable table = new JTable(datas,columnNames);
		table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
		{
		    @Override
		    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
		    {
		        final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		        ;
		        if(column == 3){
		        	String in = (String)value;
		        	c.setBackground(in.equals("fizetve ") ? Color.RED : Color.WHITE);
		        }
		        return c;
		    }
		});
		panel.add(new JScrollPane(table), BorderLayout.CENTER);
		panel.setBorder(BorderFactory.createTitledBorder("Orders"));
		return panel;
	}

	private JPanel createDeletePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.add(new JLabel("Product name: "));
		prods = prod.getAllProducts();
		JComboBox name = new JComboBox();
		for(Product p:prods){
			name.addItem(p.getName());
		}
		
		panel.add(name);
		JButton addBtn = new JButton("Delete");

		panel.add(new JLabel("Quantity: "));
		JTextField field = new JTextField();
		field.setText(""+prod.getQuantity(prods.get(name.getSelectedIndex())));
		name.addActionListener(e ->{
			if(name.getSelectedIndex()<0) name.setSelectedIndex(0);
			field.setText(""+prod.getQuantity(prods.get(name.getSelectedIndex())));
		});
		panel.add(field);
		JButton updateBtn = new JButton("Update qty");
		updateBtn.addActionListener(e->{
			try{
				int num = Integer.parseInt(field.getText());
				if(!contr.updateQty(prods.get(name.getSelectedIndex()), num)){
					JOptionPane.showMessageDialog(this, "Database error :(");
				} else {
					JOptionPane.showMessageDialog(this, "Update succesful", "Done", JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (NumberFormatException ex){
				JOptionPane.showMessageDialog(this, "Not a number");
			}
		});
		panel.add(updateBtn);
		panel.add(addBtn);
		addBtn.addActionListener(e -> {
			 
				if(contr.deleteProduct(prods.get(name.getSelectedIndex()))){
					JOptionPane.showMessageDialog(this, "Delete succesful", "Done", JOptionPane.INFORMATION_MESSAGE);
					name.removeItemAt(name.getSelectedIndex());
					this.repaint();
				} else {
					JOptionPane.showMessageDialog(this, "Database error :(");
				}
			
		});
		panel.setBorder(BorderFactory.createTitledBorder("Delete or update quantity of product"));
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
		out.setLayout(new GridLayout(5,2));
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

		JTextField qtity = new JTextField();
		out.add(categ);
		JButton addBtn = new JButton("Add product");
		addBtn.addActionListener(e -> {
			if(nameField.getText().isEmpty() || qtity.getText().isEmpty()){
				JOptionPane.showMessageDialog(this, "Empty field");
			} else {
				try {
					int price = Integer.parseInt(priceField.getText());
					Date d = new Date(Calendar.getInstance().getTimeInMillis());
					Product p = new Product(0, nameField.getText(), price, (String)categ.getSelectedItem(), d);
					int quan = Integer.parseInt(qtity.getText());
					if(contr.addProduct(p, quan)){
						JOptionPane.showMessageDialog(this, "Adding succesful", "Done", JOptionPane.INFORMATION_MESSAGE);
						
					} else {
						JOptionPane.showMessageDialog(this, "Database error :(");
					}
				} catch (NumberFormatException ex){
					JOptionPane.showMessageDialog(this, "Not a number!");
				}
			}
		});
		
		out.add(new JLabel("Quantity:"));
		
		out.add(qtity);
		out.add(new JLabel(""));
		out.add(addBtn);
		out.setBorder(BorderFactory.createTitledBorder("Add product"));
		return out;
	}
}
