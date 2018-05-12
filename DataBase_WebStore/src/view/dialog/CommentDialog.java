package view.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import controller.ProductController;
import model.bean.User;

public class CommentDialog extends JDialog {
	
	private JTextField text;
	private JSpinner spinner;
	private ProductController prod;
	
	public CommentDialog(User u, ProductController prod){
		this.setTitle("Add comment");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.prod = prod;
		this.add(createMainPanel(), BorderLayout.NORTH);
		this.add(createButtonPanel(), BorderLayout.CENTER);
		this.pack();
		this.setVisible(true);
	}

	private JPanel createButtonPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		JButton okbtn = new JButton("OK");
		okbtn.addActionListener(e -> {
			if(text.getText().length() == 0){
				JOptionPane.showMessageDialog(this, "Empty field");
			} else {
				//prod
			}
		});
		JButton cnclBtn = new JButton("Cancel");
		cnclBtn.addActionListener(e -> {
			this.dispose();
		});
		panel.add(okbtn);
		panel.add(cnclBtn);
		return panel;
	}

	private JPanel createMainPanel() {
		JPanel out = new JPanel();
		out.setLayout(new GridLayout(2,2));
		out.add(new JLabel("Értékelés: "));
		spinner = new JSpinner(new SpinnerNumberModel(5,1,5,1));
		out.add(spinner);
		out.add(new JLabel("Komment szövege: "));
		text = new JTextField();
		out.add(text);
		return out;
	}

}
