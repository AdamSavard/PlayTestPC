package mainframe;

import io.DAOFactory;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

import mainframe.popup.ExportDialog;
import cards.Card;
import cards.CardSet;

public final class PrintWizard {

	private static ExportDialog exporter; // Export dialog
	
	public static void exportHtmlDeck(JFrame parent, CardSet openSet) {
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
	
	public static void exportExcelDeck(JFrame parent, CardSet openSet)
	{
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setName("Save Excel File");
		fileChooser.setFileFilter(new FileNameExtensionFilter("Excel File","xlsx"));
		fileChooser.setSelectedFile(new File("export.xlsx"));
		if (fileChooser.showSaveDialog(parent) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		setToExcelFile(fileChooser.getSelectedFile().getAbsolutePath(), openSet);
	}
	
	private static void setToExcelFile(String fileName, CardSet openSet)
	{
		List<List<Object>> excelData = new ArrayList<List<Object>>();
		Map<String, Integer> fieldIndexes = new HashMap<String, Integer>();
		int tempRowIndex, tempColumnIndex;
		excelData.add(new ArrayList<Object>());
		excelData.get(0).add("Template");
		for(Card card : openSet.getCards(MainView.CARD_EDITOR_LIST)){
			excelData.add(new ArrayList<Object>());
			tempRowIndex = excelData.size() - 1;
			for(String field : card.getData().keySet()) {
				if(!fieldIndexes.containsKey(field)) {
					excelData.get(0).add(field);
					fieldIndexes.put(field, excelData.get(0).size() - 1);
				}
				tempColumnIndex = fieldIndexes.get(field);
				while (excelData.get(tempRowIndex).size() <= tempColumnIndex) {
					excelData.get(tempRowIndex).add("");
				}
				excelData.get(tempRowIndex).set(tempColumnIndex, (String)card.getData(field));
			}
		}
		// Save excel file
		DAOFactory.getExcelDAO().write(fileName, excelData);
	}
	
	private static boolean isZeroes(List<Integer> data){
		for(int i : data) if(i != 0) return false;
		return true;
	}
}
