import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.TitledBorder;

public class LayoutBuilder implements Runnable{
	private JFrame frame;
	private ArrayList<String> tempControlButtons = new ArrayList<String>();
	private ArrayList<String> unitControlButtons = new ArrayList<String>();
	private String currentUnit;
	private float thermTemp;
	private float outTemp;
	
	LayoutBuilder(JFrame f){
		frame = f;
		tempControlButtons.add("Increase Temperature");
		tempControlButtons.add("Decrease Temperature");
		unitControlButtons.add("Kelvin");
		unitControlButtons.add("Celcius");
		unitControlButtons.add("Fahrenheit");
		thermTemp = 0;
		outTemp = 0;
		currentUnit = "C";
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public void run(){
		while(true){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			JPanel gui = new JPanel(new BorderLayout(5,5));
			ArrayList<String> nameList = new ArrayList<String>();
			nameList.add("Increase Temperature");
			nameList.add("Decrease Temperature");
			JPanel tempControls = buildFlowLayout("Temperature Controls", tempControlButtons);
			gui.add(tempControls, BorderLayout.NORTH);
        
			nameList.clear();
			nameList.add("Current Temperature: ");
			nameList.add(Float.toString(outTemp) + " " + currentUnit);
			JPanel outsideTempPanel = buildDualBoxLayout("Outside", nameList);
			gui.add(outsideTempPanel, BorderLayout.WEST);
        
			nameList.clear();
			nameList.add("Thermometer Temperature: ");
			TemperatureUpdater t = new TemperatureUpdater();
			float celciusTempT = t.Convert(thermTemp, currentUnit, "C");
			float celciusTempO = t.Convert(outTemp, currentUnit, "C");
			if(Math.abs(celciusTempT - celciusTempO) < 0.10){
				thermTemp = outTemp;
			}else{
				thermTemp = t.Convert(t.UpdateTemp(celciusTempT, celciusTempO), "C", currentUnit);
			}
			nameList.add(Float.toString(thermTemp) + " " + currentUnit);
			if(thermTemp > outTemp){
				nameList.add("Decreasing");
			}else if(thermTemp < outTemp){
				nameList.add("Increasing");
			}else{
				nameList.add("Stable");
			}
			JPanel thermometerTempPanel = buildDualBoxLayout("Thermometer", nameList);
			gui.add(thermometerTempPanel, BorderLayout.EAST);

			JPanel imagePanel = new JPanel();
			DrawThermometer thermIMG = new DrawThermometer();
			imagePanel.add(thermIMG);
			imagePanel.setBorder(new TitledBorder("Picture"));
			gui.add(imagePanel, BorderLayout.CENTER);

			JPanel unitControls = buildFlowLayout("Unit Controls", unitControlButtons);
			gui.add(unitControls, BorderLayout.SOUTH);

			frame.setContentPane(gui);

			frame.pack();

			frame.setLocationRelativeTo(null);
			try {
				frame.setLocationByPlatform(true);
				frame.setMinimumSize(frame.getSize());
			} catch(Throwable ignoreAndContinue) {
			}

			frame.setVisible(true);
		}
    }
	private class DrawThermometer extends JPanel {
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawRect(50, 0, 50, 300);
			g.drawOval(25, 290, 100, 100);
			g.setColor(Color.RED);
			TemperatureUpdater t = new TemperatureUpdater();
			float celciusTemp = t.Convert(thermTemp, currentUnit, "C");
			if(celciusTemp < 100){
				g.fillRect(50,300 - (int)celciusTemp*3,50, (int)celciusTemp*3);
			}else{
				g.fillRect(50,0,50,300);
			}
			g.fillOval(25, 290, 100, 100);
		}
		public Dimension getPreferredSize() {
			return new Dimension(150, 410);
		}	
	}
	private JPanel buildFlowLayout(String title, ArrayList<String> buttons){
		JPanel layoutPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 1,1));
        layoutPanel.setBorder(new TitledBorder(title) );
        for(int i = 0; i < buttons.size(); i++){
        	JButton jButton = new JButton(buttons.get(i));
        	jButton.addActionListener(new ButtonListener());
        	layoutPanel.add(jButton);
        }
        return layoutPanel;
	}
	private JPanel buildDualBoxLayout(String title, ArrayList<String> labels){
		JPanel outsideLayout = new JPanel();
        outsideLayout.setLayout(new BoxLayout(outsideLayout, BoxLayout.X_AXIS));
        JPanel insideLayout = new JPanel();
        insideLayout.setLayout(new BoxLayout(insideLayout, BoxLayout.PAGE_AXIS));
        for(String lab : labels){
        	JLabel jLabel = new JLabel(lab);
        	jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        	insideLayout.add(jLabel);
        }
        outsideLayout.setBorder(new TitledBorder(title));
        outsideLayout.add(insideLayout);
		return outsideLayout;
	}
	class ButtonListener implements ActionListener{
		ButtonListener(){
		}
		public void actionPerformed(ActionEvent e){
			TemperatureUpdater t = new TemperatureUpdater();
			float tempCelcius;
			switch(e.getActionCommand()){
			case "Increase Temperature":
				t = new TemperatureUpdater();
				tempCelcius = t.Convert(outTemp, currentUnit, "C");
				tempCelcius++;
				outTemp = t.Convert(tempCelcius, "C", currentUnit);
				break;
			case "Decrease Temperature":
				t = new TemperatureUpdater();
				tempCelcius = t.Convert(outTemp, currentUnit, "C");
				tempCelcius--;
				outTemp = t.Convert(tempCelcius, "C", currentUnit);
				break;
			case "Kelvin":
				outTemp = t.Convert(outTemp, currentUnit, "K");
				thermTemp = t.Convert(thermTemp, currentUnit, "K");
				currentUnit = "K";
				break;
			case "Celcius":
				outTemp = t.Convert(outTemp, currentUnit, "C");
				thermTemp = t.Convert(thermTemp, currentUnit, "C");
				currentUnit = "C";
				break;
			case "Fahrenheit":
				outTemp = t.Convert(outTemp, currentUnit, "F");
				thermTemp = t.Convert(thermTemp, currentUnit, "F");
				currentUnit = "F";
				break;
			}
		}
	}
}