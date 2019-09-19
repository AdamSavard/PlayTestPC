package mainframe;

import mainframe.popup.AboutDialog;
import cards.CardSet;

public class MainModel {
	
	private CardSet openSet;
	
	private boolean saved;
	
	private int resetCards;
	
	private boolean resetSpoiler;
	
	private AboutDialog about;
	
	// need to disable some things while the set is opening
	private boolean openingSetFlag;
	
	public MainModel(){
		openSet = null;
		saved = false;
		resetCards = 0;
		resetSpoiler = true;
		about = null;
	}
	
	public CardSet setOpenSet(CardSet newSet){
		openSet = newSet;
		return openSet;
	}
	
	public CardSet getOpenSet(){
		return openSet;
	}
	
	public void setSaved(boolean isSaved){
		saved = isSaved;
	}
	
	public boolean isSaved(){
		return saved;
	}
	
	public void setResetCards(int rc){
		resetCards = rc;
	}
	
	public int getResetCards(){
		return resetCards;
	}

	public void setResetSpoiler(boolean rs){
		resetSpoiler = rs;
	}
	
	public boolean isResetSpoiler(){
		return resetSpoiler;
	}
	
	public void setAboutDialog(AboutDialog newAbout){
		about = newAbout;
	}
	
	public AboutDialog getAboutDialog(){
		return about;
	}


	public void setOpeningSetFlag(boolean isOpening){
		openingSetFlag = isOpening;
	}
	
	public boolean isSetOpening(){
		return openingSetFlag;
	}
	
}
