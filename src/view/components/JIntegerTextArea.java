package view.components;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class JIntegerTextArea extends JSuperTextArea {

	private static final long serialVersionUID = 1L;

	public JIntegerTextArea(){
		super();
		addKeyListener(new IntegerAdapter());
		setText("0");
	}
	
	public int getIntegerText(){
		if("".equals(getText())) return 0;
		return Integer.parseInt(getText());
	}
	
	public void setText(int newText){
		setText(Integer.toString(newText));
	}
	
	private class IntegerAdapter extends KeyAdapter {
		public void keyTyped(KeyEvent e) {
			handleKey(e);
		}
	}
	
	private void handleKey(KeyEvent e){
		// Make sure enter does not create a new line
	   if(e.getKeyCode() == KeyEvent.VK_ENTER) {
		   e.consume();
		   return;
	   }
	   char c = e.getKeyChar();
	   if (c == KeyEvent.CHAR_UNDEFINED){
	      return;
	   }
	   if(c == '-'){
		   if(getText().length() > 0 && getText().charAt(0) == '-'){
			   e.consume();
			   return;
		   }
		   setText('-' + getText());
		   e.consume();
		   return;
	   }
	   if(c >= '0' && c <= '9'){
		   // prevent extra leading zero
		   if(c == '0' && getText().length() > 0 && getText().indexOf('0') > -1 && this.getCaretPosition() < 2){
			   e.consume();
			   return;
		   }
		   return;
	   }
	   e.consume();
	}
}
