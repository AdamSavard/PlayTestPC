package mainframe;

import io.DAOFactory;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import mainframe.popup.ObjChooser;
import mainframe.popup.TextDialog;
import cards.CardSet;
import cards.CardTemplate;
import cards.CardTemplateListFactory;
import templatemaker.CardTemplateController;

public final class SetWizard {
	
	// Opening a set
	public static CardSet openASet(JFrame view){
		List<String> files = DAOFactory.getCardSetDAO().list();
		if(files == null || files.size() == 0){
			return SetWizard.createNewSet(view);
		}
		files.add(0, "New Set");
		ObjChooser setChooser = new ObjChooser(view, "Choose a set to open", files);
		setChooser.setVisible(true);
		if(setChooser.getChoice() == null || setChooser.getChoice().length() == 0){ // No set being opened
			return null;
		}
		if(setChooser.getChoice().equals("New Set")){
			return SetWizard.createNewSet(view);
		}
        CardSet output;
		try{
			output = DAOFactory.getCardSetDAO().read(setChooser.getChoice());
		}catch(Exception e){
			System.out.println("Failed to open set " + setChooser.getChoice());
			e.printStackTrace();
			JOptionPane.showMessageDialog(view, "Failed to open set.", "ERROR", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		return output;
	}
	
	// Creating a new set
	public static CardSet createNewSet(JFrame view){
		List<String> files = DAOFactory.getCardTemplateDAO().list();
		if(files == null || files.size() == 0){
			return createTemplateAndSet(view);
		}
		files.add(0, "New Template");
		ObjChooser templateChooser = new ObjChooser(view, "Choose a default template for a new set", files);
		templateChooser.setVisible(true);
		if(templateChooser.getChoice().equals("New Template")){
			return createTemplateAndSet(view);
		}
		if(templateChooser.getChoice().length() == 0){ // No template selected
			return null;
		}
		// New Set with existing template
		CardSet openSet = new CardSet(templateChooser.getChoice());
		return openSet;
	}
	
	private static CardSet createTemplateAndSet(JFrame view){
		// Initialize template controller and view
		CardTemplateController control = new CardTemplateController(view);
		control.openView();
		// Once template view has closed
		if(!control.getSaved()) return null; // No new set :(
		CardTemplateListFactory.getCardTemplateList().put(control.getTemplate());
		CardSet openSet = new CardSet(control.getTemplate().getName());
		return openSet;
	}
	
	public static CardTemplate editTemplate(JFrame view, CardTemplate template){
		// Initialize template controller and view
		CardTemplateController control = new CardTemplateController(view, template);
		control.openView();
		// Once template view has closed
		if(!control.getSaved()) return null; // Template was not saved
		return control.getTemplate();
	}
	
	public static boolean nameSet(JFrame view, CardSet openSet){
		TextDialog setNamer = new TextDialog(view, "Choose Set Name", "Name your new set", "Create", null);
		setNamer.setVisible(true);
		if(setNamer.selection < 0) return false; // No name selected
		String oldName = openSet.getFileName();
		openSet.setFileName(setNamer.output); // Set name assigneds
		if(openSet.legallyNamed()) return true;
		openSet.setFileName(oldName); // Don't actually rename the set
		return false;
	}
}
