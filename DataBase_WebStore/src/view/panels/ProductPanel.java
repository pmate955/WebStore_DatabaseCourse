package view.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import controller.LogInController;
import controller.ProductController;
import model.bean.Product;
import model.bean.User;
import view.dialog.ProductDialog;

public class ProductPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private User user;
	public String prodName;
	
	public ProductPanel(ProductController controller, Product product, ProductDialog dialog, User user,LogInController con){
		super();
		this.setPreferredSize(new Dimension(200,250));
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		JLabel title = new JLabel(product.getName());
		prodName = product.getName();
		title.setFont(new Font("TimesRoman", Font.BOLD, 18));
		title.setAlignmentX(CENTER_ALIGNMENT);
		this.add( title);
		JLabel image = new JLabel();
		image.setIcon(product.getImageBySize(150));
		image.setAlignmentX(CENTER_ALIGNMENT);
		this.add(image);
		JLabel price = new JLabel(String.valueOf(product.getPrice()) + " huf/piece");
		price.setFont(new Font("TimesRoman", Font.ITALIC, 18));
		price.setAlignmentX(CENTER_ALIGNMENT);
		this.add(price);
		JButton viewProduct = new JButton("Open product");
		viewProduct.setAlignmentX(CENTER_ALIGNMENT);
		this.add(viewProduct);
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.WHITE, Color.DARK_GRAY));
		viewProduct.addActionListener(event -> {
			dialog.dispose();
			new ProductDialog(controller, product, user, con);
		});
	}
	
	public ProductPanel(ProductController controller, Product product, User user, LogInController con){
		super();
		this.setPreferredSize(new Dimension(200,250));
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		JLabel title = new JLabel(product.getName());
		prodName = product.getName();
		title.setFont(new Font("TimesRoman", Font.BOLD, 18));
		title.setAlignmentX(CENTER_ALIGNMENT);
		this.add( title);
		JLabel image = new JLabel();
		image.setIcon(product.getImageBySize(150));
		image.setAlignmentX(CENTER_ALIGNMENT);
		this.add(image);
		JLabel price = new JLabel(String.valueOf(product.getPrice()) + " huf/piece");
		price.setFont(new Font("TimesRoman", Font.ITALIC, 18));
		price.setAlignmentX(CENTER_ALIGNMENT);
		this.add(price);
		JButton viewProduct = new JButton("Open product");
		viewProduct.setAlignmentX(CENTER_ALIGNMENT);
		this.add(viewProduct);
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.WHITE, Color.DARK_GRAY));
		viewProduct.addActionListener(event -> {
			new ProductDialog(controller, product, user, con);
		});
	}
}
