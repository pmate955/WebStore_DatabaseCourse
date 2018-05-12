package view.dialog;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.LogInController;
import model.bean.User;

public class RegistDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private LogInController controller;
	
	public RegistDialog(LogInController controller) {
		this.controller = controller;
		this.setTitle("Registration");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.add(createRegistPanel());
		this.pack();
		this.setVisible(true);
	}

	private JPanel createRegistPanel() {
		JPanel panel = new JPanel();
		JTextField username = new JTextField();
		JTextField firstname = new JTextField();
		JTextField lastname = new JTextField();
		JPasswordField password = new JPasswordField();
		JPasswordField password1 = new JPasswordField();
		JTextField email = new JTextField();
		JTextField zipcode = new JTextField();
		JTextField city = new JTextField();
		JTextField street = new JTextField();
		JTextField houseNum = new JTextField();
		JButton ok = new JButton("Registration");
		JButton cancel = new JButton("Cancel");
		
		panel.setLayout(new GridLayout(11, 2));
		panel.add(new JLabel("Username: "));
		panel.add(username);
		panel.add(new JLabel("Firstname: "));
		panel.add(firstname);
		panel.add(new JLabel("Lastname: "));
		panel.add(lastname);
		panel.add(new JLabel("E-mail: "));
		panel.add(email);
		panel.add(new JLabel("Password: "));
		panel.add(password);
		panel.add(new JLabel("Password again: "));
		panel.add(password1);
		panel.add(new JLabel("Zipcode: "));
		panel.add(zipcode);
		panel.add(new JLabel("City: "));
		panel.add(city);
		panel.add(new JLabel("Street: "));
		panel.add(street);
		panel.add(new JLabel("House number: "));
		panel.add(houseNum);
		panel.add(ok);
		panel.add(cancel);
		
		ok.addActionListener(event -> {
			if(username.getText().isEmpty() || firstname.getText().isEmpty() || lastname.getText().isEmpty() || email.getText().isEmpty()
					|| password.getPassword().length == 0 || password1.getPassword().length == 0 || zipcode.getText().isEmpty()
					|| city.getText().isEmpty() || street.getText().isEmpty() || houseNum.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this, "At least one textfield is empty!", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			} else {
				String pwd1 = new String(password.getPassword());
				String pwd2 = new String(password1.getPassword());
				if(!pwd1.equals(pwd2)) {
					JOptionPane.showMessageDialog(this, "The two password not same!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if(controller.isAvailableUser(username.getText(), email.getText())) {
					try {
						User user = new User(0, username.getText(), firstname.getText(), lastname.getText(), email.getText(), Integer.parseInt(zipcode.getText()),
								city.getText(), street.getText(), houseNum.getText(), 0, 0, false);
						
						if(controller.addUser(user, pwd1)) {
							this.dispose();
						} else {
							JOptionPane.showMessageDialog(this, "Some Error Happened!", "Error", JOptionPane.ERROR_MESSAGE);
						}
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(this, "You are a fucking stupid bitch!", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(this, "The username or e-mail is used!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			
			
		});
		
		cancel.addActionListener(event -> {
			this.dispose();
		});
		
		return panel;
	}
}
