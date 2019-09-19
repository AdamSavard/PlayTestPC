package model.serialize;

import java.awt.datatransfer.DataFlavor;

import cards.components.CardComponent;

public class CardComponentFlavor extends DataFlavor {

    public CardComponentFlavor() 
    {
        super(CardComponent.class, null);
    }  

}
