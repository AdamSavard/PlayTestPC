package cards;

import io.DAOFactory;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardTemplateList {
	
	// Templates
	private List<CardTemplate> templates;
	
	// Template name -> Index in template list
	private Map<String, Integer> templateIndexes;
	
	protected CardTemplateList() {
		templates = new ArrayList<CardTemplate>();
		templateIndexes = new HashMap<String,Integer>();
	}
	
	public CardTemplate get(String templateName) {
		Integer index = templateIndexes.get(templateName);
		if(index == null) {
			put(templateName);
			index = templateIndexes.get(templateName);
		}
		return templates.get(index);
	}
	
	public CardTemplate get(int index) {
		return templates.get(index);
	}
	
	/*
	 * Add the template, return the index
	 */
	public int put(String templateName) {
		Integer index = templateIndexes.get(templateName);
		if(index != null) return index;
		return put(DAOFactory.getCardTemplateDAO().read(templateName));
	}
	
	public int put(CardTemplate ct) {
		if(ct == null) return -1;
		// template is already here
		if(templateIndexes.get(ct.getName()) != null) {
			return templateIndexes.get(ct.getName());
		}
		// Place new template in correct index
		int index = determineIndex(ct);
		templates.add(index, ct);
		// Update name to index map
		for(int i = index; i < templates.size(); i++) {
			templateIndexes.put(templates.get(i).getName(), i);
		}
		return index;
	}
	
	public int update(CardTemplate ct) {
		if(ct == null) return -1;
		Integer index = templateIndexes.get(ct.getName());
		// template is not here
		if(index == null) {
			return -1;
		}
		templates.set(index, ct);
		return index;
	}
	
	public void load(List<String> templateNames) {
		for(String i : templateNames) {
			if(get(i) != null) continue;
			put(i);
		}
	}
	
	private int determineIndex(CardTemplate newTemplate) {
		int min = 0, max = templates.size(), med = 0;
		int compare = 0;
		while(max > min) {
			med = min + ((max - min) / 2);
			compare = templates.get(med).compareTo(newTemplate);
			if(compare >= 0) {
				min = med + 1; // goes after this element
			} else {
				max = med - 1; // goes before this element
			}
		}
		return min;
	}
	
	public int getIndexOf(String name) {
		return templateIndexes.get(name);
	}
	
	public List<BufferedImage> getTemplateImages() {
		List<BufferedImage> templateImages = new ArrayList<BufferedImage>();
		for(CardTemplate i : templates) {
			i.updateImage();
			templateImages.add(i.getImage(i.getWidth(), i.getHeight()));
		}
		return templateImages;
	}
	
	public List<String> getTemplateNames() {
		List<String> templateNames = new ArrayList<String>();
		for(CardTemplate i : templates) {
			i.updateImage();
			templateNames.add(i.getName());
		}
		return templateNames;
	}
}
