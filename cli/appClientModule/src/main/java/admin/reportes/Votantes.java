package src.main.java.admin.reportes;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
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

public class Votantes {

  private Logger logger = Logger.getLogger(Votantes.class);

  private JDBCExample ds = new JDBCExample();

  public Votantes() {}

  public void start() {
    try {

      String reportName = "ListadoVotantes";
      // load report location

      File a = new File("jasperTemplates/votantes.jasper");

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

      Connection jdbcConnection = ds.getConnection();

      // compile report
      JasperReport jasperReport = (JasperReport) JRLoader.loadObject(bufferedInputStream);

      HashMap<String, Object> parameters = new HashMap<String, Object>();

      // parameters.put("P_ID_Evento", Integer.parseInt(VentanaBuscarEvento.evento));
      parameters.put("P_ID_EVENTO", Integer.parseInt(VentanaBuscarEvento.evento));
      parameters.put("P_GENERADO_POR", Login.nombreApellidoUserLogeado);
      parameters.put("P_FECHA", VentanaBuscarEvento.date);
      // parameters.put("P_ID_EVENTO", 8);
      // parameters.put("P_GENERADO_POR","HOLA");



      JasperPrint jasperPrint =
          JasperFillManager.fillReport(jasperReport, parameters, jdbcConnection);

      if (jasperPrint.getPages().toString().compareTo("[]") == 0) {
        JOptionPane.showMessageDialog(null, "El reporte no contiene datos.", "Información",
            JOptionPane.WARNING_MESSAGE);
      } else {
        // view report to UI
        JasperViewer.viewReport(jasperPrint, false);

        // exports report to pdf
        // C:\Users\arma2\Desktop

        /*
         * DateTime s = new org.joda.time.DateTime(); String o =
         * String.valueOf(s.getDayOfMonth())+String
         * .valueOf(s.getMonthOfYear())+String.valueOf(s.getYear())
         * +String.valueOf(s.getHourOfDay())
         * +String.valueOf(s.getMinuteOfHour())+String.valueOf(s.getSecondOfMinute());
         * 
         * 
         * JasperExportManager.exportReportToPdfFile(jasperPrint,
         * "c:/Users/arma2/Desktop/ExportPDF/"+reportName+o+".pdf");
         */
        ReporteLog log = new ReporteLog();

        log.insert(a.getName().substring(0, a.getName().length() - 7));

        if ((jdbcConnection != null) && (!jdbcConnection.isClosed())) {
          jdbcConnection.close();
        }

      }

    } catch (Exception e) {
      logger.error(e, e);
    }
  }



  private String getRandomString() {
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
