import javax.swing.JOptionPane;

import controller.LogInController;
import controller.ProductController;
import dao.Database_Dao;
import view.MainFrame;

public class Main {

	public static void main(String[] args) {
		String [] locations = { "Kabinet", "Localhost" };
		String city = (String) JOptionPane.showInputDialog(
		    null,
		    "Select source",
		    "Webstore database",
		    JOptionPane.PLAIN_MESSAGE,
		    null,
		    locations,
		    locations[0]);
		if(city.equals("Localhost")) Database_Dao.isLocalhost = true; 
		new MainFrame(new LogInController(), new ProductController());
	}

}
