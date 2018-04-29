package view;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import controller.LogInController;
import view.dialog.LoginDialog;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private LogInController controller;

	public MainFrame(LogInController controller) {
		this.controller = controller;
		
		this.setTitle("Webshop");
		this.setSize(new Dimension(800, 600));
		this.setJMenuBar(createMenuBar());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	private JMenuBar createMenuBar() {
		JMenuBar menubar = new JMenuBar();
		JButton login = new JButton("Log In");
		JMenu regist = new JMenu("Registration");
		
		login.addActionListener(event -> {
			new LoginDialog(controller);
		});
		
		menubar.add(login);
		menubar.add(regist);
		
		return menubar;
	}
}
