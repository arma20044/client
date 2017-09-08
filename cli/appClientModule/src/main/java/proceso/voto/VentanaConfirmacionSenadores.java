package src.main.java.proceso.voto;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;
import hello.wsdl.Voto;
import hello.wsdl.ConsultarRequest;
import hello.wsdl.ConsultarResponse;
import hello.wsdl.VotarRequest;
import hello.wsdl.VotarResponse;

import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;
import src.main.java.votante.VentanaPrincipalVotante;

import javax.swing.ImageIcon;
import java.awt.Window.Type;

public class VentanaConfirmacionSenadores extends JDialog{
	private Container contenedor;
	JLabel labelTitulo;
	
	public VentanaConfirmacionSenadores(final VentanaSenadores miVentanaPrincipal, boolean modal){
		/*Al llamar al constructor super(), le enviamos el
		 * JFrame Padre y la propiedad booleana que determina
		 * que es hija*/
		super(miVentanaPrincipal, modal);
		setType(Type.POPUP);
		setUndecorated(true);
		setAlwaysOnTop(true);
		setModal(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	    this.addWindowListener(new WindowAdapter() {
	      @Override
	      public void windowClosing(WindowEvent we)
	      { 
	          String ObjButtons[] = {"Sí","No"};
	           
	         
	          int PromptResult = JOptionPane.showOptionDialog(null,"Desea Salir?","Sistema E-vote: Paraguay Elecciones 2015.",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE,null,ObjButtons,ObjButtons[1]);
	          if(PromptResult==JOptionPane.YES_OPTION)
	          {
	              System.exit(0);
	          }
	      }
	  });
		getContentPane().setLayout(null);
		//contenedor=getContentPane();
		//contenedor.setLayout(null);
   		//Asigna un titulo a la barra de titulo
		setTitle("Confirmar?");
		
		
		//labelTitulo= new JLabel();
		//labelTitulo.setText("VENTANA DE CONFIRMACION");
		//labelTitulo.setBounds(20, 20, 180, 23);
		
		//contenedor.add(labelTitulo);
		//tama�o de la ventana
		setSize(365,305);
		//pone la ventana en el Centro de la pantalla
		//setLocationRelativeTo(null);
		//setLocationRelativeTo(null);
		
		setBounds(100, 100, 335, 305);
		
		JLabel lblEstSeguro = new JLabel("Est\u00E1 seguro?");
		lblEstSeguro.setIcon(new ImageIcon(VentanaConfirmacionSenadores.class.getResource("/img/info.png")));
		lblEstSeguro.setBounds(10, 48, 325, 75);
		lblEstSeguro.setFont(new Font("Tahoma", Font.PLAIN, 40));
		Image img5 = ((ImageIcon) lblEstSeguro.getIcon()).getImage();
        Image newimg5 = img5.getScaledInstance(70, 70,
            java.awt.Image.SCALE_SMOOTH);
        lblEstSeguro.setIcon(new ImageIcon(newimg5));
		
		JButton btnNewButton = new JButton("SI");
		btnNewButton.setIcon(new ImageIcon(VentanaConfirmacionSenadores.class.getResource("/img/success.png")));
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 35));
		btnNewButton.setBounds(0, 188, 167, 65);
		Image img4 = ((ImageIcon) btnNewButton.getIcon()).getImage();
        Image newimg4 = img4.getScaledInstance(80, 60,
            java.awt.Image.SCALE_SMOOTH);
        btnNewButton.setIcon(new ImageIcon(newimg4));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				VentanaParlasur parlasur = new VentanaParlasur();
				parlasur.setVisible(true);
				dispose(); 
				
				
			}
		});
		
		JButton btnNewButton_1 = new JButton("NO");
		btnNewButton_1.setIcon(new ImageIcon(VentanaConfirmacionSenadores.class.getResource("/img/NO.png")));
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 35));
		btnNewButton_1.setLocation(168, 188);
		btnNewButton_1.setSize(167, 65);
		Image img3 = ((ImageIcon) btnNewButton_1.getIcon()).getImage();
	    Image newimg3 = img3.getScaledInstance(80, 60,
	        java.awt.Image.SCALE_SMOOTH);
	    btnNewButton_1.setIcon(new ImageIcon(newimg3));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				VentanaSenadores senadores = new VentanaSenadores();
				senadores.setVisible(true);
			}
		});
		getContentPane().setLayout(null);
		getContentPane().add(lblEstSeguro);
		getContentPane().add(btnNewButton);
		getContentPane().add(btnNewButton_1);
		
		
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void guardar(Voto voto) throws Exception{
		ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
		ConsultarRequest consulta = new ConsultarRequest();
		
		consulta.setCedula(voto.getNombre());
		consulta.setOrigenPeticion(1);
		
		
		
		ConsultarResponse response = weatherClient.getConsultarResponse(consulta);
		weatherClient.printResponse(response);
	}
	
	private void votar(Voto voto) throws Exception{
		ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
		VotarRequest votar = new VotarRequest();
		
		votar.setCedula(voto.getNombre());
		votar.setOrigenPeticion(1);
		votar.setCandidato(voto.getCanditato().toString());
		
		
		
		VotarResponse response = weatherClient.getVotarResponse(votar);
		weatherClient.printVotoResponse(response);
	}
}
