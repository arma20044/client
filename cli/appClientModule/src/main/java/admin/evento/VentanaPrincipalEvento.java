package src.main.java.admin.evento;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import src.main.java.admin.Administracion;
import src.main.java.admin.Coordinador;
import src.main.java.hello.VentanaConfirmacion;

public class VentanaPrincipalEvento extends JFrame implements ActionListener{
	
	private Coordinador miCoordinador; //objeto miCoordinador que permite la relacion entre esta clase y la clase coordinador
	private JTextArea areaIntroduccion;
	private JLabel labelTitulo, labelSeleccion;
	private JButton botonRegistrar,botonBuscar;
	

	/**
	 * Establece la informacion que se presentara como introduccion del sistema
	 */
	public String textoIntroduccion = "";
	private JButton btnVolver;

	/**
	 * constructor de la clase donde se inicializan todos los componentes
	 * de la ventana principal
	 */
	public VentanaPrincipalEvento() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		botonRegistrar = new JButton();
		botonRegistrar.setBounds(50, 194, 120, 25);
		botonRegistrar.setText("Registrar");
		
		botonBuscar = new JButton();
		botonBuscar.setBounds(190, 194, 120, 25);
		botonBuscar.setText("Buscar");

		labelTitulo = new JLabel();
		labelTitulo.setText("Menú de Administración de Eventos.");
		labelTitulo.setBounds(60, 40, 380, 30);
		labelTitulo.setFont(new java.awt.Font("Verdana", 1, 15));

		labelSeleccion = new JLabel();
		labelSeleccion.setText("Escoja que operacion desea realizar");
		labelSeleccion.setBounds(60, 158, 250, 25);

		textoIntroduccion = "Esta aplicaci�n presenta un ejemplo pr�ctico del patron "
				+ "de dise�o MVC.\n\n"
				+ "La aplicaci�n permite registrar, actualizar, buscar y eliminar registros de una tabla Persona." +
				"tambien son vinculados algunos conceptos de los Patrones Value Object y Data Access Objetc\n";

		areaIntroduccion = new JTextArea();
		areaIntroduccion.setBounds(50, 90, 380, 57);
		areaIntroduccion.setEditable(false);
		areaIntroduccion.setFont(new java.awt.Font("Verdana", 0, 14));
		areaIntroduccion.setLineWrap(true);
		areaIntroduccion.setText("En esta ventana podrá realizar operaciones de ABM sobre la tabla de Eventos del Sistema.");
		areaIntroduccion.setWrapStyleWord(true);
		areaIntroduccion.setBorder(javax.swing.BorderFactory.createBevelBorder(
				javax.swing.border.BevelBorder.LOWERED, null, null, null,
				new java.awt.Color(0, 0, 0)));

		botonRegistrar.addActionListener(this);
		botonBuscar.addActionListener(this);
		getContentPane().add(botonBuscar);
		getContentPane().add(botonRegistrar);
		getContentPane().add(labelSeleccion);
		getContentPane().add(labelTitulo);
		getContentPane().add(areaIntroduccion);
	
		setSize(480, 320);
		setTitle("Sistema E-vote: Paraguay Elecciones 2015");
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		          Administracion miVentanaPrincipal;
		    		/*Instanciamos el objeto*/
		    		miVentanaPrincipal= new Administracion();
		    		/*Enviamos el objeto como parametro para que sea unico
		    		 * en toda la aplicaci�n*/
		    		//miVentanaPrincipal.setVentanaBuscar(miVentanaPrincipal);
		    		/*Hacemos que se cargue la ventana*/
		    		miVentanaPrincipal.setVisible(true);
		            dispose();
			}
		});
		btnVolver.setBounds(331, 194, 120, 25);
		getContentPane().add(btnVolver);

	}


	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador=miCoordinador;
	}


	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==botonRegistrar) {
			VentanaRegistroEvento ventanaRegistro = new VentanaRegistroEvento();
			ventanaRegistro.setVisible(true);
		}
		if (e.getSource()==botonBuscar) {
			VentanaBuscarEvento ventanaBuscar = new VentanaBuscarEvento();
			ventanaBuscar.setVisible(true);		
		}
	}
}
