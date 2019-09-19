package constants;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.KeyStroke;

public final class Constants {
	
	// JFrame titles
	public static final String MAIN_TITLE = "PlayTest";
	public static final String TEMPLATE_MAKER_TITLE = "Card Template Maker";
	
	public static final int LEFT_ALIGNED = 0, H_CENTER_ALIGNED = 1, RIGHT_ALIGNED = 2;
	public static final int TOP_ALIGNED = 0, V_CENTER_ALIGNED = 1, BOTTOM_ALIGNED = 2;
	public static final String[] HORIZON_LIST_STR = new String[]{"Left", "Center", "Right"};
	public static final String[] VERT_LIST_STR = new String[]{"Top", "Middle", "Bottom"};
	public static final Font DEFAULT_FONT = new Font("SansSerif", Font.PLAIN, 12);
	public static final Font BIG_DEFAULT_FONT = new Font("SansSerif", Font.PLAIN, 18);
	
	public static final int MAX_TEMPLATE_LENGTH = 999;
	public static final int DEFAULT_TEMPLATE_WIDTH = 238;
	public static final int DEFAULT_TEMPLATE_HEIGHT = 338;
	public static final int MIN_Z = 0;
	public static final int MAX_Z = 99;
	
	// template editor modes
	public static final int EDITOR_MODE_MAX = 4;
	public static final int EDITOR_MODE_TEXT = 0;
	public static final int EDITOR_MODE_LINE = 1;
	public static final int EDITOR_MODE_RECT = 2;
	public static final int EDITOR_MODE_IMG = 3;
	public static final int EDITOR_MODE_MOVE = 4;
	
	// option lists for card template properties
	public static final int[] FONT_STYLE_LIST = new int[]{Font.PLAIN, Font.BOLD, Font.ITALIC};
	public static final String[] FONT_STYLE_LIST_STR = new String[]{"Plain", "Bold", "Italic"};
	public static final String[] ROTATION_LIST = new String[]{"None", "90 Degrees", "180 Degrees", "270 Degrees"};
	
	// data types for model properties
	public static final String STRING_DATA_TYPE = "String";
	public static final String INTEGER_DATA_TYPE = "Integer";
	public static final String COMBO_DATA_TYPE = "Combo";
	public static final String BOOL_DATA_TYPE = "Boolean";
	public static final String COLOR_DATA_TYPE = "Color";
	
	// panel background color
	public static final Color BACK_COLOR_LIGHT = new Color(165, 187, 228);
	public static final Color BACK_COLOR_DARK = new Color(177, 191, 216);
	public static final Color SELECT_RECT_COLOR = new Color(97, 248, 84);
	public static final Color DRAW_HIGHLIGHT_COLOR = new Color(45, 115, 235);
	
	public static final String TEXT_TYPE = "text";
	public static final String LINE_TYPE = "line";
	public static final String RECT_TYPE = "rect";
	public static final String IMAGE_TYPE = "image";
	
	// key strokes
	public static final KeyStroke DELETE_KEYSTROKE = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0);
	public static final KeyStroke COPY_KEYSTROKE = KeyStroke.getKeyStroke("ctrl pressed C");
	public static final KeyStroke CUT_KEYSTROKE = KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK);
	public static final KeyStroke PASTE_KEYSTROKE = KeyStroke.getKeyStroke("ctrl pressed V");
	
	// action names for key binding
	public static final String DELETE_ACTION = "doDeleteAction";
	public static final String COPY_ACTION = "doCopyAction";
	public static final String CUT_ACTION = "doCutAction";
	public static final String PASTE_ACTION = "doPasteAction";
}
