package src.main.java.admin.reportes;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.exception.DRException;

public class SimpleReportExample {
  

  public static void main(String[] args) {
    /*Connection connection = null;
    try {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(
                    "jdbc:mysql://hostname:port/dbname","username", "password");
    } catch (SQLException e) {
        e.printStackTrace();
        return;
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
        return;
    }*/
    Connection jdbcConnection = connectDB();

    JasperReportBuilder report = DynamicReports.report();//a new report
    report
      .columns(
          Columns.column("nombre", "nombre", DataTypes.stringType()),
          Columns.column("apellido", "apellido", DataTypes.stringType())
         // Columns.column("tl.descripcion", "tl.descripcion", DataTypes.stringType()),
         // Columns.column("can.descripcion", "can.descripcion", DataTypes.dateType())
          )
          .title(//title of the report
          Components.text("SimpleReportExample")
          .setHorizontalAlignment(HorizontalAlignment.CENTER))
          .pageFooter(Components.pageXofY())//show page number on the page footer
          .setDataSource("select nombre, apellido from ucsaws_candidatos can "+
"join ucsaws_persona per on (per.id_persona = can.id_persona) join ucsaws_listas list on (list.id_lista = can.id_lista)"+
"join ucsaws_tipo_lista tl on (tl.id_tipo_lista = list.id_tipo_lista) WHERE CAN.ID_EVENTO = 1 order by nro_lista DESC",
              jdbcConnection);

    try {
                //show the report
        report.show();

                //export the report to a pdf file
        report.toPdf(new FileOutputStream("e:/TESIS/report.pdf"));
        report.toDocx(new FileOutputStream("e:/TESIS/report.docx"));
    } catch (DRException e) {
        e.printStackTrace();
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
  }
  
  
  public static Connection connectDB() {
      Connection jdbcConnection = null;
      try {
        Class.forName("org.postgresql.Driver");
          String url = "jdbc:postgresql://voto.db:5432/VOTOPY";
          jdbcConnection = DriverManager.getConnection(url,"ucsa2014", "ucsa2014");
      } catch (Exception ex) {
           String connectMsg = "Could not connect to the database: "
                     + ex.getMessage() + " " + ex.getLocalizedMessage();
           System.out.println(connectMsg);
      }
      return jdbcConnection;
 }
  

}
