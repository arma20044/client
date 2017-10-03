package src.main.java.admin.utils;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JProgressBar;

public class ProgressBarStepIndeterminate {


 
    public static void main(String[] argv) throws Exception {
      int minimum = 0;
      int maximum = 100;
      JProgressBar progress = new JProgressBar(minimum, maximum);

      // Overlay a string showing the percentage done
      progress.setStringPainted(true);

    
  }
}
