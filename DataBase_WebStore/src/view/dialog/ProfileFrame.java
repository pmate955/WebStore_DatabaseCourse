package view.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.ProductController;
import model.bean.User;

public class ProfileFrame extends JDialog {
	private User user;
	private ProductController prod;
	
	public ProfileFrame(User user, ProductController prod){
		this.user = user;
		this.setTitle(user.getUserName());
		this.setSize(new Dimension(500,500));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.add(createDataPanel(), BorderLayout.NORTH);
		//this.add(createProdPanel(), BorderLayout.CENTER);
		this.setVisible(true);
	}

	private JPanel createDataPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		JLabel name = new JLabel("Name: " + user.getFirstName() + " " + user.getLastName());
		name.setFont(new Font("TimesNewRoman", Font.BOLD, 16));
		JLabel address = new JLabel(user.getAddress().toString());
		address.setFont(new Font("TimesNewRoman", Font.BOLD, 16));
		panel.add(name);
		panel.add(address);
		return panel;
	}
	
}
