package view.components.choosers;

import java.awt.Font;
import java.awt.event.KeyListener;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.miginfocom.swing.MigLayout;

public class JSuperTextChooser extends JAbstractSuperChooser {

	private static final long serialVersionUID = 1L;
	
	private JTextArea text;
	
	private JScrollPane scroll;
	
	public JSuperTextChooser(int rows, int columns){
		super();
		setLayout(new MigLayout("wrap 1, insets 0 0 0 0","[grow,fill]","[grow,fill]"));
		text = new JTextArea();
		text.setColumns(columns);
		text.setRows(rows);
		text.getDocument().addDocumentListener(new STADocListener());
		if(rows == 1){
			add(text);
		} else {
			scroll = new JScrollPane(text);
			scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
			scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			add(scroll);
		}
	}
	
	@Override
	public boolean setData(Object data) {
		if(data == null){
			return false;
		}
		text.setText(data.toString());
		return true;
	}

	@Override
	public Object getData() {
		return text.getText();
	}

	@Override
	public void addKeyListener(KeyListener newListener) {
		if(text != null) text.addKeyListener(newListener);
	}

	@Override
	public void setFont(Font newFont) {
		if(text != null) text.setFont(newFont);
	}
	
	@Override
	public JComponent getTabComponent() {
		return text;
	}
	
	public JTextArea getTextArea() {
		return text;
	}
	
	private class STADocListener implements DocumentListener {
		public void changedUpdate(DocumentEvent e) {
			update(e);
		}
		public void insertUpdate(DocumentEvent e) {
			update(e);
		}
		public void removeUpdate(DocumentEvent e) {
			update(e);
		}
		public void update(DocumentEvent e){
			dataChanged();
		}
	}

}
