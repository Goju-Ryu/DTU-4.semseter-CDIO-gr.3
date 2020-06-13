package data;

import com.google.gson.Gson;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import model.cabal.internals.card.Card;

import java.util.ArrayList;

/**
 * This class is intented to translate the input string we get to a json object.
 */
public class InputDTO {
    private Gson g = new Gson();

    public ArrayList<Card> getUsrInput(String UIChoice){
        ArrayList<Card> cards = new ArrayList<>();
        String usrInput ="";
        String target = "localhost:50051";
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                // needing certificates.
                .usePlaintext()
                .build();
        InputAccesPoint accessInput = new InputAccesPoint(channel);
        try{
            usrInput = accessInput.getInput(UIChoice);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println("From getUsrInput: "+usrInput);
        usrInput = stringToJson(usrInput);
        return cards;
    }

    /**
     * We are using the gson libary to transform our string into a json object
     *///TODO: Transform the string into a JSON object that you use when you initilize the cards

    public String stringToJson(String toJson){
        String str = g.toJson(toJson);
        return str;
    }
    /**
     * When initiating a board we have made the cards so that they are given in
     * the order corrisponding to our JSON object;
     * Drawstack, then the suit stacks(Hearts, Clubs, Dimonds, Spades),
     * and finally the buildstacs ordered from 1-7
     * @return
     */// TODO: implement this class
    public ArrayList<Card> jsonToCard(){
        ArrayList<Card> cards = new ArrayList<>();
        return cards;
    }


}
