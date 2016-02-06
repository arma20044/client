package src.main.java.admin.utils;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.login.Login;
import java.awt.FlowLayout;

public class JPanelOne extends JPanel{
	

        public JPanelOne()
        {
                // setting up black JPanel
                JPanel panel = new JPanel();
                panel.setBounds(0, 0, 56, 24);
                //panel.setPreferredSize(new Dimension(220, 40));
                //panel.setBackground(Color.BLACK);
                
              
                JLabel lblNombre = new JLabel("Nombre:");
        		lblNombre.setBounds(0, 0, 63, 14);
        		panel.add(lblNombre);
        		
        		JLabel lblNombreDescripcion = new JLabel(Login.nombreApellidoUserLogeado);
        		lblNombreDescripcion.setBounds(73, 0, 287, 14);
        		panel.add(lblNombreDescripcion);
        		
        		
        		 JPanel panel2 = new JPanel();
        		 panel2.setBounds(0, 24, 199, 24);
        		JLabel label = new JLabel("Nro. Evento:");
        		label.setBounds(0, 25, 79, 14);
        		panel2.add(label);
        		
        		JLabel label_1 = new JLabel(VentanaBuscarEvento.nroEvento);
        		label_1.setBounds(77, 25, 60, 14);
        		panel2.add(label_1);
        		label_1.setForeground(Color.BLACK);
                //setLayout(null);
        		
        		
        		
        		JLabel label_2 = new JLabel("Descripcion Evento:");
        		label_2.setBounds(0, 37, 111, 14);
        		panel2.add(label_2);
        		
        		JLabel label_3 = new JLabel(VentanaBuscarEvento.descripcionEvento);
        		label_3.setBounds(114, 37, 200, 14);
        		panel2.add(label_3);
        		label_3.setForeground(Color.BLACK);
//        		
//        		JLabel label_4 = new JLabel("Fecha Desde:");
//        		label_4.setBounds(317, 25, 83, 14);
//        		panel.add(label_4);
//        		
//        		JLabel label_5 = new JLabel(VentanaBuscarEvento.fechaDesde);
//        		label_5.setBounds(410, 25, 191, 14);
//        		panel.add(label_5);
//        		label_5.setForeground(Color.BLACK);
//        		
//        		JLabel lblFechaHasta = new JLabel("Fecha Hasta:");
//        		lblFechaHasta.setBounds(317, 37, 83, 14);
//        		panel.add(lblFechaHasta);
//        		
//        		JLabel label_7 = new JLabel(VentanaBuscarEvento.fechaHasta);
//        		label_7.setBounds(410, 37, 191, 14);
//        		panel.add(label_7);
//        		label_7.setForeground(Color.BLACK);
//        		
//        		JLabel label_8 = new JLabel("Tipo Evento:");
//        		label_8.setBounds(0, 50, 79, 14);
//        		panel.add(label_8);
//        		
//        		JLabel label_9 = new JLabel(VentanaBuscarEvento.tipoEventoDescripcon);
//        		label_9.setBounds(87, 50, 196, 14);
//        		panel.add(label_9);
//        		label_9.setForeground(Color.BLACK);

                
                add(panel);
                add(panel2);
        }
}
