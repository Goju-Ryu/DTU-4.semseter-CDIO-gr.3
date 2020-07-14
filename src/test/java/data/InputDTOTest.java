package data;

import com.google.gson.Gson;
import model.GameCardDeck;
import model.cabal.Board;
import model.cabal.E_PileID;
import model.cabal.I_BoardModel;
import model.cabal.internals.card.Card;
import model.cabal.internals.card.I_CardModel;
import org.junit.jupiter.api.Test;
import util.TestUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static model.cabal.internals.card.E_CardSuit.*;
import static org.junit.jupiter.api.Assertions.*;

class InputDTOTest {

    @Test
    void getUsrInput(){}

    @Test
    void stringToJson(){
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

    @Test
    void deserializeJson(){
        String a = "{\"DRAWSTACK\": {\"suit\": \"HEARTS\", \"rank\": 2, \"isFacedUp\": \"true\"}, \"SUITSTACKHEARTS\": null, \"SUITSTACKCLUBS\": null, \"SUITSTACKDIAMONDS\": null, \"SUITSTACKSPADES\": null, \"BUILDSTACK1\": {\"suit\": \"HEARTS\", \"rank\": 3, \"isFacedUp\": \"true\"}, \"BUILDSTACK2\": {\"suit\": \"SPADES\", \"rank\": 4, \"isFacedUp\": \"true\"}, \"BUILDSTACK3\": {\"suit\": \"DIAMONDS\", \"rank\": 5, \"isFacedUp\": \"true\"}, \"BUILDSTACK4\": {\"suit\": \"HEARTS\", \"rank\": 6, \"isFacedUp\": \"true\"}, \"BUILDSTACK5\": {\"suit\": \"CLUBS\", \"rank\": 7, \"isFacedUp\": \"true\"}, \"BUILDSTACK6\": {\"suit\": \"HEARTS\", \"rank\": 9, \"isFacedUp\": \"true\"}, \"BUILDSTACK7\": {\"suit\": \"HEARTS\", \"rank\": 10, \"isFacedUp\": \"true\"}}";
        InputDTO dto = new InputDTO("testing");
        Map<E_PileID , I_CardModel> map = dto.deserializeJson(a);
        System.out.println(map);

        for (E_PileID e: E_PileID.values()) {
            I_CardModel mapsCard = map.get(e);
            switch(e){
                case DRAWSTACK:
                    assertEquals(2,mapsCard.getRank());
                    assertEquals(HEARTS,mapsCard.getSuit()); break;
                case SUITSTACKCLUBS:
                    assertNull(mapsCard); break;
                case SUITSTACKDIAMONDS:
                    assertNull(mapsCard); break;
                case SUITSTACKSPADES:
                    assertNull(mapsCard); break;
                case SUITSTACKHEARTS:
                    assertNull(mapsCard); break;
                case BUILDSTACK1:
                    assertEquals(3,mapsCard.getRank());
                    assertEquals(HEARTS,mapsCard.getSuit()); break;
                case BUILDSTACK2:
                    assertEquals(4,mapsCard.getRank());
                    assertEquals(SPADES,mapsCard.getSuit()); break;
                case BUILDSTACK3:
                    assertEquals(5,mapsCard.getRank());
                    assertEquals(DIAMONDS,mapsCard.getSuit()); break;
                case BUILDSTACK4:
                    assertEquals(6,mapsCard.getRank());
                    assertEquals(HEARTS,mapsCard.getSuit()); break;
                case BUILDSTACK5:
                    assertEquals(7,mapsCard.getRank());
                    assertEquals(CLUBS,mapsCard.getSuit()); break;
                case BUILDSTACK6:
                    assertEquals(9,mapsCard.getRank());
                    assertEquals(HEARTS,mapsCard.getSuit()); break;
                case BUILDSTACK7:
                    assertEquals(10,mapsCard.getRank());
                    assertEquals(HEARTS,mapsCard.getSuit()); break;
            }
        }
    }

    @Test
    void deserializeJson_AndBoardModel(){
        String a = "{\"DRAWSTACK\": {\"suit\": \"HEARTS\", \"rank\": 2, \"isFacedUp\": \"true\"}, \"SUITSTACKHEARTS\": null, \"SUITSTACKCLUBS\": null, \"SUITSTACKDIAMONDS\": null, \"SUITSTACKSPADES\": null, \"BUILDSTACK1\": {\"suit\": \"HEARTS\", \"rank\": 3, \"isFacedUp\": \"true\"}, \"BUILDSTACK2\": {\"suit\": \"SPADES\", \"rank\": 4, \"isFacedUp\": \"true\"}, \"BUILDSTACK3\": {\"suit\": \"DIAMONDS\", \"rank\": 5, \"isFacedUp\": \"true\"}, \"BUILDSTACK4\": {\"suit\": \"HEARTS\", \"rank\": 6, \"isFacedUp\": \"true\"}, \"BUILDSTACK5\": {\"suit\": \"CLUBS\", \"rank\": 7, \"isFacedUp\": \"true\"}, \"BUILDSTACK6\": {\"suit\": \"HEARTS\", \"rank\": 9, \"isFacedUp\": \"true\"}, \"BUILDSTACK7\": {\"suit\": \"HEARTS\", \"rank\": 10, \"isFacedUp\": \"true\"}}";
        InputDTO dto = new InputDTO("testing");
        Map<E_PileID , I_CardModel> map = dto.deserializeJson(a);
        System.out.println(map);

        // checking the combination of the Board
        var test = TestUtil.getTestReadyBoard(map);
        I_BoardModel board = test.getKey();
        System.out.println(board);

        for (E_PileID e: E_PileID.values() ) {
            if(e == E_PileID.SUITSTACKCLUBS ||e == E_PileID.SUITSTACKSPADES ||e == E_PileID.SUITSTACKHEARTS ||e == E_PileID.SUITSTACKDIAMONDS )
                continue;

            I_CardModel mapsCard = map.get(e.toString());
            List<I_CardModel> listC = board.getPile(e);
            I_CardModel boardsCard = getTopCard(listC);

            if(mapsCard.isFacedUp()) {
                assertTrue(boardsCard.isFacedUp());
                assertEquals(mapsCard.getRank(), boardsCard.getRank());
                assertEquals(mapsCard.getSuit(), boardsCard.getSuit());
            } else {
                assertFalse(boardsCard.isFacedUp());
            }
        }
    }
    private I_CardModel getTopCard(List<I_CardModel> list){
        int top = list.size()-1;
        return list.get(top);
    }
}