package view.components;

import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import constants.Constants;

public class CardDisplayPanel extends JPanel {
	
	// To draw or not to draw
	private boolean draw;
	
	private BufferedImage toDraw;
	
	// drag and drop rectangle data
	private int startX, startY, endX, endY;
	
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	public CardDisplayPanel(){
		super();
		draw = false;
	}
	
	public CardDisplayPanel(BufferedImage cardImg){
		super();
		toDraw = cardImg;
		draw = false;
	}

	public void setStartPoint(int x, int y){
		startX = x;
		startY = y;
	}
	
	public void setEndPoint(int x, int y){
		endX = x;
		endY = y;
	}
	
	public void clearStartEndPoint(){
		startX = startY = endX = endY = 0;
	}
	
	public void updateDisp(boolean doDraw){
		draw = doDraw;
		repaint();
	}
	
	public void updateDisp(BufferedImage nDisp){
		toDraw = nDisp;
		draw = nDisp != null;
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		drawBackground((Graphics2D)g);
		
		if(draw && toDraw != null){
			g.drawImage(toDraw, (getWidth() - toDraw.getWidth()) / 2,
				(getHeight() - toDraw.getHeight()) / 2,
				toDraw.getWidth(), toDraw.getHeight(), null);
		}
		
		drawRectangle(g);
	}
	
	private void drawBackground(Graphics2D g){
        GradientPaint gp = new GradientPaint(
            0, 0, Constants.BACK_COLOR_DARK, getWidth(), getHeight(), Constants.BACK_COLOR_LIGHT);
        g.setPaint(gp);
        g.fillRect(0, 0, getWidth(), getHeight());
	}
	
	private void drawRectangle(Graphics g){
		if(startX <= 0 || startY <= 0 || endX <= 0 || endY <= 0) return;
		if(startX >= getWidth() || startY >= getHeight() || endX >= getWidth() || endY >= getHeight()) return;
		
		int x1 = Math.min(startX, endX);
		int x2 = Math.max(startX, endX);
		int y1 = Math.min(startY, endY);
		int y2 = Math.max(startY, endY);
		
		g.setColor(Constants.SELECT_RECT_COLOR);
		g.drawRect(x1, y1, x2 - x1, y2 - y1);
	}
}
