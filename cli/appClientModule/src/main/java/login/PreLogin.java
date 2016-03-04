package src.main.java.login;
import hello.wsdl.AutenticarRequest;
import hello.wsdl.AutenticarResponse;
import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import javax.swing.AbstractAction;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import src.main.java.admin.MenuPrincipal;
import src.main.java.hello.VentanaPrincipal;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.votante.VentanaPrincipalVotante;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
        btnAdmin.setIcon(new ImageIcon(PreLogin.class.getResource("/imgs/admin.png")));
        btnAdmin.setText("ADMINISTRADOR");
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
        		EleccionMesa mesa = new EleccionMesa();
        		mesa.setVisible(true);
        		dispose();
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



    


  
    public static void main(String args[]) {
    	try {   
    		  javax.swing.UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");               
    		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
    		  java.util.logging.Logger.getLogger(PreLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    		}
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PreLogin().setVisible(true);
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
}
