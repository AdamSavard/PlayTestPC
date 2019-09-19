package mainframe.popup;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class AboutDialog extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private JLabel imgContainer;
	
	public AboutDialog(JFrame parent, ImageIcon img){
		super(parent, true);
        setLocationByPlatform(true);
        setResizable(false);
		setTitle("About This Awesome Software");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		imgContainer = new JLabel("", img, JLabel.CENTER);
		add(imgContainer);

		pack();
	}

}
