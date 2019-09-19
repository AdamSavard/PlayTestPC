package cards;

public class CardTemplateListFactory {

	private static CardTemplateList ctlSingle;
	
    public static CardTemplateList getCardTemplateList(){
        if (null == ctlSingle) {
            synchronized (CardTemplateList.class){
            	if (null != ctlSingle) return ctlSingle;
        		ctlSingle = new CardTemplateList();
            }
        }
        return ctlSingle;
    }

}
