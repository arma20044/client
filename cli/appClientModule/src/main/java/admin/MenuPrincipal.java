package src.main.java.admin;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.login.Login;

import javax.swing.ImageIcon;

public class MenuPrincipal extends JFrame implements ActionListener{
	
	private Coordinador miCoordinador; //objeto miCoordinador que permite la relacion entre esta clase y la clase coordinador
	
	public static boolean reporte;

	/**
	 * Establece la informacion que se presentara como introduccion del sistema
	 */
	public String textoIntroduccion = "";
	
	public MenuPrincipal() {
		
		reporte = false;
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		textoIntroduccion = "Esta aplicaci�n presenta un ejemplo pr�ctico del patron "
				+ "de dise�o MVC.\n\n"
				+ "La aplicaci�n permite registrar, actualizar, buscar y eliminar registros de una tabla Persona." +
				"tambien son vinculados algunos conceptos de los Patrones Value Object y Data Access Objetc\n";
	
		setSize(575, 460);
		setTitle("Sistema E-vote: Paraguay Elecciones 2015");
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		JLabel lblAdmin = new JLabel("Administrador");
		lblAdmin.setBounds(24, 11, 89, 14);
		getContentPane().add(lblAdmin);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(24, 35, 54, 14);
		getContentPane().add(lblNombre);
		
		JLabel lblNombreDescripcion = new JLabel(Login.nombreApellidoUserLogeado);
		lblNombreDescripcion.setBounds(73, 36, 278, 14);
		//lblNombreDescripcion.setText();
		getContentPane().add(lblNombreDescripcion);
		
		JButton btnAdministracion = new JButton("Administracion");
		btnAdministracion.setHorizontalAlignment(SwingConstants.LEFT);
		btnAdministracion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Administracion administracion = new Administracion();
				administracion.setVisible(true);
				dispose();
			}
		});
		btnAdministracion.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/imgs/administracion.png")));
		btnAdministracion.setFont(new Font("Tahoma", Font.PLAIN, 21));
		btnAdministracion.setBounds(122, 169, 338, 73);
		getContentPane().add(btnAdministracion);
		
		JLabel lblTitulo = new JLabel();
		lblTitulo.setText("Menú Principal");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Verdana", Font.BOLD, 46));
		lblTitulo.setBounds(24, 72, 503, 86);
		getContentPane().add(lblTitulo);
		
		JButton btnReportes = new JButton("Reportes");
		btnReportes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				reporte = true;
				VentanaBuscarEvento ventanaBuscarEvento = new VentanaBuscarEvento();
				ventanaBuscarEvento.setVisible(true);
				dispose();
				
			}
		});
		btnReportes.setHorizontalAlignment(SwingConstants.LEFT);
		btnReportes.setIcon(new ImageIcon(MenuPrincipal.class.getResource("/imgs/pfd64.png")));
		btnReportes.setFont(new Font("Tahoma", Font.PLAIN, 21));
		btnReportes.setBounds(122, 282, 338, 73);
		getContentPane().add(btnReportes);
		//lblNombreDescripcion.repaint();
		
		
		
		

	}




	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador=miCoordinador;
	}


	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
