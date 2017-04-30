package src.main.java.login;
import hello.wsdl.AutenticarRequest;
import hello.wsdl.AutenticarResponse;
import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.BasicConfigurator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.jfree.date.DateUtilities;
import org.joda.time.DateTime;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import entity.Generic;
import entity.UcsawsEvento;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.utils.DeQuePais;
import src.main.java.admin.utils.FechaDeOtroPaisParametrizado;
import src.main.java.admin.utils.Ip;
import src.main.java.hello.VentanaPrincipal;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;

public class PreLogin extends javax.swing.JFrame {
	
	 public static String userLogeado;
	 public static String nombreApellidoUserLogeado;
	 
	 public static final Integer timer = 3000;
	 
	 JButton btnVotante;
	 

    public PreLogin()
    {
    	getContentPane().addKeyListener(new KeyAdapter() {
    		@Override
    		public void keyPressed(KeyEvent e) {
    			
    			if(e.getKeyCode()==KeyEvent.VK_ENTER){
   				 
   				 if(e.getSource() == btnAdmin && btnAdmin.isFocusOwner())
   				  
   				  System.out.println("admin");
   			 }
    			
    		}
    	});
    	
    	setResizable(false);
    	setIconImage(Toolkit.getDefaultToolkit().getImage(PreLogin.class.getResource("/imgs/paraguay.png")));
        initComponents();
        this.setBounds(0,0, 419, 190);
        this.setLocationRelativeTo(null);
       // this.setLocation(0, 0);
        
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0),"clickButton");

        		getRootPane().getActionMap().put("clickButton",new AbstractAction(){
        		        public void actionPerformed(ActionEvent ae)
        		        {
        		        	
        		        	if(btnAdmin.isFocusOwner()){
        		        		System.out.println("button clicked admin");
        		        		btnAdmin.doClick();
        		        	}
        		        	
        		        	if (btnVotante.isFocusOwner()){
        		        		System.out.println("button clicked votante");
        		        		btnVotante.doClick();
        		        		
        		        }
        		        	
        		    
        		        }
        		    });
        		    
    }



    private void ingresar()
    {
       // clases.utilidades.Global.usuario=campoUsuario.getText();
        VentanaPrincipal miVentanaPrincipal;
		/*Instanciamos el objeto*/
		miVentanaPrincipal= new VentanaPrincipal();
		/*Enviamos el objeto como parametro para que sea unico
		 * en toda la aplicaci�n*/
		miVentanaPrincipal.setVentanaPrincipal(miVentanaPrincipal);
		/*Hacemos que se cargue la ventana*/
		miVentanaPrincipal.setVisible(true);
        this.dispose();
		
    }













    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        btnAdmin = new javax.swing.JButton();

        labelTitulo = new javax.swing.JLabel();

        jLabel3.setText("jLabel3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema E-vote: Paraguay Elecciones 2015");
        btnAdmin.setText("ADMINISTRADOR");
        btnAdmin.setIcon(new ImageIcon(PreLogin.class.getResource("/imgs/admin.png")));
		
        Image img2 = ((ImageIcon) btnAdmin.getIcon()).getImage();
		Image newimg2 = img2.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		
		btnAdmin.setIcon(new ImageIcon(newimg2));
		
        btnAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	IrLogin();
  				
            }
        });

        labelTitulo.setText("Ver. 1.0.0");
        
        btnVotante = new JButton("VOTANTE");
       
        btnVotante.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		
        		
        		
        		if(eventoVigente() == 0){
        			JOptionPane.showMessageDialog(null, "No existe ningun evento vigente.",
        					"Advertencia", JOptionPane.WARNING_MESSAGE);
        		}
        		else
        		{
        		EleccionMesa mesa = new EleccionMesa();
        		mesa.setVisible(true);
        		dispose();
        		}
        	}
        });
        btnVotante.setIcon(new ImageIcon(PreLogin.class.getResource("/imgs/votante.png")));
        btnVotante.setBounds(415, 52, 32, 32);
       // btnVotante.setOpaque(false);
      //  btnVotante.setContentAreaFilled(false);
       // btnVotante.setBorderPainted(false);
		Image img = ((ImageIcon) btnVotante.getIcon()).getImage();
		Image newimg = img.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		btnVotante.setIcon(new ImageIcon(newimg));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap(423, Short.MAX_VALUE)
        			.addComponent(labelTitulo)
        			.addGap(16))
        		.addGroup(Alignment.TRAILING, layout.createSequentialGroup()
        			.addGap(87)
        			.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        				.addComponent(btnAdmin, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
        				.addComponent(btnVotante, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE))
        			.addGap(97))
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(layout.createSequentialGroup()
        			.addGap(33)
        			.addComponent(btnVotante)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(btnAdmin, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(labelTitulo)
        			.addContainerGap())
        );
        getContentPane().setLayout(layout);

        pack();
        
        

    }// </editor-fold>//GEN-END:initComponents



    


  
    public static void main(String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
    	try {   
    		
    		BasicConfigurator.configure();
    		  javax.swing.UIManager.setLookAndFeel("com.jgoodies.looks.windows.WindowsLookAndFeel");               
    		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
    		  java.util.logging.Logger.getLogger(PreLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    		}
    	
    	
    	//UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new PreLogin().setVisible(true);
                PreLogin frame = new PreLogin();
                frame.setVisible(true);
            	frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				frame.addWindowListener(new WindowAdapter() {
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
            }
        });
    }
    private javax.swing.JButton btnAdmin;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel labelTitulo;
    // End of variables declaration//GEN-END:variables
    

	
	public String consultar(String codigoUsuario, String contrasena) throws Exception{

		
	    try {
	   
	        ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

			WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
			AutenticarRequest autenticar = new AutenticarRequest();
			
			autenticar.setUsuario(codigoUsuario);
			autenticar.setContrasenha(contrasena);			
			
			
			AutenticarResponse response = weatherClient.getAutenticarResponse(autenticar);
			weatherClient.printAutenticarResponse(response);
			
			if(response.getCodigo().toString().compareTo("12345")==0){
				//se autentica un admin
				return response.getCodigo().toString();
			}
			
			else
				if(response.getCodigo().toString().compareTo("54321")==0){
					//se autentica un votante
					return response.getCodigo().toString();
				}
	        
	        else{
	        	System.out.println("Connection Failed!");
	        	return "ERROR";
	        	
	        }
	    }catch (Exception e) {
	    	 throw new Exception(e);
	    } 
	//return null;
}
	
	void IrLogin(){
		 Login l = new Login();
			l.setVisible(true);
			this.dispose();
	}

	//se adapta para que verifique el pais desde donde se esta accediendo
	public static Integer eventoVigente() {
		String result ="";
		
		//obtener pais desde donde se quiere votar
		DeQuePais ip = new DeQuePais();
		String MiPais = ip.main().toUpperCase();
		System.out.println(MiPais);
		//
		String fechaHusoHora= "";
		Date fechaHusoHoraDate;
		
		if(!(MiPais.compareTo("PARAGUAY")==0)){
		//obtener huso horario
		FechaDeOtroPaisParametrizado fechaDeOtroPaisParametrizado = new FechaDeOtroPaisParametrizado();
		DateTime huso = fechaDeOtroPaisParametrizado.main(MiPais);
		//obtener huso horario
		
		 System.out.println(huso.getZone());
		 fechaHusoHora =   huso.getDayOfMonth() + "/" + huso.getMonthOfYear() + "/" + huso.getYear() + " " + huso.getHourOfDay() + ":" + huso.getMinuteOfHour() + ":" + huso.getSecondOfMinute();
		
		//armar fecha hora
		
		
		}
		else{
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
	        String dateInString = fechaHusoHora;

	        

	            Date date = new Date();
				try {
					date = formatter.parse(dateInString);
				} catch (java.text.ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            System.out.println(date);
	            System.out.println(formatter.format(date));

	        

	       
			fechaHusoHora = "current_date";
			
			ObjectMapper mapperObj = new ObjectMapper();
			String jsonStr = "";
			try {
				// get Employee object as a json string
				jsonStr = mapperObj.writeValueAsString(date);
				System.out.println(jsonStr);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}

		JSONArray filas = new JSONArray();
		JSONArray fil = new JSONArray();

		Object ob = null;

		ApplicationContext ctx = SpringApplication
				.run(WeatherConfiguration.class);

		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
		QueryGenericoRequest query = new QueryGenericoRequest();

		// para registrar se inserta el codigo es 1
		query.setTipoQueryGenerico(1);
		System.out.println(Login.userLogeado);
		//query.setQueryGenerico("select id_evento, descripcion from ucsaws_evento where cast(fch_hasta as timestamp) <= cast (now() as timestamp)");
		
		
		if((MiPais.compareTo("PARAGUAY")==0)){
		query.setQueryGenerico(jsonStr);

		}else{
			query.setQueryGenerico("select id_vigencia, id_pais "
					+ " from ucsaws_vigencia_horario_x_pais where to_char(fch_vigencia_desde, 'DD/MM/YYYY HH24:MI')  <= to_char(to_timestamp('"+ fechaHusoHora +"', 'DD/MM/YYYY HH24:MI'),'DD/MM/YYYY HH24:MI')  "
					+ " and (to_char(fch_vigencia_hasta, 'DD/MM/YYYY HH24:MI')  >= to_char(to_timestamp('"+ fechaHusoHora +"', 'DD/MM/YYYY HH24:MI'),'DD/MM/YYYY HH24:MI')) "
					//+ " or (to_char(fch_hasta, 'DD/MM/YYYY HH24:MI')  < to_char(now(), 'DD/MM/YYYY HH24:MI')  )"
					);
			
			
		}
		
	 

		QueryGenericoResponse response = weatherClient
				.getQueryGenericoResponse(query);
		weatherClient.printQueryGenericoResponse(response);

		JSONParser j = new JSONParser();

		String generoAntesPartir = response.getQueryGenericoResponse();
		
		if (generoAntesPartir.compareTo("NO")==0){
			return 0;
		}
		
		else{
			
			//json string to java object;
			ObjectMapper mapper = new ObjectMapper();
			String jsonInString  =response.getQueryGenericoResponse();
			try {
				UcsawsEvento fecha = mapper.readValue(jsonInString, UcsawsEvento.class);
				result = fecha.getIdEvento().toString();
			} catch (JsonParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (JsonMappingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		

		

		 
		
		VentanaBuscarEvento.evento = result;
		
		return Integer.parseInt(result);
		
		}
		}
		return null;
		
		}
		
	
	

	
	public static Integer eventoVigenteConParametros(Date fechaDesde , Date fechaHasta, Integer idEvento) {

		JSONArray filas = new JSONArray();
		JSONArray fil = new JSONArray();

		Object ob = null;

		ApplicationContext ctx = SpringApplication
				.run(WeatherConfiguration.class);

		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
		QueryGenericoRequest query = new QueryGenericoRequest();

		// para registrar se inserta el codigo es 1
		query.setTipoQueryGenerico(35);
		System.out.println(Login.userLogeado);
		//query.setQueryGenerico("select id_evento, descripcion from ucsaws_evento where cast(fch_hasta as timestamp) <= cast (now() as timestamp)");
		
		
		Generic g = new Generic();
		g.setDesde(fechaDesde);
		g.setHasta(fechaHasta);
		g.setId(idEvento);
		//parseo json
		ObjectMapper mapperObj = new ObjectMapper();
		String jsonStr = "";
		try {
			// get Employee object as a json string
			jsonStr = mapperObj.writeValueAsString(g);
			System.out.println(jsonStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	}
		
		query.setQueryGenerico(jsonStr);
//		("select id_evento, descripcion "
//				+ " from ucsaws_evento where (  '" + fechaDesde + "'   <= to_char(now(), 'DD/MM/YYYY HH24:MI') "
//				+ " and '" + fechaHasta + "'  >= to_char(now(), 'DD/MM/YYYY HH24:MI') and id_evento = " + idEvento + ")"
//				+ " or ( '" + fechaHasta + "'  < to_char(now(), 'DD/MM/YYYY HH24:MI') and id_evento = " + idEvento + " )"
//				);
		
//		("select id_evento, descripcion "
//				+ " from ucsaws_evento where (  fch_desde   <= now() "
//				+ " and fch_hasta  >= now() and id_evento = " + idEvento + ")"
//				+ " or ( fch_hasta  <  now() and id_evento = " + idEvento + " )"
//				);
		
		
	 

		QueryGenericoResponse response = weatherClient
				.getQueryGenericoResponse(query);
		weatherClient.printQueryGenericoResponse(response);
		
		
		// json string to List<String>;
		ObjectMapper mapper = new ObjectMapper();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		mapper.setDateFormat(df);
		String jsonInString = response.getQueryGenericoResponse();
		
		UcsawsEvento evento  = new UcsawsEvento();
		
		try{
		evento = mapper.readValue(jsonInString, UcsawsEvento.class);
		}
		catch(Exception e){
			System.out.println(e);
		}
		
		
		
		
		if (evento.getIdEvento() == null){
			return 0;
		}
		
		else{
			
		 
		
		VentanaBuscarEvento.evento = evento.getIdEvento().toString();

		return 1;
		
		}

	}
	
	
}
