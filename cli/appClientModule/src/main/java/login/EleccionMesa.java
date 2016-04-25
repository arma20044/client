package src.main.java.login;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.persona.Item;
import src.main.java.admin.zona.VentanaBuscarZona;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.proceso.voto.VentanaPresidente;
import src.main.java.votante.VentanaPrincipalVotante;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JComboBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JPasswordField;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class EleccionMesa extends JFrame {

	private JPanel contentPane;
	
	public static Integer evento = obtenerEventoVigente();
	
	public static Integer Mesa = 0;
	
	public static Integer habilitado;
	
	public static String local ;
	
	public static Integer idEvento;
	private JLabel lblCIN;
	
	List<Object[]> ciudades = new ArrayList<Object[]>();
	private JPasswordField pfPass;
	private JTextField txtUser;

	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	public EleccionMesa() {
		
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 354, 301);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		this.setLocationRelativeTo(null);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(!(txtUser.getText().isEmpty()) && !(pfPass.getText().isEmpty()) ){
				
				
				if(verificarDatos(txtUser.getText(), pfPass.getText())== true){
					if (habilitado == 0){
					VentanaPresidente main = new VentanaPresidente();
					main.setVisible(true);
					
					dispose();
					}
					else{
						JOptionPane.showMessageDialog(null,
		                          "VOTANTE - Ud. ya ha votado",
		                          "error",
		                          JOptionPane.INFORMATION_MESSAGE);
					}
				}
				
				else{
					
					
				}
				
			//	obtenerMesa
				
				//Mesa = Integer.parseInt((txtUser.getText()));
				
				//System.out.println("Mesa Seleccionada es: " + Mesa);
				
				//local = obtenerLocal(Mesa.toString());
				
				//idEvento = obtenerEvento(Integer.parseInt(local));
				
				
				//SE OMITE
				/*VentanaPrincipalVotante votantePrincipal = new VentanaPrincipalVotante();
        		votantePrincipal.setVisible(true);
        		dispose();*/
			}
				else{
					txtUser.setBackground(Color.RED);
					
					JOptionPane.showMessageDialog(null,
	                          "VOTANTE - Ingrese usuario y contraseña",
	                          "Campos Vacíos",
	                          JOptionPane.INFORMATION_MESSAGE);
					
					txtUser.requestFocus();
					
				}
			}
		});
		btnAceptar.setBounds(89, 247, 89, 23);
		contentPane.add(btnAceptar);
		
		JLabel lblContrasenha = new JLabel("Contraseña:");
		lblContrasenha.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblContrasenha.setBounds(47, 128, 101, 37);
		contentPane.add(lblContrasenha);
		
		lblCIN = new JLabel("C.I.N° / DNI:");
		lblCIN.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblCIN.setBounds(49, 68, 117, 37);
		contentPane.add(lblCIN);
		
		pfPass = new JPasswordField();
		pfPass.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(pfPass.getText().trim().length() == 0){
					pfPass.setBackground(Color.RED);
				}
				else{
					pfPass.setBackground(Color.WHITE);
				}
			}
		});
		pfPass.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if(pfPass.getText().trim().length() == 0){
					pfPass.setBackground(Color.RED);
				}
				else{
					pfPass.setBackground(Color.WHITE);
				}
			}
		});
		pfPass.setBounds(178, 138, 137, 23);
		contentPane.add(pfPass);
		
		txtUser = new JTextField();
		txtUser.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if(txtUser.getText().trim().length() == 0){
					txtUser.setBackground(Color.RED);
				}
				else{
					txtUser.setBackground(Color.WHITE);
				}
			}
		});
		txtUser.addKeyListener(new KeyAdapter() {
		
			@Override
			public void keyPressed(KeyEvent e) {
			
			}
			@Override
			public void keyReleased(KeyEvent e) {
				if(txtUser.getText().trim().length() == 0){
					txtUser.setBackground(Color.RED);
				}
				else{
					txtUser.setBackground(Color.WHITE);
				}
			}
		});
		txtUser.setBounds(178, 79, 137, 20);
		contentPane.add(txtUser);
		txtUser.setColumns(10);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PreLogin pre = new PreLogin();
				pre.setVisible(true);
				dispose();
			}
		});
		btnVolver.setBounds(191, 247, 89, 23);
		contentPane.add(btnVolver);
		
		getRootPane().setDefaultButton(btnAceptar);
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
				
				private Vector recuperarDatosComboBoxPaisOrigen() {
					Vector model = new Vector();
					JSONArray filas = new JSONArray();
					JSONArray fil = new JSONArray();

					boolean existe = false;

					// Statement estatuto = conex.getConnection().createStatement();

					ApplicationContext ctx = SpringApplication
							.run(WeatherConfiguration.class);

					WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
					QueryGenericoRequest query = new QueryGenericoRequest();

					// para registrar se inserta el codigo es 1
					query.setTipoQueryGenerico(2);

					// query.setQueryGenerico("SELECT id_genero, descripcion, to_char(fch_ins, 'DD/MM/YYYY HH24:MI:SS') as FchIns , "
					// +
					// "usuario_ins, to_char(fch_upd, 'DD/MM/YYYY HH24:MI:SS') as FchUpd ,usuario_upd from ucsaws_departamento ");

					query.setQueryGenerico("SELECT id_pais, nombre" + " from ucsaws_pais where id_evento =  " + VentanaBuscarEvento.evento
							+ " order by nombre");

					QueryGenericoResponse response = weatherClient
							.getQueryGenericoResponse(query);
					weatherClient.printQueryGenericoResponse(response);

					String res = response.getQueryGenericoResponse();

					if (res.compareTo("ERRORRRRRRR") == 0) {
						JOptionPane.showMessageDialog(null, "algo salio mal",
								"Advertencia", JOptionPane.WARNING_MESSAGE);

					}

					else {
						existe = true;

						String generoAntesPartir = response.getQueryGenericoResponse();

						JSONParser j = new JSONParser();
						Object ob = null;
						String part1, part2, part3;

						try {
							ob = j.parse(generoAntesPartir);
						} catch (org.json.simple.parser.ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						filas = (JSONArray) ob;

					}

					int ite = 0;
					String campo4, campo5 = "";
					while (filas.size() > ite) {
						fil = (JSONArray) filas.get(ite);

						String[] fin = { fil.get(0).toString(), fil.get(1).toString(), };

						ciudades.add(fin);
						model.addElement(new Item(Integer.parseInt(fin[0]), fin[1]));
						ite++;
					}
					return model;

				}
				
				
				private static Boolean verificarDatos(String user, String pass) {
					Boolean result = false;

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
					query.setQueryGenerico("select id_user, id_mesa, sufrago, v.id_votante  from ucsaws_users u join ucsaws_persona p on (u.id_persona = p.id_persona) join ucsaws_votante v on (v.id_persona = p.id_persona) "
							+ "where habilitado = 1 and sufrago = 0 and usuario = '" + user + "' and pass = '" + pass + "'" );
					
					
					
				 

					QueryGenericoResponse response = weatherClient
							.getQueryGenericoResponse(query);
					weatherClient.printQueryGenericoResponse(response);

					JSONParser j = new JSONParser();

					String generoAntesPartir = response.getQueryGenericoResponse();
					
					if (generoAntesPartir.compareTo("[]") == 0){
						   JOptionPane.showMessageDialog(null,
			                          "No se puede continuar, Usted ya ha votado.",
			                          "ERROR.",
			                          JOptionPane.INFORMATION_MESSAGE);
						   return result;
					}
					else{
					try {
						ob = j.parse(generoAntesPartir);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					filas = (JSONArray) ob;

					fil = (JSONArray) filas.get(0);
					
					//Mesa = (Integer) fil.get(1);
					
					Mesa = (int) (long)  fil.get(1);
					
					habilitado = (int) (long) fil.get(2);
					
					VentanaPrincipalVotante.idVotante = (int) (long) fil.get(3);

					result = true;

					return result;

				}
				}
}
