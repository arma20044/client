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
import src.main.java.admin.reportes.Candidatos;
import src.main.java.admin.reportes.CantidadVotosElegir;
import src.main.java.admin.reportes.ReporteDymmy;
import src.main.java.admin.reportes.Votantes;
import src.main.java.login.Login;

public class Reportes extends JFrame implements ActionListener{
	
	private Coordinador miCoordinador; //objeto miCoordinador que permite la relacion entre esta clase y la clase coordinador
	private JLabel labelTitulo, labelSeleccion;
	private JButton btnAtras;
	
	

	/**
	 * Establece la informacion que se presentara como introduccion del sistema
	 */
	public String textoIntroduccion = "";
	
	public Reportes() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		btnAtras = new JButton();
		btnAtras.setIcon(new ImageIcon(Reportes.class.getResource("/imgs/volver.png")));
		btnAtras.setToolTipText("Volver al Menu Principal");
		btnAtras.setBounds(114, 421, 83, 51);

		labelSeleccion = new JLabel();
		labelSeleccion.setText("Escoja que operacion desea realizar");
		labelSeleccion.setBounds(73, 385, 250, 25);

		textoIntroduccion = "Esta aplicaci�n presenta un ejemplo pr�ctico del patron "
				+ "de dise�o MVC.\n\n"
				+ "La aplicaci�n permite registrar, actualizar, buscar y eliminar registros de una tabla Persona." +
				"tambien son vinculados algunos conceptos de los Patrones Value Object y Data Access Objetc\n";

		btnAtras.addActionListener(this);
		getContentPane().add(btnAtras);
		getContentPane().add(labelSeleccion);
	
		setSize(772, 501);
		setTitle("Sistema E-vote: Paraguay Elecciones 2015");
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		JLabel lblAdmin = new JLabel("Administrador");
		lblAdmin.setBounds(55, 11, 89, 14);
		getContentPane().add(lblAdmin);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(55, 35, 54, 14);
		getContentPane().add(lblNombre);
		
		JLabel lblNombreDescripcion = new JLabel(Login.nombreApellidoUserLogeado);
		lblNombreDescripcion.setBounds(73, 36, 278, 14);
		//lblNombreDescripcion.setText();
		getContentPane().add(lblNombreDescripcion);
		
				labelTitulo = new JLabel();
				labelTitulo.setIcon(new ImageIcon(Reportes.class.getResource("/imgs/pfd64.png")));
				labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
				labelTitulo.setText("Reportes");
				labelTitulo.setBounds(24, 36, 647, 86);
				labelTitulo.setFont(new Font("Verdana", Font.BOLD, 46));
				getContentPane().add(labelTitulo);
				
				JButton btnPersonas = new JButton("Votantes");
				btnPersonas.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						Votantes votantes = new Votantes();
						votantes.start();
					}
				});
			
				btnPersonas.setBounds(113, 133, 173, 23);
				getContentPane().add(btnPersonas);
				
				JButton btnCantvotosPorCategoria = new JButton("Votos por Categoria");
				btnCantvotosPorCategoria.setBounds(422, 141, 269, 23);
				getContentPane().add(btnCantvotosPorCategoria);
				
				JButton btnCantVotantesTotal = new JButton("Cantidad Total de Votantes");
				btnCantVotantesTotal.setBounds(422, 175, 269, 23);
				getContentPane().add(btnCantVotantesTotal);
				
				JButton btnVotantesXDep = new JButton("Cantidad Votantes por Departamento");
				btnVotantesXDep.setBounds(422, 209, 269, 23);
				getContentPane().add(btnVotantesXDep);
				
				JButton btnCantVotantesXDistrito = new JButton("Cantidad Votantes por Distrito");
				btnCantVotantesXDistrito.setBounds(422, 243, 269, 23);
				getContentPane().add(btnCantVotantesXDistrito);
				
				JButton btnCantidadVotantesPorZona = new JButton("Cantidad Votantes por Zona");
				btnCantidadVotantesPorZona.setBounds(422, 281, 269, 23);
				getContentPane().add(btnCantidadVotantesPorZona);
				
				JButton btnCantidadVotantesPorLocal = new JButton("Cantidad Votantes por Local");
				btnCantidadVotantesPorLocal.setBounds(422, 314, 269, 23);
				getContentPane().add(btnCantidadVotantesPorLocal);
				
				JButton btnTotalParticipacion = new JButton("Participacion");
				btnTotalParticipacion.setBounds(113, 175, 173, 23);
				getContentPane().add(btnTotalParticipacion);
				
				JButton btnCantidadVotosNulos = new JButton("Cantidad Votos Nulos");
				btnCantidadVotosNulos.setBounds(114, 244, 173, 23);
				getContentPane().add(btnCantidadVotosNulos);
				
				JButton btnCantidadVotosBlancos = new JButton("Cantidad Votos Blancos");
				btnCantidadVotosBlancos.setBounds(114, 281, 173, 23);
				getContentPane().add(btnCantidadVotosBlancos);
				
				JButton btnListaCandidatos = new JButton("Lista de Candidatos");
				btnListaCandidatos.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						Candidatos can = new Candidatos();
						can.start();
					}
				});
				btnListaCandidatos.setBounds(114, 209, 173, 23);
				getContentPane().add(btnListaCandidatos);
				
				JButton btnVotosXCandidatura = new JButton("Votos por Candidatura");
				btnVotosXCandidatura.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						CantidadVotosElegir elegir = new CantidadVotosElegir();
						elegir.setVisible(true);
						dispose();
					}
				});
				btnVotosXCandidatura.setBounds(114, 314, 173, 23);
				getContentPane().add(btnVotosXCandidatura);
				
				JButton btnHome = new JButton("");
				btnHome.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						MenuPrincipal menuprincipal = new MenuPrincipal();
						menuprincipal.setVisible(true);
						dispose();
					}
				});
				btnHome.setIcon(new ImageIcon(Reportes.class.getResource("/imgs/home.png")));
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
		//lblNombreDescripcion.repaint();
		
		
		
		

	}

	


	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador=miCoordinador;
	}


	public void actionPerformed(ActionEvent arg0) {
		MenuPrincipal menu = new MenuPrincipal();
		menu.setVisible(true);
		dispose();
		// TODO Auto-generated method stub
		
	}
}
