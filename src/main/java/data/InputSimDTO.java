package data;

import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;

import java.util.ArrayList;

/**
 * This class is intented to translate the input string we get to a json object.
 */
public class InputSimDTO {

    private ArrayList<Card> listTop = new ArrayList<>();
    private ArrayList<Card> listAll = setupData();
    private boolean first = true;

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

    private ArrayList<Card> setupData(){
        E_CardSuit[] a = {E_CardSuit.HEARTS, E_CardSuit.DIAMONDS,E_CardSuit.CLUBS,E_CardSuit.SPADES};
        ArrayList<Card> list = new ArrayList<>(4*13);
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j <= 13; j++) {
                list.add(new Card(a[i],j));
            }
        }

        for (int i = 0; i < 14 ; i++) {
            int ran = getRandom( list.size() );
            //Card elem = list.get( ran );
            listTop.add( list.get( ran ) );
            list.remove(ran);
        }

        return list;
    }
    private int getRandom(int maxValue){
        return (int) (Math.random() * maxValue);
    }
}
