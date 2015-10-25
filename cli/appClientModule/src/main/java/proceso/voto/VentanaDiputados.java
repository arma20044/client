package src.main.java.proceso.voto;

import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import src.main.java.votante.VentanaPrincipalVotante;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

public class VentanaDiputados extends JFrame implements ActionListener{
	
	public static String diputados;

	private Container contenedor;/*declaramos el contenedor*/
	JButton botonCambiar;/*declaramos el objeto Boton*/
	JLabel labelTitulo;/*declaramos el objeto Label*/
	private VentanaDiputados miVentanaPrincipal;
	final JRadioButton  rdbList1, rdbList2,rdbList3,rdbList4, rdbList5, rdbList6, rdbList7, rdbList8 ;
	ButtonGroup buttonGroup;
	//datos a enviar
	Integer cedula;
	Integer candidato;
	String lista;
	private ButtonGroup buttonGroup_1;
	
	
	public Integer getCedula() {
		return cedula;
	}

	public void setCedula(Integer cedula) {
		this.cedula = cedula;
	}

	public Integer getCandidato() {
		return candidato;
	}

	public void setCandidato(Integer candidato) {
		this.candidato = candidato;
	}
	//datos a enviar
	
	public VentanaDiputados(){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		
		/*permite iniciar las propiedades de los componentes*/
		iniciarComponentes();
		setTitle("Sistema E-vote - 2014");
		setSize(1201,763);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(900, 192, 203, 234);
		getContentPane().add(panel);
		
		JLabel lblAlanTuring = new JLabel("Marcian Hoff");
		lblAlanTuring.setIcon(new ImageIcon(VentanaDiputados.class.getResource("/candidatosJpeg/Marcian Hoff.jpg")));
		lblAlanTuring.setBounds(0, 0, 100, 141);
		
		Image img = ((ImageIcon) lblAlanTuring.getIcon()).getImage();
		Image newimg = img.getScaledInstance(lblAlanTuring.getWidth(), lblAlanTuring.getHeight(),
				java.awt.Image.SCALE_SMOOTH);
		lblAlanTuring.setIcon(new ImageIcon(newimg));
		
		panel.add(lblAlanTuring);
		
		JLabel label_3 = new JLabel(lblAlanTuring.getText());
		label_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_3.setBounds(21, 140, 78, 15);
		panel.add(label_3);
		
		JLabel lblPartidoDeInformaticos = new JLabel("Partido de Informaticos");
		lblPartidoDeInformaticos.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblPartidoDeInformaticos.setBounds(0, 167, 208, 25);
		panel.add(lblPartidoDeInformaticos);
		
		JLabel lblPi = new JLabel("P.I.");
		lblPi.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblPi.setBounds(90, 203, 44, 31);
		panel.add(lblPi);
		
		rdbList4 = new JRadioButton("4");
		buttonGroup_1.add(rdbList4);
		rdbList4.setBounds(106, 36, 75, 105);
		panel.add(rdbList4);
		rdbList4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				capturarSenador((JRadioButton) arg0.getSource());
			}
		});
		rdbList4.setFont(new Font("Tahoma", Font.BOLD, 79));
		
		JLabel label_8 = new JLabel("");
		label_8.setIcon(new ImageIcon(VentanaDiputados.class.getResource("/imgs/justicia electoral logo.jpg")));
		label_8.setBounds(0, 0, 117, 115);
		
		Image img7 = ((ImageIcon) label_8.getIcon()).getImage();
		Image newimg7 = img7.getScaledInstance(label_8.getWidth(), label_8.getHeight(),
				java.awt.Image.SCALE_SMOOTH);
		label_8.setIcon(new ImageIcon(newimg7));
		
		getContentPane().add(label_8);
		
		JLabel label = new JLabel("BOLETIN DE VOTO OFICIAL");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Tahoma", Font.BOLD, 25));
		label.setBounds(444, 24, 342, 31);
		getContentPane().add(label);
		
		JLabel label_1 = new JLabel("PERIODO 2016-2021");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setForeground(Color.GRAY);
		label_1.setFont(new Font("Tahoma", Font.BOLD, 25));
		label_1.setBounds(351, 108, 536, 31);
		getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("SELECCIONE LA CANDIDATURA DE SU PREFERENCIA");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setForeground(Color.BLACK);
		label_2.setFont(new Font("Tahoma", Font.BOLD, 20));
		label_2.setBounds(302, 150, 631, 31);
		getContentPane().add(label_2);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBounds(129, 192, 203, 234);
		getContentPane().add(panel_1);
		
		JLabel lblStevenBallmer = new JLabel("Philip Donald Estridge");
		lblStevenBallmer.setIcon(new ImageIcon(VentanaDiputados.class.getResource("/candidatosJpeg/Philip Donald Estridge.jpg")));
		lblStevenBallmer.setBounds(0, 0, 100, 141);
		
		Image img4 = ((ImageIcon) lblStevenBallmer.getIcon()).getImage();
		Image newimg4 = img4.getScaledInstance(lblStevenBallmer.getWidth(), lblStevenBallmer.getHeight(),
				java.awt.Image.SCALE_SMOOTH);
		lblStevenBallmer.setIcon(new ImageIcon(newimg4));
		
		panel_1.add(lblStevenBallmer);
		
		JLabel label_7 = new JLabel(lblStevenBallmer.getText());
		label_7.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_7.setBounds(10, 141, 134, 15);
		panel_1.add(label_7);
		
		JLabel lblVentanasUnidas = new JLabel("Ventanas Unidas");
		lblVentanasUnidas.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblVentanasUnidas.setBounds(21, 167, 179, 25);
		panel_1.add(lblVentanasUnidas);
		
		JLabel label_10 = new JLabel("V.F.");
		label_10.setFont(new Font("Tahoma", Font.PLAIN, 25));
		label_10.setBounds(90, 203, 44, 31);
		panel_1.add(label_10);
		
		rdbList1 = new JRadioButton("1");
		rdbList1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				capturarSenador((JRadioButton) arg0.getSource());
			}
		});
		buttonGroup_1.add(rdbList1);
		rdbList1.setFont(new Font("Tahoma", Font.BOLD, 79));
		rdbList1.setBounds(106, 36, 75, 105);
		panel_1.add(rdbList1);
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBounds(377, 192, 203, 234);
		getContentPane().add(panel_2);
		
		JLabel lblRolandWayne = new JLabel("Robert Noyce");
		lblRolandWayne.setIcon(new ImageIcon(VentanaDiputados.class.getResource("/candidatosJpeg/Robert Noyce.jpg")));
		lblRolandWayne.setBounds(0, 0, 100, 141);
		
		Image img5 = ((ImageIcon) lblRolandWayne.getIcon()).getImage();
		Image newimg5 = img5.getScaledInstance(lblRolandWayne.getWidth(), lblRolandWayne.getHeight(),
				java.awt.Image.SCALE_SMOOTH);
		lblRolandWayne.setIcon(new ImageIcon(newimg5));
		
		panel_2.add(lblRolandWayne);
		
		JLabel label_6 = new JLabel(lblRolandWayne.getText());
		label_6.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_6.setBounds(0, 141, 83, 15);
		panel_2.add(label_6);
		
		JLabel lblCaminoALa = new JLabel("Camino a la Innovación");
		lblCaminoALa.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblCaminoALa.setBounds(0, 167, 196, 23);
		panel_2.add(lblCaminoALa);
		
		JLabel lblCi = new JLabel("C.I.");
		lblCi.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblCi.setBounds(90, 203, 44, 31);
		panel_2.add(lblCi);
		
		rdbList2 = new JRadioButton("2");
		rdbList2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				capturarSenador((JRadioButton) e.getSource());
			}
		});
		buttonGroup_1.add(rdbList2);
		rdbList2.setFont(new Font("Tahoma", Font.BOLD, 79));
		rdbList2.setBounds(106, 36, 75, 105);
		panel_2.add(rdbList2);
		
		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBounds(639, 192, 203, 234);
		getContentPane().add(panel_3);
		
		JLabel lblShawnFanning = new JLabel("Jimmy Wales");
		lblShawnFanning.setIcon(new ImageIcon(VentanaDiputados.class.getResource("/candidatosJpeg/Jimmy Wales.jpg")));
		lblShawnFanning.setBounds(0, 0, 100, 141);
		
		Image img8 = ((ImageIcon) lblShawnFanning.getIcon()).getImage();
		Image newimg8 = img8.getScaledInstance(lblShawnFanning.getWidth(), lblShawnFanning.getHeight(),
				java.awt.Image.SCALE_SMOOTH);
		lblShawnFanning.setIcon(new ImageIcon(newimg8));
		
		panel_3.add(lblShawnFanning);
		
		JLabel label_11 = new JLabel(lblShawnFanning.getText());
		label_11.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_11.setBounds(0, 141, 94, 15);
		panel_3.add(label_11);
		
		JLabel lblPartidoLibre = new JLabel("Partido Libre");
		lblPartidoLibre.setFont(new Font("Tahoma", Font.PLAIN, 19));
		lblPartidoLibre.setBounds(40, 169, 105, 23);
		panel_3.add(lblPartidoLibre);
		
		JLabel lblPl = new JLabel("P.L.");
		lblPl.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblPl.setBounds(90, 203, 44, 31);
		panel_3.add(lblPl);
		
		rdbList3 = new JRadioButton("3");
		rdbList3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				capturarSenador((JRadioButton) e.getSource());
			}
		});
		buttonGroup_1.add(rdbList3);
		rdbList3.setFont(new Font("Tahoma", Font.BOLD, 79));
		rdbList3.setBounds(106, 36, 75, 105);
		panel_3.add(rdbList3);
		
		JPanel panel_4 = new JPanel();
		panel_4.setLayout(null);
		panel_4.setBounds(129, 437, 203, 234);
		getContentPane().add(panel_4);
		
		JLabel lblVintonCerf = new JLabel("Michael Saul Dell");
		lblVintonCerf.setIcon(new ImageIcon(VentanaDiputados.class.getResource("/candidatosJpeg/Michael Saul Dell.jpg")));
		lblVintonCerf.setBounds(0, 0, 100, 141);
		
		Image img9 = ((ImageIcon) lblVintonCerf.getIcon()).getImage();
		Image newimg9 = img9.getScaledInstance(lblVintonCerf.getWidth(), lblVintonCerf.getHeight(),
				java.awt.Image.SCALE_SMOOTH);
		lblVintonCerf.setIcon(new ImageIcon(newimg9));
		
		panel_4.add(lblVintonCerf);
		
		JLabel label_5 = new JLabel(lblVintonCerf.getText());
		label_5.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_5.setBounds(10, 141, 101, 15);
		panel_4.add(label_5);
		
		JLabel lblPartidoDeAlgoritmos = new JLabel("Partido de Algoritmos");
		lblPartidoDeAlgoritmos.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPartidoDeAlgoritmos.setBounds(0, 167, 192, 25);
		panel_4.add(lblPartidoDeAlgoritmos);
		
		JLabel lblPa = new JLabel("P.A.");
		lblPa.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblPa.setBounds(90, 203, 45, 31);
		panel_4.add(lblPa);
		
		rdbList5 = new JRadioButton("5");
		rdbList5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				capturarSenador((JRadioButton) e.getSource());
			}
		});
		buttonGroup_1.add(rdbList5);
		rdbList5.setFont(new Font("Tahoma", Font.BOLD, 79));
		rdbList5.setBounds(106, 36, 75, 105);
		panel_4.add(rdbList5);
		
		JPanel panel_5 = new JPanel();
		panel_5.setLayout(null);
		panel_5.setBounds(377, 437, 203, 234);
		getContentPane().add(panel_5);
		
		JLabel lblJackSKilby = new JLabel("Seymour Cray");
		lblJackSKilby.setIcon(new ImageIcon(VentanaDiputados.class.getResource("/candidatosJpeg/Seymour Cray.jpg")));
		lblJackSKilby.setBounds(0, 0, 100, 141);
		
		Image img10 = ((ImageIcon) lblJackSKilby.getIcon()).getImage();
		Image newimg10 = img10.getScaledInstance(lblJackSKilby.getWidth(), lblJackSKilby.getHeight(),
				java.awt.Image.SCALE_SMOOTH);
		lblJackSKilby.setIcon(new ImageIcon(newimg10));
		
		
		panel_5.add(lblJackSKilby);
		
		JLabel label_15 = new JLabel(lblJackSKilby.getText());
		label_15.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_15.setBounds(10, 141, 91, 15);
		panel_5.add(label_15);
		
		JLabel lblMovimientoDeProgramadores = new JLabel("Movimiento de");
		lblMovimientoDeProgramadores.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblMovimientoDeProgramadores.setBounds(52, 152, 116, 22);
		panel_5.add(lblMovimientoDeProgramadores);
		
		JLabel lblMpi = new JLabel("M.P.I.");
		lblMpi.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblMpi.setBounds(90, 203, 66, 31);
		panel_5.add(lblMpi);
		
		rdbList6 = new JRadioButton("6");
		rdbList6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				capturarSenador((JRadioButton) e.getSource());
			}
		});
		buttonGroup_1.add(rdbList6);
		rdbList6.setFont(new Font("Tahoma", Font.BOLD, 79));
		rdbList6.setBounds(106, 36, 75, 105);
		panel_5.add(rdbList6);
		
		JLabel lblProgramadores = new JLabel("Programadores");
		lblProgramadores.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblProgramadores.setBounds(52, 167, 118, 22);
		panel_5.add(lblProgramadores);
		
		JLabel lblIndependientes = new JLabel("Independientes");
		lblIndependientes.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblIndependientes.setBounds(50, 185, 121, 22);
		panel_5.add(lblIndependientes);
		
		JPanel panel_6 = new JPanel();
		panel_6.setLayout(null);
		panel_6.setBounds(639, 437, 203, 234);
		getContentPane().add(panel_6);
		
		JLabel lblGuglielmoMarconi = new JLabel("Douglas Engelbart");
		lblGuglielmoMarconi.setIcon(new ImageIcon(VentanaDiputados.class.getResource("/candidatosJpeg/Douglas Engelbart.jpg")));
		lblGuglielmoMarconi.setBounds(0, 0, 100, 141);
		
		Image img11 = ((ImageIcon) lblGuglielmoMarconi.getIcon()).getImage();
		Image newimg11 = img11.getScaledInstance(lblGuglielmoMarconi.getWidth(), lblGuglielmoMarconi.getHeight(),
				java.awt.Image.SCALE_SMOOTH);
		lblGuglielmoMarconi.setIcon(new ImageIcon(newimg11));
		
		panel_6.add(lblGuglielmoMarconi);
		
		JLabel label_19 = new JLabel(lblGuglielmoMarconi.getText());
		label_19.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_19.setBounds(0, 141, 111, 15);
		panel_6.add(label_19);
		
		JLabel lblPartidoDeAnalistas = new JLabel("Partido de Analistas");
		lblPartidoDeAnalistas.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPartidoDeAnalistas.setBounds(20, 167, 177, 25);
		panel_6.add(lblPartidoDeAnalistas);
		
		JLabel lblPaa = new JLabel("P.A.A.");
		lblPaa.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblPaa.setBounds(90, 203, 68, 31);
		panel_6.add(lblPaa);
		
		rdbList7 = new JRadioButton("7");
		rdbList7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				capturarSenador((JRadioButton) e.getSource());
			}
		});
		buttonGroup_1.add(rdbList7);
		rdbList7.setFont(new Font("Tahoma", Font.BOLD, 79));
		rdbList7.setBounds(106, 36, 75, 105);
		panel_6.add(rdbList7);
		
		JLabel lblAliados = new JLabel("Aliados");
		lblAliados.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblAliados.setBounds(73, 187, 64, 25);
		panel_6.add(lblAliados);
		
		JPanel panel_7 = new JPanel();
		panel_7.setLayout(null);
		panel_7.setBounds(900, 437, 203, 234);
		getContentPane().add(panel_7);
		
		JLabel lblLawrenceJEllison = new JLabel("Tim Berners-Lee");
		lblLawrenceJEllison.setIcon(new ImageIcon(VentanaDiputados.class.getResource("/candidatosJpeg/Tim Berners-Lee.jpg")));
		lblLawrenceJEllison.setBounds(0, 0, 100, 141);
		
		Image img12 = ((ImageIcon) lblLawrenceJEllison.getIcon()).getImage();
		Image newimg12 = img12.getScaledInstance(lblLawrenceJEllison.getWidth(), lblLawrenceJEllison.getHeight(),
				java.awt.Image.SCALE_SMOOTH);
		lblLawrenceJEllison.setIcon(new ImageIcon(newimg12));
		
		
		panel_7.add(lblLawrenceJEllison);
		
		JLabel label_23 = new JLabel(lblLawrenceJEllison.getText());
		label_23.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_23.setBounds(0, 142, 114, 15);
		panel_7.add(label_23);
		
		JLabel lblPartidoDeIngenieros = new JLabel("Partido de Ingenieros");
		lblPartidoDeIngenieros.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPartidoDeIngenieros.setBounds(10, 152, 191, 25);
		panel_7.add(lblPartidoDeIngenieros);
		
		JLabel lblPip = new JLabel("P.I.P.");
		lblPip.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblPip.setBounds(90, 203, 61, 31);
		panel_7.add(lblPip);
		
		rdbList8 = new JRadioButton("8");
		rdbList8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				capturarSenador((JRadioButton) e.getSource());
			}
		});
		buttonGroup_1.add(rdbList8);
		rdbList8.setFont(new Font("Tahoma", Font.BOLD, 79));
		rdbList8.setBounds(106, 36, 75, 105);
		panel_7.add(rdbList8);
		
		JLabel lblProgresistas = new JLabel("Progresistas");
		lblProgresistas.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblProgresistas.setBounds(20, 179, 191, 25);
		panel_7.add(lblProgresistas);
   		/*Asigna un titulo a la barra de titulo*/
		//setTitle("CoDejaVu : JFrame VentanaPrincipal");
		/*tama�o de la ventana*/
		//setSize(300,180);
		/*pone la ventana en el Centro de la pantalla*/
		setLocationRelativeTo(null);
	}

	/**
	 * @param miVentana
	 * Enviamos una instancia de la ventana principal
	 */
	public void setVentanaPrincipal(VentanaDiputados miVentana) {
		miVentanaPrincipal=miVentana;
	}

	private void iniciarComponentes() {	
		contenedor=getContentPane();/*instanciamos el contenedor*/
		/*con esto definmos nosotros mismos los tama�os y posicion
		 * de los componentes*/
		contenedor.setLayout(null);	
	buttonGroup_1= new ButtonGroup();	
	//VentanaPrincipal frmSistemaEvote = new VentanaPrincipal();
	
	
	JLabel lblNewLabel = new JLabel("CANDIDATOS A DIPUTADOS DE LA NACIÓN");
	lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
	lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
	lblNewLabel.setBounds(341, 66, 544, 31);
	getContentPane().add(lblNewLabel);
	
	
	//ver www.camick.com select button group
	final JButton botonCambiar = new JButton();
	botonCambiar.setText("Confirmar Voto");
	botonCambiar.addActionListener(new ActionListener(){
	public void actionPerformed(ActionEvent evento) {
		JButton source = (JButton) evento.getSource();
		Integer ban = 0;
		if (evento.getSource()==botonCambiar)
		{
			//para saber que rdb se selecciono//
			JRadioButton[] rdb = {rdbList1, rdbList2,rdbList3,rdbList4, rdbList5, rdbList6, rdbList7, rdbList8	}; //agregar cada RDB
			for(int i = 0; i<rdb.length; i++){
				if(rdb[i].isSelected()){
					lista = rdb[i].getText();
						if(!lista.isEmpty()){
							dispose();
							VentanaConfirmacionDiputados miVentanaConfirmacion=new VentanaConfirmacionDiputados(miVentanaPrincipal,true);
//							if (lista.compareTo("LISTA 1")== 0){
//							miVentanaPrincipal.setCandidato(1);
//							}
//							miVentanaPrincipal.setCedula("3619250");
							//setear voto para lista
							
							//miVentanaPrincipal.setLista(VentanaSenadores.senadores);
							//miVentanaPrincipal.setCedula(VentanaPrincipalVotante.cedulaVotante);
							ban = 1;
							break;
						}
				
				
			}
			
			
			///////
			
			
			
		
			}
			if (ban == 0){
				JOptionPane.showMessageDialog(source,"Debe seleccionar la lista que desea, si quiere votar en blanco favor clic en el Boton Votar Blanco");
			}	
		}
	}
	}
	);
	//btnNewButton.addActionListener(new ActionListener() {
//		public void actionPerformed(ActionEvent arg0) {
//			JButton source = (JButton) arg0.getSource();
//			if (rdbLista1.isSelected()) {
//				//guardar
//				Jd confirmar = new Jd();
//				confirmar.setModal(true);
//				confirmar.show();
//				//confirmar.setVisible(true);
//			}
//			else if (rdbLista3.isSelected()){
//				//guardar
//				Jd confirmar = new Jd();
//				confirmar.setModal(true);
//				confirmar.show();
//				//confirmar.setVisible(true);
//			}
//				
//				
//			else if (rdbLista4.isSelected() ){
//				//guardar
//				Jd confirmar = new Jd();
//				confirmar.setModal(true);
//				confirmar.show();
//				//confirmar.setVisible(true);
//				
//				
//			}
//			else{
//				JOptionPane.showMessageDialog(source,"Debe seleccionar la lista que desea, si quiere votar en blanco favor clic en el Boton Votar Blanco");
//			}
//		}
//	});
	botonCambiar.setFont(new Font("Tahoma", Font.PLAIN, 23));
	botonCambiar.setBounds(395, 686, 185, 37);
	getContentPane().add(botonCambiar);
	
	JButton btnNewButton_1 = new JButton("Votar BLANCO");
	btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 23));
	btnNewButton_1.setBounds(639, 686, 179, 37);
	getContentPane().add(btnNewButton_1);
	

	}

	public static String getDiputados() {
		return diputados;
	}

	public static void setSenadores(String diputados) {
		VentanaDiputados.diputados = diputados;
	}

	public String getLista() {
		return lista;
	}

	public void setLista(String lista) {
		this.lista = lista;
	}

	/*Agregamos el evento al momento de llamar la otra ventana*/
	public void actionPerformed(ActionEvent evento) {
		if (evento.getSource()==botonCambiar)
		{
		//	VentanaConfirmacion miVentanaConfirmacion=new VentanaConfirmacion(miVentanaPrincipal,true);
			//miVentanaConfirmacion.setVisible(true);
		}
	}
	
	public void capturarSenador(JRadioButton rdb){
		diputados = rdb.getText().substring(rdb.getText().length()-1, rdb.getText().length());
		System.out.println("Senador: " + diputados);
	}
}
