package src.main.java.proceso.voto;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JButton;

import src.main.java.login.PreLogin;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaVotoFinal extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	public VentanaVotoFinal() {
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("EXCELENTE!");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 34));
		lblNewLabel.setBounds(103, 39, 277, 62);
		contentPane.add(lblNewLabel);
		
		JLabel lblHaCulminadoEl = new JLabel("HA CULMINADO EL PROCESO DE VOTACION EXITOSAMENTE");
		lblHaCulminadoEl.setBounds(60, 120, 332, 62);
		contentPane.add(lblHaCulminadoEl);
		
		JButton btnNewButton = new JButton("SALIR");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				//System.exit(0);
				PreLogin p = new PreLogin();
				p.setVisible(true);
				dispose();
				//System.
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNewButton.setBounds(150, 217, 180, 46);
		contentPane.add(btnNewButton);
		setLocationRelativeTo(null);
		
	}
}
