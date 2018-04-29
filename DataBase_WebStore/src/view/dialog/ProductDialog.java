package view.dialog;

import java.awt.LayoutManager;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import controller.ProductController;
import model.bean.Product;

public class ProductDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private ProductController controller;
	private Product product;

	public ProductDialog(ProductController controller, Product product) {
		this.controller = controller;
		this.product = product;
		
		this.setTitle(product.getName());
		this.setSize(400, 500);
		this.setJMenuBar(createMenuBar());
		this.add(createMainPanel());
		this.setVisible(true);
	}

	private JMenuBar createMenuBar() {
		JMenuBar menubar = new JMenuBar();
		JMenu price = new JMenu(String.valueOf(product.getPrice()) + " huf/piece");
		JMenu buy = new JMenu("Buy");
		
		price.setEnabled(false);
		
		menubar.add(price);
		menubar.add(buy);
		
		return menubar;
	}
	
	private JScrollPane createMainPanel() {
		JPanel panel = new JPanel();
		JScrollPane scrollPane = new JScrollPane(panel);
		
		//set horizontal layout for the panel
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		
		
		return scrollPane;
	}
}
