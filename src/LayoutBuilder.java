import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

import javax.swing.border.TitledBorder;

public class LayoutBuilder{
	LayoutBuilder(){	
		
	}
	
	//Main panel
	public JPanel buildBorderLayout(){
		JPanel borderPanel = new JPanel(new BorderLayout(5,5));
		return borderPanel;
	}
	
	//Flow layouts, panel used for button controls
	public JPanel buildFlowLayout(String title, JButton[] buttons){
		JPanel layoutPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 1,1));
        layoutPanel.setBorder(new TitledBorder(title) );
        for(JButton button : buttons){
        	layoutPanel.add(button);
        }
        return layoutPanel;
	}
	
	//Part 1 of dualboxlayout, inside panel used for panel contents
	public JPanel buildInsideBoxLayout(ArrayList<String> labels){
		JPanel insideLayout = new JPanel();
		insideLayout.setLayout(new BoxLayout(insideLayout, BoxLayout.PAGE_AXIS));
        for(String lab : labels){
        	JLabel jLabel = new JLabel(lab);
        	jLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        	insideLayout.add(jLabel);
        }
		return insideLayout;
	}
	
	//Part 2 of dualboxlayout, outside panel used for alignment
	public JPanel buildOutsideBoxLayout(String title, JPanel insideLayout){
		JPanel outsideLayout = new JPanel();
        outsideLayout.setLayout(new BoxLayout(outsideLayout, BoxLayout.X_AXIS));
        outsideLayout.setBorder(new TitledBorder(title));
        outsideLayout.add(insideLayout);
		return outsideLayout;
	}
}