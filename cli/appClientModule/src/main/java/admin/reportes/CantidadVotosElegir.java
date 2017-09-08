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
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import entity.TipoLista;
import entity.UcsawsTipoLista;
import src.main.java.admin.Reportes;
import src.main.java.admin.candidato.Item;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.admin.genero.VentanaRegistroGenero;
import src.main.java.dao.listas.ListasDAO;
import src.main.java.dao.tipoLista.TipoListaDAO;
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
		cmbTipoCanditado.setBounds(77, 116, 312, 20);
		contentPane.add(cmbTipoCanditado);
		
		JLabel lblTipoCandidato = new JLabel();
		lblTipoCandidato.setText("Seleccione el Tipo Candidato");
		lblTipoCandidato.setHorizontalAlignment(SwingConstants.CENTER);
		lblTipoCandidato.setBounds(77, 75, 313, 20);
		contentPane.add(lblTipoCandidato);
		
		JButton btnGenerar = new JButton("Generar");
		btnGenerar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Item idTipo = (Item) cmbTipoCanditado.getSelectedItem();
				tipoSelected = idTipo.getId();
				
				TipoListaDAO tipoListaDAO = new TipoListaDAO();
				
				String seleccionado = tipoListaDAO.obtenerTipoListaById(tipoSelected).getCodigo();
				
				;
				
				
				if (seleccionado.contains("PRE" )){
				CantidadVotosPresidente votos = new CantidadVotosPresidente(tipoSelected);
				System.out.println(idTipo);
				votos.start();
				}
				
				else 
					if (seleccionado.contains("SEN") || seleccionado.contains("PAR")){
					  
					 UcsawsTipoLista tl = tipoListaDAO.obtenerTipoListaById(tipoSelected);
					  
						CantidadVotosSenadorDiputado votos = new CantidadVotosSenadorDiputado(tl);
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
		botonCancelar.setBounds(366, 225, 32, 32);
		contentPane.add(botonCancelar);
		
		botonCancelar.setBorderPainted(false);
		Image img2 = ((ImageIcon) botonCancelar.getIcon()).getImage();
		Image newimg2 = img2.getScaledInstance(32, 32,
				java.awt.Image.SCALE_SMOOTH);
		botonCancelar.setIcon(new ImageIcon(newimg2));
	}
	
	private Vector recuperarDatosComboBoxTipoCandidato() {
	    Vector model = new Vector();

	    ListasDAO listaDAO = new ListasDAO();
	    TipoListaDAO tipoListaDAO = new TipoListaDAO();

	    List<UcsawsTipoLista> tipoLista = tipoListaDAO
	        .obtenerTipoListaByIdEvento(Integer
	            .parseInt(VentanaBuscarEvento.evento));
	    // List<UcsawsListas> lista =
	    // listaDAO.obtenerListaByIdEvento(Integer.parseInt(VentanaBuscarEvento.evento));

	    if (tipoLista.isEmpty()) {
	        // JOptionPane.showMessageDialog(null, "algo salio mal",
	        // "Advertencia", JOptionPane.WARNING_MESSAGE);
	        // return lista;
	    }

	    else {

	        // DefaultTableModel model = (DefaultTableModel) tabla.getModel();
	        Iterator<UcsawsTipoLista> ite = tipoLista.iterator();

	        UcsawsTipoLista aux;

	        while (ite.hasNext()) {
	        aux = ite.next();

	        model.addElement(new Item(aux.getIdTipoLista(), aux
	            .getDescripcion()));

	        }
	        // return model;
	    }

	    return model;

	}
}
