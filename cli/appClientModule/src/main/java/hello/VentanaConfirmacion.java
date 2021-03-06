package src.main.java.hello;

import hello.wsdl.Voto;
import hello.wsdl.ConsultarRequest;
import hello.wsdl.ConsultarResponse;
import hello.wsdl.VotarRequest;
import hello.wsdl.VotarResponse;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

public class VentanaConfirmacion extends JDialog{
	private Container contenedor;
	JLabel labelTitulo;
	
	public VentanaConfirmacion(final VentanaPrincipal miVentanaPrincipal, boolean modal){
		/*Al llamar al constructor super(), le enviamos el
		 * JFrame Padre y la propiedad booleana que determina
		 * que es hija*/
		super(miVentanaPrincipal, modal);
		setAlwaysOnTop(true);
		setResizable(false);
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
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
		setSize(350,150);
		//pone la ventana en el Centro de la pantalla
		//setLocationRelativeTo(null);
		//setLocationRelativeTo(null);
		
		setBounds(100, 100, 335, 305);
		
		JLabel lblEstSeguro = new JLabel("Est\u00E1 seguro?");
		lblEstSeguro.setFont(new Font("Tahoma", Font.PLAIN, 40));
		
		JButton btnNewButton = new JButton("SI");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Voto voto = new Voto();
				voto.setNombre(miVentanaPrincipal.getCedula());
				voto.setVoto(true);
				voto.setCanditato(miVentanaPrincipal.getCandidato());
				Date fecha = new Date(2014, 9, 15);
				voto.setFecha(fecha);
				try {
					votar(voto);
					dispose();
					JOptionPane.showMessageDialog(null,"Excelente, ya cumplido con su deber civico. Se ha finalizado el proceso.");
					System.exit(0);
					
					
				} catch (Exception ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
				
			}
		});
		
		JButton btnNewButton_1 = new JButton("NO");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(51)
							.addComponent(lblEstSeguro, GroupLayout.PREFERRED_SIZE, 284, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(69)
							.addComponent(btnNewButton)
							.addGap(63)
							.addComponent(btnNewButton_1)))
					.addContainerGap(109, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(30)
					.addComponent(lblEstSeguro, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton)
						.addComponent(btnNewButton_1))
					.addGap(65))
		);
		getContentPane().setLayout(groupLayout);
		
		
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
