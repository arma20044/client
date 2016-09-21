package src.main.java.admin.reportes;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JComboBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import src.main.java.admin.Reportes;
import src.main.java.admin.candidato.Item;
import src.main.java.admin.genero.VentanaRegistroGenero;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CantidadVotosElegir extends JFrame {

	private JPanel contentPane;
	List<Object[]> listas = new ArrayList<Object[]>();
	
	static Integer idTipo,tipoSelected;
	
	JComboBox cmbTipoCanditado;
	private JButton botonCancelar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CantidadVotosElegir frame = new CantidadVotosElegir();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CantidadVotosElegir() {
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		cmbTipoCanditado = new JComboBox(recuperarDatosComboBoxTipoCandidato());
		cmbTipoCanditado.setBounds(121, 109, 312, 20);
		contentPane.add(cmbTipoCanditado);
		
		JLabel lblTipoCandidato = new JLabel();
		lblTipoCandidato.setText("Tipo Candidato");
		lblTipoCandidato.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTipoCandidato.setBounds(39, 112, 72, 14);
		contentPane.add(lblTipoCandidato);
		
		JButton btnGenerar = new JButton("Generar");
		btnGenerar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Item idTipo = (Item) cmbTipoCanditado.getSelectedItem();
				tipoSelected = idTipo.getId();
				
				;
				
				
				if (tipoSelected == 1 ){
				CantidadVotosPresidente votos = new CantidadVotosPresidente(tipoSelected);
				System.out.println(idTipo);
				votos.start();
				}
				
				else 
					if (tipoSelected == 2 || tipoSelected == 3){
						CantidadVotosSenadorDiputado votos = new CantidadVotosSenadorDiputado(tipoSelected);
						System.out.println(idTipo);
						votos.start();
					}
				//dispose();
			}
		});
		btnGenerar.setBounds(298, 160, 89, 23);
		contentPane.add(btnGenerar);
		
		botonCancelar = new JButton();
		botonCancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Reportes reporte = new Reportes();
				reporte.setVisible(true);
				dispose();
			}
		});
		botonCancelar.setToolTipText("Atrás");
		botonCancelar.setIcon(new ImageIcon(VentanaRegistroGenero.class
				.getResource("/imgs/back2.png")));
		botonCancelar.setOpaque(false);
		botonCancelar.setContentAreaFilled(false);
		botonCancelar.setBorderPainted(false);
		botonCancelar.setBackground(Color.WHITE);
		botonCancelar.setBounds(401, 229, 32, 32);
		contentPane.add(botonCancelar);
		
		botonCancelar.setBorderPainted(false);
		Image img2 = ((ImageIcon) botonCancelar.getIcon()).getImage();
		Image newimg2 = img2.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		botonCancelar.setIcon(new ImageIcon(newimg2));
	}
	
	private Vector recuperarDatosComboBoxTipoCandidato() {
		Vector model = new Vector();
		JSONArray filas = new JSONArray();
		JSONArray fil = new JSONArray();

		boolean existe = false;

		// Statement estatuto = conex.getConnection().createStatement();

		ApplicationContext ctx = SpringApplication
				.run(WeatherConfiguration.class);

		WeatherClient weatherClient = ctx.getBean(WeatherClient.class);
		QueryGenericoRequest query = new QueryGenericoRequest();

		// para registrar se inserta el codigo es 1
		query.setTipoQueryGenerico(2);

		// query.setQueryGenerico("SELECT id_genero, descripcion, to_char(fch_ins, 'DD/MM/YYYY HH24:MI:SS') as FchIns , "
		// +
		// "usuario_ins, to_char(fch_upd, 'DD/MM/YYYY HH24:MI:SS') as FchUpd ,usuario_upd from ucsaws_departamento ");

		query.setQueryGenerico("select id_tipo_lista, descripcion from ucsaws_tipo_lista order by descripcion");

		QueryGenericoResponse response = weatherClient
				.getQueryGenericoResponse(query);
		weatherClient.printQueryGenericoResponse(response);

		String res = response.getQueryGenericoResponse();

		if (res.compareTo("ERRORRRRRRR") == 0) {
			JOptionPane.showMessageDialog(null, "algo salio mal",
					"Advertencia", JOptionPane.WARNING_MESSAGE);

		}

		else {
			existe = true;

			String generoAntesPartir = response.getQueryGenericoResponse();

			JSONParser j = new JSONParser();
			Object ob = null;
			String part1, part2, part3;

			try {
				ob = j.parse(generoAntesPartir);
			} catch (org.json.simple.parser.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			filas = (JSONArray) ob;

		}

		int ite = 0;
		String campo4, campo5 = "";
		while (filas.size() > ite) {
			fil = (JSONArray) filas.get(ite);

			String[] fin = { fil.get(0).toString(), fil.get(1).toString() };

			listas.add(fin);
			model.addElement(new Item(Integer.parseInt(fin[0]), fin[1]));
			ite++;
		}
		return model;

	}
}
