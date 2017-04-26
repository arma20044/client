package src.main.java.login;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import entity.UcsawsEvento;

public class TestRol {

	private JFrame frame;
	private JTextField txtCodigo;
	private JTextField txtDesc;
	private JTextField txtEvento;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestRol window = new TestRol();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TestRol() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		txtCodigo = new JTextField();
		txtCodigo.setBounds(204, 47, 123, 24);
		frame.getContentPane().add(txtCodigo);
		txtCodigo.setColumns(10);
		
		txtDesc = new JTextField();
		txtDesc.setColumns(10);
		txtDesc.setBounds(204, 92, 123, 24);
		frame.getContentPane().add(txtDesc);
		
		txtEvento = new JTextField();
		txtEvento.setColumns(10);
		txtEvento.setBounds(204, 137, 123, 24);
		frame.getContentPane().add(txtEvento);
		
		JLabel lblNewLabel = new JLabel("Codigo");
		lblNewLabel.setBounds(30, 44, 53, 24);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblApellido = new JLabel("Descripcion");
		lblApellido.setBounds(30, 92, 96, 34);
		frame.getContentPane().add(lblApellido);
		
		JLabel lblFechaNacimiento = new JLabel("Evento");
		lblFechaNacimiento.setBounds(30, 132, 96, 34);
		frame.getContentPane().add(lblFechaNacimiento);
		
		JButton btnNewButton = new JButton("Guardar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TestGuardarRol g = new TestGuardarRol();
				TestEventoDao e = new TestEventoDao();
				
				
				g.guardar(txtCodigo.getText(), txtDesc.getText(), e.getEventoById(Integer.parseInt(txtEvento.getText())));
			}
		});
		btnNewButton.setBounds(172, 227, 89, 23);
		frame.getContentPane().add(btnNewButton);
	}
}
