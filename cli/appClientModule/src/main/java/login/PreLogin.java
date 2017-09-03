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
import java.util.Iterator;
import java.util.List;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.sun.media.sound.MidiOutDeviceProvider;

import entity.Generic;
import entity.UcsawsDepartamento;
import entity.UcsawsDistrito;
import entity.UcsawsEvento;
import entity.UcsawsListas;
import entity.UcsawsVigenciaHorarioXPais;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.utils.DeQuePais;
import src.main.java.admin.utils.FechaDeOtroPaisParametrizado;
import src.main.java.admin.utils.Ip;
import src.main.java.dao.departamento.DepartamentoDAO;
import src.main.java.dao.distrito.DistritoDAO;
import src.main.java.dao.evento.EventoDAO;
import src.main.java.dao.vigencia.VigenciaDAO;
import src.main.java.hello.VentanaPrincipal;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;

//@Component
public class PreLogin extends javax.swing.JFrame {
  
  //@Autowired
  private DistritoDAO distritoDAO = new DistritoDAO();
  
 // @Autowired
  private EventoDAO eventoDAO = new EventoDAO();
	
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
        		
        		Integer vigente;
        		
        		vigente = eventoVigente();
        		
        		if(vigente == 0){
        			JOptionPane.showMessageDialog(null, "No existe ningun evento vigente.",
        					"Advertencia", JOptionPane.WARNING_MESSAGE);
        		}
        		if(vigente == 3){
        		  JOptionPane.showMessageDialog(null, "No existe ningun evento vigente.",
                      "Evento Finalizado.", JOptionPane.WARNING_MESSAGE);
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



    


    
    public static void main(String args[]) throws ClassNotFoundException, 
    InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
      
    //  ApplicationContext context = 
      //    new ClassPathXmlApplicationContext("META-INF/config.xml");
      
     // PreLogin p = context.getBean(PreLogin.class);
      
      PreLogin.comenzar();
        
  
    }
    
    
    static void comenzar(){
      
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
	public  Integer eventoVigente() {
		String result ="";
		String MiPais = "";
		String paisDescripcion= "";
		
		DeQuePais ip = new DeQuePais();
		paisDescripcion = ip.main();
		
		if (paisDescripcion.compareToIgnoreCase("brazil")==0){
		  paisDescripcion="BRASIL";
		}
		
		/*//obtener pais desde donde se quiere votar
		
		String paisSigla = System.getProperty("user.country");
		
		
		if(paisSigla.toUpperCase().compareTo("PY")==0){
		  paisDescripcion = "PARAGUAY";
		}
		else if (paisSigla.toUpperCase().compareTo("US")==0){
		  paisDescripcion = "ESTADOS UNIDOS";
		}
		else if (paisSigla.toUpperCase().compareTo("ES")==0){
		  paisDescripcion = "ESPAÑA";
		}
		//obtener pais desde donde se quiere votar*/
		
		
		UcsawsEvento eventoVigente = eventoDAO.obtenerEventoVigente();
		if(eventoVigente.getIdEvento() == null){
		  //JOptionPane.showMessageDialog(null, "No está habilitado para votar desde este pais: " + paisSigla);
		  return 0;
		}
		List<UcsawsDistrito>  listas =distritoDAO.obtenerDistritoByIdEvento(eventoVigente.getIdEvento());
		
		Iterator<UcsawsDistrito> ite = listas.iterator();

		UcsawsDistrito aux;
	    while (ite.hasNext()) {
	        aux = ite.next();
	        if (aux.getDescDistrito().toUpperCase().compareTo(paisDescripcion.toUpperCase())==0){
	          MiPais = aux.getDescDistrito();
	          break;
	        }
	       
	        
	    }
		if (MiPais.compareTo("")==0){
           JOptionPane.showMessageDialog(null, "No está habilitado para votar desde este pais: " + paisDescripcion);
           return 2;
         }
		else{
		   
		  if (MiPais.compareTo("PARAGUAY")!=0){
		    UcsawsVigenciaHorarioXPais v = new UcsawsVigenciaHorarioXPais() ;
		    //verificar la lo hora de acuerdo al pais
		    VigenciaDAO vigenciaDAO = new VigenciaDAO();
		    
		    try{
		    List<UcsawsVigenciaHorarioXPais> vigencias = vigenciaDAO.obtenerVigenciaByEvento(eventoVigente);
		    Iterator<UcsawsVigenciaHorarioXPais> ite2 = vigencias.iterator();
		    UcsawsVigenciaHorarioXPais aux2;
		    
		        while (ite2.hasNext()) {
		            aux2 = ite2.next();
		            if (aux2.getIdPais().getNombre().toUpperCase().compareTo(MiPais.toUpperCase())==0){
		              v = aux2;
		            }
		           
		            
		        }
		    
		    }
		    catch(Exception e){
		      System.out.println(e);
		    }
		    
		    //BLOQUE COMPARACION FECHA
		    Date fechaHoy = new Date();
		    Date FechaVigenciaDesde = v.getFchVigenciaDesde();
		    Date FechaVigenciaHasta = v.getFchVigenciaHasta();
		    
		    if (!(fechaHoy.after(FechaVigenciaDesde) &&  fechaHoy.before(FechaVigenciaHasta))){
		         return 3;


		    }
		    
		  //BLOQUE COMPARACION FECHA
		  
		    else{
		 
		  
		  
	

		

		 
		result = eventoVigente.getIdEvento().toString();
		 
		VentanaBuscarEvento.evento = result;
		
		return Integer.parseInt(result);
		    }
		}
		}
		result = eventoVigente.getIdEvento().toString();
        
        VentanaBuscarEvento.evento = result;
        
        return Integer.parseInt(result);
		
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
