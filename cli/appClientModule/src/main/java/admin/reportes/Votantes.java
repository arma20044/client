package src.main.java.admin.reportes;
 
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
 
public class Votantes {
 
    private Logger logger = Logger.getLogger(Votantes.class);
 
    public Votantes() {
    }
 
    private void start() {
        try {                                           
            // load report location
        	
        	File a = new File("jasperTemplates/votantes.jasper");
        	
            FileInputStream fis = new FileInputStream(a);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fis);
 
            // fill report
            List<Map<String,?>> maps = new ArrayList<Map<String, ?>> ();
            for (int i = 0; i < 10; i++) {
                Map<String,Object> map = new HashMap<String, Object>();
//                map.put("name", getRandomString());
//                map.put("address", getRandomString());
                maps.add(map);
            }           
            JRMapCollectionDataSource dataSource = new JRMapCollectionDataSource(maps);
            
            Connection jdbcConnection = connectDB();
             
            // compile report
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(bufferedInputStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap(), jdbcConnection);
 
            // view report to UI
            JasperViewer.viewReport(jasperPrint, false);
 
        } catch (Exception e) {
            logger.error(e, e);
        }
    }
    
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
    
     
    private String getRandomString(){
        return UUID.randomUUID().toString();
    }
 
    public static void main(String[] args) {
    	PatternLayout pl = new PatternLayout("[%-5p] %C.%M:%L: %m%n");
        ConsoleAppender appender = new ConsoleAppender(pl);
        Logger.getRootLogger().addAppender(appender);
        Votantes main = new Votantes();
        main.start();
    }
}