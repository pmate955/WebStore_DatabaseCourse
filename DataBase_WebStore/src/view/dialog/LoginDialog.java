package view.dialog;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.LogInController;

public class LoginDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private LogInController controller;

	public LoginDialog(LogInController controller) {
		this.controller = controller;
		
		this.setTitle("Login");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.add(createLoginPanel());
		this.pack();
		this.setVisible(true);
	}

	private JPanel createLoginPanel() {
		JPanel panel = new JPanel();
		JLabel user = new JLabel("Username:");
		JLabel pass = new JLabel("Password:");
		JTextField username = new JTextField();
		JPasswordField password = new JPasswordField();
		JButton login = new JButton("Login");
		
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		panel.add(user);
		panel.add(username);
		panel.add(pass);
		panel.add(password);
		panel.add(login);
		
		return panel;
	}
}
