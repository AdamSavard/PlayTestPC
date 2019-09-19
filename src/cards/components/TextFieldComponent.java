package cards.components;

import java.awt.Font;
import java.awt.image.BufferedImage;

import mainframe.DrawWizard;
import constants.Constants;

/*
 * Represents a text field on a card template
 */
public class TextFieldComponent extends CardComponent {

	private static final long serialVersionUID = 1L;

	private Integer horizontalAlign;
	
	private Integer verticalAlign;
	
	private Font font;
	
	public TextFieldComponent(String iName){
		super(iName);
		horizontalAlign = Constants.LEFT_ALIGNED;
		verticalAlign = Constants.TOP_ALIGNED;
		lines = 1;
		font = Constants.DEFAULT_FONT;
	}
	
	public TextFieldComponent(String iName, int iX, int iY, int iZ, int iWidth, int iHeight, int iH, int iV, int iL, Font iFont){
		super(iName, iX, iY, iZ, iWidth, iHeight);
		horizontalAlign = iH;
		verticalAlign = iV;
		lines = iL;
		font = iFont;
	}
	
	public Integer getHorizontalAlign(){
		return horizontalAlign;
	}
	
	public Integer setHorizontalAlign(Integer newH){
		if(newH < 0 || newH > 2) return -1;
		horizontalAlign = newH;
		updateImage();
		return horizontalAlign;
	}
	
	public boolean isLeftAligned(){
		return horizontalAlign == Constants.LEFT_ALIGNED;
	}
	
	public boolean isHCenterAligned(){
		return horizontalAlign == Constants.H_CENTER_ALIGNED;
	}
	
	public boolean isRightAligned(){
		return horizontalAlign == Constants.RIGHT_ALIGNED;
	}
	
	public Integer getVerticalAlign(){
		return verticalAlign;
	}
	
	public Integer setVerticalAlign(Integer newV){
		if(newV < 0 || newV > 2) return -1;
		verticalAlign = newV;
		updateImage();
		return verticalAlign;
	}
	
	public boolean isTopAligned(){
		return verticalAlign == Constants.TOP_ALIGNED;
	}
	
	public boolean isVCenterAligned(){
		return verticalAlign == Constants.V_CENTER_ALIGNED;
	}
	
	public boolean isBottomAligned(){
		return verticalAlign == Constants.BOTTOM_ALIGNED;
	}

	@Override
	public int getLines(){
		return lines;
	}
	
	@Override
	public int setLines(Integer newLines){
		if(newLines < 0) return -1;
		if(newLines == 0) newLines = 1; // Allow 0 as an input but don't allow < 1 for text field
		lines = newLines;
		updateImage();
		return lines;
	}
	
	public String getFontFamily(){
		return font.getFamily();
	}
	
	public Integer getFontStyle(){
		return font.getStyle();
	}
	
	public Integer getFontSize(){
		return font.getSize();
	}
	
	public float setFontSize(Integer newSize){
		if(font.getSize() == newSize) return newSize;
		font = new Font(font.getFamily(), font.getStyle(), newSize);
		updateImage();
		return newSize;
	}
	
	public int setFontStyle(Integer newStyle){
		if(font.getStyle() == newStyle) return newStyle;
		font = new Font(font.getFamily(), newStyle, font.getSize());
		updateImage();
		return newStyle;
	}
	
	public String setFontFamily(String newFamily){
		if(font.getFamily().equals(newFamily)) return newFamily;
		font = new Font(newFamily, font.getStyle(), font.getSize());
		updateImage();
		return newFamily;
	}
	
	@Override
	public void updateImage(){
		img = DrawWizard.drawTextField(name, width, height, horizontalAlign, verticalAlign, lines, font, true, selected);
	}
	
	@Override
	public BufferedImage getImage(Object data){
		String dataStr = editable ? (data == null ? "" : (String)data) : name;
		return DrawWizard.drawTextField(dataStr, width, height, horizontalAlign, verticalAlign, lines, font, false, selected);
	}

	@Override
	public String getTypeString() {
		return Constants.TEXT_TYPE;
	}
}
