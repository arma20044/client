package scr.main.java.admin.distrito;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import src.main.java.admin.Coordinador;
import src.main.java.hello.Genero;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;


public class VentanaRegistroDistrito extends JFrame implements ActionListener{

	private Coordinador miCoordinador; //objeto miCoordinador que permite la relacion entre esta clase y la clase coordinador
	private JLabel labelTitulo;
	private JTextField txtDepartamento;
	private JLabel lblDepartamento;
	private JButton botonGuardar,botonCancelar;
	private VentanaRegistroDistrito ventanaRegistro;
	
	private CiudadesJTableModel model = new CiudadesJTableModel();
	private JTextField txtNumeroDepartamento;
	private JTable table;
	
	private JScrollPane scrollPane;
	
	List<Object[]> ciudades = new ArrayList<Object[]>();
	
	String SelectedCombo = "";
	
	
	/**
	 * constructor de la clase donde se inicializan todos los componentes
	 * de la ventana de registro
	 */
	public VentanaRegistroDistrito() {

		botonGuardar = new JButton();
		botonGuardar.setBounds(254, 212, 120, 25);
		botonGuardar.setText("Registrar");
		
		botonCancelar = new JButton();
		botonCancelar.setBounds(402, 212, 120, 25);
		botonCancelar.setText("Cancelar");

		labelTitulo = new JLabel();
		labelTitulo.setText("REGISTRO DE DISTRITOS.");
		labelTitulo.setBounds(207, 28, 380, 30);
		labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));

		lblDepartamento=new JLabel();
		lblDepartamento.setText("Departamento:");
		lblDepartamento.setBounds(195, 137, 94, 25);
		getContentPane().add(lblDepartamento);
		
		txtDepartamento=new JTextField();
		txtDepartamento.setBounds(329, 101, 258, 25);
		getContentPane().add(txtDepartamento);
		
		botonGuardar.addActionListener(this);
		botonCancelar.addActionListener(this);
		getContentPane().add(botonCancelar);
		getContentPane().add(botonGuardar);
		getContentPane().add(labelTitulo);
		limpiar();
		setSize(812, 444);
		setTitle("Sistema E-vote: Paraguay Elecciones 2015");
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(null);
		
		
		txtNumeroDepartamento = new JTextField();
		txtNumeroDepartamento.setText("");
		txtNumeroDepartamento.setBounds(329, 69, 80, 25);
		getContentPane().add(txtNumeroDepartamento);
		
		JLabel lblNumeroDistrito = new JLabel();
		lblNumeroDistrito.setText("Nro. Distrito:");
		lblNumeroDistrito.setBounds(217, 69, 102, 25);
		getContentPane().add(lblNumeroDistrito);
		
		scrollPane = new JScrollPane();
		recuperarDatos();
		scrollPane.setBounds(0, 248, 806, 167);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setBounds(74, 226, 665, 178);
		table.setToolTipText("Listado de Generos");
		table.setAutoCreateRowSorter(true);
		scrollPane.setViewportView(table);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setModel(model);
		table.removeColumn(table.getColumnModel().getColumn(0));
		JLabel lblDistrito = new JLabel();
		lblDistrito.setText("Distrito:");
		lblDistrito.setBounds(239, 101, 80, 25);
		getContentPane().add(lblDistrito);
		
		final JComboBox comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object cmboitem = comboBox.getSelectedItem();
				SelectedCombo = cmboitem.toString();
			}
		});
		comboBox.setBounds(329, 139, 171, 20);
		getContentPane().add(comboBox);
		recuperarDatosComboBox();
		int pos = 0;
		while (ciudades.size() > pos){
		Object elemento[] = ciudades.get(pos);
		comboBox.addItem(elemento[1]);
		pos++;
		}		
//		    Object cmboitem = comboBox.getSelectedItem();
//		    System.out.println(cmboitem);
		
		//recuperarDatos();
		
		
		

	}


	private void limpiar() 
	{
		txtDepartamento.setText("");
	}


	public void setCoordinador(Coordinador miCoordinador) {
		this.miCoordinador=miCoordinador;
	}


	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource()==botonGuardar)
		{
			try {
				
				if (!txtDepartamento.getText().equals(""))
				{
					JSONArray filas = new JSONArray();
					JSONArray fil = new JSONArray();
					//Obtener el Departamento
					ApplicationContext ctxDept = SpringApplication.run(WeatherConfiguration.class);

					WeatherClient weatherClientDept = ctxDept.getBean(WeatherClient.class);
					QueryGenericoRequest queryDept = new QueryGenericoRequest();
					
					//para registrar se inserta el codigo es 1
					queryDept.setTipoQueryGenerico(2);
					
//					query.setQueryGenerico("SELECT id_genero, descripcion, to_char(fch_ins, 'DD/MM/YYYY HH24:MI:SS') as FchIns , "
//							+ "usuario_ins, to_char(fch_upd, 'DD/MM/YYYY HH24:MI:SS') as FchUpd ,usuario_upd from ucsaws_departamento ");
					
					queryDept.setQueryGenerico("SELECT id_departamento,  desc_departamento from ucsaws_departamento  " +
		"where desc_departamento = '" + SelectedCombo + "'");
					
					QueryGenericoResponse responseDept = weatherClientDept.getQueryGenericoResponse(queryDept);
					weatherClientDept.printQueryGenericoResponse(responseDept);
					
					String res = responseDept.getQueryGenericoResponse();
					
					Object ob = null;
					JSONParser j = new JSONParser();
					//try {
						ob = j.parse(res);
					//} catch (org.json.simple.parser.ParseException e) {
						// TODO Auto-generated catch block
					//	e.printStackTrace();
					//}
				
				filas = (JSONArray) ob;
				
				fil = (JSONArray) filas.get(0);
					
					//
				
				Genero genero = new Genero();
				genero.setDescripcion(txtDepartamento.getText());
				
				
				ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

				WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
				QueryGenericoRequest query = new QueryGenericoRequest();
				
				//para registrar se inserta el codigo es 1
				query.setTipoQueryGenerico(1);
				System.out.println(Login.userLogeado);
				query.setQueryGenerico("INSERT INTO ucsaws_distrito"
						+ "( id_distrito, desc_distrito, id_departamento, nro_distrito, usuario_ins,fch_ins, usuario_upd, fch_upd) "
						+ "VALUES ("
						+ "nextval('ucsaws_distrito_seq')"
						+ ", "
						+"upper('"
						+ txtDepartamento.getText() 
						+"'), '" + fil.get(0) +  "'," +"upper('" + txtNumeroDepartamento.getText()
						+"')"
						+ ",'"
						+ Login.userLogeado + "' , now(), '"
						+ Login.userLogeado +"' , now())");
						
				
				
				
				QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
				weatherClient.printQueryGenericoResponse(response);
				JOptionPane.showMessageDialog(null,"Excelente, se ha guardado el Departamento.");
				
				txtDepartamento.setText("");
				txtNumeroDepartamento.setText("");
				
				
				model = new CiudadesJTableModel();
				recuperarDatos();
				table.setModel(model);
				model.fireTableDataChanged();
				
				//this.dispose();
				}
				else{
					JOptionPane.showMessageDialog(null, "Debe ingresar el codigo.", "Información",JOptionPane.WARNING_MESSAGE);
				}
				
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null,"Error al intentar insertar","Error",JOptionPane.ERROR_MESSAGE);
			}
			
		}
		if (e.getSource()==botonCancelar)
		{
			this.dispose();
		}
	}


	public VentanaRegistroDistrito getVentanaRegistro() {
		return ventanaRegistro;
	}


	public void setVentanaRegistro(VentanaRegistroDistrito ventanaRegistro) {
		this.ventanaRegistro = ventanaRegistro;
	}
	
	private void recuperarDatos(){
		JSONArray filas = new JSONArray();
		JSONArray fil = new JSONArray();
		
		Genero gen = new Genero();
		
		
		boolean existe=false;
		
		
			//Statement estatuto = conex.getConnection().createStatement();
		
			
			
			ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

			WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
			QueryGenericoRequest query = new QueryGenericoRequest();
			
			//para registrar se inserta el codigo es 1
			query.setTipoQueryGenerico(2);
			
//			query.setQueryGenerico("SELECT id_genero, descripcion, to_char(fch_ins, 'DD/MM/YYYY HH24:MI:SS') as FchIns , "
//					+ "usuario_ins, to_char(fch_upd, 'DD/MM/YYYY HH24:MI:SS') as FchUpd ,usuario_upd from ucsaws_departamento ");
			
			query.setQueryGenerico("SELECT id_distrito, desc_distrito, nro_distrito , desc_departamento from ucsaws_distrito di " +
"join ucsaws_departamento de on (di.id_departamento = de.id_departamento)" +
 "order by nro_distrito");
			
			QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
			weatherClient.printQueryGenericoResponse(response);
			
			String res = response.getQueryGenericoResponse();
	
				if(res.compareTo("ERRORRRRRRR")==0){
					JOptionPane.showMessageDialog(null, "algo salio mal","Advertencia",JOptionPane.WARNING_MESSAGE);
					
				}
				
				else
			{
				existe=true;
				
				
				
				String generoAntesPartir = response.getQueryGenericoResponse();
				
				JSONParser j = new JSONParser();
				Object ob = null;
				String part1,part2,part3;
				
					
						try {
							ob = j.parse(generoAntesPartir);
						} catch (org.json.simple.parser.ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
					filas = (JSONArray) ob;
					
					
		
				
			}
			
			
		int ite = 0; 
		String campo4,campo5 = "";
		while(filas.size() >  ite ){
			 fil = (JSONArray) filas.get(ite);
			 
			 
			 
			 String[] fin
				 = {fil.get(0).toString(),  fil.get(1).toString(),  fil.get(2).toString()
					 ,
					 fil.get(3).toString()
			 };
			 
			 model.ciudades.add(fin);
			ite++;
		}
		
	}
	
	private List<Object[]> recuperarDatosComboBox(){
		JSONArray filas = new JSONArray();
		JSONArray fil = new JSONArray();
		
		Genero gen = new Genero();
		
		
		boolean existe=false;
		
		
			//Statement estatuto = conex.getConnection().createStatement();
		
			
			
			ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

			WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
			QueryGenericoRequest query = new QueryGenericoRequest();
			
			//para registrar se inserta el codigo es 1
			query.setTipoQueryGenerico(2);
			
//			query.setQueryGenerico("SELECT id_genero, descripcion, to_char(fch_ins, 'DD/MM/YYYY HH24:MI:SS') as FchIns , "
//					+ "usuario_ins, to_char(fch_upd, 'DD/MM/YYYY HH24:MI:SS') as FchUpd ,usuario_upd from ucsaws_departamento ");
			
			query.setQueryGenerico("SELECT id_departamento, desc_departamento"
				+ " from ucsaws_departamento "
					+ "order by desc_departamento");
			
			QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
			weatherClient.printQueryGenericoResponse(response);
			
			String res = response.getQueryGenericoResponse();
	
				if(res.compareTo("ERRORRRRRRR")==0){
					JOptionPane.showMessageDialog(null, "algo salio mal","Advertencia",JOptionPane.WARNING_MESSAGE);
					
				}
				
				else
			{
				existe=true;
				
				
				
				String generoAntesPartir = response.getQueryGenericoResponse();
				
				JSONParser j = new JSONParser();
				Object ob = null;
				String part1,part2,part3;
				
					
						try {
							ob = j.parse(generoAntesPartir);
						} catch (org.json.simple.parser.ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
					filas = (JSONArray) ob;
					
					
		
				
			}
			
			
		int ite = 0; 
		String campo4,campo5 = "";
		while(filas.size() >  ite ){
			 fil = (JSONArray) filas.get(ite);
			 
			 
			 
			 String[] fin
				 = {fil.get(0).toString(),  fil.get(1).toString(),  
			 };
			 
			 ciudades.add(fin);
			ite++;
		}
		return ciudades;
		
	}
}
