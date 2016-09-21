package src.main.java.admin.reporte.log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import javax.swing.JFrame;

import src.main.java.login.Login;



public class ReporteLog extends JFrame {
	
    public static Connection connectDB() {
        Connection jdbcConnection = null;
        try {
      	  Class.forName("org.postgresql.Driver");
           // String url = "jdbc:postgresql://192.168.1.2:5432/VOTOPY"; ip casa
      	String url = "jdbc:postgresql://voto.db:5432/VOTOPY";
      	  
            jdbcConnection = DriverManager.getConnection(url,"ucsa2014", "ucsa2014");
        } catch (Exception ex) {
             String connectMsg = "Could not connect to the database: "
                       + ex.getMessage() + " " + ex.getLocalizedMessage();
             System.out.println(connectMsg);
        }
        return jdbcConnection;
   }
	
	
	public int insert(String nombreReporte) throws SQLException {
		
		Connection c = connectDB();
		
		

		String query = "INSERT INTO ucsaws_crear_reporte_log(id_reporte_log, fch_ins, usuario_ins, nombre_reporte) "+
		"     VALUES (   nextval('ucsaws_crear_reporte_sec'), now(), '"+ Login.userLogeado  + "','" +  nombreReporte.toUpperCase() +"'   )";
		
		return c.createStatement().executeUpdate(query);
	}
	
	


}
