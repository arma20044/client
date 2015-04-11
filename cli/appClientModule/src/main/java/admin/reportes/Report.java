package src.main.java.admin.reportes;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperCompileManager;


public class Report {
static Connection conn = null;


public static void main(String[] args){
// Cargamos el driver JDBC
try {
Class.forName("com.mysql.jdbc.Driver");
    }
    catch (ClassNotFoundException e) {
System.out.println("MySQL JDBC Driver not found.");
System.exit(1);
}
//Para iniciar el Logger.
//inicializaLogger();
try {
conn = DriverManager.getConnection("jdbc:mysql://localhost/tecnireg_osc1","root", "");
//conn.setAutoCommit(false);

    }
    catch (SQLException e) {
System.out.println("Error de conexión: " + e.getMessage());
System.exit(4);
}

try {
Map parameters = new HashMap();
parameters.put("TITULO", "PAISES");
parameters.put("FECHA", new java.util.Date());
JasperReport report = JasperCompileManager.compileReport(
"C:\\informes JAsper\\JRXML\\InformeMySql.jrxml");
JasperPrint print = JasperFillManager.fillReport(report, parameters, conn);
// Exporta el informe a PDF
JasperExportManager.exportReportToPdfFile(print,
"C:\\informes JAsper\\PDF's\\InformePaisesMySQL.pdf");
//Para visualizar el pdf directamente desde java
JasperViewer.viewReport(print, false);
    }
    catch (Exception e) {
e.printStackTrace();
}
finally {
/*
* Cleanup antes de salir
*/
try {
if (conn!= null) {
conn.rollback();
System.out.println("ROLLBACK EJECUTADO");
conn.close();
}
 }
      catch (Exception e) {
e.printStackTrace();
      }
    }
 
  }
 }
  }
 }
 }
 } } } } } } } 
 
} 