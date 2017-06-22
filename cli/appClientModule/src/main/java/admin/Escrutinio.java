package src.main.java.admin;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import entity.UcsawsEvento;
import src.main.java.admin.escrutinio.VentanaEscrutinioDiputados;
import src.main.java.admin.escrutinio.VentanaEscrutinioPresidente;
import src.main.java.admin.escrutinio.VentanaEscrutinioSenadores;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.login.Login;
import org.jdesktop.swingx.JXDatePicker;

public class Escrutinio extends JFrame implements ActionListener{
	
	private Coordinador miCoordinador; //objeto miCoordinador que permite la relacion entre esta clase y la clase coordinador
	private JLabel labelTitulo, labelSeleccion;
	private JButton btnAtras;
	
	

	/**
	 * Establece la informacion que se presentara como introduccion del sistema
	 */
	public String textoIntroduccion = "";
	
	public Escrutinio() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		btnAtras = new JButton();
		btnAtras.setIcon(new ImageIcon(Escrutinio.class.getResource("/imgs/volver.png")));
		btnAtras.setToolTipText("Atras...");
		btnAtras.setBounds(683, 474, 83, 51);

		labelSeleccion = new JLabel();
		labelSeleccion.setFont(new Font("Tahoma", Font.ITALIC, 11));
		labelSeleccion.setText("Escoja que operacion desea realizar");
		labelSeleccion.setBounds(272, 318, 172, 14);

		textoIntroduccion = "Esta aplicaci�n presenta un ejemplo pr�ctico del patron "
				+ "de dise�o MVC.\n\n"
				+ "La aplicaci�n permite registrar, actualizar, buscar y eliminar registros de una tabla Persona." +
				"tambien son vinculados algunos conceptos de los Patrones Value Object y Data Access Objetc\n";

		btnAtras.addActionListener(this);
		getContentPane().add(btnAtras);
		getContentPane().add(labelSeleccion);
	
		setSize(772, 554);
		setTitle("Sistema E-vote: Paraguay Elecciones 2015");
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		JButton btnContarVotosDip = new JButton("Contar Votos Diputados");
		btnContarVotosDip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentanaEscrutinioDiputados escr = new VentanaEscrutinioDiputados();
				escr.setVisible(true);
				dispose();
			}
		});
		btnContarVotosDip.setBounds(259, 267, 185, 23);
		getContentPane().add(btnContarVotosDip);
		
				labelTitulo = new JLabel();
				labelTitulo.setIcon(new ImageIcon(Escrutinio.class.getResource("/imgs/def.png")));
				labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
				labelTitulo.setText("Escrutinio");
				labelTitulo.setBounds(10, 96, 712, 86);
				labelTitulo.setFont(new Font("Verdana", Font.BOLD, 46));
				getContentPane().add(labelTitulo);
				
				JButton btnHome = new JButton("");
				btnHome.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						MenuPrincipal menuprincipal = new MenuPrincipal();
						menuprincipal.setVisible(true);
						dispose();
					}
				});
				btnHome.setSelectedIcon(new ImageIcon(Escrutinio.class.getResource("/imgs/home.png")));
				btnHome.setToolTipText("Inicio");
				btnHome.setBounds(0, 0, 32, 32);
				getContentPane().add(btnHome);
				btnHome.setIcon(new ImageIcon(VentanaBuscarEvento.class
						.getResource("/imgs/home.png")));
				btnHome.setBounds(0, 0, 32, 32);
				Image img5 = ((ImageIcon) btnHome.getIcon()).getImage();
				Image newimg5 = img5.getScaledInstance(32, 32,
						java.awt.Image.SCALE_SMOOTH);
				btnHome.setIcon(new ImageIcon(newimg5));
				getContentPane().add(btnHome);
				
				JPanel panel = new JPanel();
				panel.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
				panel.setBounds(49, 0, 619, 85);
				getContentPane().add(panel);
				panel.setLayout(null);
				
				JLabel lblNombre = new JLabel("Nombre:");
				lblNombre.setBounds(0, 0, 63, 14);
				panel.add(lblNombre);
				
				JLabel lblNombreDescripcion = new JLabel(Login.nombreApellidoUserLogeado);
				lblNombreDescripcion.setBounds(73, 0, 287, 14);
				panel.add(lblNombreDescripcion);
				
				JLabel label = new JLabel("Nro. Evento:");
				label.setBounds(0, 25, 79, 14);
				panel.add(label);
				
				JLabel label_1 = new JLabel(VentanaBuscarEvento.nroEvento);
				label_1.setBounds(77, 25, 60, 14);
				panel.add(label_1);
				label_1.setForeground(Color.BLACK);
				
				JLabel label_2 = new JLabel("Descripcion Evento:");
				label_2.setBounds(0, 37, 111, 14);
				panel.add(label_2);
				
				JLabel label_3 = new JLabel(VentanaBuscarEvento.descripcionEvento);
				label_3.setBounds(114, 37, 200, 14);
				panel.add(label_3);
				label_3.setForeground(Color.BLACK);
				
				JLabel label_4 = new JLabel("Fecha Desde:");
				label_4.setBounds(317, 25, 83, 14);
				panel.add(label_4);
				
				SimpleDateFormat dt1 = new SimpleDateFormat("dd-mm-yyyy");
				 
				
				JLabel label_5 = new JLabel(dt1.format(VentanaBuscarEvento.eventoClase.getFchDesde()));
				label_5.setBounds(410, 25, 191, 14);
				panel.add(label_5);
				label_5.setForeground(Color.BLACK);
				
				JLabel lblFechaHasta = new JLabel("Fecha Hasta:");
				lblFechaHasta.setBounds(317, 37, 83, 14);
				panel.add(lblFechaHasta);
				
				JLabel label_7 = new JLabel(dt1.format(VentanaBuscarEvento.eventoClase.getFchHasta()));
				label_7.setBounds(410, 37, 191, 14);
				panel.add(label_7);
				label_7.setForeground(Color.BLACK);
				
				JLabel label_8 = new JLabel("Tipo Evento:");
				label_8.setBounds(0, 50, 79, 14);
				panel.add(label_8);
				
				JLabel label_9 = new JLabel(VentanaBuscarEvento.tipoEventoDescripcon);
				label_9.setBounds(87, 50, 196, 14);
				panel.add(label_9);
				label_9.setForeground(Color.BLACK);
				
				JButton btnContarVotosSen = new JButton("Contar Votos Senadores");
				btnContarVotosSen.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						VentanaEscrutinioSenadores escr = new VentanaEscrutinioSenadores();
						escr.setVisible(true);
						dispose();
					}
				});
				btnContarVotosSen.setBounds(259, 233, 185, 23);
				getContentPane().add(btnContarVotosSen);
				
				JButton btnContarVotosPre = new JButton("Contar Votos Presidente");
				btnContarVotosPre.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						VentanaEscrutinioPresidente escr = new VentanaEscrutinioPresidente();
						escr.setVisible(true);
						dispose();
					}
				});
				btnContarVotosPre.setBounds(259, 199, 185, 23);
				getContentPane().add(btnContarVotosPre);
		//lblNombreDescripcion.repaint();
		
		
		
		

	}

	


	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador=miCoordinador;
	}


	public void actionPerformed(ActionEvent arg0) {
		DefinicionesGenerales def = new DefinicionesGenerales();
		def.setVisible(true);
		dispose();
		// TODO Auto-generated method stub
		
	}
}
