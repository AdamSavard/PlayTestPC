package templatemaker;

import cards.CardTemplate;
import cards.components.CardComponent;
import constants.Constants;

public class CardTemplateModel {
	
	/*
	 * The current mode
	 *  0 - text - click on text field to select, drag cursor to create text field then select it
	 *  1 - line - create lines
	 *  2 - rect - create rectangles
	 *  3 - img - create images
	 *  4 - moveSelect - move and select components
	 */
	private int mode;
	public boolean setMode(int newMode){
		if(newMode < 0 || newMode > Constants.EDITOR_MODE_MAX) return false;
		mode = newMode;
		return true;
	}
	public int getMode(){
		return mode;
	}
	
	private CardTemplate template;
	public CardTemplate setTemplate(CardTemplate newTemplate){
		template = newTemplate;
		return template;
	}
	public CardTemplate getTemplate(){
		return template;
	}
	public boolean templateIsNamed(){
		return !template.getName().trim().equals("");
	}
	
	private boolean saved;
	public boolean setSaved(boolean isSaved){
		saved = isSaved;
		return saved;
	}
	public boolean isSaved(){
		return saved;
	}
	
	// drag and drop rectangle data
	private int startX, startY, endX, endY;
	public int getStartX(){
		return startX;
	}
	public int getStartY(){
		return startY;
	}
	public int getEndX(){
		return endX;
	}
	public int getEndY(){
		return endY;
	}
	public void setStartPoint(int x, int y){
		startX = x;
		startY = y;
	}
	public void setEndPoint(int x, int y){
		endX = x;
		endY = y;
	}
	public void clearStartEndPoint(){
		startX = startY = endX = endY = 0;
	}
	
	public CardComponent selectComponent(int x, int y){
		return template.selectComponent(x, y);
	}
	public String getSelectedComponentName(){
		if(template.getSelectedComponent() == null) return null;
		return template.getSelectedComponent().getName();
	}

	public CardTemplateModel(){
		mode = 0;
		saved = false;
		template = new CardTemplate();
		startX = startY = endX = endY = 0;
	}
	
	public CardTemplateModel(CardTemplate initTemplate){
		mode = 0;
		saved = true;
		template = initTemplate;
		startX = startY = endX = endY = 0;
	}
}
