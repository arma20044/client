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
import src.main.java.admin.reportes.Participacion;
import src.main.java.admin.reportes.ReporteDymmy;
import src.main.java.admin.reportes.Votantes;
import src.main.java.admin.reportes.VotoBlancoElegir;
import src.main.java.admin.reportes.VotosPorDepartamento;
import src.main.java.admin.reportes.VotosPorDistrito;
import src.main.java.admin.reportes.VotosPorLocal;
import src.main.java.admin.reportes.VotosPorZona;
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
		lblNombreDescripcion.setBounds(101, 36, 250, 14);
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
			
				btnPersonas.setBounds(133, 133, 227, 23);
				getContentPane().add(btnPersonas);
				
				JButton btnVotantesXDep = new JButton("Cantidad Votantes por Departamento");
				btnVotantesXDep.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						VotosPorDepartamento votoDepartamento = new VotosPorDepartamento();
						votoDepartamento.start();
					}
				});
				btnVotantesXDep.setBounds(391, 133, 227, 23);
				getContentPane().add(btnVotantesXDep);
				
				JButton btnCantVotantesXDistrito = new JButton("Cantidad Votantes por Distrito");
				btnCantVotantesXDistrito.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						VotosPorDistrito votoDistrito = new VotosPorDistrito();
						votoDistrito.start();
					}
				});
				btnCantVotantesXDistrito.setBounds(391, 167, 227, 23);
				getContentPane().add(btnCantVotantesXDistrito);
				
				JButton btnCantidadVotantesPorZona = new JButton("Cantidad Votantes por Zona");
				btnCantidadVotantesPorZona.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						VotosPorZona votoZona = new VotosPorZona();
						votoZona.start();
					}
				});
				btnCantidadVotantesPorZona.setBounds(391, 201, 227, 23);
				getContentPane().add(btnCantidadVotantesPorZona);
				
				JButton btnCantidadVotantesPorLocal = new JButton("Cantidad Votantes por Local");
				btnCantidadVotantesPorLocal.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						VotosPorLocal votoLocal = new VotosPorLocal();
						votoLocal.start();
					}
				});
				btnCantidadVotantesPorLocal.setBounds(391, 239, 227, 23);
				getContentPane().add(btnCantidadVotantesPorLocal);
				
				JButton btnTotalParticipacion = new JButton("Participacion");
				btnTotalParticipacion.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Participacion p = new Participacion();
						p.start();
					}
				});
				btnTotalParticipacion.setBounds(133, 201, 227, 23);
				getContentPane().add(btnTotalParticipacion);
				
				JButton btnCantidadVotosBlancos = new JButton("Cantidad Votos Blancos");
				btnCantidadVotosBlancos.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						VotoBlancoElegir v = new VotoBlancoElegir();
						v.setVisible(true);
						dispose();
					}
				});
				btnCantidadVotosBlancos.setBounds(133, 239, 227, 23);
				getContentPane().add(btnCantidadVotosBlancos);
				
				JButton btnListaCandidatos = new JButton("Lista de Candidatos");
				btnListaCandidatos.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						Candidatos can = new Candidatos();
						can.start();
					}
				});
				btnListaCandidatos.setBounds(133, 167, 227, 23);
				getContentPane().add(btnListaCandidatos);
				
				JButton btnVotosXCandidatura = new JButton("Votos por Candidatura");
				btnVotosXCandidatura.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						CantidadVotosElegir elegir = new CantidadVotosElegir();
						elegir.setVisible(true);
						dispose();
					}
				});
				btnVotosXCandidatura.setBounds(133, 276, 227, 23);
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
