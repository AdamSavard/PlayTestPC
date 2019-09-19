package templatemaker;

import io.DAOFactory;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import constants.Constants;

public class TemplateMakerModePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JButton textMode; // click on text field to select, drag cursor to create text field then select it
	private JButton lineMode;
	private JButton rectMode;
	private JButton imgMode;
	private JButton moveSelectMode; // click on fields to select them (on mouse up) or hold mouse down to move them
	
	private CardTemplateController controller;
	
	public TemplateMakerModePanel(CardTemplateController newControl){
		super();
		controller = newControl;
		setLayout(new MigLayout("insets 0 0 0 0","[]0[]0[]0[]0[]0","[grow,fill]"));
		setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		setMinimumSize(new Dimension(100, 30));
		setPreferredSize(new Dimension(100, 30));
		setMaximumSize(new Dimension(10000, 30));
		setBackground(new Color(125, 125, 125));
		init();
	}
	
	private void init(){
		textMode = new JButton();
		textMode.setIcon(DAOFactory.getImageDAO().readImageIcon("TemplateText.png"));
		textMode.setBorder(BorderFactory.createEmptyBorder());
		textMode.setMinimumSize(new Dimension(32, 32));
		textMode.setMaximumSize(new Dimension(32, 32));
		textMode.setToolTipText("Text Mode: Drag cursor over image to create text fields.");
		textMode.addActionListener(new TextModeClicked());
		textMode.setEnabled(false);
		add(textMode);
		
		lineMode = new JButton();
		lineMode.setIcon(DAOFactory.getImageDAO().readImageIcon("TemplateLine.png"));
		lineMode.setMinimumSize(new Dimension(32, 32));
		lineMode.setMaximumSize(new Dimension(32, 32));
		lineMode.setToolTipText("Line Mode: Drag cursor over image to create lines.");
		lineMode.addActionListener(new LineModeClicked());
		add(lineMode);
		
		rectMode = new JButton();
		rectMode.setIcon(DAOFactory.getImageDAO().readImageIcon("TemplateRect.png"));
		rectMode.setMinimumSize(new Dimension(32, 32));
		rectMode.setMaximumSize(new Dimension(32, 32));
		rectMode.setToolTipText("Rectangle Mode: Drag cursor over image to create rectangles with borders and fill.");
		rectMode.addActionListener(new RectModeClicked());
		add(rectMode);
		
		imgMode = new JButton();
		imgMode.setIcon(DAOFactory.getImageDAO().readImageIcon("TemplateImage.png"));
		imgMode.setMinimumSize(new Dimension(32, 32));
		imgMode.setMaximumSize(new Dimension(32, 32));
		imgMode.setToolTipText("Image Mode: Drag cursor over image to create areas to populate with images.");
		imgMode.addActionListener(new ImgModeClicked());
		add(imgMode);
		
		moveSelectMode = new JButton();
		moveSelectMode.setIcon(DAOFactory.getImageDAO().readImageIcon("TemplateMove.png"));
		moveSelectMode.setMinimumSize(new Dimension(32, 32));
		moveSelectMode.setMaximumSize(new Dimension(32, 32));
		moveSelectMode.setToolTipText("Move Mode: Click on components and drag them around.");
		moveSelectMode.addActionListener(new MoveSelectModeClicked());
		add(moveSelectMode);
		
		JPanel filler = new JPanel();
		filler.setOpaque(false);
		add(filler);
	}
	
	/******************************** vvv EVENT LISTENERS vvv ********************************/
	
	private class TextModeClicked implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			controller.setMode(Constants.EDITOR_MODE_TEXT);
		}
	}
	
	private class LineModeClicked implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			controller.setMode(Constants.EDITOR_MODE_LINE);
		}
	}
	
	private class RectModeClicked implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			controller.setMode(Constants.EDITOR_MODE_RECT);
		}
	}
	
	private class ImgModeClicked implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			controller.setMode(Constants.EDITOR_MODE_IMG);
		}
	}
	
	private class MoveSelectModeClicked implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			controller.setMode(Constants.EDITOR_MODE_MOVE);
		}
	}
	
	/******************************** ^^^ EVENT LISTENERS ^^^ ********************************/
	
	public void disableButton(int index){
		textMode.setEnabled(index != 0);
		lineMode.setEnabled(index != 1);
		rectMode.setEnabled(index != 2);
		imgMode.setEnabled(index != 3);
		moveSelectMode.setEnabled(index != 4);
	}
}
