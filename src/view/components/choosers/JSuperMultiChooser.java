package view.components.choosers;

import java.awt.Font;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

public class JSuperMultiChooser extends JAbstractSuperChooser {

	private static final long serialVersionUID = 1L;
	
	private List<JAbstractSuperChooser> choosers;
	
	public JSuperMultiChooser(){
		super();
		choosers = new ArrayList<JAbstractSuperChooser>();
	}
	
	public void addChooser(JAbstractSuperChooser newChooser){
		choosers.add(newChooser);
		add(newChooser);
	}
	
	@SuppressWarnings("unchecked")
	public boolean setData(Object data){
		if(data == null){
			return false;
		}
		if(!(data instanceof List)){
			return false;
		}
		List<Object> dataList = (List<Object>)data;
		for(int i = 0; i < dataList.size() && i < choosers.size(); i++){
			choosers.get(i).setData(choosers.get(i));
		}
		return true;
	}
	
	public List<Object> getData(){
		List<Object> data = new ArrayList<Object>();
		for(int i = 0; i < choosers.size(); i++){
			data.add(choosers.get(i).getData());
		}
		return data;
	}

	@Override
	public void addKeyListener(KeyListener newListener) {
		for(JAbstractSuperChooser i : choosers) {
			i.addKeyListener(newListener);
		}
	}

	@Override
	public void setFont(Font newFont) {
		for(JAbstractSuperChooser i : choosers) {
			i.setFont(newFont);
		}
	}
	
	@Override
	public JComponent getTabComponent() {
		return null;
	}

}
