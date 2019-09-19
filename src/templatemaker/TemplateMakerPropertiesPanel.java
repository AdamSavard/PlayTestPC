package templatemaker;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import view.components.JIdMapPanel;
import view.components.JIntegerTextArea;
import view.components.JSuperTextArea;
import view.components.choosers.JSuperColorChooser;
import view.components.choosers.JSuperImageChooser;
import cards.components.CardComponentProperties;
import constants.Constants;
import constants.ViewInputType;
import net.miginfocom.swing.MigLayout;

public class TemplateMakerPropertiesPanel extends JTabbedPane {

	private static final long serialVersionUID = 1L;
	
	private JPanel compPanel;
	
	private JIdMapPanel textPanel;
	private JLabel txtMetaLabel, txtMetaValue;
	private JLabel txtNameLabel, txtXLabel, txtYLabel, txtWidthLabel, txtHeightLabel, fontFamilyLabel,
					fontStyleLabel, fontSizeLabel, horizonLabel, vertLabel, isNameLabel, txtEditLabel;
	private JIntegerTextArea txtXField, txtYField, txtWidthField, txtHeightField, txtFontSizeField;
	private JSuperTextArea txtNameField, txtFontFamilyField;
	private JComboBox<String> txtFontStyleField, txtHorizonField, txtVertField;
	private JCheckBox isNameField, txtEditField;
	
	private JPanel tempPanel;
	private JLabel tempNameLabel, tempWidthLabel,tempHeightLabel, rotateLabel;
	private JSuperTextArea tempNameField;
	private JIntegerTextArea tempWidthField, tempHeightField;
	private JComboBox<String> rotateField;
	
	private JIdMapPanel linePanel;
	private JLabel lineMetaLabel, lineMetaValue, lineNameLabel, lineX1Label, lineY1Label, lineX2Label, lineY2Label,
	                lineColorLabel, lineEditLabel;
	private JIntegerTextArea lineX1Field, lineY1Field, lineX2Field, lineY2Field;
	private JSuperTextArea lineNameField;
	private JCheckBox lineEditField;
	private JSuperColorChooser lineColorField;
	
	private JIdMapPanel rectPanel;
	private JLabel rectMetaLabel, rectMetaValue, rectNameLabel, rectXLabel, rectYLabel, rectWidthLabel, rectHeightLabel,
    rectFillLabel, rectOutlineLabel, rectEditLabel;
	private JIntegerTextArea rectXField, rectYField, rectWidthField, rectHeightField;
	private JSuperTextArea rectNameField;
	private JCheckBox rectEditField;
	private JSuperColorChooser rectFillField, rectOutlineField;
	
	private JIdMapPanel imagePanel;
	private JLabel imgMetaLabel, imgMetaValue, imgNameLabel, imgXLabel, imgYLabel, imgWidthLabel, imgHeightLabel,
    imgImageLabel, imgEditLabel;
	private JIntegerTextArea imgXField, imgYField, imgWidthField, imgHeightField;
	private JSuperTextArea imgNameField;
	private JCheckBox imgEditField;
	private JSuperImageChooser imgImageField;
	
	private CardTemplateController controller;

	public TemplateMakerPropertiesPanel(CardTemplateController newControl){
		super();
		
		controller = newControl;
		
		compPanel = new JPanel(new BorderLayout());
		addTab("Component Properties", compPanel);
		tempPanel = new JPanel();
		tempPanel.setLayout(new MigLayout("wrap 2","[][grow,fill]",""));
		addTab("Template Properties", tempPanel);
		// initialize the TextField Properties panel
		initTextPanel();
		// initialize the Line Properties panel
		initLinePanel();
		// initialize the Rectangle Properties panel
		initRectPanel();
		// initialize the Image Properties panel
		initImagePanel();
		// initialize the Template Properties pane;
		initTempPanel();
	}
	
	private void initTextPanel(){
		textPanel = new JIdMapPanel();
		textPanel.setLayout(new MigLayout("wrap 2","[][grow,fill]",""));
		
		txtMetaLabel = new JLabel("Component Type:");
		txtMetaLabel.setFont(Constants.DEFAULT_FONT);
		textPanel.add(txtMetaLabel);
		
		txtMetaValue = new JLabel("Text Field");
		txtMetaValue.setFont(Constants.DEFAULT_FONT);
		textPanel.add(txtMetaValue);
		
		txtNameLabel = new JLabel("Name:");
		txtNameLabel.setFont(Constants.DEFAULT_FONT);
		textPanel.add(txtNameLabel);
		
		txtNameField = new JSuperTextArea();
		txtNameField.setFont(Constants.DEFAULT_FONT);
		txtNameField.addKeyListener(new PropertyKeyListener(ViewInputType.JTextArea, CardComponentProperties.Name, true));
		txtNameField.disableKeyBindings();
		textPanel.add(CardComponentProperties.Name, txtNameField);
		
		txtXLabel = new JLabel("X:");
		txtXLabel.setFont(Constants.DEFAULT_FONT);
		textPanel.add(txtXLabel);
		
		txtXField = new JIntegerTextArea();
		txtXField.setFont(Constants.DEFAULT_FONT);
		txtXField.setPrevious(txtNameField);
		txtNameField.setNext(txtXField);
		txtXField.addKeyListener(new PropertyKeyListener(ViewInputType.JIntegerTextArea, CardComponentProperties.X, false));
		txtXField.addFocusListener(new PropertyFocusListener(ViewInputType.JIntegerTextArea, CardComponentProperties.X));
		textPanel.add(CardComponentProperties.X, txtXField);
		
		txtYLabel = new JLabel("Y:");
		txtYLabel.setFont(Constants.DEFAULT_FONT);
		textPanel.add(txtYLabel);
		
		txtYField = new JIntegerTextArea();
		txtYField.setFont(Constants.DEFAULT_FONT);
		txtYField.setPrevious(txtXField);
		txtXField.setNext(txtYField);
		txtYField.addKeyListener(new PropertyKeyListener(ViewInputType.JIntegerTextArea, CardComponentProperties.Y, false));
		txtYField.addFocusListener(new PropertyFocusListener(ViewInputType.JIntegerTextArea, CardComponentProperties.Y));
		textPanel.add(CardComponentProperties.Y, txtYField);
		
		txtWidthLabel = new JLabel("Width:");
		txtWidthLabel.setFont(Constants.DEFAULT_FONT);
		textPanel.add(txtWidthLabel);
		
		txtWidthField = new JIntegerTextArea();
		txtWidthField.setFont(Constants.DEFAULT_FONT);
		txtWidthField.setPrevious(txtYField);
		txtYField.setNext(txtWidthField);
		txtWidthField.addKeyListener(new PropertyKeyListener(ViewInputType.JIntegerTextArea, CardComponentProperties.Width, false));
		txtWidthField.addFocusListener(new PropertyFocusListener(ViewInputType.JIntegerTextArea, CardComponentProperties.Width));
		textPanel.add(CardComponentProperties.Width, txtWidthField);
		
		txtHeightLabel = new JLabel("Height:");
		txtHeightLabel.setFont(Constants.DEFAULT_FONT);
		textPanel.add(txtHeightLabel);
		
		txtHeightField = new JIntegerTextArea();
		txtHeightField.setFont(Constants.DEFAULT_FONT);
		txtHeightField.setPrevious(txtWidthField);
		txtWidthField.setNext(txtHeightField);
		txtHeightField.addKeyListener(new PropertyKeyListener(ViewInputType.JIntegerTextArea,CardComponentProperties.Height, false));
		txtHeightField.addFocusListener(new PropertyFocusListener(ViewInputType.JIntegerTextArea, CardComponentProperties.Height));
		textPanel.add(CardComponentProperties.Height, txtHeightField);
		
		fontFamilyLabel = new JLabel("Font Family:");
		fontFamilyLabel.setFont(Constants.DEFAULT_FONT);
		textPanel.add(fontFamilyLabel);
		
		txtFontFamilyField = new JSuperTextArea();
		txtFontFamilyField.setFont(Constants.DEFAULT_FONT);
		txtFontFamilyField.setPrevious(txtHeightField);
		txtHeightField.setNext(txtFontFamilyField);
		txtFontFamilyField.addKeyListener(new PropertyKeyListener(ViewInputType.JTextArea, CardComponentProperties.FontFamily, true));
		textPanel.add(CardComponentProperties.FontFamily, txtFontFamilyField);
		
		fontStyleLabel = new JLabel("Font Style:");
		fontStyleLabel.setFont(Constants.DEFAULT_FONT);
		textPanel.add(fontStyleLabel);
		
		txtFontStyleField = new JComboBox<String>(Constants.FONT_STYLE_LIST_STR);
		txtFontStyleField.setFont(Constants.DEFAULT_FONT);
		txtFontFamilyField.setNext(txtFontStyleField);
		txtFontStyleField.addActionListener(new PropertyActionListener(ViewInputType.JComboBoxString, CardComponentProperties.FontStyle));
		textPanel.add(CardComponentProperties.FontStyle, txtFontStyleField);
		
		fontSizeLabel = new JLabel("Font Size:");
		fontSizeLabel.setFont(Constants.DEFAULT_FONT);
		textPanel.add(fontSizeLabel);
		
		txtFontSizeField = new JIntegerTextArea();
		txtFontSizeField.setFont(Constants.DEFAULT_FONT);
		txtFontSizeField.addKeyListener(new PropertyKeyListener(ViewInputType.JIntegerTextArea, CardComponentProperties.FontSize, true));
		textPanel.add(CardComponentProperties.FontSize, txtFontSizeField);
		
		horizonLabel = new JLabel("Horizontal Alignment:");
		horizonLabel.setFont(Constants.DEFAULT_FONT);
		textPanel.add(horizonLabel);
		
		txtHorizonField = new JComboBox<String>(Constants.HORIZON_LIST_STR);
		txtHorizonField.setFont(Constants.DEFAULT_FONT);
		txtHorizonField.addActionListener(new PropertyActionListener(ViewInputType.JComboBoxString, CardComponentProperties.Horizon));
		textPanel.add(CardComponentProperties.Horizon, txtHorizonField);
		
		vertLabel = new JLabel("Vertical Alignment:");
		vertLabel.setFont(Constants.DEFAULT_FONT);
		textPanel.add(vertLabel);
		
		txtVertField = new JComboBox<String>(Constants.VERT_LIST_STR);
		txtVertField.setFont(Constants.DEFAULT_FONT);
		txtVertField.addActionListener(new PropertyActionListener(ViewInputType.JComboBoxString, CardComponentProperties.Vertical));
		textPanel.add(CardComponentProperties.Vertical, txtVertField);
		
		isNameLabel = new JLabel("Is Name Field:");
		isNameLabel.setFont(Constants.DEFAULT_FONT);
		textPanel.add(isNameLabel);
		
		isNameField = new JCheckBox();
		isNameField.setFont(Constants.DEFAULT_FONT);
		isNameField.addActionListener(new PropertyActionListener(ViewInputType.JCheckBox, CardComponentProperties.IsName));
		textPanel.add(CardComponentProperties.IsName, isNameField);
		
		txtEditLabel = new JLabel("Editable:");
		txtEditLabel.setFont(Constants.DEFAULT_FONT);
		textPanel.add(txtEditLabel);
		
		txtEditField = new JCheckBox();
		txtEditField.setFont(Constants.DEFAULT_FONT);
		txtEditField.addActionListener(new PropertyActionListener(ViewInputType.JCheckBox, CardComponentProperties.Editable));
		textPanel.add(CardComponentProperties.Editable, txtEditField);
	}

	private void initLinePanel(){
		linePanel = new JIdMapPanel();
		linePanel.setLayout(new MigLayout("wrap 2","[][grow,fill]",""));
		
		lineMetaLabel = new JLabel("Component Type:");
		lineMetaLabel.setFont(Constants.DEFAULT_FONT);
		linePanel.add(lineMetaLabel);
		
		lineMetaValue = new JLabel("Line");
		lineMetaValue.setFont(Constants.DEFAULT_FONT);
		linePanel.add(lineMetaValue);
		
		lineNameLabel = new JLabel("Name:");
		lineNameLabel.setFont(Constants.DEFAULT_FONT);
		linePanel.add(lineNameLabel);
		
		lineNameField = new JSuperTextArea();
		lineNameField.setFont(Constants.DEFAULT_FONT);
		lineNameField.addKeyListener(new PropertyKeyListener(ViewInputType.JTextArea, CardComponentProperties.Name, true));
		lineNameField.disableKeyBindings();
		linePanel.add(CardComponentProperties.Name, lineNameField);
		
		lineX1Label = new JLabel("Start X:");
		lineX1Label.setFont(Constants.DEFAULT_FONT);
		linePanel.add(lineX1Label);
		
		lineX1Field = new JIntegerTextArea();
		lineX1Field.setFont(Constants.DEFAULT_FONT);
		lineX1Field.setPrevious(lineNameField);
		lineNameField.setNext(lineX1Field);
		lineX1Field.addKeyListener(new PropertyKeyListener(ViewInputType.JIntegerTextArea, CardComponentProperties.StartX, false));
		lineX1Field.addFocusListener(new PropertyFocusListener(ViewInputType.JIntegerTextArea, CardComponentProperties.StartX));
		linePanel.add(CardComponentProperties.StartX, lineX1Field);
		
		lineY1Label = new JLabel("Start Y:");
		lineY1Label.setFont(Constants.DEFAULT_FONT);
		linePanel.add(lineY1Label);
		
		lineY1Field = new JIntegerTextArea();
		lineY1Field.setFont(Constants.DEFAULT_FONT);
		lineY1Field.setPrevious(lineX1Field);
		lineX1Field.setNext(lineY1Field);
		lineY1Field.addKeyListener(new PropertyKeyListener(ViewInputType.JIntegerTextArea, CardComponentProperties.StartY, false));
		lineY1Field.addFocusListener(new PropertyFocusListener(ViewInputType.JIntegerTextArea, CardComponentProperties.StartY));
		linePanel.add(CardComponentProperties.StartY, lineY1Field);
		
		lineX2Label = new JLabel("End X:");
		lineX2Label.setFont(Constants.DEFAULT_FONT);
		linePanel.add(lineX2Label);
		
		lineX2Field = new JIntegerTextArea();
		lineX2Field.setFont(Constants.DEFAULT_FONT);
		lineX2Field.setPrevious(lineY1Field);
		lineY1Field.setNext(lineX2Field);
		lineX2Field.addKeyListener(new PropertyKeyListener(ViewInputType.JIntegerTextArea, CardComponentProperties.EndX, true));
		lineX2Field.addFocusListener(new PropertyFocusListener(ViewInputType.JIntegerTextArea, CardComponentProperties.EndX));
		linePanel.add(CardComponentProperties.EndX, lineX2Field);
		
		lineY2Label = new JLabel("End Y:");
		lineY2Label.setFont(Constants.DEFAULT_FONT);
		linePanel.add(lineY2Label);
		
		lineY2Field = new JIntegerTextArea();
		lineY2Field.setFont(Constants.DEFAULT_FONT);
		lineY2Field.setPrevious(lineX2Field);
		lineX2Field.setNext(lineY2Field);
		lineY2Field.addKeyListener(new PropertyKeyListener(ViewInputType.JIntegerTextArea, CardComponentProperties.EndY, false));
		lineY2Field.addFocusListener(new PropertyFocusListener(ViewInputType.JIntegerTextArea, CardComponentProperties.EndY));
		linePanel.add(CardComponentProperties.EndY, lineY2Field);
		
		lineColorLabel = new JLabel("Color:");
		lineColorLabel.setFont(Constants.DEFAULT_FONT);
		linePanel.add(lineColorLabel);
		
		lineColorField = new JSuperColorChooser();
		lineColorField.setFont(Constants.DEFAULT_FONT);
		lineColorField.addActionListener(new PropertyActionListener(ViewInputType.JSuperColorChooser, CardComponentProperties.Color));
		linePanel.add(CardComponentProperties.Color, lineColorField);
		
		lineEditLabel = new JLabel("Editable:");
		lineEditLabel.setFont(Constants.DEFAULT_FONT);
		linePanel.add(lineEditLabel);
		
		lineEditField = new JCheckBox();
		lineEditField.setFont(Constants.DEFAULT_FONT);
		lineEditField.addActionListener(new PropertyActionListener(ViewInputType.JCheckBox, CardComponentProperties.Editable));
		linePanel.add(CardComponentProperties.Editable, lineEditField);
	}

	private void initRectPanel(){
		rectPanel = new JIdMapPanel();
		rectPanel.setLayout(new MigLayout("wrap 2","[][grow,fill]",""));
		
		rectMetaLabel = new JLabel("Component Type:");
		rectMetaLabel.setFont(Constants.DEFAULT_FONT);
		rectPanel.add(rectMetaLabel);
		
		rectMetaValue = new JLabel("Rectangle");
		rectMetaValue.setFont(Constants.DEFAULT_FONT);
		rectPanel.add(rectMetaValue);
		
		rectNameLabel = new JLabel("Name:");
		rectNameLabel.setFont(Constants.DEFAULT_FONT);
		rectPanel.add(rectNameLabel);
		
		rectNameField = new JSuperTextArea();
		rectNameField.setFont(Constants.DEFAULT_FONT);
		rectNameField.addKeyListener(new PropertyKeyListener(ViewInputType.JTextArea, CardComponentProperties.Name, true));
		rectNameField.disableKeyBindings();
		rectPanel.add(CardComponentProperties.Name, rectNameField);
		
		rectXLabel = new JLabel("X:");
		rectXLabel.setFont(Constants.DEFAULT_FONT);
		rectPanel.add(rectXLabel);
		
		rectXField = new JIntegerTextArea();
		rectXField.setFont(Constants.DEFAULT_FONT);
		rectXField.addKeyListener(new PropertyKeyListener(ViewInputType.JIntegerTextArea, CardComponentProperties.X, false));
		rectXField.addFocusListener(new PropertyFocusListener(ViewInputType.JIntegerTextArea, CardComponentProperties.X));
		rectPanel.add(CardComponentProperties.X, rectXField);
		
		rectYLabel = new JLabel("Y:");
		rectYLabel.setFont(Constants.DEFAULT_FONT);
		rectPanel.add(rectYLabel);
		
		rectYField = new JIntegerTextArea();
		rectYField.setFont(Constants.DEFAULT_FONT);
		rectYField.addKeyListener(new PropertyKeyListener(ViewInputType.JIntegerTextArea, CardComponentProperties.Y, false));
		rectYField.addFocusListener(new PropertyFocusListener(ViewInputType.JIntegerTextArea, CardComponentProperties.Y));
		rectPanel.add(CardComponentProperties.Y, rectYField);
		
		rectWidthLabel = new JLabel("Width:");
		rectWidthLabel.setFont(Constants.DEFAULT_FONT);
		rectPanel.add(rectWidthLabel);
		
		rectWidthField = new JIntegerTextArea();
		rectWidthField.setFont(Constants.DEFAULT_FONT);
		rectWidthField.addKeyListener(new PropertyKeyListener(ViewInputType.JIntegerTextArea, CardComponentProperties.Width, false));
		rectWidthField.addFocusListener(new PropertyFocusListener(ViewInputType.JIntegerTextArea, CardComponentProperties.Width));
		rectPanel.add(CardComponentProperties.Width, rectWidthField);
		
		rectHeightLabel = new JLabel("Height:");
		rectHeightLabel.setFont(Constants.DEFAULT_FONT);
		rectPanel.add(rectHeightLabel);
		
		rectHeightField = new JIntegerTextArea();
		rectHeightField.setFont(Constants.DEFAULT_FONT);
		rectHeightField.addKeyListener(new PropertyKeyListener(ViewInputType.JIntegerTextArea, CardComponentProperties.Height, true));
		rectHeightField.addFocusListener(new PropertyFocusListener(ViewInputType.JIntegerTextArea, CardComponentProperties.Height));
		rectPanel.add(CardComponentProperties.Height, rectHeightField);
		
		rectFillLabel = new JLabel("Fill Color:");
		rectFillLabel.setFont(Constants.DEFAULT_FONT);
		rectPanel.add(rectFillLabel);
		
		rectFillField = new JSuperColorChooser();
		rectFillField.setFont(Constants.DEFAULT_FONT);
		rectFillField.addActionListener(new PropertyActionListener(ViewInputType.JSuperColorChooser, CardComponentProperties.FillColor));
		rectPanel.add(CardComponentProperties.FillColor, rectFillField);
		
		rectOutlineLabel = new JLabel("Outline Color:");
		rectOutlineLabel.setFont(Constants.DEFAULT_FONT);
		rectPanel.add(rectOutlineLabel);
		
		rectOutlineField = new JSuperColorChooser();
		rectOutlineField.setFont(Constants.DEFAULT_FONT);
		rectOutlineField.addActionListener(new PropertyActionListener(ViewInputType.JSuperColorChooser, CardComponentProperties.OutlineColor));
		rectPanel.add(CardComponentProperties.OutlineColor, rectOutlineField);
		
		rectEditLabel = new JLabel("Editable:");
		rectEditLabel.setFont(Constants.DEFAULT_FONT);
		rectPanel.add(rectEditLabel);
		
		rectEditField = new JCheckBox();
		rectEditField.setFont(Constants.DEFAULT_FONT);
		rectEditField.addActionListener(new PropertyActionListener(ViewInputType.JCheckBox, CardComponentProperties.Editable));
		rectPanel.add(CardComponentProperties.Editable, rectEditField);
	}

	private void initImagePanel(){
		imagePanel = new JIdMapPanel();
		imagePanel.setLayout(new MigLayout("wrap 2","[][grow,fill]",""));
		
		imgMetaLabel = new JLabel("Component Type:");
		imgMetaLabel.setFont(Constants.DEFAULT_FONT);
		imagePanel.add(imgMetaLabel);
		
		imgMetaValue = new JLabel("Image");
		imgMetaValue.setFont(Constants.DEFAULT_FONT);
		imagePanel.add(imgMetaValue);
		
		imgNameLabel = new JLabel("Name:");
		imgNameLabel.setFont(Constants.DEFAULT_FONT);
		imagePanel.add(imgNameLabel);
		
		imgNameField = new JSuperTextArea();
		imgNameField.setFont(Constants.DEFAULT_FONT);
		imgNameField.addKeyListener(new PropertyKeyListener(ViewInputType.JTextArea, CardComponentProperties.Name, true));
		imgNameField.disableKeyBindings();
		imagePanel.add(CardComponentProperties.Name, imgNameField);
		
		imgXLabel = new JLabel("X:");
		imgXLabel.setFont(Constants.DEFAULT_FONT);
		imagePanel.add(imgXLabel);
		
		imgXField = new JIntegerTextArea();
		imgXField.setFont(Constants.DEFAULT_FONT);
		imgXField.addKeyListener(new PropertyKeyListener(ViewInputType.JIntegerTextArea, CardComponentProperties.X, false));
		imgXField.addFocusListener(new PropertyFocusListener(ViewInputType.JIntegerTextArea, CardComponentProperties.X));
		imagePanel.add(CardComponentProperties.X, imgXField);
		
		imgYLabel = new JLabel("Y:");
		imgYLabel.setFont(Constants.DEFAULT_FONT);
		imagePanel.add(imgYLabel);
		
		imgYField = new JIntegerTextArea();
		imgYField.setFont(Constants.DEFAULT_FONT);
		imgYField.addKeyListener(new PropertyKeyListener(ViewInputType.JIntegerTextArea, CardComponentProperties.Y, false));
		imgYField.addFocusListener(new PropertyFocusListener(ViewInputType.JIntegerTextArea, CardComponentProperties.Y));
		imagePanel.add(CardComponentProperties.Y, imgYField);
		
		imgWidthLabel = new JLabel("Width:");
		imgWidthLabel.setFont(Constants.DEFAULT_FONT);
		imagePanel.add(imgWidthLabel);
		
		imgWidthField = new JIntegerTextArea();
		imgWidthField.setFont(Constants.DEFAULT_FONT);
		imgWidthField.addKeyListener(new PropertyKeyListener(ViewInputType.JIntegerTextArea, CardComponentProperties.Width, false));
		imgWidthField.addFocusListener(new PropertyFocusListener(ViewInputType.JIntegerTextArea, CardComponentProperties.Width));
		imagePanel.add(CardComponentProperties.Width, imgWidthField);
		
		imgHeightLabel = new JLabel("Height:");
		imgHeightLabel.setFont(Constants.DEFAULT_FONT);
		imagePanel.add(imgHeightLabel);
		
		imgHeightField = new JIntegerTextArea();
		imgHeightField.setFont(Constants.DEFAULT_FONT);
		imgHeightField.addKeyListener(new PropertyKeyListener(ViewInputType.JIntegerTextArea, CardComponentProperties.Height, false));
		imgHeightField.addFocusListener(new PropertyFocusListener(ViewInputType.JIntegerTextArea, CardComponentProperties.Height));
		imagePanel.add(CardComponentProperties.Height, imgHeightField);
		
		imgImageLabel = new JLabel("Image:");
		imgImageLabel.setFont(Constants.DEFAULT_FONT);
		imagePanel.add(imgImageLabel);
		
		imgImageField = new JSuperImageChooser();
		imgImageField.setFont(Constants.DEFAULT_FONT);
		imgImageField.addImageListener(new PropertyActionListener(ViewInputType.JSuperImageChooser, CardComponentProperties.Image));
		imagePanel.add(CardComponentProperties.Image, imgImageField);
		
		imgEditLabel = new JLabel("Editable:");
		imgEditLabel.setFont(Constants.DEFAULT_FONT);
		imagePanel.add(imgEditLabel);
		
		imgEditField = new JCheckBox();
		imgEditField.setFont(Constants.DEFAULT_FONT);
		imgEditField.addActionListener(new PropertyActionListener(ViewInputType.JCheckBox, CardComponentProperties.Editable));
		imagePanel.add(CardComponentProperties.Editable, imgEditField);
	}
	
	private void initTempPanel(){
		tempNameLabel = new JLabel("Template Name:");
		tempNameLabel.setFont(Constants.DEFAULT_FONT);
		tempPanel.add(tempNameLabel);
		
		tempNameField = new JSuperTextArea();
		tempNameField.setFont(Constants.DEFAULT_FONT);
		tempNameField.addKeyListener(new TempNameKeyListener());
		tempPanel.add(tempNameField);

		tempWidthLabel = new JLabel("Template Width:");
		tempWidthLabel.setFont(Constants.DEFAULT_FONT);
		tempPanel.add(tempWidthLabel);
		
		tempWidthField = new JIntegerTextArea();
		tempWidthField.setText(Constants.DEFAULT_TEMPLATE_WIDTH);
		tempWidthField.setFont(Constants.DEFAULT_FONT);
		tempWidthField.addKeyListener(new TempWidthKeyListener());
		tempWidthField.addFocusListener(new TempWidthFocusListener());
		tempPanel.add(tempWidthField);

		tempHeightLabel = new JLabel("Template Height:");
		tempHeightLabel.setFont(Constants.DEFAULT_FONT);
		tempPanel.add(tempHeightLabel);
		
		tempHeightField = new JIntegerTextArea();
		tempHeightField.setText(Constants.DEFAULT_TEMPLATE_HEIGHT);
		tempHeightField.setFont(Constants.DEFAULT_FONT);
		tempHeightField.addKeyListener(new TempHeightKeyListener());
		tempHeightField.addFocusListener(new TempHeightFocusListener());
		tempPanel.add(tempHeightField);

		rotateLabel = new JLabel("Print Rotation:");
		rotateLabel.setFont(Constants.DEFAULT_FONT);
		tempPanel.add(rotateLabel);
		
		rotateField = new JComboBox<String>(Constants.ROTATION_LIST);
		rotateField.setFont(Constants.DEFAULT_FONT);
		rotateField.addActionListener(new TempRotationListener());
		tempPanel.add(rotateField);
	}
	
	/******************************** vvv EVENT LISTENERS vvv ********************************/
	
	private class PropertyKeyListener implements KeyListener {

		private ViewInputType type;
		private String property;
		private boolean alwaysUpdate;
		
		public PropertyKeyListener(ViewInputType type, String property, boolean alwaysUpdate){
			this.type = type;
			this.property = property;
			this.alwaysUpdate = alwaysUpdate;
		}
		
		private Object getInput(Component comp){
			switch(type){
				case JTextArea:
					return ((JSuperTextArea)comp).getText();
				case JIntegerTextArea:
					return ((JIntegerTextArea)comp).getIntegerText();
				default:
					return null;
			}
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			JSuperTextArea component = ((JSuperTextArea)e.getComponent());
			if(!alwaysUpdate && (e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB)){
				controller.updateComponentProperty(property, getInput(e.getComponent()));
			}
			if(e.getKeyCode() == KeyEvent.VK_ENTER || 
						   e.getKeyCode() == KeyEvent.VK_TAB || 
						   e.getKeyCode() == KeyEvent.VK_DOWN){
				// go down a field
				if(component.getNext() != null) component.getNext().requestFocus();
				e.consume();
			}
			if(e.getKeyCode() == KeyEvent.VK_UP){
				// go up a field
				if(component.getPrevious() != null) component.getPrevious().requestFocus();
				e.consume();
			}
		}
		@Override
		public void keyReleased(KeyEvent e) {
			if(alwaysUpdate) controller.updateComponentProperty(property, getInput(e.getComponent()));
		}
		@Override
		public void keyTyped(KeyEvent e) {
		}
	}
	
	private class PropertyFocusListener implements FocusListener {

		private ViewInputType type;
		private String property;
		
		public PropertyFocusListener(ViewInputType type, String property){
			this.type = type;
			this.property = property;
		}
		
		private Object getInput(Component comp){
			switch(type){
				case JTextArea:
					return ((JSuperTextArea)comp).getText();
				case JIntegerTextArea:
					return ((JIntegerTextArea)comp).getIntegerText();
				default:
					return null;
			}
		}
		
		@Override
		public void focusGained(FocusEvent e) {
		}
		@Override
		public void focusLost(FocusEvent e) {
			// Make sure this component is still there
			if(e.getComponent().getParent() == null) return;
			if(e.getComponent().getParent().getParent() == null) return;
			controller.updateComponentProperty(property, getInput(e.getComponent()));
		}
	}
	
	private class PropertyActionListener implements ActionListener {

		private ViewInputType type;
		private String property;
		
		public PropertyActionListener(ViewInputType type, String property){
			this.type = type;
			this.property = property;
		}
		
		@SuppressWarnings("unchecked")
		private Object getInput(Object comp){
			switch(type){
				case JComboBoxString:
					int index = ((JComboBox<String>)comp).getSelectedIndex();
					Object output = index;
					if(CardComponentProperties.FontStyle.equals(property)){
						output = Constants.FONT_STYLE_LIST[index];
					}
					return output;
				case JCheckBox:
					return ((JCheckBox)comp).isSelected();
				case JSuperColorChooser:
					return ((JSuperColorChooser)comp).getColor();
				case JSuperImageChooser:
					return ((JSuperImageChooser)comp).getImage();
				default:
					return null;
			}
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			controller.updateComponentProperty(property, getInput(e.getSource()));
		}
	}
	
	private class TempRotationListener implements ActionListener {
		@Override public void actionPerformed(ActionEvent e) {
			controller.updateRotation(rotateField.getSelectedIndex());
		}
	}
	
	private class TempNameKeyListener implements KeyListener {
		@Override
		public void keyPressed(KeyEvent e) {
		}
		@Override
		public void keyReleased(KeyEvent e) {
			controller.updateTemplateName(tempNameField.getText());
		}
		@Override
		public void keyTyped(KeyEvent e) {
		}
	}
	
	private class TempWidthKeyListener implements KeyListener {
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB){
				controller.updateTemplateWidth(tempWidthField.getIntegerText());
				return;
			}
		}
		@Override
		public void keyReleased(KeyEvent e) {
		}
		@Override
		public void keyTyped(KeyEvent e) {
		}
	}
	
	private class TempWidthFocusListener implements FocusListener {
		@Override
		public void focusGained(FocusEvent e) {
		}
		@Override
		public void focusLost(FocusEvent e) {
			// Make sure this component is still there
			if(e.getComponent().getParent() == null) return;
			if(e.getComponent().getParent().getParent() == null) return;
			controller.updateTemplateWidth(tempWidthField.getIntegerText());
		}
	}
	
	private class TempHeightKeyListener implements KeyListener {
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB){
				controller.updateTemplateHeight(tempHeightField.getIntegerText());
				return;
			}
		}
		@Override
		public void keyReleased(KeyEvent e) {
		}
		@Override
		public void keyTyped(KeyEvent e) {
		}
	}
	
	private class TempHeightFocusListener implements FocusListener {
		@Override
		public void focusGained(FocusEvent e) {
		}
		@Override
		public void focusLost(FocusEvent e) {
			// Make sure this component is still there
			if(e.getComponent().getParent() == null) return;
			if(e.getComponent().getParent().getParent() == null) return;
			controller.updateTemplateHeight(tempHeightField.getIntegerText());
		}
	}
	
	/******************************** ^^^ EVENT LISTENERS ^^^ ********************************/
	
	public void emptyComponentProperties(){
		compPanel.removeAll();
		compPanel.revalidate();
		compPanel.repaint();
	}
	
	public void switchToText(){
		switchToPanel(textPanel);
	}
	
	public void switchToLine(){
		switchToPanel(linePanel);
	}
	
	public void switchToRect(){
		switchToPanel(rectPanel);
	}
	
	public void switchToImage(){
		switchToPanel(imagePanel);
	}
	
	public void switchToPanel(JPanel panel){
		if(panel.getParent() != null && panel.getParent().equals(compPanel)) return;
		compPanel.removeAll();
		compPanel.add(panel, BorderLayout.CENTER);
		compPanel.revalidate();
		compPanel.repaint();
	}
	
	public void selectField(String id){
		Component tmp = ((JIdMapPanel)compPanel.getComponent(0)).getComponentFromId(id);
		if(tmp == null) return;
		tmp.requestFocus();
	}
	
	@SuppressWarnings("unchecked")
	public void setField(String datatype, String id, Object data){
		if(datatype.equals(Constants.STRING_DATA_TYPE)){
			JSuperTextArea tmp = (JSuperTextArea)((JIdMapPanel)compPanel.getComponent(0)).getComponentFromId(id);
			if(tmp == null) return;
			tmp.setText(data.toString());
			return;
		}
		if(datatype.equals(Constants.INTEGER_DATA_TYPE)){
			JIntegerTextArea tmp = (JIntegerTextArea)((JIdMapPanel)compPanel.getComponent(0)).getComponentFromId(id);
			if(tmp == null) return;
			tmp.setText((Integer)data);
			return;
		}
		if(datatype.equals(Constants.COMBO_DATA_TYPE)){
			JComboBox<String> tmp = (JComboBox<String>)((JIdMapPanel)compPanel.getComponent(0)).getComponentFromId(id);
			if(tmp == null) return;
			tmp.setSelectedIndex((Integer)data);
			return;
		}
		if(datatype.equals(Constants.BOOL_DATA_TYPE)){
			JCheckBox tmp = (JCheckBox)((JIdMapPanel)compPanel.getComponent(0)).getComponentFromId(id);
			if(tmp == null) return;
			tmp.setSelected((boolean)data);
			return;
		}
		if(datatype.equals(Constants.COLOR_DATA_TYPE)){
			JSuperColorChooser tmp = (JSuperColorChooser)((JIdMapPanel)compPanel.getComponent(0)).getComponentFromId(id);
			if(tmp == null) return;
			tmp.setColor((Color)data);
			return;
		}
	}
	
	public void selectTemplateName(){
		tempNameField.requestFocus();
	}
	
	public void setTemplateName(String tName){
		tempNameField.setText(tName);
	}
	
	public void setTemplateRotation(int rotation){
		rotateField.setSelectedIndex(rotation);
	}
}