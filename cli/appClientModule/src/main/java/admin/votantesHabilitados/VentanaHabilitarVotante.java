package src.main.java.admin.votantesHabilitados;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import entity.UcsawsVotante;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.dao.votantesHabilitados.VotantesHabilitadosDAO;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;

import java.awt.Font;

public class VentanaHabilitarVotante extends JDialog {

	private final JPanel contentPanel = new JPanel();
	
	String nombre1, apellido1, ci1, idMesa1;

	/**
	 * Launch the application.
	 */
	public static void main(UcsawsVotante votante) {
		try {
			VentanaHabilitarVotante dialog = new VentanaHabilitarVotante(votante);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	


	/**
	 * Create the dialog.
	 */
	public VentanaHabilitarVotante(UcsawsVotante votante) {
		
		nombre1 = votante.getIdPersona().getNombre();
		apellido1 = votante.getIdPersona().getApellido();
		ci1 = votante.getIdPersona().getCi().toString();
		idMesa1 = votante.getUcsawsMesa().getDescMesa();
		
		final UcsawsVotante votanteHabilitar = votante;
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblPregunta = new JLabel("Esta seguro que desea habilitar a:");
		lblPregunta.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPregunta.setBounds(29, 33, 305, 25);
		contentPanel.add(lblPregunta);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setText(nombre1);
		lblNombre.setBounds(29, 81, 266, 14);
		contentPanel.add(lblNombre);
		
		JLabel lblApellido = new JLabel("Apellido:");
		lblApellido.setText(apellido1);
		lblApellido.setBounds(29, 106, 266, 14);
		contentPanel.add(lblApellido);
		
		JLabel lblCI = new JLabel("C.I.:");
		lblCI.setText(ci1);
		lblCI.setBounds(29, 142, 266, 14);
		contentPanel.add(lblCI);
		
		JLabel lblN = new JLabel("");
		lblN.setBounds(92, 81, 220, 14);
		contentPanel.add(lblN);
		
		JLabel lblA = new JLabel("");
		lblA.setBounds(92, 106, 220, 14);
		contentPanel.add(lblA);
		
		JLabel lblC = new JLabel("");
		lblC.setBounds(92, 142, 220, 14);
		contentPanel.add(lblC);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
					//	if (validarUnSoloHabilitadoPorMesaALaVez() == false){
						VotantesHabilitadosDAO votanteDAO = new VotantesHabilitadosDAO();
						if (votanteDAO.habilitarVotante(votanteHabilitar)){
							System.out.println("deluxe time");
							VentanaBuscarVotantesHabilitados buscar = new VentanaBuscarVotantesHabilitados();
							buscar.setVisible(true);
							dispose();
						}
						else
						{
							System.out.println("error time");
							
						}
						//}
						
					/*	else{
							JOptionPane.showMessageDialog(null, "ya hay una persona habilitada en la mesa favor aguarde unos minutos para habilitar",
									"Advertencia", JOptionPane.WARNING_MESSAGE);
							System.out.println("hule. ya hay una persona habilitada en la mesa favor aguarde unos minutos para habilitar");
						}*/
						
						
						
						
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
				
			    if (Login.rol.compareToIgnoreCase("ADM") == 0) {
			      okButton.setEnabled(true);
			    } else {
			      okButton.setEnabled(false);
			      okButton.setToolTipText("Solo los Miembros de Mesa pueden acceder a ésta Área");
			    }
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						VentanaBuscarVotantesHabilitados buscar = new VentanaBuscarVotantesHabilitados();
						buscar.setVisible(true);
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	

	

	
}
