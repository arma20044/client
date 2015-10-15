package src.main.java.login;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.zona.VentanaBuscarZona;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.votante.VentanaPrincipalVotante;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EleccionMesa extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	
	public static Integer evento = obtenerEventoVigente();
	
	public static Integer Mesa = 0;
	
	public static String local ;
	
	public static Integer idEvento;

	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	public EleccionMesa() {
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 354, 215);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				char car = arg0.getKeyChar();
				if ((car < '0' || car > '9'))
					arg0.consume();
			}
		});
		textField.setBounds(137, 72, 89, 50);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Aceptar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Mesa = Integer.parseInt((textField.getText()));
				
				System.out.println("Mesa Seleccionada es: " + Mesa);
				
				local = obtenerLocal(Mesa.toString());
				
				idEvento = obtenerEvento(Integer.parseInt(local));
				
				VentanaPrincipalVotante votantePrincipal = new VentanaPrincipalVotante();
        		votantePrincipal.setVisible(true);
        		dispose();
			}
		});
		btnNewButton.setBounds(137, 160, 89, 23);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Seleccione el Numero de Mesa:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(51, 23, 278, 37);
		contentPane.add(lblNewLabel);
	}
	
	//Metodo para obtener el ID del evento
		private static Integer obtenerEventoVigente() {

			JSONArray filas = new JSONArray();
			JSONArray fil = new JSONArray();

			Object ob = null;

			ApplicationContext ctx = SpringApplication
					.run(WeatherConfiguration.class);

			WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
			QueryGenericoRequest query = new QueryGenericoRequest();

			// para registrar se inserta el codigo es 1
			query.setTipoQueryGenerico(2);
			System.out.println(Login.userLogeado);
			query.setQueryGenerico("select id_evento, descripcion"
					+ " from ucsaws_evento where to_char(current_timestamp , 'dd/mm/yyyy' ) between to_char(fch_desde, 'dd/mm/yyyy') " +
					" and to_char(fch_hasta, 'dd/mm/yyyy')");
			
			
			
		 

			QueryGenericoResponse response = weatherClient
					.getQueryGenericoResponse(query);
			weatherClient.printQueryGenericoResponse(response);

			JSONParser j = new JSONParser();

			String generoAntesPartir = response.getQueryGenericoResponse();

			try {
				ob = j.parse(generoAntesPartir);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			filas = (JSONArray) ob;

			fil = (JSONArray) filas.get(0);

			String result = fil.get(0).toString();

			return Integer.parseInt(result);

		}
		
		//Metodo para obtener el ID del local
				private static String obtenerLocal(String nroMesa) {

					JSONArray filas = new JSONArray();
					JSONArray fil = new JSONArray();

					Object ob = null;

					ApplicationContext ctx = SpringApplication
							.run(WeatherConfiguration.class);

					WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
					QueryGenericoRequest query = new QueryGenericoRequest();

					// para registrar se inserta el codigo es 1
					query.setTipoQueryGenerico(2);
					System.out.println(Login.userLogeado);
					query.setQueryGenerico("SELECT  l.id_local, nro_local,desc_local,nro_zona, desc_zona "
							+ "from  ucsaws_local l join ucsaws_zona z on (l.id_zona = z.id_zona)"
							+ " join ucsaws_distrito dis on (dis.id_distrito = z.id_distrito)"
							+ " join ucsaws_departamento dep on (dep.id_departamento = dis.id_departamento)"
							+ " join ucsaws_mesa mesa on (mesa.id_local = l.id_local)"
							+ " where mesa.id_evento = " + evento
							+ " and nro_mesa = " + nroMesa
							+ " order by nro_zona , nro_local" + "");
					
					
					
				 

					QueryGenericoResponse response = weatherClient
							.getQueryGenericoResponse(query);
					weatherClient.printQueryGenericoResponse(response);

					JSONParser j = new JSONParser();

					String generoAntesPartir = response.getQueryGenericoResponse();

					try {
						ob = j.parse(generoAntesPartir);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					filas = (JSONArray) ob;

					fil = (JSONArray) filas.get(0);

					String result = fil.get(0).toString();

					return result;

				}
				
				//Metodo para obtener el ID del evento
				private static Integer obtenerEvento(Integer idLocal) {

					JSONArray filas = new JSONArray();
					JSONArray fil = new JSONArray();

					Object ob = null;

					ApplicationContext ctx = SpringApplication
							.run(WeatherConfiguration.class);

					WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
					QueryGenericoRequest query = new QueryGenericoRequest();

					// para registrar se inserta el codigo es 1
					query.setTipoQueryGenerico(2);
					System.out.println(Login.userLogeado);
					query.setQueryGenerico("SELECT  mesa.id_evento, desc_local "
							+ "from  ucsaws_local l join ucsaws_mesa mesa on (mesa.id_local = l.id_local)"
							+ " where l.id_local = " + idLocal );
					
					
					
				 

					QueryGenericoResponse response = weatherClient
							.getQueryGenericoResponse(query);
					weatherClient.printQueryGenericoResponse(response);

					JSONParser j = new JSONParser();

					String generoAntesPartir = response.getQueryGenericoResponse();

					try {
						ob = j.parse(generoAntesPartir);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					filas = (JSONArray) ob;

					fil = (JSONArray) filas.get(0);

					String result = fil.get(0).toString();

					return Integer.parseInt(result);

				}
		
		
}
