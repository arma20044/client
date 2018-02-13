package src.main.java.admin.reportes;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
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
import org.apache.log4j.PropertyConfigurator;

import entity.UcsawsActas;
import entity.UcsawsMiembroMesa;
import entity.UcsawsTipoMiembroMesa;
import src.main.java.admin.acta.VentanaBuscarActa;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.reporte.log.ReporteLog;
import src.main.java.admin.utils.JDBCExample;
import src.main.java.admin.utils.NumberToLetterConverter;
import src.main.java.dao.acta.ActaDAO;
import src.main.java.dao.tipoMiembroMesa.TipoMiembroMesaDAO;
import src.main.java.login.Login;

public class Acta {

  /** The Constant LOG. */
  private static final Logger LOG = Logger.getLogger(Acta.class);

  private Logger logger = Logger.getLogger(Acta.class);

  private JDBCExample ds = new JDBCExample();

  public Acta() {}

  public void start() {


    try {

      // Log log4j configuration
      final Properties log4jProperties = new Properties();
      log4jProperties.load(new FileInputStream("E:/Log4j.properties"));
      PropertyConfigurator.configure(log4jProperties);

      String reportName = "Actas";
      // load report location

      File a = new File("jasperTemplates/actasl.jasper");

      FileInputStream fis = new FileInputStream(a);
      BufferedInputStream bufferedInputStream = new BufferedInputStream(fis);


      // SUBREPORTES
      JasperReport sub1 =
          (JasperReport) JRLoader.loadObject(new File("jasperTemplates/subReport.jasper"));
      JasperReport sub2 =
          (JasperReport) JRLoader.loadObject(new File("jasperTemplates/subReport2.jasper"));

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

      NumberToLetterConverter nTL = new NumberToLetterConverter();

      TipoMiembroMesaDAO tipoMiembroMesaDAO = new TipoMiembroMesaDAO();
      List<UcsawsTipoMiembroMesa> listTtipoMiembroMesa =
          tipoMiembroMesaDAO.obtenerTipoMiembroMesaByIdEvento(Integer
              .parseInt(VentanaBuscarEvento.evento));
      UcsawsTipoMiembroMesa miembro = new UcsawsTipoMiembroMesa();
      Iterator<UcsawsTipoMiembroMesa> ite = listTtipoMiembroMesa.iterator();

      UcsawsTipoMiembroMesa aux;
      while (ite.hasNext()) {
        aux = ite.next();
        if (aux.getDescripcion().contains("VEEDOR")
        // && aux.getMiembroMesa().getIdTipoMiembroMesa().equals(idTipoMiembroMesa)
        ) {
          // existe = true;
          miembro = aux;
          break;
        }
      }



      // parameters.put("P_ID_Evento", Integer.parseInt(VentanaBuscarEvento.evento));
      parameters.put("P_ID_EVENTO", Integer.parseInt(VentanaBuscarEvento.evento));
      parameters.put("P_GENERADO_POR", Login.nombreApellidoUserLogeado);
      parameters.put("P_FECHA", VentanaBuscarEvento.date);
      parameters.put("P_ID_ACTA", Integer.parseInt(VentanaBuscarActa.codTemporal));
      parameters.put("SUBREPORT_DIR", sub1);
      parameters.put("SUBREPORT_DIR2", sub2);
      parameters.put("P_ID_TIPO_MIEMBRO", miembro.getIdTipoMiembroMesa());


      // ***
      ActaDAO actaDAO = new ActaDAO();
      UcsawsActas acta = actaDAO.obtenerActaById(Integer.parseInt(VentanaBuscarActa.codTemporal));
      Date date = acta.getFecha();// your date
      
      Calendar cal = Calendar.getInstance();
      cal.setTime(date);
      int year = cal.get(Calendar.YEAR);
      int month = cal.get(Calendar.MONTH);
      int day = cal.get(Calendar.DAY_OF_MONTH);
      int hour = cal.get(Calendar.HOUR_OF_DAY);
      int minute = cal.get(Calendar.MINUTE);
      // ***
      String mesString = "";
      if (month == 0) {
        mesString = "Enero";
      } else if (month == 1) {
        mesString = "Febrero";
      } else if (month == 2) {
        mesString = "Marzo";
      } else if (month == 3) {
        mesString = "Abril";
      } else if (month == 4) {
        mesString = "Mayo";
      } else if (month == 5) {
        mesString = "Junio";
      } else if (month == 6) {
        mesString = "Julio";
      } else if (month == 7) {
        mesString = "Agosto";
      } else if (month == 8) {
        mesString = "Setiembre";
      } else if (month == 9) {
        mesString = "Octubre";
      } else if (month == 10) {
        mesString = "Noviembre";
      } else if (month == 11) {
        mesString = "Diciembre";
      }


      String dia = nTL.convertNumberToLetter(day).toLowerCase();
      String mes = nTL.convertNumberToLetter(month).toLowerCase();
      String anho = nTL.convertNumberToLetter(year).toLowerCase();
      
      String hora = nTL.convertNumberToLetter(hour).toLowerCase();
      String minuto = nTL.convertNumberToLetter(minute).toLowerCase();
      

      parameters.put("P_FECHA_LETRA", " " + dia + " días del mes de " + mesString + " del año " + anho);
      parameters.put("P_HORA_LETRA", hora + " horas con " + minuto + " minutos");
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
    Acta main = new Acta();
    main.start();
  }
}
