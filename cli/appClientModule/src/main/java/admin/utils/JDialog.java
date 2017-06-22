package src.main.java.admin.utils;

import java.awt.EventQueue;

import javax.swing.JFrame;
import org.jdesktop.swingx.JXFindBar;
import java.awt.BorderLayout;
import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.JXTipOfTheDay;
import org.jdesktop.swingx.JXHeader;
import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.JXMultiThumbSlider;
import org.jdesktop.swingx.JXMonthView;
import org.jdesktop.swingx.JXLoginPane;
import org.jdesktop.swingx.JXSearchField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import org.jdesktop.swingx.JXGraph;
import org.jdesktop.swingx.JXRadioGroup;
import org.jdesktop.swingx.JXStatusBar;
import org.jdesktop.swingx.JXBusyLabel;
import org.jdesktop.swingx.JXGradientChooser;

public class JDialog {

  private JFrame frame;

  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          JDialog window = new JDialog();
          window.frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * Create the application.
   */
  public JDialog() {
    initialize();
  }

  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
    frame = new JFrame();
    frame.setBounds(100, 100, 450, 300);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(null);
    
    JXErrorPane errorPane = new JXErrorPane();
    errorPane.setBounds(47, 11, 334, 239);
    frame.getContentPane().add(errorPane);
  }
}
