package view.dialog;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import controller.LogInController;
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
	private LogInController log;
	
	
	public ProductDialog(ProductController controller, Product product, User user, LogInController log) {
		this.controller = controller;
		this.product = product;
		this.user = log.reloadUser(user);
		this.log = log;
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
		JButton buy = new JButton("Buy");
		buy.addActionListener(e -> {
			if(user == null) {
				JOptionPane.showMessageDialog(this, "You have to log in to buy!");
				return;
			}
			if(user.getBalance()>= product.getPrice() && controller.buyProduct(user, product)){
				JOptionPane.showMessageDialog(this, "Success");
				user = log.reloadUser(user);
				this.setVisible(false);
				new ProductDialog(controller,product, user, log);
				this.dispose();
				
			} else {
				JOptionPane.showMessageDialog(this, "You are poor :(");
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
		panel.add(new JLabel("Quantity: " + controller.getQuantity(product)));
		
		JButton commentBtn = new JButton("Write comment");
		panel.add(this.createProductsPanel());
		commentBtn.setAlignmentX(CENTER_ALIGNMENT);
		if(user != null){
			panel.add(commentBtn);
			commentBtn.addActionListener(e -> {
				new CommentDialog(user,product,controller);
				this.setVisible(false);
				new ProductDialog(controller,product, user, log);
				this.dispose();
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
		int count = 0;
		for(Product pr : controller.getProductsByBuyers(product)){
			count++;
			if(count > 5) break;
			ProductPanel ppanel = new ProductPanel(controller, pr, this, user, log);
			p.add(ppanel);
		}
		p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.blue, 2), "Termékek, amit mások is megvettek", TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION, new Font("TimesRoman", Font.BOLD, 12)));
		return p;
	}

	private JPanel createCommentPanel(Comment c){
		JPanel out = new JPanel();
		out.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel user = new JLabel(c.getUsername());
		user.setFont(new Font("TimesRoman", Font.ITALIC, 18));
		out.add(user);
		JLabel value = new JLabel(c.getValue() + "/5");
		value.setFont(new Font("TimesRoman", Font.BOLD, 18));
		out.add(value);
		JLabel comment = new JLabel(c.getComment());
		comment.setFont(new Font("TimesRoman", Font.BOLD, 18));
		out.add(comment);
		out.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		return out;
	}

	
}
