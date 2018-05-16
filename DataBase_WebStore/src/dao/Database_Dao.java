package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.bean.Address;
import model.bean.Comment;
import model.bean.Order;
import model.bean.Product;
import model.bean.User;
import oracle.jdbc.pool.OracleDataSource;

public class Database_Dao {

	public static String url = "jdbc:oracle:thin:@localhost:1521:xe";
	public static String usrLocal = "SYSTEM";
	public static String passLocal = "admin";
	public static String urlKab = "jdbc:oracle:thin:H672651/H672651@localhost:4000:kabinet";
	public static String usrKab = "H672651";
	public static boolean isLocalhost = false;
	
	private ResultSet rs;
	private Statement stmt;
	private PreparedStatement prestmt;
	private String SQL;
	private String SQL2;
	private String SQL3;
	private String SQL4;
	private String SQL5;
	Connection conn = null;

	public Database_Dao() {

		try {
			OracleDataSource ods = new OracleDataSource();
			Class.forName("oracle.jdbc.OracleDriver");

			ods.setURL(isLocalhost?url:urlKab);
			conn = ods.getConnection(isLocalhost?"SYSTEM":"H672651", isLocalhost?"admin":"H672651");
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isAvailableUser(String username, String email) {

		SQL = "SELECT Count(*) AS SZAM FROM FELHASZNALO WHERE FELHASZNALONEV='" + username + "'";

		try {
			rs = stmt.executeQuery(SQL);
			rs.next();
			if (rs.getInt("SZAM") >= 1) {
				return false;
			} else {
				SQL = "SELECT Count(*) AS SZAM FROM FELHASZNALO WHERE EMAIL='" + email + "'";
				rs = stmt.executeQuery(SQL);
				rs.next();
				if (rs.getInt("SZAM") >= 1) {
					return false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Valami hiba");
			return false;
		}
		return true;
	}

	public boolean addUser(User user, String pwd1) {

		SQL = "INSERT INTO FELHASZNALO(FELHASZNALONEV,VEZETEKNEV,KERESZTNEV,JELSZO,EMAIL,IRANYITOSZAM,VAROS,UTCA,HAZSZAM,JOGOSULTSAG,EGYENLEG,KEDVEZMENYPONT,TORZSVASARLO) "
				+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try {
			prestmt = conn.prepareStatement(SQL);

			prestmt.setString(1, user.getUserName());
			prestmt.setString(2, user.getLastName());
			prestmt.setString(3, user.getFirstName());
			prestmt.setString(4, pwd1);
			prestmt.setString(5, user.getEmail());
			prestmt.setInt(6, user.getAddress().getZipcode());
			prestmt.setString(7, user.getAddress().getCity());
			prestmt.setString(8, user.getAddress().getStreet());
			prestmt.setString(9, user.getAddress().getHouseNumber());
			prestmt.setInt(10, 1);
			prestmt.setInt(11, 1000);
			prestmt.setInt(12, 10);
			prestmt.setInt(13, 0);

			int result = prestmt.executeUpdate();

			return result == 1;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return true;
	}

	public User getUser(String username, String password) {

		SQL = "SELECT * FROM FELHASZNALO WHERE FELHASZNALONEV='" + username + "' AND JELSZO='" + password + "'";

		try {
			rs = stmt.executeQuery(SQL);
			rs.next();

			User u = new User(rs.getInt("ID"), rs.getString("FELHASZNALONEV"), rs.getString("VEZETEKNEV"),
					rs.getString("KERESZTNEV"), rs.getString("EMAIL"), rs.getInt("IRANYITOSZAM"), rs.getString("VAROS"),
					rs.getString("UTCA"), rs.getString("HAZSZAM"), rs.getInt("EGYENLEG"), rs.getInt("KEDVEZMENYPONT"),
					rs.getInt("JOGOSULTSAG") == 0);
			return u;

		} catch (Exception e) {
			return null;
		}

	}

	public List<String> getCategories() { // Lekéri a kategóriákat
		List<String> out = new ArrayList<String>();

		SQL = "SELECT NEV FROM ARUKATEGORIA";

		try {
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				String kategoria = rs.getString("NEV");
				out.add(kategoria);
			}
			return out;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public List<Product> getProductsByCategory(String category) { // Lekéri az adott kategóriába tartozó termékeket
		List<Product> out = new ArrayList<Product>();

		SQL = "SELECT TERMEK.ID,TERMEK.NEV,TERMEK.AR,ARUKATEGORIA.NEV,TERMEK.DATUM FROM TERMEK,ARUKATEGORIA,KATEGORIA WHERE ARUKATEGORIA.NEV='"
				+ category + "' AND KATEGORIA.TERMEK_ID = TERMEK.ID AND"
				+ " KATEGORIA.ARUKATEGORIA_ID = ARUKATEGORIA.ID";

		try {
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				Product p = new Product(rs.getInt("ID"), rs.getString("NEV"), rs.getInt("AR"), category,
						rs.getDate("DATUM"));
				out.add(p);
			}
			return out;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public List<Comment> getComments(Product product) { // Lekéri az adott termékhez tartozó kommenteket
		List<Comment> output = new ArrayList<Comment>();

		SQL = "SELECT KOMMENT.KOMMENT,KOMMENT.ERTEKELES,KOMMENT.IDOPONT,FELHASZNALO.FELHASZNALONEV FROM TERMEK,KOMMENT,FELHASZNALO WHERE"
				+ " TERMEK.ID = KOMMENT.TERMEK_ID AND KOMMENT.FELHASZNALO_ID = FELHASZNALO.ID AND TERMEK.NEV='"
				+ product.getName() + "'";
		try {
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				Comment c = new Comment(rs.getString("KOMMENT"), rs.getInt("ERTEKELES"), rs.getDate("IDOPONT"),
						rs.getString("FELHASZNALONEV"));
				output.add(c);
			}
			return output;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public List<Product> getProductsByUser(User u) { // Lekéri egy adott felhasználó által vásárolt termék kategóriákba
														// sorolt termékeket
		List<Product> out = new ArrayList<Product>();

		SQL = "SELECT TERMEK.ID,TERMEK.NEV,TERMEK.AR,ARUKATEGORIA.NEV,TERMEK.DATUM FROM TERMEK,KATEGORIA,ARUKATEGORIA WHERE ARUKATEGORIA.ID = KATEGORIA.ARUKATEGORIA_ID AND TERMEK.ID = KATEGORIA.TERMEK_ID AND KATEGORIA.ARUKATEGORIA_ID in (SELECT ARUKATEGORIA.ID FROM RAKTAR,TERMEK,KATEGORIA,ARUKATEGORIA,RENDELES,FELHASZNALO WHERE "
				+ " FELHASZNALO.ID =" + u.getID() + " AND" + " RENDELES.FELHASZNALO_ID = FELHASZNALO.ID AND"
				+ " TERMEK.ID = RENDELES.TERMEK_ID AND" + " TERMEK.ID = KATEGORIA.TERMEK_ID AND"
				+ " KATEGORIA.ARUKATEGORIA_ID = ARUKATEGORIA.ID)";

		try {
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				Product p = new Product(rs.getInt("ID"), rs.getString("NEV"), rs.getInt("AR"), rs.getString("NEV"),
						rs.getDate("DATUM"));
				out.add(p);
			}
			return out;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public List<Product> getProductsByTheUser(User u) { // Azon termékek listája, amit user megvett
		List<Product> out = new ArrayList<Product>();

		SQL = "SELECT TERMEK.ID,TERMEK.NEV,TERMEK.AR,ARUKATEGORIA.NEV,TERMEK.DATUM FROM ARUKATEGORIA,TERMEK,RENDELES,FELHASZNALO,KATEGORIA WHERE "
				+ "FELHASZNALO.ID =" + u.getID() + "AND " + "RENDELES.FELHASZNALO_ID = FELHASZNALO.ID AND "
				+ "TERMEK.ID = RENDELES.TERMEK_ID AND " + "ARUKATEGORIA.ID = KATEGORIA.ARUKATEGORIA_ID AND "
				+ "KATEGORIA.TERMEK_ID = TERMEK.ID";

		try {
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				Product p = new Product(rs.getInt("ID"), rs.getString("NEV"), rs.getInt("AR"), rs.getString("NEV"),
						rs.getDate("DATUM"));
				out.add(p);
			}
			return out;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public List<Product> getProductsByBuyers(Product p) { // Azon termékek listája, amit az adott terméket megvásárolt
															// felhasználók vásároltak
		List<Product> output = new ArrayList<Product>();

		SQL = "SELECT TERMEK.ID,TERMEK.NEV,TERMEK.AR,ARUKATEGORIA.NEV,TERMEK.DATUM FROM ARUKATEGORIA,TERMEK,RENDELES,FELHASZNALO,KATEGORIA WHERE "
				+ "RENDELES.FELHASZNALO_ID = FELHASZNALO.ID AND " + "TERMEK.ID = RENDELES.TERMEK_ID AND "
				+ "ARUKATEGORIA.ID = KATEGORIA.ARUKATEGORIA_ID AND " + "KATEGORIA.TERMEK_ID = TERMEK.ID AND "
				+ "FELHASZNALO_ID in (SELECT FELHASZNALO.ID FROM TERMEK,FELHASZNALO,RENDELES WHERE "
				+ "RENDELES.TERMEK_ID = TERMEK.ID AND " + "FELHASZNALO.ID = RENDELES.FELHASZNALO_ID AND "
				+ "TERMEK.ID =" + p.getID() + ")";
		try {
			rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				Product pr = new Product(rs.getInt("ID"), rs.getString("NEV"), rs.getInt("AR"), rs.getString("NEV"),
						rs.getDate("DATUM"));
				output.add(pr);
			}
			return output;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean addComment(Comment c, Product p, User u) { // Hozzáadja a kommentet

		SQL = "INSERT INTO KOMMENT(FELHASZNALO_ID,TERMEK_ID,IDOPONT,KOMMENT,ERTEKELES) VALUES (?,?,?,?,?)";

		try {
			prestmt = conn.prepareStatement(SQL);
			prestmt.setInt(1, u.getID());
			prestmt.setInt(2, p.getID());
			prestmt.setDate(3, (Date) c.getDate());
			prestmt.setString(4, c.getComment());
			prestmt.setInt(5, c.getValue());

			int result = prestmt.executeUpdate();

			return result == 1;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean buyProduct(User u, Product p) { // Megveszi a terméket (ár újraszámolva), felhasználó egyenlegét is
													// frissiteni kell, raktár értéke növelés
		SQL = "SELECT DARABSZAM FROM RAKTAR WHERE TERMEK_ID=" + p.getID();
		SQL2 = "SELECT ELADOTT_TERMEK FROM RAKTAR WHERE TERMEK_ID=" + p.getID();
		
		int darabszam = 0;
		int eladottszam = 0;
		try {
			rs = stmt.executeQuery(SQL);
			rs.next();
			darabszam = rs.getInt("DARABSZAM");
			rs = stmt.executeQuery(SQL2);
			rs.next();
			eladottszam = rs.getInt("ELADOTT_TERMEK");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (darabszam > 0) {
			int balance = u.getBalance() - p.getPrice();
			int availableAfter = darabszam - 1;
			int soldAfter = eladottszam + 1;
			SQL = "UPDATE FELHASZNALO SET EGYENLEG=" + balance + " WHERE FELHASZNALO.ID=" + u.getID();
			SQL2 = "UPDATE RAKTAR SET DARABSZAM=" + availableAfter + " WHERE TERMEK_ID=" + p.getID();
			SQL3 = "UPDATE RAKTAR SET ELADOTT_TERMEK=" + soldAfter + " WHERE TERMEK_ID=" + p.getID();
			SQL4 = "INSERT INTO PENZUGY(ID,FIZETESI_IDOPONT,BEFOLYO_OSSZEG) VALUES ((SELECT MAX(ID) FROM PENZUGY)+1,SYSDATE," + p.getPrice() +")";
			SQL5 = "INSERT INTO RENDELES(FELHASZNALO_ID,TERMEK_ID,PENZUGY_ID,RENDELESI_IDOPONT,STATUSZ,FIZETESI_MOD) VALUES"
					+ "(" + u.getID() + ", "
						  + p.getID() + ", "
						  + "(SELECT MAX(ID) FROM PENZUGY), "
						  + "SYSDATE, "
						  + "'fizetve', "
						  + "1)";
						
			try {
				prestmt = conn.prepareStatement(SQL);
				int result = prestmt.executeUpdate();
				prestmt = conn.prepareStatement(SQL2);
				int result2 = prestmt.executeUpdate();
				prestmt = conn.prepareStatement(SQL3);
				int result3 = prestmt.executeUpdate();
				prestmt = conn.prepareStatement(SQL4);
				int result4 = prestmt.executeUpdate();
				prestmt = conn.prepareStatement(SQL5);
				int result5 = prestmt.executeUpdate();
				return (result == 1) && (result2 == 1) && (result3 == 1) && (result4 == 1) && (result5 == 1);
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		return false;
	}
	
	public User reloadUser(User u){
		
		SQL = "SELECT * FROM FELHASZNALO WHERE FELHASZNALONEV='" + u.getUserName() + "'";

		try {
			rs = stmt.executeQuery(SQL);
			rs.next();

			User us = new User(rs.getInt("ID"), rs.getString("FELHASZNALONEV"), rs.getString("VEZETEKNEV"),
					rs.getString("KERESZTNEV"), rs.getString("EMAIL"), rs.getInt("IRANYITOSZAM"), rs.getString("VAROS"),
					rs.getString("UTCA"), rs.getString("HAZSZAM"), rs.getInt("EGYENLEG"), rs.getInt("KEDVEZMENYPONT"),
					rs.getInt("JOGOSULTSAG") == 0);
			return us;

		} catch (Exception e) {
			return null;
		}
	}
	
	public boolean addMoney(User u, int money){
		
		int NewBalance = u.getBalance() + money;
		
		SQL = "UPDATE FELHASZNALO SET EGYENLEG=" + NewBalance + " WHERE FELHASZNALO.ID=" + u.getID();
		
		try {
			prestmt = conn.prepareStatement(SQL);
			int result = prestmt.executeUpdate();
			return result == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteProduct(Product p){
		
		SQL = "DELETE KATEGORIA WHERE KATEGORIA.TERMEK_ID=" + p.getID();
		SQL2 = "DELETE RAKTAR WHERE RAKTAR.TERMEK_ID=" + p.getID();
		SQL3 = "DELETE KOMMENT WHERE KOMMENT.TERMEK_ID=" + p.getID();
		SQL4 = "DELETE RENDELES WHERE RENDELES.TERMEK_ID=" + p.getID();
		SQL5 = "DELETE TERMEK WHERE TERMEK.NEV='" + p.getName() + "'";
		
		try {
			prestmt = conn.prepareStatement(SQL);
			int result = prestmt.executeUpdate();
			prestmt = conn.prepareStatement(SQL2);
			int result2 = prestmt.executeUpdate();
			prestmt = conn.prepareStatement(SQL3);
			int result3 = prestmt.executeUpdate();
			prestmt = conn.prepareStatement(SQL4);
			int result4 = prestmt.executeUpdate();
			prestmt = conn.prepareStatement(SQL5);
			int result5 = prestmt.executeUpdate();
			return (result == 1) && (result2 == 1) && (result5 == 1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean addCategory(String category){
		
		SQL = "INSERT INTO ARUKATEGORIA(NEV) VALUES(?)";
		
		try {
			prestmt = conn.prepareStatement(SQL);
			prestmt.setString(1, category);
			int result = prestmt.executeUpdate();
			return result == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean addProduct(Product p, int quantity){
		SQL = "INSERT INTO TERMEK(ID, NEV, DATUM, AR) VALUES((SELECT MAX(ID) FROM TERMEK) + 1, '" + p.getName() + "', SYSDATE, " + p.getPrice() + ")";
		SQL2 = "INSERT INTO KATEGORIA(TERMEK_ID, ARUKATEGORIA_ID) VALUES((SELECT MAX(ID) FROM TERMEK), (SELECT ID FROM ARUKATEGORIA WHERE NEV = '" + p.getCategory() + "'))";
		SQL3 = "INSERT INTO RAKTAR(TERMEK_ID, DARABSZAM, ELADOTT_TERMEK) VALUES((SELECT MAX(ID) FROM TERMEK), " + quantity + ", 0)";
		
		try {
			prestmt = conn.prepareStatement(SQL);
			int result = prestmt.executeUpdate();
			prestmt = conn.prepareStatement(SQL2);
			int result2 = prestmt.executeUpdate();
			prestmt = conn.prepareStatement(SQL3);
			int result3 = prestmt.executeUpdate();
			
			return (result == 1) && (result2 == 1) && (result3 == 1);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	public int getQuantity(Product p) {
		SQL = "SELECT DARABSZAM FROM RAKTAR WHERE TERMEK_ID = " + p.getID();
		
		try {
			rs = stmt.executeQuery(SQL);
			rs.next();
			
			return rs.getInt("DARABSZAM");
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	public List<Product> getAllProduct() {
		SQL = "SELECT TERMEK.ID,TERMEK.NEV,TERMEK.AR,ARUKATEGORIA.NEV,TERMEK.DATUM FROM TERMEK,ARUKATEGORIA,KATEGORIA WHERE "
				+ " KATEGORIA.TERMEK_ID = TERMEK.ID AND"
				+ " KATEGORIA.ARUKATEGORIA_ID = ARUKATEGORIA.ID";
		List<Product> out = new ArrayList<Product>();
		try {
			rs = stmt.executeQuery(SQL);
			
			while(rs.next()) {
				Product p = new Product(rs.getInt("ID"), rs.getString("NEV"), rs.getInt("AR"), rs.getString("NEV"),
						rs.getDate("DATUM"));
				out.add(p);
			}
			
			return out;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public boolean updateQty(Product p, int quantity) {
		SQL = "UPDATE RAKTAR SET DARABSZAM = " + quantity + " WHERE TERMEK_ID = " + p.getID();
		
		
		try {
			prestmt = conn.prepareStatement(SQL);
			int result = prestmt.executeUpdate();
			
			return result == 1;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	public List<Order> getOrders() {
		SQL = "SELECT FELHASZNALO.ID AS FID, FELHASZNALO.FELHASZNALONEV, FELHASZNALO.VEZETEKNEV,"
				+ "FELHASZNALO.KERESZTNEV, FELHASZNALO.EMAIL, FELHASZNALO.IRANYITOSZAM,"
				+ "FELHASZNALO.VAROS, FELHASZNALO.UTCA, FELHASZNALO.HAZSZAM,"
				+ "FELHASZNALO.JOGOSULTSAG, FELHASZNALO.EGYENLEG, FELHASZNALO.KEDVEZMENYPONT, TERMEK.ID AS PID, TERMEK.NEV, "
				+ "TERMEK.AR,ARUKATEGORIA.NEV, TERMEK.DATUM, RENDELES.RENDELESI_IDOPONT, RENDELES.STATUSZ, RENDELES.PENZUGY_ID AS PPID "
				+ "RENDELES.FIZETESI_MOD, PENZUGY.FIZETESI_IDOPONT FROM FELHASZNALO, TERMEK, "
				+ "KATEGORIA, ARUKATEGORIA, PENZUGY, RENDELES WHERE "
				+ " KATEGORIA.TERMEK_ID = TERMEK.ID AND "
				+ " KATEGORIA.ARUKATEGORIA_ID = ARUKATEGORIA.ID AND"
				+ " RENDELES.FELHASZNALO_ID = FELHASZNALO.ID AND "
				+ " RENDELES.TERMEK_ID = TERMEK.ID AND "
				+ " RENDELES.PENZUGY_ID = PENZUGY.ID ";
		
		List<Order> out = new ArrayList<Order>();
		
		try {
			rs = stmt.executeQuery(SQL);
			
			while(rs.next()) {
				User user = new User(rs.getInt("FID"), rs.getString("FELHASZNALONEV"), rs.getString("VEZETEKNEV"), rs.getString("KERESZTNEV"), rs.getString("EMAIL"),
						rs.getInt("IRANYITOSZAM"), rs.getString("VAROS"), rs.getString("UTCA"),
						rs.getString("HAZSZAM"), rs.getInt("EGYENLEG"), rs.getInt("KEDVEZMENYPONT"), 
						rs.getInt("JOGOSULTSAG") == 0);
				
				Product p = new Product(rs.getInt("PID"), rs.getString("NEV"), rs.getInt("AR"), rs.getString("NEV"),
						rs.getDate("DATUM"));
				
				Order order = new Order(user, p, rs.getInt("PPID"), rs.getString("STATUSZ"), rs.getDate("RENDELESI_IDOPONT"), rs.getDate("FIZETESI_IDOPONT"), rs.getInt("FIZETESI_MOD"));
				
				out.add(order);
			}
			
			return out;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean updateOrder(Order o) {
		SQL = "UPDATE RENDELES SET STATUSZ = " + o.getStatus() + " WHERE PENZUGY_ID = " + o.getPID();
		
		try {
			prestmt = conn.prepareStatement(SQL);
			int result = prestmt.executeUpdate();
			
			return result == 1;
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
