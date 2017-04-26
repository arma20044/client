package src.main.java.login;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Test {

	private JFrame frame;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtFecha;
	private JTextField txtEmail;
	private JLabel lblEmail;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Test window = new Test();
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
	public Test() {
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
		
		txtNombre = new JTextField();
		txtNombre.setBounds(204, 47, 123, 24);
		frame.getContentPane().add(txtNombre);
		txtNombre.setColumns(10);
		
		txtApellido = new JTextField();
		txtApellido.setColumns(10);
		txtApellido.setBounds(204, 92, 123, 24);
		frame.getContentPane().add(txtApellido);
		
		txtFecha = new JTextField();
		txtFecha.setColumns(10);
		txtFecha.setBounds(204, 137, 123, 24);
		frame.getContentPane().add(txtFecha);
		
		JLabel lblNewLabel = new JLabel("Nombre");
		lblNewLabel.setBounds(30, 44, 53, 24);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblApellido = new JLabel("Apellido");
		lblApellido.setBounds(30, 92, 96, 34);
		frame.getContentPane().add(lblApellido);
		
		JLabel lblFechaNacimiento = new JLabel("Fecha Nacimiento");
		lblFechaNacimiento.setBounds(30, 132, 96, 34);
		frame.getContentPane().add(lblFechaNacimiento);
		
		JButton btnNewButton = new JButton("Guardar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GuardarRule g = new GuardarRule();
				g.guardar(txtNombre.getText(), txtApellido.getText(), txtFecha.getText(), txtEmail.getText());
			}
		});
		btnNewButton.setBounds(172, 227, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBounds(204, 178, 123, 24);
		frame.getContentPane().add(txtEmail);
		
		lblEmail = new JLabel("E-mail");
		lblEmail.setBounds(30, 172, 96, 34);
		frame.getContentPane().add(lblEmail);
	}
}
