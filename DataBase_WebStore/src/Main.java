import controller.ProductController;
import model.bean.Product;
import view.dialog.ProductDialog;

public class Main {

	public static void main(String[] args) {
		new ProductDialog(new ProductController(), new Product(0, "Bré", 100, null, null, 0, 0));
	}

}
