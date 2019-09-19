package mainframe;

import io.DAOFactory;

import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JFrame;

import mainframe.popup.ExportDialog;
import cards.Card;
import cards.CardSet;

public final class PrintWizard {

	private static ExportDialog exporter; // Export dialog
	
	public static void exportDeck(JFrame parent, CardSet openSet) {
		List<Integer> ids = openSet.getIds();
		List<Card> cards = openSet.getCards(ids);
		List<String> names = openSet.getCardNames(ids);
		List<BufferedImage> images = openSet.getCardImages(ids, true);
		List<Integer> quant = null;
		String htmlPage = "";

		// Use exporter dialog to determine what to print
		exporter = new ExportDialog(parent, names);
		exporter.setVisible(true);
		if(!exporter.willPrint()) return;
		quant = exporter.getQuantities();
		if(quant == null) return;
		if(isZeroes(quant)) return;
		
		// Start html file
    	htmlPage += "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n";
    	htmlPage += "<html lang=\"en\"><head>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n";
    	htmlPage += "<title>Print Proxies</title>\n<style type=\"text/css\">\n</style>\n</head>\n<body>\n";
		
    	// For each card, populate html and create image
		for(int i = 0; i < cards.size(); i++){
			if(quant.get(i) > 0){
				DAOFactory.getImageDAO().write(openSet.getFileName(), names.get(i), "png", images.get(i));
			}
			for(int j = 0; j < quant.get(i); j++){
				htmlPage += "<a><img src=\"" + openSet.getFileName() + "\\" +
					names.get(i) + ".png\" style=\"margin: 0;\" height=\"" + 
					images.get(i).getHeight() + "\" width=\"" + images.get(i).getWidth() + "\"></a>";
			}
		}
		
		htmlPage += "</body></html>";
		
		// Print html file and open it
		DAOFactory.getHtmlDAO().write(htmlPage);
	}
	
	private static boolean isZeroes(List<Integer> data){
		for(int i : data) if(i != 0) return false;
		return true;
	}
}
