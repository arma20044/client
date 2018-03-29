package src.main.java.admin;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.reportes.Candidatos;
import src.main.java.admin.reportes.CantidadVotosElegir;
import src.main.java.admin.reportes.Participacion;
import src.main.java.admin.reportes.Votantes;
import src.main.java.admin.reportes.VotoBlancoElegir;
import src.main.java.admin.reportes.VotosElegir;
import src.main.java.admin.utils.Close;
import src.main.java.login.Login;
import src.main.java.login.PreLogin;

import javax.swing.UIManager;

import java.awt.Color;

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
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we)
            { 
                Close close = new Close();
                close.cerrarAplicacion(we);
            }
        });

		btnAtras = new JButton();
		btnAtras.setIcon(new ImageIcon(Reportes.class.getResource("/imgs/volver.png")));
		btnAtras.setToolTipText("Volver al Menu Principal");
		btnAtras.setBounds(694, 359, 83, 51);

		labelSeleccion = new JLabel();
		labelSeleccion.setText("Escoja que operacion desea realizar");
		labelSeleccion.setBounds(0, 385, 250, 25);

		textoIntroduccion = "Esta aplicaci�n presenta un ejemplo pr�ctico del patron "
				+ "de dise�o MVC.\n\n"
				+ "La aplicaci�n permite registrar, actualizar, buscar y eliminar registros de una tabla Persona." +
				"tambien son vinculados algunos conceptos de los Patrones Value Object y Data Access Objetc\n";

		btnAtras.addActionListener(this);
		getContentPane().add(btnAtras);
		getContentPane().add(labelSeleccion);
	
		setSize(772, 439);
		setTitle("Sistema E-vote: Paraguay Elecciones 2015");
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		
	    
		
				labelTitulo = new JLabel();
				labelTitulo.setIcon(new ImageIcon(Reportes.class.getResource("/imgs/pfd64.png")));
				labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
				labelTitulo.setText("Reportes");
				labelTitulo.setBounds(23, 87, 647, 86);
				labelTitulo.setFont(new Font("Verdana", Font.BOLD, 46));
				getContentPane().add(labelTitulo);
				
				final JPanel panel = new JPanel();
				panel.setBounds(115, 184, 485, 91);
				getContentPane().add(panel);
				panel.setLayout(null);
				
				JButton btnPersonas = new JButton("Votantes");
				btnPersonas.setBounds(0, 0, 227, 23);
				panel.add(btnPersonas);
				
				JButton btnVotantesXDep = new JButton("Cantidad de Votos");
				btnVotantesXDep.setBounds(258, 0, 227, 23);
				panel.add(btnVotantesXDep);
				
				JButton btnTotalParticipacion = new JButton("Participacion");
				btnTotalParticipacion.setBounds(0, 68, 227, 23);
				panel.add(btnTotalParticipacion);
				
				JButton btnCantidadVotosBlancos = new JButton("Cantidad de Votos Blancos");
				btnCantidadVotosBlancos.setBounds(258, 34, 227, 23);
				panel.add(btnCantidadVotosBlancos);
				
				JButton btnListaCandidatos = new JButton("Lista de Candidatos");
				btnListaCandidatos.setBounds(0, 34, 227, 23);
				panel.add(btnListaCandidatos);
				
				JButton btnVotosXCandidatura = new JButton("Cantidad de Votos por Candidatura");
				btnVotosXCandidatura.setBounds(258, 68, 227, 23);
				panel.add(btnVotosXCandidatura);
				btnVotosXCandidatura.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Date hoy = new Date();
					  if (between(hoy, VentanaBuscarEvento.eventoClase.getFchDesde(), VentanaBuscarEvento.eventoClase.getFchHasta()) ){
					    //evento seleccionado esta vigente no mostrar los nombres de los candidatos
					    JOptionPane.showMessageDialog(panel, "Se podrá acceder al culminar la votación.");
					  }
					  else {
					    //evento seleccionado ya ha terminado mostrar
					    CantidadVotosElegir elegir = new CantidadVotosElegir();
                        elegir.setVisible(true);
                        dispose();
					  }
						
					}
				});
				btnListaCandidatos.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						Candidatos can = new Candidatos();
						can.start();
					}
				});
				btnCantidadVotosBlancos.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						VotoBlancoElegir v = new VotoBlancoElegir();
						v.setVisible(true);
						dispose();
					}
				});
				btnTotalParticipacion.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Participacion p = new Participacion();
						p.start();
					}
				});
				btnVotantesXDep.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						VotosElegir votosElegir = new VotosElegir();
						votosElegir.setVisible(true);
						dispose();
					}
				});
				btnPersonas.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						Votantes votantes = new Votantes();
						votantes.start();
					}
				});
				
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
				
				JPanel panel_1 = new JPanel();
				panel_1.setLayout(null);
				panel_1.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
				panel_1.setBounds(79, 0, 619, 85);
				getContentPane().add(panel_1);
				
				JLabel label = new JLabel("Usuario logueado:");
				label.setFont(new Font("Tahoma", Font.BOLD, 11));
				label.setBounds(0, 0, 111, 26);
				panel_1.add(label);
				
				JLabel label_1 = new JLabel(Login.nombreApellidoUserLogeado);
				label_1.setBounds(143, 0, 405, 26);
				panel_1.add(label_1);
				
				JLabel label_2 = new JLabel("Nro. Evento:");
				label_2.setFont(new Font("Tahoma", Font.BOLD, 11));
				label_2.setBounds(0, 25, 79, 14);
				panel_1.add(label_2);
				
				JLabel label_3 = new JLabel(VentanaBuscarEvento.nroEvento);
				label_3.setForeground(Color.BLACK);
				label_3.setBounds(77, 25, 60, 14);
				panel_1.add(label_3);
				
				JLabel label_4 = new JLabel("Descripcion Evento:");
				label_4.setFont(new Font("Tahoma", Font.BOLD, 11));
				label_4.setBounds(0, 37, 111, 14);
				panel_1.add(label_4);
				
				JLabel label_5 = new JLabel(VentanaBuscarEvento.descripcionEvento);
				label_5.setForeground(Color.BLACK);
				label_5.setBounds(114, 37, 200, 14);
				panel_1.add(label_5);
				
				 /*
			     * date a string formateado
			     */
			    // **
			    DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			    String reportDate = df.format(VentanaBuscarEvento.fechaDesde);
			    // **
				
				JLabel label_6 = new JLabel("Fecha Desde:");
				label_6.setFont(new Font("Tahoma", Font.BOLD, 11));
				label_6.setBounds(317, 25, 83, 14);
				panel_1.add(label_6);
				
				JLabel label_7 = new JLabel(reportDate);
				label_7.setForeground(Color.BLACK);
				label_7.setBounds(410, 25, 191, 14);
				panel_1.add(label_7);
				
				JLabel label_8 = new JLabel("Fecha Hasta:");
				label_8.setFont(new Font("Tahoma", Font.BOLD, 11));
				label_8.setBounds(317, 37, 83, 14);
				panel_1.add(label_8);
				
				
			    /*
			     * date a string formateado
			     */
			    // **
			    DateFormat df2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			    String reportDate2 = df.format(VentanaBuscarEvento.fechaHasta);
			    // **

				
				JLabel label_9 = new JLabel(reportDate2);
				label_9.setForeground(Color.BLACK);
				label_9.setBounds(410, 37, 191, 14);
				panel_1.add(label_9);
				
				JLabel label_10 = new JLabel("Tipo Evento:");
				label_10.setFont(new Font("Tahoma", Font.BOLD, 11));
				label_10.setBounds(0, 50, 79, 14);
				panel_1.add(label_10);
				
				JLabel label_11 = new JLabel(VentanaBuscarEvento.tipoEventoDescripcon);
				label_11.setForeground(Color.BLACK);
				label_11.setBounds(87, 50, 196, 14);
				panel_1.add(label_11);
				
				 String r = "";
				    if (Login.rol.compareToIgnoreCase("ADM") == 0) {
				      r = "ADMINISTRADOR";
				      
				    } else if (Login.rol.compareToIgnoreCase("COO") == 0) {
				      r = "COORDINADOR";
				    }

				    else if (Login.rol.compareToIgnoreCase("VOT") == 0) {
				      r = "VOTANTE";
				    }

				    else if (Login.rol.compareToIgnoreCase("CON") == 0) {
				      r = "CONSULTA";
				    }

				    else if (Login.rol.compareToIgnoreCase("MIE") == 0) {
				      r = "MIEMBRO DE MESA";
				    }
				
				JLabel label_12 = new JLabel("Rol:");
				label_12.setFont(new Font("Tahoma", Font.BOLD, 11));
				label_12.setBounds(317, 50, 83, 14);
				panel_1.add(label_12);
				
				JLabel label_13 = new JLabel(r);
				label_13.setForeground(Color.BLACK);
				label_13.setBounds(410, 50, 191, 14);
				panel_1.add(label_13);
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
	
	public static boolean between(Date date, Date dateStart, Date dateEnd) {
	    if (date != null && dateStart != null && dateEnd != null) {
	        if (date.after(dateStart) && date.before(dateEnd)) {
	            return true;
	        }
	        else {
	            return false;
	        }
	    }
	    return false;
	}
}
