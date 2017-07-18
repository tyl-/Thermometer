import javax.swing.*;


public class main {

    public static void main(String[] args) {
    	JFrame frame = new JFrame("Thermometer Simulator");
    	Thread t = new Thread(new Worker(frame));
    	t.run();
    }
}
