import java.awt.*;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class Worker implements Runnable{
	private JFrame frame;
	private ArrayList<String> tempCtrlBtnLbl = new ArrayList<String>();
	private ArrayList<String> unitCtrlBtnLbl = new ArrayList<String>();
	private JButton[] tempCtrlButtons = new JButton[2];
	private JButton[] unitCtrlButtons = new JButton[3];
	private String currentUnit;
	private ArrayList<String> currentStatus = new ArrayList<String>();
	private float insideTemp;
	private float outsideTemp;
	private TemperatureUpdater tUpdate = new TemperatureUpdater();
	private LayoutBuilder buildGUI = new LayoutBuilder();
	
	//Initializes variable values
	Worker(JFrame f){
		//Adds String to the array
		tempCtrlBtnLbl.add("Increase Outside Temperature");
		tempCtrlBtnLbl.add("Decrease Outside Temperature");
		
		//Set button listeners
		tempCtrlButtons[0] = new JButton(tempCtrlBtnLbl.get(0));
		tempCtrlButtons[0].addActionListener(new ButtonListener());
		tempCtrlButtons[1] = new JButton(tempCtrlBtnLbl.get(1));
		tempCtrlButtons[1].addActionListener(new ButtonListener());
		
		unitCtrlBtnLbl.add("Kelvin");
		unitCtrlBtnLbl.add("Celcius");
		unitCtrlBtnLbl.add("Fahrenheit");
		
		unitCtrlButtons[0] = new JButton(unitCtrlBtnLbl.get(0));
		unitCtrlButtons[0].addActionListener(new ButtonListener());
		unitCtrlButtons[1] = new JButton(unitCtrlBtnLbl.get(1));
		unitCtrlButtons[1].addActionListener(new ButtonListener());
		unitCtrlButtons[2] = new JButton(unitCtrlBtnLbl.get(2));
		unitCtrlButtons[2].addActionListener(new ButtonListener());
		
		//Starting values
		insideTemp = 0;
		outsideTemp = 0;
		currentUnit = "C";
		currentStatus.add("Stable");
		
		//Initializes frame
		frame = f;
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void run(){
		//While loop to continuously draw/update the frames
		while(true){
			
			//update every second
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//Update temperatures
			update();
			//Render graphical interface
			RenderGUI();
		}
    }
	
	private void update(){
		//update inside temperature
		float tempVal = tUpdate.UpdateTemp(tUpdate.Convert(insideTemp, currentUnit,  "C"), tUpdate.Convert(outsideTemp, currentUnit,  "C"));
		insideTemp = tUpdate.Convert(tempVal, "C", currentUnit);
		currentStatus.clear();
		//update status
		currentStatus = tUpdate.StatusChange(insideTemp, outsideTemp);
	}
	
	private void RenderGUI(){
		ArrayList<String> tempString = new ArrayList<String>();
		
		JPanel mainGUI = buildGUI.buildBorderLayout();
		
		//Add temperature control panel at the top
		JPanel tempControlPanel = buildGUI.buildFlowLayout("Temperature Controls", tempCtrlButtons);
		mainGUI.add(tempControlPanel, BorderLayout.NORTH);

		//Add left-middle panel for outside temperature information
		tempString.clear();
		tempString.add("Outside Temperature");
		tempString.add("Is set to: ");
		tempString.add(" ");
		tempString.add(Float.toString(outsideTemp) + " " + currentUnit);
		JPanel outsideTempPanel1 = buildGUI.buildInsideBoxLayout(tempString);
		JPanel outsideTempPanel2 = buildGUI.buildOutsideBoxLayout("Outside", outsideTempPanel1);
		mainGUI.add(outsideTempPanel2, BorderLayout.WEST);
		
		//Add right-middle panel for inside temperature information
		tempString.clear();
		tempString.add("Inside Temperature");
		tempString.add("Is currently at: ");
		tempString.add(" ");
		tempString.add(Float.toString(insideTemp) + " " + currentUnit);
		JPanel insideTempPanel1 = buildGUI.buildInsideBoxLayout(tempString);
		JPanel insideTempPanel2 = buildGUI.buildOutsideBoxLayout("Inside", insideTempPanel1);
		mainGUI.add(insideTempPanel2, BorderLayout.EAST);
		
		//Add center-middle panel for thermometer image
		tempString.clear();
		tempString.add("Currently");
		for (String s : currentStatus){
			tempString.add(s);
		}
		JPanel imagePanel1 = buildGUI.buildInsideBoxLayout(tempString);
		DrawThermometer thermIMG = new DrawThermometer();
		imagePanel1.add(thermIMG);
		JPanel imagePanel2 = buildGUI.buildOutsideBoxLayout("Status", imagePanel1);
		mainGUI.add(imagePanel2, BorderLayout.CENTER);
		
		//Add unit control panel at the bottom
		JPanel unitControls = buildGUI.buildFlowLayout("Unit Controls", unitCtrlButtons);
		mainGUI.add(unitControls, BorderLayout.SOUTH);
		
		//Updates the frame
		frame.setContentPane(mainGUI);
		frame.pack();
		frame.setVisible(true);
	}
	
	private class DrawThermometer extends JPanel {
		//Draw function for the thermometer image
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			//Thermometer shell
			g.drawRect(40, 30, 50, 101);
			
			//100 and 0 C markers
			g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
			g.setColor(Color.BLACK);
			g.drawString("100 C", 92, 40);
			g.drawString("0 C", 92, 130);
			
			g.setColor(Color.RED);
			float celciusOut = tUpdate.Convert(outsideTemp, currentUnit, "C");
			float celciusIn = tUpdate.Convert(insideTemp, currentUnit, "C");
			int yOffsetOut = 131 - (int)celciusOut;
			
			//Draw dashed line for outside temperature
			if(celciusOut >= 100){
				g.drawLine(41,31,46,31);
				g.drawLine(51,31,56,31);
				g.drawLine(61,31,66,31);
				g.drawLine(71,31,76,31);
				g.drawLine(81,31,86,31);
			}else if(celciusOut < 100 && celciusOut > 0){
				g.drawLine(41,yOffsetOut,46,yOffsetOut);
				g.drawLine(51,yOffsetOut,56,yOffsetOut);
				g.drawLine(61,yOffsetOut,66,yOffsetOut);
				g.drawLine(71,yOffsetOut,76,yOffsetOut);
				g.drawLine(81,yOffsetOut,86,yOffsetOut);
			}
			
			//Fill in thermometer based on inside temperature
			if(celciusIn >= 100){
				g.fillRect(41,31,49,100);
			}else if(celciusIn < 100 && celciusIn > 0){
				g.fillRect(41,131 - (int)celciusIn, 49, (int)celciusIn);
			}
		}
		public Dimension getPreferredSize() {
			return new Dimension(150, 150);
		}	
	}
	
	class ButtonListener implements ActionListener{
		//Button actions
		ButtonListener(){
		}
		public void actionPerformed(ActionEvent e){
			float tempCelcius;
			String actionCommand = e.getActionCommand();
			if(actionCommand.equals(tempCtrlBtnLbl.get(0))){
				outsideTemp = tUpdate.UpdateByCelcius(outsideTemp, currentUnit, "INC");
			}else if(actionCommand.equals(tempCtrlBtnLbl.get(1))){
				outsideTemp = tUpdate.UpdateByCelcius(outsideTemp, currentUnit, "DEC");
			}else if(actionCommand.equals(unitCtrlBtnLbl.get(0))){
				outsideTemp = tUpdate.Convert(outsideTemp, currentUnit, "K");
				insideTemp = tUpdate.Convert(insideTemp, currentUnit, "K");
				currentUnit = "K";
			}else if(actionCommand.equals(unitCtrlBtnLbl.get(1))){
				outsideTemp = tUpdate.Convert(outsideTemp, currentUnit, "C");
				insideTemp = tUpdate.Convert(insideTemp, currentUnit, "C");
				currentUnit = "C";
			}else if(actionCommand.equals(unitCtrlBtnLbl.get(2))){
				outsideTemp = tUpdate.Convert(outsideTemp, currentUnit, "F");
				insideTemp = tUpdate.Convert(insideTemp, currentUnit, "F");
				currentUnit = "F";
			}
		}
	}
}