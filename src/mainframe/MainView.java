package mainframe;

import io.DAOFactory;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.miginfocom.swing.MigLayout;

import org.pushingpixels.flamingo.api.common.JCommandButton;
import org.pushingpixels.flamingo.api.ribbon.JRibbonBand;
import org.pushingpixels.flamingo.api.ribbon.JRibbonFrame;
import org.pushingpixels.flamingo.api.ribbon.RibbonElementPriority;
import org.pushingpixels.flamingo.api.ribbon.RibbonTask;
import org.pushingpixels.flamingo.api.ribbon.resize.CoreRibbonResizePolicies;
import org.pushingpixels.flamingo.api.ribbon.resize.IconRibbonBandResizePolicy;
import org.pushingpixels.flamingo.internal.ui.ribbon.BasicRibbonUI;

import cards.components.CardComponent;
import view.components.CardDisplayPanel;
import view.components.JSearchBarTextField;
import view.components.JSuperTextArea;
import view.components.MultiCardDisplayPanel;
import view.components.choosers.JAbstractSuperChooser;
import view.components.choosers.JSuperChooserList;
import view.components.choosers.JSuperTextChooser;
import view.components.choosers.SuperChooserFactory;
import view.misc.TabControl;
import view.misc.table.CardTableModel;
import constants.Constants;

// Old main GUI
public class MainView extends JRibbonFrame {

	public static final String CARD_EDITOR_LIST = "cards";
	public static final String SUBSETS_LIST = "subsets";
	
	private static final long serialVersionUID = 1L;
	
	private MainController controller;
	
	// Ribbon at the top of the screen
	//RibbonApplicationMenu ribbonMenu;
	//RibbonApplicationMenuEntryPrimary newButton;
	RibbonTask fileTask, setManagerTask, helpTask;
	JRibbonBand ioBand, clipboardBand, tableBand, aboutBand;
	JCommandButton newButton, openButton, saveButton, saveAsButton, printButton, exportButton, pasteButton, copyButton, cutButton;
	JCommandButton bulkEditButton, aboutButton;
	
	// Tabs
	private JTabbedPane tabs;
	private JPanel cardsTab, templatesTab, spoilerTab, subsetsTab;
	
	// CARDS TAB
	private JSplitPane spImgElse, spTableData;
	
	private CardDisplayPanel cardsDispPanel; // Panel to display example of the card
	
	private JPanel listPanel;// panel displaying card table and search
	private JButton addButton, removeButton;
	private JSearchBarTextField searchBar;
	private Map<String, JTable> cardListTableMap;
	private Map<String, CardTableModel> cardListTableModelMap;
	private JScrollPane listScroll;

	private JPanel cardPanel; // panel displaying card components
	private JScrollPane rtScroll;
	private JLabel templateLabel, templateNameLabel;
	private JLabel[] compLabels; // card component text labels
	private JSuperChooserList dataComps; // components for card component values
	private JLabel commentLabel;
	private JSuperTextChooser commentText;
	
	// TEMPLATES TAB
	private JSplitPane spDispTemplates;
	
	private CardDisplayPanel tempDispPanel;
	
	private JPanel templatesPanel;
	
	private JScrollPane templateVisualScroll;
	private MultiCardDisplayPanel templateVisualPanel;
	
	private JPanel templateBottomPanel;
	private JButton newTemplateButton;
	
	// SPOILER TAB
	private JScrollPane spoilerScroll; 
	private MultiCardDisplayPanel spoilerPanel; 
	
	// SUBSETS TAB TODO
	private JSplitPane spDispSubsets;
	
	private CardDisplayPanel subsetDispPanel;

	private JSplitPane spSubsetDetails;
	
	private JPanel subsetDetailsPanel;
	
	private JScrollPane subsetTableScroll;

	/******************************** vvv INITIALIZATION vvv ********************************/
	
	public MainView(MainController iController){
		super();
		controller = iController;
		init();
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new MainDispatcher());
	}
	
	private void init(){
        setLocationByPlatform(true);
		setResizable(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new MainViewWindowListener());
		setIconImage(DAOFactory.getImageDAO().read("PlayTest.png"));
		
		cardListTableMap = new HashMap<String, JTable>();
		cardListTableModelMap = new HashMap<String, CardTableModel>();
		
		initRibbon();
		initTabs();
		initCardTab();
		initTemplatesTab();
		initSpoilerTab();
		initSubsetsTab();

    	pack();
    	setExtendedState(getExtendedState()|JFrame.MAXIMIZED_BOTH);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void initRibbon(){
		/* TODO RIBBON
		 */
		
		//ribbonMenu = new RibbonApplicationMenu();
		
		//newButton = new RibbonApplicationMenuEntryPrimary(FileHandler.getResizableIcon("New.png"), "New", new NewClicked(), CommandButtonKind.ACTION_ONLY);
		
		//ribbonMenu.addMenuEntry(newButton);
		
		//getRibbon().setApplicationIcon(FileHandler.getResizableIcon("PlayTest.png"));
		//getRibbon().setApplicationMenu(ribbonMenu);

		// This stops the taskbar from drawing as revealed by BasicRibbonUI.java
		getRibbon().putClientProperty(BasicRibbonUI.IS_USING_TITLE_PANE, true);
		
		ioBand = new JRibbonBand("File", null);
		ioBand.setResizePolicies((List) Arrays.asList(new IconRibbonBandResizePolicy(ioBand.getControlPanel())));

		newButton = new JCommandButton("New Set", DAOFactory.getImageDAO().readResizableIcon("New.png"));
		newButton.addActionListener(new NewClicked());
		ioBand.addCommandButton(newButton, RibbonElementPriority.TOP);
		
		openButton = new JCommandButton("Open Set", DAOFactory.getImageDAO().readResizableIcon("Open.png"));
		openButton.addActionListener(new OpenClicked());
		ioBand.addCommandButton(openButton, RibbonElementPriority.MEDIUM);
		
		saveButton = new JCommandButton("Save Set", DAOFactory.getImageDAO().readResizableIcon("Save.png"));
		saveButton.addActionListener(new SaveClicked());
		ioBand.addCommandButton(saveButton, RibbonElementPriority.MEDIUM);
		
		saveAsButton = new JCommandButton("Save As", DAOFactory.getImageDAO().readResizableIcon("SaveAs.png"));
		saveAsButton.addActionListener(new SaveAsClicked());
		ioBand.addCommandButton(saveAsButton, RibbonElementPriority.MEDIUM);
		
		printButton = new JCommandButton("Print Set", DAOFactory.getImageDAO().readResizableIcon("Export.png"));
		printButton.addActionListener(new PrintClicked());
		ioBand.addCommandButton(printButton, RibbonElementPriority.MEDIUM);
		
		exportButton = new JCommandButton("Export Set", DAOFactory.getImageDAO().readResizableIcon("Excel.png"));
		exportButton.addActionListener(new ExportExcelClicked());
		ioBand.addCommandButton(exportButton, RibbonElementPriority.MEDIUM);
		
		ioBand.setResizePolicies((List) Arrays.asList(
				new CoreRibbonResizePolicies.None(ioBand.getControlPanel()),
				new CoreRibbonResizePolicies.Mirror(ioBand.getControlPanel()),
				new CoreRibbonResizePolicies.Mid2Low(ioBand.getControlPanel()),
				new CoreRibbonResizePolicies.High2Low(ioBand.getControlPanel()),
				new IconRibbonBandResizePolicy(ioBand.getControlPanel())));
		
		clipboardBand = new JRibbonBand("Clipboard", null);
		

		pasteButton = new JCommandButton("Paste", DAOFactory.getImageDAO().readResizableIcon("Paste.png"));
		pasteButton.addActionListener(new PasteClicked());
		clipboardBand.addCommandButton(pasteButton, RibbonElementPriority.TOP);
		
		copyButton = new JCommandButton("Copy", DAOFactory.getImageDAO().readResizableIcon("Copy.png"));
		copyButton.addActionListener(new CopyClicked());
		clipboardBand.addCommandButton(copyButton, RibbonElementPriority.MEDIUM);
		
		cutButton = new JCommandButton("Cut", DAOFactory.getImageDAO().readResizableIcon("Cut.png"));
		cutButton.addActionListener(new CutClicked());
		clipboardBand.addCommandButton(cutButton, RibbonElementPriority.MEDIUM);
		
		clipboardBand.setResizePolicies((List) Arrays.asList(
				new CoreRibbonResizePolicies.None(clipboardBand.getControlPanel()),
				new IconRibbonBandResizePolicy(clipboardBand.getControlPanel())));
		
		fileTask = new RibbonTask("File", ioBand, clipboardBand);
		getRibbon().addTask(fileTask);
		
		tableBand = new JRibbonBand("Table", null);
		tableBand.setResizePolicies((List) Arrays.asList(new IconRibbonBandResizePolicy(tableBand.getControlPanel())));
		
		bulkEditButton = new JCommandButton("Bulk Edit", DAOFactory.getImageDAO().readResizableIcon("BulkEdit.png"));
		bulkEditButton.addActionListener(new BulkEditClicked());
		tableBand.addCommandButton(bulkEditButton, RibbonElementPriority.TOP);
		
		tableBand.setResizePolicies((List) Arrays.asList(
				new CoreRibbonResizePolicies.None(tableBand.getControlPanel()),
				new CoreRibbonResizePolicies.Mirror(tableBand.getControlPanel()),
				new CoreRibbonResizePolicies.Mid2Low(tableBand.getControlPanel()),
				new IconRibbonBandResizePolicy(tableBand.getControlPanel())));
		
		setManagerTask = new RibbonTask("Set Management", tableBand);
		getRibbon().addTask(setManagerTask);
		
		aboutBand = new JRibbonBand("About", null);
		aboutBand.setResizePolicies((List) Arrays.asList(new IconRibbonBandResizePolicy(aboutBand.getControlPanel())));

		aboutButton = new JCommandButton("About PlayTest", DAOFactory.getImageDAO().readResizableIcon("About.png"));
		aboutButton.addActionListener(new AboutClicked());
		aboutBand.addCommandButton(aboutButton, RibbonElementPriority.TOP);
		
		aboutBand.setResizePolicies((List) Arrays.asList(
				new CoreRibbonResizePolicies.None(aboutBand.getControlPanel()),
				new CoreRibbonResizePolicies.Mirror(aboutBand.getControlPanel()),
				new CoreRibbonResizePolicies.Mid2Low(aboutBand.getControlPanel()),
				new IconRibbonBandResizePolicy(aboutBand.getControlPanel())));
		
		helpTask = new RibbonTask("Help", aboutBand);
		getRibbon().addTask(helpTask);
	}
	
	private void initTabs(){
		// tabs
		tabs = new JTabbedPane();
		cardsTab = new JPanel();
		cardsTab.setLayout(new BorderLayout(0, 0));
		tabs.addTab("Cards", null, cardsTab, "View, create and edit cards.");
		templatesTab = new JPanel();
		templatesTab.setLayout(new BorderLayout(0, 0));
		tabs.addTab("Templates", null, templatesTab, "Manage templates and change the template of a card.");
		spoilerTab = new JPanel();
		spoilerTab.setLayout(new BorderLayout(0, 0));
		tabs.addTab("Spoiler", null, spoilerTab, "View a visual spoiler of the set.");
		subsetsTab = new JPanel();
		subsetsTab.setLayout(new BorderLayout(0, 0));
		tabs.addTab("Subsets", null, subsetsTab, "Generate random subsets of cards in the set.");
		tabs.setSelectedIndex(MainViewTabs.CARDS_TAB);
		tabs.addChangeListener(new TabChangeListener());
		add(tabs);
	}
	
	private void initCardTab(){
		
		// PANELS
		
		cardsDispPanel = new CardDisplayPanel();
		
		listPanel = new JPanel();
		listPanel.setLayout(new MigLayout("wrap 3, insets 0 0 0 0","[][][grow,fill]","[][grow,fill]"));
		listPanel.setBackground(Color.white);

		cardPanel = new JPanel();
		cardPanel.setLayout(new MigLayout("wrap 2, insets 2 2 2 2","[][grow,fill]",""));
		cardPanel.setBackground(new Color(180, 180, 180));
		rtScroll = new JScrollPane(cardPanel);
		rtScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		rtScroll.setBackground(new Color(255, 255, 255));

		// Split pane with card table and scroll pane for card components
		spTableData = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listPanel, rtScroll);
		spTableData.setOneTouchExpandable(true);
		
		// Split pane for card image panel and split pane for card table and card components
		spImgElse = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, cardsDispPanel, spTableData);
		spImgElse.setOneTouchExpandable(true);
		spImgElse.setDividerLocation(300);
		cardsTab.add(spImgElse, BorderLayout.CENTER);
		
		// COMPONENTS
				
		addButton = new JButton("Add");
		//add.setBounds(5, 5, 50, 30);
		addButton.setFont(Constants.DEFAULT_FONT);
		addButton.addKeyListener(new AddCardKeyListener());
		addButton.addActionListener(new AddCardClicked());
		listPanel.add(addButton);
		removeButton = new JButton("Remove");
		//remove.setBounds(60, 5, 50, 30);
		removeButton.setFont(Constants.DEFAULT_FONT);
		removeButton.addKeyListener(new RemoveCardKeyListener());
		removeButton.addActionListener(new RemoveCardClicked());
		listPanel.add(removeButton);
		searchBar = new JSearchBarTextField(DAOFactory.getImageDAO().read("search.png"), "Search");
		searchBar.setFont(Constants.DEFAULT_FONT);
		searchBar.getDocument().addDocumentListener(new SearchBarListener());
		listPanel.add(searchBar);

		cardListTableModelMap.put(CARD_EDITOR_LIST, new CardTableModel());
		cardListTableMap.put(CARD_EDITOR_LIST, new JTable(cardListTableModelMap.get(CARD_EDITOR_LIST)));
		cardListTableMap.get(CARD_EDITOR_LIST).setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		cardListTableMap.get(CARD_EDITOR_LIST).getSelectionModel().addListSelectionListener(new CardSelected(CARD_EDITOR_LIST,cardListTableMap.get(CARD_EDITOR_LIST)));
		cardListTableMap.get(CARD_EDITOR_LIST).addKeyListener(new ListKeyListener());
		cardListTableMap.get(CARD_EDITOR_LIST).getTableHeader().addMouseListener(new ColumnClicked(CARD_EDITOR_LIST));
		
		listScroll = new JScrollPane(cardListTableMap.get(CARD_EDITOR_LIST));
		listScroll.setBackground(new Color(255, 255, 255));
		listPanel.add(listScroll, "span 3, growx");
		
		dataComps = new JSuperChooserList();
	}
	
	private void initTemplatesTab(){
		// PANELS

		// Single card display panel on the left
		tempDispPanel = new CardDisplayPanel();
		
		templatesPanel = new JPanel();
		templatesPanel.setLayout(new MigLayout("wrap 1, insets 0 0 0 0","[grow,fill]","[][grow,fill]"));
		
		templateVisualPanel = new MultiCardDisplayPanel();
		templateVisualPanel.setMargin(20);
		templateVisualPanel.scaleToHeight(200);
		templateVisualPanel.setMultiline(false);
		templateVisualPanel.addMouseListener(new TemplateListMouseListener());
		
		templateVisualScroll = new JScrollPane(templateVisualPanel);
		templateVisualScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		templatesPanel.add(templateVisualScroll, "height 250:250:1000");
		
		templateBottomPanel = new JPanel();
		newTemplateButton = new JButton("New Template");
		newTemplateButton.setFont(Constants.DEFAULT_FONT);
		newTemplateButton.addActionListener(new NewTemplateClicked());
		templateBottomPanel.add(newTemplateButton);
		
		templatesPanel.add(templateBottomPanel);
		
		// Split pane with card image display panel and template manager panel
		spDispTemplates = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
		                           tempDispPanel, templatesPanel);
		spDispTemplates.setOneTouchExpandable(true);
		spDispTemplates.setDividerLocation(300);
		templatesTab.add(spDispTemplates, BorderLayout.CENTER);
	}
	
	private void initSpoilerTab(){
		spoilerPanel = new MultiCardDisplayPanel();
		
		spoilerScroll = new JScrollPane(spoilerPanel);
		spoilerScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		spoilerTab.add(spoilerScroll);
	}
	
	private void initSubsetsTab(){

		subsetDispPanel = new CardDisplayPanel();
		
		subsetDetailsPanel = new JPanel();
		
		subsetTableScroll = new JScrollPane();
		
		cardListTableModelMap.put(SUBSETS_LIST, new CardTableModel());
		cardListTableMap.put(SUBSETS_LIST, new JTable(cardListTableModelMap.get(SUBSETS_LIST)));
		
		subsetTableScroll.add(cardListTableMap.get(SUBSETS_LIST));

		spSubsetDetails = new JSplitPane(JSplitPane.VERTICAL_SPLIT, subsetDetailsPanel, subsetTableScroll);
		spSubsetDetails.setOneTouchExpandable(true);
		spSubsetDetails.setDividerLocation(200);

		spDispSubsets = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, subsetDispPanel, spSubsetDetails);
		spDispSubsets.setOneTouchExpandable(true);
		spDispSubsets.setDividerLocation(300);
		subsetsTab.add(spDispSubsets);
	}
	
	/******************************** ^^^ INITIALIZATION ^^^ ********************************/
	
	/******************************** vvv EVENT LISTENERS vvv ********************************/
	
	class MainViewWindowListener implements WindowListener {
		@Override
		public void windowActivated(WindowEvent arg0) {
		}
		
		@Override
		public void windowClosed(WindowEvent arg0) {
		}
		
		@Override
		public void windowClosing(WindowEvent arg0) {
			controller.exit();
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
			spTableData.setDividerLocation(spTableData.getWidth() - 450);
			controller.openASet(); // We need to open a new set
		}
	}
	
	// Tab changed
	class TabChangeListener implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent arg0) {
			if("".equals(currentList())){
				pasteButton.setEnabled(false);
				copyButton.setEnabled(false);
				cutButton.setEnabled(false);
			} else {
				pasteButton.setEnabled(true);
				copyButton.setEnabled(true);
				cutButton.setEnabled(true);
			}
			controller.switchTab(tabs.getSelectedIndex());
		}
	}

	// NEW SET clicked
	class NewClicked implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			controller.createNewSet();
		}
	}
	
	private class MainDispatcher implements KeyEventDispatcher {
        public boolean dispatchKeyEvent(KeyEvent e) {
            if (e.getID() != KeyEvent.KEY_PRESSED) return false;
        	// Ctrl+D to ADD
        	if ((e.getKeyCode() == KeyEvent.VK_D) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
        		controller.addCard(CARD_EDITOR_LIST, false);
                return true;
            }
        	// Ctrl+S to SAVE
        	if ((e.getKeyCode() == KeyEvent.VK_S) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
        		controller.save();
                return true;
            }
            return false;
        }
    }
	
	private class PrintClicked implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			controller.exportHtmlDeck();
		}
	}
	
	private class ExportExcelClicked implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			controller.exportExcelDeck();
		}
	}
	
	private class PasteClicked implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			controller.pasteCard(currentList());
		}
	}
	
	private class CopyClicked implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			controller.copyCard(currentList());
		}
	}
	
	private class CutClicked implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			controller.cutCard(currentList());
		}
	}

	private class BulkEditClicked implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			controller.openBulkEdit();
		}
	}
	
	private class AboutClicked implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			controller.showAbout();
		}
	}
	
	private class AddCardClicked implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			controller.addCard(CARD_EDITOR_LIST, true);
		}
	}
	
	private class NewTemplateClicked implements ActionListener {
		public void actionPerformed(ActionEvent e){
			controller.newTemplateClicked();
		}
	}
	
	private class AddCardKeyListener implements KeyListener {
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				controller.addCard(CARD_EDITOR_LIST, false);
				e.consume();
				return;
			}
		}
		public void keyReleased(KeyEvent e) { }
		public void keyTyped(KeyEvent e) { }
	}
	
	private class ListKeyListener implements KeyListener {
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_DELETE){
				controller.removeCard(CARD_EDITOR_LIST);
				e.consume();
				return;
			}
			if(e.getKeyCode() == KeyEvent.VK_UP && cardListTableMap.get(CARD_EDITOR_LIST).getSelectedRow() > 0){
        		controller.selectCard(CARD_EDITOR_LIST, cardListTableMap.get(CARD_EDITOR_LIST).getSelectedRow() - 1, false);
				e.consume();
				return;
			}
			if(e.getKeyCode() == KeyEvent.VK_DOWN && cardListTableMap.get(CARD_EDITOR_LIST).getSelectedRow() < cardListTableMap.get(CARD_EDITOR_LIST).getRowCount() - 1){
        		controller.selectCard(CARD_EDITOR_LIST, cardListTableMap.get(CARD_EDITOR_LIST).getSelectedRow() + 1, false);
				e.consume();
				return;
			}
		}
		public void keyReleased(KeyEvent e) { }
		public void keyTyped(KeyEvent e) { }
	}
	
	private class RemoveCardClicked implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			controller.removeCard(CARD_EDITOR_LIST);
		}
	}
	
	private class RemoveCardKeyListener implements KeyListener {
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER){
				controller.removeCard(CARD_EDITOR_LIST);
				e.consume();
				return;
			}
			if(e.getKeyCode() == KeyEvent.VK_DOWN) { // Down tabs back
				e.getComponent().transferFocus();
				e.consume();
				return;
			}
			if(e.getKeyCode() == KeyEvent.VK_UP) { // Up tabs back
				e.getComponent().transferFocusBackward();
				e.consume();
				return;
			}
		}
		public void keyReleased(KeyEvent e) { }
		public void keyTyped(KeyEvent e) { }
	}
	
	private class CardSelected implements ListSelectionListener	{
		
		private String list;
		
		private JTable owner;
		
		public CardSelected(String list, JTable owner){
			super();
			this.list = list;
			this.owner = owner;
		}
		
		@Override
		public void valueChanged(ListSelectionEvent ev) {
			if(cardListTableMap.get(list).getSelectedRow() < 0) return;
			controller.selectCard(list, cardListTableMap.get(list).getSelectedRow(), true);
			owner.requestFocusInWindow();
		} 
	}
	
	private class ColumnClicked implements MouseListener {
		
		private String list;
		
		public ColumnClicked(String iList){
			super();
			list = iList;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			int col = cardListTableMap.get(list).columnAtPoint(e.getPoint());
		    String columnName = cardListTableModelMap.get(list).getColumnName(col);
			controller.sortColumn(list, columnName);
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}
	}
	
	private class CardKeyListener implements KeyListener {
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_TAB) {
	            if(e.getModifiers() > 0) e.getComponent().transferFocusBackward();
	            else e.getComponent().transferFocus();
	            e.consume();
	            return;
	        }
			if(e.getKeyCode() == KeyEvent.VK_ENTER){ // If enter
				JTextArea txtArea = (JTextArea)e.getComponent();
				if(isFinalRow(txtArea)){ // If final row, move down to next component
		            if(e.getModifiers() > 0){
		            	e.getComponent().transferFocusBackward();
		            }else{
		            	e.getComponent().transferFocus();
		            }
					e.consume();
		            return;
				}
				// If there are already enough line separators
				String text = txtArea.getText();
				text = text.replaceAll("\r", "");
				String[] textSplit = text.split("\n", -1);
				if(textSplit.length >= txtArea.getRows()){
					e.consume();
					return; // Do nothing
				}
				//Add line separator properly
				((JTextArea)e.getComponent()).insert(System.lineSeparator(), ((JTextArea)e.getComponent()).getSelectionStart());
	            e.consume();
	            return;
			}
			if(e.getKeyCode() == KeyEvent.VK_UP){
				if(isTopRow((JTextArea)e.getComponent())){ // Move up a component if on top row
					e.getComponent().transferFocusBackward();
		            e.consume();
					return;
				}
				return;
			}
			if(e.getKeyCode() == KeyEvent.VK_DOWN){
				if(isBottomRow((JTextArea)e.getComponent())){// Move down a component if on bottom row
					e.getComponent().transferFocus(); 
					e.consume();
					return;
				}
				return;
			}
			if(e.getKeyChar() == KeyEvent.CHAR_UNDEFINED) return;
			// Keys with a defined charater only
		}
		public void keyReleased(KeyEvent e) { }
		public void keyTyped(KeyEvent e) { }
	}
	
	private class OpenClicked implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			controller.openASet();
		}
	}
	private class SaveClicked implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			controller.save();
		}
	}
	private class SaveAsClicked implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			controller.saveAs();
		}
	}
	
	private class CardPropertyChanged implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) { // Card component changed somewhere
			JAbstractSuperChooser owner = (JAbstractSuperChooser)e.getSource();
			if(owner == null) return;
			String compName = owner.getValue("Name"); // Card Component name
			controller.updateSelectedCard(CARD_EDITOR_LIST, compName, owner.getData());
		}
	}
	
	private class CommentChanged implements ActionListener {
		public void actionPerformed(ActionEvent e) { // Card comment changed
			JComponent owner = (JAbstractSuperChooser)e.getSource();
			if(owner == null) return;
			controller.updateComments(CARD_EDITOR_LIST, (String)commentText.getData());
		}
	}
	
	private class TemplateListMouseListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent event) {
			int index = templateVisualPanel.getClickIndex(event.getX(), event.getY());
			if(index < 0) return;
			controller.templateImageClicked(index);
		}
		@Override
		public void mouseEntered(MouseEvent event) {
		}
		@Override
		public void mouseExited(MouseEvent event) {
		}
		@Override
		public void mousePressed(MouseEvent event) {	
		}
		@Override
		public void mouseReleased(MouseEvent event) {
		}
	}
	
	private class SearchBarListener implements DocumentListener {
		@Override
		public void changedUpdate(DocumentEvent e) {
			update(e);
		}
		@Override
		public void insertUpdate(DocumentEvent e) {
			update(e);
		}
		@Override
		public void removeUpdate(DocumentEvent e) {
			update(e);
		}
		private void update(DocumentEvent e){
			controller.searchTextChanged(CARD_EDITOR_LIST, searchBar.getText());
		}
	}
	
	/******************************** ^^^ EVENT LISTENERS ^^^ ********************************/
	
	// Setup right panel here
	public void buildLabelsAndTexts(String templateName, List<CardComponent> comps){
		int columns = 40; // text field length
		
		// get list of components from game
		// Focus traversal
		Vector<Component> order = new Vector<Component>();
		// Hide card panel
		cardPanel.setVisible(false);
		// Flush card panel
		cardPanel.removeAll();
		
		// Initialize template label and place it at top of right panel
		templateLabel = new JLabel("Template Name:");
		//templateLabel.setBounds(5, 5, templateLabel.getPreferredSize().width, templateLabel.getPreferredSize().height);
		cardPanel.add(templateLabel);
		templateNameLabel = new JLabel(templateName);
		cardPanel.add(templateNameLabel);

		// initialize compLabels, dataComps and place them in right panel
		compLabels = new JLabel[comps.size()];
		dataComps.clear();
		for(int i = 0; i < comps.size(); i++){
			if(!comps.get(i).isEditable()) continue;
			compLabels[i] = new JLabel(comps.get(i).getName());
			compLabels[i].setFont(new Font("Sans Serif", Font.PLAIN, 12));
			
			addComponent(i, comps.get(i).getName(), comps.get(i).getTypeString(), comps.get(i).getLines(), columns);
			
			JComponent tabComponent = dataComps.get(i).getTabComponent();
			if (tabComponent != null) order.add(tabComponent);
		}
		
		// Comment text
		commentLabel = new JLabel("Comments");
		commentLabel.setFont(new Font("Sans Serif", Font.PLAIN, 12));
		cardPanel.add(commentLabel);
		commentText = new JSuperTextChooser(5, columns);
		commentText.addKeyListener(new CardKeyListener());
		commentText.addActionListener(new CommentChanged());
		commentText.setFont(new Font("Sans Serif", Font.PLAIN, 12));
		cardPanel.add(commentText);
		order.add(commentText);
		
		cardPanel.revalidate();
		cardPanel.repaint();
		
		// Show card panel
		cardPanel.setVisible(true);
		
		// Setup tab order
		order.add(addButton);
		order.add(removeButton);
		this.setFocusTraversalPolicy(new TabControl(order));
		// Focus on first component if not focusing on list
		if(this.getFocusOwner() == null){
			dataComps.get(0).requestFocusInWindow();
			return;
		}
		if(this.getFocusOwner().equals(cardListTableMap.get(CARD_EDITOR_LIST))) return;
		dataComps.get(0).requestFocusInWindow();
	}
	
	private void addComponent(int i, String name, String type, int ... options){
		cardPanel.add(compLabels[i]);
		dataComps.add(SuperChooserFactory.getChooser(type, options));
		dataComps.get(i).addKeyListener(new CardKeyListener());
		dataComps.get(i).addActionListener(new CardPropertyChanged());
		dataComps.get(i).putValue("Name", name);
		dataComps.get(i).setFont(new Font("Sans Serif", Font.PLAIN, 12));
		cardPanel.add(dataComps.get(i));
	}
	
	private boolean isTopRow(JTextArea textArea){
		return !textArea.getText().substring(0, textArea.getCaretPosition()).contains(System.lineSeparator());
	}
	
	private boolean isBottomRow(JTextArea textArea){
		return !textArea.getText().substring(textArea.getCaretPosition()).contains(System.lineSeparator());
	}
	
	private boolean isFinalRow(JTextArea textArea){
		if(!isBottomRow(textArea)) return false;
		return textArea.getText().split("\r\n|\r|\n", -1).length >= textArea.getRows();
	}
	
	private String currentList(){
		int tab = tabs.getSelectedIndex();
		if(tab == MainViewTabs.CARDS_TAB) return CARD_EDITOR_LIST;
		if(tab == MainViewTabs.SUBSETS_TAB) return SUBSETS_LIST;
		return "";
	}
	
	/******************************** vvv METHODS FOR CONTROLLER vvv ********************************/
	
	public void setTab(int tabIndex){
		tabs.setSelectedIndex(tabIndex);
	}

	public void removeAllFromList(String list){
		cardListTableModelMap.get(list).removeAll();
		cardListTableModelMap.get(list).fireTableDataChanged();
	}
	
	public void setSelectedCard(String list, int index){
		cardListTableMap.get(list).setRowSelectionInterval(index, index);
	}
	
	public void updateSelectedCardInList(String list, String columnName, String text){
		int column;
		column = cardListTableModelMap.get(list).indexOfColumn(columnName);
		if(column < 0) return;
		cardListTableModelMap.get(list).setValueAt(text, cardListTableMap.get(list).getSelectedRow(), column);
		cardListTableModelMap.get(list).fireTableCellUpdated(cardListTableMap.get(list).getSelectedRow(), column);
	}
	
	public void addCardToList(String list, int index, List<String> columns, Map<String, Object> data){
		// Add necessary columns to the table
		boolean addedColumns = false;
		for(String newColumn : columns){
			if(cardListTableModelMap.get(list).indexOfColumn(newColumn) < 0){
				cardListTableModelMap.get(list).addColumn(newColumn, String.class);
				addedColumns = true;
			}
		}
		// If columns were added, refresh
		if(addedColumns){
			cardListTableModelMap.get(list).fireTableStructureChanged();
		}
		cardListTableModelMap.get(list).addRow(data, index);
		cardListTableModelMap.get(list).fireTableRowsInserted(index, index);
		cardListTableMap.get(list).setRowSelectionInterval(index, index);
	}
	
	public void removeCardFromList(String list, int index){
		cardListTableModelMap.get(list).removeRow(index);
		cardListTableModelMap.get(list).fireTableRowsDeleted(index, index);
	}
	
	public void selectCardOnList(String list, int index){
		if(index < 0 || index >= cardListTableMap.get(list).getRowCount()){
			System.out.println("Out of bounds index in selectCardOnList");
			return;
		}
		cardListTableMap.get(list).setRowSelectionInterval(index, index);
	}
	
	public void toggleSetEditing(boolean canEdit){
		cardPanel.setEnabled(canEdit);
		listPanel.setEnabled(canEdit);
	}
	
	public void moveTableRow(String list, int oldIndex, int newIndex){
		if(oldIndex == newIndex) return;
		int firstRow = Math.min(oldIndex, newIndex), lastRow = Math.max(oldIndex, newIndex);
		cardListTableModelMap.get(list).moveRow(oldIndex, newIndex);
		cardListTableModelMap.get(list).fireTableRowsUpdated(firstRow, lastRow);
	}
	
	public void disableComponents(){
		for(Component i : cardPanel.getComponents()) i.setEnabled(false);
	}
	
	public void moveFocusToName(){
		if(dataComps == null || dataComps.size() <= 0) {
			return;
		}
		// TODO make this work
		dataComps.get(0).requestFocus();
		dataComps.get(0).requestFocusInWindow();
		/*try {
			rtScroll.getVerticalScrollBar().setValue(0);
			Robot r = new Robot();
			Point location = dataComps.get(0).getLocationOnScreen();
			Point mouse = MouseInfo.getPointerInfo().getLocation();
			r.mouseMove(location.x + 2, location.y + 2);
		    r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		    r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			r.mouseMove(mouse.x, mouse.y);
		} catch (AWTException e) {
			System.out.println("Robot hack isn't working");
		}*/
	}
	
	public boolean componentsInitialized(){
		return dataComps.size() > 0;
	}
	
	public void updateCardComponent(String type, int index, Object data){
		if(dataComps.getData(index).equals(data.toString())) return;
		// Increment this before triggering event listener
		dataComps.get(index).disableActionListeners();
		if(!dataComps.setData(index, data)){
			// Nevermind, didn't touch the data
		}
		dataComps.get(index).enableActionListeners();
	}
	
	public void updateCardComments(String newComments){
		if(commentText.getData().equals(newComments)) return;
		commentText.disableActionListeners();
		commentText.setData(newComments);
		commentText.enableActionListeners();
	}
	
	public void updateCardImage(BufferedImage img){
		cardsDispPanel.updateDisp(img);
	}
	
	public void updateCardTemplateImage(BufferedImage img){
		tempDispPanel.updateDisp(img);
	}
	
	public void updateCardTemplateListImages(List<BufferedImage> imgs, List<String> names, int selected){
		templateVisualPanel.deselectAll();
		templateVisualPanel.select(selected);
		templateVisualPanel.updateDisplay(imgs, names);
		templateVisualPanel.revalidate();
		templateVisualPanel.repaint();
		templateVisualScroll.revalidate();
		templateVisualScroll.repaint();
	}
	
	public void updateCardTemplateListSelection(int selected){
		templateVisualPanel.deselectAll();
		templateVisualPanel.select(selected);
		templateVisualPanel.updateDisplay();
	}
	
	public void updateCardTemplateListSingleImage(BufferedImage img, int index){
		templateVisualPanel.updateImage(img, index);
		templateVisualPanel.revalidate();
		templateVisualPanel.repaint();
		templateVisualScroll.revalidate();
		templateVisualScroll.repaint();
	}
	
	public void loadSpoilerImages(List<BufferedImage> imgs){
		spoilerPanel.updateDisplay(imgs);
		spoilerScroll.getViewport().revalidate();
	}

	/******************************** ^^^ METHODS FOR CONTROLLER ^^^ ********************************/
}