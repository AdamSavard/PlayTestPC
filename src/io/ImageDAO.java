package io;

import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import org.pushingpixels.flamingo.api.common.icon.ResizableIcon;

public interface ImageDAO {
	
    public abstract Image read(String fileName);
	
	public abstract BufferedImage readBufferedImage(String fileName);
    
    public abstract ImageIcon readImageIcon(String fileName);
    
    public abstract ResizableIcon readResizableIcon(String fileName);
    
    public abstract Image open(Component parent);
    
	public abstract boolean write(String pathName, String imgName, String type, BufferedImage img);

}
