package data;

import com.google.gson.Gson;
import model.cabal.E_PileID;
import model.cabal.I_BoardModel;
import model.cabal.internals.card.Card;
import model.cabal.internals.card.I_CardModel;
import org.junit.jupiter.api.Test;
import util.TestUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static model.cabal.E_PileID.*;
import static model.cabal.internals.card.E_CardSuit.*;
import static org.junit.jupiter.api.Assertions.*;

class InputDTOTest {

    @Test
    void getUsrInput(){}

    @Test
    void stringToJson(){
        var gson = new Gson();
        InputDTO input = new InputDTO("");

        var cardMap = new HashMap<E_PileID, I_CardModel>();

        for (E_PileID pileID : E_PileID.values()) {
            cardMap.put(pileID, new Card(HEARTS, pileID.ordinal() + 1));
        }

        var jsonString = gson.toJson(cardMap);

        System.out.println("jsonString = " + jsonString);

        var jsonMap = input.deserializeJson(jsonString);

        assertEquals(cardMap, jsonMap);
    }

    @Test
    void deserializeJson(){
        String a = "{" +
                       "\"" +DRAWSTACK.name()+ "\": {\"suit\": \"HEARTS\", \"rank\": 2, \"isFacedUp\": \"true\"}, " +
                       "\"" +SUITSTACK_HEARTS+ "\": null, " +
                       "\"" +SUITSTACK_CLUBS+ "\": null, " +
                       "\"" +SUITSTACK_DIAMONDS+ "\": null, " +
                       "\"" +SUITSTACK_SPADES+ "\": null, " +
                       "\"" +BUILDSTACK_1.name()+ "\": {\"suit\": \"HEARTS\", \"rank\": 3, \"isFacedUp\": \"true\"}, " +
                       "\"" +BUILDSTACK_2.name()+ "\": {\"suit\": \"SPADES\", \"rank\": 4, \"isFacedUp\": \"true\"}, " +
                       "\"" +BUILDSTACK_3.name()+ "\": {\"suit\": \"DIAMONDS\", \"rank\": 5, \"isFacedUp\": \"true\"}, " +
                       "\"" +BUILDSTACK_4.name()+ "\": {\"suit\": \"HEARTS\", \"rank\": 6, \"isFacedUp\": \"true\"}, " +
                       "\"" +BUILDSTACK_5.name()+ "\": {\"suit\": \"CLUBS\", \"rank\": 7, \"isFacedUp\": \"true\"}, " +
                       "\"" +BUILDSTACK_6.name()+ "\": {\"suit\": \"HEARTS\", \"rank\": 9, \"isFacedUp\": \"true\"}, " +
                       "\"" +BUILDSTACK_7.name()+ "\": {\"suit\": \"HEARTS\", \"rank\": 10, \"isFacedUp\": \"true\"}" +
                   "}";
        InputDTO dto = new InputDTO("testing");
        Map<E_PileID , I_CardModel> map = dto.deserializeJson(a);
        System.out.println(map);

        for (E_PileID e: E_PileID.values()) {
            I_CardModel mapsCard = map.get(e);
            switch(e){
                case DRAWSTACK:
                    assertEquals(2,mapsCard.getRank());
                    assertEquals(HEARTS,mapsCard.getSuit()); break;
                case SUITSTACK_CLUBS:
                    assertNull(mapsCard); break;
                case SUITSTACK_DIAMONDS:
                    assertNull(mapsCard); break;
                case SUITSTACK_SPADES:
                    assertNull(mapsCard); break;
                case SUITSTACK_HEARTS:
                    assertNull(mapsCard); break;
                case BUILDSTACK_1:
                    assertEquals(3,mapsCard.getRank());
                    assertEquals(HEARTS,mapsCard.getSuit()); break;
                case BUILDSTACK_2:
                    assertEquals(4,mapsCard.getRank());
                    assertEquals(SPADES,mapsCard.getSuit()); break;
                case BUILDSTACK_3:
                    assertEquals(5,mapsCard.getRank());
                    assertEquals(DIAMONDS,mapsCard.getSuit()); break;
                case BUILDSTACK_4:
                    assertEquals(6,mapsCard.getRank());
                    assertEquals(HEARTS,mapsCard.getSuit()); break;
                case BUILDSTACK_5:
                    assertEquals(7,mapsCard.getRank());
                    assertEquals(CLUBS,mapsCard.getSuit()); break;
                case BUILDSTACK_6:
                    assertEquals(9,mapsCard.getRank());
                    assertEquals(HEARTS,mapsCard.getSuit()); break;
                case BUILDSTACK_7:
                    assertEquals(10,mapsCard.getRank());
                    assertEquals(HEARTS,mapsCard.getSuit()); break;
            }
        }
    }

    @Test
    void deserializeJson_AndBoardModel(){
        String a = "{" +
                       "\"" +DRAWSTACK.name()+ "\": {\"suit\": \"HEARTS\", \"rank\": 2, \"isFacedUp\": \"true\"}, " +
                       "\"" +SUITSTACK_HEARTS+ "\": null, " +
                       "\"" +SUITSTACK_CLUBS+ "\": null, " +
                       "\"" +SUITSTACK_DIAMONDS+ "\": null, " +
                       "\"" +SUITSTACK_SPADES+ "\": null, " +
                       "\"" +BUILDSTACK_1.name()+ "\": {\"suit\": \"HEARTS\", \"rank\": 3, \"isFacedUp\": \"true\"}, " +
                       "\"" +BUILDSTACK_2.name()+ "\": {\"suit\": \"SPADES\", \"rank\": 4, \"isFacedUp\": \"true\"}, " +
                       "\"" +BUILDSTACK_3.name()+ "\": {\"suit\": \"DIAMONDS\", \"rank\": 5, \"isFacedUp\": \"true\"}, " +
                       "\"" +BUILDSTACK_4.name()+ "\": {\"suit\": \"HEARTS\", \"rank\": 6, \"isFacedUp\": \"true\"}, " +
                       "\"" +BUILDSTACK_5.name()+ "\": {\"suit\": \"CLUBS\", \"rank\": 7, \"isFacedUp\": \"true\"}, " +
                       "\"" +BUILDSTACK_6.name()+ "\": {\"suit\": \"HEARTS\", \"rank\": 9, \"isFacedUp\": \"true\"}, " +
                       "\"" +BUILDSTACK_7.name()+ "\": {\"suit\": \"HEARTS\", \"rank\": 10, \"isFacedUp\": \"true\"}" +
                   "}";
        InputDTO dto = new InputDTO("testing");
        Map<E_PileID , I_CardModel> map = dto.deserializeJson(a);
        System.out.println(map);

        // checking the combination of the Board
        var test = TestUtil.getTestReadyBoard(map);
        I_BoardModel board = test.getKey();
        System.out.println(board);

        for (E_PileID e: E_PileID.values() ) {
            if(e == E_PileID.SUITSTACK_CLUBS || e == E_PileID.SUITSTACK_SPADES || e == E_PileID.SUITSTACK_HEARTS || e == E_PileID.SUITSTACK_DIAMONDS)
                continue;

            I_CardModel mapsCard = map.get(e);
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