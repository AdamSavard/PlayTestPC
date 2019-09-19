package view.components.choosers;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComponent;

import view.components.JSuperTextArea;
import constants.Constants;
import net.miginfocom.swing.MigLayout;

/*
 * Custom color chooser class. Should consist of a text field that only takes 6 digits and displays a #, and a button
 * that brings up a color chooser interface
 */
public class JSuperColorChooser extends JAbstractSuperChooser {

	private static final long serialVersionUID = 1L;
	
	private JSuperTextArea text;
	
	private JButton btn;
	
	public JSuperColorChooser() {
		super();
		this.setLayout(new MigLayout("wrap 2, insets 0 0 0 0","[][grow,fill]","[grow,fill]"));
		
		btn = new JButton("C");
		btn.addActionListener(new ChooseColorListener());
		this.add(btn);
		
		text = new JSuperTextArea();
		text.setText("000000");
		text.setCharacterLimit(6);
		text.setCharacterWhitelist(new char[]{'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','a','b','c','d','e','f'});
		text.addKeyListener(new ColorChangeListener());
		this.add(text);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setFont(Constants.DEFAULT_FONT);
		g.setColor(Color.LIGHT_GRAY);
		g.drawString("#", 0, 0);
	}
	
	private void chooseColor() {
		Color chosen = JColorChooser.showDialog(this, "Choose a color", Color.black);
		if(chosen == null) return;
		setColor(chosen);
	}
	
	public boolean setData(Object data) {
		if(!(data instanceof Color)) return false;
		setColor((Color)data);
		return true;
	}
	
	public Object getData() {
		return getColor();
	}
	
	public void setColor(Color newColor) {
		if(text == null) return;
		text.setText(colorToHex(newColor));
		for(ActionListener i : this.listeners) {
			i.actionPerformed(new ActionEvent(this, 0, ""));
		}
	}
	
	public Color getColor() {
		if(text == null) return Color.black;
		String txt = text.getText();
		if(txt.length() < 6) return Color.black;
		return hexToColor(txt);
	}

	@Override
	public void addKeyListener(KeyListener newListener) {
		if(text != null) text.addKeyListener(newListener);
	}

	@Override
	public void setFont(Font newFont) {
		if(text != null) text.setFont(newFont);
		if(btn != null) btn.setFont(newFont);
	}
	
	@Override
	public JComponent getTabComponent() {
		return text;
	}
	
	/**
	 * Convert hex color string to Color object
	 * @param colorStr e.g. "FFFFFF"
	 * @return 
	 */
	public static Color hexToColor(String colorStr) {
	    return new Color(
	            Integer.valueOf( colorStr.substring( 0, 2 ), 16 ),
	            Integer.valueOf( colorStr.substring( 2, 4 ), 16 ),
	            Integer.valueOf( colorStr.substring( 4, 6 ), 16 ));
	}
	
	public static String colorToHex(Color color) {
		return String.format("%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());
	}
	
	private class ColorChangeListener implements KeyListener {
		@Override
		public void keyPressed(KeyEvent e) {
		}
		@Override
		public void keyReleased(KeyEvent e) {
			if(text.getText().length() < 6) return;
			if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) return;
			setColor(hexToColor(text.getText()));
		}
		@Override
		public void keyTyped(KeyEvent e) {
		}
	}

	private class ChooseColorListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			chooseColor();
		}
	}
}
