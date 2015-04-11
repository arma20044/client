package src.main.java.hello;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

public class Main {

	private JFrame frmSistemaEvote;
	private static final String INSERT_SQL =" insert into voto (nombre, Voto) values (?,?)";
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Main window = new Main();
//					
//					window.frmSistemaEvote.setVisible(true);
//					//window.frmSistemaEvote.pack();
//					
//					//window.frmSistemaEvote.setDefaultCloseOperation(Jdialog.EXIT_ON_CLOSE);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	public Main() {
		initialize();
		
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSistemaEvote = new JFrame();
		frmSistemaEvote.setTitle("Sistema E-vote - 2014");
		frmSistemaEvote.setResizable(false);
		frmSistemaEvote.setSize(1280,960);
		frmSistemaEvote.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("CANDIDATOS A PRESIDENTE Y VICEPRESIDENTE DE LA REPUBLICA ");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(166, 59, 850, 31);
		frmSistemaEvote.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBounds(35, 99, 364, 363);
		frmSistemaEvote.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblHC = new JLabel("");
		lblHC.setBounds(20, 18, 129, 167);
		panel.add(lblHC);
		lblHC.setIcon(new ImageIcon(Main.class.getResource("/img/hc.jpg")));
		
		JLabel lblAfara = new JLabel("");
		lblAfara.setBounds(189, 42, 124, 143);
		panel.add(lblAfara);
		lblAfara.setIcon(new ImageIcon(Main.class.getResource("/img/afara.gif")));
		
		JLabel lblNewLabel_2 = new JLabel("JUAN EUDES AFARA MACIEL ");
		lblNewLabel_2.setBounds(189, 196, 141, 14);
		panel.add(lblNewLabel_2);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		
		JLabel lblNewLabel_1 = new JLabel("HORACIO MANUEL CARTES JARA ");
		lblNewLabel_1.setBounds(0, 196, 161, 13);
		panel.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		
		final JRadioButton rdbLista1 = new JRadioButton("LISTA 1");
		buttonGroup.add(rdbLista1);
		rdbLista1.setFont(new Font("Tahoma", Font.BOLD, 25));
		rdbLista1.setBounds(107, 234, 123, 39);
		panel.add(rdbLista1);
		
		JLabel lblPartidoColorado = new JLabel("Partido Colorado");
		lblPartidoColorado.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblPartidoColorado.setBounds(79, 280, 185, 31);
		panel.add(lblPartidoColorado);
		
		JLabel lblAnr = new JLabel("A.N.R.");
		lblAnr.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblAnr.setBounds(136, 322, 72, 31);
		panel.add(lblAnr);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBounds(421, 101, 364, 363);
		frmSistemaEvote.getContentPane().add(panel_1);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(Main.class.getResource("/img/ferre.jpg")));
		label.setBounds(20, 18, 129, 167);
		panel_1.add(label);
		
		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(Main.class.getResource("/img/imagen-cynthia-brizuela-speratti.jpg")));
		label_1.setBounds(189, 42, 124, 143);
		panel_1.add(label_1);
		
		JLabel lblCynthiaElviraBrizuela = new JLabel("CYNTHIA ELVIRA BRIZUELA SPERATTI");
		lblCynthiaElviraBrizuela.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblCynthiaElviraBrizuela.setBounds(171, 195, 175, 14);
		panel_1.add(lblCynthiaElviraBrizuela);
		
		JLabel lblMarioAnibalFerreiro = new JLabel("MARIO ANIBAL FERREIRO SANABRIA");
		lblMarioAnibalFerreiro.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblMarioAnibalFerreiro.setBounds(0, 196, 161, 13);
		panel_1.add(lblMarioAnibalFerreiro);
		
		final JRadioButton rdbLista3 = new JRadioButton("LISTA 3");
		buttonGroup.add(rdbLista3);
		rdbLista3.setFont(new Font("Tahoma", Font.BOLD, 25));
		rdbLista3.setBounds(107, 234, 123, 39);
		panel_1.add(rdbLista3);
		
		JLabel lblAvanzaPais = new JLabel("Avanza Pa\u00EDs\r\n");
		lblAvanzaPais.setHorizontalAlignment(SwingConstants.CENTER);
		lblAvanzaPais.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblAvanzaPais.setBounds(87, 280, 185, 31);
		panel_1.add(lblAvanzaPais);
		
		JLabel lblMap = new JLabel("M.A.P.");
		lblMap.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblMap.setBounds(136, 322, 72, 31);
		panel_1.add(lblMap);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBounds(799, 101, 364, 363);
		frmSistemaEvote.getContentPane().add(panel_2);
		
		JLabel label_2 = new JLabel("");
		label_2.setIcon(new ImageIcon(Main.class.getResource("/img/alegre.jpg")));
		label_2.setBounds(20, 18, 129, 167);
		panel_2.add(label_2);
		
		JLabel label_3 = new JLabel("");
		label_3.setIcon(new ImageIcon(Main.class.getResource("/img/fili.jpg")));
		label_3.setBounds(189, 42, 124, 143);
		panel_2.add(label_3);
		
		JLabel lblRafaelAugustoFilizzola = new JLabel("RAFAEL AUGUSTO FILIZZOLA SERRA");
		lblRafaelAugustoFilizzola.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblRafaelAugustoFilizzola.setBounds(171, 195, 175, 14);
		panel_2.add(lblRafaelAugustoFilizzola);
		
		JLabel lblPedroEfrainAlegre = new JLabel("PEDRO EFRAIN ALEGRE SASIAIN");
		lblPedroEfrainAlegre.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblPedroEfrainAlegre.setBounds(0, 196, 161, 13);
		panel_2.add(lblPedroEfrainAlegre);
		
		final JRadioButton rdbLista4 = new JRadioButton("LISTA 4");
		buttonGroup.add(rdbLista4);
		rdbLista4.setFont(new Font("Tahoma", Font.BOLD, 25));
		rdbLista4.setBounds(107, 234, 123, 39);
		panel_2.add(rdbLista4);
		
		JLabel lblAlianzaParaguayAlegre = new JLabel("Alianza Paraguay Alegre\r\n");
		lblAlianzaParaguayAlegre.setHorizontalAlignment(SwingConstants.CENTER);
		lblAlianzaParaguayAlegre.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblAlianzaParaguayAlegre.setBounds(48, 280, 279, 31);
		panel_2.add(lblAlianzaParaguayAlegre);
		
		JLabel lblApa = new JLabel("A.P.A.");
		lblApa.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblApa.setBounds(136, 322, 72, 31);
		panel_2.add(lblApa);
		
		
		//ver www.camick.com select button group
		JButton btnNewButton = new JButton("Confirmar Voto");
		btnNewButton.setVerticalAlignment(SwingConstants.BOTTOM);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JButton source = (JButton) arg0.getSource();
				if (rdbLista1.isSelected()) {
					//guardar
					Jd confirmar = new Jd();
					confirmar.setModal(true);
					confirmar.show();
					//confirmar.setVisible(true);
				}
				else if (rdbLista3.isSelected()){
					//guardar
					Jd confirmar = new Jd();
					confirmar.setModal(true);
					confirmar.show();
					//confirmar.setVisible(true);
				}
					
					
				else if (rdbLista4.isSelected() ){
					//guardar
					Jd confirmar = new Jd();
					confirmar.setModal(true);
					confirmar.show();
					//confirmar.setVisible(true);
					
					
				}
				else{
					JOptionPane.showMessageDialog(source,"Debe seleccionar la lista que desea, si quiere votar en blanco favor clic en el Boton Votar Blanco");
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 23));
		btnNewButton.setBounds(178, 608, 185, 37);
		frmSistemaEvote.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Votar BLANCO");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 23));
		btnNewButton_1.setBounds(495, 608, 179, 37);
		frmSistemaEvote.getContentPane().add(btnNewButton_1);
		
	
		
	}
	
//	private void guardar(Voto voto) throws Exception{
//		if(voto!=null){
//		    Connection conn = null;
//		    PreparedStatement sth = null;
//			ResultSet rs =null;
//		    try {
//		        conn = getConnection();
//		        //Statement.NO_GENERATED_KEYS por el id esta definido con serial.
//		        sth = conn.prepareStatement(INSERT_SQL,ResultSet.TYPE_SCROLL_INSENSITIVE,Statement.NO_GENERATED_KEYS);
//		        
//		        sth.clearParameters();
//		        sth.setString(1, voto.getNombre());
//		        sth.setBoolean(2, voto.getVoto());
//		        //sth.setBoolean(3, producto.getDisponible());
//		        
//		        sth.executeUpdate();
//		        
////		        rs=sth.getGeneratedKeys();
////		        if(rs.next()){
////		        	Integer id = (Integer) rs.getInt(1);
////			        producto.setId(id);
////		        }
//		    }catch (Exception e) {
//		    	 throw new Exception(e);
//		    } finally {
//		    	try{
//		    		if(!conn.isClosed()) conn.close();
//		    	}catch(Exception e){
//		    		throw new Exception(e);
//		    	}
//			}
//		   // return Voto.getId();
//	   }
//		//return null;
//	}

	public Connection getConnection() throws SQLException {
		try{
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/ajax","root","bluray2014");
		return connection;
		 }
        catch (Exception e) 
        {
            System.out.println(e);
        }
		return null;
		
	}
}
