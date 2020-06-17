package data;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * This class is intented to translate the input string we get to a json object.
 */
public class InputSimDTO {

    private ArrayList<Card> lista = setupData();
    private ArrayList<Card> listb = new ArrayList<>(14);
    private boolean first = true;

    public ArrayList<Card> getUsrInput(String UIChoice){
        if(first){
          return listb;
        }else{

            int ranB = getRandom( listb.size() );
            int ranA = getRandom( lista.size() );

            Card newCard = lista.get(ranA);
            listb.set(ranB, newCard);

            return listb;
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
            int ran = getRandom( lista.size() );
            listb.add(lista.get( ran ));
            lista.remove(ran);
        }

        return list;
    }
    private int getRandom(int maxValue){
        return (int) (Math.random() * maxValue);
    }
}
