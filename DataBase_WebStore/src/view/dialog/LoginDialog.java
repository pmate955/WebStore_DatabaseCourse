package view.dialog;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.LogInController;
import model.bean.User;

public class LoginDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private LogInController controller;
	public User user1;

	public LoginDialog(LogInController controller) {
		this.controller = controller;
		
		this.setTitle("Login");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.add(createLoginPanel());
		this.pack();
		this.setModal(true);
		this.setVisible(true);
	}

	private JPanel createLoginPanel() {
		JPanel panel = new JPanel();
		JLabel user = new JLabel("Username:");
		JLabel pass = new JLabel("Password:");
		JTextField username = new JTextField();
		JPasswordField password = new JPasswordField();
		JButton login = new JButton("Login");
		
		
		login.addActionListener(event -> {
			user1 = controller.getUser(username.getText(), new String(password.getPassword()));
			
			if(user1 == null) {
				JOptionPane.showMessageDialog(this, "Nope!", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				setVisible(false);
			}
		});
		
		password.addKeyListener( new KeyListener(){

			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER) login.doClick();
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		panel.add(user);
		panel.add(username);
		panel.add(pass);
		panel.add(password);
		panel.add(login);
		
		return panel;
	}
}
