package cards.components;

import java.awt.Color;
import java.awt.image.BufferedImage;

import constants.Constants;
import mainframe.DrawWizard;

/*
 * Represents a line on a card template
 */
public class LineComponent extends CardComponent {
	
	private static final long serialVersionUID = 1L;
	
	private Color color;
	public Color getColor(){
		return color;
	}
	public void setColor(Color newColor){
		if(newColor == null && color == null) return;
		if(newColor != null && color != null && newColor.equals(color)) return;
		color = newColor;
		updateImage();
	}
	
	private Integer startX;
	public Integer getStartX(){
		return startX;
	}
	public void setStartX(Integer newX){
		startX = newX;
		width = Math.abs(endX - startX) + 1;
		super.setX(Math.min(startX, endX));
	}
	
	private Integer startY;
	public Integer getStartY(){
		return startY;
	}
	public void setStartY(Integer newY){
		if(newY > endY){
			startY = endY;
			endY = newY;
		} else {
			startY = newY;
		}
		height = Math.abs(endY - startY) + 1;
	}
	
	private Integer endX;
	public Integer getEndX(){
		return endX;
	}
	public void setEndX(Integer newX){
		endX = newX;
		width = Math.abs(endX - startX) + 1;
		super.setX(Math.min(startX, endX));
	}
	
	private Integer endY;
	public Integer getEndY(){
		return endY;
	}
	public void setEndY(Integer newY){
		if(newY < startY){
			endY = startY;
			startY = newY;
		} else {
			endY = newY;
		}
		height = Math.abs(endY - startY) + 1;
	}
	
	@Override
	public Integer setX(Integer newX){
		if(startX < endX){
			startX = newX;
			endX = newX + width;
			return super.setX(startX);
		} else {
			endX = newX;
			startX = newX + width;
			return super.setX(endX);
		}
	}
	
	@Override
	public Integer setY(Integer newY){
		startY = newY;
		endY = newY + height;
		return super.setY(startY);
	}
	
	@Override
	public Integer setWidth(Integer newWidth){
		if(startX < endX){
			endX = startX + newWidth;
		} else {
			startX = endX + newWidth;
		}
		return super.setWidth(newWidth);
	}
	@Override
	public Integer setHeight(Integer newHeight){
		endY = startY + newHeight;
		return super.setHeight(newHeight);
	}
	
	@Override
	public void updateImage(){
		img = DrawWizard.drawLine(Math.abs(endX - startX) + 1, Math.abs(endY - startY) + 1, color, selected, startX > endX);
	}
	
	@Override
	public BufferedImage getImage(Object data){
		this.color = (Color)data;
		return DrawWizard.drawLine(Math.abs(endX - startX) + 1, Math.abs(endY - startY) + 1, color, selected, startX > endX);
	}
	
	public LineComponent(String iName){
		super(iName);
		color = Color.black;
		startX = startY = endX = endY = 0;
	}
	
	public LineComponent(String iName, int iStartX, int iStartY, int iZ, int iEndX, int iEndY, Color iColor){
		super(iName, Math.min(iStartX, iEndX), Math.min(iStartY, iEndY), iZ, Math.abs(iEndX - iStartX) + 1, Math.abs(iEndY - iStartY) + 1);
		startX = iStartX;
		startY = iStartY;
		endX = iEndX;
		endY = iEndY;
		color = iColor;
	}
	
	@Override
	public String getTypeString() {
		return Constants.LINE_TYPE;
	}
}
