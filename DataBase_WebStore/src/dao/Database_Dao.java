package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.bean.Comment;
import model.bean.Product;
import model.bean.User;
import oracle.jdbc.pool.OracleDataSource;

public class Database_Dao {
	
	private ResultSet rs;
	private Statement stmt;
	private PreparedStatement prestmt;
	private String SQL;
	Connection conn = null;
	
		public Database_Dao() {
			
			try {
			 OracleDataSource ods = new OracleDataSource();
	 		  Class.forName ("oracle.jdbc.OracleDriver");

			  ods.setURL("jdbc:oracle:thin:H672651/H672651@localhost:4000:kabinet");
			  conn = ods.getConnection("H672651","H672651");
			  stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,  ResultSet.CONCUR_READ_ONLY);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		public boolean isAvailableUser(String username, String email) {
			
			SQL = "SELECT Count(*) AS SZAM FROM FELHASZNALO WHERE FELHASZNALONEV='" + username + "'";
			
			try {
				rs = stmt.executeQuery(SQL);
				rs.next();
				if(rs.getInt("SZAM") >= 1) {
					return false;
				} else {
					SQL = "SELECT Count(*) AS SZAM FROM FELHASZNALO WHERE EMAIL='" + email + "'";
					rs = stmt.executeQuery(SQL);
					rs.next();
					if(rs.getInt("SZAM") >= 1) {
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
				
				User u = new User(rs.getInt("ID"),
						rs.getString("FELHASZNALONEV"),
						rs.getString("VEZETEKNEV"),
						rs.getString("KERESZTNEV"),
						rs.getString("EMAIL"),
						rs.
						getInt("IRANYITOSZAM"),
						rs.getString("VAROS"),
						rs.getString("UTCA"),
						rs.getString("HAZSZAM"),
						rs.getInt("EGYENLEG"),
						rs.getInt("KEDVEZMENYPONT"),
						rs.getInt("JOGOSULTSAG")==0);
						return u;

								
			} catch(Exception e) {
				return null;
			}
			
		}
		
		public List<String> getCategories(){								//Lekéri a kategóriákat
			List<String> out = new ArrayList<String>();
			
			SQL = "SELECT NEV FROM ARUKATEGORIA";
			
			try {
				rs = stmt.executeQuery(SQL);
				while(rs.next()) {
					String kategoria = rs.getString("NEV");
					out.add(kategoria);
				}
				return out;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return null;
		}
		
		public List<Product> getProductsByCategory(String category){		//Lekéri az adott kategóriába tartozó termékeket
			List<Product> out = new ArrayList<Product>();
			
			SQL = "SELECT TERMEK.ID,TERMEK.NEV,TERMEK.AR,ARUKATEGORIA.NEV,TERMEK.DATUM FROM TERMEK,ARUKATEGORIA,KATEGORIA WHERE ARUKATEGORIA.NEV='" + category +"' AND KATEGORIA.TERMEK_ID = TERMEK.ID AND"
					+ " KATEGORIA.ARUKATEGORIA_ID = ARUKATEGORIA.ID";
			
			
			try {
				rs = stmt.executeQuery(SQL);
				while(rs.next()) {
					Product p = new Product(rs.getInt("ID"), rs.getString("NEV"), rs.getInt("AR"), category, rs.getDate("DATUM"));
					out.add(p);
				}
				return out;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return null;
		}
		
		public List<Comment> getComments(Product product) {					//Lekéri az adott termékhez tartozó kommenteket
			List<Comment> output = new ArrayList<Comment>();
			
			SQL = "SELECT KOMMENT.KOMMENT,KOMMENT.ERTEKELES,KOMMENT.IDOPONT,FELHASZNALO.FELHASZNALONEV FROM TERMEK,KOMMENT,FELHASZNALO WHERE"
					+ " TERMEK.ID = KOMMENT.TERMEK_ID AND KOMMENT.FELHASZNALO_ID = FELHASZNALO.ID AND TERMEK.NEV='" + product.getName() + "'";
			try {
				rs = stmt.executeQuery(SQL);
				while(rs.next()) {
					Comment c = new Comment(rs.getString("KOMMENT"), rs.getInt("ERTEKELES"), rs.getDate("IDOPONT"), rs.getString("FELHASZNALONEV"));
					output.add(c);
				}
				return output;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return null;
		}
	
		public List<Product> getProductsByUser(User u){						//Lekéri egy adott felhasználó által vásárolt termék kategóriákba sorolt termékeket
			List<Product> out = new ArrayList<Product>();

			SQL = "SELECT TERMEK.ID,TERMEK.NEV,TERMEK.AR,ARUKATEGORIA.NEV,TERMEK.DATUM FROM TERMEK,KATEGORIA,ARUKATEGORIA WHERE ARUKATEGORIA.ID = KATEGORIA.ARUKATEGORIA_ID AND TERMEK.ID = KATEGORIA.TERMEK_ID AND KATEGORIA.ARUKATEGORIA_ID in (SELECT ARUKATEGORIA.ID FROM RAKTAR,TERMEK,KATEGORIA,ARUKATEGORIA,RENDELES,FELHASZNALO WHERE "
                   + " FELHASZNALO.ID ="+ u.getID() +" AND"
                   + " RENDELES.FELHASZNALO_ID = FELHASZNALO.ID AND"
                   + " TERMEK.ID = RENDELES.TERMEK_ID AND"
                   + " TERMEK.ID = KATEGORIA.TERMEK_ID AND"
                   + " KATEGORIA.ARUKATEGORIA_ID = ARUKATEGORIA.ID)"; 
			
			try {
				rs = stmt.executeQuery(SQL);
				while(rs.next()) {
					Product p = new Product(rs.getInt("ID"), rs.getString("NEV"), rs.getInt("AR"), rs.getString("NEV"), rs.getDate("DATUM"));
					out.add(p);
				}
				return out;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
			return null;
		}
		
		public List<Product> getProductsByTheUser(User u){					//Azon termékek listája, amit user megvett
			List<Product> out = new ArrayList<Product>();
			
			SQL = "SELECT TERMEK.ID,TERMEK.NEV,TERMEK.AR,ARUKATEGORIA.NEV,TERMEK.DATUM FROM ARUKATEGORIA,TERMEK,RENDELES,FELHASZNALO,KATEGORIA WHERE "
					+ "FELHASZNALO.ID =" + u.getID() + "AND "
					+ "RENDELES.FELHASZNALO_ID = FELHASZNALO.ID AND "
					+ "TERMEK.ID = RENDELES.TERMEK_ID AND "
                    + "ARUKATEGORIA.ID = KATEGORIA.ARUKATEGORIA_ID AND "
                    + "KATEGORIA.TERMEK_ID = TERMEK.ID";

			try {
				rs = stmt.executeQuery(SQL);
				while(rs.next()) {
					Product p = new Product(rs.getInt("ID"), rs.getString("NEV"), rs.getInt("AR"), rs.getString("NEV"), rs.getDate("DATUM"));
					out.add(p);
				}
				return out;
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			return null;
		}
		
		public List<Product> getProductsByBuyers(Product p) {				//Azon termékek listája, amit az adott terméket megvásárolt felhasználók vásároltak
			List<Product> output = new ArrayList<Product>();
		
			SQL = "SELECT TERMEK.ID,TERMEK.NEV,TERMEK.AR,ARUKATEGORIA.NEV,TERMEK.DATUM FROM ARUKATEGORIA,TERMEK,RENDELES,FELHASZNALO,KATEGORIA WHERE " 
					+ "RENDELES.FELHASZNALO_ID = FELHASZNALO.ID AND " 
					+ "TERMEK.ID = RENDELES.TERMEK_ID AND "
					+ "ARUKATEGORIA.ID = KATEGORIA.ARUKATEGORIA_ID AND " 
					+ "KATEGORIA.TERMEK_ID = TERMEK.ID AND "
					+ "FELHASZNALO_ID in (SELECT FELHASZNALO.ID FROM TERMEK,FELHASZNALO,RENDELES WHERE "
					+ "RENDELES.TERMEK_ID = TERMEK.ID AND " 
					+ "FELHASZNALO.ID = RENDELES.FELHASZNALO_ID AND " 
					+ "TERMEK.ID =" + p.getID() + ")";
			try {
				rs = stmt.executeQuery(SQL);
				while(rs.next()) {
					Product pr = new Product(rs.getInt("ID"), rs.getString("NEV"), rs.getInt("AR"), rs.getString("NEV"), rs.getDate("DATUM"));
					output.add(pr);
				}
				return output;
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			
			return null;
		}
		
}
