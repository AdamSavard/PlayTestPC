package view.components;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

/*
 * A JPanel with extra functionality for convenience
 */
public class JIdMapPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Map<String, Component> compIdMap;
	public Component getComponentFromId(String id){
		return compIdMap.get(id);
	}
	
	public Component add(String id, Component comp){
		add(comp);
		compIdMap.put(id, comp);
		return comp;
	}
	
	public void remove(String id, Component comp){
		remove(comp);
		compIdMap.remove(id, comp);
	}
	
	public JIdMapPanel(){
		super();
		compIdMap = new HashMap<String, Component>();
	}
}
