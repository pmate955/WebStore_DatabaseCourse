package view;

import controller.LogInController;
import controller.ProductController;
import model.bean.User;

public class LoggedinFrame extends MainFrame {
	private static final long serialVersionUID = 1L;
	private User user;

	public LoggedinFrame(LogInController controller, ProductController prod, User user) {
		super(controller, prod);
		super.username.setText(user.getUserName());
		this.user = user;
	}
	
	
}
