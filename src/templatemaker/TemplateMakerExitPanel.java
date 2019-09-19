package templatemaker;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import constants.Constants;

public class TemplateMakerExitPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	/*
	 * Label to display messages
	 */
	private JLabel message;
	
	/*
	 * Button to close the form
	 */
	private JButton cancel;
	
	/*
	 * Button to finish editing
	 */
	private JButton finish;
	
	private CardTemplateController controller;
	
	public TemplateMakerExitPanel(CardTemplateController newControl){
		super();
		controller = newControl;
		this.setLayout(new MigLayout("insets 0 0 0 0","2[grow,fill][][]2","[]4"));
		
		message = new JLabel();
		message.setFont(Constants.BIG_DEFAULT_FONT);
		message.setForeground(Color.RED);
		add(message);
		
		cancel = new JButton("Exit Without Saving");
		cancel.setFont(Constants.BIG_DEFAULT_FONT);
		cancel.addActionListener(new CancelClicked());
		cancel.setBackground(new Color(199, 23, 10));
		add(cancel);
		
		finish = new JButton("Save and Finish");
		finish.setFont(Constants.BIG_DEFAULT_FONT);
		finish.addActionListener(new FinishClicked());
		finish.setBackground(new Color(59,89,152));
		add(finish);
	}
	
	/******************************** vvv EVENT LISTENERS vvv ********************************/

	private class CancelClicked implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			controller.cancelTemplate();
		}
	}
	
	private class FinishClicked implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			controller.finishTemplate();
		}
	}
	
	/******************************** ^^^ EVENT LISTENERS ^^^ ********************************/

	public void displayMessage(String mess){
		message.setText(mess);
	}
}
