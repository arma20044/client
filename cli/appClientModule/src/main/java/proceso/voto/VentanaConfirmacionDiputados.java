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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.EleccionMesa;
import src.main.java.login.Login;
import src.main.java.votante.VentanaPrincipalVotante;
import javax.swing.UIManager;
import java.awt.Color;

public class VentanaConfirmacionDiputados extends JDialog {
	private Container contenedor;
	JLabel labelTitulo;
	private JLabel lblListaPresidente, lblListaSenador, lblListaDiputado,lblMensaje;
	
	//public static Integer idLocal;
	
	public static Integer idEvento;
	
	public static Integer idMesa;

	public VentanaConfirmacionDiputados(
			final VentanaDiputados miVentanaPrincipal, boolean modal) {
		getContentPane().setLayout(null);

		JLabel label = new JLabel("Está seguro?");
		label.setFont(new Font("Tahoma", Font.PLAIN, 40));
		label.setBounds(80, 24, 225, 49);
		getContentPane().add(label);

		JButton button = new JButton("SI");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				
				//idMesa = obtenerMesa(EleccionMesa.Mesa, EleccionMesa.evento, Integer.parseInt(EleccionMesa.local));
				idMesa = EleccionMesa.Mesa;
				
				
				Integer idListaPresidete = obtenerLista(1,
						Integer.parseInt(VentanaPresidente.presidente));
				
				votar(idListaPresidete, idMesa);
				System.out.println("Se voto presidente");
				
				Integer idListaSenador = obtenerLista(8,
						Integer.parseInt(VentanaSenadores.senadores));
				
				votar(idListaSenador, idMesa);
				System.out.println("Se voto Senador");
				
				Integer idListaDiputado = obtenerLista(7,
						Integer.parseInt(VentanaDiputados.diputados));
				
				votar(idListaDiputado, idMesa);
				System.out.println("Se voto Diputado");
				
				
				actualizarVotante();
				//eliminarVotanteHabilitado();
				
				
				

				

				VentanaVotoFinal end = new VentanaVotoFinal();
				end.setVisible(true);
				dispose();
			}
		});
		button.setBounds(127, 227, 43, 23);
		getContentPane().add(button);

		JButton button_1 = new JButton("NO");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				VentanaDiputados diputados = new VentanaDiputados();
				diputados.setVisible(true);
				dispose();
			}
		});
		button_1.setBounds(233, 227, 47, 23);
		getContentPane().add(button_1);

		lblListaPresidente = new JLabel();

		lblListaPresidente.setBounds(80, 95, 141, 14);
		lblListaPresidente.setText("Presidente Lista: "
				+ VentanaPresidente.presidente);
		getContentPane().add(lblListaPresidente);

		lblListaSenador = new JLabel();

		lblListaSenador.setBounds(80, 120, 141, 14);
		lblListaSenador.setText("Senador Lista: " + VentanaSenadores.senadores);
		getContentPane().add(lblListaSenador);

		lblListaDiputado = new JLabel();

		lblListaDiputado.setBounds(80, 147, 141, 14);
		lblListaDiputado.setText("Diputado Lista: "
				+ VentanaDiputados.diputados);
		getContentPane().add(lblListaDiputado);
		/*
		 * Al llamar al constructor super(), le enviamos el JFrame Padre y la
		 * propiedad booleana que determina que es hija
		 */
		// super(miVentanaPrincipal, modal);
		setAlwaysOnTop(true);
		setResizable(false);
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		// contenedor=getContentPane();
		// contenedor.setLayout(null);
		// Asigna un titulo a la barra de titulo
		setTitle("Confirmar?");

		// labelTitulo= new JLabel();
		// labelTitulo.setText("VENTANA DE CONFIRMACION");
		// labelTitulo.setBounds(20, 20, 180, 23);

		// contenedor.add(labelTitulo);
		// tama�o de la ventana
		setSize(446, 305);
		// pone la ventana en el Centro de la pantalla
		// setLocationRelativeTo(null);
		// setLocationRelativeTo(null);

		setBounds(100, 100, 335, 305);

		JLabel lblEstSeguro = new JLabel("Está seguro?");
		lblEstSeguro.setFont(new Font("Tahoma", Font.PLAIN, 40));

		JButton btnNewButton = new JButton("SI");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			

			}
		});

		JButton btnNewButton_1 = new JButton("NO");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				VentanaDiputados diputados = new VentanaDiputados();
				diputados.setVisible(true);
			}
		});
		getContentPane().setLayout(null);
		getContentPane().add(label);
		getContentPane().add(button);
		getContentPane().add(button_1);
		getContentPane().add(lblListaPresidente);
		getContentPane().add(lblListaSenador);
		getContentPane().add(lblListaDiputado);
		
		lblMensaje = new JLabel("");
		lblMensaje.setForeground(Color.RED);
		lblMensaje.setFont(UIManager.getFont("Label.font"));
		lblMensaje.setBounds(80, 172, 199, 32);
		getContentPane().add(lblMensaje);

		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void guardar(Voto voto) throws Exception {
		ApplicationContext ctx = SpringApplication
				.run(WeatherConfiguration.class);

		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
		ConsultarRequest consulta = new ConsultarRequest();

		consulta.setCedula(voto.getNombre());
		consulta.setOrigenPeticion(1);

		ConsultarResponse response = weatherClient
				.getConsultarResponse(consulta);
		weatherClient.printResponse(response);
	}

	private void votar(Voto voto) throws Exception {
		ApplicationContext ctx = SpringApplication
				.run(WeatherConfiguration.class);

		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
		VotarRequest votar = new VotarRequest();

		votar.setCedula(voto.getNombre());
		votar.setOrigenPeticion(1);
		votar.setCandidato(voto.getCanditato().toString());

		VotarResponse response = weatherClient.getVotarResponse(votar);
		weatherClient.printVotoResponse(response);
	}

	//Metodo para obtener el ID de lista que fue seleccionado
	private Integer obtenerLista(int tipoLista, Integer lista) {

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
		query.setQueryGenerico("select id_lista, nombre_lista"
				+ " from ucsaws_listas" + " where nro_lista = " + lista
				+ " and id_tipo_lista = " + tipoLista);

		QueryGenericoResponse response = weatherClient
				.getQueryGenericoResponse(query);
		weatherClient.printQueryGenericoResponse(response);

		JSONParser j = new JSONParser();

		String generoAntesPartir = response.getQueryGenericoResponse();
		
		if (generoAntesPartir.compareTo("[]")==0){
			
			//JOptionPane.showMessageDialog(null, "LA consulta arrojo vacio!!!.");;
			lblMensaje
			.setText("Lista: LA consulta arrojo vacio!!!.");
		}
		
		else

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
	
	//Metodo para obtener el ID de la Mesa en donde se voto
	private Integer obtenerMesa(int nroMesa, Integer idEvento, Integer idLocal) {

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
		query.setQueryGenerico("select id_mesa, desc_mesa, id_local, id_evento"
				+ " from ucsaws_mesa" + " where nro_mesa = " + nroMesa
				+ " and id_evento = " + idEvento
				+ " and  id_local = " + idLocal);

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
		
		idLocal =  Integer.parseInt(fil.get(2).toString());
		
		idEvento = Integer.parseInt(fil.get(3).toString());
		
		String result = fil.get(0).toString();

		return Integer.parseInt(result);

	}
	
	
	private void votar(Integer idLista, Integer idMesa){
		
		// insertar voto inicioS
		Calendar calendar = new GregorianCalendar();
		int year = calendar.get(Calendar.YEAR);

		ApplicationContext ctx = SpringApplication
				.run(WeatherConfiguration.class);

		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
		QueryGenericoRequest query = new QueryGenericoRequest();

		// para registrar se inserta el codigo es 1
		query.setTipoQueryGenerico(1);
		System.out.println(Login.userLogeado);
		query.setQueryGenerico(
				"INSERT INTO ucsaws_votos ( id_voto, id_lista, id_mesa,usuario_ins,fch_ins, usuario_upd, fch_upd) "
				+ "VALUES (nextval('ucsaws_votos_seq') , " + idLista + "," +  idMesa + " , '" + Login.userLogeado + "' , now(), '"
						+ Login.userLogeado + "' , now())");

		QueryGenericoResponse response = weatherClient
				.getQueryGenericoResponse(query);
		weatherClient.printQueryGenericoResponse(response);

		// insertar votoS fin
		
	}
	
	private void actualizarVotante(){
		
		// actualizar situacion votante inicio
				Calendar calendar = new GregorianCalendar();
				int year = calendar.get(Calendar.YEAR);

				ApplicationContext ctx = SpringApplication
						.run(WeatherConfiguration.class);

				WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
				QueryGenericoRequest query = new QueryGenericoRequest();

				// para registrar se inserta el codigo es 1
				// by default sufrago es 0 cuando no votó, al votar cambia a 1
				query.setTipoQueryGenerico(3);
				System.out.println(Login.userLogeado);
				query.setQueryGenerico(
						"UPDATE ucsaws_votante"
						+ " SET sufrago = 1,  fch_upd = now() ,  usuario_upd= 'ucsavoto' "
						+ " WHERE  id_votante = " + VentanaPrincipalVotante.idVotante
						+ " and id_mesa = "  + idMesa);

				QueryGenericoResponse response = weatherClient
						.getQueryGenericoResponse(query);
				weatherClient.printQueryGenericoResponse(response);

				// actualizar situacion votante fin
				
	}
}
