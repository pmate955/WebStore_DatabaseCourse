package view.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.sql.Date;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import controller.ProductController;
import model.bean.Comment;
import model.bean.Product;
import model.bean.User;

public class CommentDialog extends JDialog {
	
	private JTextField text;
	private JSpinner spinner;
	private ProductController prod;
	private Product p;
	private User u;
	
	public CommentDialog(User u,Product p, ProductController prod){
		this.p = p;
		this.u = u;
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
				Calendar cal = Calendar.getInstance();
				Date d = new Date(cal.getTimeInMillis());
				if(prod.addComment(new Comment(text.getText(), (int)spinner.getValue(),d, u.getUserName()),p,u)){
					this.dispose();
				} else {
					JOptionPane.showMessageDialog(this, "Database error!");
				}
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
