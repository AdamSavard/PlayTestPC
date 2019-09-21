package cards;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.util.SortMethod;

public class CardSet {
	
	private String fileName;
	
	// ID number -> Card
	private Map<Integer,Card> cardpool;
	
	// ID number -> Card image
	private transient Map<Integer,CardImage> images;

	// Default template name
	private String defaultTemplate;
	
	// List name -> Card List
	private Map<String,CardList> cardlists;
	
	/**
	 * List of column names to hide in the main card view
	 */
	private Set<String> hiddenColumns;
	
	private int maxID;
	
	public CardSet(){
		init();
	}
	
	public CardSet(String defaultTemplateName){
		init();
		defaultTemplate = defaultTemplateName;
		CardTemplateListFactory.getCardTemplateList().put(defaultTemplateName);
	}
	
	private void init(){
		fileName = "";
		cardpool = new HashMap<Integer,Card>();
		cardlists = new HashMap<String,CardList>();
		images = new HashMap<Integer,CardImage>();
		maxID = 0;
		hiddenColumns = new HashSet<String>();
	}
	
	public String getDefaultTemplate(){
		return defaultTemplate;
	}

	public boolean setFileName(String newFileName){
		fileName = newFileName;
		return true;
	}
	
	public String getFileName(){
		return fileName;
	}
	
	public boolean legallyNamed(){
		if(fileName == null | fileName.length() == 0) return false;
		if(fileName.contains(".") | fileName.contains("$")) return false;
		return true;
	}
	
	public Card getSelectedCard(String list){
		return cardpool.get(cardlists.get(list).getSelected());
	}
	
	public String getSelectedCardTemplate(String list){
		return cardpool.get(cardlists.get(list).getSelected()).getTemplateName();
	}
	
	public boolean setSelectedCardTemplate(String list, String template){
		int id = cardlists.get(list).getSelected();
		if(cardpool.get(id).getTemplateName().equals(template)){
			return false; // the template isn't changing!
		}
		// Update template name
		cardpool.get(id).setTemplateName(template);
		// Update image
		images.get(id).updateImage(CardTemplateListFactory.getCardTemplateList().get(template), cardpool.get(id).getData());
		return true;
	}
	
	public BufferedImage getSelectedImage(String list){
		return images.get(cardlists.get(list).getSelected()).getImage(false);
	}
	
	public int getSelectedIndex(String list){
		if(cardlists.get(list) == null){
			cardlists.put(list, new CardList());
		}
		return cardlists.get(list).getSelectedIndex();
	}
	
	public boolean selectCard(String list, int index){
		return cardlists.get(list).setSelectedIndex(index);
	}
	
	public Card getCardByID(int id){
		return cardpool.get(id);
	}
	
	public int getCardCount() {
		return cardpool.size();
	}
	
	public int addCardToListAndSet(String list, Card newCard) {
		// Add this list if it is a new list
		if(cardlists.get(list) == null){
			cardlists.put(list, new CardList());
		}
		// Assign the card an ID number
		int newID = ++maxID;
		// Fetch template if it is not loaded
		if(CardTemplateListFactory.getCardTemplateList().get(newCard.getTemplateName()) == null){
			CardTemplateListFactory.getCardTemplateList().put(newCard.getTemplateName());
		}
		// Add to cardpool
		cardpool.put(newID, newCard);
		// Add to list
		int index = 0;
		if(cardlists.get(list).size() > 0){
			if(cardlists.get(list).getSort().compare(newCard, cardpool.get(cardlists.get(list).getAtIndex(0))) > 0){
				index = cardlists.get(list).size();
			}
		}
		cardlists.get(list).insertCard(newID, index);
		// Add card image
		images.put(newID, new CardImage(CardTemplateListFactory.getCardTemplateList().get(newCard.getTemplateName())));
		return index;
	}
	
	private int determineIndex(String list, Card newCard){
		int min = 0, max = cardlists.get(list).size(), med = 0;
		int compare = 0;
		while(max > min){
			med = min + ((max - min) / 2);
			compare = cardlists.get(list).getSort().compare(newCard, cardpool.get(cardlists.get(list).getAtIndex(med)));
			if(compare >= 0){
				min = med + 1; // goes after this element
			} else {
				max = med - 1; // goes before this element
			}
		}
		return min;
	}
	
	public Card addNewCardToSet(Card toAdd) {
		int newID = ++maxID;
		cardpool.put(newID, toAdd);
		return toAdd;
	}
	
	public void removeSelectedCardFromListAndSet(String list){
		cardpool.remove(cardlists.get(list).removeSelected());
	}
	
	public void removeSelectedCardFromListOnly(String list){
		cardlists.get(list).removeSelected();
	}
	
	/*
	 * Set the data for a specified component on the selected card on a specified list
	 * Return 0 is data is not set, 1 if it was set, 2 if it was set and if the card was moved
	 */
	public int setSelectedCardData(String list, String compName, Object data){
		int id = cardlists.get(list).getSelected();
		int newIndex;
		if(id < 0) return 0;
		if(!cardpool.get(id).setData(compName, data)) return 0; // set the data, if unchanged we're done
		// Update image
		images.get(id).updateImage(CardTemplateListFactory.getCardTemplateList().get(cardpool.get(id).getTemplateName()), compName, data);
		// Prepare to move the card
		newIndex = determineIndex(list, cardpool.get(cardlists.get(list).getSelected()));
		if(newIndex > cardlists.get(list).getSelectedIndex()) newIndex--;
		if(cardlists.get(list).getSelectedIndex() == newIndex) return 1; // Not moving the card
		// Move the card
		// TODO cardlists.get(list).moveCard(cardlists.get(list).getSelectedIndex(), newIndex);
		return 2;
	}

	public List<Integer> getIds(){
		List<Integer> output = new ArrayList<Integer>();
		for(Integer i : cardpool.keySet()){
			output.add(i);
		}
		return output;
	}
	
	public List<Integer> getIds(String list){
		return cardlists.get(list).getCards();
	}

	public List<Card> getCards(List<Integer> ids){
		List<Card> output = new ArrayList<Card>();
		for(int i = 0; i < ids.size(); i++){
			output.add(cardpool.get(i));
		}
		return output;
	}
	
	public List<Card> getCards(String list){
		List<Integer> ids = cardlists.get(list).getCards();
		List<Card> output = new ArrayList<Card>(ids.size());
		for(int i : ids){
			output.add(cardpool.get(i));
		}
		return output;
	}
	
	/*
	 * Get value of the name component. Get the template name, then get the name component's name from
	 * that template, then get that component's data from the card
	 */
	public String getDataFromCard(Integer id, String comp){
		return (String)cardpool.get(id).getData(comp);
	}
	
	public List<String> getCardNames(List<Integer> ids){
		List<String> output = new ArrayList<String>(ids.size());
		Card tempCard;
		for(int i : ids){
			tempCard = cardpool.get(i);
			output.add(tempCard.getData(CardTemplateListFactory.getCardTemplateList().get(tempCard.getTemplateName()).getNameComponent().getName()).toString());
		}
		return output;
	}
	
	public Map<String, Object> getDataFromCard(Card c, List<String> components){
		Map<String, Object> data = new HashMap<String, Object>();
		for(String i : components){
			data.put(i, c.getData(i));
		}
		return data;
	}
	
	public BufferedImage getCardImage(Integer id){
		return images.get(id).getImage(false);
	}
	
	public List<BufferedImage> getCardImages(List<Integer> id, boolean isExporting){
		List<BufferedImage> output = new ArrayList<BufferedImage>();
		for(int i = 0; i < id.size(); i++){
			output.add(images.get(id.get(i)).getImage(isExporting));
		}
		return output;
	}
	
	public List<BufferedImage> getCardImages(String list){
		List<Integer> ids = cardlists.get(list).getCards();
		List<BufferedImage> output = new ArrayList<BufferedImage>();
		for(int i = 0; i < ids.size(); i++){
			output.add(images.get(ids.get(i)).getImage(false));
		}
		return output;
	}
	
	public void sortList(String list, String colName){
		SortMethod sort = cardlists.get(list).getSort();
		sort.addColumn(colName, sort.hasColumn(colName) ? !sort.columnIsAscending(colName) : true);
		List<Integer> listToSort = cardlists.get(list).getCards();
		// Sort the list
		if (listToSort == null || listToSort.size() == 0) return;
        quickSortCards(listToSort, sort, 0, listToSort.size() - 1);
		cardlists.get(list).setCards(listToSort);
	}
	
    private void quickSortCards(List<Integer> array, SortMethod sort, int lowerIndex, int higherIndex) {
        int i = lowerIndex;
        int j = higherIndex;
        int temp;
        // calculate pivot number, I am taking pivot as middle index number
        int pivot = array.get(lowerIndex + (higherIndex - lowerIndex) / 2);
        // Divide into two arrays
        while (i <= j) {
            /**
             * In each iteration, we will identify a number from left side which 
             * is greater then the pivot value, and also we will identify a number 
             * from right side which is less then the pivot value. Once the search 
             * is done, then we exchange both numbers.
             */
            while (sort.compare(cardpool.get(array.get(i)), cardpool.get(pivot)) < 0) {
                i++;
            }
            while (sort.compare(cardpool.get(array.get(j)), cardpool.get(pivot)) > 0) {
                j--;
            }
            if (i <= j) {
                temp = array.get(i);
                array.set(i, array.get(j));
                array.set(j, temp);
                //move index to next position on both sides
                i++;
                j--;
            }
        }
        // call quickSort() method recursively
        if (lowerIndex < j){
            quickSortCards(array, sort, lowerIndex, j);
        }
        if (i < higherIndex){
            quickSortCards(array, sort, i, higherIndex);
        }
    }
	
    /*
     * Redraw the entire set's images
     */
	public void refresh(){
		CardImage ci;
		String tempName;
		for(int id : cardpool.keySet()){
			// Get template from the card, load if needed
			tempName = cardpool.get(id).getTemplateName();
			if(CardTemplateListFactory.getCardTemplateList().get(tempName) == null){ // Load template from file
				CardTemplateListFactory.getCardTemplateList().put(tempName);
				if(CardTemplateListFactory.getCardTemplateList().get(tempName) == null) continue; // Oh no, can't draw this card
			}
			// Get card image from card, make new one if needed
			ci = images.get(id);
			if(ci == null){
				images.put(id, new CardImage(CardTemplateListFactory.getCardTemplateList().get(cardpool.get(id).getTemplateName())));
			}
			// Update the image
			images.get(id).updateImage(CardTemplateListFactory.getCardTemplateList().get(tempName), cardpool.get(id).getData());
		}
	}
	
	public Set<Integer> getCardIds(){
		return cardpool.keySet();
	}
	
}
