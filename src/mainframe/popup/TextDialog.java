package mainframe.popup;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * Dialog box featuring a label, a text box to enter a string into and up to two buttons
 * @author Adam
 *
 */
public class TextDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	public static Dimension backBuffer = new Dimension(300, 80);
	
	public static Font mainFont = new Font("Sans Serif", Font.PLAIN, 12);
	
	public JDialog window;
	
	private JLabel inputTxt;
	private JTextField outputTxt;
	private JButton bttn0, bttn1;
	
	public int selection;
	public String output;
	
	public TextDialog(JFrame parent, String title, String label, String bttn0txt, String bttn1txt){
		super(parent, true);
		this.getContentPane().setPreferredSize(backBuffer);
    	this.getContentPane().setMinimumSize(backBuffer);
    	this.pack();
    	this.setLayout(null);
        this.setLocationByPlatform(true);
		this.setTitle(title);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		window = this;
		selection = -1;
		output = "";
		
		inputTxt = new JLabel(label);
		inputTxt.setFont(mainFont);
		inputTxt.setBounds(5, 5, inputTxt.getPreferredSize().width, inputTxt.getPreferredSize().height);
		this.add(inputTxt);
		
		outputTxt = new JTextField();
		outputTxt.setFont(mainFont);
		outputTxt.addKeyListener(new DialogKeyListener());
		outputTxt.setBounds(5, 10 + inputTxt.getPreferredSize().height, backBuffer.width - 5, outputTxt.getPreferredSize().height);
		this.add(outputTxt);
		
		bttn0 = new JButton(bttn0txt);
		bttn0.setFont(mainFont);
		bttn0.setBounds(backBuffer.width - bttn0.getPreferredSize().width - (bttn1txt == null ? 0 : bttn1.getPreferredSize().width + 5), backBuffer.height - 30, bttn0.getPreferredSize().width, 30);
		bttn0.addActionListener(new BttnClicked());
		this.add(bttn0);
		
		if(bttn1txt != null){
			bttn1 = new JButton(bttn0txt);
			bttn1.setFont(mainFont);
			bttn1.setBounds(backBuffer.width - bttn1.getPreferredSize().width - 5, backBuffer.height - 35, bttn1.getPreferredSize().width, 30);
			bttn1.addActionListener(new BttnClicked());
			this.add(bttn1);
		}
	}
	
	private class DialogKeyListener implements KeyListener {
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				selection = 0;
				output = outputTxt.getText();
				window.dispose();
				return;
			}
		}
		public void keyReleased(KeyEvent e) { }
		public void keyTyped(KeyEvent e) { }
	}
	
	private class BttnClicked implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			selection = ((JButton)e.getSource()).equals(bttn0) ? 0 : 1;
			output = outputTxt.getText();
			window.dispose();
		}
	}

}
