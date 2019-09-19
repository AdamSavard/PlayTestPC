package io;

public class HtmlDAOFileSys implements HtmlDAO {

	@Override
	public boolean write(String htmlData){
		String file = Paths.PRINT_PATH + "print" + System.currentTimeMillis() + ".html";
		FileHandler.write(file, htmlData, true);
		FileHandler.openInBrowser("file:/" + FileHandler.workingDir() + "/" + file);
		return true;
	}
}
