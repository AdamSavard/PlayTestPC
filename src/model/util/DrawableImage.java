package model.util;

import java.awt.image.BufferedImage;

public class DrawableImage extends BufferedImage {

	private int x;
	
	private int y;
	
	public DrawableImage(int width, int height, int imageType) {
		super(width, height, imageType);
		x = y = 0;
	}
	
	public DrawableImage(BufferedImage img, int x, int y) {
		super(img.getColorModel(), img.copyData(null), img.getColorModel().isAlphaPremultiplied(), null);
		this.x = x;
		this.y = y;
	}
	
	public int getX(){
		return x;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public int getY(){
		return y;
	}
	
	public void setY(int y){
		this.y = y;
	}
}
