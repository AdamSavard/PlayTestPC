package view.misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class IntDocument extends PlainDocument {
	
	private static final long serialVersionUID = 1L;

	private static List<Character> intChars = new ArrayList<Character>(Arrays.asList('0', '1', '2', '3', '4',
			'5', '6', '7', '8', '9'));
	
	private int limit;
	
    public IntDocument(int iLimt) {
       super();
	   limit = iLimt;
    }

    public void insertString( int offset, String  str, AttributeSet attr ) throws BadLocationException {
       if (str == null) return;
       str = filter(str);
       if ((getLength() + str.length()) <= limit) {
          super.insertString(offset, str, attr);
       }
    }
   
    private String filter(String input){
	   String output = "";
	   for(int i = 0; i < input.length(); i++) if(intChars.contains(input.charAt(i))) output += input.charAt(i);
	   return output;
    }
}
