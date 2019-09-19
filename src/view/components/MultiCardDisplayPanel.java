package view.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;

import constants.Constants;

public class MultiCardDisplayPanel extends JPanel /*implements Runnable*/ {
	
	/*private Thread drawThread;*/
	
	private static final int DEFAULT_MARGIN = 50;
	
	private List<BufferedImage> toDraw;
	
	private List<String> toDrawTitles;
	
	private List<Rectangle> drawn;
	
	private Set<Integer> selected;
	
	// Determine space drawn between images
	private int margin;
	
	// Use this to make each image scale to a specified height
	private int scaleToHeight;
	
	// whether to expand horizontally on a single line or expand vertically
	private boolean multiLine;
	
	private int repaintFlag;
	
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;

	public MultiCardDisplayPanel() {
		this(null, null);
	}
	
	public MultiCardDisplayPanel(List<BufferedImage> cardImages, List<String> cardTitles) {
		super();
		this.toDraw = cardImages;
		this.toDrawTitles = cardTitles;
		selected = new HashSet<Integer>();
		this.margin = DEFAULT_MARGIN;
		scaleToHeight = 0;
		multiLine = true;
		repaintFlag = 0;
		drawn = new ArrayList<Rectangle>();
	}
	
	public void updateDisplay() {
		updateDisplay(null);
	}
	
	public void updateDisplay(List<BufferedImage> nDisp){
		updateDisplay(nDisp, null);
	}
	
	public void updateDisplay(List<BufferedImage> nDisp, List<String> nTitles) {
		if(nDisp != null) toDraw = nDisp;
		if(nTitles != null) toDrawTitles = nTitles;
		if(multiLine) {
			updateHeight();
		} else {
			updateWidth();
		}
		repaintFlag++;
		repaint();
	}
	
	public void updateImage(BufferedImage img, int index) {
		if(index >= toDraw.size()) return;
		toDraw.set(index, img);
		Rectangle area = drawn.get(index);
		paintImmediately(area.x, area.y, area.width, area.height);
	}
	
	private void updateHeight() {
		int x = margin, y = margin, maxHeight = 0, width = 0, height, futureX;
		for(int i = 0; i < toDraw.size(); i++) {
			if(toDraw.get(i) == null) return;
			width = toDraw.get(i).getWidth();
			height = toDraw.get(i).getHeight();
			if(scaleToHeight > 0) {
				width = (int)(width * ((scaleToHeight - (margin * 2.0)) / height));
				height = scaleToHeight - (margin * 2);
			}
			// Determine position for next image
			futureX = x + width;
			if(i < toDraw.size() - 1) futureX += margin + toDraw.get(i + 1).getWidth();
			if(getWidth() - futureX < 0) {
				y += maxHeight + margin;
				x = margin;
				maxHeight = 0;
			} else {
				maxHeight = Math.max(maxHeight, height);
				x += width + margin;
			}
		}
		
		setPreferredSize(new Dimension(getWidth(), y + maxHeight + margin));
	}
	
	private void updateWidth() {
		int x = margin, width = 0;
		for(int i = 0; i < toDraw.size(); i++) {
			if(toDraw.get(i) == null) break;
			width = toDraw.get(i).getWidth();
			if(scaleToHeight > 0) {
				width = (int)(width * ((scaleToHeight - (margin * 2.0)) / toDraw.get(i).getHeight()));
			}
			x += width + margin;
		}
		
		setPreferredSize(new Dimension(x + width + margin, getHeight()));
	}
	
	public void setMargin(int margin) {
		this.margin = margin;
	}
	
	public void scaleToHeight(int scale) {
		scaleToHeight = scale;
	}
	
	public void setMultiline(boolean multi) {
		multiLine = multi;
	}
	
	public void select(int newSelected) {
		selected.add(newSelected);
	}
	
	public void deselect(int notSelected) {
		selected.remove(notSelected);
	}
	
	public void deselectAll() {
		selected.clear();
	}
	
	public int getClickIndex(int x, int y) {
		for(int i = 0; i < drawn.size(); i++) {
			if(drawn.get(i).contains(x, y)) {
				return i;
			}
		}
		return -1;
	}
	
	@Override
	public void repaint() {
		if(repaintFlag > 0) {
			repaintFlag--;
		} else {
			return;
		}
		
		super.repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		drawBackground((Graphics2D)g);
		
		if(toDraw == null) return;
		
		drawn.clear();
		
		g.setColor(Constants.DRAW_HIGHLIGHT_COLOR);
		
		boolean drawTitles = toDrawTitles != null && toDrawTitles.size() == toDraw.size();
		
		int marginX = margin;
		int marginY = margin + (drawTitles ? 10 : 0);
		
		int x = marginX, y = marginY, maxHeight = 0, width = 0, height, futureX;
		for(int i = 0; i < toDraw.size(); i++) {
			if(toDraw.get(i) == null) return;
			width = toDraw.get(i).getWidth();
			height = toDraw.get(i).getHeight();
			if(scaleToHeight > 0) {
				width = (int)(width * ((scaleToHeight - (marginY * 2.0)) / height));
				height = scaleToHeight - (marginY * 2);
			}
			if(selected.contains(i)) {
				g.fillRect(x - 5, y - 5, width + 10, 5);
				g.fillRect(x - 5, y, 5, height);
				g.fillRect(x + width, y, 5, height);
				g.fillRect(x - 5, y + height, width + 10, 5);
			}
			g.drawImage(toDraw.get(i), x, y, width, height, null);
			if (drawTitles){
				g.setColor(Color.BLACK);
				g.drawString(toDrawTitles.get(i), x, y - 5);
				g.setColor(Constants.DRAW_HIGHLIGHT_COLOR);
			}
			drawn.add(new Rectangle(x, y, width, height));
			// Determine position for next image
			futureX = x + width;
			if(i < toDraw.size() - 1) futureX += marginX + toDraw.get(i + 1).getWidth();
			if(getWidth() - futureX < 0 && multiLine) {
				y += maxHeight + marginY;
				x = marginX;
				maxHeight = 0;
			} else {
				maxHeight = Math.max(maxHeight, height);
				x += width + marginX;
			}
		}
	}
	
	private void drawBackground(Graphics2D g) {
        GradientPaint gp = new GradientPaint(
            0, 0, Constants.BACK_COLOR_DARK, getWidth(), getHeight(), Constants.BACK_COLOR_LIGHT);
        g.setPaint(gp);
        g.fillRect(0, 0, getWidth(), getHeight());
	}
}
