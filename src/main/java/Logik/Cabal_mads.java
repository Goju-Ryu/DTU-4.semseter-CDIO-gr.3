package Logik;


import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.*;


/**
 * This is the model of the entire cabal
 */
public class Cabal_mads implements I_CabalModel{

    final private Column[] columns;
    final private A_StackModel[] acesPile;
    final private List<I_CardModel> turnedPile;
    final private List<I_CardModel> cardPile;

    public Cabal_mads() {
        columns = new Column[7];
        acesPile = new A_StackModel[4];

        turnedPile = new Stack<>();
        cardPile = new Stack<>();
    }

    //---------------------------------------Getters------------------------------------------------------------------------

    public Column[] getColumns() {
        return columns;
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

    /**
     *  This will take a card from the card pile and put it face up in the turned card pile
     * @return the card drawn from the pile
     */
    public I_CardModel turnCard() {
        return null; //TODO: Implement
    }

    /**
     * @return the top card of the turned card pile
     */
    public I_CardModel getTurnedCard() {
        return null; //TODO: Implement
    }


    /**
     * Make sure all variables are ready for playing the game. This includes putting all cards in place face down.
     */
    public void initialize() {
        //empty the acesPile list
        for (int i = 0; i < acesPile.length; i++) {
            acesPile[i] = new CardStack();
        }

        //empty the columns list
        for (List<A_StackModel> a_stackModels : columns) {
            a_stackModels.removeIf(e -> true);
        }
        //fill with face-down cards
        for (int i = 0; i < columns.length; i++) {
            for (int j = 0; j < 7 - i; j++) {
                columns[i].initialize(j - 1);
            }
        }

        cardPile.removeIf(e -> true); //remove all
        for (int i = 0; i < 24; i++) { //24 is number of cards left when cabal is ready
            cardPile.add(new Card());
        }

        turnedPile.removeIf(e -> true); // empty list
    }


    //---------------------------------------Helper methods-------------------------------------------------------------

    /**
     * Method for filling an array with any class  that is either implementing a specific interface or extends a
     * specific class
     * @param arr The array to be filled
     * @param aClass The class to fill the array
     * @param <T> The type of the array, most often an interface or abstract class
     * @throws InstantiationException Thrown by internal method call
     * @throws NoSuchMethodException Thrown by internal method call
     * @throws InvocationTargetException Thrown by internal method call
     * @throws IllegalAccessException Thrown by internal method call
     */
    private static <T> void fillArrayWithNew(T[] arr, Class<? extends T> aClass)
            throws InstantiationException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = aClass.getDeclaredConstructor().newInstance();
        }
    }


    /**
     * Class for easily and safely interact with an entire column
     */
    public class Column {
        private final List<A_StackModel> stacks;

        public Column() {
            stacks = new ArrayList<>(2);
        }

        public A_StackModel get() {
            return stacks.get(stacks.size() - 1);
        }

        public A_StackModel getAt(int i) {
            return stacks.get(i);
        }

        public int size() {
            return stacks.size();
        }

        public void initialize() {
            stacks.removeIf(e -> true);
        }

        public void initialize(int faceDownNum, I_CardModel ... knownCards) {
            initialize();
            CardStack empty = new CardStack();
            for (int i = 0; i < faceDownNum; i++) {
                empty.addToStack();
            }

            CardStack known = new CardStack(knownCards);

            stacks.set(0, empty);
            stacks.set(1, known);
        }

        public void add(A_StackModel stack){
            stacks.add(stack);
        }

    }
}
