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

import org.apache.log4j.Logger;

import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.reporte.log.ReporteLog;
import src.main.java.admin.utils.JDBCExample;
import src.main.java.login.Login;
import entity.UcsawsTipoLista;
 
public class CantidadVotosSenadorDiputado {
	
	String P_Candidato_Desc_String;
 
    private Logger logger = Logger.getLogger(CantidadVotosSenadorDiputado.class);
    
    private JDBCExample ds = new JDBCExample();
 
    public CantidadVotosSenadorDiputado(UcsawsTipoLista idTipo) {
    	System.out.println(idTipo);
    	if (idTipo.getCodigo().compareTo("PRE") == 0){
    		P_Candidato_Desc_String = "Presidente de la Republica del Paraguay";
    	}
    	else if (idTipo.getCodigo().compareTo("PAR") == 0){
    		
    		P_Candidato_Desc_String =  "PARLAMENTO DEL MERCOSUR";
    	}
    	else if (idTipo.getCodigo().compareTo("SEN") == 0){
    		P_Candidato_Desc_String =  "SENADOR";
    	}
    }
 
    public void start() {
        try {                                           
            // load report location
        	
        	File a = new File("jasperTemplates/cantidadVotosXCandidaturaGraficoSenadoresDiputados.jasper");
        	
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
            
            Connection jdbcConnection =   ds.getConnection();
             
            // compile report
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(bufferedInputStream);
            
            HashMap<String, Object> parameters = new HashMap<String, Object>();  
            parameters.put("P_Tipo_Candidato", CantidadVotosElegir.tipoSelected);
            parameters.put("P_Candidato_Desc",P_Candidato_Desc_String.toUpperCase());
 //           parameters.put("P_Tipo_Candidato", 1);
//            parameters.put("P_Candidato_Desc","Presidente de la Republica del Paraguay");
            parameters.put("P_FECHA", VentanaBuscarEvento.date );
            parameters.put("P_ID_Evento", Integer.parseInt(VentanaBuscarEvento.evento));
            parameters.put("P_GENERADO_POR",Login.nombreApellidoUserLogeado);
            
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters, jdbcConnection);
            
            
            System.out.println(jasperPrint.getPages());
 
            if (jasperPrint.getPages().toString().compareTo("[]")==0){
            	JOptionPane.showMessageDialog(null, "El reporte no contiene datos.",
						"Información", JOptionPane.WARNING_MESSAGE);
            }
            else{
            // view report to UI
            JasperViewer.viewReport(jasperPrint, false);
            
            ReporteLog log = new ReporteLog();
            
            log.insert(a.getName().substring(0,a.getName().length()-7));
            
            if ((jdbcConnection != null) && (!jdbcConnection.isClosed())) {
            	jdbcConnection.close();
             }
            
            }
 
        } catch (Exception e) {
            logger.error(e, e);
        }
    }
    
 
    
     
    private String getRandomString(){
        return UUID.randomUUID().toString();
    }
 
   /* public static void main(String[] args) {
    	PatternLayout pl = new PatternLayout("[%-5p] %C.%M:%L: %m%n");
        ConsoleAppender appender = new ConsoleAppender(pl);
        Logger.getRootLogger().addAppender(appender);
        CantidadVotosSenadorDiputado main = new CantidadVotosSenadorDiputado(CantidadVotosElegir.tipoSelected);
       // CantidadVotos main = new CantidadVotos(1);
        main.start();
    }*/
}