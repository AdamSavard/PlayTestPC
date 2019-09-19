package view.misc.table;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

// Handles a list for the list GUI
public class ListModel extends AbstractListModel<String> {
	private static final long serialVersionUID = 1L;
	
	private List<String> data;
    public List<String> getList(){ return data; }
    public int getSize(){ return data.size(); }
    public String getElementAt(int index){ return data.get(index); }
    public void setData(List<String> newData) { data = newData; }
    public void set(int index, String newElement) { data.set(index, newElement); }
    public int add(int index, String toAdd){
    	if(data.size() > 0) data.add(index + 1, toAdd);
    	else data.add(toAdd);
    	ListDataEvent addition = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, data.size() - 1, data.size() - 1);
        for (ListDataListener l : listeners) { l.intervalAdded(addition); }
        return data.size() == 1 ? 0 : index + 1;
    }
    public void addElement(String toAdd){
    	data.add(toAdd);
    	ListDataEvent addition = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, data.size() - 1, data.size() - 1);
        for (ListDataListener l : listeners) { l.intervalAdded(addition); }
    }
    public int remove(int index){
    	data.remove(index);
        ListDataEvent removal = new ListDataEvent(this, ListDataEvent.INTERVAL_REMOVED, data.size(), data.size());
        for (ListDataListener l : listeners) {  l.intervalRemoved(removal); }
        return data.size() > index ? index : data.size() - 1;
    }
    public void swap(int i1, int i2){
    	String temp = data.get(i1);
    	data.set(i1, data.get(i2));
    	data.set(i2, temp);
    }
    public void reset(){
    	data = new ArrayList<String>();
    	data.add("New Card");
    }
    
    private List<ListDataListener> listeners = new ArrayList<ListDataListener>();
    @Override
    public void addListDataListener(ListDataListener l) { listeners.add(l); }
    @Override
    public void removeListDataListener(ListDataListener l) { listeners.remove(l); }

    public ListModel(){
    	data = new ArrayList<String>();
    	data.add("New Card");
    }
    
    public ListModel(List<String> array){
        data = array;
    }
}