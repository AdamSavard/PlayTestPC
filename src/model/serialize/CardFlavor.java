package model.serialize;

import java.awt.datatransfer.DataFlavor;

import cards.Card;

public class CardFlavor extends DataFlavor {

    public CardFlavor() 
    {
        super(Card.class, null);
    }  

}
