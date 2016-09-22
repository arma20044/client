package src.main.java.admin;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
import src.main.java.admin.roles.VentanaBuscarRoles;
import src.main.java.admin.tipoCandidato.VentanaBuscarTipoCandidato;
import src.main.java.admin.tipoEvento.VentanaBuscarTipoEvento;
import src.main.java.admin.tipoLista.VentanaBuscarTipoLista;
import src.main.java.admin.users.VentanaBuscarUsers;
import src.main.java.admin.vigencia.VentanaBuscarVigencia;
import src.main.java.admin.votantesHabilitados.VentanaBuscarVotantesHabilitados;
import src.main.java.admin.zona.VentanaBuscarZona;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;

import javax.swing.ImageIcon;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.UIManager;

public class DefinicionesGenerales extends JFrame implements ActionListener {

	private Coordinador miCoordinador; // objeto miCoordinador que permite la
										// relacion entre esta clase y la clase
										// coordinador
	private JLabel labelTitulo, labelSeleccion;
	private JButton btnAtras;
	private JButton btnGenero, btnPersonas, btnVotantesHabilitados,
			btnVigenciaHorarioXPais, btnDepartamentos, btnNacionalidades,
			btnPais, btnTipoEvento, btnTipoLista, btnListas, btnCandidatos,
			btnEscrutinio,btnHome;

	/**
	 * Establece la informacion que se presentara como introduccion del sistema
	 */
	public String textoIntroduccion = "";
	private JButton btnRoles;

	public DefinicionesGenerales() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		btnAtras = new JButton();
		btnAtras.setIcon(new ImageIcon(DefinicionesGenerales.class
				.getResource("/imgs/volver.png")));
		btnAtras.setToolTipText("Atras...");
		btnAtras.setBounds(683, 474, 83, 51);

		labelSeleccion = new JLabel();
		labelSeleccion.setFont(new Font("Tahoma", Font.ITALIC, 11));
		labelSeleccion.setText("Escoja que operacion desea realizar");
		labelSeleccion.setBounds(272, 318, 172, 14);

		textoIntroduccion = "Esta aplicaci�n presenta un ejemplo pr�ctico del patron "
				+ "de dise�o MVC.\n\n"
				+ "La aplicaci�n permite registrar, actualizar, buscar y eliminar registros de una tabla Persona."
				+ "tambien son vinculados algunos conceptos de los Patrones Value Object y Data Access Objetc\n";

		btnAtras.addActionListener(this);
		getContentPane().add(btnAtras);
		getContentPane().add(labelSeleccion);

		setSize(772, 554);
		setTitle("Sistema E-vote: Paraguay Elecciones 2015");
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		btnGenero = new JButton("Generos");
		btnGenero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentanaBuscarGenero genero = new VentanaBuscarGenero();
				genero.setVisible(true);
				dispose();
			}
		});
		btnGenero.setBounds(105, 182, 155, 23);
		getContentPane().add(btnGenero);

		btnPais = new JButton("Paises");
		btnPais.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentanaBuscarPais pais = new VentanaBuscarPais();
				pais.setVisible(true);
				dispose();
			}
		});
		btnPais.setBounds(105, 250, 155, 23);
		getContentPane().add(btnPais);

		btnListas = new JButton("Listas");
		btnListas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentanaBuscarLista lista = new VentanaBuscarLista();
				lista.setVisible(true);
				dispose();
			}
		});
		btnListas.setBounds(105, 284, 155, 23);
		getContentPane().add(btnListas);

		btnPersonas = new JButton("Personas");
		btnPersonas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaBuscarPersona persona = new VentanaBuscarPersona();
				persona.setVisible(true);
				dispose();

			}
		});
		btnPersonas.setBounds(320, 178, 146, 23);
		getContentPane().add(btnPersonas);

		btnDepartamentos = new JButton("Departamentos");
		btnDepartamentos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentanaBuscarDepartamento departamento = new VentanaBuscarDepartamento();
				departamento.setVisible(true);
				dispose();
			}
		});
		btnDepartamentos.setBounds(320, 216, 146, 23);
		getContentPane().add(btnDepartamentos);

		labelTitulo = new JLabel();
		labelTitulo.setIcon(new ImageIcon(DefinicionesGenerales.class
				.getResource("/imgs/def.png")));
		labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitulo.setText("Definiciones Generales");
		labelTitulo.setBounds(10, 96, 712, 86);
		labelTitulo.setFont(new Font("Verdana", Font.BOLD, 46));
		getContentPane().add(labelTitulo);

		btnCandidatos = new JButton("Candidatos");
		btnCandidatos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentanaBuscarCandidato candidato;
				candidato = new VentanaBuscarCandidato();
				candidato.setVisible(true);
				dispose();

			}
		});
		btnCandidatos.setBounds(320, 284, 146, 23);
		getContentPane().add(btnCandidatos);

		btnVotantesHabilitados = new JButton("Votantes Habilitados");
		btnVotantesHabilitados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentanaBuscarVotantesHabilitados votantesHabilitados = new VentanaBuscarVotantesHabilitados();
				votantesHabilitados.setVisible(true);
				dispose();
			}
		});
		btnVotantesHabilitados.setBounds(492, 182, 176, 23);
		getContentPane().add(btnVotantesHabilitados);

		btnTipoEvento = new JButton("Tipo Evento");
		btnTipoEvento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentanaBuscarTipoEvento tevento;
				tevento = new VentanaBuscarTipoEvento();
				tevento.setVisible(true);
				dispose();
			}
		});
		btnTipoEvento.setBounds(320, 250, 146, 23);
		getContentPane().add(btnTipoEvento);

		btnVigenciaHorarioXPais = new JButton("Vigencia Horario por Pais");
		btnVigenciaHorarioXPais.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentanaBuscarVigencia vigencia = new VentanaBuscarVigencia();
				vigencia.setVisible(true);
				dispose();
			}
		});
		btnVigenciaHorarioXPais.setBounds(105, 216, 155, 23);
		getContentPane().add(btnVigenciaHorarioXPais);

		btnNacionalidades = new JButton("Nacionalidades\r\n");
		btnNacionalidades.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentanaBuscarNacionalidad nacionalidad = new VentanaBuscarNacionalidad();
				nacionalidad.setVisible(true);
				dispose();
			}
		});
		btnNacionalidades.setBounds(492, 216, 176, 23);
		getContentPane().add(btnNacionalidades);

		btnHome = new JButton("");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MenuPrincipal menuprincipal = new MenuPrincipal();
				menuprincipal.setVisible(true);
				dispose();
			}
		});
		btnHome.setSelectedIcon(null);
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

		JLabel lblNombreDescripcion = new JLabel(
				Login.nombreApellidoUserLogeado);
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

		JLabel label_5 = new JLabel(VentanaBuscarEvento.fechaDesde);
		label_5.setBounds(410, 25, 191, 14);
		panel.add(label_5);
		label_5.setForeground(Color.BLACK);

		JLabel lblFechaHasta = new JLabel("Fecha Hasta:");
		lblFechaHasta.setBounds(317, 37, 83, 14);
		panel.add(lblFechaHasta);

		JLabel label_7 = new JLabel(VentanaBuscarEvento.fechaHasta);
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

		btnEscrutinio = new JButton("Escrutinio");
		btnEscrutinio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Escrutinio escru = new Escrutinio();
				escru.setVisible(true);
				dispose();
			}
		});
		btnEscrutinio.setBounds(513, 284, 101, 23);
		getContentPane().add(btnEscrutinio);

		btnTipoLista = new JButton("Tipo Lista");
		btnTipoLista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentanaBuscarTipoLista tLista = new VentanaBuscarTipoLista();
				tLista.setVisible(true);
				dispose();
			}
		});
		btnTipoLista.setBounds(492, 250, 146, 23);
		getContentPane().add(btnTipoLista);
		// lblNombreDescripcion.repaint();

		btnGenero.addKeyListener(new MKeyListener());
		btnPersonas.addKeyListener(new MKeyListener());
		btnVotantesHabilitados.addKeyListener(new MKeyListener());
		btnVigenciaHorarioXPais.addKeyListener(new MKeyListener());
		btnDepartamentos.addKeyListener(new MKeyListener());
		btnNacionalidades.addKeyListener(new MKeyListener());
		btnPais.addKeyListener(new MKeyListener());
		btnTipoEvento.addKeyListener(new MKeyListener());
		btnTipoLista.addKeyListener(new MKeyListener());
		btnListas.addKeyListener(new MKeyListener());
		btnCandidatos.addKeyListener(new MKeyListener());
		btnEscrutinio.addKeyListener(new MKeyListener());
		btnAtras.addKeyListener(new MKeyListener());
		btnHome.addKeyListener(new MKeyListener());
		
		
		//btnGenero.requestFocus();
		getRootPane().setDefaultButton(btnHome);
		
		JButton bntUsers = new JButton("Usuarios");
		bntUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaBuscarUsers u = new VentanaBuscarUsers();
				u.setVisible(true);
				dispose();
			}
		});
		bntUsers.setBounds(513, 314, 101, 23);
		getContentPane().add(bntUsers);
		
		btnRoles = new JButton("Roles");
		btnRoles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaBuscarRoles roles = new VentanaBuscarRoles();
				roles.setVisible(true);
				dispose();
			}
		});
		btnRoles.setBounds(513, 348, 101, 23);
		getContentPane().add(btnRoles);

	}

	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador = miCoordinador;
	}

	public void actionPerformed(ActionEvent arg0) {
		VentanaBuscarEvento mainEvento = new VentanaBuscarEvento();
		mainEvento.setVisible(true);
		dispose();
		// TODO Auto-generated method stub

	}

	class MKeyListener extends KeyAdapter {

		@Override
	        public void keyPressed(KeyEvent event) {

	    

	            if (event.getKeyCode() == KeyEvent.VK_ENTER) {
	            	
	            	
	            if(btnGenero.isFocusOwner()==true){
	                	btnGenero.doClick();
	                    System.out.println("the button is: " + btnGenero.getText()+ " btnGenero");
	                }
	                
	                if(btnPersonas.isFocusOwner()==true){
	                	btnPersonas.doClick();
	                 System.out.println("the button is: " + btnPersonas.getText()+ " btnPersonas");
	                }
	                
	                if(btnVotantesHabilitados.isFocusOwner()==true){
	                	btnVotantesHabilitados.doClick();
	                 System.out.println("the button is: " + btnVotantesHabilitados.getText()+ " btnVotantesHabilitados");
	                }
	                
	                if(btnVigenciaHorarioXPais.isFocusOwner()==true){
	                	btnVigenciaHorarioXPais.doClick();
	                 System.out.println("the button is: " + btnVigenciaHorarioXPais.getText()+ " btnVigenciaHorarioXPais");
	                }
	                
	                if(btnDepartamentos.isFocusOwner()==true){
	                	btnDepartamentos.doClick();
	                 System.out.println("the button is: " + btnDepartamentos.getText()+ " btnDepartamentos");
	                }
	               
	                
	                if(btnNacionalidades.isFocusOwner()==true){
	                	btnNacionalidades.doClick();
	                 System.out.println("the button is: " + btnNacionalidades.getText()+ " btnNacionalidades");
	                }
	                
	                if(btnPais.isFocusOwner()==true){
	                	btnPais.doClick();
	                 System.out.println("the button is: " + btnPais.getText()+ " btnPais");
	                }

	             //   ****
	                
	                if(btnTipoEvento.isFocusOwner()==true){
	                	btnTipoEvento.doClick();
	                 System.out.println("the button is: " + btnTipoEvento.getText()+ " btnTipoEvento");
	                }
	                
	                
	                if(btnTipoLista.isFocusOwner()==true){
	                	btnTipoLista.doClick();
	                 System.out.println("the button is: " + btnTipoLista.getText()+ " btnTipoLista");
	                }
	                
	                
	                if(btnListas.isFocusOwner()==true){
	                	btnListas.doClick();
	                 System.out.println("the button is: " + btnListas.getText()+ " btnListas");
	                }
	                
	                
	                if(btnCandidatos.isFocusOwner()==true){
	                	btnCandidatos.doClick();
	                 System.out.println("the button is: " + btnCandidatos.getText()+ " btnCandidatos");
	                }
	                
	                if(btnEscrutinio.isFocusOwner()==true){
	                	btnEscrutinio.doClick();
	                 System.out.println("the button is: " + btnEscrutinio.getText()+ " btnEscrutinio");
	                }
	                

	            }
	            if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
	            	btnAtras.doClick();
	            }
	            
	            if (event.getKeyCode() == KeyEvent.VK_HOME) {
	            	btnHome.doClick();
	            }
	        }
	}
}
