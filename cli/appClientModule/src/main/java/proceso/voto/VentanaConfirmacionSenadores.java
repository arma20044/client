package src.main.java.proceso.voto;

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

import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.votante.VentanaPrincipalVotante;

public class VentanaConfirmacionSenadores extends JDialog{
	private Container contenedor;
	JLabel labelTitulo;
	
	public VentanaConfirmacionSenadores(final VentanaSenadores miVentanaPrincipal, boolean modal){
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
				VentanaPrincipalVotante votante = new VentanaPrincipalVotante();
				votante.setVisible(true);
				dispose(); 
				
				
			}
		});
		
		JButton btnNewButton_1 = new JButton("NO");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				VentanaSenadores senadores = new VentanaSenadores();
				senadores.setVisible(true);
			}
		});
		
		JLabel lblNewLabel = new JLabel("Presidente Lista: " + VentanaPresidente.presidente);
		
		JLabel label = new JLabel("Diputado Lista: " + VentanaDiputados.diputados);
		
		JLabel label_1 = new JLabel("Senador Lista: " + VentanaSenadores.senadores);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(69)
							.addComponent(btnNewButton)
							.addGap(63)
							.addComponent(btnNewButton_1))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(51)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblEstSeguro, GroupLayout.PREFERRED_SIZE, 284, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(10)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(label, GroupLayout.PREFERRED_SIZE, 205, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 205, GroupLayout.PREFERRED_SIZE)
										.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 205, GroupLayout.PREFERRED_SIZE))))))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(30)
					.addComponent(lblEstSeguro, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton)
						.addComponent(btnNewButton_1))
					.addContainerGap())
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
