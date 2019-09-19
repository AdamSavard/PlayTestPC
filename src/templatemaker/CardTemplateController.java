package templatemaker;

import io.DAOFactory;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.serialize.CardComponentFlavor;
import model.serialize.CardComponentSelection;
import cards.CardTemplate;
import cards.components.CardComponent;
import cards.components.CardComponentProperties;
import cards.components.ImageComponent;
import cards.components.LineComponent;
import cards.components.RectangleComponent;
import cards.components.TextFieldComponent;
import constants.Constants;

public class CardTemplateController {
	
	protected CardTemplateView view;
	
	protected CardTemplateModel model;
	
	public CardTemplateController(JFrame parent){
		model = new CardTemplateModel();
		initView(parent);
	}
	
	public CardTemplateController(JFrame parent, CardTemplate initTemplate){
		model = new CardTemplateModel(initTemplate);
		initView(parent);
		view.setTemplateName(initTemplate.getName());
		view.setTemplateRotation(initTemplate.getRotation());
	}
	
	private void initView(JFrame parent){
		view = new CardTemplateView(this, parent);
		view.selectTemplateName();
		view.setTitle(Constants.TEMPLATE_MAKER_TITLE);
	}
	
	public void openView(){
		view.setVisible(true);
	}
	
	public boolean getSaved(){
		return model.isSaved();
	}
	
	public CardTemplate getTemplate(){
		return model.getTemplate();
	}
	
	public void onViewOpened(){
		view.updateTemplateImage(model.getTemplate().getImage(view.getDispPanelWidth(), view.getDispPanelHeight()));
	}
	
	public void deleteSelectedComponent() {
		model.getTemplate().removeSelectedComponent();
		if(model.getTemplate().getSelectedComponent() != null){
			updateComponentProperties(model.getTemplate().getSelectedComponent());
		} else {
			view.emptyComponentProperties();
		}
		view.updateTemplateImage(model.getTemplate().getImage(view.getDispPanelWidth(), view.getDispPanelHeight()));
	}
	
	public void copySelectedComponent() {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		CardComponent toCopy = model.getTemplate().getSelectedComponent();
		if(toCopy == null) return;
	    CardComponentSelection selection = new CardComponentSelection(toCopy);
		clipboard.setContents(selection, null);
	}
	
	public void cutSelectedComponent() {
		copySelectedComponent();
		deleteSelectedComponent();
	}
	
	public void pasteComponent() {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		try {
			CardComponentFlavor flavor = new CardComponentFlavor();
			if (clipboard.isDataFlavorAvailable(flavor)) {
				// Add the card to the set
				CardComponent newComp = (CardComponent) clipboard.getData(flavor);
				updateComponentProperties(model.getTemplate().addComponent(newComp));
			}
		}catch (UnsupportedFlavorException | IOException e) {
			e.printStackTrace();
		}
	}

	public void drawMouseClicked(int x, int y, int width, int height) {
		selectComponent(x, y, width, height);
	}

	/*
	 * Handle mouse being pressed. pX and pY are the x,y coordinates of the mouse on the draw panel
	 */
	public void drawMousePressed(int pX, int pY, int width, int height){
		int x = pX - ((view.getDispPanelWidth() - model.getTemplate().getWidth()) / 2);
		int y = pY - ((view.getDispPanelHeight() - model.getTemplate().getHeight()) / 2);
		model.setStartPoint(x, y);
		if(model.getMode() == Constants.EDITOR_MODE_MOVE){
			updateComponentProperties(model.selectComponent(x, y));
			model.setEndPoint(x, y);
			return;
		}
		view.startMouseDrag(pX, pY);
		model.setEndPoint(Integer.MIN_VALUE, Integer.MIN_VALUE);
	}

	/*
	 * Handle mouse being released. pX and pY are the x,y coordinates of the mouse on the draw panel
	 */
	public void drawMouseReleased(int pX, int pY, int width, int height) {
		if(model.getEndX() == Integer.MIN_VALUE || model.getEndY() == Integer.MIN_VALUE){
			// click happened, not drag, only select, not create
			int x = pX - ((view.getDispPanelWidth() - model.getTemplate().getWidth()) / 2);
			int y = pY - ((view.getDispPanelHeight() - model.getTemplate().getHeight()) / 2);
			updateComponentProperties(model.selectComponent(x, y));
			model.setEndPoint(x, y);
			return;
		}
		
		int startX = model.getStartX();
		int endX = model.getEndX();
		int startY = model.getStartY();
		int endY = model.getEndY();
		int iX = Math.min(startX, endX);
		int iY = Math.min(startY, endY);
		int iWidth = Math.abs(endX - startX);
		int iHeight = Math.abs(endY - startY);
		
		model.clearStartEndPoint();
		view.stopMouseDrag(pX, pY);
		
		// Make new component
		switch(model.getMode()){
			case Constants.EDITOR_MODE_TEXT:
				updateComponentProperties(model.getTemplate().addComponent(new TextFieldComponent(""), iX, iY, iWidth, iHeight));
				break;
			case Constants.EDITOR_MODE_LINE:
				updateComponentProperties(model.getTemplate().addComponent(new LineComponent("", startX, startY, 0, endX, endY, Color.black)));
				break;
			case Constants.EDITOR_MODE_RECT:
				updateComponentProperties(model.getTemplate().addComponent(new RectangleComponent(""), iX, iY, iWidth, iHeight));
				break;
			case Constants.EDITOR_MODE_IMG:
				updateComponentProperties(model.getTemplate().addComponent(new ImageComponent(""), iX, iY, iWidth, iHeight));
				break;
			default:
				return; // nothing to make here
		}
		// Handle switching to new component
		view.updateTemplateImage(model.getTemplate().getImage(view.getDispPanelWidth(), view.getDispPanelHeight()));
		setMode(Constants.EDITOR_MODE_MOVE);
		view.selectField(CardComponentProperties.Name);
	}

	/*
	 * Handle mouse being dragged. pX and pY are the x,y coordinates of the mouse on the draw panel
	 */
	public void drawMouseDragged(int pX, int pY, int width, int height) {
		// x,y are position on card where the cursor is
		int x = pX - ((view.getDispPanelWidth() - model.getTemplate().getWidth()) / 2);
		int y = pY - ((view.getDispPanelHeight() - model.getTemplate().getHeight()) / 2);
		if(model.getMode() == Constants.EDITOR_MODE_MOVE){
			if(model.getTemplate().getSelectedComponent() == null) return;
			// The new x and y coordinates of the component
			int newX = model.getTemplate().getSelectedComponent().getX() + x - model.getEndX();
			int newY = model.getTemplate().getSelectedComponent().getY() + y - model.getEndY();
			model.getTemplate().getSelectedComponent().setX(newX);
			model.getTemplate().getSelectedComponent().setY(newY);
			view.updateTemplateImage(model.getTemplate().getImage(view.getDispPanelWidth(), view.getDispPanelHeight()));
			model.setEndPoint(x, y);
			if(model.getTemplate().getSelectedComponent() instanceof LineComponent){
				LineComponent tmp = (LineComponent)model.getTemplate().getSelectedComponent();
				view.setField(Constants.INTEGER_DATA_TYPE, "StartX", tmp.getStartX());
				view.setField(Constants.INTEGER_DATA_TYPE, "StartY", tmp.getStartY());
				view.setField(Constants.INTEGER_DATA_TYPE, "EndX", tmp.getEndX());
				view.setField(Constants.INTEGER_DATA_TYPE, "EndY", tmp.getEndY());
			} else {
				view.setField(Constants.INTEGER_DATA_TYPE, "X", newX);
				view.setField(Constants.INTEGER_DATA_TYPE, "Y", newY);
			}
			return;
		}
		model.setEndPoint(x, y);
		view.continueMouseDrag(pX, pY);
	}
	
	/*
	 * Select component by taking in display area width and height, and x and y coordinate of mouse click
	 * in display area. The card is centered in that area. Need to calculate card x,y coordinate then find
	 * The highest z component on that coordinate
	 */
	public void selectComponent(int dx, int dy, int dWidth, int dHeight){
		int mWidth = model.getTemplate().getWidth();
		int mHeight = model.getTemplate().getHeight();
		int x = dx - (int)((dWidth - mWidth) / 2.0);
		int y = dy - (int)((dHeight - mHeight) / 2.0);
		model.selectComponent(x, y);
		setMode(Constants.EDITOR_MODE_MOVE);
		updateComponentProperties(model.getTemplate().getSelectedComponent());
		if(model.getTemplate().getSelectedComponent() != null){
			view.selectField(CardComponentProperties.Name);
		}
		view.updateTemplateImage(model.getTemplate().getImage(view.getDispPanelWidth(), view.getDispPanelHeight()));
	}
	
	private void updateComponentProperties(CardComponent comp){
		if(comp == null){
			view.emptyComponentProperties();
			return;
		}
		if(comp instanceof TextFieldComponent){
			TextFieldComponent txt = (TextFieldComponent)comp;
			view.switchToText();
			view.setField(Constants.STRING_DATA_TYPE, CardComponentProperties.Name, txt.getName());
			view.setField(Constants.INTEGER_DATA_TYPE, CardComponentProperties.X, txt.getX());
			view.setField(Constants.INTEGER_DATA_TYPE, CardComponentProperties.Y, txt.getY());
			view.setField(Constants.INTEGER_DATA_TYPE, CardComponentProperties.Width, txt.getWidth());
			view.setField(Constants.INTEGER_DATA_TYPE, CardComponentProperties.Height, txt.getHeight());
			view.setField(Constants.STRING_DATA_TYPE, CardComponentProperties.FontFamily, txt.getFontFamily());
			for(int i : Constants.FONT_STYLE_LIST){
				if(i == txt.getFontStyle()){
					view.setField(Constants.COMBO_DATA_TYPE, CardComponentProperties.FontStyle, i);
					break;
				}
			}
			view.setField(Constants.INTEGER_DATA_TYPE, CardComponentProperties.FontSize, txt.getFontSize());
			view.setField(Constants.COMBO_DATA_TYPE, CardComponentProperties.Horizon, txt.getHorizontalAlign());
			view.setField(Constants.COMBO_DATA_TYPE, CardComponentProperties.Vertical, txt.getVerticalAlign());
			view.setField(Constants.BOOL_DATA_TYPE, CardComponentProperties.IsName, txt.isCardName());
			view.setField(Constants.BOOL_DATA_TYPE, CardComponentProperties.Editable, txt.isEditable());
			return;
		}
		if(comp instanceof LineComponent){
			LineComponent txt = (LineComponent)comp;
			view.switchToLine();
			view.setField(Constants.STRING_DATA_TYPE, CardComponentProperties.Name, txt.getName());
			view.setField(Constants.INTEGER_DATA_TYPE, CardComponentProperties.StartX, txt.getStartX());
			view.setField(Constants.INTEGER_DATA_TYPE, CardComponentProperties.StartY, txt.getStartY());
			view.setField(Constants.INTEGER_DATA_TYPE, CardComponentProperties.EndX, txt.getEndX());
			view.setField(Constants.INTEGER_DATA_TYPE, CardComponentProperties.EndY, txt.getEndY());
			view.setField(Constants.COLOR_DATA_TYPE, CardComponentProperties.Color, txt.getColor());
			view.setField(Constants.BOOL_DATA_TYPE, CardComponentProperties.Editable, txt.isEditable());
			return;
		}
		if(comp instanceof RectangleComponent){
			RectangleComponent txt = (RectangleComponent)comp;
			view.switchToRect();
			view.setField(Constants.STRING_DATA_TYPE, CardComponentProperties.Name, txt.getName());
			view.setField(Constants.INTEGER_DATA_TYPE, CardComponentProperties.X, txt.getX());
			view.setField(Constants.INTEGER_DATA_TYPE, CardComponentProperties.Y, txt.getY());
			view.setField(Constants.INTEGER_DATA_TYPE, CardComponentProperties.Width, txt.getWidth());
			view.setField(Constants.INTEGER_DATA_TYPE, CardComponentProperties.Height, txt.getHeight());
			view.setField(Constants.COLOR_DATA_TYPE, CardComponentProperties.FillColor, txt.getFillColor());
			view.setField(Constants.COLOR_DATA_TYPE, CardComponentProperties.OutlineColor, txt.getOutlineColor());
			view.setField(Constants.BOOL_DATA_TYPE, CardComponentProperties.Editable, txt.isEditable());
			return;
		}
		if(comp instanceof ImageComponent){
			ImageComponent txt = (ImageComponent)comp;
			view.switchToImage();
			view.setField(Constants.STRING_DATA_TYPE, CardComponentProperties.Name, txt.getName());
			view.setField(Constants.INTEGER_DATA_TYPE, CardComponentProperties.X, txt.getX());
			view.setField(Constants.INTEGER_DATA_TYPE, CardComponentProperties.Y, txt.getY());
			view.setField(Constants.INTEGER_DATA_TYPE, CardComponentProperties.Width, txt.getWidth());
			view.setField(Constants.INTEGER_DATA_TYPE, CardComponentProperties.Height, txt.getHeight());
			view.setField(Constants.BOOL_DATA_TYPE, CardComponentProperties.Editable, txt.isEditable());
			return;
		}
	}
	
	/*
	 * Button in toolbar is clicked to set the mode
	 */
	public void setMode(int newMode){
		model.setMode(newMode);
		view.disableButton(newMode);
	}
	
	public void cancelTemplate(){
		if(model.getTemplate().getComponentCount() > 0){
			// Make sure user wants to quit
			int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you want to exit?","Warning", JOptionPane.YES_NO_OPTION);
			if(dialogResult != JOptionPane.YES_OPTION){
				return;
			}
		}
		view.dispose();
	}
	
	public void finishTemplate(){
		if(model.getTemplate().getName().trim().equals("")){
			view.selectTemplateName();
			view.displayMessage("Template does not have a name");
			return;
		}
		if(model.getTemplate().getComponentCount() == 0){
			view.displayMessage("Template does not have any components");
			return;
		}
		if(!model.getTemplate().hasNameComponent()){
			view.displayMessage("Template does not have an editable name component");
			return;
		}
		/*if(DAOFactory.getCardTemplateDAO().list().contains(model.getTemplate().getName().trim())){
			view.selectTemplateName();
			view.displayMessage("You already have a template with this name");
			return;
		}*/
		// Determine if a component needs to be renamed
		int compToRename = model.getTemplate().validateComponents();
		if(compToRename != 0){
			CardComponent brokenComponent = model.getTemplate().getComponentById(compToRename);
			view.displayMessage("Editable component needs a valid name at (" + 
								brokenComponent.getX() + "," + brokenComponent.getY() + ")");
			return;
		}
		if(DAOFactory.getCardTemplateDAO().write(model.getTemplate().getName(), model.getTemplate(), false)){
			model.setSaved(true);
			view.dispose();
			return;
		}
		// Does player want to overwrite the file!?
		int result = JOptionPane.showConfirmDialog(view,"The file exists, overwrite?","Existing file",JOptionPane.YES_NO_CANCEL_OPTION);
        if(result == JOptionPane.YES_OPTION){
        	DAOFactory.getCardTemplateDAO().write(model.getTemplate().getName(), model.getTemplate(), true);
			model.setSaved(true);
			view.dispose();
			return;
        }
        return;
	}
	
	/* Update component properties */
	
	public void updateComponentProperty(String propertyName, Object value){
		if(model.getTemplate().getSelectedComponent() == null) return;
		if(CardComponentProperties.IsName.equals(propertyName)){
			updateIsName((Boolean)value);
			return;
		}
		model.getTemplate().getSelectedComponent().set(propertyName, value);
		if(model.getTemplate().getSelectedComponent().getTypeString().equals(Constants.TEXT_TYPE)) updateLines();
		view.updateTemplateImage(model.getTemplate().getImage(view.getDispPanelWidth(), view.getDispPanelHeight()));
	}
	
	public void updateIsName(Boolean isName){
		if(isName){
			model.getTemplate().makeSelectedComponentBeTheName();
			return;
		}
		model.getTemplate().unsetTheName();
	}
	
	/*
	 * Whenever height or font size changes, change the number of lines
	 * This should be called before updating template image
	 */
	private void updateLines(){
		if(model.getTemplate().getSelectedComponent().getLines() == 0) return;
		TextFieldComponent selectedTxt = (TextFieldComponent)model.getTemplate().getSelectedComponent();
		int newLines = selectedTxt.getHeight() / selectedTxt.getFontSize();
		if(newLines < 1){
			// Set height equal to font size so that there is enough height for exactly 1 line
			selectedTxt.setHeight(selectedTxt.getFontSize());
			view.setField(Constants.INTEGER_DATA_TYPE, "Height", selectedTxt.getFontSize());
			newLines = 1;
		}
		selectedTxt.setLines(newLines);
	}
	
	public void updateRotation(int newRotation){
		model.getTemplate().setRotation(newRotation);
	}
	
	/* Update template properties */
	
	public void updateTemplateName(String newName){
		model.getTemplate().setName(newName);
	}
	
	public void updateTemplateWidth(int newWidth){
		model.getTemplate().setWidth(newWidth);
		view.updateTemplateImage(model.getTemplate().getImage(view.getDispPanelWidth(), view.getDispPanelHeight()));
	}
	
	public void updateTemplateHeight(int newHeight){
		model.getTemplate().setHeight(newHeight);
		view.updateTemplateImage(model.getTemplate().getImage(view.getDispPanelWidth(), view.getDispPanelHeight()));
	}

}
