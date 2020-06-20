package data;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import model.cabal.E_PileID;
import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is intented to translate the input string we get to a json object.
 */
public class InputDTO implements I_InputDTO {
    private String uiType;

    public InputDTO(String uiChoice) {
        uiType = uiChoice;
    }

    @Override
    public Map<String, I_CardModel> getUsrInput(){

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
            usrInput = accessInput.getInput(uiType);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return deserializeJson(usrInput);
    }

    /**
     * We are using the gson libary to transform our string into a map from String to {@link I_CardModel}
     *
     * @return a map representation of the top card of each pile in the physical game with the
     * {@code E_PileID.name()} as key to the card in the corresponding pile.
     */
    Map<String, I_CardModel> deserializeJson(String jsonString){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.serializeNulls();
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        Type mapType = new TypeToken<Map<String, Card>>(){}.getType();
        return gson.fromJson(jsonString, mapType);
    }




}
