package Logik;


import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * This is the model of the entire cabal
 */
public class Cabal_mads implements I_CabalModel{

    private List<A_StackModel>[] columns;
    private A_StackModel[] acesPile;
    private List<I_CardModel> turnedPile;
    private List<I_CardModel> cardPile;

    public Cabal_mads() {
        columns = (List<A_StackModel>[]) Array.newInstance(List.class, 7);
        try {
            fillArrayWithNew(columns, ArrayList.class);
        } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        acesPile = new A_StackModel[4];
        try {
            fillArrayWithNew(acesPile, Stack.class);
        } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }


        turnedPile = new Stack<>();
        cardPile = new Stack<>();
    }

    //---------------------------------------Getters------------------------------------------------------------------------

    public List<A_StackModel>[] getColumns() {
        return  columns;
    }

    public A_StackModel[] getAcesPile() {
        return acesPile;
    }

    public List<I_CardModel> getTurnedPile() {
        return turnedPile;
    }

    public List<I_CardModel> getCardPile() {
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
        //empty the acesPile list
        for (A_StackModel stack : acesPile) {
            stack = new CardStack();
        }

        //empty the columns list
        for (List<A_StackModel> a_stackModels : columns) {
            a_stackModels.removeIf(e -> true);
        }
        //fill with face-down cards
        for (int i = 0; i < columns.length; i++) {
            for (int j = 0; j < 7 - i; j++) {
                columns[i].add(new CardStack());
            }
        }

        cardPile.removeIf(e -> true); //remove all
        for (int i = 0; i < 24; i++) { //24 is number of cards left when cabal is ready
            cardPile.add(new Card());
        }

        turnedPile.removeIf(e -> true); // empty list
    }


    //---------------------------------------Helper methods-------------------------------------------------------------
    private static <T> void fillArrayWithNew(T[] arr, Class<? extends T> aClass)
            throws InstantiationException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = aClass.getDeclaredConstructor().newInstance();
        }
    }
}
