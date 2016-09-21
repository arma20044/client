package src.main.java.login;
import hello.wsdl.AutenticarRequest;
import hello.wsdl.AutenticarResponse;
import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import src.main.java.admin.MenuPrincipal;
import src.main.java.admin.utils.StringEncrypter;
import src.main.java.hello.VentanaPrincipal;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;

import java.awt.Color;
import java.awt.KeyEventPostProcessor;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.NoSuchAlgorithmException;

import javax.swing.UIManager;

import java.awt.event.KeyAdapter;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowEvent;

public class Login extends javax.swing.JFrame implements KeyListener {
	
	 public static String userLogeado;
	 public static String nombreApellidoUserLogeado;
	 public static String email;
	 
	 public static final Integer timer = 3000;
	 

    public Login()
    {
    	addKeyListener(new KeyAdapter() {
    		@Override
    		public void keyPressed(KeyEvent e) {
    			System.out.println("holaaaaaa");
    		}
    	});
    
 
    
    	setResizable(false);
    	setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/imgs/paraguay.png")));
        initComponents();
        this.setBounds(0,0, 417, 250);
        this.setLocationRelativeTo(null);
    }

    private Integer autenticar()
    {
        boolean autenticado=false;
        try
          {
        String u = campoUsuario.getText();
        String p =  campoContrasena.getText();
        
        String logroAutenticarse = null;
        
        logroAutenticarse = consultar(u, p);
        
        
        if(	logroAutenticarse.compareTo("12345")==0){
        	System.out.println(u);
        	System.out.println(p);
        
                  //if(new Usuarios().autenticar(campoUsuario.getText(),campoContrasena.getText()))
                
                       /// if(!campoUsuario.getText().equals(campoContrasena.getText())) {
                            autenticado=true;
//                            JOptionPane.showMessageDialog(this,
//                                    "ADMIN - Inicio de sesion correcto",
//                                    "Correcto",
//                                    JOptionPane.INFORMATION_MESSAGE);
                            
                           
                           
                            
//                        }
//                         else {
//                            cambioContrasena chgPwd = new cambioContrasena(campoUsuario.getText());
//                            chgPwd.setModal(true);
//                            chgPwd.setVisible(true);
                              return 12345;
                         }
        else
        	if(logroAutenticarse.compareTo("54321")==0){
        		  autenticado=true;
//                  JOptionPane.showMessageDialog(this,
//                          "VOTANTE - Inicio de sesion correcto",
//                          "Correcto",
//                          JOptionPane.INFORMATION_MESSAGE);
                  // clases.utilidades.Global.usuario=campoUsuario.getText();
               
        		return 54321;
        	}
                  
//                  else
//                  {
//                     if(campoContrasena.getText().equals(clases.utilidades.MasterPwd.generarPassword())) {
//                         JOptionPane.showMessageDialog(null, "Ingreso con una contraseña maestra");
//                         autenticado = true;
//                         
//                         clases.utilidades.MasterPwd.bloquearPassword(clases.utilidades.MasterPwd.generarPassword());
//                     }
                     else {
                        JOptionPane.showMessageDialog(this,
                        "No se encuentra la combinación usuario y contraseña",
                        "No se puede autenticar",
                        JOptionPane.INFORMATION_MESSAGE);
                     }
                  
                  return 9999;
          }
          catch(Exception e)
          {
        	  e.printStackTrace();
              JOptionPane.showMessageDialog(this,
                "Se ha producido la siguiente excepción: "+e,
                "Se ha producido una excepción",
                JOptionPane.ERROR_MESSAGE);
          }

              return 9999;
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
        labelUsuario = new javax.swing.JLabel();
        campoUsuario = new javax.swing.JTextField();
       
        campoUsuario.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusLost(FocusEvent arg0) {
        		if (campoUsuario.getText().length() == 0 ){
        			campoUsuario.setBackground(Color.decode("#FF0000"));
        		}
        		else if (campoUsuario.getText().length() > 0){
        			campoUsuario.setBackground(UIManager.getColor("info"));
        				
        		      			
        		}
        		else{
        			campoUsuario.setBackground(Color.white);
        		}
        	}
        });
        labelContrasena = new javax.swing.JLabel();
        botonEntrar = new javax.swing.JButton();
        botonEntrar.setIcon(new ImageIcon(Login.class.getResource("/imgs/key.png")));
        btnAtras = new javax.swing.JButton();
        labelImagen = new javax.swing.JLabel();
        campoContrasena = new javax.swing.JPasswordField();
        campoContrasena.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusLost(FocusEvent arg0) {
        		if (campoContrasena.getText().length() == 0 ){
        			campoContrasena.setBackground(Color.decode("#FF0000"));
        		}
        		else{
        			campoContrasena.setBackground(Color.white);
        		}
        	}
        	@Override
        	public void focusGained(FocusEvent arg0) {
        	//	char car = arg0.getKeyListeners().getKeyChar();
        	
        	}
        });
        labelTitulo = new javax.swing.JLabel();

        jLabel3.setText("jLabel3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema E-vote: Paraguay Elecciones 2015");

        labelUsuario.setText("Usuario");

        campoUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                campoUsuarioKeyPressed(evt);
            }
        });

        labelContrasena.setText("Contraseña");

        //botonEntrar.setIcon(new ImageIcon(Login.class.getResource("/imgs/llaves.png"))); // NOI18N
        botonEntrar.setText("Entrar");
        botonEntrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                	if(campoUsuario.getText().length()>0 && campoContrasena.getPassword().length > 0){
					botonEntrarActionPerformed(evt);}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });

        btnAtras.setText("Atras");
        btnAtras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSalirActionPerformed(evt);
            }
        });

        labelImagen.setIcon(new ImageIcon(Login.class.getResource("/imgs/PadLock-128.png"))); // NOI18N

        campoContrasena.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                try {
                	if(campoUsuario.getText().length()>0 && campoContrasena.getPassword().length > 0){
               
					campoContrasenaKeyPressed(evt);
                	 }
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });

        labelTitulo.setText("Ver. 1.0.0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        layout.setHorizontalGroup(
        	layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(layout.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(labelImagen)
        			.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        				.addGroup(layout.createSequentialGroup()
        					.addGap(18)
        					.addGroup(layout.createParallelGroup(Alignment.LEADING)
        						.addGroup(layout.createSequentialGroup()
        							.addComponent(botonEntrar, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(btnAtras, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE))
        						.addGroup(Alignment.TRAILING, layout.createSequentialGroup()
        							.addGroup(layout.createParallelGroup(Alignment.TRAILING)
        								.addComponent(labelContrasena)
        								.addComponent(labelUsuario))
        							.addGap(18)
        							.addGroup(layout.createParallelGroup(Alignment.LEADING, false)
        								.addComponent(campoUsuario)
        								.addComponent(campoContrasena, GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))))
        					.addContainerGap(26, GroupLayout.PREFERRED_SIZE))
        				.addGroup(layout.createSequentialGroup()
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(labelTitulo)
        					.addContainerGap())))
        );
        layout.setVerticalGroup(
        	layout.createParallelGroup(Alignment.TRAILING)
        		.addGroup(layout.createSequentialGroup()
        			.addGap(36)
        			.addGroup(layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(layout.createSequentialGroup()
        					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        						.addComponent(campoUsuario, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
        						.addComponent(labelUsuario))
        					.addGap(18)
        					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        						.addComponent(campoContrasena, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
        						.addComponent(labelContrasena))
        					.addGap(27)
        					.addGroup(layout.createParallelGroup(Alignment.BASELINE)
        						.addComponent(botonEntrar, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
        						.addComponent(btnAtras, GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(labelTitulo))
        				.addComponent(labelImagen, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE))
        			.addContainerGap())
        );
        getContentPane().setLayout(layout);

        pack();
        getRootPane().setDefaultButton(botonEntrar);
    }// </editor-fold>//GEN-END:initComponents

    private void botonEntrarActionPerformed(java.awt.event.ActionEvent evt) throws ParseException {//GEN-FIRST:event_botonEntrarActionPerformed
    	
    	Integer autenticado = autenticar();
    	
       if(autenticado == 12345){ //admin
    	   
    	   
    	
    	   
    	   userLogeado = campoUsuario.getText();
    	   
    
    	   
    	   ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

   		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
   		QueryGenericoRequest query = new QueryGenericoRequest();
   		
   		//para registrar se inserta el codigo es 1
   		query.setTipoQueryGenerico(2);
   		
   		query.setQueryGenerico("select nombre , apellido, email "
   								+ "from ucsaws_persona per join ucsaws_users us on (per.id_persona = us.id_persona)"
   								+ "where usuario = '" + userLogeado + "'");
   		
   		
   		JSONArray filas = new JSONArray();
   		QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
   		weatherClient.printQueryGenericoResponse(response);
   		
   		String res = response.getQueryGenericoResponse();
   		
   		String generoAntesPartir = response.getQueryGenericoResponse();
			
			JSONParser j = new JSONParser();
			Object ob;
			String part1,part2,part3;
			
				ob = j.parse(generoAntesPartir);
				filas = (JSONArray) ob;
				JSONArray a = (JSONArray) filas.get(0);
				
				nombreApellidoUserLogeado = (String) a.get(0)+ " " + a.get(1);
				email = (String) a.get(2);
    	   /*VentanaBuscar miVentanaPrincipal;
     		Instanciamos el objeto
     		miVentanaPrincipal= new VentanaBuscar();
     		Enviamos el objeto como parametro para que sea unico
     		 * en toda la aplicaci�n
     		//miVentanaPrincipal.setVentanaBuscar(miVentanaPrincipal);
     		Hacemos que se cargue la ventana
     		miVentanaPrincipal.setVisible(true);
             this.dispose();*/
//    	   VentanaRegistro miVentanaPrincipal;
//    		/*Instanciamos el objeto*/
//    		miVentanaPrincipal= new VentanaRegistro();
//    		/*Enviamos el objeto como parametro para que sea unico
//    		 * en toda la aplicaci�n*/
//    		//miVentanaPrincipal.setVentanaBuscar(miVentanaPrincipal);
//    		/*Hacemos que se cargue la ventana*/
//    		miVentanaPrincipal.setVisible(true);
//            this.dispose();
            MenuPrincipal menuPrincipal = new MenuPrincipal();
    		/*Enviamos el objeto como parametro para que sea unico
    		 * en toda la aplicaci�n*/
    		//miVentanaPrincipal.setVentanaBuscar(miVentanaPrincipal);
    		/*Hacemos que se cargue la ventana*/
            menuPrincipal.setVisible(true);
            this.dispose();
            
           
            
            
				
       }
       
       else
    	   if(autenticado == 54321){ //votante
//    		   VentanaPrincipal miVentanaPrincipal;
//         		/*Instanciamos el objeto*/
//         		miVentanaPrincipal= new VentanaPrincipal();
//         		/*Enviamos el objeto como parametro para que sea unico
//         		 * en toda la aplicaci�n*/
//         		miVentanaPrincipal.setVentanaPrincipal(miVentanaPrincipal);
//         		/*Hacemos que se cargue la ventana*/
//         		miVentanaPrincipal.setVisible(true);
//                 this.dispose();
    		   JOptionPane.showMessageDialog(null,
                       "Solo pueden acceder a ésta área los Administradores.",
                       "ERROR.",
                       JOptionPane.INFORMATION_MESSAGE);
    		   
    	   }
    		   //)
//          {
//              ingresar();
//          }
    }//GEN-LAST:event_botonEntrarActionPerformed

    private void botonSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSalirActionPerformed
    	PreLogin pL = new PreLogin();
    	pL.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_botonSalirActionPerformed

    private void campoUsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoUsuarioKeyPressed
        if(evt.getKeyCode()==10)
        {
            campoContrasena.requestFocus();
        }
    }//GEN-LAST:event_campoUsuarioKeyPressed

    private void campoContrasenaKeyPressed(java.awt.event.KeyEvent evt) throws ParseException {//GEN-FIRST:event_campoContrasenaKeyPressed
      if(evt.getKeyCode()==KeyEvent.VK_ENTER)
        {
    		Integer autenticado = autenticar();
        	
    	       if(autenticado == 12345){ //admin
    	    	   
    	    	   userLogeado = campoUsuario.getText();
    	    	   
    	    	   ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

   	    		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
   	    		QueryGenericoRequest query = new QueryGenericoRequest();
   	    		
   	    		//para registrar se inserta el codigo es 1
   	    		query.setTipoQueryGenerico(2);
   	    		
   	    		query.setQueryGenerico("select nombre , apellido, email "
   	    								+ "from ucsaws_persona per join ucsaws_users us on (per.id_persona = us.id_persona)"
   	    								+ "where usuario = '" + userLogeado + "'");
   	    		
   	    		
   	    		JSONArray filas = new JSONArray();
   	    		QueryGenericoResponse response = weatherClient.getQueryGenericoResponse(query);
   	    		weatherClient.printQueryGenericoResponse(response);
   	    		
   	    		String res = response.getQueryGenericoResponse();
   	    		
   	    		String generoAntesPartir = response.getQueryGenericoResponse();
   				
   				JSONParser j = new JSONParser();
   				Object ob;
   				String part1,part2,part3;
   				
   					ob = j.parse(generoAntesPartir);
   					filas = (JSONArray) ob;
   					JSONArray a = (JSONArray) filas.get(0);
   					
   					nombreApellidoUserLogeado = (String) a.get(0)+ " " + a.get(1);
   					email = (String) a.get(2);
//    	    	   VentanaBuscar miVentanaPrincipal;
//    	     		/*Instanciamos el objeto*/
//    	     		miVentanaPrincipal= new VentanaBuscar();
//    	     		/*Enviamos el objeto como parametro para que sea unico
//    	     		 * en toda la aplicaci�n*/
//    	     		miVentanaPrincipal.setVentanaBuscar(miVentanaPrincipal);
//    	     		/*Hacemos que se cargue la ventana*/
//    	     		miVentanaPrincipal.setVisible(true);
//    	             this.dispose();
//    	    	   VentanaRegistro miVentanaPrincipal;
//    	    		/*Instanciamos el objeto*/
//    	    		miVentanaPrincipal= new VentanaRegistro();
//    	    		/*Enviamos el objeto como parametro para que sea unico
//    	    		 * en toda la aplicaci�n*/
//    	    		//miVentanaPrincipal.setVentanaBuscar(miVentanaPrincipal);
//    	    		/*Hacemos que se cargue la ventana*/
//    	    		miVentanaPrincipal.setVisible(true);
//    	            this.dispose();
   					MenuPrincipal menuPrincipal = new MenuPrincipal();
   		    		/*Enviamos el objeto como parametro para que sea unico
   		    		 * en toda la aplicaci�n*/
   		    		//miVentanaPrincipal.setVentanaBuscar(miVentanaPrincipal);
   		    		/*Hacemos que se cargue la ventana*/
   		            menuPrincipal.setVisible(true);
   		            this.dispose();
    	           
    	            
    	         
    	       }
    	       
    	       else
    	    	   if(autenticado == 54321){ //votante
//    	    		   VentanaPrincipal miVentanaPrincipal;
//    	         		/*Instanciamos el objeto*/
//    	         		miVentanaPrincipal= new VentanaPrincipal();
//    	         		/*Enviamos el objeto como parametro para que sea unico
//    	         		 * en toda la aplicaci�n*/
//    	         		miVentanaPrincipal.setVentanaPrincipal(miVentanaPrincipal);
//    	         		/*Hacemos que se cargue la ventana*/
//    	         		miVentanaPrincipal.setVisible(true);
//    	                 this.dispose();
    	    		   JOptionPane.showMessageDialog(null,
    	                       "Solo pueden acceder a ésta área los Administradores.",
    	                       "ERROR.",
    	                       JOptionPane.INFORMATION_MESSAGE);
    	    		   
    	    	   }
        }
    }//GEN-LAST:event_campoContrasenaKeyPressed

  
//    public static void main(String args[]) {
//    	try {   
//    		  javax.swing.UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");               
//    		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
//    		  java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//    		}
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Login().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonEntrar;
    private javax.swing.JButton btnAtras;
    private javax.swing.JPasswordField campoContrasena;
    private javax.swing.JTextField campoUsuario;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel labelContrasena;
    private javax.swing.JLabel labelImagen;
    private javax.swing.JLabel labelTitulo;
    private javax.swing.JLabel labelUsuario;
    // End of variables declaration//GEN-END:variables
    

	
	public String consultar(String codigoUsuario, String contrasena) throws Exception{

		
	    try {
	   
	        ApplicationContext ctx = SpringApplication.run(WeatherConfiguration.class);

			WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
			AutenticarRequest autenticar = new AutenticarRequest();
			
			autenticar.setUsuario(codigoUsuario);
			
			   //desencriptar user
//	    	   try {
//	    	   
//	    	   SecretKey desKey       = KeyGenerator.getInstance("DESede").generateKey();
//	    	   
//	    	   StringEncrypter desEncrypter = new StringEncrypter(desKey, desKey.getAlgorithm());
//	    	   
//	    	   
//	    	    
//	    	    String desEncrypted       = desEncrypter.encrypt(campoUsuario.getText());
//	    	   
//	    	   
//	    	   
//	    	   
//	    	  
//	    	   
//	    	   String desDecrypted       = desEncrypter.decrypt(desEncrypted);
//	    	   
//	    	   
//	    		
//	    	   
//	    	   //desecnriptar user
//			   
//			   
//
//	    	   autenticar.setContrasenha(desEncrypted);
//	    	   
//	    	   } catch (NoSuchAlgorithmException e) {
//	  	     }
	    	   
			
			
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

	@Override
	public void keyPressed(KeyEvent e) {
		 System.out.println("keyPressed");
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()== KeyEvent.VK_RIGHT)
			System.out.println("derecha");
        else if(e.getKeyCode()== KeyEvent.VK_LEFT)
        	System.out.println("izq");
        else if(e.getKeyCode()== KeyEvent.VK_DOWN)
        	System.out.println("abajo");
        else if(e.getKeyCode()== KeyEvent.VK_UP)
        	System.out.println("arriba");
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		 System.out.println("keyTyped");
		
	}
	
	
}
