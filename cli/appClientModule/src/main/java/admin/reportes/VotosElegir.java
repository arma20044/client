package src.main.java.admin.reportes;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import net.miginfocom.swing.MigLayout;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

import javax.swing.ImageIcon;

import src.main.java.admin.Reportes;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SwingConstants;

public class VotosElegir extends JFrame {

  private JPanel contentPane;
  private JButton botonCancelar;

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          VotosElegir frame = new VotosElegir();
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
  public VotosElegir() {
    setUndecorated(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(100, 100, 479, 150);
    setLocationRelativeTo(null);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    setContentPane(contentPane);
   
 
        
        JButton btnPorDepartamento = new JButton("Por Departamento");
        btnPorDepartamento.setBounds(100, 24, 121, 23);
        btnPorDepartamento.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent arg0) {
            VotosPorDepartamento votoDepartamento = new VotosPorDepartamento();
            votoDepartamento.start();
          }
        });
        contentPane.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("VOTOS POR:");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(195, 5, 110, 14);
        contentPane.add(lblNewLabel);
        contentPane.add(btnPorDepartamento);
        
        JButton btnPorZona = new JButton("Por Zona\r\n");
        btnPorZona.setBounds(123, 52, 75, 23);
        btnPorZona.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            VotosPorZona votosPorZona = new VotosPorZona();
            votosPorZona.start();
          }
        });
        
        JButton btnPorDistrito = new JButton("Por Distrito");
        btnPorDistrito.setBounds(293, 24, 85, 23);
        btnPorDistrito.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            VotosPorDistrito votosPorDistrito = new VotosPorDistrito();
            votosPorDistrito.start();
          }
        });
        contentPane.add(btnPorDistrito);
        contentPane.add(btnPorZona);
        
            
            botonCancelar = new JButton();
            botonCancelar.setBounds(404, 109, 65, 41);
            botonCancelar.addMouseListener(new MouseAdapter() {
              @Override
              public void mouseClicked(MouseEvent e) {
                Reportes reporte = new Reportes();
                reporte.setVisible(true);
                dispose();
              }
            });
            botonCancelar.setIcon(new ImageIcon(VotosElegir.class.getResource("/imgs/back2.png")));
            botonCancelar.setToolTipText("Atrás");
            botonCancelar.setOpaque(false);
            botonCancelar.setContentAreaFilled(false);
            botonCancelar.setBorderPainted(false);
            botonCancelar.setBackground(Color.WHITE);
            
            botonCancelar.setBorderPainted(false);
            Image img2 = ((ImageIcon) botonCancelar.getIcon()).getImage();
            Image newimg2 = img2.getScaledInstance(32, 32,
                java.awt.Image.SCALE_SMOOTH);
            JButton btnVotantesPorDepartamentos = new JButton("Por Local");
            btnVotantesPorDepartamentos.setBounds(298, 52, 75, 23);
            btnVotantesPorDepartamentos.addMouseListener(new MouseAdapter() {
              @Override
              public void mouseClicked(MouseEvent e) {
                VotosPorLocal votosPorLocal = new VotosPorLocal();
                votosPorLocal.start();
              }
            });
            contentPane.add(btnVotantesPorDepartamentos);
            botonCancelar.setIcon(new ImageIcon(newimg2));
            contentPane.add(botonCancelar);
  }
}
