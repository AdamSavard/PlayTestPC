package cards.components;

import java.awt.Color;
import java.awt.image.BufferedImage;

import constants.Constants;
import mainframe.DrawWizard;

/*
 * Represents a rectangle on a card template
 */
public class RectangleComponent extends CardComponent {
	
	private static final long serialVersionUID = 1L;
	
	private Color fill;
	public Color getFillColor(){
		return fill;
	}
	public void setFillColor(Color newColor){
		if(newColor == null && fill == null) return;
		if(newColor != null && fill != null && newColor.equals(fill)) return;
		fill = newColor;
		updateImage();
	}
	
	private Color outline;
	public Color getOutlineColor(){
		return outline;
	}
	public void setOutlineColor(Color newColor){
		if(newColor == null && outline == null) return;
		if(newColor != null && outline != null && newColor.equals(outline)) return;
		outline = newColor;
		updateImage();
	}
	
	@Override
	public void updateImage(){
		img = DrawWizard.drawRectangle(width, height, fill, outline, selected);
	}
	
	@Override
	public BufferedImage getImage(Object data){ // TODO handle data
		return DrawWizard.drawRectangle(width, height, fill, outline, selected);
	}
	
	public RectangleComponent(String iName){
		super(iName);
		fill = outline = Color.black;
	}
	
	public RectangleComponent(String iName, int iX, int iY, int iZ, int iWidth, int iHeight, Color iFill, Color iOutline){
		super(iName, iX, iY, iZ, iWidth, iHeight);
		fill = iFill;
		outline = iOutline;
	}
	
	@Override
	public String getTypeString() {
		return Constants.RECT_TYPE;
	}
}
