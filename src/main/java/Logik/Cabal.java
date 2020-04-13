package Logik;

import Logik.cabal.internals.CardStack;
import Logik.cabal.I_CabalModel;
import Logik.cabal.internals.card.I_CardModel;

import java.util.List;

/**
 * This is the model of the entire cabal
 */
public class Cabal implements I_CabalModel {

    private List<CardStack> columns;
    private List<CardStack> acesPile;
    private List<CardStack> turnedPile;
    private List<CardStack> cardPile;
    private boolean isCabalSet;

    public Cabal(List<CardStack> columns, List<CardStack> acesPile, List<CardStack> turnedPile, List<CardStack> cardPile, boolean isCabalSet) {
        this.columns = columns;
        this.acesPile = acesPile;
        this.turnedPile = turnedPile;
        this.cardPile = cardPile;
        this.isCabalSet = isCabalSet;
    }

    //---------------------------------------Getters------------------------------------------------------------------------

    public List<CardStack> getColumns() {
        return columns;
    }

    public List<CardStack> getAcesPile() {
        return acesPile;
    }

    public List<CardStack> getTurnedPile() {
        return turnedPile;
    }

    public List<CardStack> getCardPile() {
        return cardPile;
    }

    public boolean isCabalSet() {
        return isCabalSet;
    }

//---------------------------------------Setters------------------------------------------------------------------------

    public void setCabalSet(boolean cabalSet) {
        isCabalSet = cabalSet;
    }

//---------------------------------------Various methods----------------------------------------------------------------

    // This will take a card from the card pile and put it face up in the turned card pile
    public I_CardModel turnCard() {



        return null;
    }

    public I_CardModel getTurnedCard() {
        return null;
    }

    public void initialize() {

    }
}
