import controller.LogInController;
import controller.ProductController;
import view.MainFrame;

public class Main {

	public static void main(String[] args) {
		new MainFrame(new LogInController(), new ProductController());
	}

}
