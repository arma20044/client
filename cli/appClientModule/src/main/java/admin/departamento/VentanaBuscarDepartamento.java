package src.main.java.admin.departamento;

import hello.wsdl.QueryGenericoRequest;
import hello.wsdl.QueryGenericoResponse;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.Timer;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.jdesktop.swingx.JXFindBar;
import org.jdesktop.swingx.JXTable;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.oxbow.swingbits.table.filter.TableRowFilterSupport;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import entity.UcsawsDepartamento;
import entity.UcsawsEvento;
import entity.UcsawsTipoEvento;
import scr.main.java.admin.distrito.VentanaBuscarDistrito;
import src.main.java.admin.Coordinador;
import src.main.java.admin.DefinicionesGenerales;
import src.main.java.admin.MenuPrincipal;
import src.main.java.admin.evento.VentanaBuscarEvento;
import src.main.java.dao.departamento.DepartamentoDAO;
import src.main.java.hello.WeatherClient;
import src.main.java.hello.WeatherConfiguration;
import src.main.java.login.Login;

public class VentanaBuscarDepartamento extends JFrame implements ActionListener {

  private Coordinador miCoordinador; // objeto miCoordinador que permite la
  // relacion entre esta clase y la clase
  // coordinador
  private JLabel labelTitulo;
  private JXFindBar txtBuscar;
  private JButton botonCancelar, btnEliminar, btnNuevo;

  JSONArray miPersona = null;
  DefaultTableModel modelo;
  JXTable table_1;
  private DepartamentoJTableModel model = new DepartamentoJTableModel();
  private JScrollPane scrollPane;

  private String codTemporal = "";

  private JLabel lblMensaje;

  public static String departamentoSeleccionado;

  private DefaultTableModel dm;

  /**
   * constructor de la clase donde se inicializan todos los componentes de la ventana de busqueda
   */
  public VentanaBuscarDepartamento() {

    addWindowListener(new WindowAdapter() {
      public void windowOpened(WindowEvent e) {
        txtBuscar.requestFocus();
      }
    });

    setResizable(false);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    botonCancelar = new JButton();
    botonCancelar.setIcon(new ImageIcon(VentanaBuscarDepartamento.class
        .getResource("/imgs/back2.png")));
    botonCancelar.setToolTipText("Atrás");
    botonCancelar.setBounds(589, 422, 45, 25);
    botonCancelar.setOpaque(false);
    botonCancelar.setContentAreaFilled(false);
    botonCancelar.setBorderPainted(false);
    Image img = ((ImageIcon) botonCancelar.getIcon()).getImage();
    Image newimg = img.getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
    botonCancelar.setIcon(new ImageIcon(newimg));

    btnEliminar = new JButton();
    btnEliminar.setToolTipText("Eliminar");
    btnEliminar.setIcon(new ImageIcon(VentanaBuscarDepartamento.class
        .getResource("/imgs/borrar.png")));
    btnEliminar.setBounds(558, 52, 32, 32);
    btnEliminar.setOpaque(false);
    btnEliminar.setContentAreaFilled(false);
    btnEliminar.setBorderPainted(false);
    Image img4 = ((ImageIcon) btnEliminar.getIcon()).getImage();
    Image newimg4 = img4.getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
    btnEliminar.setIcon(new ImageIcon(newimg4));

    labelTitulo = new JLabel();
    labelTitulo.setText("VER DEPARTAMENTO");
    labelTitulo.setBounds(248, 11, 270, 30);
    labelTitulo.setFont(new java.awt.Font("Verdana", 1, 18));

   
    btnEliminar.addActionListener(this);
    botonCancelar.addActionListener(this);

    getContentPane().add(botonCancelar);
    getContentPane().add(btnEliminar);
    getContentPane().add(labelTitulo);
    limpiar();

    setSize(640, 476);
    setTitle("Sistema E-vote: Paraguay Elecciones 2015");
    setLocationRelativeTo(null);
    getContentPane().setLayout(null);

    scrollPane = new JScrollPane();
    scrollPane.setAutoscrolls(true);
    scrollPane.setToolTipText("Lista de Departamentos");
    scrollPane.setBounds(0, 158, 634, 265);
    getContentPane().add(scrollPane);

    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
        KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "clickButtonescape");

    getRootPane().getActionMap().put("clickButtonescape", new AbstractAction() {
      public void actionPerformed(ActionEvent ae) {
        botonCancelar.doClick();
        System.out.println("button esc clicked");
      }
    });

    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
        KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "clickButtondelete");

    getRootPane().getActionMap().put("clickButtondelete", new AbstractAction() {
      public void actionPerformed(ActionEvent ae) {
        btnEliminar.doClick();
        System.out.println("button delete clicked");
      }
    });

    table_1 = new JXTable() {
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    table_1.setToolTipText("Listado de Generos.");
    table_1.setAutoCreateRowSorter(false);
    table_1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    scrollPane.setViewportView(table_1);
    // String[] columnNames = {"Picture", "Description"};
    table_1.setColumnControlVisible(true);

    TableRowFilterSupport.forTable(table_1).searchable(true).apply();
    
    
    txtBuscar = new JXFindBar(table_1.getSearchable());

    txtBuscar.setBounds(28, 52, 474, 33);
    getContentPane().add(txtBuscar);
    table_1.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent arg0) {

        List<String> selectedData = new ArrayList<String>();

        // int selectedRow = table_1.rowAtPoint(arg0.getPoint());

        // Object a =
        // table_1.getModel().getValueAt(table_1.convertRowIndexToView(selectedRow[0]),
        // 0);
        // int[] selectedColumns = table_1.getSelectedColumns();
        // System.out.println(a);

        // if (selectedRow >= 0) {
        int selectedRow = table_1.rowAtPoint(arg0.getPoint());
        // System.out.println(selectedRow);
        int col = 0;
        while (col < table_1.getColumnCount() + 1) {
          // System.out.println(table_1.getValueAt(selectedRow,
          // col));
          try {
            int row = table_1.rowAtPoint(arg0.getPoint());
            String table_click0 =
                table_1.getModel().getValueAt(table_1.convertRowIndexToModel(row), col).toString();
            // System.out.println(table_click0);

            selectedData.add(table_click0);
            // System.out.println(selectedData);

          } catch (Exception e) {
            System.out.println(e.getMessage());
          }

          col++;
        }
        // selectedData.ad table_1.getValueAt(selectedRow[i],
        // selectedColumns[0]);
        // txtId.setText(selectedData.get(0));
        // txtBuscar.setText(selectedData.get(3));

        // textFecha.setText(selectedData.get(2));
        // textUsu.setText(selectedData.get(4));
        // codTemporal.setText(selectedData.get(1));
        codTemporal = selectedData.get(0);

        departamentoSeleccionado = codTemporal;

        // System.out.println(departamentoSeleccionado);

        if (arg0.getClickCount() == 2) {
          // System.out.println("double clicked");
          VentanaBuscarDistrito distrito = new VentanaBuscarDistrito();
          distrito.setVisible(true);
          dispose();
        }

        // System.out.println("Selected: " + selectedData);

      }
    });
    table_1.getTableHeader().setReorderingAllowed(false);
    table_1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
    // recuperarDatos();
    table_1.setModel(model);
    table_1.removeColumn(table_1.getColumnModel().getColumn(0));
    JLabel lblListaDeGeneros = new JLabel();
    lblListaDeGeneros.setText("LISTA DE DEPARTAMENTOS");
    lblListaDeGeneros.setFont(new Font("Verdana", Font.BOLD, 18));
    lblListaDeGeneros.setBounds(147, 117, 325, 30);
    getContentPane().add(lblListaDeGeneros);

    JButton btnHome = new JButton("");
    btnHome.setToolTipText("Inicio");
    btnHome.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        DefinicionesGenerales menuprincipal = new DefinicionesGenerales();
        menuprincipal.setVisible(true);
        dispose();
      }
    });
    btnHome.setIcon(new ImageIcon(VentanaBuscarDepartamento.class.getResource("/imgs/home.png")));
    btnHome.setBounds(0, 0, 32, 32);
    Image img5 = ((ImageIcon) btnHome.getIcon()).getImage();
    Image newimg5 = img5.getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
    btnHome.setIcon(new ImageIcon(newimg5));
    getContentPane().add(btnHome);

    btnNuevo = new JButton("");
    btnNuevo.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        VentanaRegistroDepartamento departamento = new VentanaRegistroDepartamento();
        departamento.setVisible(true);
        dispose();
      }
    });
    btnNuevo.setToolTipText("Nuevo");
    btnNuevo.setOpaque(false);
    btnNuevo.setContentAreaFilled(false);
    btnNuevo.setBorderPainted(false);
    btnNuevo.setIcon(new ImageIcon(VentanaBuscarDepartamento.class.getResource("/imgs/add.png")));
    btnNuevo.setBounds(516, 52, 32, 32);
    Image img2 = ((ImageIcon) btnNuevo.getIcon()).getImage();
    Image newimg2 = img2.getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH);
    btnNuevo.setIcon(new ImageIcon(newimg2));
    getContentPane().add(btnNuevo);

    lblMensaje = new JLabel("");
    lblMensaje.setForeground(Color.RED);
    lblMensaje.setBounds(57, 88, 432, 14);
    getContentPane().add(lblMensaje);



    // table_1.getColumnModel().getColumn(0).setHeaderValue("Descripcion");

    ListSelectionModel cellSelectionModel = table_1.getSelectionModel();
    recuperarDatos();
    cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

    // cellSelectionModel.addListSelectionListener(new
    // ListSelectionListener() {
    // public void valueChanged(ListSelectionEvent e) {
    // String selectedData = null;
    //
    // int[] selectedRow = table_1.getSelectedRows();
    // int[] selectedColumns = table_1.getSelectedColumns();
    //
    // for (int i = 0; i < selectedRow.length; i++) {
    // for (int j = 0; j < selectedColumns.length; j++) {
    // selectedData = (String) table_1.getValueAt(selectedRow[i],
    // selectedColumns[j]);
    // }
    // }
    // System.out.println("Selected: " + selectedData);
    // }
    //
    // });

    DateFormat format = new SimpleDateFormat("dd/MM/yy hh:mm:ss");

    if (VentanaBuscarEvento.readOnly == true) {
      btnNuevo.setEnabled(false);
      btnNuevo.setToolTipText("Ya No se puede cargar datos durante ni despues la votacion");
      btnEliminar.setEnabled(false);
      btnEliminar.setToolTipText("Ya No se puede eliminar datos durante ni despues la votacion");
      // btnModificar.setEnabled(false);
      // btnModificar.setToolTipText("Ya No se puede Modificar datos durante ni despues la votacion");
    }

  }

  public void setCoordinador(Coordinador miCoordinador) {
    this.miCoordinador = miCoordinador;
  }

  public void actionPerformed(ActionEvent e) {

    if (e.getSource() == btnEliminar) {
      if (!codTemporal.equals("")) {
        int respuesta =
            JOptionPane.showConfirmDialog(this, "¿Esta seguro de eliminar el Departamento?",
                "Confirmación", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_NO_OPTION) {
          DepartamentoDAO departamentoDAO = new DepartamentoDAO();

          try {
            if (departamentoDAO.eliminarDepartamento(codTemporal) == false) {
              JOptionPane.showMessageDialog(null, "Error al intentar Borrar el Departamento",
                  "Error", JOptionPane.ERROR_MESSAGE);
            } else {

              lblMensaje.setText("Excelente, se ha eliminado el Departamento.");

              Timer t = new Timer(Login.timer, new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                  lblMensaje.setText(null);
                }
              });
              t.setRepeats(false);
              t.start();
              // JOptionPane.showMessageDialog(null,
              // "Excelente, se ha eliminado el Departamento ","Información",
              // JOptionPane.INFORMATION_MESSAGE);
              codTemporal = "";
              limpiar();

              model = new DepartamentoJTableModel();

              recuperarDatos();
              table_1.setModel(model);
              table_1.removeColumn(table_1.getColumnModel().getColumn(0));
            }

          } catch (Exception e2) {
            // TODO: handle exception
            JOptionPane.showMessageDialog(null, "sfdsfsfsdfs", "Información",
                JOptionPane.WARNING_MESSAGE);
          }

          // model.fireTableDataChanged();
          // table_1.repaint();
        }
      } else {
        lblMensaje.setText("Por favor seleccione que Departamento desea Eliminar");

        Timer t = new Timer(Login.timer, new ActionListener() {

          public void actionPerformed(ActionEvent e) {
            lblMensaje.setText(null);
          }
        });
        t.setRepeats(false);
        t.start();

        // JOptionPane.showMessageDialog(null,
        // "Por favor seleccione que Genero desea Eliminar",
        // "Información",JOptionPane.WARNING_MESSAGE);
      }

    }
    if (e.getSource() == botonCancelar) {
      DefinicionesGenerales definiciones = new DefinicionesGenerales();
      definiciones.setVisible(true);
      this.dispose();
    }

  }

  /**
   * permite cargar los datos de la persona consultada
   * 
   * @param miPersona
   */
  private void muestraPersona(JSONArray genero) {
    JSONArray a = (JSONArray) genero.get(0);
    // txtId.setText(Long.toString( (Long) a.get(0)) );
    // txtBuscar.setText((String) a.get(1));
    // textFecha.setText((String) a.get(2));
    // textUsu.setText((String) a.get(4));
    codTemporal = a.get(0).toString();

    habilita(true, false, false, false, false, true, false, true, true);
  }

  /**
   * Permite limpiar los componentes
   */
  public void limpiar() {
    // txtBuscar.setText("");

    // codTemporal.setText("");
    habilita(true, false, false, false, false, true, false, true, true);
  }

  /**
   * Permite habilitar los componentes para establecer una modificacion
   * 
   * @param codigo
   * @param nombre
   * @param edad
   * @param tel
   * @param profesion
   * @param cargo
   * @param bBuscar
   * @param bGuardar
   * @param bModificar
   * @param bEliminar
   */
  public void habilita(boolean codigo, boolean nombre, boolean edad, boolean tel,
      boolean profesion, boolean bBuscar, boolean bGuardar, boolean bModificar, boolean bEliminar) {
    // txtBuscar.setEditable(codigo);
    // botonModificar.setEnabled(true);
    btnEliminar.setEnabled(bEliminar);
  }

  private void recuperarDatos() {
    DepartamentoDAO departamentoDAO = new DepartamentoDAO();

    List<UcsawsDepartamento> listaDepartamentosByEvento =
        departamentoDAO.obtenerDepartamentoByIdEvento(Integer.parseInt(VentanaBuscarEvento.evento));

    List<UcsawsDepartamento> lista = new ArrayList<UcsawsDepartamento>();
    try {
      lista = listaDepartamentosByEvento;
    } catch (Exception e) {
      System.out.println(e);
    }

    if (lista.isEmpty()) {
      // JOptionPane.showMessageDialog(null, "algo salio mal",
      // "Advertencia", JOptionPane.WARNING_MESSAGE);
      // return lista;
    }

    else {
      obtenerModeloA(table_1, lista);

      // return lista;
    }

  }

  public AbstractTableModel obtenerModeloA(JTable tabla, List<UcsawsDepartamento> departamento) {

    // AbstractTableModel model = (DefaultTableModel) tabla.getModel();
    Iterator<UcsawsDepartamento> ite = departamento.iterator();

    // String header[] = new String[] { "ID","Item","Nro.",
    // "Desc. Evento","Inicio","Fin","Desc. Tipo Evento" };
    // model.setColumnIdentifiers(header);

    UcsawsDepartamento aux;
    Integer cont = 1;
    while (ite.hasNext()) {
      aux = ite.next();

      Object[] row =
          {aux.getIdDepartamento(), cont, aux.getNroDepartamento(), aux.getDescDepartamento()};

      // new
      // SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format((aux.getFechaNacimiento())),formatter.format(aux.getSalario())};
      model.departamento.add(row);

      cont++;

    }

    return model;
  }

  void LimpiarCampos() {
    // txtBuscar.setText("");
    // textFecha.setText("");
    // textUsu.setText("");
    codTemporal = "";
    // txtId.setText("");

  }

  public void filter(String query) {
    TableRowSorter sorter = new TableRowSorter(table_1.getModel());
    sorter.setRowFilter(RowFilter.regexFilter(query));
    table_1.setRowSorter(sorter);

    //
    // TableRowSorter<DefaultTableModel> tr = new
    // TableRowSorter<DefaultTableModel>(dm);
    //
    //
    //
    // table_1.setRowSorter(tr);
    //
    // tr.setRowFilter(RowFilter.regexFilter(query));

  }
}
