import control.GameController;
import control.I_GameController;
import model.cabal.Board;
import model.cabal.E_PileID;
import model.cabal.I_BoardModel;
import model.cabal.internals.card.Card;
import model.cabal.internals.card.E_CardSuit;

import java.util.ArrayList;

public class main {
    public static void main(String[] args) {
        I_GameController Klondike = new GameController();


        ArrayList<Card> usrInput = new ArrayList<>();
        usrInput.add(new Card(E_CardSuit.HEARTS,1));
        usrInput.add(new Card());
        usrInput.add(new Card());
        usrInput.add(new Card());
        usrInput.add(new Card());
        usrInput.add(new Card(E_CardSuit.HEARTS,2));
        usrInput.add(new Card(E_CardSuit.HEARTS,3));
        usrInput.add(new Card(E_CardSuit.HEARTS,4));
        usrInput.add(new Card(E_CardSuit.HEARTS,5));
        usrInput.add(new Card(E_CardSuit.HEARTS,6));
        usrInput.add(new Card(E_CardSuit.HEARTS,7));
        usrInput.add(new Card(E_CardSuit.HEARTS,8));


        I_BoardModel board = new Board(usrInput.get(0), usrInput.get(5), usrInput.get(6),
                usrInput.get(7),usrInput.get(8), usrInput.get(9), usrInput.get(10),
                usrInput.get(11));

        System.out.println("Stack 7"+board.getPile(E_PileID.BUILDSTACK7));
        System.out.println("Stack 1"+board.getPile(E_PileID.BUILDSTACK1));
        System.out.println("turn pile:"+board.getPile(E_PileID.TURNPILE));


//        Klondike.startGame("ManGUI");
////        Klondike.startGame("sdsldfndslfnds");
//
//        System.out.println("Hello Main World!");
//        //change
    }
}
