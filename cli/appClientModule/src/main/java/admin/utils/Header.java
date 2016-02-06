package src.main.java.admin.utils;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.login.Login;

public class Header extends JPanel {
	private JPanel panel;
	private JLabel label;
	private JLabel label_1;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel label_4;
	private JLabel label_5;
	private JLabel label_6;
	private JLabel label_7;
	private JLabel label_8;
	private JLabel label_9;
	private JLabel label_10;
	private JLabel label_11;

	/**
	 * Create the panel.
	 */
	public Header() {
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
		panel.setBounds(38, 0, 619, 85);
		//getContentPane().add(panel);
		
		label = new JLabel("Nombre:");
		label.setBounds(0, 0, 63, 14);
		panel.add(label);
		
		label_1 = new JLabel(Login.nombreApellidoUserLogeado);
		label_1.setBounds(73, 0, 287, 14);
		panel.add(label_1);
		
		label_2 = new JLabel("Nro. Evento:");
		label_2.setBounds(0, 25, 79, 14);
		panel.add(label_2);
		
		label_3 = new JLabel(VentanaBuscarEvento.nroEvento);
		label_3.setForeground(Color.BLACK);
		label_3.setBounds(77, 25, 60, 14);
		panel.add(label_3);
		
		label_4 = new JLabel("Descripcion Evento:");
		label_4.setBounds(0, 37, 111, 14);
		panel.add(label_4);
		
		label_5 = new JLabel(VentanaBuscarEvento.descripcionEvento);
		label_5.setForeground(Color.BLACK);
		label_5.setBounds(114, 37, 200, 14);
		panel.add(label_5);
		
		label_6 = new JLabel("Fecha Desde:");
		label_6.setBounds(317, 25, 83, 14);
		panel.add(label_6);
		
		label_7 = new JLabel(VentanaBuscarEvento.fechaDesde);
		label_7.setForeground(Color.BLACK);
		label_7.setBounds(410, 25, 191, 14);
		panel.add(label_7);
		
		label_8 = new JLabel("Fecha Hasta:");
		label_8.setBounds(317, 37, 83, 14);
		panel.add(label_8);
		
		label_9 = new JLabel(VentanaBuscarEvento.fechaHasta);
		label_9.setForeground(Color.BLACK);
		label_9.setBounds(410, 37, 191, 14);
		panel.add(label_9);
		
		label_10 = new JLabel("Tipo Evento:");
		label_10.setBounds(0, 50, 79, 14);
		panel.add(label_10);
		
		label_11 = new JLabel(VentanaBuscarEvento.tipoEventoDescripcon);
		label_11.setForeground(Color.BLACK);
		label_11.setBounds(87, 50, 196, 14);
		panel.add(label_11);
	}

}
