package src.main.java.proceso.voto;

import java.awt.Container;
import java.awt.Font;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VentanaPresidente extends JFrame implements ActionListener{
	
	public static String presidente;

	private Container contenedor;/*declaramos el contenedor*/
	JButton botonCambiar;/*declaramos el objeto Boton*/
	JLabel labelTitulo;/*declaramos el objeto Label*/
	private VentanaPresidente miVentanaPrincipal;
	
	//datos a enviar
	String cedula;
	Integer candidato;
	String lista;
	
	
	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public Integer getCandidato() {
		return candidato;
	}

	public void setCandidato(Integer candidato) {
		this.candidato = candidato;
	}
	//datos a enviar
	
	public VentanaPresidente(){
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		
		/*permite iniciar las propiedades de los componentes*/
		iniciarComponentes();
		setTitle("Sistema E-vote - 2014");
		setSize(1366,708);
		getContentPane().setLayout(null);
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
	lblNewLabel.setBounds(166, 59, 850, 31);
	getContentPane().add(lblNewLabel);
	
	JPanel panel = new JPanel();
	panel.setBounds(35, 99, 364, 363);
	getContentPane().add(panel);
	panel.setLayout(null);
	
	JLabel lblHC = new JLabel("");
	//lblHC.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/img/hc.jpg")));
	lblHC.setBounds(20, 18, 129, 167);
	panel.add(lblHC);
	//lblHC.setIcon(new ImageIcon(Main.class.getResource("/img/hc.jpg")));
	
	JLabel lblAfara = new JLabel("");
	lblAfara.setBounds(189, 42, 124, 143);
	panel.add(lblAfara);
	//lblAfara.setIcon(new ImageIcon(Main.class.getResource("/img/afara.gif")));
	
	JLabel lblNewLabel_2 = new JLabel("candidato a vice");
	lblNewLabel_2.setBounds(189, 196, 141, 14);
	panel.add(lblNewLabel_2);
	lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
	
	JLabel lblNewLabel_1 = new JLabel("candidato a presidente");
	lblNewLabel_1.setBounds(0, 196, 161, 13);
	panel.add(lblNewLabel_1);
	lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
	
	final JRadioButton rdbLista1 = new JRadioButton("LISTA 1");
	rdbLista1.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			presidente = rdbLista1 .getText().substring(rdbLista1.getText().length()-1, rdbLista1.getText().length());
			System.out.println("PRESIDENTE: " + presidente);
		}
	});
	buttonGroup.add(rdbLista1);
	rdbLista1.setFont(new Font("Tahoma", Font.BOLD, 25));
	rdbLista1.setBounds(107, 234, 123, 39);
	panel.add(rdbLista1);
	
	JLabel lblPartidoColorado = new JLabel("Nombre de la lista");
	lblPartidoColorado.setFont(new Font("Tahoma", Font.PLAIN, 25));
	lblPartidoColorado.setBounds(79, 280, 185, 31);
	panel.add(lblPartidoColorado);
	
	JLabel lblAnr = new JLabel("sigla");
	lblAnr.setFont(new Font("Tahoma", Font.PLAIN, 25));
	lblAnr.setBounds(136, 322, 72, 31);
	panel.add(lblAnr);
	
	JPanel panel_1 = new JPanel();
	panel_1.setLayout(null);
	panel_1.setBounds(421, 101, 364, 363);
	getContentPane().add(panel_1);
	
	JLabel label = new JLabel("");
	//label.setIcon(new ImageIcon(Main.class.getResource("/img/ferre.jpg")));
	label.setBounds(20, 18, 129, 167);
	panel_1.add(label);
	
	JLabel label_1 = new JLabel("");
	//label_1.setIcon(new ImageIcon(Main.class.getResource("/img/imagen-cynthia-brizuela-speratti.jpg")));
	label_1.setBounds(189, 42, 124, 143);
	panel_1.add(label_1);
	
	JLabel lblCynthiaElviraBrizuela = new JLabel("vice 2");
	lblCynthiaElviraBrizuela.setFont(new Font("Tahoma", Font.PLAIN, 10));
	lblCynthiaElviraBrizuela.setBounds(171, 195, 175, 14);
	panel_1.add(lblCynthiaElviraBrizuela);
	
	JLabel lblMarioAnibalFerreiro = new JLabel("candidato 2");
	lblMarioAnibalFerreiro.setFont(new Font("Tahoma", Font.PLAIN, 10));
	lblMarioAnibalFerreiro.setBounds(0, 196, 161, 13);
	panel_1.add(lblMarioAnibalFerreiro);
	
	final JRadioButton rdbLista2 = new JRadioButton("LISTA 2");
	rdbLista2.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			presidente = rdbLista2 .getText().substring(rdbLista2.getText().length()-1, rdbLista2.getText().length());
			System.out.println("PRESIDENTE: " + presidente);
		}
	});
	buttonGroup.add(rdbLista2);
	rdbLista2.setFont(new Font("Tahoma", Font.BOLD, 25));
	rdbLista2.setBounds(107, 234, 123, 39);
	panel_1.add(rdbLista2);
	
	JLabel lblAvanzaPais = new JLabel("Nombre de la lista");
	lblAvanzaPais.setHorizontalAlignment(SwingConstants.CENTER);
	lblAvanzaPais.setFont(new Font("Tahoma", Font.PLAIN, 25));
	lblAvanzaPais.setBounds(87, 280, 185, 31);
	panel_1.add(lblAvanzaPais);
	
	JLabel lblMap = new JLabel("sigla");
	lblMap.setFont(new Font("Tahoma", Font.PLAIN, 25));
	lblMap.setBounds(136, 322, 72, 31);
	panel_1.add(lblMap);
	
	JPanel panel_2 = new JPanel();
	panel_2.setLayout(null);
	panel_2.setBounds(799, 101, 364, 363);
	getContentPane().add(panel_2);
	
	JLabel label_2 = new JLabel("");
	//label_2.setIcon(new ImageIcon(Main.class.getResource("/img/alegre.jpg")));
	label_2.setBounds(20, 18, 129, 167);
	panel_2.add(label_2);
	
	JLabel label_3 = new JLabel("");
	//label_3.setIcon(new ImageIcon(Main.class.getResource("/img/fili.jpg")));
	label_3.setBounds(189, 42, 124, 143);
	panel_2.add(label_3);
	
	JLabel lblRafaelAugustoFilizzola = new JLabel("vice 3");
	lblRafaelAugustoFilizzola.setFont(new Font("Tahoma", Font.PLAIN, 10));
	lblRafaelAugustoFilizzola.setBounds(171, 195, 175, 14);
	panel_2.add(lblRafaelAugustoFilizzola);
	
	JLabel lblPedroEfrainAlegre = new JLabel("candidato 3");
	lblPedroEfrainAlegre.setFont(new Font("Tahoma", Font.PLAIN, 10));
	lblPedroEfrainAlegre.setBounds(0, 196, 161, 13);
	panel_2.add(lblPedroEfrainAlegre);
	
	final JRadioButton rdbLista3 = new JRadioButton("LISTA 3");
	rdbLista3.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent arg0) {
			presidente = rdbLista3 .getText().substring(rdbLista3.getText().length()-1, rdbLista3.getText().length());
			System.out.println("PRESIDENTE: " + presidente);
		}
	});
	buttonGroup.add(rdbLista3);
	rdbLista3.setFont(new Font("Tahoma", Font.BOLD, 25));
	rdbLista3.setBounds(107, 234, 123, 39);
	panel_2.add(rdbLista3);
	
	JLabel lblAlianzaParaguayAlegre = new JLabel("Nombre de la lista");
	lblAlianzaParaguayAlegre.setHorizontalAlignment(SwingConstants.CENTER);
	lblAlianzaParaguayAlegre.setFont(new Font("Tahoma", Font.PLAIN, 25));
	lblAlianzaParaguayAlegre.setBounds(48, 280, 279, 31);
	panel_2.add(lblAlianzaParaguayAlegre);
	
	JLabel lblApa = new JLabel("sigla");
	lblApa.setFont(new Font("Tahoma", Font.PLAIN, 25));
	lblApa.setBounds(136, 322, 72, 31);
	panel_2.add(lblApa);
	
	
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
							dispose();
							VentanaConfirmacionPresidente miVentanaConfirmacion=new VentanaConfirmacionPresidente(miVentanaPrincipal,true);
							if (lista.compareTo("LISTA 1")== 0){
							miVentanaPrincipal.setCandidato(1);
							}
							miVentanaPrincipal.setCedula("3619250");
							//setear voto para lista
							
							
							ban = 1;
							break;
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
	botonCambiar.setBounds(178, 608, 185, 37);
	getContentPane().add(botonCambiar);
	
	JButton btnNewButton_1 = new JButton("Votar BLANCO");
	btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 23));
	btnNewButton_1.setBounds(495, 608, 179, 37);
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
