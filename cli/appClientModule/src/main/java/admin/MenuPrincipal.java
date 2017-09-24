package src.main.java.admin;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import src.main.java.admin.acta.VentanaBuscarActa;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.utils.Close;
import src.main.java.login.Login;

import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuPrincipal extends JFrame implements ActionListener{
	
	private Coordinador miCoordinador; //objeto miCoordinador que permite la relacion entre esta clase y la clase coordinador
	
	public static boolean reporte;
	
	private JButton btnAdministracion ,btnReportes ;

	/**
	 * Establece la informacion que se presentara como introduccion del sistema
	 */
	public String textoIntroduccion = "";
	
	
	
	public MenuPrincipal() {
		
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
		    @Override
		    public void windowClosing(WindowEvent we)
		    { 
		    	Close close = new Close();
		    	close.cerrarAplicacion(we);
		    }
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(MenuPrincipal.class.getResource("/imgs/paraguay.png")));
		
		reporte = false;
		
		VentanaBuscarEvento.readOnly = false;
		
		setResizable(false);
		//setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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
		
		btnAdministracion = new JButton("Administracion");
		btnAdministracion.setHorizontalAlignment(SwingConstants.LEFT);
		btnAdministracion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VentanaBuscarEvento.evento="";
				VentanaBuscarEvento ventanaBuscarEvento = new VentanaBuscarEvento();
				ventanaBuscarEvento.setVisible(true);
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
		
		btnReportes = new JButton("Reportes");
		btnReportes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentanaBuscarEvento.evento="";
				
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
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.addMouseListener(new MouseAdapter() {
		  @Override
		  public void mouseClicked(MouseEvent arg0) {
		    VentanaBuscarActa acta = new VentanaBuscarActa();
		    acta.setVisible(true);
		    dispose();
		  }
		});
		btnNewButton.setBounds(167, 375, 89, 23);
		getContentPane().add(btnNewButton);
		//lblNombreDescripcion.repaint();
		
		
		btnAdministracion.addKeyListener(new MKeyListener());
		btnReportes.addKeyListener(new MKeyListener());
		
		
		
		
		
		
		
		
		

	}




	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador=miCoordinador;
	}


	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	class MKeyListener extends KeyAdapter {

		@Override
	        public void keyPressed(KeyEvent event) {

	    

	            if (event.getKeyCode() == KeyEvent.VK_ENTER) {
	            	
	            	
	            if(btnAdministracion.isFocusOwner()==true){
	            	btnAdministracion.doClick();
	                    System.out.println("the button is: " + btnAdministracion.getText()+ " btnAdministracion");
	                }
	            
	            if(btnReportes.isFocusOwner()==true){
	            	btnReportes.doClick();
	                    System.out.println("the button is: " + btnReportes.getText()+ " btnReportes");
	                }
	            }
		}
	}
}
