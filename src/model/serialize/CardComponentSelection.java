package model.serialize;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import cards.components.CardComponent;

public class CardComponentSelection implements Transferable, ClipboardOwner {

    private final static DataFlavor CARD_COMPONENT_FLAVOR = new CardComponentFlavor();
    private final static DataFlavor[] SUPPORTED_FLAVORS = { CARD_COMPONENT_FLAVOR };

    private CardComponent comp;

    public CardComponentSelection(CardComponent comp) {
        this.comp = comp;
    }

    public Object getTransferData(DataFlavor dataFlavor)
            throws UnsupportedFlavorException, IOException 
    {
        if (!isDataFlavorSupported(dataFlavor)) {
            throw new UnsupportedFlavorException(dataFlavor);
        }
        
        return comp;
    }

    public DataFlavor[] getTransferDataFlavors() 
    {
        return SUPPORTED_FLAVORS;
    }

    public boolean isDataFlavorSupported(DataFlavor dataFlavor) 
    {

        for (int i = 0; i < SUPPORTED_FLAVORS.length; i++) {
            if (SUPPORTED_FLAVORS[i].equals(dataFlavor)) {
                return true;
            }
        }
        return false;

    }

    public void lostOwnership(Clipboard arg0, Transferable arg1) 
    {
        // Lost ownership.
    }

}