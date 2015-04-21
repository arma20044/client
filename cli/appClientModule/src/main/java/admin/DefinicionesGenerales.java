package src.main.java.admin;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import scr.main.java.admin.distrito.VentanaBuscarDistrito;
import src.main.java.admin.candidato.VentanaBuscarCandidato;
import src.main.java.admin.departamento.VentanaBuscarDepartamento;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.genero.VentanaBuscarGenero;
import src.main.java.admin.listas.VentanaBuscarLista;
import src.main.java.admin.local.VentanaBuscarLocal;
import src.main.java.admin.pais.VentanaBuscarPais;
import src.main.java.admin.tipoCandidato.VentanaBuscarTipoCandidato;
import src.main.java.admin.tipoEvento.VentanaBuscarTipoEvento;
import src.main.java.admin.zona.VentanaBuscarZona;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;

import javax.swing.ImageIcon;

public class DefinicionesGenerales extends JFrame implements ActionListener{
	
	private Coordinador miCoordinador; //objeto miCoordinador que permite la relacion entre esta clase y la clase coordinador
	private JLabel labelTitulo, labelSeleccion;
	private JButton btnAtras;
	
	

	/**
	 * Establece la informacion que se presentara como introduccion del sistema
	 */
	public String textoIntroduccion = "";
	private JButton btnTiposDeCandidatos;
	
	public DefinicionesGenerales() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		btnAtras = new JButton();
		btnAtras.setIcon(new ImageIcon(DefinicionesGenerales.class.getResource("/imgs/volver.png")));
		btnAtras.setToolTipText("Volver al Menu Principal");
		btnAtras.setBounds(124, 410, 83, 51);

		labelSeleccion = new JLabel();
		labelSeleccion.setText("Escoja que operacion desea realizar");
		labelSeleccion.setBounds(93, 374, 250, 25);

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
		
		JButton btnGenero = new JButton("Generos");
		btnGenero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentanaBuscarGenero genero = new VentanaBuscarGenero();
				genero.setVisible(true);
				dispose();
			}
		});
		btnGenero.setBounds(93, 146, 155, 23);
		getContentPane().add(btnGenero);
		
		btnTiposDeCandidatos = new JButton("Tipos de Candidatos");
		btnTiposDeCandidatos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentanaBuscarTipoCandidato tipoCandidato = new VentanaBuscarTipoCandidato();
				tipoCandidato.setVisible(true);
	            dispose();
			}
		});
		btnTiposDeCandidatos.setBounds(93, 180, 157, 23);
		getContentPane().add(btnTiposDeCandidatos);
		
		JButton btnPais = new JButton("Paises");
		btnPais.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentanaBuscarPais pais = new VentanaBuscarPais();
				pais.setVisible(true);
	            dispose();
			}
		});
		btnPais.setBounds(93, 214, 155, 23);
		getContentPane().add(btnPais);
		
		JButton btnListas = new JButton("Listas");
		btnListas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentanaBuscarLista lista = new VentanaBuscarLista();
				lista.setVisible(true);
	            dispose();
			}
		});
		btnListas.setBounds(93, 248, 155, 23);
		getContentPane().add(btnListas);
		
		JButton btnPersonas = new JButton("Personas");
		btnPersonas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				src.main.java.admin.persona.VentanaPrincipal miVentanaPrincipal;
				miVentanaPrincipal= new src.main.java.admin.persona.VentanaPrincipal();
	    		/*Enviamos el objeto como parametro para que sea unico
	    		 * en toda la aplicaci�n*/
	    		//miVentanaPrincipal.setVentanaBuscar(miVentanaPrincipal);
	    		/*Hacemos que se cargue la ventana*/
	    		miVentanaPrincipal.setVisible(true);
	            dispose();
	            
			}
		});
		btnPersonas.setBounds(308, 142, 146, 23);
		getContentPane().add(btnPersonas);
		
		JButton btnDepartamentos = new JButton("Departamentos");
		btnDepartamentos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentanaBuscarDepartamento departamento = new VentanaBuscarDepartamento();
				departamento.setVisible(true);
				dispose();
			}
		});
		btnDepartamentos.setBounds(308, 180, 146, 23);
		getContentPane().add(btnDepartamentos);
		
		JButton btnDistritos = new JButton("Distritos");
		btnDistritos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentanaBuscarDistrito distrito = new VentanaBuscarDistrito();
				distrito.setVisible(true);
				dispose();
			}
		});
		btnDistritos.setBounds(308, 214, 146, 23);
		getContentPane().add(btnDistritos);
		
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
		
				labelTitulo = new JLabel();
				labelTitulo.setIcon(new ImageIcon(DefinicionesGenerales.class.getResource("/imgs/def.png")));
				labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
				labelTitulo.setText("Definiciones Generales");
				labelTitulo.setBounds(44, 59, 712, 86);
				labelTitulo.setFont(new Font("Verdana", Font.BOLD, 46));
				getContentPane().add(labelTitulo);
				
				JButton btnCandidatos = new JButton("Candidatos");
				btnCandidatos.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						VentanaBuscarCandidato candidato;
						candidato = new VentanaBuscarCandidato();
						candidato.setVisible(true);
						dispose();
						
					}
				});
				btnCandidatos.setBounds(308, 248, 146, 23);
				getContentPane().add(btnCandidatos);
				
				JButton btnZonas = new JButton("Zonas");
				btnZonas.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						VentanaBuscarZona zona = new VentanaBuscarZona();
						zona.setVisible(true);
						dispose();
					}
				});
				btnZonas.setBounds(480, 142, 176, 23);
				getContentPane().add(btnZonas);
				
				JButton btnLocal = new JButton("Local");
				btnLocal.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						VentanaBuscarLocal local = new VentanaBuscarLocal();
						local.setVisible(true);
						dispose();
					}
				});
				btnLocal.setBounds(480, 180, 176, 23);
				getContentPane().add(btnLocal);
				
				JButton btnCiudad = new JButton("Ciudad");
				btnCiudad.setBounds(480, 214, 176, 23);
				getContentPane().add(btnCiudad);
				
				JButton btnVotantesHabilitados = new JButton("Votantes Habilitados");
				btnVotantesHabilitados.setBounds(480, 248, 176, 23);
				getContentPane().add(btnVotantesHabilitados);
				
				JButton btnTipoEvento = new JButton("Tipo Evento");
				btnTipoEvento.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						VentanaBuscarTipoEvento tevento;
						tevento = new VentanaBuscarTipoEvento();
						tevento.setVisible(true);
						dispose();
					}
				});
				btnTipoEvento.setBounds(308, 282, 146, 23);
				getContentPane().add(btnTipoEvento);
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
