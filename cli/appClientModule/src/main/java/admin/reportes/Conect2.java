package src.main.java.admin.reportes;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class Conect2 {
     public static Connection connectDB() {
          Connection jdbcConnection = null;
          try {
        	  Class.forName("org.postgresql.Driver");
              String url = "jdbc:postgresql://localhost:5432/VOTOPY";
              jdbcConnection = DriverManager.getConnection(url,"ucsa2014", "ucsa2014");
          } catch (Exception ex) {
               String connectMsg = "Could not connect to the database: "
                         + ex.getMessage() + " " + ex.getLocalizedMessage();
               System.out.println(connectMsg);
          }
          return jdbcConnection;
     }

     public static void runReport() {
          try {
               JasperDesign jasperDesign = JRXmlLoader
                         .load(new File("report.jrxml"));
               JasperReport jasperReport = JasperCompileManager
                         .compileReport(jasperDesign);
               Connection jdbcConnection = connectDB();
               JasperPrint jasperPrint = JasperFillManager.fillReport(
                         jasperReport, null, jdbcConnection);
               JasperViewer.viewReport(jasperPrint);
          } catch (Exception ex) {
               String connectMsg = "Could not create the report "
                         + ex.getMessage() + " " + ex.getLocalizedMessage();
               System.out.println(connectMsg);
          }
     }

     public static void main(String[] args) {
    	 PatternLayout pl = new PatternLayout("[%-5p] %C.%M:%L: %m%n");
         ConsoleAppender appender = new ConsoleAppender(pl);
         Logger.getRootLogger().addAppender(appender);
          runReport();

     }
}