package src.main.java.admin;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
					    JOptionPane.showMessageDialog(panel, "Proximamente...");
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
