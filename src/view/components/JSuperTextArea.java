package view.components;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import constants.Constants;

/*
 * Custom text field with extra features
 *  - document replaced with FancyDocument for character limit and whitelist
 *  - option to include an icon
 */
public class JSuperTextArea extends JTextArea {

	private static final long serialVersionUID = 1L;
	
	private Image icon;
    
    private Image iconSized;
    
    private int iconLeft;
    
    // Optional properties for manual tabbing
    private JComponent next, previous;
	
	public JSuperTextArea() {
		super();
		this.setDocument(new FancyDocument());
        next = null;
        previous = null;
	}
 
    public JSuperTextArea(Image iIcon) {
        super();
        icon = iIcon;
        iconSized = iIcon;
        iconLeft = 5;
        next = null;
        previous = null;
		this.setDocument(new FancyDocument());
		this.setRows(1);
    }
    
    public void setIcon(Image nIcon) {
        icon = nIcon;
    }
    
    public Image getIcon() {
        return icon;
    }
    
    public void setNext(JComponent newNext){
    	next = newNext;
    }
    
    public JComponent getNext(){
    	return next;
    }
    
    public void setPrevious(JComponent newPrev){
    	previous = newPrev;
    }
    
    public JComponent getPrevious(){
    	return previous;
    }
	
    public void disableKeyBindings() {
    	getInputMap().put(Constants.DELETE_KEYSTROKE, "nothing");
    	getInputMap().put(Constants.COPY_KEYSTROKE, "nothing");
		getInputMap().put(Constants.CUT_KEYSTROKE, "nothing");
		getInputMap().put(Constants.PASTE_KEYSTROKE, "nothing");
    }
    
	public int getCharacterLimit() {
		if(!(this.getDocument() instanceof FancyDocument)) return -1;
		return ((FancyDocument)this.getDocument()).getLimit();
	}
	
	public void setCharacterLimit(int newLimit) {
		if(!(this.getDocument() instanceof FancyDocument)){
			this.setDocument(new FancyDocument());
		}
		((FancyDocument)this.getDocument()).setLimit(newLimit);
	}
	
	public void setCharacterWhitelist(char[] chars) {
		if(!(this.getDocument() instanceof FancyDocument)){
			this.setDocument(new FancyDocument());
		}
		((FancyDocument)this.getDocument()).setCharacterList(chars);
	}
	
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(iconSized != null) {
        	g.drawImage(iconSized, iconLeft, (this.getHeight() - iconSized.getHeight(null)) / 2, null);
            setMargin(new Insets(2, iconLeft + iconSized.getWidth(null), 2, 2));
        }
    }
	
	public class FancyDocument extends PlainDocument {
		
		private static final long serialVersionUID = 1L;
		
		private int limit;
		public int getLimit() {
			return limit;
		}
		public void setLimit(int newLimit) {
			limit = newLimit;
		}
		
		private char[] whitelist;
		public void setCharacterList(char[] chars) {
			whitelist = chars;
		}
		
		public FancyDocument() {
			super();
			limit = 0;
			whitelist = new char[0];
		}

		public void insertString(int offset,String str,AttributeSet attr) throws BadLocationException {
		    if(str == null) return;
		    int extra;
		    boolean found;
		    
		    if(limit > 0) {
		    	extra = getLength() + str.length() - limit;
		    	if(extra > 0){
		    		if(extra >= str.length()) return;
		    		str = str.substring(0, str.length() - extra);
		    	}
		    }
		    
		    if(whitelist != null && whitelist.length > 0){
		    	for(int i = str.length() - 1; i >= 0; i--){
		    		found = false;
		    		for(int j = 0; j < whitelist.length; j++){
		    			if(whitelist[j] == str.charAt(i)){
		    				found = true;
		    				break;
		    			}
		    		}
		    		if(found) continue;
		    		// If the character was not found on the whitelist, remove it
		    		str = str.substring(0, i) + str.substring(i + 1);
		    	}
		    }
		    
		    super.insertString(offset, str, attr);
		}
	}

}
