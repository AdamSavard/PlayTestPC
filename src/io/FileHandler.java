package io;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * This class provides methods for quickly dealing with files
 */
public class FileHandler {
	
	private final static Charset ENCODING = StandardCharsets.UTF_8;

	// Get working directory
	protected static String workingDir(){
		return Paths.get("").toAbsolutePath().toString();
	}
	
	protected static boolean write(String fileName, String data, boolean overwrite) {
		Path path = Paths.get(fileName);
		if(!overwrite && Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
			return false;
		}
		String[] lines = data.split("\\r?\\n");
		List<String> linesList = new ArrayList<String>();
		for(int i = 0; i < lines.length; i++) linesList.add(lines[i]);
		
		try{
			Files.write(path, linesList, ENCODING);
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return true;
    }

	protected static String read(String fileName) {
		Path path = Paths.get(fileName);
	    List<String> lines;
	    try {
			lines = Files.readAllLines(path, ENCODING);
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	    return String.join("", lines);
    }

	protected static List<String> allFileNames(String dirPath){
    	File[] files = (new File(workingDir() + "\\" + dirPath)).listFiles();
    	if(files == null) return new ArrayList<String>(0);
    	List<String> output = new ArrayList<String>(files.length);
    	for(int i = 0; i < files.length; i++) if(files[i].getName().contains(".")) output.add(files[i].getName());
    	return output;
    }
    
	protected static Image getImage(String fileName) {
    	return getImage(new File(fileName));
    }
    
	protected static Image getImage(File file) {
    	BufferedImage img;
		try {
			img = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		};
    	return img;
    }
    
	/*
	 * Open a file using a dialog
	 */
	protected static File openFile(Component parent, String title, String filterDesc, String[] filterExt){
    	JFileChooser fc = new JFileChooser();
    	fc.setMultiSelectionEnabled(false);
    	fc.setDialogTitle(title);
    	fc.setFileFilter(new FileNameExtensionFilter(filterDesc,filterExt));
    	fc.showOpenDialog(parent);
    	return fc.getSelectedFile();
    }
    
	/**
	* Open the specified webpage in the default browser if possible.
	* 
	* @param url - Web address (http://www.mywebsite.com) or a path to a local file 
	*/
	protected static void openInBrowser(String url) {
		url = url.replace(" ", "%20");
	    try {
            URI uri = new URL(url).toURI();
            Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
            if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)){
                desktop.browse(uri);
            }
        }
	    catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	protected static boolean writeExcel(String fileName, List<List<Object>> data) {
		// Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();
        // Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("Cards");
        
        // Iterate over data and write to sheet
        Object obj;
        for (int rowNum = 0; rowNum < data.size(); rowNum++)
        {
            Row row = sheet.createRow(rowNum);
            for (int cellNum = 0; cellNum < data.get(rowNum).size(); cellNum++)
            {
               Cell cell = row.createCell(cellNum);
               obj = data.get(rowNum).get(cellNum);
               if(obj instanceof String)
                    cell.setCellValue((String)obj);
                else if(obj instanceof Integer)
                    cell.setCellValue((Integer)obj);
            }
        }
        try
        {
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(new File(fileName));
            workbook.write(out);
            out.close();
            workbook.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
	}

}
