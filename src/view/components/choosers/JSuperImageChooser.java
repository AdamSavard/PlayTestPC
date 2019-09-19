package view.components.choosers;

import io.DAOFactory;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;

import net.miginfocom.swing.MigLayout;

/*
 * Custom image chooser class
 */
public class JSuperImageChooser extends JAbstractSuperChooser {

	private static final long serialVersionUID = 1L;
	
	private BufferedImage image;
	
	private List<ActionListener> imgListener;
	
	private JButton chooseImage, removeImage;
	
	public JSuperImageChooser(){
		super();
		image = null;
		imgListener = new ArrayList<ActionListener>();
		this.setLayout(new MigLayout("wrap 2","[grow,fill][grow,fill]","[grow,fill]"));
		
		chooseImage = new JButton("Choose");
		chooseImage.addActionListener(new ChooseImageListener());
		this.add(chooseImage);
		
		removeImage = new JButton("Remove");
		removeImage.addActionListener(new RemoveImageListener());
		this.add(removeImage);
	}
	
	private class ChooseImageListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			chooseImage();
		}
	}
	
	private class RemoveImageListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			setImage(null);
		}
	}
	
	private void chooseImage(){
		// select image file dialog
		BufferedImage img = (BufferedImage)DAOFactory.getImageDAO().open(this);
		if(img == null) return;
		setImage(img);
	}
	
	public boolean setData(Object data){
		if(!(data instanceof BufferedImage)) return false;
		setImage((BufferedImage)data);
		return true;
	}
	
	public BufferedImage getData(){
		return image;
	}
	
	public void setImage(BufferedImage newImage){
		image = newImage;
		for(ActionListener i : imgListener){
			i.actionPerformed(new ActionEvent(this, 0, ""));
		}
	}
	
	public BufferedImage getImage(){
		return image;
	}

	public void addImageListener(ActionListener listen){
		imgListener.add(listen);
	}
	
	public void removeImageListener(ActionListener listen){
		imgListener.remove(listen);
	}

	@Override
	public void addKeyListener(KeyListener newListener) {
		// Nothing to see here
	}

	@Override
	public void setFont(Font newFont) {
		if(chooseImage != null) chooseImage.setFont(newFont);
		if(removeImage != null) removeImage.setFont(newFont);
	}
	
	@Override
	public JComponent getTabComponent() {
		return chooseImage;
	}
}
