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

public class VentanaPresidente extends JFrame implements ActionListener{
	
	public static String presidente;

	private Container contenedor;/*declaramos el contenedor*/
	JButton botonCambiar;/*declaramos el objeto Boton*/
	JLabel labelTitulo;/*declaramos el objeto Label*/
	private VentanaPresidente miVentanaPrincipal;
	
	//datos a enviar
	Integer cedula;
	Integer candidato;
	String lista;
	
	
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
	
	public String getLista() {
		return lista;
	}

	public void setLista(String lista) {
		this.lista = lista;
	}

	public VentanaPresidente(){
		getContentPane().setForeground(Color.WHITE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		
		/*permite iniciar las propiedades de los componentes*/
		iniciarComponentes();
		setTitle("Sistema E-vote - 2014");
		setSize(1089,621);
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(VentanaPresidente.class.getResource("/imgs/justicia electoral logo.jpg")));
		lblNewLabel_1.setBounds(0, 0, 117, 115);
		
		Image img6 = ((ImageIcon) lblNewLabel_1.getIcon()).getImage();
		Image newimg6 = img6.getScaledInstance(lblNewLabel_1.getWidth(), lblNewLabel_1.getHeight(),
				java.awt.Image.SCALE_SMOOTH);
		lblNewLabel_1.setIcon(new ImageIcon(newimg6));
		
		
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblBoletinDeVoto = new JLabel("BOLETIN DE VOTO OFICIAL");
		lblBoletinDeVoto.setHorizontalAlignment(SwingConstants.CENTER);
		lblBoletinDeVoto.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblBoletinDeVoto.setBounds(405, 24, 342, 31);
		getContentPane().add(lblBoletinDeVoto);
		
		JLabel lblPeriodo = new JLabel("PERIODO 2016-2021");
		lblPeriodo.setForeground(Color.GRAY);
		lblPeriodo.setHorizontalAlignment(SwingConstants.CENTER);
		lblPeriodo.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblPeriodo.setBounds(445, 108, 264, 31);
		getContentPane().add(lblPeriodo);
		
		JLabel lblS = new JLabel("SELECCIONE LA CANDIDATURA DE SU PREFERENCIA");
		lblS.setHorizontalAlignment(SwingConstants.CENTER);
		lblS.setForeground(Color.BLACK);
		lblS.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblS.setBounds(311, 150, 531, 25);
		getContentPane().add(lblS);
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
	public void setVentanaPrincipal(VentanaPresidente miVentana) {
		miVentanaPrincipal=miVentana;
	}

	private void iniciarComponentes() {	
		contenedor=getContentPane();/*instanciamos el contenedor*/
		/*con esto definmos nosotros mismos los tama�os y posicion
		 * de los componentes*/
		contenedor.setLayout(null);	
	final ButtonGroup  buttonGroup= new ButtonGroup();	
	//VentanaPrincipal frmSistemaEvote = new VentanaPrincipal();
	
	
	JLabel lblNewLabel = new JLabel("CANDIDATOS A PRESIDENTE Y VICEPRESIDENTE DE LA REPUBLICA ");
	lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
	lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
	lblNewLabel.setBounds(171, 66, 850, 31);
	getContentPane().add(lblNewLabel);
	
	JPanel panel = new JPanel();
	panel.setBounds(211, 204, 211, 290);
	getContentPane().add(panel);
	panel.setLayout(null);
	
	JLabel lblFotoPrimerCandidatoPresidente = new JLabel("Bill Gates");
	lblFotoPrimerCandidatoPresidente.setIcon(new ImageIcon(VentanaPresidente.class.getResource("/candidatosJpeg/billGates.jpg")));
	//lblHC.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/img/hc.jpg")));
	lblFotoPrimerCandidatoPresidente.setBounds(0, 0, 100, 141);
	
	Image img = ((ImageIcon) lblFotoPrimerCandidatoPresidente.getIcon()).getImage();
	Image newimg = img.getScaledInstance(lblFotoPrimerCandidatoPresidente.getWidth(), lblFotoPrimerCandidatoPresidente.getHeight(),
			java.awt.Image.SCALE_SMOOTH);
	lblFotoPrimerCandidatoPresidente.setIcon(new ImageIcon(newimg));
	
	panel.add(lblFotoPrimerCandidatoPresidente);
	//lblHC.setIcon(new ImageIcon(Main.class.getResource("/img/hc.jpg")));
	
	JLabel lblFotoPrimerCandidatoVicePresidente = new JLabel("Paul Allen");
	lblFotoPrimerCandidatoVicePresidente.setIcon(new ImageIcon(VentanaPresidente.class.getResource("/candidatosJpeg/Paul Allen.jpg")));
	lblFotoPrimerCandidatoVicePresidente.setBounds(110, 0, 100, 141);
	
	Image img2 = ((ImageIcon) lblFotoPrimerCandidatoVicePresidente.getIcon()).getImage();
	Image newimg2 = img2.getScaledInstance(lblFotoPrimerCandidatoVicePresidente.getWidth(), lblFotoPrimerCandidatoVicePresidente.getHeight(),
			java.awt.Image.SCALE_SMOOTH);
	lblFotoPrimerCandidatoVicePresidente.setIcon(new ImageIcon(newimg2));
	
	panel.add(lblFotoPrimerCandidatoVicePresidente);
	//lblAfara.setIcon(new ImageIcon(Main.class.getResource("/img/afara.gif")));
	
	JLabel lblNombrePrimerCandidatoVicePresidente = new JLabel("candidato a vice");
	lblNombrePrimerCandidatoVicePresidente.setBounds(136, 139, 72, 14);
	lblNombrePrimerCandidatoVicePresidente.setText(lblFotoPrimerCandidatoVicePresidente.getText());
	panel.add(lblNombrePrimerCandidatoVicePresidente);
	lblNombrePrimerCandidatoVicePresidente.setFont(new Font("Tahoma", Font.BOLD, 12));
	
	JLabel lblNombrePrimerCandidatoPresidente = new JLabel("candidato a presidente");
	lblNombrePrimerCandidatoPresidente.setBounds(21, 140, 72, 13);
	lblNombrePrimerCandidatoPresidente.setText(lblFotoPrimerCandidatoPresidente.getText());
	panel.add(lblNombrePrimerCandidatoPresidente);
	
	lblNombrePrimerCandidatoPresidente.setFont(new Font("Tahoma", Font.BOLD, 12));
	
	final JRadioButton rdbLista1 = new JRadioButton("LISTA 1");
//	rdbLista1.addMouseListener(new MouseAdapter() {
//		@Override
//		public void mouseClicked(MouseEvent arg0) {
//			presidente = rdbLista1 .getText().substring(rdbLista1.getText().length()-1, rdbLista1.getText().length());
//			System.out.println("PRESIDENTE: " + presidente);
//		}
//	});
	buttonGroup.add(rdbLista1);
	rdbLista1.setFont(new Font("Tahoma", Font.BOLD, 25));
	rdbLista1.setBounds(55, 173, 123, 39);
	panel.add(rdbLista1);
	
	JLabel lblPartidoColorado = new JLabel("Ventanas Unidas");
	lblPartidoColorado.setFont(new Font("Tahoma", Font.PLAIN, 20));
	lblPartidoColorado.setBounds(21, 219, 179, 25);
	panel.add(lblPartidoColorado);
	
	JLabel lblAnr = new JLabel("V.U.");
	lblAnr.setFont(new Font("Tahoma", Font.PLAIN, 25));
	lblAnr.setBounds(88, 259, 63, 31);
	panel.add(lblAnr);
	
	JLabel label = new JLabel("Presidente");
	label.setFont(new Font("Tahoma", Font.PLAIN, 10));
	label.setBounds(21, 153, 72, 13);
	panel.add(label);
	
	JLabel label_1 = new JLabel("Vicepresidente");
	label_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
	label_1.setBounds(130, 152, 90, 13);
	panel.add(label_1);
	
	JPanel panel_1 = new JPanel();
	panel_1.setLayout(null);
	panel_1.setBounds(461, 204, 211, 290);
	getContentPane().add(panel_1);
	
	JLabel lblFotoSegundoCandidatoPresidente = new JLabel("Steve Jobs");
	lblFotoSegundoCandidatoPresidente.setIcon(new ImageIcon(VentanaPresidente.class.getResource("/candidatosJpeg/steveJobs.jpg")));
	//label.setIcon(new ImageIcon(Main.class.getResource("/img/ferre.jpg")));
	lblFotoSegundoCandidatoPresidente.setBounds(0, 0, 100, 141);
	
	Image img3 = ((ImageIcon) lblFotoSegundoCandidatoPresidente.getIcon()).getImage();
	Image newimg3 = img3.getScaledInstance(lblFotoSegundoCandidatoPresidente.getWidth(), lblFotoSegundoCandidatoPresidente.getHeight(),
			java.awt.Image.SCALE_SMOOTH);
	lblFotoSegundoCandidatoPresidente.setIcon(new ImageIcon(newimg3));
	
	panel_1.add(lblFotoSegundoCandidatoPresidente);
	
	JLabel lblFotoSegundoCandidatoVicePresidente = new JLabel("Steve Wozniak");
	lblFotoSegundoCandidatoVicePresidente.setIcon(new ImageIcon(VentanaPresidente.class.getResource("/candidatosJpeg/Steve Wozniak.jpg")));
	//label_1.setIcon(new ImageIcon(Main.class.getResource("/img/imagen-cynthia-brizuela-speratti.jpg")));
	lblFotoSegundoCandidatoVicePresidente.setBounds(110, 0, 100, 141);
	
	Image img4 = ((ImageIcon) lblFotoSegundoCandidatoVicePresidente.getIcon()).getImage();
	Image newimg4 = img4.getScaledInstance(lblFotoSegundoCandidatoVicePresidente.getWidth(), lblFotoSegundoCandidatoVicePresidente.getHeight(),
			java.awt.Image.SCALE_SMOOTH);
	lblFotoSegundoCandidatoVicePresidente.setIcon(new ImageIcon(newimg4));
	
	panel_1.add(lblFotoSegundoCandidatoVicePresidente);
	
	JLabel lblNombreSegundoCandidatoVicePresidente = new JLabel("vice 2");
	lblNombreSegundoCandidatoVicePresidente.setFont(new Font("Tahoma", Font.BOLD, 12));
	lblNombreSegundoCandidatoVicePresidente.setBounds(110, 140, 100, 14);
	lblNombreSegundoCandidatoVicePresidente.setText(lblFotoSegundoCandidatoVicePresidente.getText());
	panel_1.add(lblNombreSegundoCandidatoVicePresidente);
	
	JLabel lblNombreSegundoCandidatoPresidente = new JLabel("candidato 2");
	lblNombreSegundoCandidatoPresidente.setFont(new Font("Tahoma", Font.BOLD, 12));
	lblNombreSegundoCandidatoPresidente.setBounds(10, 141, 80, 13);
	lblNombreSegundoCandidatoPresidente.setText(lblFotoSegundoCandidatoPresidente.getText());
	panel_1.add(lblNombreSegundoCandidatoPresidente);
	
	final JRadioButton rdbLista2 = new JRadioButton("LISTA 2");
//	rdbLista2.addMouseListener(new MouseAdapter() {
//		@Override
//		public void mouseClicked(MouseEvent arg0) {
//			presidente = rdbLista2 .getText().substring(rdbLista2.getText().length()-1, rdbLista2.getText().length());
//			System.out.println("PRESIDENTE: " + presidente);
//		}
//	});
	buttonGroup.add(rdbLista2);
	rdbLista2.setFont(new Font("Tahoma", Font.BOLD, 25));
	rdbLista2.setBounds(50, 172, 123, 39);
	panel_1.add(rdbLista2);
	
	JLabel lblAvanzaPais = new JLabel("Camino a la \r\nInnovación");
	lblAvanzaPais.setHorizontalAlignment(SwingConstants.CENTER);
	lblAvanzaPais.setFont(new Font("Tahoma", Font.PLAIN, 20));
	lblAvanzaPais.setBounds(0, 218, 209, 25);
	panel_1.add(lblAvanzaPais);
	
	JLabel lblMap = new JLabel("C.I.");
	lblMap.setFont(new Font("Tahoma", Font.PLAIN, 25));
	lblMap.setBounds(87, 259, 40, 31);
	panel_1.add(lblMap);
	
	JLabel lblPresidente1 = new JLabel("Presidente");
	lblPresidente1.setBounds(29, 152, 72, 13);
	panel_1.add(lblPresidente1);
	lblPresidente1.setFont(new Font("Tahoma", Font.PLAIN, 10));
	
	JLabel lblVice1 = new JLabel("Vicepresidente");
	lblVice1.setBounds(120, 152, 90, 13);
	panel_1.add(lblVice1);
	lblVice1.setFont(new Font("Tahoma", Font.PLAIN, 10));
	
	JPanel panel_2 = new JPanel();
	panel_2.setLayout(null);
	panel_2.setBounds(712, 206, 211, 290);
	getContentPane().add(panel_2);
	
	JLabel lblFotoTercerCandidatoPresidente = new JLabel("Richard Stallman");
	lblFotoTercerCandidatoPresidente.setIcon(new ImageIcon(VentanaPresidente.class.getResource("/candidatosJpeg/RichardStallman.jpg")));
	//label_2.setIcon(new ImageIcon(Main.class.getResource("/img/alegre.jpg")));
	lblFotoTercerCandidatoPresidente.setBounds(0, 0, 100, 141);
	
	Image img5 = ((ImageIcon) lblFotoTercerCandidatoPresidente.getIcon()).getImage();
	Image newimg5 = img5.getScaledInstance(lblFotoTercerCandidatoPresidente.getWidth(), lblFotoTercerCandidatoPresidente.getHeight(),
			java.awt.Image.SCALE_SMOOTH);
	lblFotoTercerCandidatoPresidente.setIcon(new ImageIcon(newimg5));
	
	panel_2.add(lblFotoTercerCandidatoPresidente);
	
	JLabel lblFotoTercerCandidatoVicePresidente = new JLabel("Linus Torvalds");
	lblFotoTercerCandidatoVicePresidente.setIcon(new ImageIcon(VentanaPresidente.class.getResource("/candidatosJpeg/Linus Benedict Torvalds.jpeg")));
	//label_3.setIcon(new ImageIcon(Main.class.getResource("/img/fili.jpg")));
	lblFotoTercerCandidatoVicePresidente.setBounds(110, 0, 100, 141);
	
	Image img6 = ((ImageIcon) lblFotoTercerCandidatoVicePresidente.getIcon()).getImage();
	Image newimg6 = img6.getScaledInstance(lblFotoTercerCandidatoVicePresidente.getWidth(), lblFotoTercerCandidatoVicePresidente.getHeight(),
			java.awt.Image.SCALE_SMOOTH);
	lblFotoTercerCandidatoVicePresidente.setIcon(new ImageIcon(newimg6));
	
	
	panel_2.add(lblFotoTercerCandidatoVicePresidente);
	
	JLabel lblNombreTercerCandidatoVicePresidente = new JLabel("vice 3");
	lblNombreTercerCandidatoVicePresidente.setFont(new Font("Tahoma", Font.BOLD, 12));
	lblNombreTercerCandidatoVicePresidente.setBounds(120, 139, 100, 14);
	lblNombreTercerCandidatoVicePresidente.setText(lblFotoTercerCandidatoVicePresidente.getText());
	panel_2.add(lblNombreTercerCandidatoVicePresidente);
	
	JLabel lblNombreTercerCandidatoPresidente = new JLabel("candidato 3");
	lblNombreTercerCandidatoPresidente.setFont(new Font("Tahoma", Font.BOLD, 12));
	lblNombreTercerCandidatoPresidente.setBounds(0, 140, 109, 13);
	lblNombreTercerCandidatoPresidente.setText(lblFotoTercerCandidatoPresidente.getText());
	panel_2.add(lblNombreTercerCandidatoPresidente);
	
	final JRadioButton rdbLista3 = new JRadioButton("LISTA 3");
//	rdbLista3.addMouseListener(new MouseAdapter() {
//		@Override
//		public void mouseClicked(MouseEvent arg0) {
//			presidente = rdbLista3 .getText().substring(rdbLista3.getText().length()-1, rdbLista3.getText().length());
//			System.out.println("PRESIDENTE: " + presidente);
//		}
//	});
	buttonGroup.add(rdbLista3);
	rdbLista3.setFont(new Font("Tahoma", Font.BOLD, 25));
	rdbLista3.setBounds(47, 170, 123, 39);
	panel_2.add(rdbLista3);
	
	JLabel lblAlianzaParaguayAlegre = new JLabel("Partido Libre");
	lblAlianzaParaguayAlegre.setHorizontalAlignment(SwingConstants.CENTER);
	lblAlianzaParaguayAlegre.setFont(new Font("Tahoma", Font.PLAIN, 25));
	lblAlianzaParaguayAlegre.setBounds(44, 216, 140, 31);
	panel_2.add(lblAlianzaParaguayAlegre);
	
	JLabel lblApa = new JLabel("P.L.");
	lblApa.setFont(new Font("Tahoma", Font.PLAIN, 25));
	lblApa.setBounds(93, 259, 42, 31);
	panel_2.add(lblApa);
	
	JLabel label_2 = new JLabel("Presidente");
	label_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
	label_2.setBounds(28, 150, 72, 13);
	panel_2.add(label_2);
	
	JLabel label_3 = new JLabel("Vicepresidente");
	label_3.setFont(new Font("Tahoma", Font.PLAIN, 10));
	label_3.setBounds(130, 150, 90, 13);
	panel_2.add(label_3);
	
	
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
			JRadioButton[] rdb = {rdbLista1,rdbLista2, rdbLista3	};
			for(int i = 0; i<rdb.length; i++){
				if(rdb[i].isSelected()){
					
					
					lista = rdb[i].getText();
					
					if(!lista.isEmpty()){
						
						if(rdbLista1.isSelected()){
							
							presidente = rdbLista1 .getText().substring(rdbLista1.getText().length()-1, rdbLista1.getText().length());
							System.out.println("PRESIDENTE: " + presidente);
						}
						
						else
							if(rdbLista2.isSelected()){
						presidente = rdbLista2 .getText().substring(rdbLista2.getText().length()-1, rdbLista2.getText().length());
						System.out.println("PRESIDENTE: " + presidente);
							}
						
							
							else
								if(rdbLista3.isSelected()){
						presidente = rdbLista3 .getText().substring(rdbLista3.getText().length()-1, rdbLista3.getText().length());
						System.out.println("PRESIDENTE: " + presidente);
								}
						
						dispose();
						VentanaConfirmacionPresidente miVentanaConfirmacion=new VentanaConfirmacionPresidente(miVentanaPrincipal,true);
						//if (lista.compareTo("LISTA 1")== 0){
						//miVentanaPrincipal.setCandidato(1);
						//}
						
						//miVentanaPrincipal.setLista(VentanaPresidente.presidente);
						//miVentanaPrincipal.setCedula(VentanaPrincipalVotante.cedulaVotante);
						//setear voto para lista
						
						
						ban = 1;
						break;
					}
					
					else{
						JOptionPane.showMessageDialog(new JFrame(), "Ocurrió un error", "Dialog",
						        JOptionPane.ERROR_MESSAGE);
					}
					
					
	
						
						
				
						//dispose(); 	
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
	botonCambiar.setBounds(294, 537, 185, 37);
	getContentPane().add(botonCambiar);
	
	JButton btnNewButton_1 = new JButton("Votar BLANCO");
	btnNewButton_1.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			presidente="BLANCO";
			dispose();
			VentanaConfirmacionPresidente miVentanaConfirmacion=new VentanaConfirmacionPresidente(miVentanaPrincipal,true);
		}
	});
	btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 23));
	btnNewButton_1.setBounds(639, 537, 179, 37);
	getContentPane().add(btnNewButton_1);
	

	}

	/*Agregamos el evento al momento de llamar la otra ventana*/
	public void actionPerformed(ActionEvent evento) {
		if (evento.getSource()==botonCambiar)
		{
			VentanaConfirmacionPresidente miVentanaConfirmacion=new VentanaConfirmacionPresidente(miVentanaPrincipal,true);
			miVentanaConfirmacion.setVisible(true);
		}
	}
}
