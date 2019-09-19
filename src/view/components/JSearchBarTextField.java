package view.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class JSearchBarTextField extends JSuperTextArea implements FocusListener {

	private static final long serialVersionUID = 1L;

	public static final Color DISPLAY_TEXT_COLOR = Color.decode("#D3D3D3");
	
	private String displayText;
	
	public JSearchBarTextField(Image icon, String dispText) {
	    super(icon);
	    displayText = dispText;
	    this.addFocusListener(this);
	    // TODO key bindings to change enter key functionality
	}
	
	public String getDisplayText() {
	    return displayText;
	}
	
	public void setTextWhenNotFocused(String newText) {
		displayText = newText;
	}
	
	@Override
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    if (this.getText().equals("")) drawDisplayText(g);
	}
	
	private void drawDisplayText(Graphics g){
        g.setFont(g.getFont().deriveFont(Font.ITALIC));
        g.setColor(DISPLAY_TEXT_COLOR);
        int h = g.getFontMetrics().getHeight();
        int textBottom = (getHeight() - h) / 2 + h - 4;
        int x = this.getInsets().left;
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.drawString(displayText, x, textBottom);
	}
	
	//FocusListener implementation:
	public void focusGained(FocusEvent e) {
	    this.repaint();
	}
	
	public void focusLost(FocusEvent e) {
	    this.repaint();
	}

}
