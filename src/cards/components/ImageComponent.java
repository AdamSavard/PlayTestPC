package cards.components;

import java.awt.image.BufferedImage;

import constants.Constants;
import mainframe.DrawWizard;

/*
 * Represents an image on a card template
 */
public class ImageComponent extends CardComponent {
	
	private static final long serialVersionUID = 1L;
	
	private BufferedImage img;
	public BufferedImage getImage(){
		return img;
	}
	public void setImage(BufferedImage newImage){
		if(newImage == null && img == null) return;
		if(newImage != null && img != null && img.equals(newImage)) return;
		img = newImage;
		updateImage();
	}
	
	@Override
	public void updateImage(){
		img = DrawWizard.drawImage(width, height, img, selected);
	}
	
	@Override
	public BufferedImage getImage(Object data){
		return DrawWizard.drawImage(width, height, img, selected);
	}
	
	public ImageComponent(String iName){
		super(iName);
		img = null;
	}
	
	public ImageComponent(String iName, int iX, int iY, int iZ, int iWidth, int iHeight){
		super(iName, iX, iY, iZ, iWidth, iHeight);
		img = null;
	}
	
	@Override
	public String getTypeString() {
		return Constants.IMAGE_TYPE;
	}
}
