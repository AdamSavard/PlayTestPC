package mainframe.popup;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import view.misc.TabControl;

// Dialog to choose which card game to use
public class ObjChooser extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private static Dimension backBuffer = new Dimension(300, 400);
	
	private JDialog window;
	
	private JPanel contents;
	
	private JScrollPane scroll;
	
	private JButton[] objButtons;
	
	private String choice; // Game name or NEW
	
	public ObjChooser(JFrame parent, String title, List<String> objList){
		super(parent, true);
		int totalLength = objList.size();
		String[] list = new String[totalLength];
		for(int i = 0; i < objList.size(); i++) list[i] = objList.get(i);
		init(parent, title, list);
	}
	
	private void init(JFrame parent, String title, String[] objList){
		Vector<Component> order = new Vector<Component>();
		window = this;
		choice = "";
		this.getContentPane().setPreferredSize(backBuffer);
    	this.getContentPane().setMinimumSize(backBuffer);
    	this.pack();
    	this.setLayout(null);
        this.setLocationByPlatform(true);
		this.setTitle(title);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		int btnHeight = 40, btnSpace = 10;
		
		contents = new JPanel();
		contents.setLayout(null);
		contents.setBounds(0, 0, backBuffer.width, (3 * btnSpace) + ((btnHeight + btnSpace) * objList.length));
		
		// Make buttons
		objButtons = new JButton[objList.length];
		for(int i = 0; i < objList.length; i++){ // For each object to choose from
			objButtons[i] = new JButton(objList[i]);
			objButtons[i].setBounds(btnSpace, btnSpace + ((btnHeight + btnSpace) * i), backBuffer.width - 20, btnHeight);
			objButtons[i].addKeyListener(new ButtonKeyListener());
			objButtons[i].addActionListener(new ButtonClicked());
			// Set keystrokes
			objButtons[i].addKeyListener(new ObjChooserListener());
			contents.add(objButtons[i]);
			order.add(objButtons[i]);
		}
		
		scroll = new JScrollPane(contents);
		scroll.setBounds(0, 0, backBuffer.width + 10, backBuffer.height + 10);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(scroll);
		
		// Set tab order
		this.setFocusTraversalPolicy(new TabControl(order));
	}
	
	public String getChoice() {
		return choice;
	}
	
	private class ButtonClicked implements ActionListener {
		public void actionPerformed(ActionEvent e){
			choice = ((JButton)e.getSource()).getText();
			window.dispose();
		}
	}
	
	private class ButtonKeyListener implements KeyListener {
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				choice = ((JButton)e.getComponent()).getText();
				window.dispose();
				return;
			}
		}
		public void keyReleased(KeyEvent e) { }
		public void keyTyped(KeyEvent e) { }
	}
	
	private class ObjChooserListener implements KeyListener {
        public void keyPressed(KeyEvent e) {
        	if(e.getID() != KeyEvent.KEY_PRESSED) return;
        	if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
        		window.dispose();
        		return;
        	}
        	if(e.getKeyCode() == KeyEvent.VK_UP){
        		window.transferFocusBackward();
        		return;
        	}
        	if(e.getKeyCode() == KeyEvent.VK_DOWN){
        		window.transferFocus();
        		return;
        	}
        }
		public void keyReleased(KeyEvent e) { }
		public void keyTyped(KeyEvent e) { }
	}
}
