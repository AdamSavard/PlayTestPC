package templatemaker;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import constants.Constants;
import net.miginfocom.swing.MigLayout;
import view.components.CardDisplayPanel;

/**
 * Form used to build the card template with WYSIWYG editor
 * 
 * @author Adam
 *
 */
public class CardTemplateView extends JDialog {

	private static final long serialVersionUID = 1L;

	public static Dimension backBuffer = new Dimension(900, 600);
	
	public static final String[] stylesList = {"Left", "Right", "Multi"};
	
	private CardTemplateController controller;
	
	/*
	 * SplitPane containing editPanel and dispPanel
	 */
	private JSplitPane spEditDisp;
	
	/*
	 * Panel containing toolPanel and propTabs
	 */
	private JPanel editPanel;
	
	/*
	 * Buttons panel for toggling modes
	 */
	private TemplateMakerModePanel toolPanel;
	
	/*
	 * Tabs with component properties and template properties
	 * This includes the panels with all the labels and fields
	 */
	private TemplateMakerPropertiesPanel propTabs;
	
	/*
	 *  Panel to display example of the card
	 */
	private CardDisplayPanel dispPanel;
	
	/*
	 * Panel with buttons to save/close the window
	 */
	private TemplateMakerExitPanel exitPanel;
	
	/******************************** vvv INITIALIZATION vvv ********************************/
	
	public CardTemplateView(CardTemplateController iController, JFrame parent){
		super(parent, true);
		controller = iController;
		addWindowListener(new ViewWindowListener());
		init();
	}
	
	private void init(){
		this.getContentPane().setPreferredSize(backBuffer);
    	this.getContentPane().setMinimumSize(backBuffer);
    	this.pack();
    	this.setLayout(new MigLayout("wrap 1, insets 0 0 0 0","[grow,fill]","[grow,fill][40!]"));
        this.setLocationByPlatform(true);
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		editPanel = new JPanel();
		editPanel.setLayout(new MigLayout("wrap 1, insets 0 0 0 0","[grow,fill]","[][grow,fill]"));
		editPanel.setBackground(Color.lightGray);
		
		dispPanel = new CardDisplayPanel();
		dispPanel.addMouseListener(new DisplayMouseListener());
		dispPanel.addMouseMotionListener(new DisplayMouseMotionListener());
		dispPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(Constants.DELETE_KEYSTROKE, Constants.DELETE_ACTION);
		dispPanel.getActionMap().put(Constants.DELETE_ACTION, new DeleteAction());
		dispPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(Constants.COPY_KEYSTROKE, Constants.COPY_ACTION);
		dispPanel.getActionMap().put(Constants.COPY_ACTION, new CopyAction());
		dispPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(Constants.CUT_KEYSTROKE, Constants.CUT_ACTION);
		dispPanel.getActionMap().put(Constants.CUT_ACTION, new CutAction());
		dispPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(Constants.PASTE_KEYSTROKE, Constants.PASTE_ACTION);
		dispPanel.getActionMap().put(Constants.PASTE_ACTION, new PasteAction());
		dispPanel.setBackground(Color.white);
		
		// Split pane with main edit panel and card display
		spEditDisp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, editPanel, dispPanel);
		spEditDisp.setOneTouchExpandable(true);
		spEditDisp.setDividerLocation(290);
		this.add(spEditDisp);
		
		exitPanel = new TemplateMakerExitPanel(controller);
		this.add(exitPanel);
		
		toolPanel = new TemplateMakerModePanel(controller);
		editPanel.add(toolPanel);
		
		propTabs = new TemplateMakerPropertiesPanel(controller);
		editPanel.add(propTabs);
	}
	
	/******************************** ^^^ INITIALIZATION ^^^ ********************************/
	
	/******************************** vvv EVENT LISTENERS vvv ********************************/
	
	private class ViewWindowListener implements WindowListener {
		@Override
		public void windowActivated(WindowEvent arg0) {
		}
		@Override
		public void windowClosed(WindowEvent arg0) {
		}
		@Override
		public void windowClosing(WindowEvent arg0) {
		}
		@Override
		public void windowDeactivated(WindowEvent arg0) {
		}
		@Override
		public void windowDeiconified(WindowEvent arg0) {
		}
		@Override
		public void windowIconified(WindowEvent arg0) {
		}
		@Override
		public void windowOpened(WindowEvent arg0) {
			controller.onViewOpened();
		}
	}
	
	private class DisplayMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			controller.drawMouseClicked(arg0.getX(), arg0.getY(), dispPanel.getWidth(), dispPanel.getHeight());
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			controller.drawMousePressed(arg0.getX(), arg0.getY(), dispPanel.getWidth(), dispPanel.getHeight());
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			controller.drawMouseReleased(arg0.getX(), arg0.getY(), dispPanel.getWidth(), dispPanel.getHeight());
		}
		
	}
	
	private class DisplayMouseMotionListener implements MouseMotionListener {
		@Override
		public void mouseDragged(MouseEvent arg0) {
			controller.drawMouseDragged(arg0.getX(), arg0.getY(), dispPanel.getWidth(), dispPanel.getHeight());
		}
		@Override
		public void mouseMoved(MouseEvent arg0) {
		}
	}
	
	private class DeleteAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		@Override
		public void actionPerformed(ActionEvent arg0) {
			controller.deleteSelectedComponent();
		}
	}
	
	private class CopyAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		@Override
		public void actionPerformed(ActionEvent arg0) {
			controller.copySelectedComponent();
		}
	}
	
	private class CutAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		@Override
		public void actionPerformed(ActionEvent arg0) {
			controller.cutSelectedComponent();
		}
	}
	
	private class PasteAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		@Override
		public void actionPerformed(ActionEvent arg0) {
			controller.pasteComponent();
		}
	}
	
	/******************************** ^^^ EVENT LISTENERS ^^^ ********************************/
	
	public void selectTemplateName(){
		propTabs.setSelectedIndex(1);
		propTabs.selectTemplateName();
	}
	
	public void displayMessage(String message){
		exitPanel.displayMessage(message);
	}
	
	public void emptyComponentProperties(){
		propTabs.emptyComponentProperties();
		pack();
	}
	
	public void switchToText(){
		propTabs.switchToText();
		pack();
	}
	
	public void switchToLine(){
		propTabs.switchToLine();
		pack();
	}
	
	public void switchToRect(){
		propTabs.switchToRect();
		pack();
	}
	
	public void switchToImage(){
		propTabs.switchToImage();
		pack();
	}
	
	public void selectField(String id){
		propTabs.setSelectedIndex(0);
		propTabs.selectField(id);
	}
	
	public void setField(String datatype, String id, Object data){
		propTabs.setField(datatype, id, data);
	}
	
	public void updateTemplateImage(BufferedImage img){
		dispPanel.updateDisp(img);
	}
	
	public int getDispPanelWidth(){
		return dispPanel.getWidth();
	}
	
	public int getDispPanelHeight(){
		return dispPanel.getHeight();
	}
	
	public void startMouseDrag(int x, int y){
		dispPanel.setStartPoint(x, y);
	}
	
	public void continueMouseDrag(int x, int y){
		dispPanel.setEndPoint(x, y);
		dispPanel.repaint();
	}
	
	public void stopMouseDrag(int x, int y){
		dispPanel.clearStartEndPoint();
		dispPanel.repaint();
	}
	
	public void disableButton(int index){
		toolPanel.disableButton(index);
	}
	
	public void setTemplateName(String tName){
		propTabs.setTemplateName(tName);
	}
	
	public void setTemplateRotation(int rotation){
		propTabs.setTemplateRotation(rotation);
	}
}
