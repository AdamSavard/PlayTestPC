package io;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.pushingpixels.flamingo.api.common.icon.ImageWrapperResizableIcon;
import org.pushingpixels.flamingo.api.common.icon.ResizableIcon;

public class ImageDAOFileSys implements ImageDAO {
	

	@Override
	public Image read(String fileName) {
		return FileHandler.getImage(Paths.IMG_PATH + fileName);
	}

	@Override
	public BufferedImage readBufferedImage(String fileName) {
    	return (BufferedImage)FileHandler.getImage(Paths.IMG_PATH + fileName);
	}

	@Override
	public ImageIcon readImageIcon(String fileName) {
    	return new ImageIcon(FileHandler.getImage(Paths.IMG_PATH + fileName));
	}

	@Override
	public ResizableIcon readResizableIcon(String fileName) {
    	return ImageWrapperResizableIcon.getIcon(FileHandler.getImage(Paths.IMG_PATH + fileName), new Dimension(48, 48));
	}

	@Override
	public Image open(Component parent) {
		File file = FileHandler.openFile(parent, "Open Image File", "Image File",new String[]{"jpg","png","gif","bmp"});
		if(file == null) return null;
		return FileHandler.getImage(file);
	}

	@Override
	public boolean write(String pathName, String imgName, String type, BufferedImage img) {
		String filePath = Paths.PRINT_PATH + "/";
		if(pathName != null) filePath += pathName + "/";
		filePath += imgName + ".png";
		File fileToWrite = new File(filePath);
    	fileToWrite.mkdirs();
		if(fileToWrite.exists()) fileToWrite.delete();
		try{
			ImageIO.write(img, "png", fileToWrite);
		}catch(IOException e){
			System.out.println("IOException - WriteFile.writePng");
			return false;
		}
		return true;
	}

}
