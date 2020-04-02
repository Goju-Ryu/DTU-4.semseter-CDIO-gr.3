package Logik;

import java.lang.reflect.Array;
import java.util.List;

/**
 * This is the model of the entire cabal
 */
public class Cabal_mads implements I_CabalModel{

    private List<A_StackModel>[] columns;
    private List<A_StackModel>[] acesPile;
    private List<A_StackModel> turnedPile;
    private List<A_StackModel> cardPile;

    public Cabal_mads(List<A_StackModel> turnedPile, List<A_StackModel> cardPile) {
        this.columns = (List<A_StackModel>[]) Array.newInstance(List.class, 7);
        this.acesPile = (List<A_StackModel>[]) Array.newInstance(List.class, 4);
        this.turnedPile = turnedPile;
        this.cardPile = cardPile;
    }

    //---------------------------------------Getters------------------------------------------------------------------------

    public List<A_StackModel>[] getColumns() {
        return  columns;
    }

    public List<A_StackModel>[] getAcesPile() {
        return acesPile;
    }

    public List<A_StackModel> getTurnedPile() {
        return turnedPile;
    }

    public List<A_StackModel> getCardPile() {
        return cardPile;
    }


//---------------------------------------Setters------------------------------------------------------------------------


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
