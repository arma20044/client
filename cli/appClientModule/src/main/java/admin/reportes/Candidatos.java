package src.main.java.admin.reportes;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.reporte.log.ReporteLog;
import src.main.java.admin.utils.JDBCExample;
import src.main.java.login.Login;



public class Candidatos {

  private Logger logger = Logger.getLogger(Candidatos.class);

  private JDBCExample ds = new JDBCExample();

  Connection jdbcConnection = null;

  public Candidatos() {}

  public void start() {
    try {
      jdbcConnection = ds.getConnection();
      // load report location

      // File a = new File("candidatos.jasper");
      File a = new File("jasperTemplates/candidatos.jasper");
      FileInputStream fis = new FileInputStream(a);
      BufferedInputStream bufferedInputStream = new BufferedInputStream(fis);

      // fill report
      List<Map<String, ?>> maps = new ArrayList<Map<String, ?>>();
      for (int i = 0; i < 10; i++) {
        Map<String, Object> map = new HashMap<String, Object>();
        // map.put("name", getRandomString());
        // map.put("address", getRandomString());
        maps.add(map);
      }
      JRMapCollectionDataSource dataSource = new JRMapCollectionDataSource(maps);

      HashMap<String, Object> parameters = new HashMap<String, Object>();
      parameters.put("P_ID_EVENTO", Integer.parseInt(VentanaBuscarEvento.evento));
      parameters.put("P_FECHA", VentanaBuscarEvento.date);
      parameters.put("P_GENERADO_POR", Login.nombreApellidoUserLogeado);



      // compile report
      JasperReport jasperReport = (JasperReport) JRLoader.loadObject(bufferedInputStream);
      JasperPrint jasperPrint =
          JasperFillManager.fillReport(jasperReport, parameters, jdbcConnection);

      if (jasperPrint.getPages().toString().compareTo("[]") == 0) {
        JOptionPane.showMessageDialog(null, "El reporte no contiene datos.", "Información",
            JOptionPane.WARNING_MESSAGE);
      } else {
        // view report to UI

        JasperViewer.viewReport(jasperPrint, false);
        ReporteLog log = new ReporteLog();

        log.insert(a.getName().substring(0, a.getName().length() - 7));

        if ((jdbcConnection != null) && (!jdbcConnection.isClosed())) {
          jdbcConnection.close();
        }

      }

    } catch (Exception e) {
      logger.error(e, e);
    }

    finally {
     
          try {
            jdbcConnection.close();
          } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
      
    }
  }



  private String getRandomString() {
    return UUID.randomUUID().toString();
  }

  public static void main(String[] args) {
    PatternLayout pl = new PatternLayout("[%-5p] %C.%M:%L: %m%n");
    ConsoleAppender appender = new ConsoleAppender(pl);
    Logger.getRootLogger().addAppender(appender);
    Candidatos main = new Candidatos();
    main.start();
  }
}
