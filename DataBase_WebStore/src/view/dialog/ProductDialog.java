package view.dialog;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import controller.ProductController;
import model.bean.Comment;
import model.bean.Product;
import model.bean.User;
import view.panels.ProductPanel;

public class ProductDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private ProductController controller;
	private Product product;
	private User user;

	public ProductDialog(ProductController controller, Product product, User user) {
		this.controller = controller;
		this.product = product;
		this.user = user;
		
		this.setTitle(product.getName());
		this.setSize(400, 500);
		this.setJMenuBar(createMenuBar());
		this.add(createMainPanel());
		this.pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}

	private JMenuBar createMenuBar() {
		JMenuBar menubar = new JMenuBar();
		JMenu price = new JMenu(String.valueOf(product.getPrice()) + " huf/piece");
		JMenu buy = new JMenu("Buy");
		buy.addActionListener(e -> {
			if(controller.buyProduct(user, product)){
				//JOptionPane.showMessageDialog(parentComponent, message);
			};
		});
		
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
		JLabel lbl = new JLabel();
		lbl.setIcon(product.getImageBySize(250));
		lbl.setAlignmentX(CENTER_ALIGNMENT);
		panel.add(lbl);
		JButton commentBtn = new JButton("Kommentelés");
		panel.add(this.createProductsPanel());
		commentBtn.setAlignmentX(CENTER_ALIGNMENT);
		if(user != null){
			panel.add(commentBtn);
			commentBtn.addActionListener(e -> {
				new CommentDialog(user,controller);
			});
		}
		for(Comment c: controller.getComments(product)){
			panel.add(this.createCommentPanel(c));
		}
		return scrollPane;
	}
	
	private JPanel createProductsPanel(){
		JPanel p = new JPanel();
		p.setLayout(new FlowLayout(FlowLayout.CENTER));
		for(Product pr : controller.getProductsByBuyers(product)){
			ProductPanel ppanel = new ProductPanel(controller, pr, this, user);
			p.add(ppanel);
		}
		p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.blue, 2), "Termékek, amit mások is megvettek", TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("TimesRoman", Font.BOLD, 12)));
		return p;
	}

	private JPanel createCommentPanel(Comment c){
		JPanel out = new JPanel();
		out.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel user = new JLabel(c.getUsername());
		user.setFont(new Font("TimesRoman", Font.BOLD, 12));
		out.add(user);
		JLabel value = new JLabel(c.getValue() + "/5");
		value.setFont(new Font("TimesRoman", Font.BOLD, 12));
		out.add(value);
		JLabel comment = new JLabel(c.getComment());
		comment.setFont(new Font("TimesRoman", Font.BOLD, 12));
		out.add(comment);
		out.setBorder(BorderFactory.createLineBorder(Color.CYAN, 1));
		return out;
	}
}
