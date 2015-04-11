package src.main.java.admin.departamento;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import src.main.java.admin.Coordinador;
import src.main.java.hello.Genero;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;

import javax.swing.JScrollPane;


public class VentanaRegistroDepartamento extends JFrame implements ActionListener{

	private Coordinador miCoordinador; //objeto miCoordinador que permite la relacion entre esta clase y la clase coordinador
	private JLabel labelTitulo;
	private JTextField txtDepartamento;
	private JLabel lblNombreDepartamento;
	private JButton botonGuardar,botonCancelar;
	private VentanaRegistroDepartamento ventanaRegistro;
	
	private CiudadesJTableModel model = new CiudadesJTableModel();
	private JTextField txtNumeroDepartamento;
	private JTable table;
	
	private JScrollPane scrollPane;
	
	
	/**
	 * constructor de la clase donde se inicializan todos los componentes
	 * de la ventana de registro
	 */
	public VentanaRegistroDepartamento() {

		botonGuardar = new JButton();
		botonGuardar.setBounds(239, 154, 120, 25);
		botonGuardar.setText("Registrar");
		
		botonCancelar = new JButton();
		botonCancelar.setBounds(384, 154, 120, 25);
		botonCancelar.setText("Cancelar");

		labelTitulo = new JLabel();
		labelTitulo.setText("REGISTRO DE DEPARTAMENTOS.");
		labelTitulo.setBounds(207, 28, 380, 30);
		labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));

		lblNombreDepartamento=new JLabel();
		lblNombreDepartamento.setText("Departamento:");
		lblNombreDepartamento.setBounds(239, 101, 80, 25);
		getContentPane().add(lblNombreDepartamento);
		
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
		//recuperarDatos();
		
		txtNumeroDepartamento = new JTextField();
		txtNumeroDepartamento.setText("");
		txtNumeroDepartamento.setBounds(329, 69, 80, 25);
		getContentPane().add(txtNumeroDepartamento);
		
		JLabel lblNumeroDepartamento = new JLabel();
		lblNumeroDepartamento.setText("Nro. Departamento:");
		lblNumeroDepartamento.setBounds(217, 69, 102, 25);
		getContentPane().add(lblNumeroDepartamento);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 211, 806, 204);
		getContentPane().add(scrollPane);
		
		table = new JTable();
		table.setBounds(74, 226, 665, 178);
		table.setToolTipText("Listado de Generos");
		table.setAutoCreateRowSorter(true);
		scrollPane.setViewportView(table);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setModel(model);
		recuperarDatos();
		
		
		

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
				
				Genero genero = new Genero();
				genero.setDescripcion(txtDepartamento.getText());
				
				
				ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

				WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
				QueryGenericoRequest query = new QueryGenericoRequest();
				
				//para registrar se inserta el codigo es 1
				query.setTipoQueryGenerico(1);
				System.out.println(Login.userLogeado);
				query.setQueryGenerico("INSERT INTO ucsaws_departamento"
						+ "( id_departamento, desc_departamento, nro_departamento, usuario_ins,fch_ins, usuario_upd, fch_upd) "
						+ "VALUES ("
						+ "nextval('ucsaws_departamento_seq')"
						+ ", "
						+"upper('"
						+ txtDepartamento.getText() 
						+"'), " + txtNumeroDepartamento.getText()
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


	public VentanaRegistroDepartamento getVentanaRegistro() {
		return ventanaRegistro;
	}


	public void setVentanaRegistro(VentanaRegistroDepartamento ventanaRegistro) {
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
			
			query.setQueryGenerico("SELECT id_departamento, desc_departamento, nro_departamento, to_char(fch_ins, 'DD/MM/YYYY HH24:MI:SS') as FchIns , "
				+ "usuario_ins, to_char(fch_upd, 'DD/MM/YYYY HH24:MI:SS') as FchUpd ,usuario_upd from ucsaws_departamento "
					+ "order by nro_departamento");
			
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
					 fil.get(3).toString(),  fil.get(4).toString() , fil.get(5).toString()
					 ,
					 fil.get(6).toString()
			 };
			 
			 model.ciudades.add(fin);
			ite++;
		}
		
	}
}
