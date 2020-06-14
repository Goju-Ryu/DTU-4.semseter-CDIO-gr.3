package data;

import com.google.gson.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import model.cabal.internals.card.Card;

import java.util.ArrayList;
import java.util.Arrays;

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

        JsonObject cardsJson = stringToJson(usrInput);
        cards = jsonToCard(cardsJson);

        return cards;
    }

    /**
     * We are using the gson libary to transform our string into a json object
     *///TODO: Transform the string into a JSON object that you use when you initilize the cards

    public JsonObject stringToJson(String toJson){
        //toJson = toJson.replaceAll("\"{","{");
        //toJson = toJson.replaceAll("}\"","}");
        System.out.println("from String to Json: "+toJson);
        JsonObject jsonObject = new JsonParser().parse(toJson).getAsJsonObject();
        //System.out.println("from String to Json: "+jsonObject);
        //String jsonObject = g.toJson(toJson);
        return jsonObject;
    }
    /**
     * When initiating a board we have made the cards so that they are given in
     * the order corrisponding to our JSON object;
     * Drawstack, then the suit stacks(Hearts, Clubs, Dimonds, Spades),
     * and finally the buildstacs ordered from 1-7
     * @return a list of card to initate the board.
     */// TODO: This class parses a json object to a string back to another json objects, there must be some way to do that smarter
    public ArrayList<Card> jsonToCard(JsonObject jsonObject){
        ArrayList<Card> cards = new ArrayList<>();
        // All the stacks that we iterate trough and makes cards from
        ArrayList<String> stacks = new ArrayList<>(
                Arrays.asList("drawPile",
                        "suitStack1",
                        "suitStack2",
                        "suitStack3",
                        "suitStack4",
                        "column1",
                        "column2",
                        "column3",
                        "column4",
                        "column5",
                        "column6",
                        "column7")
        );
        for(int i = 0; i< stacks.size();i++){
            //takeout the json object belonging to a vien key as a string
            String tmp = jsonObject.getAsJsonArray(stacks.get(i)).get(0).getAsString();
            //turn it back into a json object.
            JsonObject givenStack = new JsonParser().parse(tmp).getAsJsonObject();
            System.out.println("givenStack:"+givenStack);
            System.out.println("from json to card "+givenStack.getAsJsonPrimitive("suit"));
        }
        return cards;
    }


}
