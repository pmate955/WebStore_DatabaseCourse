package view.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.bean.Product;

public class ProductPanel extends JPanel {
	private Product product;
	
	public ProductPanel(Product product){
		super();
		this.product = product;
		this.setPreferredSize(new Dimension(200,250));
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		JLabel title = new JLabel(product.getName());
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
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
	}
}
