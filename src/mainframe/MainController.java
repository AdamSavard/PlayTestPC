package mainframe;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.DAOFactory;

import javax.swing.JOptionPane;

import templatemaker.CardTemplateController;
import cards.Card;
import cards.CardSet;
import cards.CardTemplate;
import cards.CardTemplateListFactory;
import cards.components.CardComponent;
import constants.Constants;
import mainframe.popup.AboutDialog;
import model.serialize.CardFlavor;
import model.serialize.CardSelection;

public class MainController {

	private MainView view;
	private MainModel model;
	
	public MainController() {
		model = new MainModel();
		view = new MainView(this);
		view.setTitle(Constants.MAIN_TITLE);
	}
	
	public void openView() {
		view.setVisible(true);
	}
	
	/* PUBLIC METHODS FOR LISTENERS TO CALL */
	
	// Opening a set
	public void openASet() {
		if((model.setOpenSet(SetWizard.openASet(view))) != null) {
			handleSetOpened();
			return;
		} // No set opened - display no card image
		view.updateCardImage(null);
	}
	
	public void createNewSet() {
		CardSet newSet = SetWizard.createNewSet(view);
		if(newSet == null) return;
		model.setOpenSet(newSet);
		handleSetOpened();
	}
	
	public void selectCard(String list, int index, boolean selectName) {
		if(model.getOpenSet() == null) return;
		if(model.isSetOpening()) return;
		if(model.getOpenSet().getSelectedIndex(list) == index) {
			refreshCardView(list, false, selectName);
			return;
		}
		// Get old template name
		String oldTemplate = "", newTemplate = "";
		if(model.getOpenSet().getSelectedCard(list) != null) {
			oldTemplate = model.getOpenSet().getSelectedCard(list).getTemplateName();
		}
		// return if you fail to select the card
		if(!model.getOpenSet().selectCard(list, index)) return;
		// Get new template name
		newTemplate = model.getOpenSet().getSelectedCard(list).getTemplateName();
		refreshCardView(list, !oldTemplate.equals(newTemplate), selectName);
	}
	
	private void refreshCardView(String list, boolean newTemplate, boolean selectName) {
		// Update preview image panel
		view.updateCardImage(model.getOpenSet().getSelectedImage(list));
		// Update table
		view.setSelectedCard(list, model.getOpenSet().getSelectedIndex(list));
		// Update properties panel
		displayCardData(model.getOpenSet().getSelectedCard(list), 
			CardTemplateListFactory.getCardTemplateList().get(model.getOpenSet().getSelectedCardTemplate(list)),
			newTemplate);
		// set focus on first field of properties panel
		if (selectName) view.moveFocusToName();
	}
	
	public int addCard(String list, boolean selectCard) {
		Card newCard = new Card(CardTemplateListFactory.getCardTemplateList().get(model.getOpenSet().getDefaultTemplate()));
		return addCard(list, newCard, selectCard);
	}
	
	/*
	 * Add a card to the set and to the current list
	 * optionally select that card
	 */
	public int addCard(String list, Card newCard, boolean selectCard) {
		if(model.getOpenSet() == null) return -1;
		// Add card to set
		int newIndex = model.getOpenSet().addCardToListAndSet(list, newCard);
		// not saved now				
		setSaved(false);
		// Add card to visible list
		view.addCardToList(list, 
				newIndex, 
				CardTemplateListFactory.getCardTemplateList().get(newCard.getTemplateName()).getComponentNameList(), 
				model.getOpenSet().getDataFromCard(newCard, CardTemplateListFactory.getCardTemplateList().get(newCard.getTemplateName()).getComponentNameList()));
		// Select correct card on table
		view.selectCardOnList(list, model.getOpenSet().getSelectedIndex(list));
		if(selectCard) {
			selectCard(list, newIndex, true);
		}
		return newIndex;
	}
	
	public void removeCard(String list) {
		if(model.getOpenSet() == null) return;
		if(list == null || "".equals(list)) return;
		view.removeCardFromList(list, model.getOpenSet().getSelectedIndex(list));
		model.getOpenSet().removeSelectedCardFromListAndSet(list);
		// Not saved now
		setSaved(false);
		// Move focus to first component
		view.moveFocusToName();
		// Select correct card on table
		view.selectCardOnList(list, model.getOpenSet().getSelectedIndex(list));
		// Update preview image panel
		view.updateCardImage(model.getOpenSet().getSelectedImage(list));
		// Update properties panel
		displayCardData(model.getOpenSet().getSelectedCard(list), 
				CardTemplateListFactory.getCardTemplateList().get(model.getOpenSet().getSelectedCardTemplate(list)), 
				!model.getOpenSet().getSelectedCard(list).getTemplateName().equals(model.getOpenSet().getSelectedCard(list).getTemplateName()));
	}
	
	public void updateSelectedCard(String list, String compName, Object data) {
		// TODO int oldIndex = model.getOpenSet().getSelectedIndex(list), newIndex = 0;
		// Update component's data for currently selected card
		int moveResult = model.getOpenSet().setSelectedCardData(list, compName, data);
		if(moveResult == 0) return; // Do nothing else if text is unchanged
		// If card moved, need to update the table, change selected index
		if(moveResult == 2) {
			// newIndex = model.getOpenSet().getSelectedIndex(list);
			// TODO Update table to reflect change
			//view.moveTableRow(list, oldIndex, newIndex);
			//view.selectCardOnList(list, newIndex);
		}
		// Update specified table, given Column name, selected row, with new data
		view.updateSelectedCardInList(list, compName, data.toString());
		// Update the card image
		view.updateCardImage(model.getOpenSet().getSelectedImage(list));
		// Set is no longer saved
		setSaved(false);
	}
	
	public void updateComments(String list, String comments) {
		if(model.getOpenSet().getSelectedCard(list).getComments().equals(comments)) return;
		model.getOpenSet().getSelectedCard(list).setComments(comments);
		setSaved(false);
	}
	
	public void exportHtmlDeck() {
		PrintWizard.exportHtmlDeck(view, model.getOpenSet());
	}
	
	public void exportExcelDeck() {
		PrintWizard.exportExcelDeck(view, model.getOpenSet());
	}
	
	public boolean save() {
		if(model.getOpenSet() == null) return false;
		if(!model.getOpenSet().legallyNamed()) return saveAs();
		DAOFactory.getCardSetDAO().write(model.getOpenSet().getFileName(), model.getOpenSet(), true);
		setSaved(true);
		return true;
	}
	
	public boolean saveAs() {
		if(model.getOpenSet() == null) return false;
		if(!SetWizard.nameSet(view, model.getOpenSet())) {
			JOptionPane.showMessageDialog(view, "That is not a set name.");
			return false;
		}
		DAOFactory.getCardSetDAO().write(model.getOpenSet().getFileName(), model.getOpenSet(), true);
		setSaved(true);
		return true;
	}
	
	public void exit() {
		if(model.getOpenSet() == null || model.isSaved()) { // Close because we can
			System.exit(0);
			return;
		}
		// Make sure user wants to leave
		Object[] options = {"Save", "Don't Save", "Cancel"};
		int choice = JOptionPane.showOptionDialog(view, "Do you want to save changes to " + 
                (model.getOpenSet().getFileName().length() == 0 ? "Untitled" : model.getOpenSet().getFileName()) + "?", "Warning",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
		if(choice == JOptionPane.YES_OPTION) { // Save then exit if saved
			if(save()) System.exit(0);
			return;
		}
		if(choice == JOptionPane.NO_OPTION) {
			System.exit(0);
			return;
		}
		// Cancelled, not exiting
	}
	
	public void pasteCard(String list) {
		if(list == null || "".equals(list)) return;
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		try {
			CardFlavor flavor = new CardFlavor();
			if (clipboard.isDataFlavorAvailable(flavor)) {
				// Add the card to the set
				Card newCard = (Card) clipboard.getData(flavor);
				addCard(list, newCard, true);
			}
		}catch (UnsupportedFlavorException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public void copyCard(String list) {
		if(list == null || "".equals(list)) return;
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
	    Card toCopy = model.getOpenSet().getSelectedCard(list);
	    CardSelection selection = new CardSelection(toCopy);
	    clipboard.setContents(selection, null);
	}
	
	public void cutCard(String list) {
		copyCard(list);
		removeCard(list);
	}
	
	public void openBulkEdit() {
		// TODO bulk edit
	}
	
	public void showAbout() {
		if(model.getAboutDialog() == null) {
			model.setAboutDialog(new AboutDialog(view, DAOFactory.getImageDAO().readImageIcon("AboutScreen.png")));
		}
		model.getAboutDialog().setVisible(true);
	}
	
	public void sortColumn(String list, String colName) {
		model.getOpenSet().sortList(list, colName);
		view.removeAllFromList(list);
		// Rebuild list
		List<Card> cards = model.getOpenSet().getCards(MainView.CARD_EDITOR_LIST);
		for(int i = 0; i < cards.size(); i++) {
			view.addCardToList(MainView.CARD_EDITOR_LIST, 
					i, 
					CardTemplateListFactory.getCardTemplateList().get(cards.get(i).getTemplateName()).getComponentNameList(), 
					model.getOpenSet().getDataFromCard(cards.get(i), CardTemplateListFactory.getCardTemplateList().get(cards.get(i).getTemplateName()).getComponentNameList()));
		}
	}
	
	public void templateImageClicked(int index) {
		if(index == CardTemplateListFactory.getCardTemplateList().getIndexOf(model.getOpenSet().getSelectedCard(MainView.CARD_EDITOR_LIST).getTemplateName())) {
			editTemplate(index);
			return;
		}
		switchTemplate(index);
	}
	
	private void switchTemplate(int index) {
		// Switch card to a different template
		if(!model.getOpenSet().setSelectedCardTemplate(MainView.CARD_EDITOR_LIST, 
				CardTemplateListFactory.getCardTemplateList().get(index).getName())){
			return; // same template
		}
		// Update card image
		view.updateCardTemplateImage(model.getOpenSet().getSelectedImage(MainView.CARD_EDITOR_LIST));
		view.updateCardTemplateListSelection(index);
		model.setResetCards(1);
		// File no longer saved
		setSaved(false);
	}
	
	public void newTemplateClicked() {
		// Initialize template controller and view
		CardTemplateController control = new CardTemplateController(view);
		control.openView();
		// Once template view has closed
		if(!control.getSaved()) return; // No new set :(
		// store new template in list and switch the current card's template to that
		switchTemplate(CardTemplateListFactory.getCardTemplateList().put(control.getTemplate()));
	}
	
	private void editTemplate(int index) {
		// Get the template chosen
		CardTemplate original = CardTemplateListFactory.getCardTemplateList().get(index);
		// open template editor dialog to edit and save template
		CardTemplate edited = SetWizard.editTemplate(view, original.clone());
		// If template was not saved, we're done here
		if(edited == null) return;
		// update the model
		model.getOpenSet().setSelectedCardTemplate(MainView.CARD_EDITOR_LIST, edited.getName());
		if(original.getName().equals(edited.getName())) {
			// Template has been edited, update the template image
			view.updateCardTemplateListSingleImage(edited.getImage(edited.getWidth(), edited.getHeight()),
					CardTemplateListFactory.getCardTemplateList().update(edited));
		} else {
			// New template was created, refresh template image list
			view.updateCardTemplateListImages(CardTemplateListFactory.getCardTemplateList().getTemplateImages(), 
					CardTemplateListFactory.getCardTemplateList().getTemplateNames(),
					CardTemplateListFactory.getCardTemplateList().put(edited));
		}
		// Update card image
		view.updateCardTemplateImage(model.getOpenSet().getSelectedImage(MainView.CARD_EDITOR_LIST));
		// We will need to update the cards view
		model.setResetCards(1);
	}
	
	public void searchTextChanged(String list, String searchText) {
		// TODO search
	}
	
	public void switchTab(int tabIndex) {
		if(model.getOpenSet() == null) return;
		switch(tabIndex) {
			case MainViewTabs.CARDS_TAB:
				if(model.getResetCards() == 0) return;
				// update view since something has changed
				model.setResetCards(0);
				refreshList(MainView.CARD_EDITOR_LIST, model.getOpenSet().getSelectedIndex(MainView.CARD_EDITOR_LIST));
				refreshCardView(MainView.CARD_EDITOR_LIST, true, true);
				return;
			case MainViewTabs.TEMPLATES_TAB:
				// update display of current card
				view.updateCardTemplateImage(model.getOpenSet().getSelectedImage(MainView.CARD_EDITOR_LIST));
				// Load template list
				CardTemplateListFactory.getCardTemplateList().load(DAOFactory.getCardTemplateDAO().list());
				// Display template images and Update which template is highlighted
				view.updateCardTemplateListImages(CardTemplateListFactory.getCardTemplateList().getTemplateImages(), 
						CardTemplateListFactory.getCardTemplateList().getTemplateNames(),
						CardTemplateListFactory.getCardTemplateList().getIndexOf(model.getOpenSet().getSelectedCard(MainView.CARD_EDITOR_LIST).getTemplateName()));
				return;
			case MainViewTabs.SPOILER_TAB:
				// If the set has changed, reload the card images
				if(model.isResetSpoiler()) {
					model.setResetSpoiler(false);
					view.loadSpoilerImages(model.getOpenSet().getCardImages(MainView.CARD_EDITOR_LIST));
				}
				return;
			case MainViewTabs.SUBSETS_TAB:
				// reset
				return;
		}
	}
	
	private void handleSetOpened() {
		// Turn set editing on - enables certain components
		view.toggleSetEditing(true);
		// Switch the view to the CARDS TAB
		view.setTab(MainViewTabs.CARDS_TAB);
		// Prevent selecting cards
		model.setOpeningSetFlag(true);
		// Add first card to the set
		if(model.getOpenSet().getCardCount() == 0) addCard(MainView.CARD_EDITOR_LIST, false);
		// Refresh the contents of the card editor list
		refreshList(MainView.CARD_EDITOR_LIST, 0);
		// Enable selecting cards
		model.setOpeningSetFlag(false);
		// Select the first card in the card editor list
		selectCard(MainView.CARD_EDITOR_LIST, 0, false);
		// Move focus to first component in text-based view - TODO this needs to happen later somehow
		view.moveFocusToName();
		// File no longer saved
		setSaved(!"".equals(model.getOpenSet().getFileName()));
	}
	
	private void refreshList(String list, int index) {
		view.removeAllFromList(list);
		List<Card> cards = model.getOpenSet().getCards(list);
		for(int i = 0; i < cards.size(); i++) {
			view.addCardToList(list, i, CardTemplateListFactory.getCardTemplateList().get(cards.get(i).getTemplateName()).getComponentNameList(), 
					model.getOpenSet().getDataFromCard(cards.get(i), CardTemplateListFactory.getCardTemplateList().get(cards.get(i).getTemplateName()).getComponentNameList()));
		}
		// Select the specified card in the card editor list
		selectCard(list, index, false);
	}
	
	private void setSaved(boolean isSaved) {
		model.setSaved(isSaved);
		if(model.getOpenSet() == null) {
			view.setTitle(Constants.MAIN_TITLE);
			return;
		}
		if(isSaved) {
			view.setTitle(Constants.MAIN_TITLE + " - " + model.getOpenSet().getFileName());
			return;
		}
		// Is not saved anymore
		model.setResetSpoiler(true);
		if("".equals(model.getOpenSet().getFileName())) {
			view.setTitle(Constants.MAIN_TITLE + " - [Untitled]");
			return;
		}
		view.setTitle(Constants.MAIN_TITLE + " - " + model.getOpenSet().getFileName() + " [Unsaved]");
	}
	
	public void displayCardData(Card card, CardTemplate cT, boolean newTemplate) {
		// Disable components if there is no card
		if(card == null) {
			view.disableComponents();
			return;
		}
		// get component list from template - ordered, remove components that are not editable
		List<CardComponent> comps = new ArrayList<CardComponent>();
		for(CardComponent cC : cT.getComponentList()) {
			if(cC == null) continue;
			if(!cC.isEditable()) continue;
			comps.add(cC);
		}
		// Build labels and texts if needed
		if(newTemplate || !view.componentsInitialized()) view.buildLabelsAndTexts(cT.getName(), comps);
		// Update the data of each component
		for(int i = 0; i < comps.size(); i++) {
			view.updateCardComponent(comps.get(i).getTypeString(), i, card.getData(comps.get(i).getName()));
		}
		view.updateCardComments(card.getComments());
	}
}