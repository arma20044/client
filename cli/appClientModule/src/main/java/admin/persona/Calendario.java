package src.main.java.admin.persona;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Calendario {
int month = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);
int year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);;
JLabel l = new JLabel("", JLabel.CENTER); //mes anho

String day = ""; // dia

public static String fechafinalSeleccionada = "";

JDialog d;
JButton[] button = new JButton[49];

public Calendario(JFrame parent) {
      d = new JDialog();
      d.setModal(true);
      String[] header = { "DOM", "LUN", "MAR", "MIE", "JUE", "VIE", "SAB" };
      JPanel p1 = new JPanel(new GridLayout(7, 7));
      p1.setPreferredSize(new Dimension(430, 120));

      for (int x = 0; x < button.length; x++) {
              final int selection = x;
              button[x] = new JButton();
              button[x].setFocusPainted(false);
              button[x].setBackground(Color.white);
              if (x > 6)
                      button[x].addActionListener(new ActionListener() {
                              public void actionPerformed(ActionEvent ae) {
                                      day = button[selection].getActionCommand();
                                      d.dispose();
                              }
                      });
              if (x < 7) {
                      button[x].setText(header[x]);
                      button[x].setForeground(Color.red);
              }
              p1.add(button[x]);
      }
      JPanel p2 = new JPanel(new GridLayout(1, 3));
      JButton previous = new JButton("<< Mes Anterior");
      previous.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent ae) {
            	  if(month == 0){
            		  month = 11;
            		  year--;
            	  }
            	  else
                      month--;
                      displayDate();
              }
      });
      p2.add(previous);
      p2.add(l);
      JButton next = new JButton("Mes Siguiente >>");
      next.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent ae) {
            	  if(month==11){
            		  month = 0;
            		  year++;
            	  }
            	  else
                      month++;
                      displayDate();
              }
      });
      p2.add(next);
      d.add(p1, BorderLayout.CENTER);
      d.add(p2, BorderLayout.SOUTH);
      d.pack();
      d.setLocationRelativeTo(parent);
      displayDate();
      d.setVisible(true);
}

public void displayDate() {
      for (int x = 7; x < button.length; x++)
              button[x].setText("");
      java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
                      "MMMM yyyy");
      java.util.Calendar cal = java.util.Calendar.getInstance();
      cal.set(year, month, 1);
      int dayOfWeek = cal.get(java.util.Calendar.DAY_OF_WEEK);
      int daysInMonth = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
      for (int x = 6 + dayOfWeek, day = 1; day <= daysInMonth; x++, day++)
              button[x].setText("" + day);
      l.setText(sdf.format(cal.getTime()));
      
      d.setTitle("Seleccione una Fecha:");
      String[] array = l.getText().split(" ");
      Integer mes = Integer.valueOf(month) + 1;
      System.out.println (fechafinalSeleccionada = day + "/" + mes + "/" + array[1]);
}

public String setPickedDate() {
      if (day.equals(""))
              return day;
      java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
                      "dd-MM-yyyy");
      java.util.Calendar cal = java.util.Calendar.getInstance();
      cal.set(year, month, Integer.parseInt(day));
      return sdf.format(cal.getTime());
}
}

class Picker {
public static void main(String[] args) {
      JLabel label = new JLabel("Selected Date:");
      final JTextField text = new JTextField(20);
      JButton b = new JButton("popup");
      JPanel p = new JPanel();
      p.add(label);
      p.add(text);
      p.add(b);
      final JFrame f = new JFrame();
      f.getContentPane().add(p);
      f.pack();
      f.setVisible(true);
      b.addActionListener(new ActionListener() {
              public void actionPerformed(ActionEvent ae) {
                      text.setText(new Calendario(f).setPickedDate());
              }
      });
}


}
