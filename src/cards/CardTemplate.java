package cards;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cards.components.CardComponent;
import mainframe.DrawWizard;
import constants.Constants;

/*
 * Represents a card template
 * Contains various components that will appear on cards created using this template
 */
public class CardTemplate implements Comparable<CardTemplate>, Cloneable {
	
	private transient String name;
	
	private int width;
	
	private int height;

	/*
	 * How template should be rotated when printed
	 */
	private int rotation; // 0 for no rotation, 1 for 90 clockwise, 2 for 180, 3 for 270
	
	/*
	 * Map of ID to components on the template for fast access
	 */
	private Map<Integer, CardComponent> components;
	
	/*
	 * Map of Z coordinate to set of IDs of components located at that Z coordinate
	 */
	private Map<Integer, List<Integer>> zToIds;
	
	/*
	 * List of IDs of comoponents in a specific order - This should be built when the template is saved
	 * Don't bother maintaining this while template is being edited.
	 */
	private List<Integer> orderedIdList;
	
	/*
	 * The highest ID among components
	 */
	private int maxId;
	
	/*
	 * The ID of the currently selected component
	 */
	private transient int selectedId;
	
	/*
	 * The ID of the name component, required to be a textfield
	 */
	private int nameId;
	
	// vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv CONSTRUCTORS vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
	
	public CardTemplate(){
		this(Constants.DEFAULT_TEMPLATE_WIDTH, Constants.DEFAULT_TEMPLATE_HEIGHT, "", 0);
	}
	
	public CardTemplate(int iWidth, int iHeight, String iName, int iRotation){
		name = iName;
		width = iWidth;
		height = iHeight;
		rotation = iRotation;
		zToIds = new HashMap<Integer, List<Integer>>();
		components = new HashMap<Integer, CardComponent>();
		orderedIdList = new ArrayList<Integer>();
		maxId = 0;
		selectedId = 0;
		nameId = 0;
	}
	
	public CardTemplate(CardTemplate i){
		name = i.name;
		width = i.width;
		height = i.height;
		rotation = i.rotation;
		zToIds = new HashMap<Integer, List<Integer>>();
		components = new HashMap<Integer, CardComponent>();
		maxId = 0;
		selectedId = 0;
		nameId = 0;
	}
	
	// ^^^^^^^^^^^^^^^^^^^^^^^^^ CONSTRUCTORS ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
	public String getName(){
		return name;
	}
	
	public void setName(String newName){
		name = newName;
	}
	
	public int getWidth(){
		return width;
	}
	
	public void setWidth(int newWidth){
		if(newWidth == width) return;
		width = newWidth;
	}
	
	public int getHeight(){
		return height;
	}
	
	public void setHeight(int newHeight){
		if(newHeight == height) return;
		height = newHeight;
	}
	
	public int getRotation(){ // rotation only impacts printing, not display in program
		return rotation;
	}
	
	public boolean setRotation(int newRotation){
		if(newRotation < 0 || newRotation > 3) return false;
		rotation = newRotation;
		return true;
	}
	
	/*
	 * Return a list of IDs of components that exist at x,y coordinate, ordered by z and timestamp
	 */
	private List<Integer> getIDsAt(int x, int y){
		List<Integer> output = new ArrayList<Integer>(components.size());
		// Iterate over components
		Iterator<Integer> it = components.keySet().iterator();
		Integer nextId;
		CardComponent next;
	    while (it.hasNext()) {
	    	nextId = (Integer)it.next();
	        next = components.get(nextId);
	        if(!next.containsPoint(x, y)) continue;
	        // For each component that exists in this space, add it to list
	        output.add(nextId);
	    }
	    // Order the list
	    Collections.sort(output);
		return output;
	}
	
	public int highestZ(int x, int y, int width, int height){
		int output = 0;
		List<Integer> tempList;
		for(int i = x; i < x + width; i++){
			for(int j = y; j < y + height; j++){
				tempList = getIDsAt(i, j);
				if(tempList == null || tempList.size() == 0) continue;
				if(components.get(tempList.get(tempList.size() - 1)).getZ() > output) output = tempList.get(tempList.size() - 1);
			}
		}
		return output;
	}
	
	private void setSelectedId(int newId){
		if(components.get(selectedId) != null){
			components.get(selectedId).setSelected(false);
			components.get(selectedId).updateImage();
		}
		selectedId = newId;
		if(components.get(selectedId) != null){
			components.get(selectedId).setSelected(true);
			components.get(selectedId).updateImage();
		}
	}

	public boolean makeSelectedComponentBeTheName(){
		if(components.get(selectedId) == null) return false;
		if(!components.get(selectedId).getTypeString().equals(Constants.TEXT_TYPE)) return false;
		// Unset current nameId
		if(components.get(nameId) != null){
			components.get(nameId).setIsCardName(false);
		}
		// Make selected by nameId
		nameId = selectedId;
		components.get(selectedId).setIsCardName(true);
		return true;
	}
	
	public boolean unsetTheName(){
		if(components.get(nameId) != null){
			components.get(nameId).setIsCardName(false);
		}
		nameId = 0;
		return true;
	}
	
	public<T extends CardComponent> T addComponent(T init){
		int initZ = highestZ(init.getX(), init.getY(), init.getWidth(), init.getHeight());
		int newId = (++maxId * 100) + initZ;
		if(initZ > Constants.MIN_Z && initZ < Constants.MAX_Z) initZ += 1;
		init.setZ(initZ);
		components.put(newId, init);
		if(zToIds.get(initZ) == null) zToIds.put(initZ, new ArrayList<Integer>());
		zToIds.get(initZ).add(newId);
		setSelectedId(newId);
		return init;
	}
	
	public<T extends CardComponent> T addComponent(T init, int x, int y, int width, int height){
		int initZ = highestZ(x, y, width, height);
		int newId = (++maxId * 100) + initZ;
		if(initZ > Constants.MIN_Z && initZ < Constants.MAX_Z) initZ += 1;
		init.setX(x);
		init.setY(y);
		init.setZ(initZ);
		init.setWidth(width);
		init.setHeight(height);
		components.put(newId, init);
		if(zToIds.get(initZ) == null) zToIds.put(initZ, new ArrayList<Integer>());
		zToIds.get(initZ).add(newId);
		setSelectedId(newId);
		return init;
	}
	
	public CardComponent selectComponent(int x, int y){
		// Get the list of components that touch this point
		List<Integer> tempList = getIDsAt(x, y);
		// If its empty, we're done here
		if(tempList == null || tempList.size() == 0){
			setSelectedId(-1);
			return null;
		}
		// If any except for the bottom-most are selected, switch selection to the next one down
		boolean shifted = false;
		for(int i = tempList.size() - 1; i > 1; i--){
			if(tempList.get(i) == selectedId){
				setSelectedId(tempList.get(i - 1));
				shifted = true;
				break;
			}
		}
		// Otherwise select the top one
		if(!shifted){
			setSelectedId(tempList.get(tempList.size() - 1));
		}
		return components.get(selectedId);
	}
	
	public void removeSelectedComponent(){
		int z = components.get(selectedId).getZ();
		zToIds.get(z).remove(new Integer(selectedId));
		components.remove(selectedId);
		setSelectedId(chooseNextSelected());
	}
	
	private int chooseNextSelected(){
		for(int zz = Constants.MAX_Z; zz >= Constants.MIN_Z; zz--){
			if(zToIds.get(zz) == null || zToIds.get(zz).size() == 0) continue;
			return (Integer)zToIds.get(zz).toArray()[0];
		}
		return 0;
	}
	
	public int getComponentCount(){
		return components.size();
	}
	
	public CardComponent getSelectedComponent(){
		return components.get(selectedId);
	}
	
	public Collection<CardComponent> getComponentCollection(){
		return components.values();
	}
	
	/*
	 *  Build an ordered list of components
	 */
	public void buildOrderedIdList(){
		orderedIdList.clear();
		int index = 0;
		for(int i : components.keySet()){
			for(index = 0; index < orderedIdList.size(); index++){
				if(components.get(i).compareTo(components.get(orderedIdList.get(index))) > 0){
					break;
				}
			}
			orderedIdList.add(index, i);
		}
	}
	
	public List<CardComponent> getComponentList(){
		List<CardComponent> output = new ArrayList<CardComponent>();
		for(int i = 0; i < orderedIdList.size(); i++){
			output.add(components.get(orderedIdList.get(i)));
		}
		return output;
	}
	
	public List<String> getComponentNameList(){
		List<String> output = new ArrayList<String>();
		for(int i = 0; i < orderedIdList.size(); i++){
			output.add(components.get(orderedIdList.get(i)).getName());
		}
		return output;
	}
	
	public BufferedImage getImage(int imgWidth, int imgHeight){
		return DrawWizard.drawCardTemplate(imgWidth, imgHeight, width, height, rotation, components, zToIds);
	}
	
	public void updateImage(){
		for(CardComponent i : components.values()){
			i.updateImage();
		}
	}
	
	/*
	 * Does this template have an editable component to serve as the card name
	 */
	public boolean hasNameComponent(){
		if(nameId == 0 || components.get(nameId) == null) return false;
		if(!components.get(nameId).isEditable()) return false;
		return true;
	}
	
	/*
	 * Return 0 is all component names are valid, return component id otherwise
	 */
	public int validateComponents(){
		Set<String> names = new HashSet<String>();
		for(Integer i : components.keySet()){
			// Every editable component must have a name, return id if not
			if(components.get(i).isEditable() && components.get(i).getName().trim().equals("")) return i;
			// Make sure names of editable components are not duplicated
			if(components.get(i).isEditable()){
				if(names.contains(components.get(i).getName())) return i;
				names.add(components.get(i).getName());
			}
		}
		return 0;
	}
	
	public CardComponent getNameComponent(){
		return components.get(nameId);
	}
	
	public CardComponent getComponentById(int id){
		return components.get(id);
	}

	@Override
	public int compareTo(CardTemplate otherTemplate) {
		return name.compareTo(otherTemplate.getName());
	}
	
	@Override
	public CardTemplate clone() {
		try {
			return (CardTemplate) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}
}
