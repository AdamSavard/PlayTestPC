package view.components.choosers;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JPanel;

public abstract class JAbstractSuperChooser extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Map<String, String> values;
	
	protected List<ActionListener> listeners;
	
	private boolean listenersActive;
	
	public JAbstractSuperChooser() {
		super();
		values = new HashMap<String, String>();
		listeners = new ArrayList<ActionListener>();
		listenersActive = true;
	}
	
	public abstract boolean setData(Object data);
	
	public abstract Object getData();
	
	@Override
	public abstract void addKeyListener(KeyListener newListener);
	
	@Override
	public abstract void setFont(Font newFont);
	
	public void addActionListener(ActionListener newListener) {
		listeners.add(newListener);
	}
	
	public void removeActionListener(ActionListener newListener) {
		listeners.remove(newListener);
	}
	
	public void disableActionListeners() {
		listenersActive = false;
	}
	
	public void enableActionListeners() {
		listenersActive = true;
	}
	
	protected void dataChanged(){
		if(!listenersActive) return;
		for(ActionListener i : listeners){
			i.actionPerformed(new ActionEvent(this, 0, "change"));
		}
	}
	
	public void putValue(String key, String value){
		values.put(key, value);
	}
	
	public String getValue(String key){
		return values.get(key);
	}
	
	public abstract JComponent getTabComponent();
	
}
