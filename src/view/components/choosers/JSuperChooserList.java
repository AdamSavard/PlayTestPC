package view.components.choosers;

import java.util.ArrayList;
import java.util.List;

public class JSuperChooserList {
	
	private List<JAbstractSuperChooser> components;
	
	public JSuperChooserList(){
		components = new ArrayList<JAbstractSuperChooser>();
	}
	
	public void clear(){
		components.clear();
	}
	
	public int size(){
		return components.size();
	}
	
	public void add(JAbstractSuperChooser add){
		components.add(add);
	}
	
	public void remove(JAbstractSuperChooser remove){
		components.remove(remove);
	}
	
	public void remove(int i){
		components.remove(i);
	}
	
	public JAbstractSuperChooser get(int i){
		return components.get(i);
	}
	
	public Object getData(int index){
		if(index < 0 || index >= components.size()){
			return new Object();
		}
		return components.get(index).getData();
	}
	
	public boolean setData(int index, Object data){
		if(index < 0 || index >= components.size()){
			return false;
		}
		components.get(index).setData(data);
		return true;
	}

}
