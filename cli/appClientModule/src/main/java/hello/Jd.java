package src.main.java.hello;
import hello.wsdl.ConsultarRequest;
import hello.wsdl.ConsultarResponse;
import hello.wsdl.Voto;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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


public class Jd extends JDialog {
	
	private static final String INSERT_SQL =" insert into voto (nombre, Voto, fecha) values (?,?,?)";

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			Jd dialog = new Jd();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public Jd() {
		setResizable(false);
		setBounds(100, 100, 335, 305);
		
		JLabel lblEstSeguro = new JLabel("Est\u00E1 seguro?");
		lblEstSeguro.setFont(new Font("Tahoma", Font.PLAIN, 40));
		
		JButton btnNewButton = new JButton("SI");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Voto voto = new Voto();
				voto.setNombre("Armando");
				voto.setVoto(true);
				Date fecha = new Date(2014, 9, 15);
				voto.setFecha(fecha);
				try {
					guardar(voto);
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
		
	}
	
	private void guardar(Voto voto) throws Exception{
		ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
		ConsultarRequest consulta = new ConsultarRequest();
		
		consulta.setCedula("77777");
		consulta.setOrigenPeticion(2);
		
		
		
		ConsultarResponse response = weatherClient.getConsultarResponse(consulta);
		weatherClient.printResponse(response);
	}

	public Connection getConnection() throws SQLException {
		try{
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ajax","root","bluray2014");
		return connection;
		 }
        catch (Exception e) 
        {
            System.out.println(e);
        }
		return null;
		
	}
}
