package data;

import model.GameCardDeck;
import model.Move;
import model.cabal.Board;
import model.cabal.E_PileID;
import model.cabal.I_BoardModel;
import model.cabal.internals.I_SolitaireStacks;
import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;
import model.error.IllegalMoveException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class is intented to translate the input string we get to a json object.
 */
public class InputSimDTO implements I_InputDTO {

    private ArrayList<Card> listRemain = new ArrayList<>(4*13);
    private Map<String, I_CardModel> imgData;
    private I_BoardModel board;

    /**
     * A constructor taking no arguments. This will make the class return random unused card in each position.
     */
    public InputSimDTO(){
        // filling in ALL Cards
        E_CardSuit[] a = {E_CardSuit.HEARTS, E_CardSuit.DIAMONDS,E_CardSuit.CLUBS,E_CardSuit.SPADES};
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j <= 13; j++) {
                listRemain.add(new Card(a[i],j));
            }
        }
    }
    public void giveBoard(I_BoardModel board){
        this.board = board;
    }

    // retrieving Start Simulating data sets.
    public ArrayList<I_CardModel> getDrawstack(){
        ArrayList<I_CardModel> drawStack = new ArrayList<>();
        for (int i = 0; i <24 ; i++) {
            drawStack.add(popRanCard());
        }
        return drawStack;
    }
    public Map<String, I_CardModel> getCards(){
        Map<String, I_CardModel> usrInputMap = new HashMap<>();
        usrInputMap.put(E_PileID.DRAWSTACK.name(),          null);
        usrInputMap.put(E_PileID.SUITSTACKHEARTS.name(),    null);
        usrInputMap.put(E_PileID.SUITSTACKDIAMONDS.name(),  null);
        usrInputMap.put(E_PileID.SUITSTACKSPADES.name(),    null);
        usrInputMap.put(E_PileID.SUITSTACKCLUBS.name(),     null);
        usrInputMap.put(E_PileID.BUILDSTACK1.name(),        popRanCard());
        usrInputMap.put(E_PileID.BUILDSTACK2.name(),        popRanCard());
        usrInputMap.put(E_PileID.BUILDSTACK3.name(),        popRanCard());
        usrInputMap.put(E_PileID.BUILDSTACK4.name(),        popRanCard());
        usrInputMap.put(E_PileID.BUILDSTACK5.name(),        popRanCard());
        usrInputMap.put(E_PileID.BUILDSTACK6.name(),        popRanCard());
        usrInputMap.put(E_PileID.BUILDSTACK7.name(),        popRanCard());

        imgData = usrInputMap;
        return usrInputMap;
    }

    // interface
    @Override
    public Map<String, I_CardModel> getUsrInput() {
        return imgData;
    }

    /**
     * @return a random face up card within the confines of legal suit and rank values
     */
    public Card popRanCard(){
        int ran = getRandom( listRemain.size() );
        Card c = listRemain.get( ran );
        listRemain.remove(ran);
        return c;
    }
    private int getRandom(int maxValue){
        return (int) (Math.random() * maxValue);
    }


    public void move( Move m ){

        I_SolitaireStacks from = boardGet(m.moveFromStack());
        I_SolitaireStacks to = boardGet(m.moveToStack());

        //change state
        to.addAll(from.popSubset(m.moveFromRange()));

    }
        private I_SolitaireStacks boardGet(E_PileID pile){
            return board.getPiles()[pile.ordinal()];
        }

    private class SimBoard extends Board{
        public SimBoard(Map<String, I_CardModel> imgData, ArrayList<I_CardModel> drawStack) {
            super(imgData, drawStack);
        }

        @Override
        public void move(E_PileID origin, int originPos, E_PileID destination, Map<String, I_CardModel> imgData) throws IllegalMoveException {
            I_SolitaireStacks from = boardGet(origin);
            I_SolitaireStacks to = boardGet(destination);

            //change state
            to.addAll(from.popSubset(originPos));
        }

        public Map<String,I_CardModel> getImageData(){
            Map<String, I_CardModel>  map = new HashMap<>();
            for (E_PileID e: E_PileID.values() ) {
                List<I_CardModel> l = getPile(e);
                map.put(e.name(), l.get(l.size() -1));
            }
            return map;
        }
    }
}
