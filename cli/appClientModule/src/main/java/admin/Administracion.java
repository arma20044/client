package src.main.java.admin;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.login.Login;

public class Administracion extends JFrame implements ActionListener {

	private Coordinador miCoordinador; // objeto miCoordinador que permite la
										// relacion entre esta clase y la clase
										// coordinador
	private JLabel labelTitulo, labelSeleccion;
	private JButton btnAtras;

	/**
	 * Establece la informacion que se presentara como introduccion del sistema
	 */
	public String textoIntroduccion = "";

	public Administracion() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		btnAtras = new JButton();
		btnAtras.setMnemonic('b');
		btnAtras.setIcon(new ImageIcon(Administracion.class
				.getResource("/imgs/volver.png")));
		btnAtras.setToolTipText("Volver al Menu Principal");
		btnAtras.setBounds(112, 410, 83, 51);

		labelSeleccion = new JLabel();
		labelSeleccion.setText("Escoja que operacion desea realizar");
		labelSeleccion.setBounds(81, 376, 250, 25);

		textoIntroduccion = "Esta aplicaci�n presenta un ejemplo pr�ctico del patron "
				+ "de dise�o MVC.\n\n"
				+ "La aplicaci�n permite registrar, actualizar, buscar y eliminar registros de una tabla Persona."
				+ "tambien son vinculados algunos conceptos de los Patrones Value Object y Data Access Objetc\n";

		btnAtras.addActionListener(this);
		getContentPane().add(btnAtras);
		getContentPane().add(labelSeleccion);

		setSize(772, 501);
		setTitle("Sistema E-vote: Paraguay Elecciones 2015");
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		JButton btnEvento = new JButton("Eventos");
		btnEvento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				VentanaBuscarEvento ventanaBuscarEvento = new VentanaBuscarEvento();
				ventanaBuscarEvento.setVisible(true);
				dispose();
			}
		});
		btnEvento.setBounds(190, 181, 101, 23);
		getContentPane().add(btnEvento);

		JLabel lblAdmin = new JLabel("Administrador");
		lblAdmin.setBounds(46, 0, 89, 14);
		getContentPane().add(lblAdmin);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(46, 25, 54, 14);
		getContentPane().add(lblNombre);

		JLabel lblNombreDescripcion = new JLabel(
				Login.nombreApellidoUserLogeado);
		lblNombreDescripcion.setBounds(59, 25, 278, 14);
		// lblNombreDescripcion.setText();
		getContentPane().add(lblNombreDescripcion);

		labelTitulo = new JLabel();
		labelTitulo.setIcon(new ImageIcon(Administracion.class
				.getResource("/imgs/administracion.png")));
		labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitulo.setText("Administración");
		labelTitulo.setBounds(44, 59, 647, 86);
		labelTitulo.setFont(new Font("Verdana", Font.BOLD, 46));
		getContentPane().add(labelTitulo);
		
		JButton btnHome = new JButton("");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuPrincipal menuprincipal = new MenuPrincipal();
				menuprincipal.setVisible(true);
				dispose();
			}
		});
		btnHome.setIcon(new ImageIcon(Administracion.class.getResource("/imgs/home.png")));
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
		// lblNombreDescripcion.repaint();

	}

	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador = miCoordinador;
	}

	public void actionPerformed(ActionEvent arg0) {
		MenuPrincipal menu = new MenuPrincipal();
		menu.setVisible(true);
		dispose();
		// TODO Auto-generated method stub

	}
}
