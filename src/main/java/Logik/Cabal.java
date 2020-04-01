package Logik;

import java.util.List;

public class Cabal implements I_CabalModel{

    private List<CardStack> columns;
    private List<CardStack> AcesPile;
    private List<CardStack> turnedPile;
    private List<CardStack> cardPile;
    private boolean isCabalSet;



    public List<CardStack> getColumns() {
        return columns;
    }

    public List<CardStack> getAcesPile() {
        return AcesPile;
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

    public void setCabalSet(boolean cabalSet) {
        isCabalSet = cabalSet;
    }

    @Override
    public I_CardModel turnCard() {
        return null;
    }

    @Override
    public I_CardModel getTurnedCard() {
        return null;
    }

    @Override
    public void initialize() {

    }
}
