package view.misc.table;

import java.util.HashMap;
import java.util.Map;

public class CardTableRow {
	
	// Map of Column name -> Data in that column
	// can be null for columns this card does not have
	private Map<String, Object> data;
	
	public CardTableRow(){
		data = new HashMap<String, Object>();
	}
	
	public CardTableRow(Map<String, Object> data){
		this.data = data;
	}
	
	public Object getValue(String column){
		return data.get(column);
	}
	
	public void setValue(String column, Object newData){
		data.put(column, newData);
	}

}
