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
import src.main.java.admin.evento.VentanaMainEvento;
import src.main.java.admin.genero.VentanaBuscarGenero;
import src.main.java.admin.listas.VentanaBuscarLista;
import src.main.java.admin.local.VentanaBuscarLocal;
import src.main.java.admin.mesa.VentanaBuscarMesa;
import src.main.java.admin.nacionalidad.VentanaBuscarNacionalidad;
import src.main.java.admin.pais.VentanaBuscarPais;
import src.main.java.admin.persona.VentanaBuscarPersona;
import src.main.java.admin.tipoCandidato.VentanaBuscarTipoCandidato;
import src.main.java.admin.tipoEvento.VentanaBuscarTipoEvento;
import src.main.java.admin.vigencia.VentanaBuscarVigencia;
import src.main.java.admin.votantesHabilitados.VentanaBuscarVotantesHabilitados;
import src.main.java.admin.zona.VentanaBuscarZona;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;

import javax.swing.ImageIcon;

import java.awt.Color;

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
		btnAtras.setBounds(104, 474, 83, 51);

		labelSeleccion = new JLabel();
		labelSeleccion.setText("Escoja que operacion desea realizar");
		labelSeleccion.setBounds(58, 450, 250, 25);

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
		
		JButton btnGenero = new JButton("Generos");
		btnGenero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentanaBuscarGenero genero = new VentanaBuscarGenero();
				genero.setVisible(true);
				dispose();
			}
		});
		btnGenero.setBounds(73, 264, 155, 23);
		getContentPane().add(btnGenero);
		
		btnTiposDeCandidatos = new JButton("Tipos de Candidatos");
		btnTiposDeCandidatos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentanaBuscarTipoCandidato tipoCandidato = new VentanaBuscarTipoCandidato();
				tipoCandidato.setVisible(true);
	            dispose();
			}
		});
		btnTiposDeCandidatos.setBounds(73, 298, 157, 23);
		getContentPane().add(btnTiposDeCandidatos);
		
		JButton btnPais = new JButton("Paises");
		btnPais.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentanaBuscarPais pais = new VentanaBuscarPais();
				pais.setVisible(true);
	            dispose();
			}
		});
		btnPais.setBounds(73, 332, 155, 23);
		getContentPane().add(btnPais);
		
		JButton btnListas = new JButton("Listas");
		btnListas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentanaBuscarLista lista = new VentanaBuscarLista();
				lista.setVisible(true);
	            dispose();
			}
		});
		btnListas.setBounds(73, 366, 155, 23);
		getContentPane().add(btnListas);
		
		JButton btnPersonas = new JButton("Personas");
		btnPersonas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaBuscarPersona persona = new VentanaBuscarPersona();
				persona.setVisible(true);
	            dispose();
	            
			}
		});
		btnPersonas.setBounds(288, 260, 146, 23);
		getContentPane().add(btnPersonas);
		
		JButton btnDepartamentos = new JButton("Departamentos");
		btnDepartamentos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentanaBuscarDepartamento departamento = new VentanaBuscarDepartamento();
				departamento.setVisible(true);
				dispose();
			}
		});
		btnDepartamentos.setBounds(288, 298, 146, 23);
		getContentPane().add(btnDepartamentos);
		
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
				labelTitulo.setBounds(24, 177, 712, 86);
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
				btnCandidatos.setBounds(288, 366, 146, 23);
				getContentPane().add(btnCandidatos);
				
				JButton btnVotantesHabilitados = new JButton("Votantes Habilitados");
				btnVotantesHabilitados.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						VentanaBuscarVotantesHabilitados votantesHabilitados = new VentanaBuscarVotantesHabilitados();
						votantesHabilitados.setVisible(true);
						dispose();
					}
				});
				btnVotantesHabilitados.setBounds(460, 264, 176, 23);
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
				btnTipoEvento.setBounds(288, 332, 146, 23);
				getContentPane().add(btnTipoEvento);
				
				JLabel label = new JLabel("Nro. Evento:");
				label.setBounds(10, 77, 94, 14);
				getContentPane().add(label);
				
				JLabel label_1 = new JLabel(VentanaMainEvento.nroEvento);
				label_1.setForeground(Color.RED);
				label_1.setBounds(161, 77, 214, 14);
				getContentPane().add(label_1);
				
				JLabel label_2 = new JLabel("Descripcion Evento:");
				label_2.setBounds(10, 102, 129, 14);
				getContentPane().add(label_2);
				
				JLabel label_3 = new JLabel(VentanaMainEvento.descripcionEvento);
				label_3.setForeground(Color.RED);
				label_3.setBounds(161, 102, 214, 14);
				getContentPane().add(label_3);
				
				JLabel label_4 = new JLabel("Fecha Desde:");
				label_4.setBounds(419, 77, 94, 14);
				getContentPane().add(label_4);
				
				JLabel label_5 = new JLabel(VentanaMainEvento.fechaDesde);
				label_5.setForeground(Color.RED);
				label_5.setBounds(552, 77, 214, 14);
				getContentPane().add(label_5);
				
				JLabel label_6 = new JLabel("Fecha Hasta");
				label_6.setBounds(419, 108, 94, 14);
				getContentPane().add(label_6);
				
				JLabel label_7 = new JLabel(VentanaMainEvento.fechaHasta);
				label_7.setForeground(Color.RED);
				label_7.setBounds(552, 108, 214, 14);
				getContentPane().add(label_7);
				
				JLabel label_8 = new JLabel("Tipo Evento:");
				label_8.setBounds(10, 138, 94, 14);
				getContentPane().add(label_8);
				
				JLabel label_9 = new JLabel(VentanaMainEvento.tipoEventoDescripcon);
				label_9.setForeground(Color.RED);
				label_9.setBounds(96, 138, 214, 14);
				getContentPane().add(label_9);
				
				JButton btnVigenciaHorarioXPais = new JButton("Vigencia Horario por Pais");
				btnVigenciaHorarioXPais.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						VentanaBuscarVigencia vigencia = new VentanaBuscarVigencia();
						vigencia.setVisible(true);
						dispose();
					}
				});
				btnVigenciaHorarioXPais.setBounds(73, 400, 155, 23);
				getContentPane().add(btnVigenciaHorarioXPais);
				
				JButton btnNacionalidades = new JButton("Nacionalidades\r\n");
				btnNacionalidades.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						VentanaBuscarNacionalidad nacionalidad = new VentanaBuscarNacionalidad();
						nacionalidad.setVisible(true);
						dispose();
					}
				});
				btnNacionalidades.setBounds(460, 298, 176, 23);
				getContentPane().add(btnNacionalidades);
		//lblNombreDescripcion.repaint();
		
		
		
		

	}

	


	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador=miCoordinador;
	}


	public void actionPerformed(ActionEvent arg0) {
		VentanaMainEvento mainEvento = new VentanaMainEvento();
		mainEvento.setVisible(true);
		dispose();
		// TODO Auto-generated method stub
		
	}
}
