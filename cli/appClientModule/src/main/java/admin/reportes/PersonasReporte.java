package src.main.java.admin.reportes;


	import java.io.BufferedInputStream;
import java.io.FileInputStream;
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

import org.apache.log4j.Logger;

	public class PersonasReporte {

	 //   private static Logger logger = Logger.getLogger(PersonasReporte.class);

	   
	    public static void main(String[] args) {
	    	 try {                                            
		            // load report location
		            FileInputStream fis = new FileInputStream("report.jasper");
		            BufferedInputStream bufferedInputStream = new BufferedInputStream(fis);

		            // fill report
		            List<Map<String,?>> maps = new ArrayList<Map<String, ?>> (); 
		            for (int i = 0; i < 10; i++) {
		                Map<String,Object> map = new HashMap<String, Object>();
		                map.put("name", UUID.randomUUID().toString());
		                map.put("address", UUID.randomUUID().toString());
		                maps.add(map);
		            }            
		            JRMapCollectionDataSource dataSource = new JRMapCollectionDataSource(maps);
		            
		            // compile report
		            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(bufferedInputStream);
		            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap(), dataSource);

		            // view report to UI
		            JasperViewer.viewReport(jasperPrint, false);

		        } catch (Exception e) {
		           // logger.error(e, e);
		        	e.printStackTrace();
		        }
	    }
	
}
