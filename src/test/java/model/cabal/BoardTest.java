package model.cabal;

import model.cabal.internals.SuitStack;
import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;
import model.cabal.internals.card.I_CardModel;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    void isStackComplete() {

        Card card1 = new Card(E_CardSuit.SPADES,2);
        Card card2 = new Card(E_CardSuit.SPADES,1);
        Card card3 = new Card(E_CardSuit.SPADES,3);
        Card card4 = new Card(E_CardSuit.SPADES,4);
        Card card5 = new Card(E_CardSuit.SPADES,5);
        Card card6 = new Card(E_CardSuit.SPADES,6);
        Card card7 = new Card(E_CardSuit.SPADES,7);
        Card card8 = new Card(E_CardSuit.SPADES,8);

        I_BoardModel board = new Board(card1,card2,card3,card4,card5,card6,card7,card8);
        //I_BoardModel boardModel = new Board()

        //board.turnCard()

        if(board.canMove(E_PileID.BUILDSTACK1,0,E_PileID.SPADESACEPILE)){
            System.out.println("The move is legal");

            //board.move(E_PileID.BUILDSTACK1,E_PileID.SPADESACEPILE);
        }



        System.out.println(board.getPile(E_PileID.BUILDSTACK1));




    }

    @Test
    void getPile() {
    }

    @Test
    void getPiles() {
    }

    @Test
    void turnCard() {
    }

    @Test
    void getTurnedCard() {
    }

    @Test
    void move() {
    }

    @Test
    void canMove() {
    }

    @Test
    void addPropertyChangeListener() {
    }

    @Test
    void removePropertyChangeListener() {
    }

    private SuitStack createSuitStack(int elements, E_CardSuit suit){
        SuitStack suitStack = new SuitStack();

        for (int i = 0; i < elements; i++) {
            I_CardModel card = new Card(suit,i+1);
            suitStack.add(card);
        }

        return suitStack;
    }
}