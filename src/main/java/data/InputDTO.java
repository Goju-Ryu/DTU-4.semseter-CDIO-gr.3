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
    private String uiChoice;

    private boolean usedOnce = false;
    int drawIterator = 24;

    public InputDTO(String uiChoice) {
        uiType = uiChoice;
        this.uiChoice = uiChoice;
    }



    //TODO Modify to take just one card when uiType == RevealCardGUI
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

// __________If this gets commented out, and the card values are repetetively inserted in ManGUI, a game can be simulated ________________
            //TODO make sure that the RevealCardGUI is only called when a new card is revealed
//            if(usedOnce && uiType.equals("ManGUI")){
//                uiType = "ManGUI";//"RevealCardGUI";
//            }else{
//                this.usedOnce = true;
//            }

            if(drawIterator == 0){
                uiType = uiChoice;//"RevealCardGUI";
            }else{
                drawIterator--;
                uiType = "turnDrawstack";
            }
//_______________________________________________________________________________________________________________________________________

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
