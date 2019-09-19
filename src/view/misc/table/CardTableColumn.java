package view.misc.table;

public class CardTableColumn {
	
	private String name;
	public String getName(){
		return name;
	}
	public void setName(String newName){
		name = newName;
	}
	
	private Class<?> dataClass;
	public Class<?> getDataClass(){
		return dataClass;
	}
	public void setDataClass(Class<?> newDataClass){
		dataClass = newDataClass;
	}
	
	public CardTableColumn(String iName, Class<?> iClass){
		name = iName;
		dataClass = iClass;
	}

}
