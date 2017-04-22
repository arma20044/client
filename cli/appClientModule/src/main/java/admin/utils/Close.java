package src.main.java.admin.utils;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Close {

	
	public void cerrarAplicacion(WindowEvent we){
		
		
    	
		
		        String ObjButtons[] = {"Sí","No"};
		         
		       
		        int PromptResult = JOptionPane.showOptionDialog(null,"Desea Salir?","Sistema E-vote: Paraguay Elecciones 2015.",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,ObjButtons,ObjButtons[1]);
		        if(PromptResult==JOptionPane.YES_OPTION)
		        {
		            System.exit(0);
		        }
		        else{
		        	we.getWindow().setVisible(true);
		        }
		    }
		
	
}
