import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.TitledBorder;

public class main {

    public static void main(String[] args) {

        Runnable r = new Runnable() {

            public void run() {
                JFrame frame = new JFrame("Thermometer");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                JPanel gui = new JPanel(new BorderLayout(5,5));

                JPanel tempControls = new JPanel(new FlowLayout(FlowLayout.CENTER, 1,1));
                tempControls.setBorder(new TitledBorder("Temperature Controls") );
                JButton incTemp = new JButton("Increase Temperature");
                JButton decTemp = new JButton("Decrease Temperature");
                tempControls.add(incTemp);
                tempControls.add(decTemp);
                gui.add(tempControls, BorderLayout.NORTH);
                
                JPanel outsideLayoutOT = new JPanel();
                outsideLayoutOT.setLayout(new BoxLayout(outsideLayoutOT, BoxLayout.X_AXIS));
                JPanel insideLayoutOT = new JPanel();
                insideLayoutOT.setLayout(new BoxLayout(insideLayoutOT, BoxLayout.PAGE_AXIS));
                JLabel oTempLabel1 = new JLabel("Current Temperature: ");
                JLabel oTempLabel2 = new JLabel("10 degrees");
                oTempLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
                oTempLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
                insideLayoutOT.add(oTempLabel1);
                insideLayoutOT.add(oTempLabel2);
                outsideLayoutOT.add(insideLayoutOT);
                outsideLayoutOT.setBorder(new TitledBorder("Outside"));
                gui.add(outsideLayoutOT, BorderLayout.WEST);
                
                JPanel outsideLayoutTT = new JPanel();
                outsideLayoutTT.setLayout(new BoxLayout(outsideLayoutTT, BoxLayout.X_AXIS));
                JPanel insideLayoutTT = new JPanel();
                insideLayoutTT.setLayout(new BoxLayout(insideLayoutTT, BoxLayout.PAGE_AXIS));
                JLabel tTempLabel1 = new JLabel("Thermometer Temperature: ");
                JLabel tTempLabel2 = new JLabel("10 degrees");
                JLabel tTempLabel3 = new JLabel("Increasing");
                tTempLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
                tTempLabel2.setAlignmentX(Component.CENTER_ALIGNMENT);
                tTempLabel3.setAlignmentX(Component.CENTER_ALIGNMENT);
                insideLayoutTT.add(tTempLabel1);
                insideLayoutTT.add(tTempLabel2);
                insideLayoutTT.add(tTempLabel3);
                outsideLayoutTT.add(insideLayoutTT);
                outsideLayoutTT.setBorder(new TitledBorder("Thermometer"));
                gui.add(outsideLayoutTT, BorderLayout.EAST);

                JPanel imagePanel = new JPanel();
                DrawThermometer thermIMG = new DrawThermometer();
                imagePanel.add(thermIMG);
                imagePanel.setBorder(new TitledBorder("Picture"));
                gui.add(imagePanel, BorderLayout.CENTER);

                JPanel unitControls = new JPanel(new FlowLayout(FlowLayout.CENTER, 1,1));
                unitControls.setBorder(new TitledBorder("Unit Controls") );
                JButton kalvin = new JButton("Kalvin");
                JButton celcius = new JButton("Celcius");
                JButton fahrenheit = new JButton("Fahrenheit");
                unitControls.add(kalvin);
                unitControls.add(celcius);
                unitControls.add(fahrenheit);
                gui.add(unitControls, BorderLayout.SOUTH);

                frame.setContentPane(gui);

                frame.pack();

                frame.setLocationRelativeTo(null);
                try {
                    // 1.6+
                    frame.setLocationByPlatform(true);
                    frame.setMinimumSize(frame.getSize());
                } catch(Throwable ignoreAndContinue) {
                }

                frame.setVisible(true);
            }
        };
        SwingUtilities.invokeLater(r);
    }
    private static class DrawThermometer extends JPanel {
    	  protected void paintComponent(Graphics g) {
    	    super.paintComponent(g);
    	    g.drawRect(50, 0, 50, 300);
    	    g.drawOval(25, 300, 100, 100);
    	    g.setColor(Color.RED);  
    	    g.fillRect(50,200,50,100);
    	    g.fillOval(25, 300, 100, 100);
    	  }
    	  public Dimension getPreferredSize() {
    		    return new Dimension(150, 410); // appropriate constants
    		  }

    	}
}
