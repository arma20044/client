
package src.main.java.hello;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//import ar.com.hdcm.producto.pgbddao.exception.PgdbProductoDAOException;


public class Jdialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JButton okButton;
	private JButton cancelButton;
	static Jdialog dialog = new Jdialog();
	static JFrame frame;
	private static final String INSERT_SQL =" insert into voto (nombre, Voto) values (?,?)";

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public Jdialog() {
		setUndecorated(true);
		setTitle("Confirmar?");
		frame = new JFrame("Our JDialog Close Example");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		JLabel lblNewLabel = new JLabel("desea confirmar su voto?");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 30));
		JLabel lblEstSeguroQue = new JLabel("\u00BFEst\u00E1 seguro que");
		lblEstSeguroQue.setFont(new Font("Tahoma", Font.PLAIN, 30));
		{
			okButton = new JButton("SI");
			okButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					dialog.setVisible(false);
					dialog.dispose();
					
				}
			});
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					Voto voto = new Voto();
					voto.setNombre("Armando");
					voto.setVoto(true);
					
					try {
						guardar(voto);
						dialog.setVisible(false);
						dialog.dispose();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			});
			okButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
			okButton.setActionCommand("OK");
			getRootPane().setDefaultButton(okButton);
		}
		{
			cancelButton = new JButton("NO");
			cancelButton.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					dialog.setVisible(false);
					dialog.dispose();
				}
			});
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					dialog.setLocationRelativeTo(null);
			        dialog.setVisible(true);
				}
			});
			cancelButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
			cancelButton.setActionCommand("Cancel");
		}
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(82)
							.addComponent(lblEstSeguroQue))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(104)
							.addComponent(cancelButton)
							.addGroup(gl_contentPanel.createSequentialGroup()
								.addGap(70)
								.addComponent(okButton)))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(38)
							.addComponent(lblNewLabel)))
					.addContainerGap(66, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addGap(26)
					.addComponent(lblEstSeguroQue)
					.addGap(30)
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(cancelButton)
						.addComponent(okButton))
					.addGap(28))
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
		}
		  // put the button on the frame
	   // frame.getContentPane().setLayout(new FlowLayout());
	    //frame.add(cancelButton);
	 
	    // set up the jframe, then display it
	    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	   // frame.setPreferredSize(new Dimension(300, 200));
	    frame.pack();
	    frame.setLocationRelativeTo(null);
	    frame.setVisible(true);
	}
	
	private void guardar(Voto voto) throws Exception{
		if(voto!=null){
		    Connection conn = null;
		    PreparedStatement sth = null;
			ResultSet rs =null;
		    try {
		        conn = getConnection();
		        //Statement.NO_GENERATED_KEYS por el id esta definido con serial.
		        sth = conn.prepareStatement(INSERT_SQL,ResultSet.TYPE_SCROLL_INSENSITIVE,Statement.NO_GENERATED_KEYS);
		        
		        sth.clearParameters();
		        sth.setString(1, voto.getNombre());
		        sth.setBoolean(2, voto.getVoto());
		        //sth.setBoolean(3, producto.getDisponible());
		        
		        sth.executeUpdate();
		        
//		        rs=sth.getGeneratedKeys();
//		        if(rs.next()){
//		        	Integer id = (Integer) rs.getInt(1);
//			        producto.setId(id);
//		        }
		    }catch (Exception e) {
		    	 throw new Exception(e);
		    } finally {
		    	try{
		    		if(!conn.isClosed()) conn.close();
		    	}catch(Exception e){
		    		throw new Exception(e);
		    	}
			}
		   // return Voto.getId();
	   }
		//return null;
	}

	public Connection getConnection() throws SQLException {
		try{
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ajax","root","bluray2014");
		return connection;
		 }
        catch (Exception e) 
        {
            System.out.println(e);
        }
		return null;
		
	}
}
