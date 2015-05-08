package src.main.java.admin.reportes;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class VerReporte {

	
	
	public void reportviewer() {
        try{
        String report = "C:\\Users\\cleanfuel\\Documents\\NetBeansProjects\\StringManipulation\\src\\stringmanipulation\\report1.jrxml";
        JasperReport jasp_report = JasperCompileManager.compileReport(report);
        JasperPrint jasp_print = JasperFillManager.fillReport(jasp_report, null, con);
        JasperViewer.viewReport(jasp_print);
        }
        catch (Exception e) {System.out.print(e);}
    }
	
	//enter code here
    
}
