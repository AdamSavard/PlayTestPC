package cards;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cards.components.CardComponent;
import mainframe.DrawWizard;
import model.util.DrawableImage;

public class CardImage {
	
	private List<DrawableImage> componentImages;
	
	// Component names -> component indexes in image list
	private Map<String, Integer> imgIndexes;
	
	private int width;
	
	private int height;
	
	private int rotation;
	
	public CardImage(CardTemplate template){
		componentImages = new ArrayList<DrawableImage>();
		imgIndexes = new HashMap<String, Integer>();
		width = template.getWidth();
		height = template.getHeight();
		rotation = template.getRotation();
	}
	
	public BufferedImage getImage(boolean rotate){
		return DrawWizard.drawCard(width, height, rotation, componentImages, rotate);
	}
	
	/*
	 * Build the entire list of images from a template and a set of data
	 * This should be called for new cards that need to be drawn and whenever template is changed
	 */
	public void updateImage(CardTemplate template, Map<String, Object> data){
		// Empty existing image list
		componentImages.clear();
		// Update the width and height
		width = template.getWidth();
		height = template.getHeight();
		rotation = template.getRotation();
		// Build an ordered list of components
		List<CardComponent> comps = template.getComponentList();
		// Go through the ordered list of components and create images for each of them
		for(int i = 0; i < comps.size(); i++){
			// Create image for component from data, might be null but it should handle that
			componentImages.add(new DrawableImage(
					comps.get(i).getImage(data.get(comps.get(i).getName())), comps.get(i).getX(), comps.get(i).getY())
					);
			// CardComponent should have abstract getImage that takes in data and builds image on the fly w/ data
			imgIndexes.put(comps.get(i).getName(), componentImages.size() - 1);
		}
	}
	
	/*
	 * Update a single component image. Called whenever a specific component's data changed
	 */
	public void updateImage(CardTemplate template, String compName, Object data){
		if(compName == null || template == null) return;
		// Find the component on the template
		CardComponent comp = null;
		for(CardComponent i : template.getComponentList()){
			if(compName.equals(i.getName())){
				comp = i;
				break;
			}
		}
		if(comp == null) return;
		Integer index = imgIndexes.get(compName);
		if(index == null || index < 0){ // add this component
			// Create image for component from data, might be null but it should handle that
			componentImages.add(new DrawableImage(comp.getImage(data), comp.getX(), comp.getY()));
			// CardComponent should have abstract getImage that takes in data and builds image on the fly w/ data
			imgIndexes.put(comp.getName(), componentImages.size() - 1);
			return;
		}
		// update this component's image
		componentImages.set(index, new DrawableImage(comp.getImage(data), comp.getX(), comp.getY()));
	}

}