package mainframe;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cards.components.CardComponent;
import constants.Constants;
import model.util.DrawableImage;

public final class DrawWizard {
	
	public static final Color TEXT_BORDER_UNSELECTED = new Color(200, 200, 200);
	public static final Color TEXT_BORDER_SELECTED = new Color(53, 94, 192);
	
	public static BufferedImage drawCard(int width, int height, int rotation, List<DrawableImage> comps, boolean rotate){
		if(width < 1 || height < 1) return null;
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = img.createGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.LIGHT_GRAY);
		g.drawRect(0, 0, width - 1, height - 1);
		
		// DRAW COMPONENTS
		g.setColor(Color.black);
		for(DrawableImage i : comps){
			g.drawImage(i, i.getX(), i.getY(), null);
		}
		
		// apply print rotation field of the template
		if (rotate) img = rotateImage(img, rotation);
		
		return img;
	}
	
	public static BufferedImage drawCardTemplate(int imgWidth, int imgHeight, int cardWidth, int cardHeight, int rotation, Map<Integer, CardComponent> components, Map<Integer, List<Integer>> zToIds){
		if(cardWidth < 1 || cardHeight < 1 || imgWidth < 1 || imgHeight < 1) return null;
		BufferedImage img = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = img.createGraphics();
		
		// draw card back
		int backX = (imgWidth - cardWidth) / 2;
		int backY = (imgHeight - cardHeight) / 2;
		g.setColor(Color.white);
		g.fillRect(backX, backY, cardWidth, cardHeight);
		g.setColor(Color.gray);
		g.drawRect(backX, backY, cardWidth - 1, cardHeight - 1);
		
		// DRAW COMPONENTS
		g.setColor(Color.black);
		CardComponent comp;
		BufferedImage compImg;
		for(int zz = 0; zz < 100; zz++){ // TODO draw template more efficiently
			if(zToIds.get(zz) == null) continue;
			for(int i : zToIds.get(zz)){
				comp = components.get(i);
				compImg = comp.getImage();
				g.drawImage(compImg, comp.getX() + backX, comp.getY() + backY, comp.getWidth(), comp.getHeight(), null);
			}
		}
		
		return img;
	}
	
	public static BufferedImage drawTextField(String stringToDraw, int width, int height, int horizon, int vert, int lines, Font font, boolean border, boolean selected){
		if(width < 1 || height < 1) return null;
		int drawX = 0, strWidth = 0, drawY = font.getSize();
		if(stringToDraw == null) stringToDraw = "";
		// Initialize image
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = img.createGraphics();
		// draw border
		if(border){
			g.setColor(selected ? TEXT_BORDER_SELECTED : TEXT_BORDER_UNSELECTED);
			g.drawLine(0, 0, width - 1, 0);
			g.drawLine(width - 1, 0, width - 1, height - 1);
			g.drawLine(width - 1, height - 1, 0, height - 1);
			g.drawLine(0, height - 1, 0, 0);
		}
		// draw text
		g.setColor(Color.BLACK);
		g.setFont(font);
		// Break up the string into lines
		List<String> strings = parseStringForDrawing(g, stringToDraw, width);
		// Draw each line
		for(int i = 0; i < strings.size() && i < lines; i++){
			strWidth = g.getFontMetrics().stringWidth(strings.get(i));
			drawX = 0;
			if(horizon == Constants.H_CENTER_ALIGNED) drawX = ((width - strWidth) / 2);
			if(horizon == Constants.RIGHT_ALIGNED) drawX = width - strWidth;
			drawY = 0;
			if(vert == Constants.TOP_ALIGNED) drawY = font.getSize() * (i + 1);
			if(vert == Constants.V_CENTER_ALIGNED) drawY = ((height - (strings.size() * font.getSize())) / 2) + (font.getSize() * (i + 1));
			if(vert == Constants.BOTTOM_ALIGNED) drawY = height - (font.getSize() * (i + 1));
			g.drawString(strings.get(i), drawX, drawY);
		}
		
		return img;
	}
	
	/*
	 * Draw line from top left to bottom right of a rectangular area.
	 * If inverted, draw from top right to bottom left instead.
	 * If selected, draw selection rectangle around border.
	 */
	public static BufferedImage drawLine(int width, int height, Color color, boolean selected, boolean inverted){
		if(width < 1 || height < 1) return null;
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = img.createGraphics();
		// draw border
		if(selected){
			g.setColor(TEXT_BORDER_SELECTED);
			g.drawLine(0, 0, width - 1, 0);
			g.drawLine(width - 1, 0, width - 1, height - 1);
			g.drawLine(width - 1, height - 1, 0, height - 1);
			g.drawLine(0, height - 1, 0, 0);
		}
		// draw line
		g.setColor(color);
		if(inverted){
			g.drawLine(width, 0, 0, height);
		} else {
			g.drawLine(0, 0, width, height);
		}
		
		return img;
	}
	
	public static BufferedImage drawRectangle(int width, int height, Color fill, Color outline, boolean selected){
		if(width < 1 || height < 1) return null;
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = img.createGraphics();
		// draw border
		g.setColor(outline);
		g.drawLine(0, 0, width - 1, 0);
		g.drawLine(width - 1, 0, width - 1, height - 1);
		g.drawLine(width - 1, height - 1, 0, height - 1);
		g.drawLine(0, height - 1, 0, 0);
		// draw line
		g.setColor(fill);
		g.fillRect(1, 1, width - 2, height - 2);
		
		return img;
	}
	public static BufferedImage drawImage(int width, int height, BufferedImage imgToDraw, boolean selected){
		if(width < 1 || height < 1) return null;
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = img.createGraphics();
		// draw border
		g.setColor(selected ? TEXT_BORDER_SELECTED : TEXT_BORDER_UNSELECTED);
		g.drawLine(0, 0, width - 1, 0);
		g.drawLine(width - 1, 0, width - 1, height - 1);
		g.drawLine(width - 1, height - 1, 0, height - 1);
		g.drawLine(0, height - 1, 0, 0);
		// draw image
		if(imgToDraw == null){
			// TODO draw image placeholder
		} else {
			g.drawImage(imgToDraw, 0, 0, width, height, null);
		}
		
		return img;
	}
	
	public static List<String> parseStringForDrawing(Graphics2D g, String stringToDraw, int width){
		String[] splitStrToDraw = stringToDraw.split("\\r?\\n", -1);
		List<String> output = new ArrayList<String>();
		String currLine = "", nextLine = "";
		for(int i = 0; i < splitStrToDraw.length; i++){
			currLine = splitStrToDraw[i];
			if("".equals(currLine)){
				output.add("");
				continue;
			}
			nextLine = "";
			while(currLine.length() > 0){
				// Determine length of current line on screen
				int lineWidth = g.getFontMetrics().stringWidth(currLine);
				// Remove one character at a time from that line until it is small enough
				while(lineWidth > width){
					nextLine = currLine.charAt(currLine.length() - 1) + nextLine;
					currLine = currLine.substring(0, currLine.length() - 1);
					lineWidth = g.getFontMetrics().stringWidth(currLine);
				}
				output.add(currLine);
				currLine = nextLine;
				nextLine = "";
			}
		}
		return output;
	}
	
	// rotations: 0 = none, 1 = 90 clockwise, 2 = 180, 3 = 270 (90 CC)
	private static BufferedImage rotateImage(BufferedImage img, int rotations) {
		rotations = rotations % 4;
		if(rotations == 0) return img;
		int w = img.getWidth(), h = img.getHeight();
		int w2 = rotations == 3 || rotations == 1 ? h : w;
		int h2 = rotations == 3 || rotations == 1 ? w : h;
	    double theta = rotations == 3 ? (-Math.PI / 2) : (rotations * Math.PI / 2);
	    AffineTransform tx = new AffineTransform();
	    tx.translate(w2 / 2.0, h2 / 2.0);
	    tx.rotate(theta);
	    tx.translate(-1 * w / 2.0, -1 * h / 2.0);
	    BufferedImage output = new BufferedImage(w2, h2, img.getType());
	    AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
	    op.filter(img, output);
	    return output;
	}
	
}
