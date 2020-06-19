package data;

import com.google.gson.Gson;
import model.cabal.E_PileID;
import model.cabal.internals.card.Card;
import model.cabal.internals.card.I_CardModel;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static model.cabal.internals.card.E_CardSuit.HEARTS;
import static org.junit.jupiter.api.Assertions.*;

class InputDTOTest {

    @Test
    void getUsrInput() {
    }

    @Test
    void stringToJson() {
        var gson = new Gson();
        InputDTO input = new InputDTO("");

        var cardMap = new HashMap<String, I_CardModel>();

        for (E_PileID pileID : E_PileID.values()) {
            cardMap.put(pileID.name(), new Card(HEARTS, pileID.ordinal() + 1));
        }

        var jsonString = gson.toJson(cardMap);

        System.out.println("jsonString = " + jsonString);

        var jsonMap = input.deserializeJson(jsonString);

        assertEquals(cardMap, jsonMap);
    }
}