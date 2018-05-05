package view;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import controller.LogInController;
import controller.ProductController;
import model.bean.User;

public class LoggedinFrame extends MainFrame {
	private static final long serialVersionUID = 1L;
	private User user;

	public LoggedinFrame(LogInController controller, ProductController prod, User user) {
		super(controller, prod);
		this.user = user;
		
		this.setJMenuBar(createMenuBar());
	}
	
	@Override
	protected JMenuBar createMenuBar() {
		JMenuBar menubar = new JMenuBar();
		JMenu menu = new JMenu(user.getUserName());
		JMenu category = new JMenu("Category");
		JMenuItem profile = new JMenuItem("Profile");
		JMenuItem basket = new JMenuItem("Basket");
		JMenuItem logout = new JMenuItem("Log Out");
		int i = 6;
		menu.add(profile);
		menu.add(basket);
		menu.add(logout);
		menubar.add(menu);
		menubar.add(category);
		
		return menubar;
	}
}
