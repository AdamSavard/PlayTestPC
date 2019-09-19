package cards;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Card implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String templateName;
	
	private long updateTime;
	
	// Data for each component on the card
	private Map<String, Object> data;
	
	private String comments;
	
	public Card(CardTemplate template){
		init(template);
	}
	
	private void init(CardTemplate template){
		templateName = template.getName();
		data = new HashMap<String, Object>();
		comments = "";
		setUpdateTime();
	}
	
	public String getTemplateName(){
		return templateName;
	}
	
	public void setTemplateName(String tn){
		templateName = tn;
	}
	
	public long getUpdateTime(){
		return updateTime;
	}
	
	public void setUpdateTime(){
		updateTime = System.currentTimeMillis();
	}
	
	public boolean setData(String comp, Object newData){
		// Can't update if new data is same as old data
		Object current = data.get(comp);
		if(current != null && current.equals(newData)) return false;
		// Update the component
		data.put(comp, newData);
		setUpdateTime();
		return true;
	}

	public Object getData(String comp){
		if (data.get(comp) == null) return "";
		return data.get(comp);
	}

	public Map<String, Object> getData(){
		return data;
	}

	public String getComments(){
		return comments;
	}
	
	public void setComments(String newComments){
		comments = newComments;
		setUpdateTime();
	}
	
	/*
	 * Need to preserve component data when switching a card to a new template
	 */
	public void switchTemplate(CardTemplate template){
		templateName = template.getName();
		setUpdateTime();
	}
}
