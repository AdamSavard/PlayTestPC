package model.util;

import java.util.ArrayList;
import java.util.List;

import cards.Card;

/*
 * Stores method for sorting a table
 */
public class SortMethod {
	
	// Column names
	private List<String> columns;
	
	// True if sort by column ascending , false if descending
	private List<Boolean> ascend;
	
	public SortMethod(){
		columns = new ArrayList<String>();
		ascend = new ArrayList<Boolean>();
	}
	
	public boolean hasColumn(String column){
		return columns.indexOf(column) > -1;
	}
	
	public boolean columnIsAscending(String column){
		int index = columns.indexOf(column);
		if(index < 0) return false;
		return ascend.get(index);
	}
	
	public void addColumn(String column, boolean ascending){
		int existing = columns.indexOf(column);
		if(existing > -1){
			columns.remove(existing);
			ascend.remove(existing);
		}
		columns.add(column);
		ascend.add(ascending);
	}
	
	public void reset(){
		columns = new ArrayList<String>();
		ascend = new ArrayList<Boolean>();
	}

	public int compare(Card c1, Card c2){
		Object data1, data2;
		String str1, str2;
		int result;
		for(int i = columns.size() - 1; i >= 0; i--){
			data1 = c1.getData(columns.get(i));
			data2 = c2.getData(columns.get(i));
			str1 = data1 == null ? "" : data1.toString();
			str2 = data2 == null ? "" : data2.toString();
			result = str1.compareTo(str2);
			if(result == 0) continue;
			if((ascend.get(i) && result > 0) || (!ascend.get(i) && result < 0)) return 1;
			return -1;
		}
		return 0;
	}
}
