package cards;

import java.util.ArrayList;
import java.util.List;

import model.util.SortMethod;

/*
 * List of cards in a specific set. Used for GUI tables.
 */
public class CardList {
	
	private List<Integer> ids;
	
	private SortMethod sort;
	
	private int selectedIndex;
	
	public CardList(){
		ids = new ArrayList<Integer>();
		sort = new SortMethod();
		selectedIndex = -1;
	}
	
	public List<Integer> getCards(){
		return ids;
	}
	
	public void setCards(List<Integer> newIds){
		ids = newIds;
	}
	
	public int size(){
		return ids.size();
	}
	
	public SortMethod getSort(){
		return sort;
	}
	
	public boolean setSelectedIndex(int newSelected){
		if(newSelected < 0 || newSelected >= ids.size()) return false;
		selectedIndex = newSelected;
		return true;
	}
	
	public int getSelectedIndex(){
		return selectedIndex;
	}
	
	public void insertCard(int newId, int index){
		ids.add(index, newId);
	}
	
	public int getAtIndex(int index){
		if(index < 0 || index >= ids.size()) return -1;
		return ids.get(index);
	}
	
	public int getSelected(){
		if(selectedIndex < 0 || selectedIndex >= ids.size()) return -1;
		return ids.get(selectedIndex);
	}
	
	public int removeSelected(){
		int output = ids.remove(selectedIndex);
		if(selectedIndex > 0) selectedIndex--;
		return output;
	}
	
	/*
	 * Move a card from one position in the list to another, bumping every card between up or down a notch
	 */
	public void moveCard(int oldIndex, int newIndex){
		if(oldIndex < 0 || oldIndex > ids.size() || newIndex < 0 || newIndex > ids.size() || oldIndex == newIndex) return;
		boolean increment = oldIndex > newIndex;
		int position = newIndex;
		int temp0 = ids.get(position), temp1;
		ids.set(position, ids.get(oldIndex));
		while(position != oldIndex){
			position += increment ? 1 : -1;
			temp1 = ids.get(position);
			ids.set(position, temp0);
			temp0 = temp1;
		}
		// Update the selected index
		if(selectedIndex == oldIndex){
			selectedIndex = newIndex;
		} else if(selectedIndex == newIndex) {
			selectedIndex = oldIndex;
		} else if(oldIndex > newIndex && selectedIndex > newIndex && selectedIndex < oldIndex) {
			selectedIndex++;
		} else if(oldIndex < newIndex && selectedIndex < newIndex && selectedIndex > oldIndex){
			selectedIndex--;
		}
	}
}
