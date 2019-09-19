package view.misc.table;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import view.components.choosers.JSuperColorChooser;

public class CardTableModel extends AbstractTableModel {
	
	private static final long serialVersionUID = 1L;

	private List<CardTableRow> rows;
	
	private List<CardTableColumn> columns;
	
	public CardTableModel(){
		super();
		rows = new ArrayList<CardTableRow>();
		columns = new ArrayList<CardTableColumn>();
	}

    public int addRow(Map<String, Object> rowData, int index){
    	for(String key : rowData.keySet()){
    		rowData.put(key, cleanData(rowData.get(key)));
    	}
        rows.add(index, new CardTableRow(rowData));
        return index;
    }
    
    public void removeRow(int index){
    	rows.remove(index);
    }
    
    public void moveRow(int oldIndex, int newIndex){
    	int pos = oldIndex;
    	if(oldIndex >= rows.size() || newIndex >= rows.size()){
    		System.out.println("Out of bounds index in moveRow");
    		return;
    	}
    	while(newIndex > pos){
    		Collections.swap(rows, pos, ++pos);
    	}
    	while(newIndex < pos){
    		Collections.swap(rows, pos, --pos);
    	}
    }

    @Override
    public int getRowCount(){
        return rows.size();
    }
    
    public int indexOfColumn(String colName){
    	if(colName == null) return -1;
    	for(int i = 0; i < columns.size(); i++){
    		if(colName.equals(columns.get(i).getName())) return i;
    	}
    	return -1;
    }
    
    public void addColumn(String colName, Class<?> type){
    	columns.add(new CardTableColumn(colName, type));
    }

    @Override
    public int getColumnCount(){
        return columns.size();
    }

    @Override
    public Class<?> getColumnClass(int index){
    	if(index < 0 || index >= columns.size()) return null;
        return columns.get(index).getDataClass();
    }

    @Override
    public String getColumnName(int index){
    	if(index < 0 || index >= columns.size()) return null;
        return columns.get(index).getName();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex){
    	if(rowIndex < 0 || rowIndex >= rows.size()) return null;
    	if(columnIndex < 0 || columnIndex >= columns.size()) return null;
        return rows.get(rowIndex).getValue(columns.get(columnIndex).getName());
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex){
    	if(rowIndex < 0 || rowIndex >= rows.size()) return;
    	if(columnIndex < 0 || columnIndex >= columns.size()) return;
        rows.get(rowIndex).setValue(columns.get(columnIndex).getName(), cleanData(value));
    }
    
    private Object cleanData(Object data){
    	String valueStr = data.toString();
    	if(valueStr.contains("java.awt.Color")){ // change the way the color displays
    		String[] parsedValueStr = valueStr.split("=");
    		int red = Integer.parseInt(parsedValueStr[1].substring(0, parsedValueStr[1].indexOf(',')));
    		int green = Integer.parseInt(parsedValueStr[2].substring(0, parsedValueStr[2].indexOf(',')));
    		int blue = Integer.parseInt(parsedValueStr[3].substring(0, parsedValueStr[3].indexOf(']')));
    		return JSuperColorChooser.colorToHex(new Color(red,green,blue));
    	}
    	return data;
    }
    
    public void removeAll(){
		rows = new ArrayList<CardTableRow>();
		columns = new ArrayList<CardTableColumn>();
    }
}
