package view.dialog;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JDialog;

import controller.AdminController;
import controller.LogInController;
import controller.ProductController;
import model.bean.Product;
import view.panels.ProductPanel;

public class CompareDialog extends JDialog{
	
	private ProductController p;
	private AdminController ad;  
	
	private ProductPanel pr1;
	private ProductPanel pr2;
	
	public CompareDialog(ProductController p){
		this.p = p;
		ad = new AdminController();	
		this.setLayout(new GridLayout(2,2));
		List<Product> prods = p.getAllProducts();
		JComboBox first = new JComboBox();
		for(Product pr:prods){
			first.addItem(pr.getName());
		}
		this.add(first);
		JComboBox sec = new JComboBox();
		for(Product pr:prods){
			sec.addItem(pr.getName());
		}
		this.add(sec);
		pr1 = new ProductPanel(p, prods.get(0), null, new LogInController());
		pr2 = new ProductPanel(p, prods.get(0), null, new LogInController());
		this.add(pr1);
		this.add(pr2);
		first.addActionListener(e -> {
			this.remove(pr1);
			this.remove(pr2);
			pr1 = new ProductPanel(p, prods.get(first.getSelectedIndex()), null, new LogInController());
			this.add(pr1);
			this.add(pr2);
			this.revalidate();
			this.repaint();
		});
		sec.addActionListener( e -> {
			this.remove(pr1);
			this.remove(pr2);
			pr2 = new ProductPanel(p, prods.get(sec.getSelectedIndex()), null, new LogInController());
			this.add(pr1);
			this.add(pr2);
			this.revalidate();
			this.repaint();
		});
		this.setTitle("Compare items");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}
	
	
}
