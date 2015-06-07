package src.main.java.login;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;

import src.main.java.votante.VentanaPrincipalVotante;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EleccionMesa extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	
	public static Integer Mesa = 0;

	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	public EleccionMesa() {
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 354, 215);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				char car = arg0.getKeyChar();
				if ((car < '0' || car > '9'))
					arg0.consume();
			}
		});
		textField.setBounds(137, 72, 89, 50);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Aceptar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Mesa = Integer.getInteger(textField.getText());
				VentanaPrincipalVotante votantePrincipal = new VentanaPrincipalVotante();
        		votantePrincipal.setVisible(true);
        		dispose();
			}
		});
		btnNewButton.setBounds(137, 160, 89, 23);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Seleccione el Numero de Mesa:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(51, 23, 278, 37);
		contentPane.add(lblNewLabel);
	}
}
