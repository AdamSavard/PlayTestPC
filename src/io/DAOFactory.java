package io;

public class DAOFactory {
    
    private static CardSetDAO cardSet;
    
    private static CardTemplateDAO cardTemplate;
    
    private static ImageDAO image;
    
    private static HtmlDAO html;
	
	public static CardSetDAO getCardSetDAO(){
		if (null == cardSet) {
            cardSet = new CardSetDAOFileSys();
        }
        return cardSet;
	}
	
	public static CardTemplateDAO getCardTemplateDAO(){
		if (null == cardTemplate) {
        	cardTemplate = new CardTemplateDAOFileSys();
        }
        return cardTemplate;
	}
	
	public static ImageDAO getImageDAO(){
		if (null == image) {
			image = new ImageDAOFileSys();
        }
        return image;
	}
	
	public static HtmlDAO getHtmlDAO(){
		if (null == html) {
			html = new HtmlDAOFileSys();
        }
        return html;
	}


}
