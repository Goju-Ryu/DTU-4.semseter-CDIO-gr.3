package data;

import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;

import java.util.ArrayList;

/**
 * This class is intented to translate the input string we get to a json object.
 */
public class InputSimDTO implements I_InputDTO {
        // Todo ust return less cards at first, because sui stacks arent filled in at start.

    private ArrayList<Card> listTop = new ArrayList<>(4*13);
    private ArrayList<Card> listAll = new ArrayList<>(4*13);
    private boolean first = true;

    public InputSimDTO(){
        // filling in ALL Cards
        E_CardSuit[] a = {E_CardSuit.HEARTS, E_CardSuit.DIAMONDS,E_CardSuit.CLUBS,E_CardSuit.SPADES};
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j <= 13; j++) {
                listAll.add(new Card(a[i],j));
            }
        }

        // 7 is the build stacks
        // 1 is the drawStack
        // 0 is for the suit stacks
        for (int i = 0; i < ( 7 + 1 + 0 + 4)  ; i++) {
            listTop.add(popRanCard());
        }
    }

    public ArrayList<Card> getUsrInput(String UIChoice){
        if(first){
          return listTop;
        }else{

            // todo, remove this and make a method that is called when one decides a move.
            int ranAll = getRandom( listAll.size() );
            int ranTop = getRandom( listTop.size() );

            Card newCard = listAll.get(ranAll);
            listTop.set(ranTop, newCard);

            return listTop;
        }
    }

    public Card popRanCard(){
        int ran = getRandom( listAll.size() );
        Card c = listAll.get( ran );
        listAll.remove(ran);
        return c;
    }
    public Card popSelCard(int rank, int suit){
        int i = rank * suit;
        Card c = listAll.get(i);
        listAll.remove(i);
        return c;
    }

    private int getRandom(int maxValue){
        return (int) (Math.random() * maxValue);
    }
}
